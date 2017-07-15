package com.bh.yibeitong.refresh;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.yibeitong.R;
import com.bh.yibeitong.bean.AddShopCart;
import com.bh.yibeitong.bean.GoodsIndex;
import com.bh.yibeitong.bean.ShopCartReturn;
import com.bh.yibeitong.ui.CateFoodDetailsActivity;
import com.bh.yibeitong.utils.GsonUtil;
import com.bh.yibeitong.utils.HttpPath;
import com.lidroid.xutils.BitmapUtils;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

//import com.bh.yibeitong.bean.CeShi;

public class GridViewAdapter extends BaseAdapter {
    Context context;

    private int good_num;

    private List<GoodsIndex.MsgBean.CatefoodslistBean> foodList = new ArrayList<>();

    public void setCatefoodslistBeanList(Context mContext, List<GoodsIndex.MsgBean.CatefoodslistBean> foodList) {
        this.context = mContext;
        this.foodList = foodList;
        this.notifyDataSetChanged();
    }

    //private List<CeShi.MsgBean.CatefoodslistBean> foodList = new ArrayList<>();
    /*public void setCatefoodslistBeanList(Context mContext, List<CeShi.MsgBean.CatefoodslistBean> foodList) {
        this.context = mContext;
        this.foodList = foodList;
        this.notifyDataSetChanged();
    }*/

    //Callback callback;
    /*public GridViewAdapter(Context context, Callback callback) {
        this.context = context;
        this.mCallback = callback;

    }*/

    public interface Callback {
        void click(int position, int index);
    }


    @Override
    public int getCount() {

        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_self_support, null);
            //获取item中的组件

            vh.imager = (ImageView) convertView.findViewById(R.id.iv_item_ss);
            vh.title = (TextView) convertView.findViewById(R.id.tv_item_ss_title);
            vh.price = (TextView) convertView.findViewById(R.id.tv_item_ss_price);
            vh.num = (TextView) convertView.findViewById(R.id.tv_item_ss_num);

            //添加购物车
            vh.tv_shop_num = (TextView) convertView.findViewById(R.id.tv_shop_num);
            vh.iv_add_button = (ImageView) convertView.findViewById(R.id.iv_add);
            vh.iv_sub_botton = (ImageView) convertView.findViewById(R.id.iv_sub);

            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // 组件赋值
        String imgPath = foodList.get(position).getImg();

        //购物车数量
        int cartNum = foodList.get(position).getCartnum();
        vh.tv_shop_num.setText("" + cartNum);

        if (cartNum == 0) {
            vh.tv_shop_num.setVisibility(View.INVISIBLE);
            vh.iv_sub_botton.setVisibility(View.INVISIBLE);
        } else if (cartNum > 0) {
            vh.tv_shop_num.setVisibility(View.VISIBLE);
            vh.iv_sub_botton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(context, "格式不正确", Toast.LENGTH_SHORT).show();
        }

        if (foodList.get(position).getImg().equals("")) {
            vh.imager.setImageResource(R.mipmap.yibeitong001);

        } else {
            BitmapUtils bitmapUtils = new BitmapUtils(context);

            bitmapUtils.configDiskCacheEnabled(true);
            bitmapUtils.configMemoryCacheEnabled(false);

            bitmapUtils.display(vh.imager, "http://www.ybt9.com/" + imgPath);

        }

        /*id 详情图片 名称 已售 评价 单价 单位 购物车数量*/
        final String str_id = foodList.get(position).getId();
        final String instro = foodList.get(position).getInstro();
        final String foodName = foodList.get(position).getName();
        final int str_foodSellCount = foodList.get(position).getSellcount();
        final String foodPoint = foodList.get(position).getPoint();
        final String foodCost = foodList.get(position).getCost();
        final String foodGoodattr = foodList.get(position).getGoodattr();
        final int str_cartNum = foodList.get(position).getCartnum();

        /*点击图片查看商品详细信息*/
        vh.imager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击" + position);

                Intent intent = new Intent(context, CateFoodDetailsActivity.class);
                intent.putExtra("id", str_id);//商品id
                intent.putExtra("instro", instro);

                /*intent.putExtra("foodName", foodName);
                intent.putExtra("foodSellCount", String.valueOf(str_foodSellCount));
                intent.putExtra("foodPoint", foodPoint);
                intent.putExtra("foodCost", foodCost);
                intent.putExtra("foodGoodattr", foodGoodattr);

                intent.putExtra("cartNum", String.valueOf(str_cartNum));*/

