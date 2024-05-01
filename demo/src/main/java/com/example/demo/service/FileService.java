package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService extends IService<File> {

    String importData(MultipartFile file) throws IOException;

    File singleitem(Long barcode);
}
