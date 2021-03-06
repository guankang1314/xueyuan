package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.entity.query.TeacherQuery;
import com.guli.teacher.mapper.EduTeacherMapper;
import com.guli.teacher.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-06-29
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    @Override
    public void pageQuery(Page<EduTeacher> teacherPage, TeacherQuery query) {

        if (query == null) {
            baseMapper.selectPage(teacherPage,null);
        }

        //获取对象属性
        String name = query.getName();
        Integer level = query.getLevel();
        Date gmtCreate = query.getGmtCreate();
        Date gmtModified = query.getGmtModified();

        //创建一个Warpper
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //判断对象属性是否存在
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        //判断时间是否存在，如果存在查询大于等于此时间的
        if (!StringUtils.isEmpty(gmtCreate)) {
            wrapper.ge("gmt_create",gmtCreate);
        }
        if (!StringUtils.isEmpty(gmtModified)) {
            wrapper.le("gmt_modified",gmtModified);
        }

        baseMapper.selectPage(teacherPage,wrapper);

    }

    /**
     * 前台系统讲师分页列表
     * @param teacherPage
     * @return
     */
    @Override
    public Map<String, Object> getTeacherAllFront(Page<EduTeacher> teacherPage) {


        baseMapper.selectPage(teacherPage,null);

        //把对象中的分页数据获取出来，放到map集合中
        long current = teacherPage.getCurrent();
        long total = teacherPage.getTotal();
        long size = teacherPage.getSize();
        long pages = teacherPage.getPages();
        List<EduTeacher> records = teacherPage.getRecords();
        boolean hasPrevious = teacherPage.hasPrevious();
        boolean hasNext = teacherPage.hasNext();

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
