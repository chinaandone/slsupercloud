package com.clever.common;


import com.clever.common.util.Base64;
import com.google.gson.Gson;
import com.qiniu.util.StringUtils;
import junit.framework.TestCase;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wtw001 on 2015/8/10.
 */
public class UploadManageControllerTest extends TestCase {

//    @Test
//    public void testUpload() {
//        String uploadFile = "E:/视频/10.mp4";
//        File imgFile = new File(uploadFile);
//        try{
//            UploadManager uploadManager = new UploadManager();
////			String key = "video"+"10000"+System.currentTimeMillis();
////			video100001442229359339
////			video100001442229406080
////			video100001442229433918
////			video100001442229465318
//            UploadTokenView vo = QiniuStoreClient.getUploadToken(10000, "video100001442229359339", 2);
//            System.out.println("ssssssssssssuploadToken="+vo.getUploadToken());
//            System.out.println("ssssssssssssuploadKey="+vo.getUploadKey());
//            Response rp = uploadManager.put(imgFile, vo.getUploadKey(), vo.getUploadToken(), null, null, true);
//            System.out.println("ssssssssssssbodyString="+rp.bodyString());
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//    }


//    public static String initMacKey() throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA1");
//        SecretKey secretKey = keyGenerator.generateKey();
//        return Base64.encode(secretKey.getEncoded());
//    }
//
//    public static void main(String[] args) {
//        try {
//            System.out.println("ssssaaaaaaaaaaa=" + UploadManageControllerTest.initMacKey());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        final SecretKeySpec secretKey;
////        byte[] sk = StringUtils.utf8Bytes("xOSfZft5CB-hU53KupWQQ1OsHv434d2PyOKkGfAD");
//        String skey = "9zjl5LSTAj-URBd9ZP22OtX175CUuYyWj3bVnUAD";
//        byte[] sk = skey.getBytes(Charset.forName("UTF-8"));
////          byte[] sk = StringUtils.utf8Bytes("9zjl5LSTAj-URBd9ZP22OtX175CUuYyWj3bVnUAD");
//        SecretKeySpec secretKeySpec = new SecretKeySpec(sk, "HmacSHA1");
//        secretKey = secretKeySpec;
//        long deadline = System.currentTimeMillis() / 1000L + 3600;
//        Map x = new HashMap();
//        x.put("scope", "shanghaizhaoke");
////        x.put("deadline", Long.valueOf(deadline));
//        String s = (new Gson()).toJson(x);
//        String s1 = Base64.encode(s.getBytes(Charset.forName("UTF-8")));
//
//        try {
//            Mac mac = Mac.getInstance("HmacSHA1");
//            mac.init(secretKey);
//            String encodedSign = Base64.encode(mac.doFinal(s1.getBytes(Charset.forName("UTF-8")))) + ":" + s1;
//            System.out.println("sssssssssssssss11="+mac.doFinal(s1.getBytes(Charset.forName("UTF-8"))));
//            System.out.println("sssssssssssssss222="+mac.doFinal(s.getBytes(Charset.forName("UTF-8"))));
//            System.out.println("sssssssssssssss="+encodedSign);
//        } catch (GeneralSecurityException var3) {
//            var3.printStackTrace();
//            throw new IllegalArgumentException(var3);
//        }
//
//    }


}