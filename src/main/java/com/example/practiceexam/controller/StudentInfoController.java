package com.example.practiceexam.controller;

import com.example.common.cache.SharedUser;
import com.example.common.util.BindingResultUtils;
import com.example.common.util.ExcelUtil;
import com.example.common.vo.MessageVo;
import com.example.practiceexam.form.AddStudentForm;
import com.example.practiceexam.form.UpdateStudentForm;
import com.example.practiceexam.param.SearchStudentParam;
import com.example.practiceexam.service.StudentInfoService;
import com.example.practiceexam.vo.AddStudentVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/5  18:06
 **/
@Controller
public class StudentInfoController extends BaseExcelController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentInfoController.class);

    private final StudentInfoService studentInfoService;

    @Autowired
    public StudentInfoController(StudentInfoService studentInfoService) {
        this.studentInfoService = studentInfoService;
    }


    /**
     * 添加学生
     * @param sharedUser
     * @param studentForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/student/add", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo add(SharedUser sharedUser, @RequestBody @Valid AddStudentForm studentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return studentInfoService.add(sharedUser, studentForm);
    }

    /**
     * 修改学生信息
     * @param studentForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/student/update", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo update(SharedUser sharedUser, @RequestBody @Valid UpdateStudentForm studentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return studentInfoService.update(sharedUser, studentForm);
    }

    /**
     * 根据id删除学生
     * @param studentId 学生ID
     * @return
     */
    @RequestMapping(value = "/student/delById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo delById(Long studentId) {
        if (studentId == null) {
            return MessageVo.fail("缺少学生ID参数！");
        }
        return studentInfoService.delById(studentId);
    }

    /**
     * 根据id获取学生信息
     * @param studentId 学生ID
     * @return
     */
    @RequestMapping(value = "/student/getById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo getById(Long studentId) {
        if (studentId == null) {
            return MessageVo.fail("缺少学生ID参数！");
        }
        return studentInfoService.getById(studentId);
    }

    /**
     * 添加
     * 校验学号
     * @param studentNumber
     * @return
     */
    @RequestMapping(value = "/student/checkStudentNumber", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo checkStudentNumber(String studentNumber) {
        if (StringUtils.isBlank(studentNumber)) {
            return MessageVo.fail("校验失败，缺少学号参数！");
        }
        return studentInfoService.checkStudentNumber(studentNumber);
    }

    /**
     * 修改
     * 校验学号
     * @param studentId
     * @param studentNumber
     * @return
     */
    @RequestMapping(value = "/student/checkStudentNumberById", method = RequestMethod.GET)
    @ResponseBody
    public MessageVo checkStudentNumberById(Long studentId, String studentNumber) {
        if (studentId == null || StringUtils.isBlank(studentNumber)) {
            return MessageVo.fail("校验失败，缺少学生ID或学号参数！");
        }
        return studentInfoService.checkStudentNumberById(studentId, studentNumber);
    }

    /**
     * 分页查询
     * @param studentParam
     * @return
     */
    @RequestMapping(value = "/student/getListByPage", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo getListByPage(@RequestBody @Valid SearchStudentParam studentParam, BindingResult bindingResult) {
        if (studentParam == null) {
            return MessageVo.fail("获取学生数据失败！");
        }
        if (bindingResult.hasErrors()) {
            return MessageVo.fail(BindingResultUtils.getErrorString(bindingResult));
        }
        return studentInfoService.getListByPage(studentParam);
    }

    /**
     * 下载用户批量禁用模板
     * @param response
     */
    @RequestMapping(value = "/student/downloadImportSomeUserDemo", method = RequestMethod.GET)
    public void downloadDisableUserExcelDemo(HttpServletResponse response) {
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            List<String> titles = Lists.newArrayList("学号", "姓名", "性别");
            this.createExcel("学生批量导入模板", 0, workbook, titles, Lists.newArrayList());
            HSSFSheet sheet = workbook.getSheet("学生批量导入模板");
            // 用户类型下拉选项
            CellRangeAddressList userType = new CellRangeAddressList(1, 200, 2, 2);
            String[] arrType = new String[2];
            arrType[0] = "男";
            arrType[1] = "女";
            DVConstraint constraint = DVConstraint.createExplicitListConstraint(arrType);
            HSSFDataValidation dataValidate = new HSSFDataValidation(userType, constraint);
            dataValidate.setShowErrorBox(false);
            sheet.addValidationData(dataValidate);
            workbook.write(generateResponseExcel("学生批量导入模板", response));
        } catch (Exception e) {
            LOGGER.error("用户批量禁用模板生成失败！", e);
        }
    }

    /**
     * 批量添加用户导入excel，解析数据返回到前台
     * @param uploadFile
     * @return
     */
    @RequestMapping(value = "//student/importSomeUser", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo importExcelSomeUser(@RequestParam("upfile") MultipartFile uploadFile, @RequestParam("classId") Long classId, SharedUser sharedUser) throws Exception {
        if (sharedUser == null) {
            return MessageVo.fail("请登录后重试！");
        }
        if (classId == null) {
            return MessageVo.fail("缺少班级ID参数！");
        }
        if (uploadFile == null || uploadFile.isEmpty()) {
            return MessageVo.fail("找不到文件内容！");
        }
        try (InputStream is = uploadFile.getInputStream(); Workbook book = ExcelUtil.getWorkbook(is)) {
            if (book != null) {
                if (book.getNumberOfSheets() < 1) {
                    return MessageVo.fail("导入sheet页格式有误");
                }
                Table<Integer, Integer, String> dataTable = ExcelUtil.getFirstSheetData(book);
                if (dataTable.isEmpty()) {
                    return MessageVo.fail("excel中没有数据!请把数据放在第一个工作表的位置！");
                }
                Map<Integer, String> headers = dataTable.row(0);
                Map<String, Integer> headerIndex = Maps.newHashMap();
                for (Map.Entry<Integer, String> entry : headers.entrySet()) {
                    headerIndex.put(entry.getValue().trim(), entry.getKey());
                }
                //检查必须字段-学号
                if (!headerIndex.containsKey("学号")) {
                    return MessageVo.fail("导入的文件中没有学号列");
                }
                //检查必须字段-姓名
                if (!headerIndex.containsKey("姓名")) {
                    return MessageVo.fail("导入的文件中没有姓名列");
                }
                //检查必须字段-性别
                if (!headerIndex.containsKey("性别")) {
                    return MessageVo.fail("导入的文件中没有性别列");
                }

                //数据查重
                int studentNumberIndex = headerIndex.get("学号");
                String errorMsg = ExcelUtil.checkUniqueColumn(dataTable, studentNumberIndex, "学号");
                if (StringUtils.isNotBlank(errorMsg)) {
                    return MessageVo.fail(errorMsg);
                }
                //行数
                int rowSize = dataTable.rowKeySet().size();
                int nickNameIndex = headerIndex.get("姓名");
                int genderIndex = headerIndex.get("性别");
                Map<Integer, String> studentNumberColumn = dataTable.column(studentNumberIndex);
                Map<Integer, String> nickNameColumn = dataTable.column(nickNameIndex);
                Map<Integer, String> genderColumn = dataTable.column(genderIndex);
                // 检查必填列数据是否为空
                for (int i = 1; i < rowSize; i++) {
                    String studentNumber = studentNumberColumn.get(i);
                    String nickName = nickNameColumn.get(i);
                    String gender = genderColumn.get(i);
                    if (StringUtils.isBlank(studentNumber)) {
                        return MessageVo.fail("第" + i + "行的学号数据为空！");
                    } else if (StringUtils.isBlank(nickName)) {
                        return MessageVo.fail("第" + i + "行的姓名数据为空！");
                    } else if (StringUtils.isBlank(gender)) {
                        return MessageVo.fail("第" + i + "行的性别数据为空！");
                    } else if (studentNumber.length() != 8) {
                        return MessageVo.fail("第" + i + "行的学号长度不为8位！");
                    }
                }

                List<AddStudentVo> studentVoList = Lists.newArrayList();
                for (int i = 1; i < rowSize; i++) {
                    Map<Integer, String> rowData = dataTable.row(i);
                    String studentNumber = ExcelUtil.getByHeaderKey(rowData, headerIndex, "学号");
                    String nickName = ExcelUtil.getByHeaderKey(rowData, headerIndex, "姓名");
                    String gender = ExcelUtil.getByHeaderKey(rowData, headerIndex, "性别");

                    AddStudentVo addStudentVo = new AddStudentVo();
                    addStudentVo.setStudentNumber(studentNumber);
                    addStudentVo.setStudentName(nickName);
                    addStudentVo.setClassId(classId);
                    if (StringUtils.isNotBlank(gender)) {
                        if (gender.equals("男")) {
                            addStudentVo.setGender(1);
                        } else if (gender.equals("女")) {
                            addStudentVo.setGender(2);
                        }
                    }
                    studentVoList.add(addStudentVo);
                }
                // 导入
                if (!CollectionUtils.isEmpty(studentVoList)) {
                    return studentInfoService.addSome(sharedUser, studentVoList);
                }
                return MessageVo.fail("为获取到可导入的学生数据！");
            }
        } catch (IOException e) {
            LOGGER.error("####导入用户数据失败", e);
            return MessageVo.fail("导入用户数据失败!");
        }
        return MessageVo.fail("导入用户数据失败!");
    }

}
