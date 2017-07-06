package com.bh.yibeitong.utils;

/**
 * Created by jingang on 2016/10/23.
 * 用于网络请求的Path
 * <p>
 * 商店： http://www.ybt9.com/index.php?ctrl=app&action=goodsindex&
 * lat=35.11121798058952&lng=35.11121798058952&page=1&datatype=json
 * <p>
 * 商品： http://www.ybt9.com/index.php?ctrl=app&action=goodsone&datatype=json&
 * goodsid=46
 * <p>
 * 添加购物车：http://www.ybt9.com/index.php?ctrl=app&action=addcart&datatype=json
 * shopid   num   gid
 * <p>
 * 购物车列表：http://www.ybt9.com/index.php?ctrl=app&action=cart&datatype=json&
 * shopid=100
 * <p>
 * 商店 商品详细信息
 * http://www.ybt9.com/index.php?ctrl=app&action=getshopnew&datatype=json&
 * shopid
 * <p>
 * 商家实景 商家简介
 * http://www.ybt9.com/index.php?ctrl=app&action=getShopRealInfo&datatype=json
 * shopid=8
 * i * 获取轮播图什么的
 * http://www.ybt9.com//index.php?ctrl=app&source=1&action=getnewadv&datatype=json
 * <p>
 * 登录
 * action=appMemlogin   uname登录名  pwd密码
 * <p>
 * 注册
 * http://www.ybt9.com/index.php?ctrl=app&action=reg&datatype=json&
 * tname=&pwd=888&pwd2=888&phone=17865069350&code
 * action=reg   tname（可空）   pwd   phone   pwd2 email（可空）   code验证码
 * <p>
 * http://www.ybt9.com//index.php?ctrl=app&source=1&datatype=json&action=reg&tname=jingangdan&pwd=123456&pwd2=123456&phone=17865069350&email=&code=1151
 */

public class HttpPath {
    //域名
    public static String PATH_REALM = "https://www.ybt9.com/";

    //借口的语言
    public static String PATH_MODE = "index.php?ctrl=app&";

    //商品列表 lat=35.11121798058952&lng=118.3519727194077
    public static String PATH_GOOD_LIST = "action=goodsindex&page=1&datatype=json&";

    //商品详情 goodsid
    public static String PATH_GOOD_DETAILS = "action=goodsone&datatype=json&";//商品详情

    //添加购物车 shopid   num   gid
    public static String PATH_ADD_SHOPCART = "action=addcart&datatype=json&";//添加购物车

    //获取购物车列表 shopid
    public static String PATH_SHOPCART = "action=cart&datatype=json&";

    //商店 商品详细信息
    public static String PATH_SHOPALL = "action=getshopnew&datatype=json&";

    //注册
    public static String PATH_REGISTER = "action=reg&datatype=json&";


    //获取首页数据 lat lng
    public static String GOODS_INDEX = "action=goodsindex&";


    //接口地址
    public static String PATH = "https://www.ybt9.com//index.php?ctrl=app&source=1&datatype=json&";

    //checkphone验证手机号是否存在 phone
    public static String CHECKPHONE = "action=checkphone&";

    //发送验证码 phone type（验证码类型）
    public static String SENDREGPHONE = "action=sendregphone&";

    //注册tname pwd pwd2 phone email code
    public static String REG = "action=reg&";

    //登录  uname登录名  pwd密码
    public static String APPMENLOGIN = "action=appMemlogin&";

    //快速登录 phone code
    public static String QUICK_LOGIN = "action=fastlogin&";

    /**
     * 获取用户信息
     * 用户登录验证
     */
    public static String USER_GETAPPMEM = "action=getappMem&";

    //首页 lat（经度）  lng（纬度）  page（页数）
    public static String GOODSINDEX = "action=goodsindex&";

    //获取购物车 shopid
    public static String GETCART = "action=cart&";

