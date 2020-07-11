package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.query.CourseQuery;
import com.guli.teacher.entity.vo.CoursePublishVo;
import com.guli.teacher.entity.vo.CourseVo;
import com.guli.teacher.entity.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

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


    /**
     * 根据课程id查询课程和描述
     * @param id
     * @return
     */
    CourseVo getCourseVoById(String id);

    /**
     * 修改课程基本信息
     * @param vo
     * @return
     */
    boolean updateVo(CourseVo vo);


    /**
     * 根据条件分页查询课程
     * @param coursePage
     * @param query
     */
    void getPageList(Page<EduCourse> coursePage, CourseQuery query);


    /**
     * 根据课程id删除课程信息
     * @param id
     * @return
     */
    boolean deleteById(String id);


    /**
     * 根据课程Id查询发布课程的详情
     * @param id
     * @return
     */
    CoursePublishVo getCoursePublishVoById(String id);


    /**
     * 根据Id修改课程状态
     * @param id
     * @return
     */
    Boolean updateStatusById(String id);

    /**
     * 根据课程id查询map对象
     * @param id
     * @return
     */
    Map<String, Object> getMapById(String id);

    /**
     * 通过讲师的Id查询讲师的课程
     *
     * @param id
     * @return
     */
    List<EduCourse> getCourseListById(String id);

    /**
     * 前端查询课程列表
     * @param coursePage
     * @return
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage);


    /**
     * 根据课程id查询课程基本信息
     * @param id
     * @return
     */
    CourseWebVo getCourseFrontInfoById(String id);
}
