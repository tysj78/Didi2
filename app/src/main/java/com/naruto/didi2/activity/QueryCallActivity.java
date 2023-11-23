package com.naruto.didi2.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.naruto.didi2.R;
import com.naruto.didi2.bean.CallLogInfo;
import com.naruto.didi2.bean.ContactsInfo;
import com.naruto.didi2.bean.SmsHistory;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.PermissionUtils;
import com.naruto.didi2.util.ThreadPoolUtil;
import com.naruto.didi2.util.WorkThread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


public class QueryCallActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yy";
    private ListView callView;
    private List<SmsHistory> smsHistoriesList;
    ContentResolver contentResolver;
    Uri uri;
    SimpleDateFormat simpleDateFormat;
    private Button getcall;
    private Button getsms;
    private TextView exx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_call);
        initView();
//        querySms();
//            jiaoji();
//        getPhoneContacts();
        /*try {
            ContactsMsgUtils contactsMsgUtils = new ContactsMsgUtils();
            List<CallLogInfo> infos = contactsMsgUtils.getCallLog(this);
            final MyAdapter adapter = new MyAdapter(infos);
            callView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.getMessage());
            exx.setText(e.getMessage());
        }*/

        /*callView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {

                CallLogInfo info = (CallLogInfo) adapter.getItem(arg2);
                final String number = info.number;
                String[] items = new String[]{"复制号码到拨号盘, 拨号, 发送短信 "};
                new AlertDialog.Builder(QueryCallActivity.this).setTitle("操作")
                        .setItems(items, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    case 0:
                                        startActivity(new Intent(
                                                Intent.ACTION_DIAL, Uri
                                                .parse("tel:" + number)));
                                        break;
                                    case 1:
                                        startActivity(new Intent(
                                                Intent.ACTION_CALL, Uri
                                                .parse("tel:" + number)));
                                        break;
                                    case 2:
                                        startActivity(new Intent(
                                                Intent.ACTION_SENDTO, Uri
                                                .parse("sms:" + number)));
                                        break;

                                    default:
                                        break;
                                }

                            }
                        }).show();

                return false;
            }
        });*/
        String[] per = new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS};
        PermissionUtils.requestPermissions(this, per, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
            }
        });
    }

    private void jiaoji() {
        ArrayList<String> object = new ArrayList<>();
        object.add("真");
        object.add("新");
        object.add("镇");
        object.add("小");
        ArrayList<String> objects = new ArrayList<>();
        objects.add("真");
        objects.add("小");
        objects.add("智");
        objects.retainAll(object);

        for (String s : objects) {
            Log.e(TAG, "jiaoji: ");
        }
    }

    private void initView() {
        callView = (ListView) findViewById(R.id.callView);
        getcall = (Button) findViewById(R.id.getcall);
        getcall.setOnClickListener(this);
        getsms = (Button) findViewById(R.id.getsms);
        getsms.setOnClickListener(this);
        exx = (TextView) findViewById(R.id.exx);
        exx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcall:
                ThreadPoolUtil.getInstance().start(new WorkThread() {
                    @Override
                    public void runInner() {
                        List<CallLogInfo> infos = getCallLog(QueryCallActivity.this);
                    }
                });
                break;
            case R.id.getsms:
                querySms();
                break;
        }
    }

    @SuppressLint("MissingPermission")
    public List<CallLogInfo> getCallLog(Context context) {
        List<CallLogInfo> infos = new ArrayList<CallLogInfo>();
        ContentResolver cr = context.getContentResolver();
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection = new String[]{
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.NUMBER,
                CallLog.Calls.GEOCODED_LOCATION,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
        };
        Cursor cursor = cr.query(uri, projection, null, null, "date desc");
        int sum = 0;
        Log.e(TAG, "getCallLog开始查询: ");
        List<ContactsInfo> phoneContacts = getPhoneContacts();


        while (cursor.moveToNext()) {
//                if (sum >= 21) {
//                    break;
//                }
            //姓名
            String cName = cursor.getString(0);
            //联系人号码
            String number = cursor.getString(1);
            //区域
            String geocoded_location = cursor.getString(2);
            //时长
            String duration = cursor.getString(3);
            //通话类型
            int type = cursor.getInt(4);
            //日期
            long date = cursor.getLong(5);
            Log.e(TAG, "通话记录1:" + "联系人：" + cName + "===时长: " + duration + "===联系号码: " + number + "===通话类型: " + type + "===区域：" + geocoded_location + "===通话时间" + date);
            sum++;
            if (TextUtils.isEmpty(cName)) {
                cName = getContactName(phoneContacts, number);
            }
            Log.e(TAG, "通话记录2:" + "联系人：" + cName + "===时长: " + duration + "===联系号码: " + number + "===通话类型: " + type + "===区域：" + geocoded_location + "===通话时间" + date);
            infos.add(new CallLogInfo(cName, number, geocoded_location, duration, type, date));
        }
        if (cursor != null) {
            cursor.close();
        }
        Log.e(TAG, "getCallLog==: " + sum);
//            List<CallLogInfo> callLogInfos = infos.subList(0, 20);
//            Collections.sort(callLogInfos);
//            Collections.reverse(callLogInfos);
        return infos;
    }

    private String getContactName(List<ContactsInfo> phoneContacts, String number) {
        try {
            if (phoneContacts.size() == 0 || TextUtils.isEmpty(number)) {
                return "";
            }

            for (ContactsInfo info : phoneContacts) {
                if (number.equals(info.getMobile())) {
                    return info.getName();
                }
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return "";
    }


    private class MyAdapter extends BaseAdapter {
        private List<CallLogInfo> infos;
        private LayoutInflater inflater;

        public MyAdapter(List<CallLogInfo> infos) {
            super();
            this.infos = infos;
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {

            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MViewHoler viewHoler;
            if (convertView == null) {
                viewHoler = new MViewHoler();
                convertView = inflater.inflate(R.layout.call_log_item, null);
                viewHoler.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                viewHoler.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                viewHoler.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
                viewHoler.tv_cName = (TextView) convertView.findViewById(R.id.tv_cName);
                convertView.setTag(viewHoler);
            } else {
                viewHoler = (MViewHoler) convertView.getTag();
            }

            CallLogInfo info = infos.get(position);
            viewHoler.tv_number.setText(info.getMobile());
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String dateStr = format.format(info.getCreateTime());
            viewHoler.tv_date.setText(dateStr);
            String typeStr = null;
            int color = 0;
            switch (info.getType()) {
                case CallLog.Calls.INCOMING_TYPE:
                    typeStr = "来电";
                    color = Color.BLUE;

                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    typeStr = "去电";
                    color = Color.GREEN;

                    break;
                case CallLog.Calls.MISSED_TYPE:
                    typeStr = "未接";
                    color = Color.RED;

                    break;

                default:
                    break;
            }
            viewHoler.tv_type.setText(typeStr);
            viewHoler.tv_type.setTextColor(color);
            viewHoler.tv_cName.setText(info.getContact());
            return convertView;
        }
    }

    class MViewHoler {
        TextView tv_number;
        TextView tv_date;
        TextView tv_type;
        TextView tv_cName;
    }

    void querySms() {
        simpleDateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
//        smsHistoriesList = new ArrayList<>();
        contentResolver = getContentResolver();
//        uri = Uri.parse("content://sms/");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uri = Telephony.Sms.CONTENT_URI;
        } else {
            Log.e(TAG, "qandroid4.4以下无法获取短信记录 ");
            return;
        }

        String[] projection = new String[]{
                "_id",
                "address",
                "type",
                "subject",
                "date",
                "person",
                "body",
//                Telephony.Sms.THREAD_ID,
//                Telephony.Mms.Addr.CONTACT_ID
        };
        int sum = 0;
        //先查联系人
        List<ContactsInfo> phoneContacts = getPhoneContacts();
//        Log.e(TAG, "phoneContacts.size: " + phoneContacts.size());
//        phoneContacts.size();
        try {
            Log.e(TAG, "querySms开始查询短信记录: ");
            Cursor cursor = contentResolver.query(uri, projection, null, null, "date desc");
            while (cursor.moveToNext()) {
//                if (sum >= 10) {
//                    break;
//                }
                int call_id = cursor.getInt(0);
                String address = cursor.getString(1);
                String person = cursor.getString(5);
//                String trim = address.trim();
//                Log.e(TAG, "address: " + address);
                for (ContactsInfo info : phoneContacts) {
//                    Log.e(TAG, "info.getMobile(): " + info.getMobile());
                    if (address.equals(info.getMobile())) {
                        person = info.getName();
//                        Log.e(TAG, "info.getName(): "+ info.getName());
                        break;
                    }
                }
                int type = cursor.getInt(2);
                String subject = cursor.getString(3);
                long date = cursor.getLong(4);
//                String person = cursor.getString(5);
                String body = cursor.getString(6);
//                long threadId = cursor.getLong(7);
//                long cid = cursor.getLong(8);

                Log.e(TAG, "querySms: " + call_id + "===" + address + "===" + type + "===" + subject + "===" + simpleDateFormat.format(date) + "===" + person + "===" + body);
                SmsHistory smsHistory = new SmsHistory();
                smsHistory.sms_id = call_id;
                smsHistory.sms_address = address;
                smsHistory.sms_subject = subject;
                smsHistory.sms_date = simpleDateFormat.format(date);

                if (type == 1) {
                    smsHistory.sms_type = "接收";
                } else if (type == 2) {
                    smsHistory.sms_type = "发送";
                } else {
                    smsHistory.sms_type = "未知";
                }
                sum++;
//                smsHistoriesList.add(smsHistory);
            }
            Log.e(TAG, "querySms===: " + sum);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public synchronized static String getDisplayNameByPhone1(Context context, String phoneNum) {
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        String displayName = null;
        String phone1 = new StringBuffer(phoneNum.subSequence(0, 3)).append(" ").append(phoneNum.substring(3, 7))
                .append(" ").append(phoneNum.substring(7, 11)).toString();
        String phone2 = new StringBuffer(phoneNum.subSequence(0, 3)).append("-").append(phoneNum.substring(3, 7))
                .append("-").append(phoneNum.substring(7, 11)).toString();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, ContactsContract.CommonDataKinds.Phone.NUMBER + " in(?,?,?)", new String[]{
                phoneNum, phone1, phone2}, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                if (!TextUtils.isEmpty(displayName)) {
                    break;
                }
                cursor.close();
            }
        }
        return displayName;
    }

//    void getPnameById() {
//        //获取data表的路径
//        Uri data_uri = Uri.parse("content://com.android.contacts/data");
//        //各个参数的意思，路径、列名、条件、条件参数、排序
//        Cursor dataCursor = contentResolver.query(data_uri, new String[]{"mimetype", "data1"},
//                "raw_contact_id=?", new String[]{id}, null);
//        if (dataCursor != null) {
//            //每次循环创建一个实例用于保存data表中的数据
////            ContactsData contactsData = new ContactsData();
//            while (dataCursor.moveToNext()) {
//                String type = dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
//                switch (type) {
//                    case "vnd.android.cursor.item/email_v2":
//                        //这是邮箱信息
////                        contactsData.setEmail(dataCursor.getString(dataCursor.getColumnIndex("data1")));
//                        break;
//                    case "vnd.android.cursor.item/phone_v2":
//                        //这是手机号码信息
////                        contactsData.setNumber(dataCursor.getString(dataCursor.getColumnIndex("data1")));
//                        break;
//                    case "vnd.android.cursor.item/name":
//                        //这是联系人的名字
//                        contactsData.setName(dataCursor.getString(dataCursor.getColumnIndex("data1")));
//                        break;
//                }
//            }
//        }
//    }

    /*private void getPhoneContacts() {
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        //要想获得什么信息，就在这个数组里添加
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        };
        //uri               :查询地址
        //projection        :查询的数据字段名称
        //selection         :查询的条件 where id=
        //selectionArgs     :查询条件的参数
        //sortOrder         :排序
        //contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, "data1=?", new String[]{"15538164683"}, null);
        while (cursor.moveToNext()) {
            //这个顺序和上面的projection数组一致
           String person = cursor.getString(0);
            String  phone = cursor.getString(1);
            int id = cursor.getInt(2);
//            Uri imgUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, id + "");
            //获取联系人头像，以流的方式返回
//            is = ContactsContract.Contacts.openContactPhotoInputStream(cr, imgUri);
//            bitmap = BitmapFactory.decodeStream(is);
            Log.e(TAG, "getPhoneContacts: 联系人信息姓名："+person+"===号码："+phone+"===联id"+id );
        }
        if (cursor!=null) {
            cursor.close();
        }
        //赋值，开发过程中记得判空
//        mPerson.setText(person);
//        mPhone.setText(phone);
//        mImg.setImageBitmap(bitmap);
        //最后记得关闭流
//        try {
//            cursor.close();
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }*/
    private List<ContactsInfo> getPhoneContacts() {
        ArrayList<ContactsInfo> infos = new ArrayList<>();
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        //要想获得什么信息，就在这个数组里添加

        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        };
        //uri               :查询地址
        //projection        :查询的数据字段名称
        //selection         :查询的条件 where id=
        //selectionArgs     :查询条件的参数
        //sortOrder         :排序
        //contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
        while (cursor.moveToNext()) {
            //这个顺序和上面的projection数组一致
            String person = cursor.getString(0);
            String phone = cursor.getString(1);

            int id = cursor.getInt(2);
//            Uri imgUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, id + "");
            //获取联系人头像，以流的方式返回
//            is = ContactsContract.Contacts.openContactPhotoInputStream(cr, imgUri);
//            bitmap = BitmapFactory.decodeStream(is);
//            String trim = phone.replaceAll("　", "").trim();
//           String trim = phone.replace((char) 12288, ' ');
//            String trim =  phone.replaceAll(" ", "");
//            phone.replace("/\s+/g","");

//            Pattern pattern = Pattern.compile("/\\s+/g");
//            Matcher m =pattern.matcher(phone);
//            String trims =m.replaceAll("");

            String str = phone.replaceAll("\\s*", "");
//            Log.e(TAG, "getPhoneContacts: ===" + str);
            infos.add(new ContactsInfo(person, str));
            Log.e(TAG, "getPhoneContacts: 联系人信息姓名：" + person + "===号码：" + phone + "===联id" + id);
        }
        if (cursor != null) {
            cursor.close();
        }
        //赋值，开发过程中记得判空
//        mPerson.setText(person);
//        mPhone.setText(phone);
//        mImg.setImageBitmap(bitmap);
        //最后记得关闭流
//        try {
//            cursor.close();
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return infos;
    }

    /**
     * 通话记录去重
     */
    public static List<CallLogInfo> checkRepetition(List<CallLogInfo> current) {
        List<CallLogInfo> tempInfo = new ArrayList<>();
        List<CallLogInfo> cacheInfo = new ArrayList<>();//内存中的上传记录

        for (CallLogInfo logInfo : current) {
            if (!cacheInfo.contains(logInfo)) {
                tempInfo.add(logInfo);
            }
        }
        return tempInfo;
    }
}
