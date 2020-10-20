package com.boot_demo.demo1.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class MyFileUtil {

    public static void deleteSpecifiedTypeFile(String type, String root) {
        File dir = new File(root);
        File[] files = dir.listFiles();
        if (files == null) {
            log.info("no files found");
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                deleteSpecifiedTypeFile(files[i].getAbsolutePath(), type);
            } else {
                String strFileName = files[i].getAbsolutePath().toLowerCase();
                if (strFileName.endsWith(type)) {
                    log.info("delete file with name:{}", files[i].getName());
                    files[i].delete();
                }
            }
        }
    }
}
