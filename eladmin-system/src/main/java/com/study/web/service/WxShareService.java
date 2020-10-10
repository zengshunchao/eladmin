package com.study.web.service;

import com.study.web.entity.Share;

import java.util.List;

/**
 * 分享关系表(Share)表服务接口
 *
 * @author zengsc
 * @since 2020-10-09 16:35:21
 */
public interface WxShareService {

    /**
     * 通过wxUserId查询单条数据
     *
     * @param wxUserId wxUserId
     * @return 实例对象
     */
    Share queryById(Long wxUserId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Share> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param share 实例对象
     * @return 实例对象
     */
    Share insert(Share share);

    /**
     * 修改数据
     *
     * @param share 实例对象
     * @return 实例对象
     */
    void update(Share share);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}