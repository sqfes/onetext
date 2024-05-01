package com.example.demo.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ai {
    private static final String SECRET = "SECf8049a69d622b229c28496b00873080812430197e8cfbe26c253b0852d1a0b1f";
    private static final String URL = "https://oapi.dingtalk.com/robot/send?access_token=5bd63ec3b606192cb45fd24b3e5042326891ded3721eb52873a08f17fea6b3f7";
    /**
     * 组装签名url
     * @return url
     */
    public static String getURL()throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + SECRET;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        String signResult = "&timestamp=" + timestamp + "&sign=" + sign;
        // 得到拼接后的 URL
        return URL + signResult;
    }
    /**
     * 获取客户端
     * @return
     */
    public static DingTalkClient getClient()throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException{
        return new DefaultDingTalkClient(getURL());
    }

}
