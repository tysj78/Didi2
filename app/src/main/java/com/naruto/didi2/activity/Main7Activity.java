package com.naruto.didi2.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.naruto.didi2.R;
import com.naruto.didi2.bean.CloseType;
import com.naruto.didi2.bean.FormFile;
import com.naruto.didi2.bean.Person;
import com.naruto.didi2.bean.PicTypeInfo;
import com.naruto.didi2.util.HttpService;
import com.naruto.didi2.util.HttpUploadUtil;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main7Activity extends AppCompatActivity implements View.OnClickListener {


    private static final int INSTALL_PERMISS_CODE = 201;
    private static Main7Activity ths = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(ths, "我在1", Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:

                    break;
            }
        }
    };
    private Spinner spn_classes;
    private Button btn_submit;
    private String url = "http://106.12.207.212:8080/shenying/saveDeviceServlet";
    List<Person> personList = null;
    private Button btn_install;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        initView();
        ths = this;
//        neibu.getMu();
        ArrayList<CloseType> closeTypes = new ArrayList<>();
        CloseType closeType1 = new CloseType();
        closeType1.val = "DRC";
        closeType1.desc = "DRC";
        CloseType closeType2 = new CloseType();
        closeType2.val = "MEL";
        closeType2.desc = "MEL";
        CloseType closeType3 = new CloseType();
        closeType3.val = "QQQ";
        closeType3.desc = "重工";
        closeTypes.add(closeType1);
        closeTypes.add(closeType2);
        closeTypes.add(closeType3);
//        ArrayAdapter<CloseType> adapter = new ArrayAdapter<CloseType>(this, android.R.layout.simple_spinner_item, closeTypes
//        );
//        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
//        spn_classes.setAdapter(adapter);
    }

    private void initView() {
        spn_classes = (Spinner) findViewById(R.id.spn_classes);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        btn_install = (Button) findViewById(R.id.btn_install);
        btn_install.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
//                submit();
//                upLoadTu();
//                jiexi();

                InputStream inputStream = getResources().openRawResource(R.raw.picinfo);
                if (inputStream == null) {
                    Toast.makeText(this, "InputStream is null", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    getPicTypeInfo(inputStream);
                } catch (Exception e) {
//                    e.printStackTrace();
                    Log.e("gxb", "pull解析异常: " + e.toString());
                }
                break;
            case R.id.btn_install:
//                install();
//                installApk();
                setInstallPermission();
                break;
        }
    }

    private void install() {
        Intent itins = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File apkFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/emmappstore/apkloads/Didi2.apk");
            itins.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            itins.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


            Uri contentUri = FileProvider.getUriForFile(ths, getPackageName() + ".fileProvider", apkFile);
            itins.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
//            LogUtils.log("file://" + DownloadService.DOWNLOAD_PATH + info.filename);
//            itins.setDataAndType(Uri.parse("file://" + DownloadService.DOWNLOAD_PATH + info.filename), "application/vnd.android.package-archive");
        }
