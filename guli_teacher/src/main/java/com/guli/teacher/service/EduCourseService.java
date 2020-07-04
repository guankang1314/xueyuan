package com.guli.teacher.service;

import com.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.vo.CourseVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guli
 * @since 2020-07-04
 */
public interface EduCourseService extends IService<EduCourse> {


    /**
     * 保存课程基本信息
     * @param vo
     * @return
     */
    String saveVo(CourseVo vo);
}
