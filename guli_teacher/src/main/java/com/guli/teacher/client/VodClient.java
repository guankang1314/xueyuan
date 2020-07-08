package com.guli.teacher.client;


import com.guli.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("GULI-VOD")
public interface VodClient {


    @DeleteMapping("vod/{videoSourceId}")
    public Result removeVideo(@PathVariable("videoSourceId") String videoSourceId);

    @DeleteMapping("vod/removelist")
    public Result getRemoveList(@RequestParam("videoIdList") List videoIdList);
}
