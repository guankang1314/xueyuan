package com.guli.teacher.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/frontCourse")
@CrossOrigin
public class FrontCourseController {


    @Autowired
    private EduCourseService courseService;

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


}
