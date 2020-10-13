package com.study.web.dao;

import com.study.web.dto.CourseInfoDto;
import com.study.web.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * (Course)表数据库访问层
 *
 * @author zengsc
 * @since 2020-09-03 15:47:43
 */
@Repository
public interface CourseDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CourseInfoDto queryById(@Param("id") Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param course 实例对象
     * @return 对象列表
     */
    List<CourseInfoDto> queryAll(Course course);

    /**
     * 新增数据
     *
     * @param course 实例对象
     * @return 影响行数
     */
    int insert(Course course);

    /**
     * 修改数据
     *
     * @param course 实例对象
     * @return 影响行数
     */
    int update(Course course);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteBatch(@Param("list") List<Long> ids);

    /**
     * 微信-获取上架状态的课程
     *
     * @param courseInfoDto
     * @return
     */
    List<CourseInfoDto> wxQueryCourseList(CourseInfoDto courseInfoDto);

    /**
     * 查询全部上架课程数
     *
     * @return
     */
    int wxQueryCourseListTotal();
}