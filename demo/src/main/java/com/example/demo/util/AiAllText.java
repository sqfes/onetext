package com.example.demo.util;

import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import static com.example.demo.util.ai.getClient;

public class AiAllText {
    private static final Logger log = Logger.getLogger(String.valueOf(AiAllText.class));
    public static void sendText(String content,List<String> mobiles) throws ApiException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {

//        List<String> mobiles = new ArrayList<>();
//        mobiles.add("18194852617");
        DingTalkClient client =  getClient();
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        // isAtAll类型如果不为Boolean，请升级至最新SDK
        at.setIsAtAll(false);
        //at.setAtUserIds(Arrays.asList("","manager1123"));
        at.setAtMobiles(mobiles);
        request.setAt(at);
        OapiRobotSendResponse response = client.execute(request);
        log.info("success:{}, code:{}, errorCode:{}, errorMsg:{}");

    }
    public static void sendMarkDown(String title,String content,List<String> mobiles) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, ApiException {
        DingTalkClient client =  getClient();
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        request.setMsgtype("markdown");
        markdown.setTitle(title);
        markdown.setText(content);
        request.setMarkdown(markdown);

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        // isAtAll类型如果不为Boolean，请升级至最新SDK

        at.setIsAtAll(true);
        //at.setAtUserIds(Arrays.asList("","manager1123"));
        at.setAtMobiles(mobiles);
        request.setAt(at);
        OapiRobotSendResponse response = client.execute(request);
        System.out.printf("success:%s, code:%s, errorCode:%s, errorMsg:%s%n",response.isSuccess(),response.getCode(),response.getErrcode(),response.getErrmsg());

    }
}
