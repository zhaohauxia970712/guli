package org.phoneix.Teacher.service;

import org.phoneix.Teacher.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import org.phoneix.Teacher.entity.vo.CourseDesc;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Phoneix
 * @since 2020-08-14
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseDesc(CourseDesc courseDesc);
}
