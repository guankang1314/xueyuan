package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.query.TeacherQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author guli
 * @since 2020-06-29
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 根据条件分页查询
     */
    void pageQuery(Page<EduTeacher> teacherPage,TeacherQuery query);

    /**
     * 前台查询讲师列表
     * @param teacherPage
     * @return
     */
    Map<String, Object> getTeacherAllFront(Page<EduTeacher> teacherPage);
}
