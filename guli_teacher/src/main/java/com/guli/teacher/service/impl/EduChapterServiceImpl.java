package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.teacher.entity.EduChapter;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.entity.vo.OneChapter;
import com.guli.teacher.entity.vo.TwoVideo;
import com.guli.teacher.exception.EduException;
import com.guli.teacher.mapper.EduChapterMapper;
import com.guli.teacher.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.teacher.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.QueryEval;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-07-04
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {


    @Autowired
    private EduVideoService videoService;


    @Override
    public List<OneChapter> getChapterAndVideoById(String id) {

        ArrayList<OneChapter> oneChapters = new ArrayList<>();

        //根据id查询章节列表

        QueryWrapper<EduChapter> oneChapterQueryWrapper = new QueryWrapper<>();
        oneChapterQueryWrapper.eq("course_id",id);
        oneChapterQueryWrapper.orderByAsc("sort");
        List<EduChapter> eduChapterList = baseMapper.selectList(oneChapterQueryWrapper);

        //遍历章节列表
        for (EduChapter chapter : eduChapterList) {

            //对拷章节到onechapter
            OneChapter oneChapter = new OneChapter();
            BeanUtils.copyProperties(chapter,oneChapter);

            //根据章节id查询小节列表
            String chapterId = oneChapter.getId();

            QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
            eduVideoQueryWrapper.eq("chapter_id",chapterId);
            eduVideoQueryWrapper.orderByAsc("sort");
            List<EduVideo> videoList = videoService.list(eduVideoQueryWrapper);

            //遍历小节列表
            for (EduVideo video : videoList) {

                //把每个小节对拷的twochapter
                TwoVideo twoVideo = new TwoVideo();
                BeanUtils.copyProperties(video,twoVideo);

                //把twochapter加入到children
                oneChapter.getChildren().add(twoVideo);
            }

            //把onechapter加入到返回的列表中
            oneChapters.add(oneChapter);
        }

        return oneChapters;
    }

    @Override
    public boolean saveChapter(EduChapter chapter) {

        if (chapter == null) {
            return false;
        }

        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("title",chapter.getTitle());

        Integer count = baseMapper.selectCount(wrapper);

        if (count > 0) {
            return false;
        }
        int i = baseMapper.insert(chapter);

        return i == 1;
    }

    @Override
    public boolean updateChapter(EduChapter chapter) {

        if (chapter == null) {
            return false;
        }

        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("title",chapter.getTitle());

        Integer count = baseMapper.selectCount(wrapper);

        if (count > 0) {
            throw new EduException(20001,"章节名称已存在");
        }
        int i = baseMapper.updateById(chapter);

        return i == 1;

    }

    @Override
    public boolean removeChapterById(String id) {

        //判断章节id是否有小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        List<EduVideo> list = videoService.list(wrapper);

        //如果有,不能删除，返回false
        if (list.size() != 0) {
            throw new EduException(20001,"章节下有小节先删除小节");
        }

        //如果没有，删除
        int i = baseMapper.deleteById(id);

        return i == 1;
    }

    @Override
    public Boolean removeBYCourseId(String id) {

        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);


        int i = baseMapper.delete(wrapper);

        return i > 0;
    }
}
