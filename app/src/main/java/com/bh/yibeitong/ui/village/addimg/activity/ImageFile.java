package com.bh.yibeitong.ui.village.addimg.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.yibeitong.ui.village.PublishThemeActivity;
import com.bh.yibeitong.ui.village.Res;
import com.bh.yibeitong.ui.village.addimg.adapter.FolderAdapter;
import com.bh.yibeitong.ui.village.addimg.util.Bimp;
import com.bh.yibeitong.ui.village.addimg.util.BitmapCache;
import com.bh.yibeitong.ui.village.addimg.util.ImageItem;
import com.bh.yibeitong.ui.village.addimg.util.PublicWay;

import java.util.ArrayList;

/**
 * 这个类主要是用来进行显示包含图片的文件夹
 *
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:48:06
 */
public class ImageFile extends Activity {

	private FolderAdapter folderAdapter;
	private Button bt_cancel;
	private Context mContext;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		setContentView(Res.getLayoutID("plugin_camera_image_file"));
		PublicWay.activityList.add(this);
		mContext = this;
		bt_cancel = (Button) findViewById(Res.getWidgetID("cancel"));
		bt_cancel.setOnClickListener(new CancelListener());
		GridView gridView = (GridView) findViewById(Res.getWidgetID("fileGridView"));
		TextView textView = (TextView) findViewById(Res.getWidgetID("headerTitle"));
		textView.setText(Res.getString("photo"));
		folderAdapter = new FolderAdapter(this);
		gridView.setAdapter(folderAdapter);
	}

	private class CancelListener implements OnClickListener {// 取消按钮的监听
		public void onClick(View v) {
			//清空选择的图片
			Bimp.tempSelectBitmap.clear();
			Intent intent = new Intent();
			/*intent.setClass(mContext, PublishThemeActivity.class);
			startActivity(intent);*/
			ImageFile.this.finish();
		}
	}

	/*public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(mContext, PublishThemeActivity.class);
			startActivity(intent);
		}
		
		return true;
	}*/

	public class FolderAdapter extends BaseAdapter {

		private Context mContext;
		private Intent mIntent;
		private DisplayMetrics dm;
		BitmapCache cache;
		final String TAG = getClass().getSimpleName();
		public FolderAdapter(Context c) {
			cache = new BitmapCache();
			init(c);
		}

		// 初始化
		public void init(Context c) {
			mContext = c;
			mIntent = ((Activity) mContext).getIntent();
			dm = new DisplayMetrics();
			((Activity) mContext).getWindowManager().getDefaultDisplay()
					.getMetrics(dm);
		}



		@Override
		public int getCount() {
			return AlbumActivity.contentList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
			@Override
			public void imageLoad(ImageView imageView, Bitmap bitmap,
								  Object... params) {
				if (imageView != null && bitmap != null) {
					String url = (String) params[0];
					if (url != null && url.equals((String) imageView.getTag())) {
						((ImageView) imageView).setImageBitmap(bitmap);
					} else {
						Log.e(TAG, "callback, bmp not match");
					}
				} else {
					Log.e(TAG, "callback, bmp null");
				}
			}
		};

		private class ViewHolder {
			//
			public ImageView backImage;
			// 封面
			public ImageView imageView;
			public ImageView choose_back;
			// 文件夹名称
			public TextView folderName;
			// 文件夹里面的图片数量
			public TextView fileNum;
		}
		ViewHolder holder = null;
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						Res.getLayoutID("plugin_camera_select_folder"), null);
				holder = new ViewHolder();
				holder.backImage = (ImageView) convertView
						.findViewById(Res.getWidgetID("file_back"));
				holder.imageView = (ImageView) convertView
						.findViewById(Res.getWidgetID("file_image"));
				holder.choose_back = (ImageView) convertView
						.findViewById(Res.getWidgetID("choose_back"));
				holder.folderName = (TextView) convertView.findViewById(Res.getWidgetID("name"));
				holder.fileNum = (TextView) convertView.findViewById(Res.getWidgetID("filenum"));
				holder.imageView.setAdjustViewBounds(true);
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,dipToPx(65));
//			lp.setMargins(50, 0, 50,0);
//			holder.imageView.setLayoutParams(lp);
				holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				convertView.setTag(holder);
			} else
				holder = (ViewHolder) convertView.getTag();
			String path;
			if (AlbumActivity.contentList.get(position).imageList != null) {

				//path = photoAbsolutePathList.get(position);
				//封面图片路径
				path = AlbumActivity.contentList.get(position).imageList.get(0).imagePath;
				// 给folderName设置值为文件夹名称
				//holder.folderName.setText(fileNameList.get(position));
				holder.folderName.setText(AlbumActivity.contentList.get(position).bucketName);

				// 给fileNum设置文件夹内图片数量
				//holder.fileNum.setText("" + fileNum.get(position));
				holder.fileNum.setText("" + AlbumActivity.contentList.get(position).count);

			} else
				path = "android_hybrid_camera_default";
			if (path.contains("android_hybrid_camera_default"))
				holder.imageView.setImageResource(Res.getDrawableID("plugin_camera_no_pictures"));
			else {
//			holder.imageView.setImageBitmap( AlbumActivity.contentList.get(position).imageList.get(0).getBitmap());
				final ImageItem item = AlbumActivity.contentList.get(position).imageList.get(0);
				holder.imageView.setTag(item.imagePath);
				cache.displayBmp(holder.imageView, item.thumbnailPath, item.imagePath,
						callback);
			}
			// 为封面添加监听
			holder.imageView.setOnClickListener(new ImageViewClickListener(
					position, mIntent,holder.choose_back));

			return convertView;
		}

		// 为每一个文件夹构建的监听器
		private class ImageViewClickListener implements OnClickListener {
			private int position;
			private Intent intent;
			private ImageView choose_back;
			public ImageViewClickListener(int position, Intent intent, ImageView choose_back) {
				this.position = position;
				this.intent = intent;
				this.choose_back = choose_back;
			}

			public void onClick(View v) {
				ShowAllPhoto.dataList = (ArrayList<ImageItem>) AlbumActivity.contentList.get(position).imageList;
				Intent intent = new Intent();
				String folderName = AlbumActivity.contentList.get(position).bucketName;
				intent.putExtra("folderName", folderName);
				intent.setClass(mContext, ShowAllPhoto.class);
				mContext.startActivity(intent);
				choose_back.setVisibility(v.VISIBLE);
				ImageFile.this.finish();
			}
		}

		public int dipToPx(int dip) {
			return (int) (dip * dm.density + 0.5f);
		}

	}


}
