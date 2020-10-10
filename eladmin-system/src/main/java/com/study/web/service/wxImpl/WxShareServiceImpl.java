package com.study.web.service.wxImpl;

import com.study.web.dao.ShareDao;
import com.study.web.entity.Share;
import com.study.web.service.WxShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分享关系表(Share)表服务实现类
 *
 * @author zengsc
 * @since 2020-10-09 16:35:21
 */
@Service
public class WxShareServiceImpl implements WxShareService {
    @Autowired
    private ShareDao shareDao;

    /**
     * 通过ID查询单条数据
     *
     * @param wxUserId 主键
     * @return 实例对象
     */
    @Override
    public Share queryById(Long wxUserId) {
        return shareDao.queryById(wxUserId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Share> queryAllByLimit(int offset, int limit) {
        return shareDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param share 实例对象
     * @return 实例对象
     */
    @Override
    public Share insert(Share share) {
        shareDao.insert(share);
        return share;
    }

    /**
     * 修改分享人
     *
     * @param share 实例对象
     * @return 实例对象
     */
    @Override
    public void update(Share share) {
        // 查询是否已经存在该微信用户分享关系记录
        Share queryShare = shareDao.queryById(share.getWxUserId());
        if (queryShare == null) {
            shareDao.insert(share);
        }
        if (queryShare != null) {
            shareDao.update(share);
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
        return shareDao.deleteById(id) > 0;
    }
}