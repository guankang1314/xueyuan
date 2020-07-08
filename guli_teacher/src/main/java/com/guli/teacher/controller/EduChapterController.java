package com.guli.teacher.controller;


import com.guli.common.result.Result;
import com.guli.teacher.entity.EduChapter;
import com.guli.teacher.entity.vo.OneChapter;
import com.guli.teacher.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-07-04
 */
@RestController
@RequestMapping("/chapter")
@CrossOrigin
public class EduChapterController {


    @Autowired
    private EduChapterService chapterService;


    /**
     * 根据课程id来获取章节小节列表
     * 1、创建Vo作为章节，包括二级集合
     * 2、创建二级Vo（video）
     * 3、根据课程id查询章节的列表，遍历列表
     */
    @GetMapping("{courseId}")
    public Result getChapterAndVideoById(@PathVariable("courseId") String courseId) {


        List<OneChapter> chapterList = chapterService.getChapterAndVideoById(courseId);
        return Result.ok().data("list",chapterList);
    }

    /**
     * 保存章节
     */
    @PostMapping("/save")
    public Result save(@RequestBody EduChapter chapter) {

        boolean save = chapterService.saveChapter(chapter);

        if (save) {
            return Result.ok();
        }

        return Result.error();
    }

    /**
     * 根据id查询
     */
    @GetMapping("/get/{id}")
    public Result getChapterById(@PathVariable("id") String id) {

        EduChapter chapter = chapterService.getById(id);

        return Result.ok().data("chapter",chapter);

    }

    @PutMapping("update")
    public Result updateById(@RequestBody EduChapter chapter) {

        boolean b = false;

        try {
            b = chapterService.updateChapter(chapter);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(e.getMessage());
        }

    }


    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") String id) {

        boolean b = chapterService.removeChapterById(id);

        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }


}

