package com.guli.ucenter.controller;


import com.google.gson.Gson;
import com.guli.ucenter.entity.Member;
import com.guli.ucenter.service.MemberService;
import com.guli.ucenter.utils.ConstantPropertiesUtil;
import com.guli.ucenter.utils.HttpClientUtils;
import com.guli.ucenter.utils.JwtUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApicontroller {


    @Autowired
    private MemberService memberService;


    @GetMapping("/callback")
    public String callback(String code, String state, HttpSession session, HttpServletResponse response) {

        //得到授权临时票据code
        System.out.println("code = " + code);
        System.out.println("state = " + state);

        //返回临时票据code

        //拿着code请求微信的固定地址
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        //设置其中的参数
        baseAccessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code
        );

        try {
            //使用httpclient发送请求，获取openid和access_token
            String resultAccessToken = HttpClientUtils.get(baseAccessTokenUrl);

            System.err.println(resultAccessToken);

            //将返回的字符串转化为map对象
            Gson gson = new Gson();
            Map<String,Object> map = gson.fromJson(resultAccessToken, HashMap.class);

            //获取凭证和id
            String access_token = (String) map.get("access_token");
            String openid = (String) map.get("openid");

            //判断用户表中是否存在扫码的用户信息
            Member member = memberService.getOpenUserInfo(openid);

            //如果为空，不存在
            if (member == null) {

            //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                baseUserInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );

                String resultUserInfo = HttpClientUtils.get(baseUserInfoUrl);

                System.err.println(resultUserInfo);

                Map<String,Object> userMap = gson.fromJson(resultUserInfo,HashMap.class);

                String nickname =(String) userMap.get("nickname");

                String headimgurl = (String) userMap.get("headimgurl");



                //添加
                Member member1 = new Member();
                member1.setNickname(nickname);
                member1.setAvatar(headimgurl);
                member1.setOpenid(openid);

                memberService.save(member1);
            }

            //根据member生成jwt字符串
            String token = JwtUtils.geneJsonWebToken(member);

            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);

            //回到首页面
            return "redirect:http://localhost:3000?token="+token;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }


    /**
     * 生成二维码
     */
    @GetMapping("/login")
    public String genQrConnect() {

        //请求微信提供的固定地址，向地址中拼接参数，生成二维码
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        try {
            //对重定向的地址使用Encode编码
            String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");



            String state = "atguan";

            //在baseurl中拼接参数
            String qrcodeUrl = String.format(
                    baseUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    redirectUrl,
                    state
            );

            return "redirect:"+qrcodeUrl;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

    }

}
