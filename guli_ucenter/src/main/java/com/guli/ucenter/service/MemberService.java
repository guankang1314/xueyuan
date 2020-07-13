package com.guli.ucenter.service;

import com.guli.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author guli
 * @since 2020-07-09
 */
public interface MemberService extends IService<Member> {



    Integer registerCountNum(String day);

    /**
     * 根据openid判断用户是否以存在
     * @param openid
     * @return
     */
    Member getOpenUserInfo(String openid);
}
