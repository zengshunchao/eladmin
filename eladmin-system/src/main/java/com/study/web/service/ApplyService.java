package com.study.web.service;

import com.study.web.dto.ApplyDto;
import com.study.web.entity.Apply;
import com.study.web.entity.WxUser;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (Apply)表服务接口
 *
 * @author zengsc
 * @since 2020-11-11 20:08:36
 */
public interface ApplyService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Apply queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param wxUser
     * @param pageable
     * @return 对象列表
     */
    List<ApplyDto> queryAllByLimit(WxUser wxUser, Pageable pageable);

    /**
     * 新增数据
     *
     * @param apply 实例对象
     * @return 实例对象
     */
    Apply insert(Apply apply);

    /**
     * 修改数据
     *
     * @param apply 实例对象
     * @return 实例对象
     */
    void update(Apply apply);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 分页查询统计
     *
     * @param wxUser
     * @return
     */
    int totalCount(WxUser wxUser);
}