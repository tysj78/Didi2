package com.naruto.didi2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.naruto.didi2.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class CodingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coding);

    }

    public String getString(String str_filepath) {// 转码
        File file = new File(str_filepath);
        BufferedReader reader;
        StringBuilder text = new StringBuilder("");
        String code = " ";
        try {
            // FileReader f_reader = new FileReader(file);
            // BufferedReader reader = new BufferedReader(f_reader);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(fis);
            in.mark(4);
            byte[] first3bytes = new byte[3];
            in.read(first3bytes);//找到文档的前三个字节并自动判断文档类型。
            in.reset();
            Log.e("y", "getString:=== "+first3bytes[0]+first3bytes[1]+first3bytes[2] );
//            code = getCharset(first3bytes);
//            reader = new BufferedReader(new InputStreamReader(in, code));

            if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
                    && first3bytes[2] == (byte) 0xBF) {// utf-8
                code = "utf-8";
                reader = new BufferedReader(new InputStreamReader(in, code));
            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFE) {
                code = "unicode";
                reader = new BufferedReader(
                        new InputStreamReader(in, code));
            } else if (first3bytes[0] == (byte) 0xFE
                    && first3bytes[1] == (byte) 0xFF) {
                code = "utf-16be";
                reader = new BufferedReader(new InputStreamReader(in,
                        code));
            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFF) {
                code = "utf-16le";
                reader = new BufferedReader(new InputStreamReader(in,
                        code));
            } else {
                code = "GB18030";
//                code = "utf-8";
                //InputStreamReader isr=new InputStreamReader(in);
                //reader = new BufferedReader(isr);
                reader = new BufferedReader(new InputStreamReader(in,
                        code));
            }
//            String str = "文档编码：" + code + "\n" + reader.readLine();
            Log.e("y", "getString文档编码: "+code);
            String str = reader.readLine();
            while (str != null) {
                text.append(str).append("\n");
                //text = text + str+ "\n";
                str = reader.readLine();
            }
            //text=EncodingUtils.getString(text.getBytes(isr.getEncoding()), code);
            //text=EncodingUtils.getString(text.getBytes(code), "utf-8");
            reader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = text.toString();
        Log.e("y", "getString====: "+s );
//        String fileEncode= EncodingDetect.getJavaEncode(filePath);
        return text.toString();
    }
}
