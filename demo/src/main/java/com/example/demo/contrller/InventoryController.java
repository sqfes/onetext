package com.example.demo.contrller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.mapper.InboundMaper;
import com.example.demo.mapper.ReportdamageMaper;
import com.example.demo.pojo.*;
import com.example.demo.service.InboundService;
import com.example.demo.service.ReportdamageService;
import com.example.demo.service.StocklogService;
import com.example.demo.util.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static com.example.demo.util.AiAllText.sendText;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/inventoryes")
public class InventoryController {
    @Autowired
    private InboundService inboundService;
    @Autowired
    private StocklogService stocklogService;
    @Autowired
    private InboundMaper inboundMaper;
    @Autowired
    private ReportdamageService reportdamageService;
    @Autowired
    private ReportdamageMaper reportdamageMaper;

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
        sendtextuser.add("18194852617");
        List<Inventory> rangeestime=  inboundService.rangeestime();
        List<String> objects = new ArrayList<>();
        for (Inventory i:rangeestime) {
            objects.add(i.getBarcode()+":"+i.getProductname());
        }
        List<String> resulta = removeDuplicates(objects);
        String result = String.join(",", resulta);
        sendText("过期商品<"+result+">请及时处理,登录系统:http://localhost:7000/login",sendtextuser);

        List<SumInventory> inventoryb=inboundService.inventoryb();
        List<String> objectsa = new ArrayList<>();
        for (SumInventory i:inventoryb) {
            if(i.getSuminboundquantity()<1000){
                objectsa.add(i.getBarcode()+":"+i.getProductname());
            }
        }
     List<String> resultc = removeDuplicates(objectsa);
        String resultd = String.join(",", resultc);
        sendText("库存少于1000商品<"+resultd+">请及时补货,登录系统:http://localhost:7000/login",sendtextuser);
        System.out.println(inventoryb);
    }
    @GetMapping("/inventory")
    //查询商品信息
    private PageInfo<Inventory> inventory(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,

            @RequestParam String barcode,
            @RequestParam String productname,
            @RequestParam String inbounddate,
            @RequestParam String rangees

    ) throws ParseException {

        String barcodes= barcode.replaceAll("^\\s+", "");
        String productnames=productname.replaceAll("^\\s+", "");
        String   inbounddatea="";
        if(!inbounddate.equals("NaN-NaN-NaN")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(inbounddate);
            inbounddatea = sdf.format(date);
        }
        String rangeesa="";
        String rangeesb="";
        if(!rangees.equals("NaN-NaN-NaN_NaN-NaN-NaN")){
            String[] parts = rangees.split("_");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(parts[0]);
           rangeesa = sdf.format(date1);

            Date date2 = sdf.parse(parts[1]);
            rangeesb = sdf.format(date2);
        }
   PageInfo<Inventory> userPageInfo = inboundService.finduserPagesa(pageNum, pageSize,barcodes,productnames,inbounddatea,rangeesa,rangeesb);
        return userPageInfo;
    }
    @GetMapping("/inventorya")
    //查询库存
    private PageInfo<SumInventory> inventorya(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String barcode,
            @RequestParam String productname

    ) throws ParseException {
        String barcodes= barcode.replaceAll("^\\s+", "");
        String productnames=productname.replaceAll("^\\s+", "");
        PageInfo<SumInventory> suminventory=inboundService.inventorya(pageNum, pageSize,barcodes,productnames);
        return suminventory;
    }
    @PutMapping("/update")
    //修改入库的商品信息
    public Result update(@RequestBody Inventory productinformation){
        String inbounddate = productinformation.getInbounddate();
        String rangees = productinformation.getRangees();
        if(inbounddate.contains("T")){
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(inbounddate);
        ZonedDateTime targetZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String mysqlDateTime = targetZonedDateTime.format(formatter);
        productinformation.setInbounddate(mysqlDateTime);
        }
        if(rangees.contains("T")){
                    ZonedDateTime zonedDateTimea = ZonedDateTime.parse(rangees);
        ZonedDateTime targetZonedDateTimea = zonedDateTimea.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formattera = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String mysqlDateTimea = targetZonedDateTimea.format(formattera);
        productinformation.setRangees(mysqlDateTimea);
        }

        LambdaQueryWrapper<Inventory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Inventory::getId, productinformation.getId());
        inboundService.update(productinformation,wrapper);
        return Result.success("修改成功");
    }
    @PostMapping("/add")
    //新增入库的商品信息
    public Result add(@RequestBody Inventory productinformation){
        String inbounddate = productinformation.getInbounddate();
        String rangees = productinformation.getRangees();
        if(inbounddate.contains("T")){
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(inbounddate);
            ZonedDateTime targetZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String mysqlDateTime = targetZonedDateTime.format(formatter);
            productinformation.setInbounddate(mysqlDateTime);
        }
        if(rangees.contains("T")){
            ZonedDateTime zonedDateTimea = ZonedDateTime.parse(rangees);
            ZonedDateTime targetZonedDateTimea = zonedDateTimea.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
            DateTimeFormatter formattera = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String mysqlDateTimea = targetZonedDateTimea.format(formattera);
            productinformation.setRangees(mysqlDateTimea);
        }

        inboundService.save(productinformation);
        Stocklog stocklog = new Stocklog();
        stocklog.setBarcode(productinformation.getBarcode());
        stocklog.setProductname(productinformation.getProductname());
        stocklog.setSpecifications(productinformation.getSpecifications());
        stocklog.setUnit(productinformation.getUnit());
        stocklog.setOrigin(productinformation.getOrigin());
        stocklog.setRetailprice(productinformation.getRetailprice());
        stocklog.setUsername(productinformation.getUsername());
        stocklog.setOutinbounddate(productinformation.getInbounddate());
        stocklog.setOutinboundquantity(productinformation.getInboundquantity());
        stocklog.setOutin("入库");
        stocklogService.save(stocklog);
        return Result.success("新增成功");
    }
    @DeleteMapping("/delete/{id}")
