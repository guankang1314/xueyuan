package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduCourseDescription;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.entity.query.CourseQuery;
import com.guli.teacher.entity.vo.CoursePublishVo;
import com.guli.teacher.entity.vo.CourseVo;
import com.guli.teacher.mapper.EduCourseMapper;
import com.guli.teacher.service.EduChapterService;
import com.guli.teacher.service.EduCourseDescriptionService;
import com.guli.teacher.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.teacher.service.EduVideoService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-07-04
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;

    @Override
    public String saveVo(CourseVo vo) {

        //添加课程
        baseMapper.insert(vo.getEduCourse());

        //返回课程id
        String courseId = vo.getEduCourse().getId();

        //添加课程描述
        vo.getCourseDescription().setId(courseId);
        courseDescriptionService.save(vo.getCourseDescription());

        return courseId;
    }

    @Override
    public CourseVo getCourseVoById(String id) {

        //创建Vo对象
        CourseVo vo = new CourseVo();

        //根据课程id来获取课程基本信息
        EduCourse eduCourse = baseMapper.selectById(id);

        //加入课程基本信息
        if (eduCourse == null) {
            return vo;
        }
        vo.setEduCourse(eduCourse);

        //根据课程id来获取课程描述

        EduCourseDescription description = courseDescriptionService.getById(id);
        //加入课程描述
        if (description == null) {
            return vo;
        }

        vo.setCourseDescription(description);
        //返回Vo

        return vo;
    }

    @Override
    public boolean updateVo(CourseVo vo) {

        //先判断id是否存在如果不存在，返回false
        if (StringUtils.isEmpty(vo.getEduCourse().getId())) {
            return false;

        }

        //如果存在，修改course
        int i = baseMapper.updateById(vo.getEduCourse());

        if (i <= 0) {
            return false;
        }
        //修改描述
        vo.getCourseDescription().setId(vo.getEduCourse().getId());

        boolean b = courseDescriptionService.updateById(vo.getCourseDescription());

        return b;

    }

    @Override
    public void getPageList(Page<EduCourse> coursePage, CourseQuery query) {


        if (query == null) {
            baseMapper.selectPage(coursePage,null);
        }

        String subjectId = query.getSubjectId();
        String subjectParentId = query.getSubjectParentId();
        String teacherId = query.getTeacherId();
        String title = query.getTitle();

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id",subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id",teacherId);
        }
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }

        baseMapper.selectPage(coursePage,wrapper);
    }

    @Override
    public boolean deleteById(String id) {


        videoService.removeVideoByCourseId(id);


        chapterService.removeBYCourseId(id);

        //删除课程的基本信息

        int i = baseMapper.deleteById(id);
        if (i <= 0) {
            return false;
        }


        //删除课程的描述
        boolean b = courseDescriptionService.removeById(id);

        return b;
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {

        CoursePublishVo vo = baseMapper.getCoursePublishVoById(id);

        return vo;
    }

    @Override
    public Boolean updateStatusById(String id) {

        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");

        int i = baseMapper.updateById(eduCourse);

        return i == 1;
    }

    @Override
    public Map<String, Object> getMapById(String id) {

        Map<String,Object> map = baseMapper.getMapById(id);

        return map;
    }

    @Override
    public List<EduCourse> getCourseListById(String id) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);

        List<EduCourse> courseList = baseMapper.selectList(wrapper);

        return courseList;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage) {


        baseMapper.selectPage(coursePage,null);

        long current = coursePage.getCurrent();
        long total = coursePage.getTotal();
        long size = coursePage.getSize();
        long pages = coursePage.getPages();
        List<EduCourse> records = coursePage.getRecords();
        boolean hasPrevious = coursePage.hasPrevious();
        boolean hasNext = coursePage.hasNext();


        Map<String,Object> map = new HashMap<>();

        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }


}
