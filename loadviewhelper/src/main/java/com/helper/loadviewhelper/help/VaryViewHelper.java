/*
Copyright 2015 shizhefei（LuckyJayce）
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.helper.loadviewhelper.help;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;


/**
 * 用于切换布局,用一个新的布局替换掉原先的布局
 *
 * @author LuckyJayce
 */
class VaryViewHelper implements IVaryViewHelper {
    private View currentView;
    private ViewGroup parentView;
    private ViewGroup.LayoutParams params;
    private boolean isLoad;
    private ViewPropertyAnimatorCompat animatorCompat;
    private ViewPropertyAnimatorCompat animatorCompat2;

    VaryViewHelper(@NonNull View view) {
        super();
        this.currentView = view;
        currentView.setTag(currentView.getClass().getName());
        init();
    }

    /***
     *初始化
     * **/
    private void init() {
        params = currentView.getLayoutParams();
        if (currentView.getParent() != null) {
            parentView = (ViewGroup) currentView.getParent();
        } else {
            parentView = currentView.getRootView().findViewById(android.R.id.content);
        }
    }

    @Override
    public View getCurrentLayout() {
        return currentView;
    }

    @Override
    public void restoreView() {
        showLayout(currentView);
    }

    @Override
    public void showLayout(@NonNull View view) {
        if (parentView == null) {
            return;
        }
        // 如果已经是那个view，那就不需要再进行替换操作了
        View view1 = parentView.getChildAt(0);
        if (view1 != null && !view1.toString().equals(view.toString())) {
            final ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            //第一次进入也不需要动画
            if (!isLoad) {
                isLoad = true;
                parentView.removeAllViews();
                parentView.addView(view, params);
            } else {
                release();
                view.setAlpha(0);
                animatorCompat = ViewCompat.animate(view1)
                        .alpha(0)
                        .setDuration(400)
                        .setListener(new ViewPropertyAnimator(this,view));
                animatorCompat.start();
                animatorCompat2 = ViewCompat
                        .animate(view)
                        .alpha(1)
                        .setDuration(800)
                        .setStartDelay(400);
                animatorCompat2.start();
            }

        }
    }

    @Override
    public void showLayout(int layoutId) {
        showLayout(inflate(layoutId));
    }

    @Override
    public View inflate(int layoutId) {
        return LayoutInflater.from(currentView.getContext()).inflate(layoutId, null);
    }

    @Override
    public Context getContext() {
        return currentView.getContext();
    }

    @Override
    public View getView() {
        return currentView;
    }
    @Override
    public void release() {
        if (animatorCompat != null) {
            animatorCompat.cancel();
            animatorCompat.setListener(null);
        }
        if (animatorCompat2 != null) {
            animatorCompat2.cancel();
        }
    }

    class ViewPropertyAnimator implements ViewPropertyAnimatorListener {
        private WeakReference<VaryViewHelper> ssl;
        private View v;

        ViewPropertyAnimator(VaryViewHelper varyViewHelper, View view) {
            ssl = new WeakReference<>(varyViewHelper);
            v = view;

        }

        @Override
        public void onAnimationStart(View view) {

        }

        @Override
        public void onAnimationEnd(View view) {
            if (ssl.get() == null || view == null) {
                return;
            }
            parentView.removeAllViews();
            parentView.addView(v, params);
        }

        @Override
        public void onAnimationCancel(View view) {
            if (ssl.get() == null || view == null) {
                return;
            }
            parentView.removeAllViews();
            parentView.addView(v, params);
        }
    }

}