                context.startActivity(intent);
            }
        });

        vh.title.setText(foodList.get(position).getName());

        vh.price.setText("￥" + foodList.get(position).getCost());

        vh.num.setText("月售" + foodList.get(position).getSellcount() + "笔");

        //增加
        vh.iv_add_button.setOnClickListener(new View.OnClickListener() {
            //long lastClick ;
            @Override
            public void onClick(View v) {
                vh.iv_add_button.setClickable(false);

                //大于一秒方个通过
                /*if (System.currentTimeMillis() - lastClick <= 0){
                    return;
                }else{*/
                //获取shopcart的初始值
                String str_num = vh.tv_shop_num.getText().toString();
                good_num = Integer.valueOf(str_num);
                String shopId = foodList.get(position).getShopid();

                String goodId = foodList.get(position).getId();

                String PATH = HttpPath.PATH_REALM + HttpPath.PATH_MODE + HttpPath.PATH_ADD_SHOPCART
                        + "shopid=" + shopId + "&num=1" + "&gid=" + goodId;

                RequestParams params = new RequestParams(PATH);
                x.http().post(params,
                        new org.xutils.common.Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {

                                vh.iv_add_button.setClickable(true);
                                System.out.println("添加成功 返回" + result);

                                ShopCartReturn shopCartReturn =
                                        GsonUtil.gsonIntance().gsonToBean(result, ShopCartReturn.class);

                                if (shopCartReturn.getMsg().isResult() == true) {

                                    AddShopCart addShopCart = GsonUtil.gsonIntance().gsonToBean(result, AddShopCart.class);


                                    if (addShopCart.isError() == false) {
                                        if (good_num >= 0 && good_num < 100) {
                                            vh.tv_shop_num.setVisibility(View.VISIBLE);
                                            vh.iv_sub_botton.setVisibility(View.VISIBLE);
                                            good_num++;

                                        }
                                    }
                                    vh.tv_shop_num.setText(String.valueOf(good_num));

                                    //响应按钮点击事件,调用子定义接口，并传入View
                                    //mCallback.click(position, 2);
                                } else if (shopCartReturn.getMsg().isResult() == false) {
                                    Toast.makeText(context, "添加失败，库存不足", Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                System.out.println("添加购物车失败" + isOnCallback);
                                //Toast.makeText(context, "添加购物车失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });

//                    }
//                lastClick = System.currentTimeMillis();

            }
        });

        //减少购物车
        vh.iv_sub_botton.setOnClickListener(new View.OnClickListener() {
            //long lastClick ;
            @Override
            public void onClick(View v) {

                vh.iv_sub_botton.setClickable(false);
                //获取shopcart的初始值
                String str_num = vh.tv_shop_num.getText().toString();
                good_num = Integer.valueOf(str_num);

                String shopId = foodList.get(position).getShopid();

                String goodId = foodList.get(position).getId();

                System.out.println("shopid = " + shopId + "  goodnum = " + good_num + "   goodid = " + goodId);

                //添加购物车
                //String PATH = "http://www.ybt9.com//index.php?ctrl=app&source=1&datatype=json&action=addcart&shopid=8&num=1&gid=110";

                String PATH = HttpPath.PATH_REALM + HttpPath.PATH_MODE + HttpPath.PATH_ADD_SHOPCART
                        + "shopid=" + shopId + "&num=-1" + "&gid=" + goodId;

                RequestParams params = new RequestParams(PATH);
                x.http().post(params,
                        new org.xutils.common.Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                vh.iv_sub_botton.setClickable(true);
                                System.out.println("减少购物车 返回" + result);

                                ShopCartReturn shopCartReturn =
                                        GsonUtil.gsonIntance().gsonToBean(result, ShopCartReturn.class);

                                if(shopCartReturn.getMsg().isResult() == true){

                                    AddShopCart addShopCart = GsonUtil.gsonIntance().gsonToBean(result, AddShopCart.class);

                                    if (good_num > 1 && good_num <= 100) {
                                        good_num--;
                                        String string = String.valueOf(good_num);

                                        vh.tv_shop_num.setText(string);
                                    } else if (good_num == 1) {
                                        vh.tv_shop_num.setVisibility(View.INVISIBLE);
                                        vh.iv_sub_botton.setVisibility(View.INVISIBLE);
                                        good_num--;
                                    } else {
                                        System.out.print("错误");

                                    }

                                    System.out.println("good_num = " + good_num);
                                    vh.tv_shop_num.setText(String.valueOf(good_num));

                                    //响应按钮点击事件,调用子定义接口，并传入View
                                    //mCallback.click(position, 1);
                                }else if(shopCartReturn.getMsg().isResult() == false){
                                    Toast.makeText(context, "减少失败，库存不足", Toast.LENGTH_SHORT).show();
                                }



                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                System.out.println("添加购物车失败" + isOnCallback);
                                //Toast.makeText(context, "减少购物车失败", Toast.LENGTH_SHORT).show();
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

        return convertView;

    }

    public class ViewHolder {
        private ImageView imager;
        private TextView title, price, num;

        private ImageView iv_add_button, iv_sub_botton;
        private TextView tv_shop_num;
    }

}
