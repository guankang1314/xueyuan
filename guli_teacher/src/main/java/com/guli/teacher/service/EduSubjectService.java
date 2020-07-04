package com.guli.teacher.service;

import com.guli.teacher.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author guli
 * @since 2020-07-03
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 根据EXCL表格导入课程分类
     * 返回错误信息
     * @param file
     * @return
     */

    List<String> importEXCL(MultipartFile file);


    /**
     * 获取课程分类树状图
     * @return
     */
    List<OneSubject> getTree();


    /**
     * 根据id来删除课程分类
     * @param id
     * @return
     */
    boolean deleteById(String id);


    /**
     * 添加课程一级分类
     * @param subject
     * @return
     */
    boolean saveLevelOne(EduSubject subject);


    /**
     * 保存二级分类
     * @param subject
     * @return
     */
    boolean saveLevelTwo(EduSubject subject);
}
