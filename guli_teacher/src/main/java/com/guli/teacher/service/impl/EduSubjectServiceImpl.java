package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.vo.OneSubject;
import com.guli.teacher.entity.vo.TwoSubject;
import com.guli.teacher.mapper.EduSubjectMapper;
import com.guli.teacher.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-07-03
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    List<String> msg = new ArrayList<>();
    

    @Override
    public List<String> importEXCL(MultipartFile file) {

        try {
            //1、获取文件流
            InputStream inputStream = file.getInputStream();

            //2、根据文件流创建一个workbook
            Workbook workbook = new XSSFWorkbook(inputStream);

            //3、获取sheet
            Sheet sheet = workbook.getSheetAt(0);

            //4、根据sheet获取行数
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum <= 1) {
                msg.add("请填写数据");
                return msg;
            }

            //5、遍历行
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                Row row = sheet.getRow(rowNum);
                //6、获取每一行第一列，一级分类
                Cell cell = row.getCell(0);

                //7、判断列是否存在，存在获取列的值
                if (cell == null) {
                    msg.add("第"+rowNum+"行第1列为空");
                    continue;
                }

                String cellValue = cell.getStringCellValue();
                if (StringUtils.isEmpty(cellValue)) {
                    msg.add("第"+rowNum+"行第1列数据为空");
                    continue;
                }

                EduSubject subject = this.selectSubjectByName(cellValue);
                String pid = null;

                //8、如果为空说明之前没插入过，则插入
                if (subject == null) {

                    EduSubject subject1 = new EduSubject();
                    subject1.setTitle(cellValue);
                    subject1.setParentId("0");
                    subject1.setSort(0);

                    baseMapper.insert(subject1);
                    pid = subject1.getId();
                }else {

                    //如果存在，则说明之前插入过，获取pid
                    pid = subject.getId();
                }



                //10、再获取每一行第二列
                Cell cell1 = row.getCell(1);
                if (cell1 == null) {
                    msg.add("第"+rowNum+"行第2列为空");
                    continue;
                }
                String cellValue1 = cell1.getStringCellValue();
                if (StringUtils.isEmpty(cellValue1)) {
                    msg.add("第"+rowNum+"行第1列数据为空");
                    continue;
                }

                //判断此二级分类之前是否插入过
                EduSubject subject1 = this.selectSubjectByNameAndParentId(cellValue1,pid);
                //如果没有插入过
                if (subject1 == null) {

                    EduSubject subject2 = new EduSubject();
                    subject2.setTitle(cellValue1);
                    subject2.setParentId(pid);
                    subject2.setSort(0);

                    baseMapper.insert(subject2);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return msg;
    }




    /**
     * 根据二级分类名称和parentId查询是否存在
     * @param cellValue1
     * @param pid
     * @return
     */
    private EduSubject selectSubjectByNameAndParentId(String cellValue1, String pid) {


        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",cellValue1);
        wrapper.eq("parent_id",pid);

        EduSubject subject = baseMapper.selectOne(wrapper);
        return subject;
    }


    /**
     * 根据课程一级分类的名字查询是否存在
     * @param cellValue
     * @return
     */
    private EduSubject selectSubjectByName(String cellValue) {

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",cellValue);
        wrapper.eq("parent_id",0);

        EduSubject subject = baseMapper.selectOne(wrapper);
        return subject;
    }


    @Override
    public List<OneSubject> getTree() {

        //创建集合存放OneSubject
        List<OneSubject> oneSubjectList = new ArrayList<>();

        //获取一级分类的列表

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        List<EduSubject> eduSubjectsList = baseMapper.selectList(wrapper);

        //遍历一级分类列表
        for (EduSubject eduSubject : eduSubjectsList) {
            //对拷数据到onSubject
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);

            String id = oneSubject.getId();

            //根据id来查询二级课程
            QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", id);
            List<EduSubject> subjectList = baseMapper.selectList(queryWrapper);

            for (EduSubject eduSubject1 : subjectList) {

                //对拷数据到二级课程中
                TwoSubject twoSubject = new TwoSubject();
                BeanUtils.copyProperties(eduSubject1,twoSubject);

                //将twosubject加入到onesubject的children中
                oneSubject.getChildren().add(twoSubject);
            }

            //把onsubject加入到oneSubjectList中
            oneSubjectList.add(oneSubject);
        }


        return oneSubjectList;
    }

    @Override
    public boolean deleteById(String id) {

        //根据id来查询数据库中是否有二级类型
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<EduSubject> subjectList = baseMapper.selectList(wrapper);


        if (subjectList.size() != 0) {
            //如果有，返回false
            return false;
        }

        //如果没有，直接删除，返回true
        int i = baseMapper.deleteById(id);

        return i == 1;
    }

    @Override
    public boolean saveLevelOne(EduSubject subject) {

        //根据title判断一级分类是否存在
        EduSubject eduSubject = this.selectSubjectByName(subject.getTitle());


        if (eduSubject != null) {
            //存在返回false
            return false;
        }

        //不存在，保存,返回true
        int i = baseMapper.insert(subject);

        return i == 1;

    }

    @Override
    public boolean saveLevelTwo(EduSubject subject) {

        //判断此一级分类中是否存在此二级分类
        EduSubject eduSubject = this.selectSubjectByNameAndParentId(subject.getTitle(), subject.getParentId());
        if (eduSubject != null) {
            return false;
        }

        //不存在，保存，返回true
        int i = baseMapper.insert(subject);

        return i == 1;
    }
}
