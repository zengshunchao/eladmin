package com.study.web.dao;

import com.study.web.entity.Share;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分享关系表(Share)表数据库访问层
 *
 * @author zengsc
 * @since 2020-10-09 16:35:20
 */
@Repository
public interface ShareDao {

    /**
     * 通过wxUserId查询单条数据
     *
     * @param wxUserId 微信用户id
     * @return 实例对象
     */
    Share queryById(Long wxUserId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Share> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param share 实例对象
     * @return 对象列表
     */
    List<Share> queryAll(Share share);

    /**
     * 新增数据
     *
     * @param share 实例对象
     * @return 影响行数
     */
    int insert(Share share);

    /**
     * 修改分享人
     *
     * @param share 实例对象
     * @return 影响行数
     */
    int update(Share share);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}