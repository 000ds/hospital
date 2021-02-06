package com.shop.Servlet;

import com.shop.Utils.IdGenertor;
import com.shop.Utils.OtherUtils;
import com.shop.Utils.UserInfoDao;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UploadImageServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        uploadImage(request, response);
//        response.setContentType("multipart/form-data; charset=utf-8");
//        JSONObject job= OtherUtils.getJson(request);

//        UserInfoDao uinfo = new UserInfoDao();
//        String result=uinfo.FindApplication();
//        PrintWriter out = response.getWriter();
//        out.print(result);
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//		接收图片
//        uploadImage(request, response);
//		接收图片与用户Id
//		changeUserImage(request, response);
    }

    // 上传图片文件
    private void uploadImage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        try{
            DiskFileItemFactory dff = new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload(dff);
            List<FileItem> items = sfu.parseRequest(request);
            // 获取上传字段
            FileItem fileItem = items.get(0);
            // 更改文件名为唯一的
            String filename = fileItem.getName();
            if (filename != null) {
                filename = IdGenertor.generateGUID() + "." + FilenameUtils.getExtension(filename);
            }
            // 生成存储路径
            String storeDirectory = getServletContext().getRealPath("/files/images");
            File file = new File(storeDirectory);
            if (!file.exists()) {
                file.mkdir();
            }
            String path = genericPath(filename, storeDirectory);
            // 处理文件的上传
            try {
                fileItem.write(new File(storeDirectory + path, filename));

                String filePath = "/files/images" + path + "/" + filename;
                message = filePath;
            } catch (Exception e) {
                message = "上传图片失败";
            }
        } catch (Exception e) {
            message = "上传图片失败";
        } finally {
            response.getWriter().write(message);
        }
    }///files/images/14/13/1X4LOZ8G6MJ1PKBVO8QV3MKI8N2BJJQ9.pdf/files/images/3/13/319P5G13VTNLUDG9J90MZ38MBTPJQEUP.docx

    // 修改用户的图片
    private void changeUserImage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        try{
            DiskFileItemFactory dff = new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload(dff);
            List<FileItem> items = sfu.parseRequest(request);
            for(FileItem item:items){
                if(item.isFormField()){
                    //普通表单
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();
                    System.out.println("name="+fieldName + ", value="+ fieldValue);
                } else {// 获取上传字段
                    // 更改文件名为唯一的
                    String filename = item.getName();
                    if (filename != null) {
                        filename = IdGenertor.generateGUID() + "." + FilenameUtils.getExtension(filename);
                    }
                    // 生成存储路径
                    String storeDirectory = getServletContext().getRealPath("/files/images");
                    File file = new File(storeDirectory);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    String path = genericPath(filename, storeDirectory);
                    // 处理文件的上传
                    try {
                        item.write(new File(storeDirectory + path, filename));

                        String filePath = "/files/images" + path + "/" + filename;
                        System.out.println("filePath="+filePath);
                        message = filePath;
                    } catch (Exception e) {
                        message = "上传图片失败";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "上传图片失败";
        } finally {
            response.getWriter().write(message);
        }
    }

    //计算文件的存放目录
    private String genericPath(String filename, String storeDirectory) {
        int hashCode = filename.hashCode();
        int dir1 = hashCode&0xf;
        int dir2 = (hashCode&0xf0)>>4;

        String dir = "/"+dir1+"/"+dir2;

        File file = new File(storeDirectory,dir);
        if(!file.exists()){
            file.mkdirs();
        }
        return dir;
    }



}
