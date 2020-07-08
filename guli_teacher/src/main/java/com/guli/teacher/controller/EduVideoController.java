package com.guli.teacher.controller;


import com.guli.common.result.Result;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/video")
@CrossOrigin
public class EduVideoController {


    @Autowired
    private EduVideoService videoService;



    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody EduVideo video) {

        boolean save = videoService.save(video);

        if (save) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }


    /**
     * 根据id查询video
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") String id) {

        EduVideo video = videoService.getById(id);
        return Result.ok().data("video",video);
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody EduVideo video){

        boolean b = videoService.updateById(video);

        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }


    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") String id) {

        boolean b = videoService.removeVideoById(id);

        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

}

