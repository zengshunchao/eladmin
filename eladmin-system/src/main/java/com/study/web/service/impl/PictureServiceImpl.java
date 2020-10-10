package com.study.web.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.study.config.FileProperties;
import com.study.exception.BadRequestException;
import com.study.utils.FileUtil;
import com.study.utils.SecurityUtils;
import com.study.web.dao.PictureDao;
import com.study.web.entity.Picture;
import com.study.web.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (Picture)表服务实现类
 *
 * @author zengsc
 * @since 2020-09-03 15:54:10
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private FileProperties properties;

    /**
     * 新增数据
     *
     * @return 实例对象
     */
    @Override
    public Integer insert(Picture picture, MultipartFile multipartFile, String serverIpPort) {
        FileUtil.checkSize(properties.getMaxSize(), multipartFile.getSize());
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getEnglishFileType(suffix);
        File file = FileUtil.upload(multipartFile, properties.getPath().getPath() + type + File.separator);
        if (ObjectUtil.isNull(file)) {
            throw new BadRequestException("上传失败");
        }
        picture.setPictureName(file.getName());
        String currentUsername = SecurityUtils.getCurrentUsername();
        // 保存可预览地址
        String pictureUrl = serverIpPort + file.getName();
        picture.setPictureUrl(pictureUrl);
        picture.setCreateTime(new Date());
        picture.setCreateUser(currentUsername);
        int id = this.pictureDao.insert(picture);
        return id;
    }

    /**
     * 修改数据
     *
     * @param picture 实例对象
     * @return 实例对象
     */
    @Override
    public void update(Picture picture) {
        this.pictureDao.update(picture);
    }

    @Override
    public List<Picture> queryPictureByCourseId(Long courseId) {
        return pictureDao.queryPictureList(courseId);
    }
}