
package com.helper.loadviewhelper.help;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;


/**
 * 用于切换布局,用一个新的布局覆盖在原布局之上
 *
 * @author LuckyJayce
 */
public class VaryViewHelperX {

    private IVaryViewHelper helper;
    private View view;

    public VaryViewHelperX(@NonNull View view) {
        super();
        this.view = view;
        ViewGroup group = (ViewGroup) view.getParent();
        LayoutParams layoutParams = view.getLayoutParams();
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        if (group != null) {
            group.removeView(view);
            group.addView(frameLayout, layoutParams);
        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        frameLayout.addView(view, params);
        helper = new VaryViewHelper(view);
    }


    public View getCurrentLayout() {
        return view;
    }


    public void restoreView() {
        helper.showLayout(view);
    }

    public void showLayout(@NonNull View view) {
        helper.showLayout(view);
    }

    public void showLayout( int layoutId) {
        showLayout(inflate(layoutId));
    }

    public View inflate( int layoutId) {
        return helper.inflate(layoutId);
    }

    public Context getContext() {
        return helper.getContext();
    }


    public View getView() {
        return view;
    }

    public void release() {
        if (helper != null) {
            helper.release();
        }
    }
}
