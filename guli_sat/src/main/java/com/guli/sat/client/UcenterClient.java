package com.guli.sat.client;

import com.guli.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("GULI-UCENTER")
public interface UcenterClient {

    @GetMapping("/member/countRegister/{day}")
    public Result countRegisterNum(@PathVariable("day") String day);

}