    /*
    根据货号获取商品信息
    gno	是	string	货号
    shopid	是	string	店铺id*/
    public static String GETGOODS = "action=getgoods&";

    /*
    * 添加（减少） 购物车
    * shopid 店铺id
    * num 商品数量（num为正数时添加，num为负数时减少）
    * gid 商品id
    * */
    public static String ADD_SHOPCART = "action=addcart&";

    /**
     * 删除购物车
     * shopid
     */
    public static String SHOPCART_DEL = "action=delcart&";

    //获取轮播图
    public static String ADV = "action=getadvbytype&";

    //新增/编辑收货地址
    /**
     * uid	是	int	用户id
     * pwd	是	string	密码
     * addresid	否	string	收获地址id 存在为编辑，不存在为添加
     * phone	是	string	联系电话
     * contactname	是	string	联系人
     * default	否	string	默认收获地址 0/1
     * sex	否	string	性别1男2女0未知
     * bigadr	是	string	选择地址
     * detailadr	是	string	详细地址
     * logintype	是	string	快捷登录传 phone /密码登录传 空
     * loginphone	是	string	用户手机号
     * lat	是	string	纬度
     * lng	是	string	经度
     */
    public static String ADDRESS_ADD = "action=addaddress&";

    /**
     * 删除收货地址
     * uid	是	string	用户id
     * pwd	是	string	密码
     * addresid	是	string	收获地址id
     */
    public static String ADDRESS_DEL = "action=deladdress&";

    //设置默认收货地址
    /**
     * uid	是	string	用户id
     * pwd	是	string	密码
     * addresid	是	int	收货地址id
     */
    public static String ADDRESS_SETDEF = "action=setdefaddress&";

    //我的收货地址
    /**
     * uid	是	int	用户id
     * pwd	是	string	密码
     * page	否	int	页码
     */
    public static String ADDRESS_LIST = "action=addresslist&";

    //生成订单
    /**
     * shopid	是	int	店铺id
     * lat	是	string	经纬度
     * lng	是	string	经纬度
     * ids	是	string	商品id 格式1，2，3
     * idscount	是	int	商品数量
     * pids	否	string	productid 格式1，2，3
     * pnum	否	string	productcount
     * payline	是	string	是否在线支付0/1
     * uid	否	string	用户id
     * pwd	否	string	用户密码
     * mobile	是	string	联系手机
     * address	是	string	联系地址
     * contactname	是	string	联系人
     * ordertype	否	string	订单类型 订餐方式1网站，2电话，3微信，4App
     * pstime	是	int	配送时间
     * beizhu	否	string	备注
     * yhjid	否	string	优惠券id
     */
    public static String ORDER_NEWMAKE = "action=newmakeorder&";

    //订单列表
    /**
     * 用户登录验证			密码登录：uid ，pwd ；快捷登录：logintype loginphone
     * ordershowtype	否	int	订单类型 1、外卖 2、预定 3、超市
     * pagesize	否	int	每页显示数量
     * page	否	int	页码
     */
    public static String ORDER_NEW = "action=neworder&";

    //订单详情
    /**
     * 用户登录验证
     * orderid	是	int	订单id
     */
    public static String ORDER_NEWDET = "action=neworderdet&";

    /**
     * 删除订单
     * 用户登录验证
     * doname	是	string	订单操作 delorder
     * orderid	是	string	orderid
     */
    public static String ORDER_DEL = "action=newordercontrol&doname=delorder";

    /**
     * 获取支付密钥啥的
     * uid=13
     * &pwd=aaaaaa
     * &cost=43
     * &orderid=22670
     * <p>
     * http://www.ybt9.com/index.php?ctrl=app&source=1&datatype=json&
     * uid=13&pwd=aaaaaa&cost=43&orderid=22670
     */
    public static String PAY_APPDATA = "action=apppaydata&";

