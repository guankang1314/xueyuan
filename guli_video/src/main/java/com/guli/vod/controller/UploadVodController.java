package com.guli.vod.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.guli.common.result.Result;
import com.guli.vod.service.VodService;
import com.guli.vod.util.AliyunVodSDKUtil;
import com.guli.vod.util.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vod")
@CrossOrigin
public class UploadVodController {


    @Autowired
    private VodService vodService;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {

        String videoSourceId = vodService.upload(file);

        return Result.ok().data("videoSourceId",videoSourceId);
    }


    /**
     * 根据视频Id删除
     * @param videoSourceId
     * @return
     */
    @DeleteMapping("{videoSourceId}")
    public Result removeVideo(@PathVariable("videoSourceId") String videoSourceId) throws ClientException {

        Boolean b = vodService.deleteVoById(videoSourceId);

        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }


    /**
     * 根据多个视频id删除视频
     * @param videoIdList
     * @return
     * @throws ClientException
     */
    @DeleteMapping("/removelist")
    public Result getRemoveList(@RequestParam("videoIdList") List videoIdList) throws ClientException {

        Boolean b = vodService.getRemoveListByIds(videoIdList);

        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    /**
     * 根据视频id获取视频的播放凭证
     */
    @GetMapping("getPlayAuthFront/{vid}")
    public Result getPlayAuthFront(@PathVariable("vid") String vid) {

        try {
            //初始化对象
            DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //创建获取播放凭证的request的对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(vid);

            //调用方法返回response
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            String playAuth = response.getPlayAuth();

            return Result.ok().data("playAuth",playAuth);

        } catch (ClientException e) {
            e.printStackTrace();
            return null;
        }

    }



}
