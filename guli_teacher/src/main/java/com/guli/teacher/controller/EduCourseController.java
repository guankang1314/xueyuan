package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.query.CourseQuery;
import com.guli.teacher.entity.vo.CoursePublishVo;
import com.guli.teacher.entity.vo.CourseVo;
import com.guli.teacher.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-07-04
 */
@RestController
@RequestMapping("/course")
@CrossOrigin
public class EduCourseController {


    @Autowired
    private EduCourseService courseService;


    /**
     * 保存基本信息
     */
    @PostMapping("/saveVo")
    public Result save(@RequestBody CourseVo vo) {

        String courseId = courseService.saveVo(vo);
        return Result.ok().data("id",courseId);
    }

    /**
     * 根据课程id来获取课程的基本信息和描述
     */
    @GetMapping("/{id}")
    public Result getCourseVoById(@PathVariable("id") String id) {

        CourseVo vo = courseService.getCourseVoById(id);

        return Result.ok().data("courseInfo",vo);
    }


    /**
     * 修改课程基本信息
     * @param vo
     * @return
     */
    @PostMapping("/updateVo")
    public Result updateVo(@RequestBody CourseVo vo) {

        boolean b =  courseService.updateVo(vo);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    /**
     * 根据搜索条件分页查询
     */
    @PostMapping("/{page}/{limit}")
    public Result getPageList(@PathVariable("page") Integer page,
                              @PathVariable("limit") Integer limit,
                              @RequestBody CourseQuery query) {

        Page<EduCourse> coursePage = new Page<>(page,limit);

        courseService.getPageList(coursePage,query);

        return Result.ok().data("rows",coursePage.getRecords()).data("total",coursePage.getTotal());
    }


    /**
     * 根据id删除课程
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") String id) {

        boolean b = courseService.deleteById(id);

        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    /**
     * 根据课程id查询发布课程的详情
     */
    @GetMapping("vo/{id}")
    public Result getCoursePublishVoById(@PathVariable("id") String id) {

        //CoursePublishVo vo = courseService.getCoursePublishVoById(id);

        Map<String,Object> map = courseService.getMapById(id);

        return Result.ok().data(map);
    }

    @GetMapping("/updateStatusById/{id}")
    public Result updateStatusById(@PathVariable("id") String id) {

        Boolean b = courseService.updateStatusById(id);

        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}

