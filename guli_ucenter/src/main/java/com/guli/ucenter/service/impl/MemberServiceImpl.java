package com.guli.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.ucenter.entity.Member;
import com.guli.ucenter.mapper.MemberMapper;
import com.guli.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-07-09
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {


    @Override
    public Integer registerCountNum(String day) {

        Integer num = baseMapper.countRegisterNum(day);

        return num;
    }

    @Override
    public Member getOpenUserInfo(String openid) {

        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);

        Member member = baseMapper.selectOne(wrapper);

        return member;
    }
}
