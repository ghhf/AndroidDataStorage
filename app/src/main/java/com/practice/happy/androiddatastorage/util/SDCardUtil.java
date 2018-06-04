package com.practice.happy.androiddatastorage.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hAPPY on 2018/5/27.
 */

public class SDCardUtil {

    // 判断SD卡是否被挂载
    public static boolean isSDCardMounted(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    // 获取SDCard根目录
    public static String getSDCardBaseDir(){
        if (isSDCardMounted()){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }
    // 获取SD卡的完整空间大小，返回MB
    public static long getSDCardSize(){
        if (isSDCardMounted()){
            StatFs fs = new StatFs(getSDCardBaseDir());
            int count = fs.getBlockCount();
            int size = fs.getBlockSize();
            return count * size /1024 / 1024;

        }
        return 0L;
    }

    // 获取SD卡的可用空间大小
    public static long getSDCardAvailableSize(){
        if (isSDCardMounted()){
            StatFs fs = new StatFs(getSDCardBaseDir());
            int count = fs.getAvailableBlocks();
            int size = fs.getBlockSize();
            return count * size/ 1024 / 1024;

        }
        return 0L;
    }

    // 往SD卡的公有目录下保存文件
    public static boolean saveFileToSDCardPublicDir(byte[] data,String type,String fileName){
        BufferedOutputStream bos = null;
        if (isSDCardMounted()){
            File file = Environment.getExternalStoragePublicDirectory(type);
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file,fileName)));
                bos.write(data);
                bos.flush();
                return  true;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    // 往SD卡自定义目录下保存文件
    public static boolean saveFileToSDCardCustomDir(byte[] data,String dir,String fileName){
        BufferedOutputStream bos = null;
        if (isSDCardMounted()){
            File file = new File(getSDCardBaseDir() + File.separator + dir);
            if (!file.exists()){
                file.mkdirs();
            }
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file,fileName)));
                bos.write(data);
                bos.flush();
                return true;

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
