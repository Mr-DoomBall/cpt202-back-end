package com.blog.controller;

import com.blog.util.ApiModel;
import com.blog.util.SignUtil;
import com.blog.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传控制器
 */
@RestController
public class UploadController extends BaseController {

    private static final Logger log = LogManager.getLogger(UploadController.class);

    /**
     * 上传文件
     */
    @PostMapping("/fileUpload")
    public ResponseEntity<ApiModel> fileUpload(HttpServletRequest request) throws IOException {
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> files = multipartHttpServletRequest.getFileMap();

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期文件夹格式
            String dateDir = df.format(new Date()) + "/";// 日期目录

            String attachment = "";// 文件访问路径,多个以","分隔
            for (MultipartFile file : files.values()) {
                if (file.getSize() != 0) {
                    int begin = file.getOriginalFilename().lastIndexOf(".") + 1;
                    String originalFilename = file.getOriginalFilename();
                    int end = originalFilename.length();
                    String fileType = originalFilename.substring(begin, end);// 文件后缀名
                    // 创建目录
                    File dir = new File(fileDir + dateDir);
                    if (!dir.exists()) {
                        if (!dir.mkdirs()) {
                            return ApiModel.fail("文件目录创建失败");
                        }
                    }
                    // 生成文件名
                    String fileName = SignUtil.MD5(System.currentTimeMillis() + StringUtil.getRandomNum(3)) + "." + fileType;
                    attachment = dateDir.concat(fileName);// 文件访问路径
                    File targetFile = new File(fileDir + dateDir + fileName);
                    if (!targetFile.exists()) {
                        if (targetFile.createNewFile()) {
                            file.transferTo(targetFile);
                        } else {
                            return ApiModel.fail("文件上传失败");
                        }
                    } else {
                        return ApiModel.fail("文件名已存在");
                    }
                } else {
                    return ApiModel.fail("文件不能为空");
                }
            }
            if (StringUtils.isEmpty(attachment)) {
                return ApiModel.fail("文件不能为空");
            }
            log.info("上传文件：" + viewPath + attachment);
            Map<String, Object> map = new HashMap<>();
            map.put("url", viewPath + attachment);
            return ApiModel.success(map);
        } catch (Exception e) {
            log.error("上传文件异常", e);
            throw e;
        }
    }

}
