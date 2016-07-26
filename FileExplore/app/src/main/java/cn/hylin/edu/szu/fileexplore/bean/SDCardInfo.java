package cn.hylin.edu.szu.fileexplore.bean;

/**
 * Author：林恒宜 on 16-7-25 00:50
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class SDCardInfo {
    private String totalCap;
    private String  releaseCap;

    public SDCardInfo() {
    }
    public SDCardInfo(String totalCap, String releaseCap) {
        this.totalCap = totalCap;
        this.releaseCap = releaseCap;
    }

    public void setTotalCap(String totalCap) {
        this.totalCap = totalCap;
    }

    public void setReleaseCap(String releaseCap) {
        this.releaseCap = releaseCap;
    }

    public String getTotalCap() {
        return totalCap;
    }

    public String getReleaseCap() {
        return releaseCap;
    }

    @Override
    public String toString() {
        return this.totalCap + "/" + this.releaseCap;
    }
}
