package com.guli.sat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.result.Result;
import com.guli.sat.client.UcenterClient;
import com.guli.sat.entity.StatisticsDaily;
import com.guli.sat.mapper.StatisticsDailyMapper;
import com.guli.sat.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-07-09
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {


    @Autowired
    private UcenterClient ucenterClient;


    /**
     * 获取某一天的注册人数,把注册人数添加到统计分析表中
     */
    @Override
    public void getataAdd(String day) {

        //调用远程方法
        Result result = ucenterClient.countRegisterNum(day);

        //获取返回的对象中的数据

        Integer num = (Integer) result.getData().get("registerNum");

        //测试用示例
        // System.err.println("***********************"+num);

        //删除表里相同日期的数据，再重新插入
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        //将数据加入数据库
        StatisticsDaily daily = new StatisticsDaily();

        //统计日期
        daily.setDateCalculated(day);

        //注册人数
        daily.setRegisterNum(num);

        daily.setVideoViewNum(RandomUtils.nextInt(100,200));

        daily.setCourseNum(RandomUtils.nextInt(100,200));

        daily.setLoginNum(RandomUtils.nextInt(100,200));

        baseMapper.insert(daily);

    }

    @Override
    public Map<String, Object> getCountData(String begin, String end, String type) {

        Map<String,Object> map = new HashMap<>();

        //根据时间范围查询
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> statisticsDailyList = baseMapper.selectList(wrapper);

        //把数据构建成数组

        //创建两个List集合

        //日期集合
        List<String> dateList = new ArrayList<>();

        //数据集合
        List<Integer> dataList = new ArrayList<>();

        //遍历集合
        for (StatisticsDaily statisticsDaily : statisticsDailyList) {

            //放入日期集合
            String dateCalculated = statisticsDaily.getDateCalculated();
            dateList.add(dateCalculated);

            //放入数据集合
            switch (type) {

                case "login_num":
                    Integer loginNum = statisticsDaily.getLoginNum();
                    dataList.add(loginNum);
                    break;

                case "register_num":
                    Integer registerNum = statisticsDaily.getRegisterNum();
                    dataList.add(registerNum);
                    break;

                case "video_view_num":
                    Integer videoViewNum = statisticsDaily.getVideoViewNum();
                    dataList.add(videoViewNum);
                    break;

                case "course_num":
                    Integer courseNum = statisticsDaily.getCourseNum();
                    dataList.add(courseNum);
                    break;

                default:
                    break;
            }
        }

        map.put("dateList",dateList);
        map.put("dataList",dataList);

        return map;
    }
}
