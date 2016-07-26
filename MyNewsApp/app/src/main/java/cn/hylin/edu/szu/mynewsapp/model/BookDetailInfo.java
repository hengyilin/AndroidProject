package cn.hylin.edu.szu.mynewsapp.model;

/**
 * Author：林恒宜 on 16-7-25 21:39
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class BookDetailInfo {
    private String showapi_res_code;
    private String showapi_res_error;
    private BookDetailBody showapi_res_body;

    public void setShowapi_res_code(String showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public void setShowapi_res_body(BookDetailBody showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public String getShowapi_res_code() {
        return showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public BookDetailBody getShowapi_res_body() {
        return showapi_res_body;
    }

    public BookDetailInfo(String showapi_res_code, String showapi_res_error, BookDetailBody showapi_res_body) {
        this.showapi_res_code = showapi_res_code;
        this.showapi_res_error = showapi_res_error;
        this.showapi_res_body = showapi_res_body;
    }

    public class BookDetailBody {
        private String ret_code;
        private String flag;
        private String success;
        private BookDetail bookDetails;

        public void setRet_code(String ret_code) {
            this.ret_code = ret_code;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public void setBookDetails(BookDetail bookDetails) {
            this.bookDetails = bookDetails;
        }

        public String getRet_code() {
            return ret_code;
        }

        public String getFlag() {
            return flag;
        }

        public String getSuccess() {
            return success;
        }

        public BookDetail getBookDetails() {
            return bookDetails;
        }

        public BookDetailBody(String ret_code, String flag, String success, BookDetail bookDetails) {
            this.ret_code = ret_code;
            this.flag = flag;
            this.success = success;
            this.bookDetails = bookDetails;
        }
    }

    public class BookDetail {
        private String summary;
        private String id;
        private String bookclass;
        private String author;
        private String count;
        private String name;
        private String fcount;
        private String img;
        private BookList[] list;
        private String rcount;
        private String from;

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setBookclass(String bookclass) {
            this.bookclass = bookclass;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setFcount(String fcount) {
            this.fcount = fcount;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setList(BookList[] list) {
            this.list = list;
        }

        public void setRcount(String rcount) {
            this.rcount = rcount;
        }

        public void setFrom(String from) {
            this.from = from;
        }

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

        public BookList[] getList() {
            return list;
        }

        public String getRcount() {
            return rcount;
        }

        public String getFrom() {
            return from;
        }

        public BookDetail(String summary, String id, String bookclass, String author, String count, String name, String fcount, String img, BookList[] list, String rcount, String from) {
            this.summary = summary;
            this.id = id;
            this.bookclass = bookclass;
            this.author = author;
            this.count = count;
            this.name = name;
            this.fcount = fcount;
            this.img = img;
            this.list = list;
            this.rcount = rcount;
            this.from = from;
        }
    }

    public class BookList {
        private String id;
        private String title;

        public BookList(String id, String title) {
            this.id = id;
            this.title = title;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }
}
