package cn.hylin.edu.szu.fileexplore.bean;

/**
 * Author：林恒宜 on 16-7-24 23:42
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public class GridViewItemBean {
    private int itemIconResId;
    private String itemName;
    private int itemNumber;

    public GridViewItemBean() {
    }

    public GridViewItemBean(int itemIconResId, String itemName, int itemNumber) {
        this.itemIconResId = itemIconResId;
        this.itemName = itemName;
        this.itemNumber = itemNumber;
    }

    public void setItemIconResId(int itemIconResId) {
        this.itemIconResId = itemIconResId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public int getItemIconResId() {
        return itemIconResId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemNumber() {
        return itemNumber;
    }
}
