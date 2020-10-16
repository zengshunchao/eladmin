package com.study.web.dto;

import com.study.web.entity.Order;
import lombok.Data;

import java.util.Date;

/**
 * @author zsc
 * @date 2020/10/15 0015 21:56
 */
@Data
public class BackGroundOrderQueryDto extends Order {

    /**
     * 查询-结束时间
     */
    private Date endTime;
}
