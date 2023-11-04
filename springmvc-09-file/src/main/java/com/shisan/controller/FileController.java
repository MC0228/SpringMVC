package com.shisan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * @Author:shisan
 * @Date:2023/11/4 14:53
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @RequestMapping("/goUpload")
    public String upload() {
        return "upload";
    }

    @RequestMapping("/upload")
    public String upload(String name, List<MultipartFile> files, HttpServletRequest request) {
        // 判断上传文件是否存在
        if (!files.isEmpty() && files.size() > 0) {
            // 循环输出上传的文件
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String dirPath = request.getServletContext().getRealPath("/upload/");
                File filePath = new File(dirPath);

                if (!filePath.exists()) {
                    filePath.mkdirs();
                }

                // 使用UUID重新命名上传的文件名称（上传人_原始文件名名称）
                String newFileName = name + "_" + UUID.randomUUID() + "_" + originalFilename;
                // 使用MultipartFile完成文件上传到指定位置
                try {
                    file.transferTo(new File(dirPath + newFileName));
                    System.out.println(dirPath + newFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "error";
                }
            }
            return "success";
        }
        return "error";

    }

    @RequestMapping("/goDownload")
    public String download() {
        return "download";
    }

    @RequestMapping("/download")
    public String downloads(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //要下载的图片地址
        String path = request.getServletContext().getRealPath("/upload");

        String fileName = "01.jpg";
        //1、设置response 响应头
        response.reset(); //设置页面不缓存,清空buffer
        response.setCharacterEncoding("UTF-8"); //字符编码
        response.setContentType("multipart/form-data"); //二进制传输数据
        //设置响应头
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        File file = new File(path, fileName);
        //2、 读取文件--输入流
        InputStream input = new FileInputStream(file);
        //3、 写出文件--输出流
        OutputStream out = response.getOutputStream();
        byte[] buff = new byte[1024];
        int index = 0;
        //4、执行 写出操作
        while ((index = input.read(buff)) != -1) {
            out.write(buff, 0, index);
            out.flush();
        }
        out.close();
        input.close();
        return "success";
    }


}
