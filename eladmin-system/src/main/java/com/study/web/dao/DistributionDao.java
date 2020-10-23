package com.study.web.dao;

import com.study.web.dto.BackGroundDistributionInfoDto;
import com.study.web.dto.DistributionDto;
import com.study.web.dto.WxUserDto;
import com.study.web.entity.Distribution;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分销员表(Distribution)表数据库访问层
 *
 * @author zengsc
 * @since 2020-10-09 16:10:38
 */
@Repository
public interface DistributionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Distribution queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Distribution> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param distribution 实例对象
     * @return 对象列表
     */
    List<Distribution> queryAll(Distribution distribution);

    /**
     * 新增数据
     *
     * @param distribution 实例对象
     * @return 影响行数
     */
    int insert(Distribution distribution);

    /**
     * 修改数据
     *
     * @param distribution 实例对象
     * @return 影响行数
     */
    int update(Distribution distribution);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    Distribution queryByWxUserId(Long wxUserid);

    /**
     * 下级分销员
     *
     * @param distributionDto
     * @return
     */
    List<DistributionDto> getDistributionList(DistributionDto distributionDto);

    /**
     * 总记录数
     *
     * @param distributionDto
     * @return
     */
    int totalList(DistributionDto distributionDto);

    /**
     * 分销管理-分销列表
     *
     * @param startNum
     * @param pageSize
     * @return
     */
    List<BackGroundDistributionInfoDto> queryAllDistribution(BackGroundDistributionInfoDto backGroundDistributionInfoDto, @Param("startNum") int startNum, @Param("pageSize") int pageSize);

    /**
     * 后台-分销管理统计
     *
     * @return
     */
    int backGroundQueryDistributionTotal(BackGroundDistributionInfoDto backGroundDistributionInfoDto);
}