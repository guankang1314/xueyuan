package com.guli.sat.service;

import com.guli.sat.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author guli
 * @since 2020-07-09
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void getataAdd(String day);

    Map<String, Object> getCountData(String begin, String end, String type);
}
