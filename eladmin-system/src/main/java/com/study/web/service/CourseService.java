package com.study.web.service;

import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.CourseQueryDto;
import com.study.web.entity.Course;
import com.study.web.entity.Picture;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * (Course)表服务接口
 *
 * @author zengsc
 * @since 2020-09-03 15:47:45
 */
public interface CourseService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CourseInfoDto queryById(Long id);

    /**
     * 新增课程、添加封面图 详情图
     *
     * @param course         课程信息
     * @return
     */
    Course insert(Course course, List<Picture> pictureList);

    /**
     * 修改数据
     *
     * @param course 实例对象
     * @return 实例对象
     */
    void update(Course course, List<Picture> pictureList);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 查询所有数据
     *
     * @param course
     * @param pageable
     * @return
     */
    List<CourseInfoDto> queryAll(CourseQueryDto course, Pageable pageable);

    /**
     * 批量删除
     *
     * @param ids
     */
    void deleteBatch(Set<Long> ids);

    /**
     * 更新课程状态
     *
     * @param id
     */
    void updateLineType(Long id);

    /**
     *  分页查询统计
     * @param courseQueryDto
     * @return
     */
    int totalCourse(CourseQueryDto courseQueryDto);
}