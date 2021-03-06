package com.study.web.quartz;

import cn.hutool.core.date.DateTime;
import com.study.web.dao.DictDao;
import com.study.web.entity.Commission;
import com.study.web.entity.Dict;
import com.study.web.entity.Order;
import com.study.web.service.WxCommissionService;
import com.study.web.service.WxOrderService;
import com.study.web.util.Constants;
import com.study.web.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zyf
 * @date 2020/10/20
 */
@Configuration      // 1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@EnableAsync        // 3.开启多线程
@Slf4j              // 4.后台日志打印
public class MyTask {

    @Autowired
    QuartzManager quartzManager;

    @Autowired
    WxCommissionService wxCommissionService;
    @Autowired
    private WxOrderService wxOrderService;
    @Autowired
    private DictDao dictDao;

    /**
     * 系统启动后5秒执行一次
     * 每小时执行一次
     * 佣金解锁自动任务
     *
     * @throws Exception
     */
    @Scheduled(initialDelay = 5000, fixedDelay = 60L * 60 * 1000)
    public void commissionTask() throws Exception {

        String dateStr = TimeUtil.afterTime(new Date(), TimeUtil.MINUTE, "yyyy-MM-dd HH:mm:ss", 60);
        //获取65分钟内解锁的佣金
        List<Commission> list = wxCommissionService.queryListByLockTime(dateStr);

        if (null != list && list.size() > 0) {
            for (Commission commission : list) {
                if (commission.getLockStatus() == 0) {
                    //解锁时间小于当前时间 自动解锁
                    Date now = TimeUtil.afterTime(new Date(), TimeUtil.SECOND, 10);
                    if(commission.getLockTime().compareTo(now)<=0){
                        //修改佣金状态并修改用户钱包
                        wxCommissionService.updateLockStatus(commission.getId());
                    }else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("commissionId", commission.getId());
                        String cron = TimeUtil.transCorn(commission.getLockTime());
                        quartzManager.addJob("" + commission.getId(), "动态佣金任务触发器", "" + commission.getId(), "COMMISSION_JOB_GROUP", CommissionJob.class, cron, map);
                    }
                }
            }
        }
    }
}
