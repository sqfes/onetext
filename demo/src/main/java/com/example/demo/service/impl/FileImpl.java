package com.example.demo.service.impl;

import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.FileMaper;
import com.example.demo.pojo.File;
import com.example.demo.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileImpl extends ServiceImpl<FileMaper, File> implements FileService {
    @Override
    public String importData(MultipartFile file) throws IOException {
        List<List<Object>> read = ExcelUtil.getReader(file.getInputStream()).read(0, Integer.MAX_VALUE);
        ArrayList<File> objects = new ArrayList<>();
        read.remove(0);
        for (List<Object> obj : read) {
            File file1 = new File();
            Object name = obj.get(1);
            LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .select(File::getBarcode)
                    .eq(File::getBarcode, name);
            List<File> list = this.list(queryWrapper);
            System.out.println(list);
            if(list.size()==0){
                if (obj.get(6) instanceof Double) {
                    file1.setBarcode((Long) obj.get(1));
                    file1.setProductname((String) obj.get(2));
                    file1.setSpecifications((String) obj.get(3));
                    file1.setUnit((String) obj.get(4));
                    file1.setOrigin((String) obj.get(5));
                    file1.setRetailprice((Double) obj.get(6));
                    objects.add(file1);
                }else if (obj.get(6) instanceof Long) {
                    file1.setBarcode((Long) obj.get(1));
                    file1.setProductname((String) obj.get(2));
                    file1.setSpecifications((String) obj.get(3));
                    file1.setUnit((String) obj.get(4));
                    file1.setOrigin((String) obj.get(5));
                    file1.setRetailprice(Double.valueOf((Long) obj.get(6)));
                    objects.add(file1);
                }
            }


        }
        String s="导入成功";
        if(objects.size()>0){
            this.saveBatch(objects);

        }else {
            s="导入失败";
        }

        return s;
    }

    @Override
    public File singleitem(Long barcode) {
        LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(File::getBarcode, barcode);
        File one = this.getOne(queryWrapper);
        System.out.println(one);
        return one;
    }
}
