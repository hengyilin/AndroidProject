package cn.hylin.edu.szu.mynewsapp.model;

/**
 * Author：林恒宜 on 16-7-25 18:39
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 * https://route.showapi.com/92-92?id=1&limit=1&page=
 *      &showapi_appid=21539&showapi_timestamp=20160725183942&type=
 *      &showapi_sign=632a5e5602f0b2f6280eb99235cee82b
 */
public class BookListInfo {
    private String showapi_res_code;// 0
    private String showapi_res_error;
    private ResBody showapi_res_body;

    public String getShowapi_res_code() {
        return showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public ResBody getShowapi_res_body() {
        return showapi_res_body;
    }

    public class ResBody {
        private String total;
        private String ret_code;
        private String flag;
        private BookList[] bookList;
        private String success;

        public String getTotal() {
            return total;
        }

        public String getRet_code() {
            return ret_code;
        }

        public String getFlag() {
            return flag;
        }

        public BookList[] getBookList() {
            return bookList;
        }

        public String getSuccess() {
            return success;
        }

        public class  BookList {
            private String summary;         //图书简介
            private String id;              //图书ID
            private String bookclass;       //图书分类的id
            private String author;          //作者
            private String count;           //浏览次数
            private String name;
            private String fcount;
            private String img;             //图书配图
            private String rcount;
            private String from;            //出版社/来源

            public String getSummary() {
                return summary;
            }

            public String getId() {
                return id;
            }

            public String getBookclass() {
                return bookclass;
            }

            public String getAuthor() {
                return author;
            }

            public String getCount() {
                return count;
            }

            public String getName() {
                return name;
            }

            public String getFcount() {
                return fcount;
            }

            public String getImg() {
                return img;
            }

            public String getRcount() {
                return rcount;
            }

            public String getFrom() {
                return from;
            }
        }

    }


}
