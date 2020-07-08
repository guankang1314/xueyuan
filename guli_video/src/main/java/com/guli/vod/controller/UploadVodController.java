package com.guli.vod.controller;


import com.aliyuncs.exceptions.ClientException;
import com.guli.common.result.Result;
import com.guli.vod.service.VodService;
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



}
