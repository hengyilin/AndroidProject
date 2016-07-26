package cn.hylin.edu.szu.mynewsapp.model;

/**
 * Author：林恒宜 on 16-7-16 21:58
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description : 影讯搜索
 */
public class VideoSearchResponse {
    private String reason;
    private Result result;
    private String error_code;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public Result getResult() {
        return result;
    }

    public String getError_code() {
        return error_code;
    }

    public class Result {
        private String title;//标题
        private String tag; // 标签
        private String act; // 演员表
        private String year;// 年份
        private String rating;// null
        private String area;// 地区
        private String dir;// 导演
        private String desc;// 描述
        private String cover;// 封面
        private String[] playlinks;//播放链接
        private Resource[] video_rec; //相关作品
        private ActorInfo[] act_s; // 演员的相关信息

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setPlaylinks(String[] playlinks) {
            this.playlinks = playlinks;
        }

        public void setVideo_rec(Resource[] video_rec) {
            this.video_rec = video_rec;
        }

        public void setAct_s(ActorInfo[] act_s) {
            this.act_s = act_s;
        }

        public String getTitle() {
            return title;
        }

        public String getTag() {
            return tag;
        }

        public String getAct() {
            return act;
        }

        public String getYear() {
            return year;
        }

        public String getRating() {
            return rating;
        }

        public String getArea() {
            return area;
        }

        public String getDir() {
            return dir;
        }

        public String getDesc() {
            return desc;
        }

        public String getCover() {
            return cover;
        }

        public String[] getPlaylinks() {
            return playlinks;
        }

        public Resource[] getVideo_rec() {
            return video_rec;
        }

        public ActorInfo[] getAct_s() {
            return act_s;
        }

        public class Resource {
            private String cover;//资源封面
            private String detail_url;// 详情链接
            private String title;// 资源标题

            public void setCover(String cover) {
                this.cover = cover;
            }

            public void setDetail_url(String detail_url) {
                this.detail_url = detail_url;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCover() {
                return cover;
            }

            public String getDetail_url() {
                return detail_url;
            }

            public String getTitle() {
                return title;
            }
        }
        public class ActorInfo {
            private String name; // 名字
            private String url; // 演员的百科信息
            private String image; // 演员的照片

            public void setName(String name) {
                this.name = name;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public String getUrl() {
                return url;
            }

            public String getImage() {
                return image;
            }
        }
    }
}
