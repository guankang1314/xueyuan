package com.guli.oss.controller;


import com.guli.common.result.Result;
import com.guli.oss.service.FileService;
import com.guli.oss.util.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam(value = "host",required = false) String host) {

        if (!StringUtils.isEmpty(host)) {
            ConstantPropertiesUtil.FILE_HOST = host;
        }

        //调用oss上传
        String url = fileService.upload(file);
        return Result.ok().data("url",url);

    }
}
