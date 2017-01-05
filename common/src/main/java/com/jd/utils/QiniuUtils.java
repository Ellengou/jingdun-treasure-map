package com.jd.utils;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.*;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Ellen.
 * @date 2017/1/2.
 * @since 1.0.
 * com.jd.utils .by jingdun.tech.
 */
public abstract class QiniuUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(QiniuUtils.class);

    /**
     * 默认上传空间
     */
    public static String QINIU_BUCKET;
    public static String QINIU_BUCKET_TEST;
    /**
     * 空间默认域名
     */
    public static String QINIU_DOMAIN;
    public static String QINIU_DOMAIN_TEST;

    private static Mac mac;

    private static Auth auth;

    private static UploadManager uploadManager = new UploadManager();

    private static int LIMIT_SIZE = 1000;

    private static String QINIU_MIME = "multipart/form-data";

    static {
        QINIU_BUCKET = "jingdun";
        QINIU_DOMAIN = "ohfpl7rfr.bkt.clouddn.com";
        QINIU_BUCKET_TEST = "test";
        QINIU_DOMAIN_TEST = "oj4wlx1ar.bkt.clouddn.com";
        Config.ACCESS_KEY = "ugPPPibV_KE825Zrf6mXHMKf343kvBRka27CVIHu";
        Config.SECRET_KEY = "Mx9G_48dHOr8E9nI1bZcdzcjcLMdhb8i2L3sv62y";
        mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        auth = Auth.create(mac.accessKey, mac.secretKey);
    }

    /**
     * 获取上传下载认证对象
     *
     * @param bucketName 请确保该bucket已经存在
     * @return
     */
    public static String getUploadTocken(String bucketName) throws AuthException, JSONException {
        PutPolicy putPolicy = new PutPolicy(bucketName);
        return putPolicy.token(mac);
    }

    /**
     * 获取key
     *
     * @param
     * @return
     * @throws AuthException
     * @throws JSONException
     */
    public static String getSecretKeyKey() {
        return mac.secretKey;
    }

    /**
     * 获得授权对象
     *
     * @return
     */
    public static Auth getAuth() {
        return Auth.create(mac.accessKey, mac.secretKey);
    }

    public static boolean upload(String bucket, String key, String file) throws AuthException, JSONException {
        PutPolicy putPolicy = new PutPolicy(bucket);
        String uptoken = putPolicy.token(mac);
        PutExtra extra = new PutExtra();

        PutRet ret = IoApi.putFile(uptoken, key, file, extra);
        return ret.ok();
    }

    public static String download(String domain, String key) throws EncoderException, AuthException {
        String baseUrl = URLUtils.makeBaseUrl(domain, key);
        GetPolicy getPolicy = new GetPolicy();
        String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("from qiniu's url is:" + downloadUrl);
        }
        return downloadUrl;
    }

    public static boolean delete(String bucket, String key) {
        RSClient client = new RSClient(mac);
        return client.delete(bucket, key).ok();
    }

    /**
     * 多个key 一起删除
     *
     * @param keys
     */
    public static boolean batchDelete(String bucket, Set<String> keys) {
        if (keys == null || keys.size() == 0) {
            return false;
        }

        RSClient rs = new RSClient(mac);
        List<EntryPath> entries = new ArrayList<EntryPath>();
        for (String key : keys) {
            EntryPath e = new EntryPath();
            e.bucket = bucket;
            e.key = key;
            entries.add(e);
        }
        BatchCallRet bret = rs.batchDelete(entries);
        return bret.ok();
    }


    /**
     * @param @return
     * @param @throws QiniuException
     * @return String[]
     * @throws
     * @Description: 返回七牛帐号的所有空间
     */
    public static String[] listBucket() throws QiniuException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        return bucketManager.buckets();
    }

    /**
     * @param bucketName 空间名称
     * @param prefix     文件名前缀
     * @param limit      每次迭代的长度限制，最大1000，推荐值 100[即一个批次从七牛拉多少条]
     * @param @return
     * @return List<FileInfo>
     * @throws
     * @Description: 获取指定空间下的文件列表
     */
    public static List<FileInfo> listFileOfBucket(String bucketName, String prefix, int limit) {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        BucketManager.FileListIterator it = bucketManager.createFileListIterator(bucketName, prefix, limit, null);
        List<FileInfo> list = new ArrayList<FileInfo>();
        while (it.hasNext()) {
            FileInfo[] items = it.next();
            if (null != items && items.length > 0) {
                list.addAll(Arrays.asList(items));
            }
        }
        return list;
    }

    /**
     * @param @param  inputStream    待上传文件输入流
     * @param @param  bucketName     空间名称
     * @param @param  key            空间内文件的key
     * @param @param  mimeType       文件的MIME类型，可选参数，不传入会自动判断
     * @param @return
     * @param @throws IOException
     * @return String
     * @throws
     * @Description: 七牛图片上传
     */
    public static String uploadFile(InputStream inputStream, String bucketName, String key, String mimeType) throws IOException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        String token = auth.uploadToken(bucketName);
        byte[] byteData = IOUtils.toByteArray(inputStream);
        Response response = uploadManager.put(byteData, key, token, null, mimeType, false);
        inputStream.close();
        return response.bodyString();
    }

    /**
     * @param @param  inputStream    待上传文件输入流
     * @param @param  bucketName     空间名称
     * @param @param  key            空间内文件的key
     * @param @return
     * @param @throws IOException
     * @return String
     * @throws
     * @Description: 七牛图片上传
     */
    public static String uploadFile(InputStream inputStream, String bucketName, String key) throws IOException {
        String token = auth.uploadToken(bucketName);
        byte[] byteData = IOUtils.toByteArray(inputStream);
        Response response = uploadManager.put(byteData, key, token, null, null, false);
        inputStream.close();
        return response.bodyString();
    }

    /**
     * @param @param  inputStream    待上传文件输入流
     * @param @param  bucketName     空间名称
     * @param @return
     * @param @throws IOException
     * @return String
     * @throws
     * @Description: 七牛图片上传
     */
    public static String uploadFile(InputStream inputStream, String bucketName) throws IOException {
        String token = auth.uploadToken(bucketName);
        byte[] byteData = IOUtils.toByteArray(inputStream);
        Response response = uploadManager.put(byteData, mac.secretKey, token, null, null, false);
        inputStream.close();
        return response.bodyString();
    }

    /**
     * @param @param  inputStream    待上传文件输入流
     * @param @return
     * @param @throws IOException
     * @return String
     * @throws
     * @Description: 七牛图片上传至默认空间
     */
    public static String uploadFile(InputStream inputStream) throws IOException {
        String token;
        token = auth.uploadToken(QINIU_BUCKET);
        byte[] byteData = IOUtils.toByteArray(inputStream);
        Response response = uploadManager.put(byteData, mac.secretKey, token, null, QINIU_MIME, false);
        inputStream.close();
        return response.bodyString();
    }


    /**
     * @param filePath   待上传文件的硬盘路径
     * @param fileName   待上传文件的文件名
     * @param bucketName 空间名称
     * @param key        空间内文件的key
     * @param @return
     * @param @throws    IOException
     * @return String
     * @throws
     * @Description: 七牛图片上传
     */
    public static String uploadFile(String filePath, String fileName, String bucketName, String key) throws IOException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        String token = auth.uploadToken(bucketName);
        InputStream is = new FileInputStream(new File(filePath + fileName));
        byte[] byteData = IOUtils.toByteArray(is);
        Response response = uploadManager.put(byteData, (key == null || "".equals(key)) ? fileName : key, token);
        is.close();
        return response.bodyString();
    }

    /**
     * @param filePath   待上传文件的硬盘路径
     * @param fileName   待上传文件的文件名
     * @param bucketName 空间名称
     * @param @return
     * @param @throws    IOException
     * @return String
     * @Description: 七牛图片上传[若没有指定文件的key, 则默认将fileName参数作为文件的key]
     */
    public static String uploadFile(String filePath, String fileName, String bucketName) throws IOException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        String token = auth.uploadToken(bucketName);
        InputStream is = new FileInputStream(new File(filePath + fileName));
        byte[] byteData = IOUtils.toByteArray(is);
        Response response = uploadManager.put(byteData, fileName, token);
        is.close();
        return response.bodyString();
    }

    /**
     * @param url        网络上一个资源文件的URL
     * @param bucketName 空间名称
     * @param key        空间内文件的key[唯一的]
     * @param @return
     * @return String
     * @throws QiniuException
     * @throws
     * @Description: 提取网络资源并上传到七牛空间里
     */
    public static String fetchToBucket(String url, String bucketName, String key) throws QiniuException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        DefaultPutRet putret = bucketManager.fetch(url, bucketName, key);
        return putret.key;
    }

    /**
     * @param url
     * @param bucketName
     * @param @return
     * @param @throws    QiniuException
     * @return String
     * @throws
     * @Description: 提取网络资源并上传到七牛空间里, 不指定key，则默认使用url作为文件的key
     */
    public static String fetchToBucket(String url, String bucketName) throws QiniuException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        DefaultPutRet putret = bucketManager.fetch(url, bucketName);
        return putret.key;
    }

    /**
     * @param bucket       源空间名称
     * @param key          源空间里文件的key(唯一的)
     * @param targetBucket 目标空间
     * @param targetKey    目标空间里文件的key(唯一的)
     * @return void
     * @throws QiniuException
     * @throws
     * @Description: 七牛空间内文件复制
     */
    public static void copyFile(String bucket, String key, String targetBucket, String targetKey) throws QiniuException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        bucketManager.copy(bucket, key, targetBucket, targetKey);
    }

    /**
     * @param bucket       源空间名称
     * @param key          源空间里文件的key(唯一的)
     * @param targetBucket 目标空间
     * @param targetKey    目标空间里文件的key(唯一的)
     * @param @throws      QiniuException
     * @return void
     * @throws
     * @Description: 七牛空间内文件剪切
     */
    public static void moveFile(String bucket, String key, String targetBucket, String targetKey) throws QiniuException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        bucketManager.move(bucket, key, targetBucket, targetKey);
    }

    /**
     * @param bucket
     * @param key
     * @param targetKey
     * @param @throws   QiniuException
     * @return void
     * @throws
     * @Description: 七牛空间内文件重命名
     */
    public static void renameFile(String bucket, String key, String targetKey) throws QiniuException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        bucketManager.rename(bucket, key, targetKey);
    }

    /**
     * @param bucket  空间名称
     * @param key     空间内文件的key[唯一的]
     * @param @throws QiniuException
     * @return void
     * @throws
     * @Description: 七牛空间内文件删除
     */
    public static void deleteFile(String bucket, String key) throws QiniuException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        bucketManager.delete(bucket, key);
    }

    /**
     * @param @param  bucketName   空间名称
     * @param @param  prefix       文件key的前缀
     * @param @param  limit        批量提取的最大数目
     * @param @return
     * @param @throws QiniuException
     * @return FileInfo[]
     * @throws
     * @Description: 返回指定空间下的所有文件信息
     */
    public static FileInfo[] findFiles(String bucketName, String prefix, int limit) throws QiniuException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        FileListing listing = bucketManager.listFiles(bucketName, prefix, null, limit, null);
        if (listing == null || listing.items == null || listing.items.length <= 0) {
            return null;
        }
        return listing.items;
    }

    /**
     * @param @param  bucketName   空间名称
     * @param @param  prefix       文件key的前缀
     * @param @return
     * @param @throws QiniuException
     * @return FileInfo[]
     * @throws
     * @Description: 返回指定空间下的所有文件信息
     */
    public static FileInfo[] findFiles(String bucketName, String prefix) throws QiniuException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        FileListing listing = bucketManager.listFiles(bucketName, prefix, null, LIMIT_SIZE, null);
        if (listing == null || listing.items == null || listing.items.length <= 0) {
            return null;
        }
        return listing.items;
    }

    /**
     * @param @param  bucketName
     * @param @param  key
     * @param @return
     * @param @throws QiniuException
     * @return FileInfo[]
     * @throws
     * @Description: 返回指定空间下的所有文件信息
     */
    public static FileInfo[] findFiles(String bucketName) throws QiniuException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        FileListing listing = bucketManager.listFiles(bucketName, null, null, LIMIT_SIZE, null);
        if (listing == null || listing.items == null || listing.items.length <= 0) {
            return null;
        }
        return listing.items;
    }

    /**
     * @param @param  bucketName
     * @param @param  key
     * @param @param  limit
     * @param @return
     * @param @throws QiniuException
     * @return FileInfo
     * @throws
     * @Description: 返回指定空间下的某个文件
     */
    public static FileInfo findOneFile(String bucketName, String key, int limit) throws QiniuException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        FileListing listing = bucketManager.listFiles(bucketName, key, null, limit, null);
        if (listing == null || listing.items == null || listing.items.length <= 0) {
            return null;
        }
        return (listing.items)[0];
    }

    /**
     * @param @param  bucketName
     * @param @param  key
     * @param @return
     * @param @throws QiniuException
     * @return FileInfo
     * @throws
     * @Description: 返回指定空间下的某个文件(重载)
     */
    public static FileInfo findOneFile(String bucketName, String key) throws QiniuException {
        Auth auth = Auth.create(mac.accessKey, mac.secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        FileListing listing = bucketManager.listFiles(bucketName, key, null, LIMIT_SIZE, null);
        if (listing == null || listing.items == null || listing.items.length <= 0) {
            return null;
        }
        return (listing.items)[0];
    }


    /**
     * 从 inputstream 中写入七牛
     *
     * @param key     文件名
     * @param content 要写入的内容
     * @return
     * @throws AuthException
     * @throws JSONException
     */
    public static String uploadFile(String key, String content) throws AuthException, JSONException {
        // 读取的时候按的二进制，所以这里要同一
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());

        String uptoken = getUpToken();

        // 可选的上传选项，具体说明请参见使用手册。
        PutExtra extra = new PutExtra();

        // 上传文件
        PutRet ret = IoApi.Put(uptoken, key, inputStream, extra);

        if (ret.ok()) {
            return ret.getResponse();
        } else {
            return null;
        }
    }

    //通过文件路径上传文件
    public static String uploadFile(String localFile) throws Exception {
        File file = new File(localFile);
        return uploadFile(file);
    }

    //通过File上传
    public static String uploadFile(File file) throws Exception {
        String uptoken = getUpToken();

        // 可选的上传选项，具体说明请参见使用手册。
        PutExtra extra = new PutExtra();

        // 上传文件
        PutRet ret = IoApi.putFile(uptoken, file.getName(), file.getAbsolutePath(), extra);

        if (ret.ok()) {
            return getFileUrl(ret.getKey());
        } else {
            return null;
        }
    }


    //获得下载地址
    public static String getDownloadFileUrl(String filename) throws Exception {
        String baseUrl = URLUtils.makeBaseUrl(QINIU_DOMAIN, filename);
        GetPolicy getPolicy = new GetPolicy();
        String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
        return downloadUrl;
    }

    //获得圖片地址
    public static String getFileUrl(String filename) throws Exception {
        String baseUrl = URLUtils.makeBaseUrl(QINIU_DOMAIN, filename);
        return baseUrl;
    }

    //删除文件
    public static void deleteFile(String filename) {
        RSClient client = new RSClient(mac);
        client.delete(QINIU_DOMAIN, filename);
    }

    //获取凭证
    private static String getUpToken() throws AuthException, JSONException {
        PutPolicy putPolicy = new PutPolicy(QINIU_DOMAIN);
        String uptoken = putPolicy.token(mac);
        return uptoken;
    }

    private Mac getMac() {
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        return mac;
    }

    /**
     * @param @param  key
     * @param @return
     * @param @throws QiniuException
     * @return String
     * @throws
     * @Description: 返回七牛空间内指定文件的访问URL
     */
    public static String getFileAccessUrl(String key) throws QiniuException {
        return QINIU_DOMAIN + "/" + key;
    }

    public static void main(String[] args) throws AuthException, JSONException, EncoderException, IOException {
        String file = "/Users/ellengou/Downloads/IMG_0772.JPG";
        FileInputStream inputStream = new FileInputStream(new File(file));

        String ok = uploadFile(inputStream);
        try {
            ok = getDownloadFileUrl("IMG_0772.JPG");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("====================== upload =============== :  " + ok);

//        boolean ok = upload(QINIU_BUCKET, mac.secretKey, file);
//        System.out.println("====================== upload =============== :  " + ok);
//
//
//        String url = download(QINIU_DOMAIN, mac.secretKey);
//        System.out.println("------------------download  -----------------------:     " + url);
//
//
//        String response = uploadFile("/Users/ellengou/Downloads/", "IMG_0769.JPG", QINIU_BUCKET);
//        System.out.println("===================  uploadFile 上傳結果為======== :     " + response);
//
//        String[] buckets = new String[0];
//        buckets = listBucket();
//        System.out.println("######################## listBucket start ###########################");
//        for (String bucket : buckets) {
//            System.out.println(bucket);
//        }
//        System.out.println("######################## listBucket start ###########################");
//        System.out.println();
//        System.out.println("######################## listBucket start ###########################");
//        List<FileInfo> list = listFileOfBucket(QINIU_BUCKET, null, 10000);
//        for (FileInfo fileInfo : list) {
//            System.out.println("key：" + fileInfo.key);
//            System.out.println("hash：" + fileInfo.hash);
//            System.out.println("................");
//        }
//        System.out.println("######################## listBucket end ###########################");

//        copyFile(QINIU_BUCKET,  mac.secretKey, QINIU_BUCKET_TEST, mac.secretKey);

//        renameFile(QINIU_BUCKET, "Mx9G_48dHOr8E9nI1bZcdzcjcLMdhb8i2L3sv62y", "images-test-1.jpg");

//        deleteFile(QINIU_BUCKET, "images-test-2222.jpg");

//        fetchToBucket("http://www.nanrenwo.net/uploads/allimg/121026/14-1210261JJD03.jpg", QINIU_BUCKET, "test2.jpg");
//        fetchToBucket("http://pic1.win4000.com/pic/d/bf/320f1209099.jpg", QINIU_BUCKET_TEST, "test3.jpg");


//        FileInfo[] fileInfos = new FileInfo[0];
//        fileInfos = findFiles(QINIU_BUCKET, "", 1000000);
//
//        System.out.println("..............findFiles  start..................... ");
//        for (FileInfo fileInfo : fileInfos) {
//            System.out.println(fileInfo.key);
//            System.out.println(fileInfo.hash);
//        }
//        System.out.println("..............findFiles  end ..................... ");
    }
}