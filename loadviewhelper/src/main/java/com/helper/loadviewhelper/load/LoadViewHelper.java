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
package com.helper.loadviewhelper.load;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.helper.loadviewhelper.R;
import com.helper.loadviewhelper.help.IVaryViewHelper;
import com.helper.loadviewhelper.help.OnLoadViewListener;
import com.helper.loadviewhelper.help.VaryViewHelper;

/**
 * Created by yangc on 2017/4/30.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated: 自定义要切换的布局，通过IVaryViewHelper实现真正的切换<br>
 *    使用者可以根据自己的需求，使用自己定义的布局样式
 */
public class LoadViewHelper implements OnClickListener {

    private IVaryViewHelper helper;
    private View loadError;
    private View loadEmpty;
    private View loadIng;
    private OnLoadViewListener listener;

    public LoadViewHelper(View view) {
        this(new VaryViewHelper(view));
    }

    public LoadViewHelper(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    /*****
     * /****
     * 显示错误页
     * 自定义内容
     *
     * @param errorText  错误内容
     * @param buttonText 错误按钮
     *****/
    public void showError(String errorText, String buttonText) {
        if (loadError == null) {
            loadError = helper.inflate(R.layout.load_error);
            if (errorText != null) {
                TextView textView = (TextView) loadError.findViewById(R.id.load_error_id_text);
                textView.setText(errorText);
            }
            if (buttonText != null) {
                Button button = (Button) loadError.findViewById(R.id.load_error_id_btn);
                button.setText(buttonText);
            }
        }
        if (loadError.findViewById(R.id.load_error_id_btn) != null) {
            loadError.findViewById(R.id.load_error_id_btn).setOnClickListener(this);
        } else {
            loadError.setOnClickListener(this);
        }
        helper.showLayout(loadError);
    }

    /****
     * 显示错误页
     * 默认
     ***/
    public void showError() {
        showError(null, null);
    }

    /**
     * 显示空白页
     *
     * @param errorText  错误内容
     * @param buttonText 错误按钮内容
     *****/
    public void showEmpty(String errorText, String buttonText) {
        if (loadEmpty == null) {
            loadEmpty = helper.inflate(R.layout.load_empty);
            if (errorText != null) {
                TextView textView = (TextView) loadEmpty.findViewById(R.id.load_empty_id_text);
                textView.setText(errorText);
            }
            if (buttonText != null) {
                Button button = (Button) loadEmpty.findViewById(R.id.load_empty_id_btn);
                button.setText(buttonText);
            }
        }
        if (loadEmpty.findViewById(R.id.load_empty_id_btn) != null) {
            loadEmpty.findViewById(R.id.load_empty_id_btn).setOnClickListener(this);
        } else {
            loadEmpty.setOnClickListener(this);
        }
        helper.showLayout(loadEmpty);
    }

    /****
     * 显示空白页
     ***/
    public void showEmpty() {
        showEmpty(null, null);
    }

    /***
     * 没有加载文本
     * 自定义内容
     *
     * @param loadText 页面在加载时显示文本
     **/
    public void showLoading(String loadText) {
        if (loadIng == null) {
            loadIng = helper.inflate(R.layout.load_ing);
            TextView textView = (TextView) loadIng.findViewById(R.id.load_ing_id_text);
            textView.setText(loadText);
        }
        helper.showLayout(loadIng);
    }

    /***
     * 没有加载文本
     **/
    public void showLoading() {
        showLoading(null);
    }

    public void restore() {
        helper.restoreView();
    }

    public View getLoadError() {
        return loadError;
    }

    public void setLoadError(View loadError) {
        this.loadError = loadError;
    }

    public void setLoadError(int loadErrorRes) {
        this.loadError = helper.inflate(loadErrorRes);
    }

    public View getLoadEmpty() {
        return loadEmpty;
    }

    public void setLoadEmpty(View loadEmpty) {
        this.loadEmpty = loadEmpty;
    }

    public void setLoadEmpty(int loadEmptyRes) {
        this.loadEmpty = helper.inflate(loadEmptyRes);
        ;
    }

    public View getLoadIng() {
        return loadIng;
    }

    public void setLoadIng(View loadIng) {
        this.loadIng = loadIng;
    }

    public void setLoadIng(int loadIngRes) {
        this.loadIng = helper.inflate(loadIngRes);
    }

    public OnLoadViewListener getListener() {
        return listener;
    }

    public void setListener(OnLoadViewListener listener) {
        this.listener = listener;
    }


    public void onDestroy() {
        loadError = null;
        loadEmpty = null;
        loadIng = null;
        listener = null;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onRetryClick();
        }
    }
}
