package com.study.web.service;

import com.study.web.entity.Distribution;

import java.util.List;

/**
 * 分销员表(Distribution)表服务接口
 *
 * @author zengsc
 * @since 2020-10-09 16:10:39
 */
public interface WxDistributionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Distribution queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Distribution> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param distribution 实例对象
     * @return 实例对象
     */
    Distribution insert(Distribution distribution);

    /**
     * 修改数据
     *
     * @param distribution 实例对象
     * @return 实例对象
     */
    Distribution update(Distribution distribution);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}