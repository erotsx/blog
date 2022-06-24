package com.erotsx.blog.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import javax.json.stream.JsonParser;
import java.io.IOException;


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
     * @return 图片信息
     */
    public static JSONObject upload(MultipartFile file) throws IOException {
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
        return JSON.parseObject(responseEntity.getBody()).getJSONObject("data");
    }

    public void setUID(String UID) {
        ImgBedUtils.UID = UID;
    }

    public void setToken(String Token) {
        ImgBedUtils.Token = Token;
    }

}
