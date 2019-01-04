package com.guochuang.mimedia.mvp.model;

import java.io.Serializable;

public class PictureBean implements Serializable {
        /**
         * content : string
         * jumpUrl : string
         * picture : string
         * title : string
         */

        private String content;
        private String jumpUrl;
        private String picture;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }