package com.guli.ucenter.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.guli.common.result.Result;
import com.guli.ucenter.entity.Member;
import com.guli.ucenter.service.MemberService;
import com.guli.ucenter.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 根据token值获取用户信息
     */
    @PostMapping("/getUserInfoToken/{token}")
    public Result getUserInfoToken(@PathVariable("token") String token) {

        Claims claims = JwtUtils.checkJWT(token);

        String id = (String) claims.get("id");
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");

        Member member = new Member();
        member.setAvatar(avatar);
        member.setNickname(nickname);
        member.setId(id);

        return Result.ok().data("member",member);
    }



    @PostMapping("login")
    public Result login(HttpServletRequest request){
        String token = request.getHeader("token");
        String cookie = request.getHeader("cookie");

        System.out.println(token);
        System.out.println(cookie);

        return Result.ok();
    }

}

