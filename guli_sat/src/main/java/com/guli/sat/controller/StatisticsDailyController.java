package com.guli.sat.controller;


import com.guli.common.result.Result;
import com.guli.sat.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-07-09
 */
@RestController
@RequestMapping("/sat")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;



    /**
     * 获取某一天的注册人数,把注册人数添加到统计分析表中
     */
    @PostMapping("/createStaData/{day}")
    public Result createData(@PathVariable("day") String day) {


        staService.getataAdd(day);

        return Result.ok();
    }

    /**
     * 返回统计数据，两个数组
     */
    @GetMapping("/getCountData/{begin}/{end}/{type}")
    public Result getCountData(@PathVariable("begin") String begin,
                               @PathVariable("end") String end,
                               @PathVariable("type") String type) {

        Map<String,Object> map = staService.getCountData(begin,end,type);

        return Result.ok().data(map);
    }
}

