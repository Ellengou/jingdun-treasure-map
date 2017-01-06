package com.jd.controller.upload;

import com.jd.core.ensure.Ensure;
import com.jd.face.JsonResult;
import com.jd.utils.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import static com.jd.utils.QiniuUtils.uploadFileSpecBucket;

/**
 * @author Ellen.
 * @date 2017/1/5.
 * @since 1.0.
 * com.jd.controller.upload .by jingdun.tech.
 */
@Controller
public class FileUploadController {


    /**
     * 图片文件上传
     * 目前只是用此方法
     * @param file
     * @return
     */
    @RequestMapping(value = "/image/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadImageFile(MultipartFile file) {
        try {
            String path;
            path = uploadFileSpecBucket(file.getInputStream(), QiniuUtils.QINIU_BUCKET);
            Ensure.that(path).isNotNull("60001");
            return new JsonResult(path);
        } catch (Exception e) {
            e.printStackTrace();
            Ensure.that(e).isNotNull("60001");
        }
        return new JsonResult();
    }


    /**
     * 文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadFile(MultipartFile file) {
        try {
            String path = uploadFileSpecBucket(file.getInputStream(),QiniuUtils.QINIU_BUCKET_FILE);
            Ensure.that(path).isNotNull("60001");
            return new JsonResult(path);
        } catch (Exception e) {
            e.printStackTrace();
            Ensure.that(e).isNotNull("60001");
        }
        return new JsonResult();
    }

    /**
     * 证件类型文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/base/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadIdFile(MultipartFile file) {
        try {
            String path = uploadFileSpecBucket(file.getInputStream(),QiniuUtils.QINIU_BUCKET);
            Ensure.that(path).isNotNull("60001");
            return new JsonResult(path);
        } catch (Exception e) {
            e.printStackTrace();
            Ensure.that(e).isNotNull("60001");
        }
        return new JsonResult();
    }

}
