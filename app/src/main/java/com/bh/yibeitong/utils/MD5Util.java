package com.bh.yibeitong.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * MD5 encryption
 * @author jingang
 *		MD5加密
		String srcString = "yanzi1225627";
		System.out.println("MD5加密后 = " + MD5Util.getMD5String(srcString));
 */
public class MD5Util {  
    public static String getMD5String(String sSecret) {
    	try {
    	MessageDigest bmd5 = MessageDigest.getInstance("MD5");
    	bmd5.update(sSecret.getBytes());
    	int i;
    	StringBuffer buf = new StringBuffer();
    	byte[] b = bmd5.digest();
    	for (int offset = 0; offset < b.length; offset++) {
    	i = b[offset];
    	if (i < 0)
    	i += 256;
    	if (i < 16)
    	buf.append("0");
    	buf.append(Integer.toHexString(i));
    	}
    	return buf.toString();
    	} catch (NoSuchAlgorithmException e) {
    	e.printStackTrace();
    	}
    	return "";
    	}
    
    /**
     * 
     * Unicode解码
     */
    static final Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");
    public static String getUnicode(String s) {
        Matcher m = reUnicode.matcher(s);
        StringBuffer sb = new StringBuffer(s.length());
        while (m.find()) {
            m.appendReplacement(sb,
                    Character.toString((char) Integer.parseInt(m.group(1), 16)));
        }
        m.appendTail(sb);
        return sb.toString();
    }
    
    
    
    
}
