package com.practice.happy.androiddatastorage.util;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Happy on 2018/5/3.
 */

public class XMLUtil {

    /**
     * 通过字符串拼接 保存xml文件
     */
    public static void saveXmlByStringBuilder(Context ctx, String name, String pwd) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<user>");
        sb.append("<string name = \"name\">" + name + "</string>");
        sb.append("<string name = \"pwd\">" + pwd + "</string>");
        sb.append("</user>");

        String result = sb.toString();

        File file = new File(ctx.getFilesDir(), "userInfo.xml");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(result);
        writer.close();
        Log.e("TAG", "保存成功--name" + name + "----- password" + pwd);
    }

    /**
     * 通过XmlSerializer序列化 保存xml文件
     */
    public static boolean saveXmlByXmlSerializer(Context context, String name, String pwd) throws IOException {
        // 创建XmlSerializer序列化工具
        XmlSerializer xmlSerializer = Xml.newSerializer();

        // 设置文件输出位置
        FileOutputStream fos = context.openFileOutput("userInfo.xml", Context.MODE_PRIVATE);
        xmlSerializer.setOutput(fos, "utf-8");

        // 开始写文档
        xmlSerializer.startDocument("utf-8", true);
        xmlSerializer.startTag(null, "user");

        xmlSerializer.startTag(null, "name");
        xmlSerializer.text(name);
        xmlSerializer.endTag(null, "name");

        xmlSerializer.startTag(null, "pwd");
        xmlSerializer.text(pwd);
        xmlSerializer.endTag(null, "pwd");

        xmlSerializer.endTag(null, "user");

        // 结束写入文档，此时内存中的数据会写到硬盘中
        xmlSerializer.endDocument();
        Log.e("TAG", "保存成功--name" + name + "----- password" + pwd);

        return true;

    }

    // Pull解析 解析XML文件
    public static String parseXmlFile(Context context){
        String result = "";

        XmlPullParser parser = Xml.newPullParser();

        FileInputStream fis = null;
        try {
            fis = context.openFileInput("userInfo.xml");
            parser.setInput(fis, "utf-8");

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("user".equals(parser.getName())) {
                            Log.e("XML", "<user>");

                        } else if ("name".equals(parser.getName())) {

                            result += (" name" + parser.nextText());
                            Log.e("XML", "name == " + result + "parse == " + parser.getName());

                        } else if ("pwd".equals(parser.getName())) {
                            result += (" pwd" + parser.nextText());
                            Log.e("XML", "pwd == " + result + "pwd == " + parser.getName());
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        if ("user".equals(parser.getName())) {
                            Log.e("XML", "解析xml一个节点完成" + parser.getName());
                        }
                        break;
                }

                // 解析下一个标签
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "解析的文件不存在，请先保存文件！";
        }

        return result;
    }
}
