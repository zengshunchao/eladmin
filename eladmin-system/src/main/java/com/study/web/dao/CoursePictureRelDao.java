package com.study.web.dao;

import com.study.web.entity.CoursePictureRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (CoursePictureRel)表数据库访问层
 *
 * @author zengsc
 * @since 2020-09-07 23:41:28
 */
@Repository
public interface CoursePictureRelDao {


    /**
     * 新增数据
     *
     * @param coursePictureRel 实例对象
     * @return 影响行数
     */
    int insert(CoursePictureRel coursePictureRel);

    /**
     * 批量删除
     *
     * @param ids
     */
    void deleteBatch(@Param("list") List<Long> ids);

    /**
     * 批量查询
     *
     * @param ids
     * @return
     */
    List<CoursePictureRel> queryByCourseId(@Param("list") List<Long> ids);
}