    /**
     * 余额支付
     * <p>
     * http://m6.waimairen.com/index.php?ctrl=app&source=1&
     * action=appacoutpay&
     * uid=9325&
     * pwd=123456&
     * cost=102.00&
     * orderid=24121
     */
    public static String PAY_APPACOUT = "action=appacoutpay&type=order&";

    /**
     * 配送时间
     * http://www.ybt.com/index.php?ctrl=app&datatype=json&
     * action=getpscost&
     * shopid=8&
     * lat=35.110958&
     * lng=118.3521
     */
    public static String PS_COST = "action=getpscost&";


    /**
     * 礼品列表
     * 无参
     * http://www.ybt9.com/index.php?ctrl=app&action=giftlist&datatype=json
     */
    public static String GIFT_LIST = "action=giftlist&";

    /**
     * 礼品操作
     * <p>
     * 用户登录验证
     * id	是	int	积分兑换记录id
     * controlname	是	string	操作名称 colse-取消兑换 del-删除记录
     * <p>
     * http://www.ybt9.com/index.php?ctrl=app&action=controlgift&datatype=json
     */
    public static String GIFT_CONTROL = "action=controlgift&";

    /**
     * 兑换礼品
     * http://www.ybt9.com/index.php?ctrl=app&action=exchange&datatype=json
     * <p>
     * 用户登陆验证
     * id	是	int	礼品id
     * address	是	string	地址
     * contactname	是	string	联系人
     * phone	是	string	手机号
     */

    public static String GIFT_EXCHANGE = "action=exchange";

    /**
     * 兑换记录
     * http://www.ybt9.com/index.php?ctrl=app&action=exgiftlog&datatype=json
     * <p>
     * 用户登录验证
     * page	是	int	页数
     * pagesize	否	int	每页个数
     */
    public static String GIFT_EXLOG = "action=exgiftlog&";

    /**
     * 店铺列表
     * https://www.ybt9.com/index.php?ctrl=app&action=shop
     * lat	是	string	经纬度
     * lng	是	string	经纬度
     * showtype	否	string	排序方式 ：juli/cost/is_com
     * backtype	是	string	如果大于0返回店铺开店时间
     * areaid	否	string	区域id
     * page	否	string	页码
     */
    public static String SHOP_LIST = "action=shop&";

    /**
     * 新店铺列表
     * <p>
     * shopopentype	是	int	0 表示 获取外卖，订台 预订 1表示商城
     * ordertype	是	string	0默认 1距离 2起送 3推荐
     * areaid	否	string	表示配送ID
     * limitcosttype	是	string	起送价格类型 0不限制 1低于 5元 2 5到10元 3 10元以上
     * is_waimai	否	string	表示外送
     * is_goshop	否	string	表示到店
     * searchvalue	否	string	搜索关键字
     * lat	是	string	经纬度
     * lng	是	string	经纬度
     * shoptype	否	string	店铺类型
     * is_com	否	string	推荐
     * is_hot	否	string	热卖
     * is_new	否	string	新店
     * page	否	string	页码
     * pagesize	否	string	每页个数
     */
    public static String SHOP_NEW_LIST = "action=newshop&";

    /**
     * 店铺信息
     * 用户登录验证
     * shopid	是	string	店铺id
     * lat	是	string	经纬度
     * lng	是	string	经纬度
     * source	是	string	不知道干嘛的 传1就对了
     */
    public static String SHOP_INFO = "action=newshopinfo&";

    /**
     * 获取商店类型
     * is_market  是否是超市 0不是 1是
     */
    public static String SHOP_TYPE = "action=getshoptype&";

    /**
     * 搜索  获取热搜词
     */
    public static String SEARCH_HOT_KEY = "action=searchhotkey&";

    /**
     * 搜索店铺
     * http://m6.waimairen.com/index.php?ctrl=app&source=1&source=1&action=newsearchshop&
     * datatype=json&
     * searchvalue=%E5%B1%B1%E6%A5%82&
     * lat=35.110994628562021&
     * lng=118.3519216654051
     */
    public static String SHOP_NEW_SRARCH = "action=newsearchshop&";

