package com.study.web.service.wxImpl;

import com.study.web.dao.DistributionDao;
import com.study.web.dto.DistributionDto;
import com.study.web.dto.WxUserDto;
import com.study.web.entity.Distribution;
import com.study.web.entity.Share;
import com.study.web.entity.Wallet;
import com.study.web.entity.WxUser;
import com.study.web.service.WxDistributionService;
import com.study.web.service.WxShareService;
import com.study.web.service.WxUserService;
import com.study.web.service.WxWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 分销员表(Distribution)表服务实现类
 *
 * @author zengsc
 * @since 2020-10-09 16:10:39
 */
@Service
public class WxDistributionServiceImpl implements WxDistributionService {
    @Autowired
    private DistributionDao distributionDao;


    @Autowired
    private WxWalletService wxWalletService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private WxShareService wxShareService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Distribution queryById(Long id) {
        return this.distributionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Distribution> queryAllByLimit(int offset, int limit) {
        return this.distributionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param distribution 实例对象
     * @return 实例对象
     */
    @Override
    public Distribution insert(Distribution distribution) {
        this.distributionDao.insert(distribution);
        return distribution;
    }

    /**
     * 修改数据
     *
     * @param distribution 实例对象
     * @return 实例对象
     */
    @Override
    public Distribution update(Distribution distribution) {
        this.distributionDao.update(distribution);
        return this.queryById(distribution.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.distributionDao.deleteById(id) > 0;
    }

    @Override
    public Distribution queryByWxUserId(Long wxUserid) {
        return this.distributionDao.queryByWxUserId(wxUserid);
    }

    /**
     * 变更分销员
     *
     * @param distribution
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void addDistribution(Distribution distribution) {
        Distribution temp = distributionDao.queryByWxUserId(distribution.getWxUserId());
        if (null == temp) {
            distributionDao.insert(distribution);
            //初始化钱包信息
            BigDecimal zero = new BigDecimal(0);
            Wallet wallet = wxWalletService.queryByWxUserId(distribution.getWxUserId());
            if(null == wallet){
                wallet = new Wallet(distribution.getWxUserId(), zero, zero, zero, zero);
                wxWalletService.insert(wallet);
            }

            //初始化分享信息
            //分销员的分享人只能是自己
            Share share = new Share();
            share.setShareId(distribution.getWxUserId());
            share.setWxUserId(distribution.getWxUserId());
            wxShareService.update(share);
        }
    }

    /**
     * 分销员
     *
     * @param distributionDto
     * @return
     */
    @Override
    public List<DistributionDto> getDistributionList(DistributionDto distributionDto) {
        return distributionDao.getDistributionList(distributionDto);
    }

    @Override
    public int totalList(DistributionDto distributionDto) {
        return distributionDao.totalList(distributionDto);
    }
}