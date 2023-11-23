package com.naruto.didi2.hook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzl on 5/30/18.
 */

public class CodeSet {
    private static CodeSet self = new CodeSet();
    private Map<SortCode, List<FuncCode>> m_s2f = new HashMap<SortCode, List<FuncCode>>();
    private Map<FuncCode, SortCode> m_f2u = new HashMap<FuncCode, SortCode>();;
    private Map<String, SortCode> m_s2s = new HashMap<String, SortCode>();;

    //用户关心事件，可能与FuncCode是一对多的关系
    public enum SortCode{
        S_CHECK_INJECT_STATUS,
        S_CHECK_DEBUG_STATUS,
        S_CHECK_HTTPS_HIJACKED_STATUS,
        S_CALL,
        S_CALL_LOG_ACCESS,
        S_CALL_LOG_CHANGE,
        S_SMS_SEND,
        S_SMS_ACCESS,
        S_SMS_CHANGE,
        S_EMAIL,
        S_CAMERA,
        S_CONTACTS_ACCESS,
        S_CONTACTS_CHANGE,
        S_MULTIMEDIA,
        S_RECORD_SOUND,
        S_WIFI_SWITCH,
        S_WIFI_WHITE_LIST,
        S_BLUETOOTH_SWITCH,
        S_BLUETOOTH_DISCOVER,
        S_CELLULAR_DATA,
        S_SENSOR,
        S_APK_INSTALL,
        S_APK_UNINSTALL,
        S_WATER_MARK_CFG,
        S_FILE_SECURITY_CFG,
        S_URL,
        S_URL_EXCLUDE_LIST,
        S_NOTIFICATION,
        S_PHONE_INFO,
        S_SCREENSHOT,
        S_SCREENSHOT_CFG,
        S_LOCATION,
        S_PRIMARYCLIP_COPY,
        S_PRIMARYCLIP_PAST,
        S_PRIMARYCLIP_TYPE,
        S_OVERLAY_WINDOW,
        S_PRINT,
        S_SENSITIVE_DATA,
        S_SOFT_INPUT,
        //S_VPN_HOSTS,
        //S_WHITE_SO,
        S_CHECK_ROOT_STATUS,
        S_CHECK_VM_STATUS,
        S_MSM_LOG,
        S_STOP_ALL,
        S_FRAME_ATTACH,
        S_SHARE_CONTENT,
        S_HIJACK,//劫持提醒
        S_ADB_ENABLED,//允许调试
        S_ALLOW_MOCK_LOCATION,//允许模拟位置
        S_LOCATION_FRAUD,//位置欺诈
        S_SDK_SENSITIVE_INFO,//第三方sdk敏感行为
        S_MULTI_APK,//应用多开
        S_TIME_ACCELERATER,//时间加速器
        S_CLICK_FRAUD,//点击欺诈，模拟点击，物理快速点击
        S_MEM_PROTECT,//代码段内存写保护
        S_REASONABLE_PERMISSION,//合理权限
        S_NETWORK_USE,//控制网络使用
        S_SSO,//单点登录
        S_DEL_LOG,//禁止输出日志
        S_WIFI_PROXY,//wifi地理检测，禁用
        S_CHECK_SIGNATURE,
        S_CHECK_HOSTS,
        S_SAFE_KEYBOARD,
        S_PATTERN_LOCK//超时锁屏
    };

    //底层具体功能使用
    public enum FuncCode {
        F_BINDER_SWITCH,//0
        F_CHECK_INJECT_STATUS,
        F_CHECK_DEBUG_STATUS,
        F_CHECK_HTTPS_HIJACKED_STATUS,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_CALL,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_DIAL,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_SENDTO_SMS,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_SENDTO_EMAIL,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_RECORD_SOUND,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_VIEW_CONTACTS_PEOPLE,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_CONTACT,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_IMAGE,//10
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_VIDEO,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_GET_CONTENT_IMAGE,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_GET_CONTENT_AUDIO,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_GET_CONTENT_VIDEO,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_IMAGE_CAPTURE,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_MANAGE_OVERLAY_PERMISSION,
        F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_BROWSER_VIEW,
        F_ACTIVITY_MANAGER_NATIVE_START_SERVICE_TRANSACTION,
        F_CONTENT_PROVIDER_INSERT_CALL_LOG,
        F_CONTENT_PROVIDER_DELETE_CALL_LOG,//20
        F_CONTENT_PROVIDER_UPDATE_CALL_LOG,
        F_CONTENT_PROVIDER_QUERY_CALL_LOG,
        F_CONTENT_PROVIDER_INSERT_SMS,
        F_CONTENT_PROVIDER_DELETE_SMS,
        F_CONTENT_PROVIDER_UPDATE_SMS,
        F_CONTENT_PROVIDER_QUERY_SMS,
        F_CONTENT_PROVIDER_INSERT_RAW_CONTACTS,
        F_CONTENT_PROVIDER_DELETE_RAW_CONTACTS,
        F_CONTENT_PROVIDER_UPDATE_RAW_CONTACTS,
        F_CONTENT_PROVIDER_QUERY_RAW_CONTACTS,//30
        F_IBLUETOOTH_MANAGER_TRANSACTION_ENABLE,
        F_IBLUETOOTH_MANAGER_TRANSACTION_START_DISCOVER,
        F_ICLIPBOARD_TRANSACTION_SETPRIMARYCLIP,
        F_ICLIPBOARD_TRANSACTION_GETPRIMARYCLIP,
        F_ICLIPBOARD_TRANSACTION_PRIMARYCLIP_TYPE,
        F_ILOCATION_MANAGER_TRANSACTION_GETLASTLOCATION,
        F_ILOCATION_MANAGER_GET_BAIDU_LOCATION,
        F_IPHONE_SUBINFO_TRANSACTION_GETLINE1NUMBER,
        F_SCREENSHOT,
        F_SCREENSHOT_CFG,
        F_IPRINT_MANAGER_TRANSACTION_PRINT,
        F_IWINDOW_SESSION_TRANSACTION_ADDTODISPLAY,//40
        F_IWIFI_MANAGER_ADD_OR_UPDATE_NETWORK,
        F_TELEPHONY_DATA_SET_ENABLED,
        F_IWIFI_MANAGER_SET_WIFI_ENABLED,
        F_ISENSOR_EVENT_ENABLE_DISABLE,
        F_INPUT_SENSITIVE_DATA,
        F_NOTIFICATION_MANAGER,
        F_WEBVIEW_LOADURL,
        F_WATER_MARK_CFG,
        F_FILE_SECURITY_CFG,
        F_INSTALL_APK,
        F_UNINSTALL_APK,//50
        F_SMSMANAGER_SEND,
        F_SOFT_INPUT,
        F_VPN_HOSTS,
        F_WHITE_SO,
        F_URL_EXCLUDE_LIST,
        F_MEDIARECORDER_START,
        F_CAMERA_OPEN,
        F_CHECK_ROOT_STATUS,
        F_CHECK_VM_STATUS,
        F_MSM_LOG,
        F_FRAME_ATTACH,
        F_SHARE_CONTENT_SYS,
        F_SHARE_CONTENT_SHARESDK,
        F_HIJACK,
        F_ADB_ENABLED,
        F_ALLOW_MOCK_LOCATION,
        F_LOCATION_FRAUD,
        F_SDK_SENSITIVE_INFO,
        F_MULTI_APK,
        F_TIME_ACCELERATER,
        F_CLICK_FRAUD,
        F_MEM_PROTECT,
        F_REASONABLE_PERMISSION,
        F_NETWORK_USE,
        F_SSO,
        F_DEL_LOG,
        F_WIFI_PROXY,
        F_CHECK_SIGNATURE,
        F_CHECK_HOSTS,
        F_SAFE_KEYBOARD,
        F_PATTERN_LOCK
    };

