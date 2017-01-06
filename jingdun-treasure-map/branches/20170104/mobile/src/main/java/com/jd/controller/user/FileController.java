package com.jd.controller.user;

import com.jd.core.ensure.Ensure;
import com.jd.face.JsonResult;
import com.jd.utils.QiniuUtils;
import com.jd.utils.StringUtil;
import com.qiniu.api.auth.AuthException;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Ellen.
 * @date 2017/1/3.
 * @since 1.0.
 * com.jd.controller.user .by jingdun.tech.
 */
@Controller
@RequestMapping("/wap")
public class FileController {

    /**
     * 文件上傳TOKEN 獲取
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/upload/token", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult uploadToken(String bucketName) {
        if (!StringUtil.isNotBlank(bucketName))
            bucketName = QiniuUtils.QINIU_BUCKET;
        String token = QiniuUtils.getUpToken(bucketName);
        Ensure.that(token).isNotNull("60003");
        return new JsonResult(token);
    }

    /**
     * 图片文件上傳
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadImage(MultipartFile file) {
        try {
            Ensure.that(file).isNotNull("60000");
            String path = QiniuUtils.uploadFileSpecBucket(file.getInputStream(), QiniuUtils.QINIU_BUCKET);
            Ensure.that(path).isNotNull("60001");
            return new JsonResult(path);
        } catch (IOException e) {
            Ensure.that(e).isNotNull("60002");
            e.printStackTrace();
        }
        return new JsonResult();
    }
}
