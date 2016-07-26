package cn.hylin.edu.szu.mynewsapp.model;

/**
 * Author：林恒宜 on 16-7-15 13:11
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description : 新闻主体的Bean类，用于解析从服务器返回的json数据
 */
public class NewsResponse {
    private String reason;
    private String error_code;
    private NewsResult result;

    public NewsResult getResult() {
        return result;
    }

    public void setResult(NewsResult result) {
        this.result = result;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public String getError_code() {
        return error_code;
    }

    public class NewsResult{
        private String stat;
        private NewsResponseBody[] data;

        public void setStat(String stat) {
            this.stat = stat;
        }

        public void setData(NewsResponseBody[] data) {
            this.data = data;
        }

        public String getStat() {
            return stat;
        }

        public NewsResponseBody[] getData() {
            return data;
        }
    }

    public class NewsResponseBody {
        private String title;
        private String date;
        private String author_name;
        private String thumbnail_pic_s;
        private String thumbnail_pic_s02;
        private String thumbnail_pic_s03;
        private String url;
        private String uniquekey;
        private String type;
        private String realtype;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public void setThumbnail_pic_s(String thumbnail_pic_s) {
            this.thumbnail_pic_s = thumbnail_pic_s;
        }

        public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
            this.thumbnail_pic_s02 = thumbnail_pic_s02;
        }

        public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
            this.thumbnail_pic_s03 = thumbnail_pic_s03;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setUniquekey(String uniquekey) {
            this.uniquekey = uniquekey;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setRealtype(String realtype) {
            this.realtype = realtype;
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public String getThumbnail_pic_s() {
            return thumbnail_pic_s;
        }

        public String getThumbnail_pic_s02() {
            return thumbnail_pic_s02;
        }

        public String getThumbnail_pic_s03() {
            return thumbnail_pic_s03;
        }

        public String getUrl() {
            return url;
        }

        public String getUniquekey() {
            return uniquekey;
        }

        public String getType() {
            return type;
        }

        public String getRealtype() {
            return realtype;
        }
    }
}