    private void initSortCode()
    {
        List<FuncCode> tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CHECK_INJECT_STATUS);
        m_s2f.put(SortCode.S_CHECK_INJECT_STATUS, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CHECK_DEBUG_STATUS);
        m_s2f.put(SortCode.S_CHECK_DEBUG_STATUS, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CHECK_HTTPS_HIJACKED_STATUS);
        m_s2f.put(SortCode.S_CHECK_HTTPS_HIJACKED_STATUS, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_CALL);
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_DIAL);
        m_s2f.put(SortCode.S_CALL, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CONTENT_PROVIDER_QUERY_CALL_LOG);
        m_s2f.put(SortCode.S_CALL_LOG_ACCESS, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CONTENT_PROVIDER_INSERT_CALL_LOG);
        tmpFC.add(FuncCode.F_CONTENT_PROVIDER_DELETE_CALL_LOG);
        tmpFC.add(FuncCode.F_CONTENT_PROVIDER_UPDATE_CALL_LOG);
        m_s2f.put(SortCode.S_CALL_LOG_CHANGE, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_SENDTO_SMS);
        tmpFC.add(FuncCode.F_SMSMANAGER_SEND);
        m_s2f.put(SortCode.S_SMS_SEND, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CONTENT_PROVIDER_QUERY_SMS);
        m_s2f.put(SortCode.S_SMS_ACCESS, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CONTENT_PROVIDER_INSERT_SMS);
        tmpFC.add(FuncCode.F_CONTENT_PROVIDER_DELETE_SMS);
        tmpFC.add(FuncCode.F_CONTENT_PROVIDER_UPDATE_SMS);
        m_s2f.put(SortCode.S_SMS_CHANGE, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_SENDTO_EMAIL);
        m_s2f.put(SortCode.S_EMAIL, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_VIEW_CONTACTS_PEOPLE);
        tmpFC.add(FuncCode.F_CONTENT_PROVIDER_QUERY_RAW_CONTACTS);
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_CONTACT);
        m_s2f.put(SortCode.S_CONTACTS_ACCESS, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CONTENT_PROVIDER_INSERT_RAW_CONTACTS);
        tmpFC.add(FuncCode.F_CONTENT_PROVIDER_DELETE_RAW_CONTACTS);
        tmpFC.add(FuncCode.F_CONTENT_PROVIDER_UPDATE_RAW_CONTACTS);
        m_s2f.put(SortCode.S_CONTACTS_CHANGE, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_IMAGE);
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_VIDEO);
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_GET_CONTENT_IMAGE);
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_GET_CONTENT_AUDIO);
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_GET_CONTENT_VIDEO);
        m_s2f.put(SortCode.S_MULTIMEDIA, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_RECORD_SOUND);
        tmpFC.add(FuncCode.F_MEDIARECORDER_START);
        m_s2f.put(SortCode.S_RECORD_SOUND, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_IMAGE_CAPTURE);
        tmpFC.add(FuncCode.F_CAMERA_OPEN);
        m_s2f.put(SortCode.S_CAMERA, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_IWIFI_MANAGER_SET_WIFI_ENABLED);
        m_s2f.put(SortCode.S_WIFI_SWITCH, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_IWIFI_MANAGER_ADD_OR_UPDATE_NETWORK);
        m_s2f.put(SortCode.S_WIFI_WHITE_LIST, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ISENSOR_EVENT_ENABLE_DISABLE);
        m_s2f.put(SortCode.S_SENSOR, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_INSTALL_APK);
        m_s2f.put(SortCode.S_APK_INSTALL, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_UNINSTALL_APK);
        m_s2f.put(SortCode.S_APK_UNINSTALL, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_WATER_MARK_CFG);
        m_s2f.put(SortCode.S_WATER_MARK_CFG, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_FILE_SECURITY_CFG);
        m_s2f.put(SortCode.S_FILE_SECURITY_CFG, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_WEBVIEW_LOADURL);
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_BROWSER_VIEW);
        m_s2f.put(SortCode.S_URL, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_NOTIFICATION_MANAGER);
        m_s2f.put(SortCode.S_NOTIFICATION, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_IPHONE_SUBINFO_TRANSACTION_GETLINE1NUMBER);
        m_s2f.put(SortCode.S_PHONE_INFO, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_SCREENSHOT);
        m_s2f.put(SortCode.S_SCREENSHOT, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_SCREENSHOT_CFG);
        m_s2f.put(SortCode.S_SCREENSHOT_CFG, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ILOCATION_MANAGER_TRANSACTION_GETLASTLOCATION);
        tmpFC.add(FuncCode.F_ILOCATION_MANAGER_GET_BAIDU_LOCATION);
        m_s2f.put(SortCode.S_LOCATION, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ICLIPBOARD_TRANSACTION_SETPRIMARYCLIP);
        m_s2f.put(SortCode.S_PRIMARYCLIP_COPY, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ICLIPBOARD_TRANSACTION_GETPRIMARYCLIP);
        m_s2f.put(SortCode.S_PRIMARYCLIP_PAST, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ICLIPBOARD_TRANSACTION_PRIMARYCLIP_TYPE);
        m_s2f.put(SortCode.S_PRIMARYCLIP_TYPE, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_MANAGE_OVERLAY_PERMISSION);
        tmpFC.add(FuncCode.F_IWINDOW_SESSION_TRANSACTION_ADDTODISPLAY);
        m_s2f.put(SortCode.S_OVERLAY_WINDOW, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_IBLUETOOTH_MANAGER_TRANSACTION_ENABLE);
        m_s2f.put(SortCode.S_BLUETOOTH_SWITCH, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_IBLUETOOTH_MANAGER_TRANSACTION_START_DISCOVER);
        m_s2f.put(SortCode.S_BLUETOOTH_DISCOVER, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_IPRINT_MANAGER_TRANSACTION_PRINT);
        m_s2f.put(SortCode.S_PRINT, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_TELEPHONY_DATA_SET_ENABLED);
        m_s2f.put(SortCode.S_CELLULAR_DATA, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_INPUT_SENSITIVE_DATA);
        m_s2f.put(SortCode.S_SENSITIVE_DATA, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_SOFT_INPUT);
        m_s2f.put(SortCode.S_SOFT_INPUT, tmpFC);

        //tmpFC = new ArrayList<>();
        //tmpFC.add(FuncCode.F_VPN_HOSTS);
        //m_s2f.put(SortCode.S_VPN_HOSTS, tmpFC);

