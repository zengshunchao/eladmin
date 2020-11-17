package com.study.web.dao;

import com.study.web.dto.ApplyDto;
import com.study.web.entity.Apply;
import com.study.web.entity.WxUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * (Apply)表数据库访问层
 *
 * @author zengsc
 * @since 2020-11-11 20:08:36
 */
@Repository
public interface ApplyDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Apply queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<ApplyDto> queryAllByLimit(WxUser wxUser, @Param("startNum") int startNum, @Param("pageSize") int pageSize);

    /**
     * 分页查询统计
     *
     * @return
     */
    int totalCount(WxUser wxUser);

    /**
     * 查询余额
     *
     * @param wxUserId
     * @return
     */
    BigDecimal getBalance(Long wxUserId);

    /**
     *  统计平台累计提现金额
     * @return
     */
    BigDecimal getAllMoney();

    /**
     * 通过实体作为筛选条件查询
     *
     * @param apply 实例对象
     * @return 对象列表
     */
    List<Apply> queryAll(Apply apply);

    /**
     * 新增数据
     *
     * @param apply 实例对象
     * @return 影响行数
     */
    int insert(Apply apply);

    /**
     * 修改数据
     *
     * @param apply 实例对象
     * @return 影响行数
     */
    int update(Apply apply);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 提现记录
     *
     * @param wxUser
     * @return
     */
    int totalSuccessCount(WxUser wxUser);

    /**
     * 查询指定行数据
     *
     * @param wxUser
     * @param startNum
     * @param pageSize
     * @return 对象列表
     */
    List<ApplyDto> queryAllSuccessByLimit(WxUser wxUser, @Param("startNum") int startNum, @Param("pageSize") int pageSize);
}