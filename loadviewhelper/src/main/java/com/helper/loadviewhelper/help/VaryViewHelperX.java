
package com.helper.loadviewhelper.help;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.helper.loadviewhelper.help.IVaryViewHelper;
import com.helper.loadviewhelper.help.VaryViewHelper;

/**
 * 用于切换布局,用一个新的布局覆盖在原布局之上
 *
 * @author LuckyJayce
 */
public class VaryViewHelperX implements IVaryViewHelper {

    private IVaryViewHelper helper;
    private View view;

    public VaryViewHelperX(View view) {
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
        View floatView = new View(view.getContext());
        frameLayout.addView(view, params);
        frameLayout.addView(floatView, params);
        helper = new VaryViewHelper(floatView);
    }

    @Override
    public View getCurrentLayout() {
        return helper.getCurrentLayout();
    }

    @Override
    public void restoreView() {
        helper.showLayout(view);
    }

    @Override
    public void showLayout(View view) {
        helper.showLayout(view);
    }

    @Override
    public void showLayout(int layoutId) {
        showLayout(inflate(layoutId));
    }

    @Override
    public View inflate(int layoutId) {
        return helper.inflate(layoutId);
    }

    @Override
    public Context getContext() {
        return helper.getContext();
    }

    @Override
    public View getView() {
        return view;
    }
}
