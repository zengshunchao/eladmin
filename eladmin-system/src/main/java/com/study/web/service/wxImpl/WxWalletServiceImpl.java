package com.study.web.service.wxImpl;

import com.study.web.dao.WalletDao;
import com.study.web.dao.WalletWaterDao;
import com.study.web.dto.WalletDto;
import com.study.web.entity.Commission;
import com.study.web.entity.Wallet;
import com.study.web.entity.WalletWater;
import com.study.web.service.WxWalletService;
import com.study.web.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 我的钱包(Wallet)表服务实现类
 *
 * @author zengsc
 * @since 2020-10-09 16:38:42
 */
@Service
public class WxWalletServiceImpl implements WxWalletService {
    @Autowired
    private WalletDao walletDao;

    @Autowired
    private WalletWaterDao walletWaterDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Wallet queryById(Long id) {
        return this.walletDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Wallet> queryAllByLimit(int offset, int limit) {
        return this.walletDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param wallet 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Wallet insert(Wallet wallet) {
        this.walletDao.insert(wallet);
        return wallet;
    }

    /**
     * 修改数据
     *
     * @param wallet 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Wallet update(Wallet wallet) {
        this.walletDao.update(wallet);
        return this.queryById(wallet.getId());
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
        return this.walletDao.deleteById(id) > 0;
    }

    @Override
    public Wallet queryByWxUserId(Long wxUserId) {
        return this.walletDao.queryByWxUserId(wxUserId);
    }

    /**
     * 提现
     *
     * @param walletDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdrawal(WalletDto walletDto) throws Exception {
        Wallet wallet = this.walletDao.queryById(walletDto.getId());
        if (null == wallet) {
            throw new Exception("未查询到钱包");
        }
        if (walletDto.getWithdrawal().compareTo(wallet.getMayCashMoney()) > 0) {
            throw new Exception("提现金额异常");
        }
        wallet.setCashMoney(wallet.getCashMoney().add(walletDto.getWithdrawal()));
        wallet.setMayCashMoney(wallet.getMayCashMoney().subtract(walletDto.getWithdrawal()));
        walletDao.update(wallet);
        //添加流水信息
        WalletWater water = new WalletWater(walletDto.getWxUserId(), walletDto.getWithdrawal(), Constants.WALLET_WATER_EXPEND, "佣金提现");
        walletWaterDao.insert(water);

        //TODO
        // 微信提现请求
        //
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWalletByCommission(Commission commission) {
        // 查询用户钱包
        Wallet wallet = walletDao.queryByWxUserId(commission.getWxUserId());

        if (wallet != null) {
            BigDecimal allMoney = wallet.getAllMoney().add(commission.getCommissionMoney());
            BigDecimal lockMoney = wallet.getLockMoney().add(commission.getCommissionMoney());
            wallet.setAllMoney(allMoney);
            wallet.setLockMoney(lockMoney);
            walletDao.update(wallet);
        }
    }
}