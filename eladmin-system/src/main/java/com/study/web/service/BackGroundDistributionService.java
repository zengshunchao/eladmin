package com.study.web.service;

import com.study.web.dto.BackGroundDistributionInfoDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author zsc
 * @date 2020/10/23 0023 20:24
 */
public interface BackGroundDistributionService {

    /**
     * 后台-分销管理
     *
     * @param pageable
     * @return
     */
    List<BackGroundDistributionInfoDto> backGroundQueryDistribution(BackGroundDistributionInfoDto backGroundDistributionInfoDto,Pageable pageable);

    /**
     * 统计分销记录
     *
     * @return
     */
    int backGroundQueryDistributionTotal(BackGroundDistributionInfoDto backGroundDistributionInfoDto);
}
