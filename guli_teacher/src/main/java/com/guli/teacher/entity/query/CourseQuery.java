package com.guli.teacher.entity.query;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
public class CourseQuery implements Serializable {

    private String subjectId;

    private String subjectParentId;

    private String title;

    private String teacherId;
}