    /**
     * 获取论坛模块 二手交易 小区信息
     */
    public static String LTMK_LIST = "action=getltmk&";

    /**
     * 获取以关注和未关注列表
     * 用户登陆验证	是
     * mkid	是	int	模块id
     */
    public static String VILLAGE_LUNTAN = "action=luntan&";

    /**
     * 小区搜索
     * keyword	是	string	小区搜索关键词
     * mkid	是	int	模块id
     */
    public static String VILLAGE_SREACHLUNTAN = "action=dosearchluntan&";

    /**
     * 添加关注
     * 用户登录验证	是
     * id	是	int	小区id
     */
    public static String VILLAGE_ADDCOMMENT_CATE_USER = "action=addcomment_cate_user&";

    /**
     * 取消关注
     * 参数同上
     */
    public static String VILLAGE_DELCOMMENT_CATE_USER = "action=delcomment_cate_user&";

    /**
     * 小区搜索
     * keyword	是	string	小区搜索关键词
     */
    public static String VILLAGE_DOSEARCHLUNTAN = "action=dosearchluntan&";

    /**
     * 小区信息帖子列表
     * 用户登录验证	是
     * id	是	int	小区id
     */
    public static String VILLAGE_TOGETTHERSAY = "action=togethersay&";

    /**
     * 帖子详情
     * id 帖子id
     */
    public static String VILLAGE_COMMENT_TWUSER = "action=commentwxuser&";

    /**
     * 发表主题
     * 用户登录验证	是
     * media_ids	否	string	图片列表，从上传图片接口获取地址用 @ 拼接
     * title	是	string	标题
     * message	是	string	内容
     */
    public static String VILLAGE_SAVEUSERPMES = "action=saveuserpmes&";

    /**
     * 上传图片
     * html里面file name=’imgFile[]’
     */
    //public static String VILLAGE_LUNTANIMG = "action=luntanimg";

    /**
     * 用户点赞
     * 用户登陆验证	是
     * commentid	是	int	帖子id
     */
    public static String VILLAGE_SAVEUSERZANJIA = "action=saveuserzanjia&";

    /**
     * 上传图片stream
     * imgstream	是	string	64位编码的二进制流图片
     * ext	是	string	图片扩展名
     */
    public static String VILLAGE_LUNTANIMG = "action=luntanimg1&";

    /**
     * 用户取消点赞
     * 用户登陆验证	是
     * commentid	是	int	帖子id
     */
    public static String VILLAGE_SAVEUSERZANJIAN = "action=saveuserzanjian&";

    /**
     * 优惠券
     */
    public static String JUAN_LIST = "action=myjuan&";

    /**
     * 确认收货
     * 用户登录验证
     * doname	是	string	订单操作 delorder-删除订单 sureorder-确认收货
     * orderid	是	string	orderid
     */
    public static String ORDER_NEW_CONTROL = "action=newordercontrol&";

    /**
     * 获取评价订单列表
     * 用户登录验证
     * 订单id orderid
     */
    public static String ORDER_NEW_PINGLIST = "action=neworderpinglist&";

    /**
     * 订单评价
     * 用户登陆验证	是
     * orderdetid	是	int	order详情id
     * point	是	int	评分
     * pointcontent	fou	string	评价详情
     */
    public static String ORDER_NEW_PINGORDER = "action=newpingorder&";

    /**
     * 快递
     * shopid	是	int	店铺id
     */
    public static String MKKD = "action=mkkd&";

    /**
     * 商家端登录
     * uname	是	string	用户名
     * pwd	是	string	密码
     * showtype	否	string	模式普通店铺 shop
     * mDeviceID	是	string
     * userid	是	string
     */
    public static String APP_LOGIN = "action=applogin&";

