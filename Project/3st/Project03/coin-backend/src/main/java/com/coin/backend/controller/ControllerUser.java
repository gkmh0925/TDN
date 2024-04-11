package com.coin.backend.controller;

import com.coin.backend.dto.DtoUser;
import com.coin.backend.service.ServiceUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class ControllerUser {
    @Autowired
    private ServiceUser service;

    @RequestMapping(value={"/userList"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoUser> userList() {
        return service.userList();
    }

    @RequestMapping(value={"/userIdList"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoUser> userIdList(@RequestBody DtoUser dtoUser) {
        return service.userIdList(dtoUser.getId());
    }

    @RequestMapping(value={"/userJoin"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoUser> userJoin(@RequestBody DtoUser dtoUser) {
        return service.userJoin(dtoUser.getId(), dtoUser.getPw(), dtoUser.getPh(), dtoUser.getMail(), dtoUser.getcKey(), dtoUser.getsKey(), dtoUser.getKeyYn(), dtoUser.getAdYn());
    }

    @RequestMapping(value={"/userUpdate"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoUser> userUpdate(@RequestBody DtoUser dtoUser) {
        return service.userUpdate(dtoUser.getNo(), dtoUser.getId(), dtoUser.getPw(), dtoUser.getPh(), dtoUser.getMail(), dtoUser.getcKey(), dtoUser.getsKey(), dtoUser.getKeyYn(), dtoUser.getAdYn());
    }

    @RequestMapping(value={"/userDelete"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoUser> userDelete(@RequestBody DtoUser dtoUser) {
        return service.userDelete(dtoUser.getId());
    }

    @RequestMapping(value={"/userLogin"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoUser> userLogin(@RequestBody DtoUser dtoUser) {
        return service.userLogin(dtoUser.getId(), dtoUser.getPw());
    }

    @RequestMapping(value={"/userKey"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoUser> userKey(@RequestBody DtoUser dtoUser) {
        return service.userKey(dtoUser.getId());
    }

    @RequestMapping(value={"/userIpUpdate"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoUser> userIpUpdate(@RequestBody DtoUser dtoUser) {
        //ip����
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null){
            ip = req.getRemoteAddr();
        }

        System.out.println(ip);
        
        return service.userIpUpdate(dtoUser.getId(), ip);
    }

    @RequestMapping(value={"/userKeyUpdate"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoUser> userKeyUpdate(@RequestBody DtoUser dtoUser) {
        return service.userKeyUpdate(dtoUser.getId(), dtoUser.getcKey(), dtoUser.getsKey(), dtoUser.getKeyYn());
    }
}
