package com.naruto.didi2.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.naruto.didi2.R;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/1/11/0011
 */

public class FileAdapter extends BaseAdapter {

    public Activity activity;  //创建View时必须要提供Context
    public List<File> list = new LinkedList<File>();  //数据源（文件列表）
    public String currPath;//当前路径
    private Bitmap bmp_folder, bmp_file;
    private static final int typeOne = 0;
    private static final int typeTwo = 1;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        File file = list.get(position);
        if (file.isDirectory()) {
            return typeOne;
        } else {
            return typeTwo;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder1 vh1 = null;
        ViewHolder2 vh2 = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case typeOne:
                    vh1=new ViewHolder1();
                    convertView=View.inflate(activity,R.layout.file_item,null);
                    vh1.vh1_name= (TextView) convertView.findViewById(R.id.tv_nam);
                    vh1.vh1_size= (TextView) convertView.findViewById(R.id.tv_size);
                    vh1.vh1_img= (ImageView) convertView.findViewById(R.id.iv_ico);
                    convertView.setTag(vh1);
                    break;
                case typeTwo:
                    vh2=new ViewHolder2();
                    convertView=View.inflate(activity,R.layout.doc_item,null);
                    vh2.vh2_name= (TextView) convertView.findViewById(R.id.tv_doc_nam);
                    vh2.vh2_size= (TextView) convertView.findViewById(R.id.tv_doc_size);
                    vh2.vh2_img= (ImageView) convertView.findViewById(R.id.iv_doc_ico);
                    vh2.vh2_down= (TextView) convertView.findViewById(R.id.tv_doc_down);
                    convertView.setTag(vh2);
                    break;
                default:
                    break;
            }
        }

        switch (type) {
            case typeOne:
                vh1= (ViewHolder1) convertView.getTag();
                File f = list.get(position);
                vh1.vh1_name.setText(f.getName());
                vh1.vh1_size.setText("大小：" + getFilesSize(f));
                vh1.vh1_img.setImageBitmap(bmp_folder);
                break;
            case typeTwo:
                vh2= (ViewHolder2) convertView.getTag();
                File f2 = list.get(position);
                vh2.vh2_name.setText(f2.getName());
                vh2.vh2_size.setText("大小：" + getFilesSize(f2));
                vh2.vh2_img.setImageBitmap(bmp_file);
                vh2.vh2_down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity,"开始下载中",Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            default:
                break;
        }




//        View v = View.inflate(activity, R.layout.file_item, null);
//        TextView Txt_Name = (TextView) v.findViewById(R.id.tv_nam);
//        TextView Txt_Size = (TextView) v.findViewById(R.id.tv_size);
//        ImageView img = (ImageView) v.findViewById(R.id.iv_ico);
//        File f = list.get(position);
//        Txt_Name.setText(f.getName());
//        Txt_Size.setText("大小：" + getFilesSize(f));
//        if (f.isDirectory())
//            img.setImageBitmap(bmp_folder);
//        else
//            img.setImageBitmap(bmp_file);
        return convertView;
    }


    class ViewHolder1 {
        TextView vh1_name, vh1_size;
        ImageView vh1_img;
    }

    class ViewHolder2 {
        TextView vh2_name, vh2_size,vh2_down;
        ImageView vh2_img;
    }

    public void scanFiles(String path) {
        list.clear();
        File dir = new File(path);
        File[] subFiles = dir.listFiles();
        if (subFiles != null)
            for (File f : subFiles)
                list.add(f);
        this.notifyDataSetChanged();
        currPath = path;
    }

    public FileAdapter(Activity activity) {
        this.activity = activity;
        bmp_folder = BitmapFactory.decodeResource(activity.getResources(), R.drawable.folder);//文件夹,decodeResource图片解码，source资源，解码为Bitmap类型；
        bmp_file = BitmapFactory.decodeResource(activity.getResources(), R.drawable.file);//文件
    }

    public static String getFilesSize(File f) {
        int sub_index = 0;
        String show = "";
        if (f.isFile()) {
            long length = f.length();
            if (length >= 1073741824) {
                sub_index = String.valueOf((float) length / 1073741824).indexOf(".");
                show = ((float) length / 1073741824 + "000").substring(0, sub_index + 3) + "GB";
            } else if (length >= 1048576) {
                sub_index = (String.valueOf((float) length / 1048576)).indexOf(".");
                show = ((float) length / 1048576 + "000").substring(0, sub_index + 3) + "GB";
            } else if (length >= 1024) {
                sub_index = (String.valueOf((float) length / 1024)).indexOf(".");
                show = ((float) length / 1024 + "000").substring(0, sub_index + 3) + "GB";
            } else if (length < 1024)
                show = String.valueOf(length) + "B";
        }
        return show;
    }

}
