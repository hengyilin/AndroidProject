package cn.hylin.edu.szu.mynewsapp.model;

/**
 * Author：林恒宜 on 16-7-25 22:34
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class PagerDetailInfo {
    private String showapi_res_code;
    private String showapi_res_error;
    private PagerDetailBody showapi_res_body;

    public void setShowapi_res_code(String showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public void setShowapi_res_body(PagerDetailBody showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public String getShowapi_res_code() {
        return showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public PagerDetailBody getShowapi_res_body() {
        return showapi_res_body;
    }

    public PagerDetailInfo(String showapi_res_code, String showapi_res_error, PagerDetailBody showapi_res_body) {
        this.showapi_res_code = showapi_res_code;
        this.showapi_res_error = showapi_res_error;
        this.showapi_res_body = showapi_res_body;
    }

    public class PagerDetailBody{
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

        public PagerDetailBody(String ret_code, String flag, String success, BookDetail bookDetails) {
            this.ret_code = ret_code;
            this.flag = flag;
            this.success = success;
            this.bookDetails = bookDetails;
        }
    }

    public class BookDetail {
        private String message;
        private String id;
        private String title;
        private String book;

        public void setMessage(String message) {
            this.message = message;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setBook(String book) {
            this.book = book;
        }

        public String getMessage() {
            return message;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getBook() {
            return book;
        }

        public BookDetail(String message, String id, String title, String book) {
            this.message = message;
            this.id = id;
            this.title = title;
            this.book = book;
        }
    }



}
