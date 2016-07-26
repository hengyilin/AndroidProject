package cn.hylin.edu.szu.mynewsapp.model;

/**
 * Author：林恒宜 on 16-7-17 14:09
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class City {
    private String name;
    private String pingyin;

    public City() {
    }

    public City(String name, String pingyin) {
        this.name = name;
        this.pingyin = pingyin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPingyin(String pingyin) {
        this.pingyin = pingyin;
    }

    public String getName() {
        return name;
    }

    public String getPingyin() {
        return pingyin;
    }
}
