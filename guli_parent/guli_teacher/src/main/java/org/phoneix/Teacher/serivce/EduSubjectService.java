package org.phoneix.Teacher.service;

import org.phoneix.Teacher.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.phoneix.Teacher.entity.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Phoneix
 * @since 2020-08-13
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<String> importExcel(MultipartFile file);

    EduSubject selectSubjectByName(String cellValue);

    EduSubject selectSubjectByNameAndPid(String stringCellValue,String pid);

    List<OneSubject> getTree();

    boolean saveLevelOne(EduSubject subject);

    boolean deleteById(String id);

    boolean selectTwoLevel(EduSubject subjcet);
}