//        tmpFC = new ArrayList<>();
//        tmpFC.add(FuncCode.F_WHITE_SO);
//        m_s2f.put(SortCode.S_WHITE_SO, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_URL_EXCLUDE_LIST);
        m_s2f.put(SortCode.S_URL_EXCLUDE_LIST, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CHECK_ROOT_STATUS);
        m_s2f.put(SortCode.S_CHECK_ROOT_STATUS, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CHECK_VM_STATUS);
        m_s2f.put(SortCode.S_CHECK_VM_STATUS, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_MSM_LOG);
        m_s2f.put(SortCode.S_MSM_LOG, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_FRAME_ATTACH);
        m_s2f.put(SortCode.S_FRAME_ATTACH, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_SHARE_CONTENT_SYS);
        tmpFC.add(FuncCode.F_SHARE_CONTENT_SHARESDK);
        m_s2f.put(SortCode.S_SHARE_CONTENT, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_HIJACK);
        m_s2f.put(SortCode.S_HIJACK, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ALLOW_MOCK_LOCATION);
        m_s2f.put(SortCode.S_ALLOW_MOCK_LOCATION, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_ADB_ENABLED);
        m_s2f.put(SortCode.S_ADB_ENABLED, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_LOCATION_FRAUD);
        m_s2f.put(SortCode.S_LOCATION_FRAUD, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_SDK_SENSITIVE_INFO);
        m_s2f.put(SortCode.S_SDK_SENSITIVE_INFO, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_MULTI_APK);
        m_s2f.put(SortCode.S_MULTI_APK, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_TIME_ACCELERATER);
        m_s2f.put(SortCode.S_TIME_ACCELERATER, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CLICK_FRAUD);
        m_s2f.put(SortCode.S_CLICK_FRAUD, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_MEM_PROTECT);
        m_s2f.put(SortCode.S_MEM_PROTECT, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_REASONABLE_PERMISSION);
        m_s2f.put(SortCode.S_REASONABLE_PERMISSION, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_NETWORK_USE);
        m_s2f.put(SortCode.S_NETWORK_USE, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_SSO);
        m_s2f.put(SortCode.S_SSO, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_DEL_LOG);
        m_s2f.put(SortCode.S_DEL_LOG, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_WIFI_PROXY);
        m_s2f.put(SortCode.S_WIFI_PROXY, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CHECK_SIGNATURE);
        m_s2f.put(SortCode.S_CHECK_SIGNATURE, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_CHECK_HOSTS);
        m_s2f.put(SortCode.S_CHECK_HOSTS, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_SAFE_KEYBOARD);
        m_s2f.put(SortCode.S_SAFE_KEYBOARD, tmpFC);

        tmpFC = new ArrayList<>();
        tmpFC.add(FuncCode.F_PATTERN_LOCK);
        m_s2f.put(SortCode.S_PATTERN_LOCK, tmpFC);
    }

    private void initFuncCode()
    {
        m_f2u.put(FuncCode.F_CHECK_INJECT_STATUS, SortCode.S_CHECK_INJECT_STATUS);
        m_f2u.put(FuncCode.F_CHECK_DEBUG_STATUS, SortCode.S_CHECK_DEBUG_STATUS);
        m_f2u.put(FuncCode.F_CHECK_HTTPS_HIJACKED_STATUS, SortCode.S_CHECK_HTTPS_HIJACKED_STATUS);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_CALL, SortCode.S_CALL);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_DIAL, SortCode.S_CALL);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_SENDTO_SMS, SortCode.S_SMS_SEND);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_SENDTO_EMAIL, SortCode.S_EMAIL);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_RECORD_SOUND, SortCode.S_RECORD_SOUND);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_VIEW_CONTACTS_PEOPLE, SortCode.S_CONTACTS_ACCESS);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_CONTACT, SortCode.S_CONTACTS_ACCESS);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_IMAGE, SortCode.S_MULTIMEDIA);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_PICK_VIDEO, SortCode.S_MULTIMEDIA);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_GET_CONTENT_IMAGE, SortCode.S_MULTIMEDIA);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_GET_CONTENT_AUDIO, SortCode.S_MULTIMEDIA);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_GET_CONTENT_VIDEO, SortCode.S_MULTIMEDIA);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_IMAGE_CAPTURE, SortCode.S_CAMERA);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_MANAGE_OVERLAY_PERMISSION, SortCode.S_OVERLAY_WINDOW);
        m_f2u.put(FuncCode.F_ACTIVITY_MANAGER_NATIVE_START_ACTIVITY_TRANSACTION_BROWSER_VIEW, SortCode.S_URL);
        m_f2u.put(FuncCode.F_CONTENT_PROVIDER_INSERT_CALL_LOG, SortCode.S_CALL_LOG_CHANGE);
        m_f2u.put(FuncCode.F_CONTENT_PROVIDER_DELETE_CALL_LOG, SortCode.S_CALL_LOG_CHANGE);
        m_f2u.put(FuncCode.F_CONTENT_PROVIDER_UPDATE_CALL_LOG, SortCode.S_CALL_LOG_CHANGE);
        m_f2u.put(FuncCode.F_CONTENT_PROVIDER_QUERY_CALL_LOG, SortCode.S_CALL_LOG_ACCESS);
        m_f2u.put(FuncCode.F_CONTENT_PROVIDER_INSERT_SMS, SortCode.S_SMS_CHANGE);
        m_f2u.put(FuncCode.F_CONTENT_PROVIDER_DELETE_SMS, SortCode.S_SMS_CHANGE);
        m_f2u.put(FuncCode.F_CONTENT_PROVIDER_UPDATE_SMS, SortCode.S_SMS_CHANGE);
        m_f2u.put(FuncCode.F_CONTENT_PROVIDER_QUERY_SMS, SortCode.S_SMS_ACCESS);
        m_f2u.put(FuncCode.F_CONTENT_PROVIDER_INSERT_RAW_CONTACTS, SortCode.S_CONTACTS_CHANGE);
        m_f2u.put(FuncCode.F_CONTENT_PROVIDER_DELETE_RAW_CONTACTS, SortCode.S_CONTACTS_CHANGE);
        m_f2u.put(FuncCode.F_CONTENT_PROVIDER_UPDATE_RAW_CONTACTS, SortCode.S_CONTACTS_CHANGE);
        m_f2u.put(FuncCode.F_CONTENT_PROVIDER_QUERY_RAW_CONTACTS, SortCode.S_CONTACTS_ACCESS);
        m_f2u.put(FuncCode.F_IBLUETOOTH_MANAGER_TRANSACTION_ENABLE, SortCode.S_BLUETOOTH_SWITCH);
        m_f2u.put(FuncCode.F_IBLUETOOTH_MANAGER_TRANSACTION_START_DISCOVER, SortCode.S_BLUETOOTH_DISCOVER);
        m_f2u.put(FuncCode.F_ICLIPBOARD_TRANSACTION_SETPRIMARYCLIP, SortCode.S_PRIMARYCLIP_COPY);
        m_f2u.put(FuncCode.F_ICLIPBOARD_TRANSACTION_GETPRIMARYCLIP, SortCode.S_PRIMARYCLIP_PAST);
        m_f2u.put(FuncCode.F_ICLIPBOARD_TRANSACTION_PRIMARYCLIP_TYPE, SortCode.S_PRIMARYCLIP_TYPE);
        m_f2u.put(FuncCode.F_ILOCATION_MANAGER_TRANSACTION_GETLASTLOCATION, SortCode.S_LOCATION);
        m_f2u.put(FuncCode.F_ILOCATION_MANAGER_GET_BAIDU_LOCATION, SortCode.S_LOCATION);
        m_f2u.put(FuncCode.F_IPHONE_SUBINFO_TRANSACTION_GETLINE1NUMBER, SortCode.S_PHONE_INFO);
        m_f2u.put(FuncCode.F_SCREENSHOT, SortCode.S_SCREENSHOT);
        m_f2u.put(FuncCode.F_SCREENSHOT_CFG, SortCode.S_SCREENSHOT_CFG);
        m_f2u.put(FuncCode.F_IPRINT_MANAGER_TRANSACTION_PRINT, SortCode.S_PRINT);
        m_f2u.put(FuncCode.F_IWINDOW_SESSION_TRANSACTION_ADDTODISPLAY, SortCode.S_OVERLAY_WINDOW);
        m_f2u.put(FuncCode.F_IWIFI_MANAGER_ADD_OR_UPDATE_NETWORK, SortCode.S_WIFI_WHITE_LIST);
        m_f2u.put(FuncCode.F_TELEPHONY_DATA_SET_ENABLED, SortCode.S_CELLULAR_DATA);
        m_f2u.put(FuncCode.F_IWIFI_MANAGER_SET_WIFI_ENABLED, SortCode.S_WIFI_SWITCH);
        m_f2u.put(FuncCode.F_ISENSOR_EVENT_ENABLE_DISABLE, SortCode.S_SENSOR);
        m_f2u.put(FuncCode.F_INPUT_SENSITIVE_DATA, SortCode.S_SENSITIVE_DATA);
        m_f2u.put(FuncCode.F_NOTIFICATION_MANAGER, SortCode.S_NOTIFICATION);
        m_f2u.put(FuncCode.F_WEBVIEW_LOADURL, SortCode.S_URL);
        m_f2u.put(FuncCode.F_WATER_MARK_CFG, SortCode.S_WATER_MARK_CFG);
        m_f2u.put(FuncCode.F_FILE_SECURITY_CFG, SortCode.S_FILE_SECURITY_CFG);
        m_f2u.put(FuncCode.F_INSTALL_APK, SortCode.S_APK_INSTALL);
        m_f2u.put(FuncCode.F_UNINSTALL_APK, SortCode.S_APK_UNINSTALL);
        m_f2u.put(FuncCode.F_SMSMANAGER_SEND, SortCode.S_SMS_SEND);
        m_f2u.put(FuncCode.F_SOFT_INPUT, SortCode.S_SOFT_INPUT);
        //m_f2u.put(FuncCode.F_VPN_HOSTS, SortCode.S_VPN_HOSTS);
        //m_f2u.put(FuncCode.F_WHITE_SO, SortCode.S_WHITE_SO);
        m_f2u.put(FuncCode.F_URL_EXCLUDE_LIST, SortCode.S_URL_EXCLUDE_LIST);
        m_f2u.put(FuncCode.F_MEDIARECORDER_START, SortCode.S_RECORD_SOUND);
        m_f2u.put(FuncCode.F_CAMERA_OPEN, SortCode.S_CAMERA);
        m_f2u.put(FuncCode.F_CHECK_ROOT_STATUS, SortCode.S_CHECK_ROOT_STATUS);
        m_f2u.put(FuncCode.F_CHECK_VM_STATUS, SortCode.S_CHECK_VM_STATUS);
        m_f2u.put(FuncCode.F_MSM_LOG, SortCode.S_MSM_LOG);
        m_f2u.put(FuncCode.F_FRAME_ATTACH, SortCode.S_FRAME_ATTACH);
        m_f2u.put(FuncCode.F_SHARE_CONTENT_SYS, SortCode.S_SHARE_CONTENT);
        m_f2u.put(FuncCode.F_SHARE_CONTENT_SHARESDK, SortCode.S_SHARE_CONTENT);
        m_f2u.put(FuncCode.F_HIJACK, SortCode.S_HIJACK);
        m_f2u.put(FuncCode.F_ALLOW_MOCK_LOCATION, SortCode.S_ALLOW_MOCK_LOCATION);
        m_f2u.put(FuncCode.F_ADB_ENABLED, SortCode.S_ADB_ENABLED);
        m_f2u.put(FuncCode.F_LOCATION_FRAUD, SortCode.S_LOCATION_FRAUD);
        m_f2u.put(FuncCode.F_SDK_SENSITIVE_INFO, SortCode.S_SDK_SENSITIVE_INFO);
        m_f2u.put(FuncCode.F_MULTI_APK, SortCode.S_MULTI_APK);
        m_f2u.put(FuncCode.F_TIME_ACCELERATER, SortCode.S_TIME_ACCELERATER);
        m_f2u.put(FuncCode.F_CLICK_FRAUD, SortCode.S_CLICK_FRAUD);
        m_f2u.put(FuncCode.F_MEM_PROTECT, SortCode.S_MEM_PROTECT);
        m_f2u.put(FuncCode.F_REASONABLE_PERMISSION, SortCode.S_REASONABLE_PERMISSION);
        m_f2u.put(FuncCode.F_NETWORK_USE, SortCode.S_NETWORK_USE);
        m_f2u.put(FuncCode.F_SSO, SortCode.S_SSO);
        m_f2u.put(FuncCode.F_DEL_LOG, SortCode.S_DEL_LOG);
        m_f2u.put(FuncCode.F_WIFI_PROXY, SortCode.S_WIFI_PROXY);
        m_f2u.put(FuncCode.F_CHECK_SIGNATURE, SortCode.S_CHECK_SIGNATURE);
        m_f2u.put(FuncCode.F_CHECK_HOSTS, SortCode.S_CHECK_HOSTS);
        m_f2u.put(FuncCode.F_SAFE_KEYBOARD, SortCode.S_SAFE_KEYBOARD);
        m_f2u.put(FuncCode.F_PATTERN_LOCK, SortCode.S_PATTERN_LOCK);
    }

    private void initCodeName()
    {
        for(SortCode one: SortCode.values())
        {
            m_s2s.put(one.toString(), one);
        }
    }

    private CodeSet()
    {
        initSortCode();
        initFuncCode();
        initCodeName();
    }

    public List<FuncCode> sortCode2FuncCode(SortCode uc)
    {
        return m_s2f.get(uc);
    }

    public SortCode funcCode2SortCode(FuncCode uc)
    {
        return m_f2u.get(uc);
    }

    public SortCode getSortCodeByString(String n)
    {
        return m_s2s.get(n);
    }

    public List<FuncCode> getFuncCodeByString(String n)
    {
        //return m_s2f.get(m_s2s.get(n));
        SortCode sc = getSortCodeByString(n);
        List<FuncCode> fc = sortCode2FuncCode(sc);
        return fc;
    }

    public static CodeSet getInstance()
    {
        return self;
    }
}