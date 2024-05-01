package com.example.demo.contrller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.example.demo.common.Result;
import com.example.demo.pojo.Productinformation;
import com.example.demo.service.ProductinformationService;
import com.example.demo.util.TokenUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/productinformationes")
public class ProductinformationController {
@Autowired
private ProductinformationService productinformationService;

    @GetMapping("/productinformation")
    //查询商品信息
    private PageInfo<Productinformation>  productinformation(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String barcode,
            @RequestParam String productname,
            @RequestParam String origin

    ){
        String barcodes= barcode.replaceAll("^\\s+", "");
        String productnames=productname.replaceAll("^\\s+", "");
        String origines=origin.replaceAll("^\\s+", "");
        PageInfo<Productinformation> userPageInfo = productinformationService.finduserPages(pageNum, pageSize,barcodes,productnames,origines);
        System.out.println(userPageInfo);
        return userPageInfo;
    }
    @DeleteMapping("/delete/{barcode}")
//删除商品信息
    public Result delete(@PathVariable Long barcode){

        LambdaQueryWrapper<Productinformation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Productinformation::getBarcode, barcode);
        boolean b = productinformationService.remove(lambdaQueryWrapper);
        if(!b){
            return Result.success("删除失败");
        }
        return Result.success("删除成功");
    }

    @DeleteMapping("/delete/batch")
    //批量删除商品信息
    public Result batchDelete(@RequestBody List<Long> ids) {
       String su="删除成功";
        for (Long barcode:ids) {
            LambdaQueryWrapper<Productinformation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Productinformation::getBarcode, barcode);
            boolean b = productinformationService.remove(lambdaQueryWrapper);
            if(!b){
              su="删除失败";
            }
        }
        return Result.success(su);
    }
    @PutMapping("/update")
    //修改商品信息
    public Result update(@RequestBody Productinformation productinformation){
        LambdaQueryWrapper<Productinformation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Productinformation::getId, productinformation.getId());
        productinformationService.update(productinformation,wrapper);
        return Result.success("修改成功");
    }
    @PostMapping("/add")
    //新增商品信息
    public Result add(@RequestBody Productinformation productinformation){
        productinformationService.save(productinformation);
        return Result.success("新增成功");
    }


}
