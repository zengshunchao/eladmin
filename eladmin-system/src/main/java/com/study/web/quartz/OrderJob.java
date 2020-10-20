package com.study.web.quartz;

import com.study.web.dao.OrderDao;
import com.study.web.dto.OrderDto;
import com.study.web.entity.Order;
import com.study.web.service.WxOrderService;
import com.study.web.util.Constants;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author zsc
 * @date 2020/10/20 0020 22:17
 */
public class OrderJob extends QuartzJobBean implements Job {

    @Autowired
    private QuartzManager quartzManager;
    @Autowired
    private OrderDao orderDao;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Long orderId = (Long) jobExecutionContext.getMergedJobDataMap().get("orderId");
        OrderDto orderDto = orderDao.queryById(orderId);
        if (null != orderDto && orderDto.getStatus() == Constants.UNPAID) {
            Order order = new Order();
            order.setId(orderId);
            order.setFinishTime(new Date());
            orderDao.update(order);
        }
        //删除定时任务
        quartzManager.removeJob(String.valueOf(jobExecutionContext.getMergedJobDataMap().get("orderId")), "动态订单任务触发器", String.valueOf(jobExecutionContext.getMergedJobDataMap().get("orderId")), "ORDER_JOB_GROUP");

    }
}
