package com.mobilewise.didi2;

import java.util.List;

/**
 * Created by DELL on 2022/9/1.
 */

public class AppInstall {
    /**
     * state : 0
     * list : [{"AppPackageName":"com.wandoujia.phoenix2","CertificateHash":"BE:13:53:53:43:7D:70:4F:3A:37:E2:B4:13:D0:40:A5:DD:FF:4F:19"}]
     */

    private String state;
    private List<ListBean> list;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * AppPackageName : com.wandoujia.phoenix2
         * CertificateHash : BE:13:53:53:43:7D:70:4F:3A:37:E2:B4:13:D0:40:A5:DD:FF:4F:19
         */

        private String AppPackageName;
        private String CertificateHash;

        public String getAppPackageName() {
            return AppPackageName;
        }

        public void setAppPackageName(String AppPackageName) {
            this.AppPackageName = AppPackageName;
        }

        public String getCertificateHash() {
            return CertificateHash;
        }

        public void setCertificateHash(String CertificateHash) {
            this.CertificateHash = CertificateHash;
        }
    }
}
