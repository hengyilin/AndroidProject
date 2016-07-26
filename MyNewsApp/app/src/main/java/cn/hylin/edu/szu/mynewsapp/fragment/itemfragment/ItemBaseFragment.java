package cn.hylin.edu.szu.mynewsapp.fragment.itemfragment;

import android.support.v4.app.Fragment;

/**
 * Author：林恒宜 on 16-7-15 17:06
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description :
 */
public abstract class ItemBaseFragment extends Fragment{

    protected boolean isVisiable;//当前状态是否可见

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisiable = true;
            onVisiable();
        }
        else {
            isVisiable = false;
            onInvisiable();
        }
    }

    //设置不可见时执行的方法 这里什么都不做
    protected void onInvisiable(){}
    //设置可见时执行的方法——懒加载（子类必须自己实现懒加载的方法）
    protected void onVisiable() {
        lazyLoad();
    }

    /**
     * 懒加载方法，子类必须实现此方法
     */
    protected abstract void lazyLoad();
}
