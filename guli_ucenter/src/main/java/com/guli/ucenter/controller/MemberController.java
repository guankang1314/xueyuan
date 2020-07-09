package com.guli.ucenter.controller;


import com.guli.common.result.Result;
import com.guli.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-07-09
 */
@RestController
@RequestMapping("/member")
@CrossOrigin
public class MemberController {


    @Autowired
    private MemberService memberService;

    /**
    统计某一天的注册人数
     */
    @GetMapping("/countRegister/{day}")
    public Result countRegisterNum(@PathVariable("day") String day) {

        Integer num = memberService.registerCountNum(day);

        return Result.ok().data("registerNum",num);
    }

}

