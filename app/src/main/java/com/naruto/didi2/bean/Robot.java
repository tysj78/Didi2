package com.naruto.didi2.bean;

import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/11/25/0025
 */

public class Robot {

    /**
     * code : 200
     * msg : success
     * datatype : text
     * newslist : [{"reply":"亲爱的主人你好，我叫小舞，性别女，来自北京，正在从事学生工作。摩羯座的我，爱好跳舞也喜欢和人类做朋友！"}]
     */

    private int code;
    private String msg;
    private String datatype;
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

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * reply : 亲爱的主人你好，我叫小舞，性别女，来自北京，正在从事学生工作。摩羯座的我，爱好跳舞也喜欢和人类做朋友！
         */

        private String reply;

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }
    }
}
