package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.Account;
import com.orange.verify.api.service.AccountService;
import com.orange.verify.api.vo.AccountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(description = "用户接口")
@Controller
@RequestMapping(value = "account")
public class AccountController {

    @Reference
    private AccountService accountService;

    @ApiOperation(value = "获取用户-需要验证api")
    @RspHandle
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(AccountVo accountVo, Page page) {

        Page<AccountVo> cardTypeVoPage = accountService.page(accountVo,page);
        return Response.build(ResponseCode.QUERY_SUCCESS,cardTypeVoPage);
    }

    @ApiOperation(value = "用户黑名单设置-需要验证api")
    @RspHandle
    @RequestMapping(value = "blacklist",method = RequestMethod.POST)
    @ResponseBody
    public Response blacklist(String accountId,Integer blacklist) {

        Account account = new Account();
        account.setId(accountId);
        account.setBlacklist(blacklist);
        boolean b = accountService.updateById(account);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation(value = "删除用户-需要验证api")
    @RspHandle
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    @ResponseBody
    public Response remove(String accountId) {

        boolean b = accountService.removeById(accountId);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

}