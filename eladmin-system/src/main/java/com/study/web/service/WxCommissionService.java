package com.study.web.service;

import com.study.web.entity.Commission;

import java.util.List;

/**
 * 佣金表(Commission)表服务接口
 *
 * @author zengsc
 * @since 2020-10-09 15:57:40
 */
public interface WxCommissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Commission queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Commission> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param commission 实例对象
     * @return 实例对象
     */
    Commission insert(Commission commission);

    /**
     * 修改数据
     *
     * @param commission 实例对象
     * @return 实例对象
     */
    Commission update(Commission commission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}