package com.tyd.Utils;

import org.junit.Test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Abbot
 * Date: 2017-09-03
 * Time: 17:36
 * Description:
 * MD5加密工具(32位大写)
 */
public class MD5Util
{
    public static String getMD5(String str)
    {
        String resultStr="";
        try
        {

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            resultStr = new BigInteger(1, md.digest()).toString(16).toUpperCase();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            try
            {
                throw new Exception("MD5加密出错。");
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
        }
        return resultStr;
    }

    @Test
    public void test() throws NoSuchAlgorithmException
    {
        String zky = getMD5("zky");
        System.out.println(zky + ":" + zky.length());
    }
}
