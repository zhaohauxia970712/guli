package org.phoneix.guli.oss.service.impl;

import com.aliyun.oss.OSSClient;
import org.joda.time.DateTime;
import org.phoneix.guli.oss.service.FileService;
import org.phoneix.guli.oss.utils.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file) {

        OSSClient ossClient = null;
        String url = null;
        try {
            //创建OSS连接客户端
            ossClient = new OSSClient(
                    ConstantPropertiesUtil.END_POINT,
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //获取文件名
            String filename = file.getOriginalFilename();
            //获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf("."));
            //生成新的文件名并加上后缀
            String newName = UUID.randomUUID().toString()+ext;
            //生成日期目录
            String dataPath = new DateTime().toString("yyyy/MM/dd");
            //创建完整上传路径
            String urlPath = ConstantPropertiesUtil.FILE_HOST + "/" + dataPath + "/" +newName;

            InputStream inputStream = file.getInputStream();
            ossClient.putObject(ConstantPropertiesUtil.BUCKET_NAME, urlPath, inputStream);
            url = "https://" + ConstantPropertiesUtil.BUCKET_NAME+"." + ConstantPropertiesUtil.END_POINT + "/" +urlPath ;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        return url;
    }
}
