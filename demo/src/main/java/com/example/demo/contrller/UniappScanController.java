package com.example.demo.contrller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.demo.common.AuthAccess;
import com.example.demo.common.Result;
import com.example.demo.mapper.InboundMaper;
import com.example.demo.pojo.*;
import com.example.demo.service.*;
import com.example.demo.util.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.util.AiAllText.sendText;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

@RequestMapping("/scan")
public class UniappScanController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private InboundService inboundService;
    @Autowired
    private StocklogService stocklogService;
    @Autowired
    private InboundMaper inboundMaper;
    @Autowired
    private ReportdamageService reportdamageService;
    @Autowired
    private ProductinformationService productinformationService;
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @AuthAccess

    @PostMapping("/result")
    public Result inbound(@RequestBody Inventory data){
        //查询当个商品信息
        File singleitem = fileService.singleitem(data.getBarcode());
        if(singleitem==null){
            return Result.error("商品不存在");
        }
        return Result.success(singleitem);

    }
    @AuthAccess
    @PostMapping("/scanresult")
    //扫码入库
    public Result scanresult(@RequestBody Inventory data){
        System.out.println(data);

        File singleitem = fileService.singleitem(data.getBarcode());
        if(singleitem!=null){
            Inventory inventory = new Inventory();
            Stocklog stocklog = new Stocklog();
            inventory.setBarcode(singleitem.getBarcode());
            inventory.setProductname(singleitem.getProductname());
            inventory.setSpecifications(singleitem.getSpecifications());
            inventory.setUnit(singleitem.getUnit());
            inventory.setOrigin(singleitem.getOrigin());
            inventory.setRetailprice(singleitem.getRetailprice());
            inventory.setUsername(data.getUsername());
            inventory.setInbounddate(data.getInbounddate());
            inventory.setInboundquantity(data.getInboundquantity());
            inventory.setRangees(data.getRangees());
            inboundService.save(inventory);

            stocklog.setBarcode(singleitem.getBarcode());
            stocklog.setProductname(singleitem.getProductname());
            stocklog.setSpecifications(singleitem.getSpecifications());
            stocklog.setUnit(singleitem.getUnit());
            stocklog.setOrigin(singleitem.getOrigin());
            stocklog.setRetailprice(singleitem.getRetailprice());
            stocklog.setUsername(data.getUsername());
            stocklog.setOutinbounddate(data.getInbounddate());
            stocklog.setOutinboundquantity(data.getInboundquantity());
            stocklog.setOutin("入库");
            stocklogService.save(stocklog);
            return Result.success("入库成功");

        }
        return Result.error("入库失败");
    }