//删除商品信息
    public Result delete(@PathVariable int id){

        LambdaQueryWrapper<Inventory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Inventory::getId, id);
        boolean b = inboundService.remove(lambdaQueryWrapper);
        if(!b){
            return Result.success("删除失败");
        }
        return Result.success("删除成功");
    }

    @DeleteMapping("/delete/batch")
    //批量删除商品信息
    public Result batchDelete(@RequestBody List<Integer> ids) {
        String su="删除成功";
        for (Integer id:ids) {
            LambdaQueryWrapper<Inventory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Inventory::getId, id);
            boolean b = inboundService.remove(lambdaQueryWrapper);
            if(!b){
                su="删除失败";
            }
        }
        return Result.success(su);
    }



    @GetMapping("/reportdamage")
    //商品报损
    private PageInfo<Reportdamage> reportdamage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,

            @RequestParam String barcode,
            @RequestParam String productname,
            @RequestParam String inbounddate,
            @RequestParam String rangees

    ) throws ParseException {

        String barcodes= barcode.replaceAll("^\\s+", "");
        String productnames=productname.replaceAll("^\\s+", "");
        String   inbounddatea="";
        if(!inbounddate.equals("NaN-NaN-NaN")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(inbounddate);
            inbounddatea = sdf.format(date);
        }
        String rangeesa="";
        String rangeesb="";
        if(!rangees.equals("NaN-NaN-NaN_NaN-NaN-NaN")){
            String[] parts = rangees.split("_");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(parts[0]);
            rangeesa = sdf.format(date1);

            Date date2 = sdf.parse(parts[1]);
            rangeesb = sdf.format(date2);
        }

        List<Reportdamage> objects = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Inventory> lists = inboundMaper.eqProductinformationAll(barcodes,productnames,inbounddatea,rangeesa,rangeesb);
        for (Inventory i:lists) {
            Reportdamage eqnotreportdamage = reportdamageMaper.eqnotreportdamage(i.getId(),i.getBarcode(), i.getInbounddate(), i.getRangees());
            System.out.println(eqnotreportdamage);
            Reportdamage reportdamage = new Reportdamage();
            if(eqnotreportdamage!=null){
                reportdamage.setStatus(eqnotreportdamage.getStatus());
            }else {
                reportdamage.setStatus(0);
            }

            reportdamage.setId(i.getId());
            reportdamage.setBarcode(i.getBarcode());
            reportdamage.setProductname(i.getProductname());
            reportdamage.setOrigin(i.getOrigin());
            reportdamage.setSpecifications(i.getSpecifications());
            reportdamage.setUnit(i.getUnit());
            reportdamage.setRetailprice(i.getRetailprice());
            reportdamage.setUsername(i.getUsername());
            reportdamage.setInbounddate(i.getInbounddate());
            reportdamage.setRangees(i.getRangees());
            reportdamage.setInboundquantity(i.getInboundquantity());
            reportdamage.setRemarks("无");
            AppUser currentUser = TokenUtils.getCurrentUser();
            String username = null;
            if (currentUser != null) {
                username = currentUser.getUsername();
            }
            reportdamage.setDamageclaimant(username);
            objects.add(reportdamage);

        }
        PageInfo<Reportdamage> pageInfo = new PageInfo<>(objects);


        return pageInfo;
    }

}
