package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/gif","image/jpg","image/png");

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains(contentType)) {
            LOGGER.info("文件类型不合法:{}", originalFilename);
            return null;
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                LOGGER.info("文件内容不合法:{}", originalFilename);
                return null;
            }

            //file.transferTo(new File("D:\\leyou\\images" + originalFilename));
            //保存到服务器
            String fileExtension = StringUtils.substringAfterLast(originalFilename, ".");
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), fileExtension, null);

            return "http://image.leyou.com/" + storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.info("服务器内部错误：{}", originalFilename);
            e.printStackTrace();
        }
        return null;
    }
}
