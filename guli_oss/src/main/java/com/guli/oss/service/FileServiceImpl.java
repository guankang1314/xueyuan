package com.guli.oss.service;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.DateUtil;
import com.guli.oss.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {


    private static String TYPESTR[] = {".png",".jpg",".gif",".bmp",".jpeg"};

    @Override
    public String upload(MultipartFile file) {
        OSSClient ossClient = null;

        String url = null;

        try {
            // 创建OSSClient实例。
            ossClient = new OSSClient(ConstantPropertiesUtil.END_POINT,
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //判断文件格式
            boolean flag = false;

            for (String type : TYPESTR) {
                if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(),type)){
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                return "图片格式不正确";
            }

            //判断文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());

            if (image != null) {
                System.err.println(String.valueOf(image.getHeight()));
                System.err.println(String.valueOf(image.getWidth()));
            }else  {
                return "文件内容不正确";
            }


            //获取文件名
            String filename = file.getOriginalFilename();
            String ext = filename.substring(filename.lastIndexOf("."));
            String newName = UUID.randomUUID().toString().replaceAll("-","")+ext;
            String datePath = new DateTime().toString("yyyy/MM/dd");
            String urlPath = ConstantPropertiesUtil.FILE_HOST+"/"+datePath+"/"+newName;
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(ConstantPropertiesUtil.BUCKET_NAME, urlPath, inputStream);

            url = "https://"+ConstantPropertiesUtil.BUCKET_NAME+"."+ConstantPropertiesUtil.END_POINT+"/"+urlPath;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            // 关闭OSSClient。
            ossClient.shutdown();
        }


        return url;

    }
}
