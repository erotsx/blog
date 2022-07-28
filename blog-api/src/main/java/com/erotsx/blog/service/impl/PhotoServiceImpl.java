package com.erotsx.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.erotsx.blog.dao.PhotoMapper;
import com.erotsx.blog.entity.Photo;
import com.erotsx.blog.service.PhotoService;
import com.erotsx.blog.utils.ImgBedUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Resource
    private PhotoMapper photoMapper;

    /**
     * 上传单个图片，图片不属于任何相册
     *
     * @param file 图片
     * @return 图片地址
     * @throws IOException IOException
     */
    @Override
    public String upload(MultipartFile file) throws IOException {
        JSONObject data = ImgBedUtils.upload(file);
        System.out.println(data);
        Photo photo = new Photo();
        String url = data.getString("url");
        photo.setName(data.getString("relative_path"));
        photo.setUrl(url);
        photo.setDeleteUrl(data.getString("delete"));
        photoMapper.insert(photo);
        return url;
    }

    /**
     * 批量删除图片
     *
     * @param photoIdList 图片Id列表
     */
    @Override
    public void delete(List<Long> photoIdList) {
        LambdaUpdateWrapper<Photo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Photo::getId, photoIdList).set(Photo::getAlbumId, null);
        photoMapper.update(null, updateWrapper);
    }
}
