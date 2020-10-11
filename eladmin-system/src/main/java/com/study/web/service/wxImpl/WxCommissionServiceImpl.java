package com.study.web.service.wxImpl;

import com.study.web.dao.CommissionDao;
import com.study.web.dto.CommissionDto;
import com.study.web.entity.Commission;
import com.study.web.service.WxCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 佣金表(Commission)表服务实现类
 *
 * @author zengsc
 * @since 2020-10-09 15:57:40
 */
@Service
public class WxCommissionServiceImpl implements WxCommissionService {
    @Autowired
    private CommissionDao commissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Commission queryById(Long id) {
        return this.commissionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Commission> queryAllByLimit(int offset, int limit) {
        return this.commissionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param commission 实例对象
     * @return 实例对象
     */
    @Override
    public Commission insert(Commission commission) {
        this.commissionDao.insert(commission);
        return commission;
    }

    /**
     * 修改数据
     *
     * @param commission 实例对象
     * @return 实例对象
     */
    @Override
    public Commission update(Commission commission) {
        this.commissionDao.update(commission);
        return this.queryById(commission.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.commissionDao.deleteById(id) > 0;
    }

    /**
     * 分页查询数据
     * @param commissionDto
     * @return
     */
    @Override
    public List<CommissionDto> queryList(CommissionDto commissionDto) {
        return commissionDao.queryList(commissionDto);
    }

    /**
     * 总记录数
     * @param commissionDto
     * @return
     */
    @Override
    public int totalList(CommissionDto commissionDto) {
        return commissionDao.totalList(commissionDto);
    }
}