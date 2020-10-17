package com.study.web.service;

import com.study.web.dto.WalletDto;
import com.study.web.entity.Wallet;

import java.util.List;

/**
 * 我的钱包(Wallet)表服务接口
 *
 * @author zengsc
 * @since 2020-10-09 16:38:42
 */
public interface WxWalletService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Wallet queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Wallet> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param wallet 实例对象
     * @return 实例对象
     */
    Wallet insert(Wallet wallet);

    /**
     * 修改数据
     *
     * @param wallet 实例对象
     * @return 实例对象
     */
    Wallet update(Wallet wallet);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 通过用户ID查询单条数据
     *
     * @param wxUserId 用户主键
     * @return 实例对象
     */
    Wallet queryByWxUserId(Long wxUserId);

    void withdrawal(WalletDto walletDto) throws Exception;
}