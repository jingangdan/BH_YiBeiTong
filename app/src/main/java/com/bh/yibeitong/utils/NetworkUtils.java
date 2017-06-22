package com.bh.yibeitong.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by jingang on 2016/3/16.
 * 检查网络连接状态的工具类
 *
 * 用到两个类：
 * ConnectivityManager：
 *
 * 描述：这个类用来查询当前网络状态，通知网络状态变化。
 *  .监视网络连接（WIFI,GPRS,UMTS等）
 *  .当网络变化时发出broadcast.android.net.conn.CONNECTIVITY_CHANGE
 *  .当目前的网络丢失的时候尝试转移到另一个网络
 *  .提供API来允许AP查询当前可用网络的精确度
 *
 * NetworkInfo:
 * 描述目前网络的状态
 *
 * 判断网络是否是WiFi:
 * 在判断之前一定要进行的非空判断，如果没有任何网络连接info==null
 * info.getType()==ConnectivityManager.TYPE_WIFI
 *
 * 判断是否是手机网络：
 * info!=null && info.getType()==ConnectivityManager.TYPE_MOBILE
 *
 *
 * 手机网络进行详细的区分：
 * info.getSubtype()这里使用getSubtype(),
 * 不是getType(),getType()返回的是0，或者1，
 * info.getSubtype()取值列表如下：
 *
 * 	NETWORK_TYPE_CDMA  网络类型CDMA
 *  NETWORK_TYPE_EDGE	网络类型EDGE
 *  NETWORK_TYPE_EVDO_0	网络类型EVDO_0
 *  NETWORK_TYPE_EVDO_A	网络类型EVDO_A
 *  NETWORK_TYPE_GPRS	网络类型GPRS
 *  NETWORK_TYPE_HSDPA	网络类型HSDPA
 *  NETWORK_TYPE_HSPA	网络类型HSPA
 *  NETWORK_TYPE_HSUPA	网络类型HSUPA
 *  NETWORK_TYPE_UMTS	网络类型UMTS
 *
 *
 * 手机网络的情况下，使用NetworkInfo的getSubtype()方法去和
 * TelephoneManager的网络类型常量值去比较，判断是何种具体的网络
 * TelephoneManager的网络类型常量值如下：
 *
 * 	1、NETWORK_TYPE_1xRTT:		常量值：7	网络类型:1xRTT
 * 	2、NETWORK_TYPE_CDMA:		常量值：4	网络类型:CDMA 		(电信2g)
 * 	3、NETWORK_TYPE_EDGE:		常量值：2	网络类型:EDGE		(移动2g)
 * 	4、NETWORK_TYPE_EHRPD:		常量值：14	网络类型:EHRPD
 * 	5、NETWORK_TYPE_EVDO_0:		常量值：5	网络类型:EVDO_0		版本0(电信3g)
 * 	6、NETWORK_TYPE_EVDO_A:		常量值：6	网络类型:EVDO_A		版本A(电信3g)
 * 	7、NETWORK_TYPE_EVDO_B:		常量值：12	网络类型:EVDO_B		版本B(电信3g)
 * 	8、NETWORK_TYPE_GPRS:		常量值：1	网络类型:GPRS		(联通2g)
 * 	9、NETWORK_TYPE_HSDPA:		常量值：8	网络类型:HSDPA		(联通2g)
 * 	10、NETWORK_TYPE_HSPA:		常量值：10	网络类型:HSPA
 * 	11、NETWORK_TYPE_HSPAP:		常量值：15	网络类型:HSPAP
 * 	12、NETWORK_TYPE_HSUPA:		常量值：9	网络类型:HSUPA
 * 	13、NETWORK_TYPE_IDEN:		常量值：11	网络类型:IDEN
 * 	14、NETWORK_TYPE_LTE:		常量值：13	网络类型:LTE		(3到4的一个过渡，称为准4g)
 * 	15、NETWORK_TYPE_UMTS:		常量值：3	网络类型:UMTS		(联通3g)
 * 	16、NETWORK_TYPE_UNKNOWN:	常量值：0	网络类型:	未知
 *
 *
 * 	联通的3G为UMTS或HSDPA,
 * 	移动和联通的2G为GPRS或EDGE,
 * 	电信的2G为CDMA,电信的3G为EVDO
 *
 */
public class NetworkUtils {

    /**
     * 判断手机是否连接了网络
     * @param mContext
     * @return
     */
    public static boolean isNotWorkAvilable(Context mContext){
        ConnectivityManager mConnMgr = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean flag = false;
        if((mWifi!=null) && (mWifi.isAvailable()) || (mMobile.isAvailable())){
            if((mWifi.isConnected()) || (mMobile.isConnected())){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 判断手机连接网络的类型
     * @param mContext
     * @return
     */
    public static String getCurrentNetType(Context mContext){
        String type = "";
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        //关闭数据流量
        //cm.stopUsingNetworkFeature(ConnectivityManager.TYPE_MOBILE, "");

        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info==null){
            type="null";
        }else if(info.getType()==ConnectivityManager.TYPE_WIFI){
            type = "wifi连接";
        }else if(info.getType()==ConnectivityManager.TYPE_MOBILE){
            int subType = info.getSubtype();
            if(subType== TelephonyManager.NETWORK_TYPE_CDMA || subType==TelephonyManager.NETWORK_TYPE_GPRS
                    || subType==TelephonyManager.NETWORK_TYPE_EDGE){
                type="2g连接";
            }else if(subType==TelephonyManager.NETWORK_TYPE_UMTS || subType==TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType==TelephonyManager.NETWORK_TYPE_EVDO_A || subType==TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType==TelephonyManager.NETWORK_TYPE_EVDO_B){
                type="3g连接";
            }else if(subType==TelephonyManager.NETWORK_TYPE_LTE){
                type="4g连接";
            }
        }

        return type;
    }

    /**
     * 获取手机IP
     * @return
     */
    public static String getPhoneIP(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAdrr = intf.getInetAddresses(); enumIpAdrr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAdrr.nextElement();
                    if(!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address){
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取所连Wifi的Ip地址
     * @return
     * @throws SocketException
     */
    public static String GetIpAddress() throws SocketException {
        String ipaddress = "";
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface intf = netInterfaces.nextElement();
                if (intf.getName().toLowerCase().equals("eth0") || intf.getName().toLowerCase().equals("wlan0")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::")) {// ipV6的地址
                                ipaddress = ipaddress;
                            }
                        }
                    }
                }else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipaddress;
    }


    public String getAddress(Context mContext) {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        DhcpInfo info = wifiManager.getDhcpInfo();
        int ipAddress = wifiInfo.getIpAddress();
        int ii = info.ipAddress;

        return ipAddress + "    dhcp: " + ii;
    }

    private String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }


}
