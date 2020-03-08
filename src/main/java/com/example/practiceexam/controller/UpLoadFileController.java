package com.example.practiceexam.controller;

import com.example.common.alioss.AliOssUtil;
import com.example.common.util.FileUtil;
import com.example.common.util.IdGeneratorUtils;
import com.example.common.vo.MessageVo;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author ShiQing_Chen  2020/3/8  22:32
 **/
@Controller
public class UpLoadFileController {
    private static final Logger logger = LoggerFactory.getLogger(UpLoadFileController.class);

    /**
     * 文件上传接口
     */
    @RequestMapping(value = "/imageUpload/image", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public MessageVo test1(HttpServletResponse response, @RequestParam("upfile") MultipartFile uploadFile) {
        MessageVo resultVo = this.handleFile(uploadFile, "practice-exam/image");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return resultVo;
    }

    /**
     * 文件上传接口
     */
    @RequestMapping(value = "/fileUpload/file", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public MessageVo test2(HttpServletResponse response, @RequestParam("upfile") MultipartFile uploadFile) {
        MessageVo resultVo = this.handleFile(uploadFile, "practice-exam/file");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return resultVo;
    }

    /**
     * 文件上传统一处理
     * @param uploadFile 文件
     * @param folderName 路径
     * @return 结果
     */
    @SuppressWarnings("Duplicates")
    private MessageVo handleFile(MultipartFile uploadFile, String folderName) {
        Map<String, String> map = Maps.newHashMap();
        if (uploadFile == null || uploadFile.isEmpty()) {
            return MessageVo.fail("获取文件失败！");
        }
        String uploadFileName = uploadFile.getOriginalFilename();
        map.put("fileName", uploadFileName);
        try {
            //得到上传没后缀文件名字
            String ext = FileUtil.getFileExt(uploadFileName);
            // 根据随机id生成文件的新名字
            String objectName = IdGeneratorUtils.getNewId() + ext;
            String temp = System.getProperty("user.dir");
            File file = new File(temp + File.separator + objectName);
            uploadFile.transferTo(file);

            String fileUrl = AliOssUtil.putObject(file, objectName, folderName);
            boolean del = file.delete();
            if (!del) {
                logger.warn("临时文件删除失败: {}", file.getAbsolutePath());
            }
            if (StringUtils.isNotBlank(fileUrl)) {
                map.put("url", fileUrl);
                logger.info("######上传文件：{}，地址：{}", uploadFileName, fileUrl);
                return MessageVo.success("文件上传成功！", map);
            }
        } catch (IOException e) {
            logger.error("上传文件错误：", e);
        }
        return MessageVo.fail("文件上传失败！");
    }
}
