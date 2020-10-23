package com.study.web.service.impl;

import com.study.web.dao.DistributionDao;
import com.study.web.dto.BackGroundDistributionInfoDto;
import com.study.web.service.BackGroundDistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zsc
 * @date 2020/10/23 0023 20:25
 */
@Service
public class BackGroundDistributionServiceImpl implements BackGroundDistributionService {

    @Autowired
    private DistributionDao distributionDao;

    @Override
    public List<BackGroundDistributionInfoDto> backGroundQueryDistribution(BackGroundDistributionInfoDto backGroundDistributionInfoDto, Pageable pageable) {
        int startNum = pageable.getPageNumber() > 0 ? pageable.getPageNumber() * pageable.getPageSize() : 0;
        return distributionDao.queryAllDistribution(backGroundDistributionInfoDto, startNum, pageable.getPageSize());
    }

    @Override
    public int backGroundQueryDistributionTotal(BackGroundDistributionInfoDto backGroundDistributionInfoDto) {
        return distributionDao.backGroundQueryDistributionTotal(backGroundDistributionInfoDto);
    }
}
