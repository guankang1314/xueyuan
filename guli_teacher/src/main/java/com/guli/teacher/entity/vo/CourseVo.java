package com.guli.teacher.entity.vo;


import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduCourseDescription;
import lombok.Data;

@Data
public class CourseVo {

    private EduCourse course;

    private EduCourseDescription description;
}
