package com.guli.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    /**
     * 上传文件返回视频的id
     * @param file
     * @return
     */
    String upload(MultipartFile file);

    /**
     * 根据id删除视频
     * @param videoSourceId
     * @return
     */
    Boolean deleteVoById(String videoSourceId) throws ClientException;


    /**
     * 根据多个视频id删除视频
     * @param videoIdList
     * @return
     */
    Boolean getRemoveListByIds(List videoIdList);
}
