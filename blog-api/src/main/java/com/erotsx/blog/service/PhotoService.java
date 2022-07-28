package com.erotsx.blog.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoService {

    /**
     * 上传单个图片，图片不属于任何相册
     *
     * @param file 图片
     * @return 图片地址
     * @throws IOException IOException
     */
    String upload(MultipartFile file) throws IOException;

    /**
     * 批量删除图片
     *
     * @param photoIdList 图片Id列表
     */
    void delete(List<Long> photoIdList);
}
