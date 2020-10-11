package com.study.web.dao;

import com.study.web.dto.CommissionDto;
import com.study.web.entity.Commission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 佣金表(Commission)表数据库访问层
 *
 * @author zengsc
 * @since 2020-10-09 15:57:40
 */
@Repository
public interface CommissionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Commission queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Commission> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param commission 实例对象
     * @return 对象列表
     */
    List<Commission> queryAll(Commission commission);

    /**
     * 新增数据
     *
     * @param commission 实例对象
     * @return 影响行数
     */
    int insert(Commission commission);

    /**
     * 修改数据
     *
     * @param commission 实例对象
     * @return 影响行数
     */
    int update(Commission commission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 分页查询数据
     * @param commissionDto
     * @return
     */
    List<CommissionDto> queryList(CommissionDto commissionDto);

    /**
     * 总记录数
     * @param commissionDto
     * @return
     */
    int totalList(CommissionDto commissionDto);

}