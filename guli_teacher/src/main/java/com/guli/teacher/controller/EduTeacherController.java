package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.common.result.ResultCode;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.entity.query.TeacherQuery;
import com.guli.teacher.exception.EduException;
import com.guli.teacher.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-06-29
 */
@RestController
@RequestMapping("/teacher")
@Api(description = "讲师管理")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;


    @ApiOperation(value = "所有讲师列表")
    @GetMapping("list")
    public Result list() {

        try {
            List<EduTeacher> list = teacherService.list(null);
            return Result.ok().data("items",list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "讲师删除")
    @DeleteMapping("/{id}")
    public Result deleteById(
            @ApiParam(name = "id",value = "讲师id",required = true)
            @PathVariable(value = "id") String id) {

        try {
            teacherService.removeById(id);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "讲师分页列表")
    @GetMapping("/{page}/{limit}")
    public Result selectTeacgerByPage(
            @ApiParam(name = "page",value = "当前页",required = true)
            @PathVariable(value = "page") Integer page,
            @ApiParam(name = "limit",value = "每页显示条数",required = true)
            @PathVariable(value = "limit") Integer limit
    ) {

        try {
            Page<EduTeacher> eduTeacherPage = new Page<>(page, limit);

            teacherService.page(eduTeacherPage,null);
            List<EduTeacher> records = eduTeacherPage.getRecords();
            long total = eduTeacherPage.getTotal();

            return Result.ok().data("total",total).data("rows",records);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "讲师条件分页查询")
    @PostMapping("/{page}/{limit}")
    public Result selectTeacgerByPageAndWrapper(
            @ApiParam(name = "page",value = "当前页",required = true)
            @PathVariable(value = "page") Integer page,
            @ApiParam(name = "limit",value = "每页显示条数",required = true)
            @PathVariable(value = "limit") Integer limit,
            @RequestBody TeacherQuery teacherQuery) {

        try {
            Page<EduTeacher> eduTeacherPage = new Page<>(page, limit);

            //查询
            teacherService.pageQuery(eduTeacherPage,teacherQuery);
            return Result.ok().data("total",eduTeacherPage.getTotal()).data("rows",eduTeacherPage.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "新增教师")
    @PostMapping("/save")
    public Result saveTeacher(@RequestBody EduTeacher teacher) {

        try {
            teacherService.save(teacher);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public Result selectTeacherById(
            @ApiParam(name = "id",value = "教师id",required = true)
            @PathVariable("id") String id
            ) {

        try {
            EduTeacher teacher = teacherService.getById(id);
//            if (teacher == null) {
//                throw new EduException(ResultCode.EDU_ID_ERROR,"没有此讲师信息");
//            }
            return Result.ok().data("teacher",teacher);
        } catch (EduException e) {
            e.printStackTrace();
            return Result.error();
        }
    }


    @ApiOperation(value = "修改讲师信息")
    @PutMapping("/update")
    public Result selectTeacherById(@RequestBody EduTeacher teacher) {

        try {
            teacherService.updateById(teacher);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

}

