package com.guli.ucenter.mapper;

import com.guli.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author guli
 * @since 2020-07-09
 */
public interface MemberMapper extends BaseMapper<Member> {


    /**
     * 统计某一天注册人数
     */
    public Integer countRegisterNum(String day);

}
