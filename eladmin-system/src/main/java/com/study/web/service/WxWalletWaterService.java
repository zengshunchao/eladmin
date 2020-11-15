package com.study.web.service;

import com.study.web.dto.WalletWaterDto;
import com.study.web.entity.WalletWater;

import java.util.List;

/**
 * 钱包流水(WalletWater)表服务接口
 *
 * @author zengsc
 * @since 2020-10-09 16:45:15
 */
public interface WxWalletWaterService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WalletWater queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<WalletWater> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param walletWater 实例对象
     * @return 实例对象
     */
    WalletWater insert(WalletWater walletWater);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    Integer countWalletWaterList(WalletWaterDto walletWaterDto);

    List<WalletWaterDto> getWalletWaterList(WalletWaterDto walletWaterDto);

}