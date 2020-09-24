package com.boot_demo.demo1.test.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SizeFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

public class MyFileUtils {


    /**
     * 文件转为base64编码String
     */


    public static String enCode64(File file){
        String content = null;
        byte[] front = new byte[0];
        try {
            front = FileUtils.readFileToByteArray(file);
            content= Base64.getEncoder().encodeToString(front);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
        
    }
    /**
     * 遍历目录下的所有文件--方式1
     *
     * @param targetDir
     */
    public static List<File> loopDir1(File targetDir) {
        List<File> files = new ArrayList<>();
        if (targetDir != null && targetDir.exists() && targetDir.isDirectory()) {
            /**
             * targetDir：不要为 null、不要是文件、不要不存在
             * 第二个 文件过滤 参数如果为 FalseFileFilter.FALSE ，则不会查询任何文件
             * 第三个 目录过滤 参数如果为 FalseFileFilter.FALSE , 则只获取目标文件夹下的一级文件，而不会迭代获取子文件夹下的文件
             */
            Collection<File> fileCollection = FileUtils.listFiles(targetDir,
                    TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
            for (File file : fileCollection) {
                files.add(file);
            }
        }
        return files;
    }

    /**
     * 遍历目录下的所有文件--方式2
     *
     * @param targetDir
     */
    public static void loopDir2(File targetDir) {
        if (targetDir != null && targetDir.exists() && targetDir.isDirectory()) {
            /**
             * targetDir：不要为 null、不要是文件、不要不存在
             * 第二个 文件过滤 参数为 null 时，底层就是 TrueFileFilter.INSTANCE;
             * 第三个 目录过滤 参数为 true 时，底层就是  recursive ? TrueFileFilter.INSTANCE : FalseFileFilter.INSTANCE);
             */
            Collection<File> fileCollection = FileUtils.listFiles(targetDir, null, true);
            for (File file : fileCollection) {
                System.out.println(file.getPath());
            }
        }
    }

    /**
     * 遍历目录下所有以指定字符开头的文件----方式1
     *
     * @param targetDir 不要为 null、不要是文件、不要不存在
     * @param prefixs   文件前缀，支持多个，当为 null 时，表示不过滤
     */
    public static void loopDir3(File targetDir, String[] prefixs) {
        if (targetDir != null && targetDir.exists() && targetDir.isDirectory()) {
            PrefixFileFilter prefixFileFilter = null;
            if (prefixs != null) {
                prefixFileFilter = new PrefixFileFilter(prefixs);
            } else {
                prefixFileFilter = new PrefixFileFilter("");
            }
            /**
             * targetDir：不要为 null、不要是文件、不要不存在
             * 第二个参数 文件过滤
             *      1）PrefixFileFilter：为文件名前缀过滤器
             *      2）PrefixFileFilter 构造器参数可以是 String、List<String>、String[] 等
             *      3）如果参数为空，则表示不进行过滤，等同于 TrueFileFilter.INSTANCE
             *
             * 第三个参数 目录过滤
             *      TrueFileFilter.INSTANCE：表示迭代获取所有子孙目录
             *      FalseFileFilter.FALSE：表示只获取目标目录下一级，不进行迭代
             */
            Collection<File> fileCollection = FileUtils.listFiles(targetDir, prefixFileFilter, TrueFileFilter.INSTANCE);
            for (File file : fileCollection) {
                System.out.println(file.getPath());
            }
        }
    }

    /**
     * 遍历目录下所有以指定字符结尾的文件----方式1
     *
     * @param targetDir 不要为 null、不要是文件、不要不存在
     * @param suffixs   文件后缀，支持多个，当为 null 时，表示不过滤
     */
    public static void loopDir4(File targetDir, String[] suffixs) {
        if (targetDir != null && targetDir.exists() && targetDir.isDirectory()) {
            /**
             * SuffixFileFilter 指定的后缀可以是任意字符，如文件 overview-tree.html，后缀过滤器字符 html、.html、tree.html 等都能匹配
             * */
            SuffixFileFilter suffixFileFilter = null;
            if (suffixs != null) {
                suffixFileFilter = new SuffixFileFilter(suffixs);
            } else {
                suffixFileFilter = new SuffixFileFilter("");
            }
            /**
             * targetDir：不要为 null、不要是文件、不要不存在
             * 第二个参数 文件过滤
             *      1）SuffixFileFilter：为文件名后缀过滤器
             *      2）SuffixFileFilter 构造器参数可以是 String、List<String>、String[] 等
             *      3）如果参数为空，则表示不进行过滤，等同于 TrueFileFilter.INSTANCE
             *
             * 第三个参数 目录过滤
             *      TrueFileFilter.INSTANCE：表示迭代获取所有子孙目录
             *      FalseFileFilter.FALSE：表示只获取目标目录下一级，不进行迭代
             */
            Collection<File> fileCollection = FileUtils.listFiles(targetDir, suffixFileFilter, TrueFileFilter.INSTANCE);
            for (File file : fileCollection) {
                System.out.println(file.getPath());
            }
        }
    }

    /**
     * 遍历目录下所有指定后缀名结尾的文件----方式2
     *
     * @param targetDir ：不要为 null、不要是文件、不要不存在
     * @param suffixs   ：与 SuffixFileFilter 不同 ，此时只能是文件后缀名；如 文件 overview-tree.html，suffixs 为 html 才能匹配上，.html 则不能匹配
     *                  因为 listFiles 底层自动加了 "."
     */
    public static void loopDir5(File targetDir, String[] suffixs) {
        if (targetDir != null && targetDir.exists() && targetDir.isDirectory()) {
            /**
             * targetDir：不要为 null、不要是文件、不要不存在
             * 第二个 文件过滤 参数为 null 时，底层就是 TrueFileFilter.INSTANCE;
             * 第三个 目录过滤 参数为 true 时，底层就是  recursive ? TrueFileFilter.INSTANCE : FalseFileFilter.INSTANCE);
             */
            Collection<File> fileCollection = FileUtils.listFiles(targetDir, suffixs, true);
            for (File file : fileCollection) {
                System.out.println(">>> " + file.getPath());
            }
        }
    }

    /**
     * 遍历目录下所有大于或者小于指定大小的文件
     *
     * @param targetDir    不要为 null、不要是文件、不要不存在
     * @param fileSize     文件判断大小，单位 字节
     * @param acceptLarger 为 true，则表示获取  大于等于 fileSize 的文件
     *                     为 false ，则表示获取 小于 fileSize 的文件
     */
    public static void loopDir6(File targetDir, Long fileSize, boolean acceptLarger) {
        if (targetDir != null && targetDir.exists() && targetDir.isDirectory()) {
            /**
             * SizeFileFilter(final long size, final boolean acceptLarger)
             * 1）文件大小过滤器
             * 2）size，表示判断的依据点
             * 3）acceptLarger：为 true，则表示获取  大于等于 size 的文件
             *      为 false ，则表示获取 小于 size 的文件
             * 4）单位是字节
             */
            SizeFileFilter sizeFileFilter = new SizeFileFilter(fileSize, acceptLarger);
            Collection<File> fileCollection = FileUtils.listFiles(targetDir, sizeFileFilter, TrueFileFilter.INSTANCE);
            for (File file : fileCollection) {
                System.out.println(file.getPath() + "\t" + file.length() +
                        "\t" + FileUtils.byteCountToDisplaySize(file.length()));
            }
        }
    }

}
