package com.jd.webkits.filter;

import com.jd.core.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SmsSensitiveWord
 * 短信敏感字
 *
 * @author ellen
 * @date 2015/6/17
 */
public class WordFilter {

    static final Logger log = Logger.getLogger(WordFilter.class);
    private final static File wordfilter = new File(WordFilter.class.getClassLoader().getResource("config").getFile() + "/filterword.txt");

    private static long lastModified = 0L;
    private static List<String> words = new ArrayList<String>();

    private static void checkReload() {
        if (wordfilter.lastModified() > lastModified) {
            synchronized (WordFilter.class) {
                try {
                    lastModified = wordfilter.lastModified();
                    LineIterator lines = FileUtils.lineIterator(wordfilter, "utf-8");
                    while (lines.hasNext()) {
                        String line = lines.nextLine();
                        if (StringUtils.isNotBlank(line))
                            words.add(StringUtils.trim(line).toLowerCase());
                    }
                } catch (IOException e) {
                    log.error("加载文件出错！", e);
                }
            }
        }
    }

    /**
     * 检查敏感字内容
     *
     * @param contents
     */
    public static String check(String... contents) {
        if (!wordfilter.exists())
            return null;
        checkReload();
        for (String word : words) {
            for (String content : contents)
                if (content != null && content.indexOf(word) >= 0)
                    return word;
        }
        return null;
    }

    /**
     * 检查字符串是否包含敏感词
     *
     * @param content
     * @return
     */
    public static boolean isContain(String content) {
        if (!wordfilter.exists())
            return false;
        checkReload();
        for (String word : words) {
            if (content != null && content.indexOf(word) >= 0)
                return true;
        }
        return false;
    }

    /**
     * 替换掉字符串中的敏感词
     *
     * @param str         等待替换的字符串
     * @param replaceChar 替换字符
     * @return
     */
    public static String replace(String str, String replaceChar) {
        checkReload();
        for (String word : words) {
            if (str.indexOf(word) >= 0) {
                String reChar = "";
                for (int i = 0; i < word.length(); i++) {
                    reChar += replaceChar;
                }
                str = str.replaceAll(word, reChar);
            }
        }
        return str;
    }


    public static List<String> lists() {
        checkReload();
        return words;
    }

    /**
     * 添加敏感词
     *
     * @param word
     * @throws IOException
     */
    public static void add(String word) throws IOException {
        word = word.toLowerCase();
        if (!words.contains(word)) {
            words.add(word);
            FileUtils.writeLines(wordfilter, "UTF-8", words);
            lastModified = wordfilter.lastModified();
        }
    }

    /**
     * 删除敏感词
     *
     * @param word
     * @throws IOException
     */
    public static void delete(String word) throws IOException {
        word = word.toLowerCase();
        words.remove(word);
        FileUtils.writeLines(wordfilter, "UTF-8", words);
        lastModified = wordfilter.lastModified();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(WordFilter.replace("中国共产党钓鱼岛 21世纪中国基金会 根据过滤字典过滤", "*"));
        System.out.println(WordFilter.isContain("做爱"));
        //BadWord.add("傻逼");
    }

}