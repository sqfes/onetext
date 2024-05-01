package com.example.demo.contrller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.demo.common.AuthAccess;
import com.example.demo.common.Result;
import com.example.demo.mapper.InboundquantityoutMaper;
import com.example.demo.pojo.AppUser;
import com.example.demo.pojo.Inboundquantityout;
import com.example.demo.pojo.Productinformation;
import com.example.demo.pojo.Reportdamage;
import com.example.demo.service.InboundquantityoutService;
import com.example.demo.service.UserService;
import com.example.demo.util.TokenUtils;
import com.github.pagehelper.PageInfo;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.demo.util.AiAllText.sendText;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/inboundquantityoues")
public class InboundquantityoutContrller {
    public static String generateRandomString() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(chars.length);
            sb.append(chars[index]);
        }

        return sb.toString();
    }
    @Autowired
    private UserService userService;
      @Autowired
    private InboundquantityoutService inboundquantityout;
    @Autowired
    private InboundquantityoutMaper inboundquantityoutMaper;
    @AuthAccess
    @PostMapping("/reportdamageinbath")
    //商品选择进货
    public Result reportdamageinbath(@RequestBody List<Inboundquantityout> data) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, ApiException {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
         List<String> useres = new ArrayList<>();
        List<String> sendtextuser = new ArrayList<>();
        Inboundquantityout inboundquantityout1 = new Inboundquantityout();
        for (Inboundquantityout dataes:data) {
        useres.add(dataes.getMerchant());
            Set<String> set = new HashSet<>(useres);
            useres.clear();
            useres.addAll(set);
        inboundquantityout1.setBarcode(dataes.getBarcode());
        inboundquantityout1.setProductname(dataes.getProductname());
        inboundquantityout1.setSpecifications(dataes.getSpecifications());
        inboundquantityout1.setUnit(dataes.getUnit());
        inboundquantityout1.setOrigin(dataes.getOrigin());
        inboundquantityout1.setRetailprice(dataes.getRetailprice());
        inboundquantityout1.setMerchant(dataes.getMerchant());
        inboundquantityout1.setInbounddate(formattedTime);
        inboundquantityout1.setStatus(1);
        inboundquantityout1.setPurchasequantity(dataes.getPurchasequantity());

        AppUser currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null) {
            String username = currentUser.getUsername();
            inboundquantityout1.setUsername(username);
        }
            inboundquantityoutMaper.save(inboundquantityout1);

    }
        for (String i:useres) {
            LambdaQueryWrapper<AppUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AppUser::getUsername,i);
            AppUser one = userService.getOne(wrapper);
            sendtextuser.add(one.getPhone());
        }
        sendText("请及时补货"+" \n\n"+"登录系统:http://110.40.182.197/login",sendtextuser);
        return Result.success("等待商家发货");
    }
    @AuthAccess
    @PostMapping("/inboundquantityouin")

    //商品进货
    public Result reportdamagein(@RequestBody Inboundquantityout data) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, ApiException {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        List<String> sendtextuser = new ArrayList<>();
        Inboundquantityout inboundquantityout1 = new Inboundquantityout();
        inboundquantityout1.setBarcode(data.getBarcode());
        inboundquantityout1.setProductname(data.getProductname());
        inboundquantityout1.setSpecifications(data.getSpecifications());
        inboundquantityout1.setUnit(data.getUnit());
        inboundquantityout1.setOrigin(data.getOrigin());
        inboundquantityout1.setRetailprice(data.getRetailprice());
        inboundquantityout1.setMerchant(data.getMerchant());
        inboundquantityout1.setInbounddate(formattedTime);
        inboundquantityout1.setStatus(1);
        inboundquantityout1.setPurchasequantity(data.getPurchasequantity());

        AppUser currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null) {
            String username = currentUser.getUsername();
            inboundquantityout1.setUsername(username);
        }
        LambdaQueryWrapper<AppUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AppUser::getUsername,data.getMerchant());
        AppUser one = userService.getOne(wrapper);
        sendtextuser.add(one.getPhone());
        inboundquantityout.save(inboundquantityout1);
        if(sendtextuser.size()>0){
        sendText("请及时补货"+" \n\n"+"登录系统:http://110.40.182.197/login",sendtextuser);
        }
        return Result.success("等待商家发货");
    }

    @GetMapping("/productinformation")
    //查询商品状态
    private PageInfo<Inboundquantityout> productinformation(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String barcode,
            @RequestParam String productname,
            @RequestParam String status

    ){

        String barcodes= barcode.replaceAll("^\\s+", "");
        String productnames=productname.replaceAll("^\\s+", "");
        String statuses=status.replaceAll("^\\s+", "");
        AppUser currentUser = TokenUtils.getCurrentUser();
        PageInfo<Inboundquantityout> userPageInfo = inboundquantityout.finduserPages(pageNum, pageSize,barcodes,productnames,statuses,currentUser);
        System.out.println(userPageInfo);
        return userPageInfo;
    }
    @DeleteMapping("/delete/{id}")
//删除订单和不通过的商品
    public Result delete(@PathVariable int id){
        LambdaQueryWrapper<Inboundquantityout> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Inboundquantityout::getId, id);
        boolean b = inboundquantityout.remove(lambdaQueryWrapper);
        if(!b){
            return Result.success("删除失败");
        }
        return Result.success("删除成功");
    }

    @AuthAccess
    @PostMapping("/savainboundquantityouin")
    //打回进货
    public Result savainboundquantityouin(@RequestBody Inboundquantityout data) {

        LambdaUpdateWrapper<Inboundquantityout> lambdaUpdateWrappera = new LambdaUpdateWrapper<>();
        lambdaUpdateWrappera
                .set(Inboundquantityout::getRemarks,data.getRemarks())
                .set(Inboundquantityout::getStatus,3)
                .set(Inboundquantityout::getPurchasequantity,data.getPurchasequantity())
                .eq(Inboundquantityout::getId,data.getId());
        Inboundquantityout newInfo = new Inboundquantityout();
        inboundquantityout.update(newInfo,lambdaUpdateWrappera);
        return Result.success("操作成功");
    }
    @AuthAccess
    @PostMapping("/savainboundquantityouina")
    //同意进货
    public Result savainboundquantityouina(@RequestBody Inboundquantityout data) {

        LambdaUpdateWrapper<Inboundquantityout> lambdaUpdateWrappera = new LambdaUpdateWrapper<>();
        lambdaUpdateWrappera
                .set(Inboundquantityout::getRemarks,data.getRemarks())
                .set(Inboundquantityout::getStatus,4)
                .set(Inboundquantityout::getPurchasequantity,data.getPurchasequantity())
                .eq(Inboundquantityout::getId,data.getId());
        Inboundquantityout newInfo = new Inboundquantityout();
        inboundquantityout.update(newInfo,lambdaUpdateWrappera);
        return Result.success("操作成功");
    }
    @AuthAccess
    @PostMapping("/savainboundquantityouinb")
    //发货
    public Result savainboundquantityouinb(@RequestBody Inboundquantityout data) {

        LambdaUpdateWrapper<Inboundquantityout> lambdaUpdateWrappera = new LambdaUpdateWrapper<>();
        lambdaUpdateWrappera
                .set(Inboundquantityout::getRemarks,data.getRemarks())
                .set(Inboundquantityout::getStatus,2)
                .set(Inboundquantityout::getPurchasequantity,data.getPurchasequantity())
                .eq(Inboundquantityout::getId,data.getId());
        Inboundquantityout newInfo = new Inboundquantityout();
        inboundquantityout.update(newInfo,lambdaUpdateWrappera);
        return Result.success("操作成功");
    }

}
