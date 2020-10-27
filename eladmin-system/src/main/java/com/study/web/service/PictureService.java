package com.study.web.service;

import com.study.web.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (Picture)表服务接口
 *
 * @author zengsc
 * @since 2020-09-03 15:54:10
 */
public interface PictureService {

    /**
     * 新增数据
     *
     * @return 实例对象
     */
    Long insert(Picture picture,MultipartFile multipartFile,String serverIpPort);

    /**
     * 修改数据
     *
     * @param picture 实例对象
     * @return 实例对象
     */
    void update(Picture picture);

    /**
     *  根据课程表id查询图片列表
     * @param courseId
     * @return
     */
    List<Picture> queryPictureByCourseId(Long courseId);


    /**
     *  批量删除
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}