package com.example.demo.contrller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.pojo.AppUser;
import com.example.demo.pojo.Inventory;
import com.example.demo.pojo.SumInventory;
import com.example.demo.service.InboundService;
import com.example.demo.service.UserService;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.example.demo.util.AiAllText.sendMarkDown;
import static com.example.demo.util.AiAllText.sendText;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/timer")
public class timerController {
    @Autowired
    private UserService userService;
    @Autowired
    private InboundService inboundService;
    public static List<String> removeDuplicates(List<String> list) {
        HashSet<String> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }

    // 明天上午10点执行一次
    //    @Scheduled(cron = "0 0 10 * * ?")
    // 每隔5秒执行一次
//    @Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 0 10 * * ?")
    public void task1() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, ApiException {


        List<String> sendtextuser = new ArrayList<>();
        List<String> sendtextusera = new ArrayList<>();
        //sendtextuser.add("18194852617");
        LambdaQueryWrapper<AppUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AppUser::getRole,"1")
                .or()
                .eq(AppUser::getRole,"3");
        List<AppUser> list = userService.list(wrapper);
        for (AppUser i:list) {
            sendtextuser.add(i.getPhone());
        }


        LambdaQueryWrapper<AppUser> wrappera = new LambdaQueryWrapper<>();
        wrappera.eq(AppUser::getRole,"1");
        List<AppUser> lista = userService.list(wrappera);
        for (AppUser i:lista) {
            sendtextusera.add(i.getPhone());
        }

        List<Inventory> rangeestime=  inboundService.rangeestime();
        List<String> objects = new ArrayList<>();
        for (Inventory i:rangeestime) {
            objects.add(i.getBarcode()+":"+i.getProductname());
        }
        List<String> resulta = removeDuplicates(objects);
        String result = String.join(",", resulta);
        if(objects.size()>=1){
            sendText("过期商品"+ "\n\n"+result+ "\n\n"+"请及时处理,登录系统:http://110.40.182.197/login",sendtextuser);
        }

       // sendMarkDown("过期商品","#### 过期商品"+ "\n"+result+" \n\n"+"请及时处理,登录系统:http://localhost:7000/login",sendtextuser);
        List<SumInventory> inventoryb=inboundService.inventoryb();
        List<String> objectsa = new ArrayList<>();
        for (SumInventory i:inventoryb) {
            if(i.getSuminboundquantity()<1000){
                objectsa.add(i.getBarcode()+":"+i.getProductname());
            }
        }
        List<String> resultc = removeDuplicates(objectsa);
        String resultd = String.join(",", resultc);
        if(objectsa.size()>=1){
            sendText("不足1000的商品"+ "\n\n"+resultd+" \n\n"+"请及时处理,登录系统:http://110.40.182.197/login",sendtextusera);
        }
        //sendMarkDown("过期商品","#### 过期商品"+ "\n"+result+" \n\n"+"请及时处理,登录系统:http://localhost:7000/login",sendtextuser);
        System.out.println(inventoryb);
    }
//    sendMarkDown("杭州天气","9度，西北风1级，空气良89，相对温度73%",sendtextuser);
    }

