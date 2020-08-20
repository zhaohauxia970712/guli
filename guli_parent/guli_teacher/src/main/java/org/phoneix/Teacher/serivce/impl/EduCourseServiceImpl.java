package org.phoneix.Teacher.service.impl;

import org.phoneix.Teacher.entity.EduCourse;
import org.phoneix.Teacher.entity.vo.CourseDesc;
import org.phoneix.Teacher.mapper.EduCourseMapper;
import org.phoneix.Teacher.service.EduCourseDescriptionService;
import org.phoneix.Teacher.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.phoneix.guli.common.EduException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Phoneix
 * @since 2020-08-14
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;


    @Override
    public String saveCourseDesc(CourseDesc courseDesc) {
        //1、添加课程
        int insert = baseMapper.insert(courseDesc.getEduCourse());
        if(insert <=0){
            throw new EduException(20001,"添加课程失败");
        }
        //2、获得课程id
        String courseId = courseDesc.getEduCourse().getId();
        //3、添加课程描述
        courseDesc.getEduCourseDescription().setId(courseId);
        //4、保存课程描述
        boolean save = eduCourseDescriptionService.save(courseDesc.getEduCourseDescription());
        if(!save){
            throw new EduException(20001,"添加描述失败");
        }
        return courseId;
    }
}
