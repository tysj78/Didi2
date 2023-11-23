package com.naruto.didi2.bean;

import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/11/20/0020
 */

public class MeiTu {

    /**
     * code : 200
     * msg : success
     * newslist : [{"picUrl":"https://www.huceo.com/zb_users/upload/2019/01/201901111547217811739694.png"},{"picUrl":"https://www.huceo.com/zb_users/upload/2019/01/201901111547218296185918.png"},{"picUrl":"https://www.huceo.com/zb_users/upload/2019/01/201901111547218330121809.png"},{"picUrl":"https://www.huceo.com/zb_users/upload/2019/01/201901111547218763428588.png"},{"picUrl":"https://www.huceo.com/zb_users/upload/2019/01/201901111547219637687102.png"}]
     */

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * picUrl : https://www.huceo.com/zb_users/upload/2019/01/201901111547217811739694.png
         */

        private String picUrl;

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
    }
}
