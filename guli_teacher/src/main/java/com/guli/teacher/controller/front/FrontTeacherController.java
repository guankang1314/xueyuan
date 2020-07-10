package com.guli.teacher.controller.front;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.service.EduCourseService;
import com.guli.teacher.service.EduTeacherService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frontTeacher")
@CrossOrigin
public class FrontTeacherController {


    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    /**
     * 讲师列表分页
     */

    @GetMapping("findAllTeacherFront/{page}/{limit}")
    public Result getAllTeacherFront(@PathVariable("page") Long page,
                                     @PathVariable("limit") Long limit) {

        //创建page对象
        Page<EduTeacher> teacherPage = new Page<>(page,limit);

        //返回map集合,得到所有数据
        Map<String,Object> map = teacherService.getTeacherAllFront(teacherPage);


        return Result.ok().data(map);
    }

    /**
     * 根据讲师Id查询讲师的信息
     */
    @GetMapping("getFrontTeacherInfo/{id}")
    public Result getFrontTeacherInfo(@PathVariable("id") String id) {

        //根据讲师id查询讲师基本信息

        EduTeacher teacherInfo = teacherService.getById(id);

        //根据讲师id查询讲师的课程
        List<EduCourse> courseList = courseService.getCourseListById(id);

        return Result.ok().data("teacherInfo",teacherInfo).data("courseList",courseList);
    }

}