//        LogUtils.log("安装软件");
        startActivity(itins);
    }

    private void upLoadTu() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        File tu = new File(Environment.getExternalStorageDirectory().getPath() + "/img/cdr.png");
                        HttpUploadUtil.uploadFile(tu, "装上件", "铭牌", System.currentTimeMillis() + "");
                    }
                }
        ).start();
    }

    public static class neibu {
        public neibu() {
        }

        public static void getMu() {
            msg(1);
        }


    }

    private static void msg(int i) {
        if (Main7Activity.ths != null && Main7Activity.ths.handler != null) {
            Main7Activity.ths.handler.sendEmptyMessage(i);
        }
    }

    private void submit() {
        Map<String, Object> params = new HashMap<String, Object>();
        //普通表单，key-value
        params.put("key", "value");
        //图片表单，支持多文件上传，修改参数即可
        FormFile[] files = new FormFile[2];
//        files[0] = new FormFile(String filname, byte[] data, String formname, String contentType);
//        files[1] = new FormFile(String filname, byte[] data, String formname, String contentType);
        //上传方法，回调方法可更新界面
        HttpService httpService = new HttpService();
        httpService.postHttpImageRequest(url, params, files, new HttpService.HttpCallBackListener() {

            @Override
            public void onFinish(String response) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    /**
     * 【Pull解析器解析XML文件】
     **/
    public List<Person> readXmlByPull(InputStream inputStream) {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();

            Person currenPerson = null;
            List<Person> persons = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:/**【文档开始事件】**/
                        persons = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:/**【元素（即标签）开始事件】**/
                        String name = parser.getName();
                        if (name.equals("person")) {
                            currenPerson = new Person();
                            currenPerson.setId(new Integer(parser.getAttributeValue(null, "id")));
                        } else if (currenPerson != null) {
                            if (name.equals("name")) {/**【判断标签名（元素名）是否为name】**/
                                currenPerson.setName(parser.nextText());/**【如果后面是text元素，即返回它的值】**/
                            } else if (name.equals("age")) {
                                currenPerson.setAge(new Integer(parser.nextText()));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:/**【元素结束事件】**/
                        if (parser.getName().equalsIgnoreCase("person") && currenPerson != null) {
                            persons.add(currenPerson);
                            currenPerson = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            inputStream.close();
            return persons;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void jiexi() {
        String result = "";
        InputStream inputStream = getResources().openRawResource(R.raw.itcase);
        if (inputStream == null) {
            Toast.makeText(this, "InputStream is null", Toast.LENGTH_SHORT).show();
        } else {
            personList = readXmlByPull(inputStream); //调用Pull
            //personList = XMLParsingMethods.readXmlBySAX(inputStream);//调用SAX
            //personList = XMLParsingMethods.readXmlByDOM(inputStream);//调用DOM
            if (personList != null) {
                for (int i = 0; i < personList.size(); i++) {
                    String message = "id = " + personList.get(i).getId() + " , name = " + personList.get(i).getName()
                            + " , age = " + personList.get(i).getAge() + ".\n";
                    result += message;
                }
            } else {
                Toast.makeText(this, "persons is null", Toast.LENGTH_SHORT).show();
            }
            Log.e("yy", "jiexi: " + result);
        }
    }


    /**
     * add by gxb 20200220
     *
     * @param inputStream xml数据流
     * @return
     * @throws Exception
     */
    public static PicTypeInfo getPicTypeInfo(InputStream inputStream) throws Exception {
        PicTypeInfo picTypeInfo = null;
        PicTypeInfo.OnOffs onOff = null;
        PicTypeInfo.OnOffs.ImType imType = null;
        List<PicTypeInfo.OnOffs> onOffs = new ArrayList<>();
        List<PicTypeInfo.OnOffs.ImType> imTypes = new ArrayList<>();
//    	Map<String, String> iType=new HashMap<String, String>();

//        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes("UTF-8"));
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream, "UTF-8");
        int event = parser.getEventType();//产生第一个事件
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT://判断是否是文档开始事件
                    break;
                case XmlPullParser.START_TAG://判断当前事件是否是标签元素开始事件
                    if ("retCode".equals(parser.getName())) {//判断开始标签元素
                        String retCode = parser.nextText();
                        if ("1".equals(retCode)) {
                            Log.e("gxb_onOff", "图片类型获取失败");
                            return null;
                        } else if ("0".equals(retCode)) {
                            picTypeInfo = new PicTypeInfo();
                            picTypeInfo.setRetCode(retCode);
                        }
                    }
                    if ("onOffs".equals(parser.getName())) {
                        onOff = picTypeInfo.new OnOffs();
                    }
                    if ("onOff".equals(parser.getName())) {
                        onOff.setOnOff(parser.nextText());
                    }
                    if ("imtypes".equals(parser.getName())) {
//					imType = onOff.new ImType();
                        imTypes = new ArrayList<>();
                    }
                    //newadd
                    if ("imType".equals(parser.getName())) {
//					Log.e("gxb_onOff", "====2");
//					imType.setVal(parser.nextText());
//					iType= new HashMap<String, String>();

                        Log.e("gxb_onOff", "====1");
                        imType = onOff.new ImType();
                    }
                    //newadd
                    if ("val".equals(parser.getName())) {
                        Log.e("gxb_onOff", "====2");
                        imType.setVal(parser.nextText());
//					iType.put("val", parser.nextText());
                    }

                    //newadd
                    if ("desc".equals(parser.getName())) {
                        Log.e("gxb_onOff", "====3");
                        imType.setDesc(parser.nextText());
//					iType.put("desc", parser.nextText());
                    }

                    break;
                case XmlPullParser.END_TAG://判断当前事件是否是标签元素结束事件

                    if ("imType".equals(parser.getName())) {//判断结束标签元素是否是employee
                        Log.e("gxb_onOff", "====4" + imType.toString());
                        imTypes.add(imType);
                    }


                    if ("imtypes".equals(parser.getName())) {//判断结束标签元素是否是employee
//					Log.e("gxb_onOff", "====4"+imType.toString());
//					imTypes.add(imType);
//					imTypes.add(imType);
                        onOff.setImTypes(imTypes);
                    }
                    if ("onOffs".equals(parser.getName())) {
                        onOffs.add(onOff);
                        Log.e("gxb_onOff", "====5===" + imTypes.size());
                    }
                    if ("pojoList".equals(parser.getName())) {
                        picTypeInfo.setPojoList(onOffs);
                    }

                    break;
            }
            event = parser.next();
        }
        return picTypeInfo;
    }

    /**
     * 安装apk
     * /emmappstore/apkloads/Didi2.apk
     */
    private void installApk() {
        Log.e("yy", "installApk开始: ");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String type = "application/vnd.android.package-archive";
        Uri uri;
        File apkFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/emmappstore/apkloads/Didi2.apk");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, "com.mobilewise.mobileware.fileProvider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(apkFile);
        }
        intent.setDataAndType(uri, type);
        startActivity(intent);
    }

    /**
     * 8.0以上系统设置安装未知来源权限
     */
    public void setInstallPermission() {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先判断是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
//            if(!haveInstallPermission){
//                //弹框提示用户手动打开
//                MessageDialog.showAlert(this, "安装权限", "需要打开允许来自此来源，请去设置中开启此权限", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            //此方法需要API>=26才能使用
//                            toInstallPermissionSettingIntent();
//                        }
//                    }
//                });
//                return;
//            }
            if (!haveInstallPermission) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("允许安装吗")
                        .setMessage("允许didi2安装应用")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    //此方法需要API>=26才能使用
                                    toInstallPermissionSettingIntent();
                                }
                            }
                        });
                builder.show();
            } else {
                install();
            }
        }
    }


    /**
     * 开启安装未知来源权限
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toInstallPermissionSettingIntent() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, INSTALL_PERMISS_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == INSTALL_PERMISS_CODE) {
            Toast.makeText(this, "安装应用", Toast.LENGTH_SHORT).show();
            install();
//            installApk();
        }
    }
}
