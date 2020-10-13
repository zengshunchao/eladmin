package com.study.web.service.wxImpl;

import com.study.web.dao.CourseDao;
import com.study.web.dao.PictureDao;
import com.study.web.dto.CourseInfoDto;
import com.study.web.entity.Course;
import com.study.web.entity.Picture;
import com.study.web.service.WxCourseService;
import com.study.web.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxCourseServiceImpl implements WxCourseService {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private PictureDao pictureDao;

    @Override
    public List<CourseInfoDto> queryList(CourseInfoDto courseInfoDto) {

        List<CourseInfoDto> courseList = null;
        // 查询所有为上架状态的课程
        courseList = courseDao.wxQueryCourseList(courseInfoDto);

        if (courseList != null && courseList.size() != 0) {
            for (CourseInfoDto info : courseList) {
                // 封面
                List<Picture> covers = pictureDao.queryPictureListByType(info.getId(), Constants.PICTURE_TYPE_COVER);
                info.setCoverPicture(covers);

                // 详情图
                List<Picture> infos = pictureDao.queryPictureListByType(info.getId(), Constants.PICTURE_TYPE_INFO);
                info.setCourseInfoPicture(infos);

            }
        }
        return courseList;
    }

    @Override
    public int totalList() {
        return courseDao.wxQueryCourseListTotal();
    }

    /**
     * 获取课程详情
     *
     * @param id
     * @return
     */
    @Override
    public CourseInfoDto queryCourseById(Long id) {
        CourseInfoDto courseInfoDto = courseDao.queryById(id);
        if (courseInfoDto != null) {
            // 封面
            List<Picture> covers = pictureDao.queryPictureListByType(id, Constants.PICTURE_TYPE_COVER);
            courseInfoDto.setCoverPicture(covers);

            // 详情图
            List<Picture> infos = pictureDao.queryPictureListByType(id, Constants.PICTURE_TYPE_INFO);
            courseInfoDto.setCourseInfoPicture(infos);
        }
        return courseInfoDto;
    }
}
