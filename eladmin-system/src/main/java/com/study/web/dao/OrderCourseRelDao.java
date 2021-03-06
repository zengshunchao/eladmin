package com.study.web.dao;

import com.study.web.entity.OrderCourseRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单-课程关联表(OrderCourseRel)表数据库访问层
 *
 * @author zengsc
 * @since 2020-10-09 22:28:00
 */
@Repository
public interface OrderCourseRelDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderCourseRel queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OrderCourseRel> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param orderCourseRel 实例对象
     * @return 对象列表
     */
    List<OrderCourseRel> queryAll(OrderCourseRel orderCourseRel);

    /**
     * 新增数据
     *
     * @param orderCourseRel 实例对象
     * @return 影响行数
     */
    int insert(OrderCourseRel orderCourseRel);

    /**
     * 修改数据
     *
     * @param orderCourseRel 实例对象
     * @return 影响行数
     */
    int update(OrderCourseRel orderCourseRel);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}