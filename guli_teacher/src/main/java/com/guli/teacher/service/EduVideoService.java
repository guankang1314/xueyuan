package com.guli.teacher.service;

import com.guli.teacher.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author guli
 * @since 2020-07-06
 */
public interface EduVideoService extends IService<EduVideo> {


    /**
     * 根据Videoid删除视频
     * @param id
     * @return
     */
    boolean removeVideoById(String id);


    /**
     * 根据课程id删除小节
     * @param courseId
     * @return
     */
    Boolean removeVideoByCourseId(String courseId);

}
