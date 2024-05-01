package com.example.demo.contrller;

import cn.hutool.core.io.FileUtil;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.common.AuthAccess;
import com.example.demo.common.Result;


import com.example.demo.pojo.*;
import com.example.demo.service.InboundService;
import com.example.demo.service.InboundquantityoutService;
import com.example.demo.service.ProductinformationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private ProductinformationService productinformationService;
    @Autowired
    private InboundService inboundService;
    @Autowired
    private InboundquantityoutService inboundquantityout;
    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH =  System.getProperty("user.dir") + File.separator + "files";  // D:\B站\小白做毕设2024\代码\小白做毕设2024\files

    @PostMapping("/upload")
    //上传和更新头像
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 文件的原始名称
        // aaa.png
        String mainName = FileUtil.mainName(originalFilename);  // aaa
        String extName = FileUtil.extName(originalFilename);// png
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);  // 如果当前文件的父级目录不存在，就创建
        }
        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {  // 如果当前上传的文件已经存在了，那么这个时候我就要重名一个文件名称
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        return Result.success(url);  //返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的
    }
    @AuthAccess
    @GetMapping("/download/{fileName}")
    //头像预览
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 附件下载
        response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 预览
        String filePath = ROOT_PATH  + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }
    @AuthAccess
    @PostMapping("/productinformationimport")
    //导入商品信息.xlsx
    public Result importData(@RequestParam("file") MultipartFile file) throws IOException {
        String s = productinformationService.importData(file);
        return Result.success(s);
    }

   @GetMapping("/excel/template")
   //下载商品信息模版
   public ResponseEntity<Resource> downloadTemplate(HttpServletResponse response) throws IOException {
       // 加载模板文件作为资源
       Resource resource = new ClassPathResource("template/商品信息模版.xlsx");

       // 设置响应头信息
       String fileName = URLEncoder.encode("商品信息模版", "utf-8");
       response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
       response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; ;filename=" + fileName + ".xlsx");

       // 返回资源作为响应实体
       return ResponseEntity.ok().body(resource);
   }


@RequestMapping(value = "/excel/export")
//导出商品信息.xlsx
public void bulkOutput(HttpServletResponse response) throws IOException {
    List<Productinformation> productinformationList = productinformationService.list();

    response.setCharacterEncoding("utf-8");
    response.setContentType("application/vnd.ms-excel");
    String fileName = URLEncoder.encode("商品信息", "utf-8");
    response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
    ServletOutputStream outputStream = response.getOutputStream();
    InputStream resourceAsStream = ApplicationListener.class.getClassLoader().getResourceAsStream("template/商品信息模版.xlsx");
   // InputStream resourceAsStream = Application.class.getClassLoader().getResourceAsStream();
    EasyExcel.write(outputStream)
            .withTemplate(resourceAsStream)
            .sheet()
            .doWrite(productinformationList);
}

    @RequestMapping(value = "/excel/exporta")
//导出库存.xlsx
    public void sumInventory(HttpServletResponse response) throws IOException {
        List<SumInventory> inventoryb=inboundService.inventoryb();

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel");
        String fileName = URLEncoder.encode("商品库存", "utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        InputStream resourceAsStream = ApplicationListener.class.getClassLoader().getResourceAsStream("template/商品库存模版.xlsx");
        EasyExcel.write(outputStream)
                .withTemplate(resourceAsStream)
                .sheet()
                .doWrite(inventoryb);
    }
    @RequestMapping(value = "/excel/inboundquantityout")
//导出库存.xlsx
    public void inboundquantityout(HttpServletResponse response) throws IOException {
      //  List<Inboundquantityout> inventoryb=inboundService.inventoryb();
        LambdaQueryWrapper<Inboundquantityout> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .select(Inboundquantityout::getId,Inboundquantityout::getBarcode,Inboundquantityout::getProductname,Inboundquantityout::getSpecifications,Inboundquantityout::getUnit,Inboundquantityout::getOrigin,Inboundquantityout::getRetailprice,Inboundquantityout::getMerchant,Inboundquantityout::getUsername,Inboundquantityout::getInbounddate,Inboundquantityout::getPurchasequantity)
                .eq(Inboundquantityout::getStatus, 4);
        List<Inboundquantityoutcopm> objects = new ArrayList<>();
        List<Inboundquantityout> list = inboundquantityout.list(wrapper);
        for (Inboundquantityout i:list) {
            Inboundquantityoutcopm inboundquantityoutcopm = new Inboundquantityoutcopm();
            inboundquantityoutcopm.setId(i.getId());
            inboundquantityoutcopm.setBarcode(i.getBarcode());
            inboundquantityoutcopm.setProductname(i.getProductname());
            inboundquantityoutcopm.setSpecifications(i.getSpecifications());
            inboundquantityoutcopm.setUnit(i.getUnit());
            inboundquantityoutcopm.setOrigin(i.getOrigin());
            inboundquantityoutcopm.setRetailprice(i.getRetailprice());
            inboundquantityoutcopm.setMerchant(i.getMerchant());
            inboundquantityoutcopm.setUsername(i.getUsername());
            inboundquantityoutcopm.setInbounddate(i.getInbounddate());
            inboundquantityoutcopm.setPurchasequantity(i.getPurchasequantity());
            objects.add(inboundquantityoutcopm);
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel");
        String fileName = URLEncoder.encode("商品进货", "utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        InputStream resourceAsStream = ApplicationListener.class.getClassLoader().getResourceAsStream("template/商品进货模版.xlsx");
        EasyExcel.write(outputStream)
                .withTemplate(resourceAsStream)
                .sheet()
                .doWrite(objects);
    }
}

