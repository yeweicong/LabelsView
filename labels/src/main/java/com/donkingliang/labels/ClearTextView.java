package com.donkingliang.labels;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class ClearTextView<T> extends TextView {
    public ClearTextView(Context context) {
        super(context);
        init(context);
    }

    public ClearTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClearTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ClearTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private Context mContext;
    private boolean isEnableClear;
    private T mData;
    private Drawable mClearDrawable;
    private OnTextClearListener<T> mOnTextClearListener;

    private void init(Context context) {
        mContext = context;
    }

    public void setClearDrawable(Drawable clearDrawable){
        mClearDrawable = clearDrawable;
        mClearDrawable.setBounds(0, 0, mClearDrawable.getMinimumWidth(), mClearDrawable.getMinimumHeight());
    }

    public void setData(T t){
        mData = t;
    }

    public void setEnableClear(boolean enableClear) {
        isEnableClear = enableClear;
    }

    public void setTextClear(CharSequence text){
        setText(text);
        if (!TextUtils.isEmpty(text) && isEnableClear && mClearDrawable != null) {
            setCompoundDrawables(null, null, mClearDrawable, null);
        } else {
            setCompoundDrawables(null, null, null, null);
        }
    }

    public void setOnTextClearListener(OnTextClearListener onTextClearListener) {
        mOnTextClearListener = onTextClearListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP && isEnableClear && mClearDrawable != null){
            if (event.getX() <= (getWidth() - getPaddingRight()) && event.getX() >= (getWidth() - getPaddingRight() - mClearDrawable.getBounds().width())) {
                if(mOnTextClearListener != null){
                    mOnTextClearListener.onTextClear(mData);
                }
            }
        }

        return super.onTouchEvent(event);
    }

    public interface OnTextClearListener<T>{
        void onTextClear(T t);
    }
}
