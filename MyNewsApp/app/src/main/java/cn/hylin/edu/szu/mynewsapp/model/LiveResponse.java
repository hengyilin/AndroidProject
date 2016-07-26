package cn.hylin.edu.szu.mynewsapp.model;

import java.io.Serializable;

/**
 * Author：林恒宜 on 16-7-16 14:15
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class LiveResponse implements Serializable{
    private String resultcode;
    private String reason;
    private String error_code;
    private Result result;

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getResultcode() {
        return resultcode;
    }

    public String getReason() {
        return reason;
    }

    public String getError_code() {
        return error_code;
    }

    public Result getResult() {
        return result;
    }

    public class Result implements Serializable {
        private Data[] data;
        private String totalNum;
        private String pn;
        private String rn;

        public void setData(Data[] data) {
            this.data = data;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }

        public void setPn(String pn) {
            this.pn = pn;
        }

        public void setRn(String rn) {
            this.rn = rn;
        }

        public Data[] getData() {
            return data;
        }

        public String getTotalNum() {
            return totalNum;
        }

        public String getPn() {
            return pn;
        }

        public String getRn() {
            return rn;
        }

        public class Data implements Serializable {
            private String id;
            private String title;
            private String tags;
            private String imtro;
            private String ingredients;
            private String burden;
            private String[] albums;
            private Steps[] steps;

            public void setId(String id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public void setImtro(String imtro) {
                this.imtro = imtro;
            }

            public void setIngredients(String ingredients) {
                this.ingredients = ingredients;
            }

            public void setBurden(String burden) {
                this.burden = burden;
            }

            public void setAlbums(String[] albums) {
                this.albums = albums;
            }

            public void setSteps(Steps[] steps) {
                this.steps = steps;
            }

            public String getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getTags() {
                return tags;
            }

            public String getImtro() {
                return imtro;
            }

            public String getIngredients() {
                return ingredients;
            }

            public String getBurden() {
                return burden;
            }

            public String[] getAlbums() {
                return albums;
            }

            public Steps[] getSteps() {
                return steps;
            }

            public class Steps implements Serializable{
                private String img;
                private String step;

                public void setImg(String img) {
                    this.img = img;
                }

                public void setStep(String step) {
                    this.step = step;
                }

                public String getImg() {
                    return img;
                }

                public String getStep() {
                    return step;
                }
            }

        }
    }
}
