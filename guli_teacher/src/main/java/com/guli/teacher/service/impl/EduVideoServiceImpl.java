package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.teacher.client.VodClient;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.mapper.EduVideoMapper;
import com.guli.teacher.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-07-06
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


    @Autowired
    private VodClient vodClient;

    @Override
    public boolean removeVideoById(String id) {


        //查询云端视频id
        EduVideo video = baseMapper.selectById(id);

        String videoSourceId = video.getVideoSourceId();

        //删除视频资源
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeVideo(videoSourceId);
        }

        int i = baseMapper.deleteById(id);



        return i == 1;
    }

    @Override
    public Boolean removeVideoByCourseId(String courseId) {

        //根据课程id查询小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(wrapper);
        //定义一个集合存放视频id
        List<String> list = new ArrayList<>();
        //获取视频id
        for (EduVideo video : videoList) {
            if (!StringUtils.isEmpty(video.getVideoSourceId())) {
                list.add(video.getVideoSourceId());
            }
        }

        if (list.size() > 0) {
            vodClient.getRemoveList(list);
        }

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        int i = baseMapper.delete(queryWrapper);



        return i > 0;
    }
}
