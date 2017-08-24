package com.bh.yibeitong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.Interface.CallbackCart;
import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.AddShopCart;
import com.bh.yibeitong.bean.ShopNew;
import com.bh.yibeitong.utils.GetMsgForNet;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.lidroid.xutils.BitmapUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/11/5.
 * 商品 详细信息 适配器
 */

public class ShopNewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ShopNew.MsgBean.ChildBean.DetBean> detBeen = new ArrayList<>();

    private CallbackCart callbackCart;
    private int good_num;

    public ShopNewAdapter(Context mContext, List<ShopNew.MsgBean.ChildBean.DetBean> detBeen,
                          CallbackCart callbackCart) {
        this.mContext = mContext;
        this.detBeen = detBeen;
        this.callbackCart = callbackCart;
    }

    @Override
    public int getCount() {
        return detBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return detBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shopnew, null);

            vh.imager = (ImageView) convertView.findViewById(R.id.iv_item_shopnew_ss);
            vh.name = (TextView) convertView.findViewById(R.id.tv_item_shopnew_ss_title);
            vh.cost = (TextView) convertView.findViewById(R.id.tv_item_shopnew_ss_price);
            vh.sellcount = (TextView) convertView.findViewById(R.id.tv_item_shopnew_ss_num);

            //添加购物车
            vh.shopCart_num = (TextView) convertView.findViewById(R.id.tv_shopnew_num);
            vh.shopCart_add = (ImageView) convertView.findViewById(R.id.iv_shopnew_add);
            vh.shopCart_sub = (ImageView) convertView.findViewById(R.id.iv_shopnew_sub);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // 组件赋值
        String imgPath = detBeen.get(position).getImg();
        String name = detBeen.get(position).getName();
        String cost = detBeen.get(position).getCost();
        String sellcount = detBeen.get(position).getSellcount();
        String point = detBeen.get(position).getPoint();//不知道代表什么 先代表购物车内商品数量吧

        final String shopid = detBeen.get(position).getShopid();

        int cartNum = detBeen.get(position).getCartnum();

        if (imgPath.equals("")) {
            vh.imager.setImageResource(R.mipmap.yibeitong001);

        } else {
            BitmapUtils bitmapUtils = new BitmapUtils(mContext);

            bitmapUtils.configDiskCacheEnabled(true);
            bitmapUtils.configMemoryCacheEnabled(false);
            bitmapUtils.display(vh.imager, imgPath);
        }

        vh.name.setText(name);
        vh.sellcount.setText("月售  " + sellcount);
        vh.cost.setText("￥" + cost);

        vh.shopCart_num.setText(point);

        vh.shopCart_num.setText(""+cartNum);
        //购物车为0  隐藏减少按钮和数量
        if(cartNum == 0){
            vh.shopCart_sub.setVisibility(View.INVISIBLE);
            vh.shopCart_num.setVisibility(View.INVISIBLE);
        }else if(cartNum > 0){
            vh.shopCart_sub.setVisibility(View.VISIBLE);
            vh.shopCart_num.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(mContext, "格式不正确", Toast.LENGTH_SHORT).show();
        }

        String str_num = vh.shopCart_num.getText().toString();
        good_num = Integer.valueOf(str_num);
        final String goodId = detBeen.get(position).getId();


        //添加购物车
        vh.shopCart_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加购物车  请求接口 成功则添加 反之不添加
                //添加购物车

                RequestParams params = new RequestParams(
                        HttpPath.PATH + HttpPath.ADD_SHOPCART
                                + "shopid="+shopid + "&num=" + 1 + "&gid=" + goodId
                );
                x.http().post(params,
                        new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {

                                AddShopCart addShopCart = GsonUtil.gsonIntance().gsonToBean(result, AddShopCart.class);

                                if (addShopCart.isError() == false) {
                                    System.out.println("添加成功" + addShopCart.getMsg());
                                    if (good_num >= 0 && good_num < 100) {
                                        vh.shopCart_num.setVisibility(View.VISIBLE);
                                        vh.shopCart_sub.setVisibility(View.VISIBLE);
                                        good_num++;

                                    }

                                    System.out.println("good_num = " + good_num);
                                    vh.shopCart_num.setText(String.valueOf(good_num));

                                    //响应按钮点击事件,调用子定义接口，并传入View
                                    callbackCart.click(position, 2);

                                } else {
                                    System.out.println("添加失败");
                                }

                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                            }

                            @Override
                            public void onFinished() {
                            }
                        });


            }
        });

        //减少购物车
        vh.shopCart_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //减少购物车  请求接口 成功则减少 反之 不减少

                String str_num = vh.shopCart_num.getText().toString();
                good_num = Integer.valueOf(str_num);

                //获取购物车信息
                //GetMsgForNet.getShopCart("8");
                RequestParams params = new RequestParams(
                        HttpPath.PATH + HttpPath.ADD_SHOPCART + "shopid=" + shopid + "&num=-1" + "&gid=" + goodId
                );
                x.http().post(params,
                        new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                AddShopCart addShopCart = GsonUtil.gsonIntance().gsonToBean(result, AddShopCart.class);

                                if (addShopCart.isError() == false) {
                                    System.out.println("减少成功" + addShopCart.getMsg());
                                    if (good_num > 1 && good_num <= 100) {
                                        good_num--;
                                        String string = String.valueOf(good_num);

                                        vh.shopCart_num.setText(string);
                                    } else if (good_num == 1) {
                                        vh.shopCart_num.setVisibility(View.INVISIBLE);
                                        vh.shopCart_sub.setVisibility(View.INVISIBLE);
                                        good_num--;
                                    } else {
                                        System.out.print("错误");

                                    }

                                    System.out.println("good_num = " + good_num);
                                    vh.shopCart_num.setText(String.valueOf(good_num));

                                    //响应按钮点击事件,调用子定义接口，并传入View
                                    callbackCart.click(position, 1);

                                } else {
                                    System.out.println("添加失败");
                                }
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {

                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });


                vh.shopCart_num.setText(String.valueOf(good_num));


                //响应按钮点击事件,调用子定义接口，并传入View
                callbackCart.click(position, 1);

            }
        });

        return convertView;
    }

    public class ViewHolder {
        private ImageView imager;
        private TextView name, cost, sellcount;

        private TextView shopCart_num;
        private ImageView shopCart_add, shopCart_sub;

    }

    /**
     * 购物车操作
     * num = 1 为添加
     * num = -1 为减少
     *
     * @param shopid
     * @param num
     * @param gid
     */
    public void addShopCart(String shopid, final int num, String gid) {
        String PATH = HttpPath.PATH + HttpPath.ADD_SHOPCART + "shopid=" + shopid + "&num=" + num + "&gid=" + gid;

        RequestParams params = new RequestParams(PATH);
        x.http().post(params,
                new Callback.CommonCallback<String>() {

                    @Override
                    public void onSuccess(String result) {
                        if (num == 1) {
                            System.out.println("添加购物车成功");
                        } else if (num == -1) {
                            System.out.println("减少购物车成功");
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        if (num == 1) {
                            System.out.println("添加购物车失败");
                        } else if (num == -1) {
                            System.out.println("减少购物车失败");
                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

    }
}
