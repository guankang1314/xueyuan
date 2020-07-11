package com.guli.teacher.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.vo.CourseWebVo;
import com.guli.teacher.entity.vo.OneChapter;
import com.guli.teacher.service.EduChapterService;
import com.guli.teacher.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/frontCourse")
@CrossOrigin
public class FrontCourseController {


    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    /**
     * 课程分类列表
     */

    @GetMapping("getCourseFrontList/{page}/{limit}")
    public Result getCourseFrontList(@PathVariable("page") Long page,
                                     @PathVariable("limit") Long limit) {

        Page<EduCourse> coursePage = new Page<>(page,limit);

        Map<String,Object> map = courseService.getCourseFrontList(coursePage);

        System.err.println(map);

        return Result.ok().data(map);
    }

    /**
     * 根据课程id查询课程详情信息
     */
    @GetMapping("getFrontCourseInfo/{id}")
    public Result getFrontCourseInfo(@PathVariable("id") String id) {


        //根据id查询课程基本信息
        CourseWebVo courseFrontInfo = courseService.getCourseFrontInfoById(id);

        //根据id查询课程大纲
        List<OneChapter> chapterAndVideoById = chapterService.getChapterAndVideoById(id);

        return Result.ok().data("courseFrontInfo",courseFrontInfo).data("chapterVideoList",chapterAndVideoById);

    }


}
