package com.example.demo;

import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import static com.example.demo.util.ai.getClient;

public class DateDome {
    private static final Logger log = Logger.getLogger(String.valueOf(DateDome.class));
    @Test
    public void Streamparallel() {
        LocalDate today = LocalDate.now();
        System.out.println("今天的日期是:" + today);
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前时间： " + now);

        // 将当前时间转换为指定时区的时间
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = now.atZone(zoneId);
        System.out.println("指定时区的时间： " + zonedDateTime);

        // 将指定时区的时间格式化为字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = zonedDateTime.format(formatter);
        System.out.println("格式化后的时间： " + formattedDateTime);

        // 获取当天的秒数
        int secondsOfToday = zonedDateTime.toLocalDate().atStartOfDay().getSecond();
        System.out.println("当天的秒数： " + secondsOfToday);
    }



    public static void sendText(String content) throws ApiException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {

        List<String> mobiles = new ArrayList<>();
        mobiles.add("18194852617");
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
//    public static void main(String[] args) throws ApiException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
//        sendText("马春燕154");
//    }


    @SneakyThrows
    public static void sendLink(){
        DingTalkClient client =  getClient();
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("link");
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl("https://www.dingtalk.com/");
        link.setPicUrl("https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png");
        link.setTitle("时代的火车向前开");
        link.setText("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
        request.setLink(link);
        OapiRobotSendResponse response = client.execute(request);

    }

    @SneakyThrows
    public static void sendMarkdown(){
        DingTalkClient client =  getClient();
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle("杭州天气");
        markdown.setText("#### 杭州天气 @156xxxx8827\n" +
                "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
                "> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n" +
                "> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \n");
        request.setMarkdown(markdown);
        OapiRobotSendResponse response = client.execute(request);
    }

    public static void main(String[] args) throws ApiException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        sendText("你好啊");
//        sendLink();
//        sendMarkdown();
    }

}
