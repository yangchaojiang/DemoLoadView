
package com.helper.loadviewhelper.load;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.helper.loadviewhelper.R;
import com.helper.loadviewhelper.help.OnLoadViewListener;
import com.helper.loadviewhelper.help.VaryViewHelperX;

/**
 * Created by yangc on 2017/4/30.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated: 自定义要切换的布局，通过IVaryViewHelper实现真正的切换<br>
 * 使用者可以根据自己的需求，使用自己定义的布局样式
 */
public class LoadViewHelper implements OnClickListener {
    private VaryViewHelperX helper;
    private View loadError;
    private View loadEmpty;
    private View loadIng;
    private OnLoadViewListener listener;
    private volatile static Builder builder = new Builder();

    public LoadViewHelper(@NonNull View view) {
        this(new VaryViewHelperX(view));
    }

    private LoadViewHelper(@Nullable VaryViewHelperX helper) {
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
    public void showError(@Nullable String errorText, @Nullable String buttonText) {
        if (loadError == null) {
            if (builder.loadError == 0) {
                loadError = helper.inflate(R.layout.load_error);
                if (errorText != null) {
                    TextView textView = loadError.findViewById(R.id.load_error_id_text);
                    textView.setText(errorText);
                }
                if (buttonText != null) {
                    Button button = loadError.findViewById(R.id.load_error_id_btn);
                    button.setText(buttonText);
                }
            } else {
                loadError = helper.inflate(builder.loadError);
            }
            loadError.setTag(loadError.getClass().getName());
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
    public void showEmpty(@Nullable String errorText, @Nullable String buttonText) {
        if (loadEmpty == null) {
            if (builder.loadEmpty == 0) {
                loadEmpty = helper.inflate(R.layout.load_empty);
                if (errorText != null) {
                    TextView textView = loadEmpty.findViewById(R.id.load_empty_id_text);
                    textView.setText(errorText);
                }
                if (buttonText != null) {
                    Button button = loadEmpty.findViewById(R.id.load_empty_id_btn);
                    button.setText(buttonText);
                }
            } else {
                loadEmpty = helper.inflate(builder.loadEmpty);
            }
            loadEmpty.setTag(loadEmpty.getClass().getName());
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
            if (builder.loadIng == 0) {
                loadIng = helper.inflate(R.layout.load_ing);
                TextView textView = loadIng.findViewById(R.id.load_ing_id_text);
                textView.setText(loadText);
            } else {
                loadIng = helper.inflate(builder.loadIng);
            }
            loadIng.setTag(loadIng.getClass().getName());
        }
        helper.showLayout(loadIng);
    }

    /***
     * 没有加载文本
     **/
    public void showLoading() {
        showLoading(null);
    }

    public void showContent() {
        helper.restoreView();
    }

    @NonNull
    public View getLoadError() {
        return loadError;
    }

    public void setLoadError(@NonNull View loadError) {
        this.loadError = loadError;
    }

    public void setLoadError(@IdRes int loadErrorRes) {
        this.loadError = helper.inflate(loadErrorRes);
    }

    @NonNull
    public View getLoadEmpty() {
        return loadEmpty;
    }

    public void setLoadEmpty(@NonNull View loadEmpty) {
        this.loadEmpty = loadEmpty;
    }

    public void setLoadEmpty(@IdRes int loadEmptyRes) {
        this.loadEmpty = helper.inflate(loadEmptyRes);
    }

    @NonNull
    public View getLoadIng() {
        return loadIng;
    }

    public void setLoadIng(@NonNull View loadIng) {
        this.loadIng = loadIng;
    }

    public void setLoadIng(@IdRes int loadIngRes) {
        this.loadIng = helper.inflate(loadIngRes);
    }

    public void setListener(@NonNull OnLoadViewListener listener) {
        this.listener = listener;
    }


    public void onDestroy() {
        if (helper != null) {
            helper.release();
        }
        loadError = null;
        loadEmpty = null;
        loadIng = null;
        listener = null;
        helper=null;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onRetryClick();
        }
    }

    /***
     * 全部配置类
     * ***/
    public static final class Builder {
        //全局配置加载
        int loadIng;
        //全局配置为空
        int loadEmpty;
        //全局配置错误
        int loadError;

        private Builder() {

        }

        public Builder setLoadIng(int loadIng) {
            this.loadIng = loadIng;
            return this;
        }

        public Builder setLoadEmpty(int loadEmpty) {
            this.loadEmpty = loadEmpty;
            return this;
        }

        public Builder setLoadError(int loadError) {
            this.loadError = loadError;
            return this;
        }
    }

    public static Builder getBuilder() {
        return builder;
    }

}
