package com.erotsx.blog.utils;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "img-bed")
public class ImgBedUtils {

    private static final String IMG_URL_API = "https://www.imgurl.org/api/v2/upload";
    private static String UID;
    private static String Token;


    /**
     * 上传图片到图床
     *
     * @param file 要上传的图片
     * @return 图片地址
     */
    public static String upload(MultipartFile file) throws IOException {
        ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }

            @Override
            public long contentLength() {
                return file.getSize();
            }
        };
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", fileAsResource);
        map.add("uid", UID);
        map.add("token", Token);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(IMG_URL_API, map, String.class);
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    public void setUID(String UID) {
        ImgBedUtils.UID = UID;
    }

    public void setToken(String Token) {
        ImgBedUtils.Token = Token;
    }

}
