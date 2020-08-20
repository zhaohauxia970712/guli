package org.phoneix.Teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.velocity.runtime.directive.Foreach;
import org.phoneix.Teacher.entity.EduSubject;
import org.phoneix.Teacher.entity.vo.OneSubject;
import org.phoneix.Teacher.entity.vo.TwoSubject;
import org.phoneix.Teacher.mapper.EduSubjectMapper;
import org.phoneix.Teacher.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Phoneix
 * @since 2020-08-13
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public List<String> importExcel(MultipartFile file) {
        List<String> meg = new ArrayList<>();
        try {
            //1、获取文件流
            InputStream is = file.getInputStream();
            //2、根据文件流创建一个workbook
            Workbook workBook = new HSSFWorkbook(is);
            //3、根据sheet,getsheetA()
            Sheet sheet = workBook.getSheetAt(0);
            //4、根据sheet获取行数
            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum <= 1){
                meg.add("请填写数据");
                return meg;
            }
            //5、遍历行
            for(int rowNum = 1;rowNum<lastRowNum;rowNum++){
                Row row = sheet.getRow(rowNum);
                //6、获取每一行的列
                Cell cell = row.getCell(0);
                if(cell ==null){
                    meg.add("第" +rowNum + "行第1列为空");
                    return meg;
                }
                String cellValue = cell.getStringCellValue();
                if(StringUtils.isEmpty(cellValue)){
                    meg.add("第" +rowNum + "行第1列数据为空");
                }
                //7、判断列是否存在，存在则获取列的数据
                EduSubject eduSubject = this.selectSubjectByName(cellValue);
                String pid = null;
                //8、把这个第一列中的数据（一级分类）保存到数据库中
                if (eduSubject == null) {
                    //9、在保存之前判断此一级分类是否存在，如果存在不再添加，如果不存在就添加
                    EduSubject edusub = new EduSubject();
                    edusub.setTitle(cellValue);
                    edusub.setParentId("0");
                    edusub.setSort(0);
                    baseMapper.insert(edusub);
                    pid = edusub.getId();
                }else{
                    pid = eduSubject.getId();
                }
                //10、再获取每一行的第二列
                Cell cell1 = row.getCell(1);
                //11、获取第二列的数据(二级分类)
                if(cell1 == null){
                    meg.add("第" +rowNum + "行第2列为空");
                    return meg;
                }
                String stringCellValue = cell1.getStringCellValue();
                if(StringUtils.isEmpty(stringCellValue)){
                    meg.add("第" +rowNum + "行第2列数据为空");
                    return meg;
                }
                //12、判断一级分类中是否存在此二级分类
                EduSubject eduSub2 = this.selectSubjectByNameAndPid(stringCellValue, pid);
                if(eduSub2 == null){
                    EduSubject edusub = new EduSubject();
                    edusub.setTitle(stringCellValue);
                    edusub.setParentId(pid);
                    edusub.setSort(0);
                    baseMapper.insert(edusub);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return meg;
    }

    @Override
    public EduSubject selectSubjectByName(String cellValue) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",cellValue);
        wrapper.eq("parent_id","0");
        EduSubject subject = baseMapper.selectOne(wrapper);
        return subject;
    }

    @Override
    public EduSubject selectSubjectByNameAndPid(String stringCellValue,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",stringCellValue);
        wrapper.eq("parent_id",pid);
        EduSubject subject = baseMapper.selectOne(wrapper);
        return subject;
    }

    @Override
    public List<OneSubject> getTree() {
        //1、创建一个集合来存放OneSubject
        List<OneSubject> oneSubjectList = new ArrayList<>();
        //2、获取一级分类的列表
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapper);
        //3、遍历一级分类的列表
        for (EduSubject subject:oneSubjects) {
            //4、一级分类的数据复制到OneSubject
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(subject,oneSubject);
            //5、根据每个一级分类获取二级分类的列表
            QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("parent_id",oneSubject.getId());
            List<EduSubject> eduSubjects = baseMapper.selectList(wrapper2);
            //6、遍历二级分类列表
            for(EduSubject su:eduSubjects){
                //7、把二级分类对象复制到TwoSubject
                TwoSubject twoSubject = new TwoSubject();
                BeanUtils.copyProperties(su,twoSubject);
                //8、把TwoSubject添加到OneSubject集合的children中
                oneSubject.getChildren().add(twoSubject);
            }
            //9、把OneSubject添加到集合中
            oneSubjectList.add(oneSubject);
        }



        return oneSubjectList;
    }

    @Override
    public boolean saveLevelOne(EduSubject subject) {
        EduSubject eduSubject = this.selectSubjectByName(subject.getTitle());

        if(eduSubject == null){
            return super.save(subject);
        }

        return false;
    }

    @Override
    public boolean deleteById(String id) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<EduSubject> eduSubjects = baseMapper.selectList(wrapper);
        if (eduSubjects.size() != 0) {
            return false;
        }
        int i = baseMapper.deleteById(id);

        return i == 1;
    }

    @Override
    public boolean selectTwoLevel(EduSubject subjcet) {
        //首先判断二级学科是否存在
        EduSubject eduSubject = this.selectSubjectByNameAndPid(subjcet.getTitle(), subjcet.getParentId());
        if(eduSubject == null) {
            return this.save(subjcet);
        }else{
            return false;
        }
    }
}