    /**
     * 商家端店铺
     * uid	是	string	用户名
     * pwd	是	string	密码
     */
    public static String APP_SHOP = "action=appshop&";

    /**
     * 获取商家订单
     * uid	是	string	用户名
     * pwd	是	string	密码
     * searchday	否	string	查询日期
     * gettype	否	string	订单类型 wait waitsend is_send
     */
    public static String APP_ORDER = "action=apporder&";

    /**
     * 商家获取单个订单（订单详情）
     * uid	是	string	用户名
     * pwd	是	string	密码
     * orderid	是	订单id	订单id
     */
    public static String APP_ONE = "action=appone&";

    /**
     * 商家订单处理（）
     * uid	是	string	用户名
     * pwd	是	string	密码
     * orderid	是	string	订单id
     * dostring	是	string	操作 domake 确认制作 unmake 取消订单 send 发货 over 完成订单
     */
    public static String APP_ORDER_CONTROL = "action=ordercontrol&";

    /**
     * shopid 店铺id
     * &count=1.23 筛选条件 1.23为默认
     * &page=1 页码
     * &pagesize=20 每页个数
     */
    public static String APP_CHECKSTOCK = "action=checkstock&";

    /*首页推荐 店铺优选*/

    /**
     * 获取店铺推荐区
     * shopid	是	int	店铺id
     */
    public static String CATE_GETRECOMMED = "action=getrecommend&";

    /**
     * 获取分类下子分类
     * shopid	是	int	店铺id
     * cateid	是	int	分类id
     */

    public static String CATE_INFO = "action=cateinfo&";

    /**
     * 分类下商品筛选
     * shopid	是	int	店铺名
     * cateid	否	int	分类id 如果不传分类id默认查询上级分类下的所有商品
     * parentid	是	int	上级分类id
     * price	否	int	排序——价格 0或不传 不排序，1从低到高 2从高到低
     * sell	是	int	排序——销量 0或不传 不排序，1从低到高 2从高到低 两个只能选择一个可以都不传，如果两个都传，价格排序优先
     */
    public static String CATE_RECOMMEDGOODS = "action=recommendgoods&";

    /**
     * 送水上门
     * shopid	是	int	店铺id
     */
    public static String CATE_SSSM = "&action=sssm&";

    /*检查版本更新
     V 当前版本号
    * */
    public static String ANDROID_CHECKV = "action=androidcheckv&";

    /*签到
    * 参数  登录验证*/
    public static String SIGN = "action=sign&";

    /*查询月签到
    * 参数  登录验证
    月份：search=2017-09     参数只有年月 没有日*/
    public static String GETSIGNMONTH = "action=getsignmonth&";

    /*查询今天签到状态
    * 参数  登录验证*/
    public static String GETSIGN = "action=getsign&";

    /*特色服务模块*/
    public static String GETTESE = "action=gettese&";

    /*充值列表
    * 参数：登录验证*/
    public static String MEMCARD = "action=memcard&";

    /*余额明细
    * 参数：登录验证*/
    public static String PAY_LOG = "action=paylog&";

    /*积分明细
    * 参数：登录验证
    * page:页数
    * pageSize:每页个数
    * */
    public static String SCORELOG = "action=scorelog&";


    //完整接口 测试
    public static String path = "http://www.ybt9.com/index.php?ctrl=app&action=goodsindex&page=1&datatype=json&";


    /**
     * 余额支付
     * http://m6.waimairen.com/index.php?ctrl=app&source=1&action=appacoutpay&uid=9325&pwd=123456&type=order&cost=102.00&orderid=type=order&orderid=24121&datatype=json
     */

    /**
     * 获取支付宝 信息
     * http://www.ybt9.com/index.php?ctrl=app&source=1&action=apppaydata&uid=13&pwd=aaaaaa&datatype=json&type=acount&cost=43&orderid=type=order&orderid=22670
     */

}
