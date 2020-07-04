package com.guli.teacher.controller;


import com.guli.common.result.Result;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.vo.OneSubject;
import com.guli.teacher.service.EduSubjectService;
import com.guli.teacher.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-07-03
 */
@RestController
@RequestMapping("/subject")
@CrossOrigin
public class EduSubjectController {


    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("/import")
    public Result importSubject(MultipartFile file) {

        List<String> msgList = subjectService.importEXCL(file);
        if (msgList.size() == 0) {
            return Result.ok();
        }else {
            return Result.error().data("messageList",msgList);
        }
    }

    /**
     * 获取课程分类的tree
     * @return
     */
    @GetMapping("/tree")
    public Result TreeSubject() {

        List<OneSubject> subjectList = subjectService.getTree();

        return Result.ok().data("subjectList",subjectList);
    }


    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") String id) {

        boolean b = subjectService.deleteById(id);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @PostMapping("/saveLevelOne")
    public Result saveLevelOne(@RequestBody EduSubject subject) {

        boolean flag = subjectService.saveLevelOne(subject);
        if (flag) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @PostMapping("/saveLevelTwo")
    public Result saveLevelTwo(@RequestBody EduSubject subject) {

        boolean flag = subjectService.saveLevelTwo(subject);
        if (flag) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

}

