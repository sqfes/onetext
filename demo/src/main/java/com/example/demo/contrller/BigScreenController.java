package com.example.demo.contrller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.*;
import com.example.demo.pojo.BigScreenes.*;
import com.example.demo.pojo.Inventory;
import com.example.demo.pojo.Productinformation;
import com.example.demo.pojo.Stocklog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/bigscreenes")
public class BigScreenController {
    @Autowired
    private ProductinformationMaper productinformationMaper;
    @Autowired
    private InboundMaper inboundMaper;
    @Autowired
    private StocklogMaper stocklogMaper;
    @Autowired
    private ReportdamageMaper reportdamageMaper;
    @Autowired
    private InboundquantityoutMaper inboundquantityoutMaper;

    public BigScreen getbigscreenres(String name,Integer sum){
        BigScreen productInfo = new BigScreen();
        if(sum==null){
            sum=0;
            List<Integer> objects1 = new ArrayList<>();
            objects1.add(sum);
            productInfo.setTitle(name);

            NumberInfo numberInfo = new NumberInfo();
            numberInfo.setNumber(objects1);
            numberInfo.setContent("{nt}");
            numberInfo.setTextAlign("center");

            Style style = new Style();
            style.setFill("#4d99fc");
            style.setFontWeight("bold");
            numberInfo.setStyle(style);

            productInfo.setNumber(numberInfo);
        }else {
            List<Integer> objects1 = new ArrayList<>();
            objects1.add(sum);

            productInfo.setTitle(name);

            NumberInfo numberInfo = new NumberInfo();
            numberInfo.setNumber(objects1);
            numberInfo.setContent("{nt}");
            numberInfo.setTextAlign("center");

            Style style = new Style();
            style.setFill("#4d99fc");
            style.setFontWeight("bold");
            numberInfo.setStyle(style);

            productInfo.setNumber(numberInfo);
        }
        return productInfo;
    }
    @GetMapping("/getbigscreen")
    public List<BigScreen> getbigscreen(){
        //商品信息数量
        QueryWrapper<Productinformation> queryWrapper1 = new QueryWrapper<>();
        Integer aa1 = Math.toIntExact(productinformationMaper.selectCount(queryWrapper1));
        List<BigScreen> objects = new ArrayList<>();
        BigScreen a1 = getbigscreenres("商品信息量", aa1);
        objects.add(a1);
      //库存量
      Integer aa2=  inboundMaper.eq();
        BigScreen a2 = getbigscreenres("库存量", aa2);
        objects.add(a2);
    //入库量
        Integer aa3 = stocklogMaper.eq();
        BigScreen a3 = getbigscreenres("今日入库量", aa3);
        objects.add(a3);
        //出库量
        Integer aa4 = stocklogMaper.eq1();
        BigScreen a4 = getbigscreenres("今日出库量", aa4);
        objects.add(a4);
//过期商品数量

        Integer aa5 = inboundMaper.eq1();
        BigScreen a5 = getbigscreenres("过期商品量", aa5);
        objects.add(a5);
        //商品即将过期数量
        Integer aa6 = inboundMaper.eq2();
        BigScreen a6 = getbigscreenres("商品即将过期量", aa6);
        objects.add(a6);
        return objects;
    }
    @GetMapping("/outboundranking")
    public List<OutboundRanking> outboundranking(){
        List<OutboundRanking> su= stocklogMaper.eqoutboundranking();
        return su;
    }
    @GetMapping("/stocklog")
    public  List< List<Object>> stocklog(){
      //  List<Stocklog> su= stocklogMaper.eqoutboundranking();
        QueryWrapper<Stocklog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("outinbounddate", "productname", "outinboundquantity", "outin");
        List<Stocklog> stocklogs = stocklogMaper.selectList(queryWrapper);
        List< List<Object>> objects = new ArrayList<>();
        for (Stocklog i : stocklogs) {
            List<Object> dataList = new ArrayList<>();
            dataList.add(i.getOutinbounddate());
            dataList.add(i.getProductname());
            dataList.add(i.getOutinboundquantity());
            dataList.add(i.getOutin());
            objects.add(dataList);
        }
        return objects;
    }

    @GetMapping("/lossreported")
    public  Maindata lossreported(){
   //报货
        int eq1 = inboundquantityoutMaper.eq1();//运输
        int eq2 =inboundquantityoutMaper.eq2();//完成
        int sumpurchasequantity=eq2+eq1; //总数
   //进损
     //过期
     int eq4 = reportdamageMaper.eq1(); //变质

        int eq5 = reportdamageMaper.eq();  //其它

        int eq6 = reportdamageMaper.eq2(); //过期
        int suminboundquantity=eq4+eq5+eq6; //总数
        Maindata maindata = new Maindata();
        maindata.setEq1(eq1);
        maindata.setEq2(eq2);
        maindata.setSumpurchasequantity(sumpurchasequantity);
        maindata.setEq4(eq4);
        maindata.setEq5(eq5);
        maindata.setEq6(eq6);
        maindata.setSuminboundquantity(suminboundquantity);
        return maindata;
    }

}
