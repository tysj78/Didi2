package com.naruto.didi2.bean;

import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/11/20/0020
 */

public class MeiTuBean {

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2020-11-15 12:00","title":"I邻家女孩\u2026\u2026[Meetlin作品 O卉彤等 310p]\u2026\u2026第2090辑","description":"华声美女","picUrl":"http://image.hnol.net/c/2020-11/15/10/202011151043471261-1559530.jpg","url":"http://bbs.voc.com.cn/mm/meinv-9233085-0-1.html"},{"ctime":"2020-11-14 20:00","title":"I邻家女孩\u2026\u2026[RayChen作品 龙睬葳 266p]\u2026\u2026第2089辑","description":"华声美女","picUrl":"http://image.hnol.net/c/2020-11/14/18/202011141852184841-1559530.jpg","url":"http://bbs.voc.com.cn/mm/meinv-9233009-0-1.html"},{"ctime":"2020-11-14 12:00","title":"【美D共享】♠ 朱可儿日本旅拍","description":"华声美女","picUrl":"http://image.hnol.net/c/2020-11/14/11/20201114113410591-2089977.jpg","url":"http://bbs.voc.com.cn/mm/meinv-9232945-0-1.html"},{"ctime":"2020-11-13 10:00","title":"【美D共享】♠ 居家美人韩静安","description":"华声美女","picUrl":"http://image.hnol.net/c/2020-11/13/09/202011130939261971-2089977.jpg","url":"http://bbs.voc.com.cn/mm/meinv-9232628-0-1.html"},{"ctime":"2020-11-12 16:00","title":"白领丽人9 [26P]","description":"华声美女","picUrl":"http://image.hnol.net/c/2020-11/12/12/202011121212523951-4623204.jpg","url":"http://bbs.voc.com.cn/mm/meinv-9232418-0-1.html"},{"ctime":"2020-11-12 16:00","title":"白领丽人8 [173P]","description":"华声美女","picUrl":"http://image.hnol.net/c/2020-11/12/11/202011121143202461-4623204.jpg","url":"http://bbs.voc.com.cn/mm/meinv-9232421-0-1.html"},{"ctime":"2020-11-12 15:00","title":"风的诉说 [11P]","description":"华声美女","picUrl":"http://image.hnol.net/c/2020-11/12/12/202011121239306551-4623204.jpg","url":"http://bbs.voc.com.cn/mm/meinv-9232404-0-1.html"},{"ctime":"2020-11-12 15:00","title":"一个人的快乐[32P]","description":"华声美女","picUrl":"http://image.hnol.net/c/2020-11/12/12/202011121232069421-4623204.jpg","url":"http://bbs.voc.com.cn/mm/meinv-9232405-0-1.html"},{"ctime":"2020-11-12 15:00","title":"快乐小公主 [12P]","description":"华声美女","picUrl":"http://image.hnol.net/c/2020-11/12/12/202011121221036931-4623204.jpg","url":"http://bbs.voc.com.cn/mm/meinv-9232416-0-1.html"},{"ctime":"2020-11-09 23:00","title":"☆成熟女人\u2026\u2026[RayChen作品 方唯真 215p]\u2026\u2026第1487辑","description":"华声美女","picUrl":"http://image.hnol.net/c/2020-11/09/21/202011092131049451-1559530.jpg","url":"http://bbs.voc.com.cn/mm/meinv-9231392-0-1.html"}]
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
         * ctime : 2020-11-15 12:00
         * title : I邻家女孩……[Meetlin作品 O卉彤等 310p]……第2090辑
         * description : 华声美女
         * picUrl : http://image.hnol.net/c/2020-11/15/10/202011151043471261-1559530.jpg
         * url : http://bbs.voc.com.cn/mm/meinv-9233085-0-1.html
         */

        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
