package com.example.demo.contrller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.pojo.Inventory;
import com.example.demo.pojo.Productinformation;
import com.example.demo.pojo.Stocklog;
import com.example.demo.service.StocklogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/stockloges")
public class StocklogController{
    @Autowired
    private StocklogService stocklogService;
    @GetMapping("/getstocklog")
    //查询商品信息
    private PageInfo<Stocklog> productinformation(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String productname,
            @RequestParam String outinbounddate,
            @RequestParam String outin

    ){
        PageInfo<Stocklog> su=null;
        if(outinbounddate.contains("T")){
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(outinbounddate);
            ZonedDateTime targetZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String mysqlDateTime = targetZonedDateTime.format(formatter);
            su= stocklogService.getstocklogser(pageNum,pageSize,productname,mysqlDateTime,outin);
        }else {
              su= stocklogService.getstocklogser(pageNum,pageSize,productname,outinbounddate,outin);
        }

        return su;
    }
    @DeleteMapping("/delete/{id}")
    //删除商品信息
    public Result delete(@PathVariable int id){

        LambdaQueryWrapper<Stocklog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Stocklog::getId, id);
        boolean b = stocklogService.remove(lambdaQueryWrapper);
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
            LambdaQueryWrapper<Stocklog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Stocklog::getId, id);
            boolean b = stocklogService.remove(lambdaQueryWrapper);
            if(!b){
                su="删除失败";
            }
        }
        return Result.success(su);
    }
}
