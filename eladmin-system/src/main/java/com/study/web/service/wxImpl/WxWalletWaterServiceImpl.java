package com.study.web.service.wxImpl;

import com.study.web.dao.WalletWaterDao;
import com.study.web.entity.WalletWater;
import com.study.web.service.WxWalletWaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 钱包流水(WalletWater)表服务实现类
 *
 * @author zengsc
 * @since 2020-10-09 16:45:15
 */
@Service
public class WxWalletWaterServiceImpl implements WxWalletWaterService {
    @Autowired
    private WalletWaterDao walletWaterDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WalletWater queryById(Long id) {
        return this.walletWaterDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<WalletWater> queryAllByLimit(int offset, int limit) {
        return this.walletWaterDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param walletWater 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletWater insert(WalletWater walletWater) {
        this.walletWaterDao.insert(walletWater);
        return walletWater;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        return this.walletWaterDao.deleteById(id) > 0;
    }
}