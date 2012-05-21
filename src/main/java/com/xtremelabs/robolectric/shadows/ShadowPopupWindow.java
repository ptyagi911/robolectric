package com.xtremelabs.robolectric.shadows;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.internal.RealObject;

@SuppressWarnings({"UnusedDeclaration"})
@Implements(PopupWindow.class)
public class ShadowPopupWindow {
    @RealObject
    protected PopupWindow realPopupWindow;
    private View contentView;
    private int width;
    private int height;
    private boolean focusable;
    private boolean touchable;
    private boolean outSideTouchable;
    private boolean showing;
    private Drawable background;
    private View.OnTouchListener onTouchInterceptor;
    private WindowManager windowManager;
    private Context context;

    public void __constructor__(View contentView, int width, int height, boolean focusable) {
        this.contentView = contentView;
        context = contentView.getContext();
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Implementation
    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    @Implementation
    public View getContentView() {
        return contentView;
    }

    @Implementation
    public void setWidth(int width) {
        this.width = width;
    }

    @Implementation
    public int getWidth() {
        return width;
    }

    @Implementation
    public void setHeight(int height) {
        this.height = height;
    }

    @Implementation
    public int getHeight() {
        return height;
    }

    @Implementation
    public void setFocusable(boolean focusable) {
        this.focusable = focusable;
    }

    @Implementation
    public boolean isFocusable() {
        return focusable;
    }

    @Implementation
    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }

    @Implementation
    public boolean isTouchable() {
        return touchable;
    }

    @Implementation
    public void setOutsideTouchable(boolean touchable) {
        outSideTouchable = touchable;
    }

    @Implementation
    public boolean isOutsideTouchable() {
        return outSideTouchable;
    }

    /**
     * non-android setter for testing
     *
     * @param showing true if popup window is showing
     */
    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    @Implementation
    public boolean isShowing() {
        return showing;
    }

    @Implementation
    public void dismiss() {
        showing = false;
    }

    @Implementation
    public void setBackgroundDrawable(Drawable background) {
        this.background = background;
    }

    @Implementation
    public Drawable getBackground() {
        return background;
    }

    @Implementation
    public void setTouchInterceptor(android.view.View.OnTouchListener l) {
        onTouchInterceptor = l;
    }

    @Implementation
    public void showAsDropDown(View anchor) {
        LinearLayout container = new LinearLayout(context);
        container.addView(contentView);
        windowManager.addView(container, null);
    }

    public boolean dispatchTouchEvent(MotionEvent e) {
        return onTouchInterceptor != null && onTouchInterceptor.onTouch(realPopupWindow.getContentView(), e);
    }
}