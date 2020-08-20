package org.phoneix.Teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.phoneix.Teacher.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.phoneix.Teacher.entity.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Phoneix
 * @since 2020-08-12
 */
public interface EduTeacherService extends IService<EduTeacher> {
    /**
     *  根据多个条件查询讲师列表
     * @param pageParam
     * @param teacherQuery
     */
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);
}

