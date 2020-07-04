package com.guli.oss.controller;


import com.guli.common.result.Result;
import com.guli.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss")
@CrossOrigin
public class FileController {

    @Autowired
    FileService fileService;

    /**
     * 上传文件
     *
     */
    @PostMapping("/file/upload")
    public Result upload(MultipartFile file) {

        //调用oss上传
        String url = fileService.upload(file);
        return Result.ok().data("url",url);

    }
}
