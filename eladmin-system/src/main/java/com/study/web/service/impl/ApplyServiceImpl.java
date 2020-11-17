package com.study.web.service.impl;

import com.study.web.dao.ApplyDao;
import com.study.web.dao.WalletDao;
import com.study.web.dao.WalletWaterDao;
import com.study.web.dto.ApplyDto;
import com.study.web.entity.Apply;
import com.study.web.entity.Wallet;
import com.study.web.entity.WalletWater;
import com.study.web.entity.WxUser;
import com.study.web.service.ApplyService;
import com.study.web.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * (Apply)表服务实现类
 *
 * @author zengsc
 * @since 2020-11-11 20:08:37
 */
@Service("applyService")
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    private ApplyDao applyDao;
    @Autowired
    private WalletWaterDao walletWaterDao;
    @Autowired
    private WalletDao walletDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Apply queryById(Long id) {
        return this.applyDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public List<ApplyDto> queryAllByLimit(WxUser wxUser, Pageable pageable) {
        int startNum = pageable.getPageNumber() > 0 ? pageable.getPageNumber() * pageable.getPageSize() : 0;
        List<ApplyDto> applies = applyDao.queryAllByLimit(wxUser, startNum, pageable.getPageSize());
        applies.stream().forEach(apply -> {
            BigDecimal balance = applyDao.getBalance(apply.getWxUserId());
            if (balance != null) {
                apply.setBalance(balance);
            }
        });
        return applies;
    }

    /**
     * 新增数据
     *
     * @param apply 实例对象
     * @return 实例对象
     */
    @Override
    public Apply insert(Apply apply) {
        this.applyDao.insert(apply);
        return apply;
    }

    /**
     * 修改数据
     *
     * @param apply 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Apply apply) {
        apply.setCheckTime(new Date());
        applyDao.update(apply);
        // 审核不通过则重新计算用户钱包
        if (Constants.APPLY_STATUS_FAIL == apply.getStatus()) {
            Apply queryApply = applyDao.queryById(apply.getId());
            // 查询流水
            WalletWater water = walletWaterDao.queryById(queryApply.getWalletWaterId());
            // 查询用户钱包
            Wallet wallet = walletDao.queryById(queryApply.getWxUserId());
            wallet.setCashMoney(wallet.getCashMoney().subtract(water.getMoney()));
            wallet.setMayCashMoney(wallet.getMayCashMoney().add(water.getMoney()));
            walletDao.update(wallet);
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.applyDao.deleteById(id) > 0;
    }

    @Override
    public int totalCount(WxUser wxUser) {
        return applyDao.totalCount(wxUser);
    }

    @Override
    public int totalSuccessCount(WxUser wxUser) {
        return applyDao.totalSuccessCount(wxUser);
    }

    @Override
    public List<ApplyDto> queryAllSuccessByLimit(WxUser wxUser, Pageable pageable) {
        int startNum = pageable.getPageNumber() > 0 ? pageable.getPageNumber() * pageable.getPageSize() : 0;
        List<ApplyDto> applies = applyDao.queryAllSuccessByLimit(wxUser, startNum, pageable.getPageSize());
        return applies;
    }

    @Override
    public BigDecimal getAllMoney() {
        return applyDao.getAllMoney();
    }
}