@AuthAccess
@GetMapping("/scanresultout")
//扫描查询商品出库
    private PageInfo<Inventory> scanresultout(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam Long barcode
    ){
        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
    PageHelper.startPage(pageNum, pageSize);
        queryWrapper
                .eq(Inventory::getBarcode, barcode);
        List<Inventory> list = inboundService.list(queryWrapper);


    PageInfo<Inventory> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    @AuthAccess
    @PostMapping("/scanresultouta")
    //扫码出库
    public Result scanresultouta(@RequestBody List<Inventory> data) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
     //   inboundService.updatea(data);
        System.out.println(data);
        for (Inventory i:data) {
            Long id = i.getId();
            Integer inboundquantity = i.getInboundquantity();
            Integer inboundquantityout = i.getInboundquantityout();
            int sum=inboundquantity-inboundquantityout;
            System.out.println(id);
           if(sum>=0){
               LambdaUpdateWrapper<Inventory> lambdaUpdateWrappera = new LambdaUpdateWrapper<>();
               lambdaUpdateWrappera
                       .set(Inventory::getInboundquantity,sum)
                       .eq(Inventory::getId,i.getId());
               Inventory newInfo = new Inventory();
               inboundService.update(newInfo,lambdaUpdateWrappera);


               if(inboundquantityout>0){

                   Stocklog stocklog = new Stocklog();
                   stocklog.setBarcode(i.getBarcode());
                   stocklog.setProductname(i.getProductname());
                   stocklog.setSpecifications(i.getSpecifications());
                   stocklog.setUnit(i.getUnit());
                   stocklog.setOrigin(i.getOrigin());
                   stocklog.setRetailprice(i.getRetailprice());
                   stocklog.setUsername(i.getUsername());
                   stocklog.setOutinbounddate(formattedTime);
                   stocklog.setOutinboundquantity(inboundquantityout);
                   stocklog.setOutin("出库");
                   stocklogService.save(stocklog);
               }
              ;
           }else {
                return Result.error("出库失败");
           }
        }

        LambdaQueryWrapper<Inventory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Inventory::getInboundquantity,0);
        inboundMaper.delete(wrapper);
        return Result.success("出库成功");
    }
    @AuthAccess
    @PostMapping("/reportdamagein")
    //商品报损
    public Result reportdamagein(@RequestBody Reportdamage data) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, ApiException {
        Reportdamage reportdamage = new Reportdamage();
        LambdaQueryWrapper<Productinformation> lambdaWrapper = new LambdaQueryWrapper<>();
        lambdaWrapper
                .select(Productinformation::getMerchant)
                .eq(Productinformation::getBarcode,data.getBarcode());
        Productinformation list = productinformationService.getOne(lambdaWrapper);
        LambdaQueryWrapper<AppUser> lambdaWrappera = new LambdaQueryWrapper<>();
        lambdaWrappera
                .select(AppUser::getPhone)
                .eq(AppUser::getUsername,list.getMerchant());
        AppUser one = userService.getOne(lambdaWrappera);
        List<String> bs = new ArrayList<>();
          bs.add(one.getPhone());
        //提醒商家审核
        sendText("您有新的报损订单请及时处理"+" \n\n"+"登录系统:http://110.40.182.197/login",bs);
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        reportdamage.setId(data.getId());
        reportdamage.setBarcode(data.getBarcode());
        reportdamage.setProductname(data.getProductname());
        reportdamage.setSpecifications(data.getSpecifications());
        reportdamage.setUnit(data.getUnit());
        reportdamage.setOrigin(data.getOrigin());
        reportdamage.setRetailprice(data.getRetailprice());
        reportdamage.setInbounddate(data.getInbounddate());
        reportdamage.setInboundquantity(data.getInboundquantityout());
        reportdamage.setRangees(data.getRangees());
        reportdamage.setReason(data.getReason());
        reportdamage.setRemarks(data.getRemarks());
        reportdamage.setDamageclaimant(data.getDamageclaimant());
        reportdamage.setStatus(1);
        reportdamage.setMerchant(list.getMerchant());
        reportdamage.setReportdamagedata(formattedTime);
        reportdamageService.save(reportdamage);
        return Result.success("供应商确定中...");
    }

    @GetMapping("/damagedcondition")
    //报损情况
    private PageInfo<Reportdamage> damagedcondition(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String barcode,
            @RequestParam String productname

    ) throws ParseException {
        AppUser currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        String username = currentUser.getUsername();
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Reportdamage> pageInfo = new PageInfo<>();
        if(role.equals("2")){
            LambdaQueryWrapper<Reportdamage> lambdaWrapper = new LambdaQueryWrapper<>();
            lambdaWrapper.eq(Reportdamage::getMerchant,username);
            if (barcode != null && !barcode.isEmpty()) {
                lambdaWrapper.like(Reportdamage::getBarcode, barcode);
            }

            if (productname != null && !productname.isEmpty()) {
                if (barcode != null && !barcode.isEmpty()) {
                    lambdaWrapper.or(); // 只有当barcode不为空时，才需要or连接
                }
                lambdaWrapper.like(Reportdamage::getProductname, productname);
            }
            List<Reportdamage> list = reportdamageService.list(lambdaWrapper);

           pageInfo = new PageInfo<>(list);
        }else if(role.equals("3")){
            LambdaQueryWrapper<Reportdamage> lambdaWrapper = new LambdaQueryWrapper<>();
            lambdaWrapper.eq(Reportdamage::getDamageclaimant,username);
            if (barcode != null && !barcode.isEmpty()) {
                lambdaWrapper.like(Reportdamage::getBarcode, barcode);
            }

            if (productname != null && !productname.isEmpty()) {
                if (barcode != null && !barcode.isEmpty()) {
                    lambdaWrapper.or(); // 只有当barcode不为空时，才需要or连接
                }
                lambdaWrapper.like(Reportdamage::getProductname, productname);
            }
            List<Reportdamage> list = reportdamageService.list(lambdaWrapper);

            pageInfo = new PageInfo<>(list);
        }else {
            LambdaQueryWrapper<Reportdamage> lambdaWrapper = new LambdaQueryWrapper<>();

            if (barcode != null && !barcode.isEmpty()) {
                lambdaWrapper.like(Reportdamage::getBarcode, barcode);
            }

            if (productname != null && !productname.isEmpty()) {
                if (barcode != null && !barcode.isEmpty()) {
                    lambdaWrapper.or(); // 只有当barcode不为空时，才需要or连接
                }
                lambdaWrapper.like(Reportdamage::getProductname, productname);
            }
            List<Reportdamage> list = reportdamageService.list(lambdaWrapper);

            pageInfo = new PageInfo<>(list);
        }




        return pageInfo;
    }

    @AuthAccess
    @PostMapping("/noreportdamagein")
    //商品报损不同意
    public Result noreportdamagein(@RequestBody Reportdamage data) {
        System.out.println(data);
        LambdaUpdateWrapper<Reportdamage> lambdaUpdateWrappera = new LambdaUpdateWrapper<>();
        lambdaUpdateWrappera
                .set(Reportdamage::getRemarks,data.getRemarks())
                .set(Reportdamage::getStatus,data.getStatus())
                .eq(Reportdamage::getId,data.getId());
        Reportdamage newInfo = new Reportdamage();
        reportdamageService.update(newInfo,lambdaUpdateWrappera);
        return Result.success("操作成功");
    }
    @PostMapping("/delete")
