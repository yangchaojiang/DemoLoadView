package com.example.com.myapplication.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.com.myapplication.Example1Activity;
import com.example.com.myapplication.R;
import com.helper.loadviewhelper.help.OnLoadViewListener;
import com.helper.loadviewhelper.load.LoadViewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleFragment extends Fragment {
    LoadViewHelper helper;
    int type;
    private ListView listView;
    private LoadDataTask task;
    private ArrayAdapter<String> adapter;
    private List<String> data = new ArrayList<>();
    Random rand;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_layot, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listView1);
        helper = new LoadViewHelper(listView);
        listView.setAdapter(adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data));
        helper = new LoadViewHelper(listView);
        helper.showLoading();
        rand = new Random();
        type = rand.nextInt(3);
        helper.setLoadEmpty(R.layout.this_empty);
        helper.setLoadError(R.layout.this_error);
        helper.setListener(new OnLoadViewListener() {
            @Override
            public void onRetryClick() {
                type = rand.nextInt(3);
                task = new SimpleFragment.LoadDataTask();
                task.execute();
            }
        });
        task = new SimpleFragment.LoadDataTask();
        task.execute();

    }

    private class LoadDataTask extends AsyncTask<Void, Void, List<String>> {


        public LoadDataTask() {
            super();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            helper.showLoading("加载中...");
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                // 模拟2秒到5秒的等待时间
                Thread.sleep((new Random().nextInt(10) + 20) * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (type == 1) {
                return null;
            } else if (type == 2) {
                return new ArrayList<String>(0);
            }
            List<String> strings = new ArrayList<String>();
            for (int i = 0; i < 20; i++) {
                strings.add("数据" + i);
            }
            return strings;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            if (result == null) {
                helper.showError();
            } else if (result.isEmpty()) {
                helper.showEmpty();
            } else {
                data.clear();
                data.addAll(result);
                helper.restore();
                adapter.notifyDataSetChanged();
            }
        }

    }
}
