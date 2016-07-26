package cn.hylin.edu.szu.mynewsapp.model;

/**
 * Author：林恒宜 on 16-7-16 22:14
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description : 最近影讯返回结果
 */
public class VideoRecentInfoResponse {
    private String reason; //查询成功
    private VideoRecentInfoResult result; //最近影讯信息结果
    private String error_code; //

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResult(VideoRecentInfoResult result) {
        this.result = result;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public VideoRecentInfoResult getResult() {
        return result;
    }

    public String getError_code() {
        return error_code;
    }

    /**
     * 最近影讯信息结果类 result
     */
    public class VideoRecentInfoResult {
        private String title;//深圳影讯_最近上映电影
        private String url; //http:\/\/theater.mtime.com\/China_Guangdong_Province_Shenzen\/
        private String m_url; //http:\/\/m.mtime.cn\/?cityId=366
        private VideoRecentInfoResultData[] data; //最近影讯信息结果信息具体数据，返回的是一个数组
        private String morelink; //http:\/\/theater.mtime.com\/China_Guangdong_Province_Shenzen\/
        private String date; //2016-07-16

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setM_url(String m_url) {
            this.m_url = m_url;
        }

        public void setData(VideoRecentInfoResultData[] data) {
            this.data = data;
        }

        public void setMorelink(String morelink) {
            this.morelink = morelink;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getM_url() {
            return m_url;
        }

        public VideoRecentInfoResultData[] getData() {
            return data;
        }

        public String getMorelink() {
            return morelink;
        }

        public String getDate() {
            return date;
        }
    }
    /**
     * 最近影讯信息结果信息具体数据类 data数组
     */
    public class VideoRecentInfoResultData {
        private String name; //正在上映
        private String link; //链接不能访问
        private DataStruct[] data; //data[] 数据集合

        public void setName(String name) {
            this.name = name;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public void setData(DataStruct[] data) {
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public String getLink() {
            return link;
        }

        public DataStruct[] getData() {
            return data;
        }
    }

    /**
     * 最新影讯数据集合类
     */
    public class DataStruct {
        private String tvTitle; // 惊天大逆转
        private String iconaddress; //http:\/\/p2.qhimg.com\/t01d6180ffc337d82ab.jpg?size=300x400
        private String iconlinkUrl; //http:\/\/movie.mtime.com\/224041\/
        private String m_iconlinkUrl; //http:\/\/m.mtime.cn\/#!\/movie\/224041\/
        private String subHead; // 今日145家影院上映
        private String grade; // 6.6
        private String gradeNum; // \/10.0(时光网)
        private PlayDate playDate; // 上映日期类对象
        private Director director; //导演
        private Star star; //主演
        private Type type; // 类型
        private Story story;//剧情
        private More more; // 更多

        public void setTvTitle(String tvTitle) {
            this.tvTitle = tvTitle;
        }

        public void setIconaddress(String iconaddress) {
            this.iconaddress = iconaddress;
        }

        public void setIconlinkUrl(String iconlinkUrl) {
            this.iconlinkUrl = iconlinkUrl;
        }

        public void setM_iconlinkUrl(String m_iconlinkUrl) {
            this.m_iconlinkUrl = m_iconlinkUrl;
        }

        public void setSubHead(String subHead) {
            this.subHead = subHead;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public void setGradeNum(String gradeNum) {
            this.gradeNum = gradeNum;
        }

        public void setPlayDate(PlayDate playDate) {
            this.playDate = playDate;
        }

        public void setDirector(Director director) {
            this.director = director;
        }

        public void setStar(Star star) {
            this.star = star;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public void setStory(Story story) {
            this.story = story;
        }

        public void setMore(More more) {
            this.more = more;
        }

        public String getTvTitle() {
            return tvTitle;
        }

        public String getIconaddress() {
            return iconaddress;
        }

        public String getIconlinkUrl() {
            return iconlinkUrl;
        }

        public String getM_iconlinkUrl() {
            return m_iconlinkUrl;
        }

        public String getSubHead() {
            return subHead;
        }

        public String getGrade() {
            return grade;
        }

        public String getGradeNum() {
            return gradeNum;
        }

        public PlayDate getPlayDate() {
            return playDate;
        }

        public Director getDirector() {
            return director;
        }

        public Star getStar() {
            return star;
        }

        public Type getType() {
            return type;
        }

        public Story getStory() {
            return story;
        }

        public More getMore() {
            return more;
        }
    }
    /**
     * 上映日期类
     */
    public class PlayDate {
        private String showname; //上映日期
        private String data; //2016年7月15日
        private String data2; // 2016-7-15

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public void setData(String data) {
            this.data = data;
        }

        public void setData2(String data2) {
            this.data2 = data2;
        }

        public String getShowname() {
            return showname;
        }

        public String getData() {
            return data;
        }

        public String getData2() {
            return data2;
        }
    }
    /**
     * 导演类 这里开始有数字作为键的json
     */
    public class Director {
        private String showname;//导演
        private DirectorData data; // 数据集合

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public void setData(DirectorData data) {
            this.data = data;
        }

        public String getShowname() {
            return showname;
        }

        public DirectorData getData() {
            return data;
        }
    }

    /**
     * 导演数据类
     */
    public class DirectorData {
        private DataType1 one;//json键值为1
        private DataType2 m_1; //只有一个键值对link:链接

        public void setOne(DataType1 one) {
            this.one = one;
        }

        public void setM_1(DataType2 m_1) {
            this.m_1 = m_1;
        }

        public DataType1 getOne() {
            return one;
        }

        public DataType2 getM_1() {
            return m_1;
        }
    }
    /**
     * 主演类
     */
    public class Star {
        private String showname; //主演
        private StarData data;  //主演的人物数据

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public void setData(StarData data) {
            this.data = data;
        }

        public String getShowname() {
            return showname;
        }

        public StarData getData() {
            return data;
        }
    }
    /**
     * 主演人物数据类
     */
    public class StarData {
        //以下分别是四个人的具体信息
        private DataType1 one; //钟汉良
        private DataType1 two; // 李政宰
        private DataType1 three;//郎月婷
        private DataType1 four; // 李彩英
        //以下是上面四个人的另一个网站的信息的链接
        private DataType2 m_1;
        private DataType2 m_2;
        private DataType2 m_3;
        private DataType2 m_4;

        public void setOne(DataType1 one) {
            this.one = one;
        }

        public void setTwo(DataType1 two) {
            this.two = two;
        }

        public void setThree(DataType1 three) {
            this.three = three;
        }

        public void setFour(DataType1 four) {
            this.four = four;
        }

        public void setM_1(DataType2 m_1) {
            this.m_1 = m_1;
        }

        public void setM_2(DataType2 m_2) {
            this.m_2 = m_2;
        }

        public void setM_3(DataType2 m_3) {
            this.m_3 = m_3;
        }

        public void setM_4(DataType2 m_4) {
            this.m_4 = m_4;
        }

        public DataType1 getOne() {
            return one;
        }

        public DataType1 getTwo() {
            return two;
        }

        public DataType1 getThree() {
            return three;
        }

        public DataType1 getFour() {
            return four;
        }

        public DataType2 getM_1() {
            return m_1;
        }

        public DataType2 getM_2() {
            return m_2;
        }

        public DataType2 getM_3() {
            return m_3;
        }

        public DataType2 getM_4() {
            return m_4;
        }
    }
    /**
     * 电影类型类
     */
    public class Type {
        private String showname ;// 类型
        private TypeData data;//类型数据

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public void setData(TypeData data) {
            this.data = data;
        }

        public String getShowname() {
            return showname;
        }

        public TypeData getData() {
            return data;
        }
    }
    /**
     * 类型数据类
     */
    public class TypeData {
        DataType1 one;
        DataType1 two;

        public void setOne(DataType1 one) {
            this.one = one;
        }

        public void setTwo(DataType1 two) {
            this.two = two;
        }

        public DataType1 getOne() {
            return one;
        }

        public DataType1 getTwo() {
            return two;
        }
    }

    /**
     * 剧情类
     */
    public class Story {
        private String showname;
        private StoryData data;

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public void setData(StoryData data) {
            this.data = data;
        }

        public String getShowname() {
            return showname;
        }

        public StoryData getData() {
            return data;
        }
    }

    /**
     * 剧情数据类
     */
    public class StoryData {
        private String storyBrief;
        private String storyMoreLink;

        public void setStoryBrief(String storyBrief) {
            this.storyBrief = storyBrief;
        }

        public void setStoryMoreLink(String storyMoreLink) {
            this.storyMoreLink = storyMoreLink;
        }

        public String getStoryBrief() {
            return storyBrief;
        }

        public String getStoryMoreLink() {
            return storyMoreLink;
        }
    }

    /**
     * 更多信息类
     */
    public class More {
        private String showname;
        private MoreData[] data;

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public void setData(MoreData[] data) {
            this.data = data;
        }

        public String getShowname() {
            return showname;
        }

        public MoreData[] getData() {
            return data;
        }
    }

    /**
     * 更多数据信息类
     */
    public class MoreData {
        private String name;
        private String link;

        public void setName(String name) {
            this.name = name;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public String getLink() {
            return link;
        }
    }
    /**
     * 数据类型1
     */
    public class DataType1 {
        private String name; // 名字
        private String link; // 链接

        public void setName(String name) {
            this.name = name;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public String getLink() {
            return link;
        }
    }

    /**
     * 数据类型2
     */
    public class DataType2 {
        private String link; // 链接

        public void setLink(String link) {
            this.link = link;
        }

        public String getLink() {
            return link;
        }
    }
}
