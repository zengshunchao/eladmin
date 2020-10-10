package com.study.web.dao;

import com.study.web.entity.CoursePictureRel;
import com.study.web.entity.Picture;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Picture)表数据库访问层
 *
 * @author zengsc
 * @since 2020-09-03 15:54:10
 */
@Repository
public interface PictureDao {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param picture 实例对象
     * @return 对象列表
     */
    List<Picture> queryAll(Picture picture);

    /**
     * 新增数据
     *
     * @param picture 实例对象
     * @return 影响行数
     */
    int insert(Picture picture);

    /**
     * 修改数据
     *
     * @param picture 实例对象
     * @return 影响行数
     */
    int update(Picture picture);

    /**
     *  根据课程id查询相关联图片列表
     * @param courseId
     * @return
     */
    List<Picture> queryPictureList(@Param("courseId") Long courseId);

    /**
     *  批量删除
     * @param ids
     */
    void deleteBatch(@Param("list") List<Long> ids);

    /**
     *  根据类型获取图片列表
     * @param courseId
     * @param pictureType
     * @return
     */
    List<Picture> queryPictureListByType(@Param("courseId") Long courseId,@Param("pictureType") Integer pictureType);
}