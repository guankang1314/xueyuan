package com.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 上传文件接口
     * 返回url
     */
    String upload(MultipartFile file);
}
