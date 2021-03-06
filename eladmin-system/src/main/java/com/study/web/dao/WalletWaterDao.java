package com.study.web.dao;

import com.study.web.dto.WalletDto;
import com.study.web.dto.WalletWaterDto;
import com.study.web.entity.WalletWater;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 钱包流水(WalletWater)表数据库访问层
 *
 * @author zengsc
 * @since 2020-10-09 16:45:14
 */
@Repository
public interface WalletWaterDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WalletWater queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<WalletWater> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param walletWater 实例对象
     * @return 对象列表
     */
    List<WalletWater> queryAll(WalletWater walletWater);

    /**
     * 新增数据
     *
     * @param walletWater 实例对象
     * @return 影响行数
     */
    int insert(WalletWater walletWater);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    Integer countWalletWaterList(WalletWaterDto walletWaterDto);

    List<WalletWaterDto> getWalletWaterList(WalletWaterDto walletWaterDto);
}