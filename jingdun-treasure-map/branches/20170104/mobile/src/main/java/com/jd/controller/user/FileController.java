package com.jd.controller.user;

import com.jd.constant.BusinessType;
import com.jd.core.ensure.Ensure;
import com.jd.entity.user.Evaluation;
import com.jd.entity.user.Picture;
import com.jd.face.JsonResult;
import com.jd.request.CommonRequest;
import com.jd.request.EvaluationRequest;
import com.jd.utils.ArrayUtils;
import com.jd.utils.FileUtil;
import com.jd.utils.QiniuUtils;
import com.jd.utils.StringUtil;
import com.qiniu.api.auth.AuthException;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Ellen.
 * @date 2017/1/3.
 * @since 1.0.
 * com.jd.controller.user .by jingdun.tech.
 */
@Controller
@RequestMapping("/wap/file")
public class FileController {

    /**
     * 文件上傳TOKEN 獲取
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/upload-token", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult uploadToken() {
        try {
            String token = QiniuUtils.getUploadTocken(QiniuUtils.QINIU_BUCKET);
            Ensure.that(token).isNotNull("");
            return new JsonResult(token);
        } catch (AuthException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonResult();
    }

    /**
     * 图片文件上傳
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/image/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadImage(MultipartFile file) {
        try {
            Ensure.that(file).isNotNull("60000");
            String path = QiniuUtils.uploadFile(file.getInputStream(), QiniuUtils.QINIU_BUCKET);
            Ensure.that(path).isNotNull("60001");
            return new JsonResult(path);
        } catch (IOException e) {
            Ensure.that(e).isNotNull("60002");
            e.printStackTrace();
        }
        return new JsonResult();
    }
}
