package com.study.web.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.study.utils.ElAdminConstant;
import com.study.web.entity.Picture;
import com.study.web.service.PictureService;
import com.study.web.util.ServerUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * (Picture)表控制层
 *
 * @author zengsc
 * @since 2020-09-03 16:44:21
 */
@RestController
@RequestMapping("/background/picture")
public class PictureInfoController {
    //根据id查询图片
    @Autowired
    private PictureService pictureService;

    @ApiOperation("添加附件")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @PreAuthorize("@el.check('picture:add')")
    public ResponseEntity<Object> insert(
            HttpServletRequest request,
            HttpServletResponse response, @RequestParam("file") MultipartFile file,@RequestParam("pictureType") Integer pictureType) {

        if (ObjectUtil.isEmpty(file)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Picture picture = new Picture();
            picture.setPictureType(pictureType);
            pictureService.insert(picture,file,getServerPath(request));
            return new ResponseEntity<>(picture, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("删除附件")
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @PreAuthorize("@el.check('picture:del')")
    public ResponseEntity<Object> delete(@RequestBody String ids){
        try {
            if(StringUtils.isEmpty(ids) ){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            JSONObject jsonObject = (JSONObject) JSONObject.parse(ids);
            String[] temp = jsonObject.getString("ids").split(",");
            List<Long> list = new ArrayList<>();
            for (String s : temp) {
                list.add(Long.valueOf(s));
            }
            pictureService.deleteBatch(list);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getServerPath(HttpServletRequest request) {
        // 存储图片预览地址
        String serverIPPort = ServerUtil.getServerIPPort(request);
        serverIPPort = serverIPPort + "/file/image/";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith(ElAdminConstant.WIN)) {
            return serverIPPort;
        } else if (os.toLowerCase().startsWith(ElAdminConstant.MAC)) {
            return "https://tomuchlove.xyz/file/image/";
        }
        return "https://tomuchlove.xyz/file/image/";
    }
}