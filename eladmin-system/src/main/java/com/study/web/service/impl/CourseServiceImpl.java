package com.study.web.service.impl;

import com.study.utils.ElAdminConstant;
import com.study.utils.SecurityUtils;
import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.CourseQueryDto;
import com.study.web.util.Constants;
import com.study.web.dao.CourseDao;
import com.study.web.dao.CoursePictureRelDao;
import com.study.web.dao.PictureDao;
import com.study.web.entity.Course;
import com.study.web.entity.CoursePictureRel;
import com.study.web.entity.Picture;
import com.study.web.service.CourseService;
import com.study.web.service.PictureService;
import com.study.web.util.ServerUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * (Course)表服务实现类
 *
 * @author zengsc
 * @since 2020-09-03 15:47:46
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private CoursePictureRelDao coursePictureRelDao;
    @Autowired
    private PictureDao pictureDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CourseInfoDto queryById(Long id) {
        return this.courseDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param course 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Course insert(Course course,List<Picture> pictureList) {

        // 保存课程
        String currentUsername = SecurityUtils.getCurrentUsername();
        course.setCreateUser(currentUsername);
        course.setCreateTime(new Date());
        this.courseDao.insert(course);
        addCoursePictureRel(course,pictureList);
        return course;
    }

    /**
     * 修改数据
     *
     * @param course 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Course course, List<Picture> pictureList) {
        this.courseDao.update(course);
        //删除原有图片关联
        List<Long> ids = new ArrayList<>();
        ids.add(course.getId());
        coursePictureRelDao.deleteBatch(ids);
        addCoursePictureRel(course,pictureList);

    }

    /**
     * 添加课程图片关联
     * @param course
     * @param pictureList
     */
    private void addCoursePictureRel(Course course, List<Picture> pictureList){
        for (Picture picture : pictureList) {
            CoursePictureRel coursePictureRel = new CoursePictureRel();
            coursePictureRel.setCourseId(course.getId());
            coursePictureRel.setPictureId(picture.getId());
            coursePictureRelDao.insert(coursePictureRel);
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
        return this.courseDao.deleteById(id) > 0;
    }

    @Override
    public List<CourseInfoDto> queryAll(CourseQueryDto course, Pageable pageable) {
        Course queryCourse = queryParam(course);
        // 此处为什么没有减一，因pageable传过来的页码是计算过的
        int startNum = pageable.getPageNumber() > 0 ? pageable.getPageNumber() * pageable.getPageSize() : 0;
        List<CourseInfoDto> courseList = courseDao.queryAll(queryCourse, startNum, pageable.getPageSize());
        if (courseList != null && courseList.size() != 0) {
            for (CourseInfoDto info : courseList) {
                // 设置课程状态
                String lineTypeString = toLineTypeString(info);
                info.setLineTypeString(lineTypeString);
                List<Picture> cover = new ArrayList<>();
                List<Picture> courseInfo = new ArrayList<>();
                List<Picture> pictures = pictureService.queryPictureByCourseId(info.getId());
                if (pictures != null && pictures.size() != 0) {
                    for (Picture picture : pictures) {
                        // 封面图
                        if (picture.getPictureType() == Constants.PICTURE_TYPE_COVER) {
                            cover.add(picture);
                            info.setCoverPicture(cover);
                        } else if (picture.getPictureType() == Constants.PICTURE_TYPE_INFO) {
                            // 详情图
                            courseInfo.add(picture);
                            info.setCourseInfoPicture(courseInfo);
                        }
                    }
                }
            }
        }
        return courseList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Set<Long> ids) {
        // 删除课程
        List<Long> list = new ArrayList<>(ids);
        courseDao.deleteBatch(list);

        List<CoursePictureRel> coursePictureRels = coursePictureRelDao.queryByCourseId(list);
        //删除关联表
        coursePictureRelDao.deleteBatch(list);

        List<Long> pId = new ArrayList<>();
        if (coursePictureRels != null && coursePictureRels.size() != 0) {
            for (CoursePictureRel coursePictureRel : coursePictureRels) {
                pId.add(coursePictureRel.getPictureId());
            }
            pictureDao.deleteBatch(pId);
        }

    }

    @Override
    public void updateLineType(Long id) {
        // 查询当前课程状态
        CourseInfoDto courseInfo = courseDao.queryById(id);
        if (courseInfo != null) {
            Course course = new Course();
            course.setId(id);
            Date date = new Date();
            // 上架时间在当前时间之后则修改为上架
            if (courseInfo.getOnlineTime().after(date)) {
                course.setOnlineTime(date);
            } else if (courseInfo.getOnlineTime().before(date) && courseInfo.getOfflineTime().after(date)) {
                // 上架时间在当前时间之前，并且当前时间在下架时间之前
                course.setOfflineTime(date);
            }
            courseDao.update(course);
        }

    }

    @Override
    public int totalCourse(CourseQueryDto courseQueryDto) {
        // 查询条件
        Course course = queryParam(courseQueryDto);
        int total = courseDao.totalCourseNum(course);
        return total;
    }

    @Override
    public void updateSortNumber(Course course) {
        courseDao.update(course);
    }

    private Course queryParam(CourseQueryDto course) {
        Course queryCourse = new Course();
        try {
            Date now = new Date();
            queryCourse.setCourseName(course.getCourseName());
            if (course.getLineType() != null) {
                // 已下架
                if (Constants.YES_OFFLINE == course.getLineType()) {
                    queryCourse.setOfflineTime(now);
                } else if (Constants.YES_ONLINE == course.getLineType()) {
                    queryCourse.setOnlineTime(now);
                    queryCourse.setOfflineTime(now);
                } else if (Constants.NO_ONLINE == course.getLineType()) {
                    queryCourse.setOnlineTime(now);
                }
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotEmpty(course.getOnlineTime())) {
                String[] dates = course.getOnlineTime().split(",");
                course.setOfflineTime(dates[1]);
                Date onlineDate = format.parse(dates[0]);
                queryCourse.setOnlineTime(onlineDate);
            }
            if (StringUtils.isNotEmpty(course.getOfflineTime())) {
                Date offlineDate = format.parse(course.getOfflineTime());
                queryCourse.setOfflineTime(offlineDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryCourse;
    }

    private String toLineTypeString(CourseInfoDto info) {
        Date now = new Date();
        if (now.after(info.getOfflineTime())) {
            return "已下架";
        } else if (now.after(info.getOnlineTime()) && now.before(info.getOfflineTime())) {
            return "已上架";
        } else if (now.before(info.getOnlineTime())) {
            return "未上架";
        } else {
            return "";
        }
    }
}