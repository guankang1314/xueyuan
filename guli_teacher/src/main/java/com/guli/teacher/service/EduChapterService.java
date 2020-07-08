package com.guli.teacher.service;

import com.guli.teacher.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.vo.OneChapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guli
 * @since 2020-07-04
 */
public interface EduChapterService extends IService<EduChapter> {


    /**
     * 根据课程id查询章节和小节列表
     * @param id
     * @return
     */
    List<OneChapter> getChapterAndVideoById(String id);


    /**
     * 保存章节
     * 判断保存的章节是否存在
     * @param chapter
     * @return
     */
    boolean saveChapter(EduChapter chapter);


    /**
     * 更新章节
     * 修改时判断章节名称是否存在
     * @param chapter
     * @return
     */
    boolean updateChapter(EduChapter chapter);


    /**
     * 根据章节id删除章节信息
     * @param id
     * @return
     */
    boolean removeChapterById(String id);

    /**
     * 根据课程id删除章节
     * @param id
     */
    Boolean removeBYCourseId(String id);
}
