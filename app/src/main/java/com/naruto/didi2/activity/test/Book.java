package com.naruto.didi2.activity.test;

import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/1/22/0022
 */

public class Book {
    public static class InstallBean {

        /**
         * use : true
         * content : [{"frequency":"0","frequencytype":"min","opertype":"0"}]
         */

        private String use;
        private List<ContentBean> content;

        public String getUse() {
            return use;
        }

        public void setUse(String use) {
            this.use = use;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * frequency : 0
             * frequencytype : min
             * opertype : 0
             */

            private String frequency;
            private String frequencytype;
            private String opertype;

            public String getFrequency() {
                return frequency;
            }

            public void setFrequency(String frequency) {
                this.frequency = frequency;
            }

            public String getFrequencytype() {
                return frequencytype;
            }

            public void setFrequencytype(String frequencytype) {
                this.frequencytype = frequencytype;
            }

            public String getOpertype() {
                return opertype;
            }

            public void setOpertype(String opertype) {
                this.opertype = opertype;
            }
        }
    }
}
