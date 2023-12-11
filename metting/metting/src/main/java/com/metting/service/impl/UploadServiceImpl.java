package com.metting.service.impl;

import com.metting.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {


    /**
     * Servlet请求域对象
     */
    @Resource
    private HttpServletRequest request;


    /***
     * 上传图片存储图片
     * @param files 前端传来的文件列表
     * @param path 路径
     * @return url地址列表
     * @throws IOException
     */
    public static List<String> upload(List<MultipartFile> files, String path) throws IOException {
        List<String> msgs = new ArrayList<>();//用于存放图片的路径
        if(files.size() < 1){//如果前端提交的列表是空的
            msgs.add("file_empty");//列表加入文件的为空的提示，并返回
            return msgs;
        }
        for(MultipartFile file : files){//遍历文件名称
            String oldFileName = file.getOriginalFilename();//获取旧文件名
            //断言，用于调试，如果表达式即oldFileName不为空的话，程序继续执行，否则会抛出异常
            assert oldFileName != null;
            //判断旧文件名中是否有.字符串,如果有的话就从最后一个.截取到最后一个字符，没有.字符就返回取null
            String type = oldFileName.contains(".") ? oldFileName.substring(oldFileName.lastIndexOf(".")) : null;
            //以uuid重新命名避免重复，拼接文件路径，方便前端接收
            String filePath = path + UUID.randomUUID() + type;
            System.out.println(filePath);//打印路径查看
            File filesPath = new File(path);//新建文件操作类
            if (!filesPath.exists()) {//如果是不存在的话
                filesPath.mkdirs();//创建文件路径
            }
            BufferedOutputStream out = null;//字节缓冲流
            try{
                //字节输入流，路径是之前拼凑的路径
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                out = new BufferedOutputStream(fileOutputStream);//新建缓冲流对象
                out.write(file.getBytes());//将文件流写入路径之中
                msgs.add(filePath);//将路径增加进列表中并返回
            } catch (FileNotFoundException e) {//文件为空异常抛出
                throw new RuntimeException(e);
            } catch (IOException e) {//运行时异常抛出
                throw new RuntimeException(e);
            }finally {
                if(out != null){
                    out.flush();//把内部缓冲区的数据，刷新到文件中
                    out.close();//释放资源，关闭流
                }
            }
        }
        return msgs;//返回列表
    }

    /***
     * 头像上传
     * @param files
     * @return
     */
    @Override
    public List<String> uploadAvatar(List<MultipartFile> files) {
        //新建一个存放图片url的String列表
        List<String> imagePath;
        List<String> imageUrls = new ArrayList<>();//用于存放url地址
        String path = "F:\\java web\\project\\";//图片存放的本地地址
        //调用图片上传方法，也就是upload方法，用于上传图片
        try{
            //传入参数files，path，然后返回新的文件名列表
            imagePath = this.upload(files,path);
            System.out.println("图片上传路径："+imagePath);//打印图片看看
            for(String img : imagePath){//遍历图片路径
                //按‘/’截取
                String[] split = img.split("/");
                //取出数组中的最后一个元素就是文件名，数组下标从0开始，最后一个就数组长度-1
                String fileName = split[split.length - 1];
                //开始拼凑基础的url路径，request.getScheme=http使用的协议名;
                //request.getServerName() = localhost 服务器名称;
                //request.getServerPort() = 8909,端口号
                //request.getContextPath() = 返回请求中对应context的部分，如果此context是默认context，则返回”“。
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                String httpUrl = basePath + "/upload/" + fileName;//这里的"/upload/"是配置的虚拟路径，拼凑出一个可以访问的url
                System.out.println("完整的url路径："+httpUrl);//打印出来看看，点击一下是否能访问
                imageUrls.add(httpUrl);//将完整的url路径添加进列表中

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(imagePath.get(0).equals("file_empty")){//如果imagePath列表里面的第一个元素是"file_empty"
            return null;//标识列表是空，那就返回空
        }
        System.out.println("imagePath里面的东西是："+imagePath);
        System.out.println("imageUrls里面的东西是："+imageUrls);
        return imageUrls;
    }


}