//删除商品信息
    public Result delete(@RequestBody Reportdamage data){
        System.out.println(data);
        LambdaQueryWrapper<Reportdamage> lambdaWrapper = new LambdaQueryWrapper<>();
        lambdaWrapper.eq(Reportdamage::getId,data.getId());
        reportdamageService.remove(lambdaWrapper);
        return Result.success("报损删除成功");
    }
    @AuthAccess
    @PostMapping("/yesreportdamagein")
    //商品报损不同意
    public Result yesreportdamagein(@RequestBody Reportdamage data) {
        System.out.println(data);
        LambdaUpdateWrapper<Reportdamage> lambdaUpdateWrappera = new LambdaUpdateWrapper<>();
        lambdaUpdateWrappera
                .set(Reportdamage::getRemarks,data.getRemarks())
                .set(Reportdamage::getStatus,data.getStatus())
                .eq(Reportdamage::getId,data.getId());
        Reportdamage newInfo = new Reportdamage();
        reportdamageService.update(newInfo,lambdaUpdateWrappera);


        //修改库存
        Integer inboundquantitya = data.getInboundquantity();
        LambdaQueryWrapper<Inventory> lambdaWrapper = new LambdaQueryWrapper<>();
        lambdaWrapper.eq(Inventory::getId,data.getId());
        Inventory one = inboundService.getOne(lambdaWrapper);
        Integer inboundquantity = one.getInboundquantity();
       int suminboundquantity = inboundquantity - inboundquantitya;
      if(suminboundquantity>=0){
          LambdaUpdateWrapper<Inventory> lambdaUpdate = new LambdaUpdateWrapper<>();
          lambdaUpdate
                  .set(Inventory::getInboundquantity,suminboundquantity)
                  .eq(Inventory::getId,data.getId());
          Inventory newInfoa = new Inventory();
          inboundService.update(newInfoa,lambdaUpdate);


          Stocklog stocklog = new Stocklog();
          stocklog.setBarcode(data.getBarcode());
          stocklog.setProductname(data.getProductname());
          stocklog.setSpecifications(data.getSpecifications());
          stocklog.setUnit(data.getUnit());
          stocklog.setOrigin(data.getOrigin());
          stocklog.setRetailprice(data.getRetailprice());
          stocklog.setUsername(data.getDamageclaimant());
          stocklog.setOutinbounddate(data.getReportdamagedata());
          stocklog.setOutinboundquantity(data.getInboundquantity());
          stocklog.setOutin("报损");
          stocklogService.save(stocklog);
      }
      else {
          return Result.error("库存不足,无法报损");
      }
        LambdaQueryWrapper<Inventory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Inventory::getInboundquantity,0);
        inboundMaper.delete(wrapper);

        return Result.success("报损成功");
    }
    }

