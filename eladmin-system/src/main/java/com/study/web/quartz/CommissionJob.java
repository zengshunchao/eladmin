package com.study.web.quartz;

import com.study.web.service.WxCommissionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author zyf
 * @date 2020/10/20
 */
public class CommissionJob extends QuartzJobBean implements Job {
    @Autowired
    private QuartzManager quartzManager;
    @Autowired
    private WxCommissionService wxCommissionService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //佣金id
        Long commissionId = (Long) jobExecutionContext.getMergedJobDataMap().get("commissionId");
        Long wxUserId = (Long) jobExecutionContext.getMergedJobDataMap().get("wxUserId");
        //修改佣金状态
        wxCommissionService.updateLockStatus(commissionId);

        //修改钱包


        //删除定时任务
        quartzManager.removeJob(String.valueOf(jobExecutionContext.getMergedJobDataMap().get("commissionId")), "动态任务触发器", String.valueOf(jobExecutionContext.getMergedJobDataMap().get("commissionId")), "COMMISSION_JOB_GROUP");

    }
}
