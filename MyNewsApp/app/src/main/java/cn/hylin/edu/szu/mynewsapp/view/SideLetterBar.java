package cn.hylin.edu.szu.mynewsapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import cn.hylin.edu.szu.mynewsapp.R;

/**
 * Author：林恒宜 on 16-7-17 12:45
 * Email：hylin601@126.com
 * Github：https://github.com/hengyilin
 * Version：1.0
 * Description : 自定义view侧边的字母栏
 */
public class SideLetterBar extends View{
    private final String[] option = new String[] {"定位","热门","A","B","C","D","E","F","G","H","I",
            "J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private TextView tvOverlay;
    private boolean showBg = false;
    private int choose = -1;
    private Paint paint = new Paint();
    private OnLetterChangedListener onLetterChangedListener;

    public SideLetterBar(Context context) {
        super(context);
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideLetterBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置悬浮窗
     * @param tvOverlay
     */
    public void setTvOverlay(TextView tvOverlay) {
        this.tvOverlay = tvOverlay;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBg) {
            canvas.drawColor(Color.WHITE);
        }
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / option.length;
        for (int i = 0; i < option.length; i ++) {
            paint.setColor(getResources().getColor(R.color.black));
            paint.setTextSize(26);
            paint.setAntiAlias(true);
            if (choose == i) {
                paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            }
            //确定绘制的位置
            float xPos = width/2 - paint.measureText(option[i])/2;
            float yPos = singleHeight * (1 + i);

            canvas.drawText(option[i],xPos,yPos,paint);//绘制文字

            paint.reset();// 重置画笔便于下次再绘制
        }
    }

    /**
     * Pass the touch screen motion event down to the target view, or this view if it is the target.
     * 把屏幕触摸事件传递到目标View中去，或者自己处理
     * @param event 屏幕触摸事件
     * @return true代表自己处理不传递事件；false代表把事件传递下去自己不处理
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnLetterChangedListener listener = onLetterChangedListener;
        final int c = (int) (y / getHeight() *option.length); // 获得按下时按中的是哪一个view

        switch (action) {
            case MotionEvent.ACTION_DOWN://按下事件
                showBg = true;//显示背景
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < option.length) {
                        listener.OnLetterChange(option[c]);
                        choose = c;
                        invalidate();// 通知重绘
                        if (tvOverlay != null) {
                            tvOverlay.setVisibility(VISIBLE);
                            tvOverlay.setText(option[c]);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE://移动事件
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < option.length) {
                        listener.OnLetterChange(option[c]);
                        choose = c;
                        invalidate();
                        if (tvOverlay != null) {
                            tvOverlay.setVisibility(VISIBLE);
                            tvOverlay.setText(option[c]);
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_UP://抬起事件（重置状态）
                showBg = false;
                choose = -1;
                invalidate();//通知重绘
                if (tvOverlay != null) {
                    tvOverlay.setVisibility(INVISIBLE);
                }
                break;
        }
        return true;
    }

    /**
     * 回调触摸事件
     * @param event 触摸事件
     * @return 是否拦截处理事件 （true：拦截 false：不拦截 ）
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener) {
        this.onLetterChangedListener = onLetterChangedListener;
    }
    public interface OnLetterChangedListener {
        void OnLetterChange (String letter);
    }
}
