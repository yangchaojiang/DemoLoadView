package com.example.com.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.helper.loadviewhelper.load.LoadViewHelper;
import com.helper.loadviewhelper.help.OnLoadViewListener;


public class Example1Activity extends AppCompatActivity {
	private int type;
	private ListView listView;
	private LoadDataTask task;
	private LoadViewHelper helper;
	private ArrayAdapter<String> adapter;
	private List<String> data = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example1);
		listView =(ListView) findViewById(R.id.listView1);
		helper = new LoadViewHelper(listView);
		listView.setAdapter(adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
		type=1;
		task = new LoadDataTask();
		helper.showLoading();
		helper.showContent();
		//task.execute();
		helper.setListener(new OnLoadViewListener() {
			@Override
			public void onRetryClick() {
				task = new LoadDataTask();
				task.execute();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (task != null) {
			task.cancel(true);
		}
	}

	public void showLoadError(View view) {
		if (task != null) {
			task.cancel(true);
		}
		type=1;
		task = new LoadDataTask();
		task.execute();
	}

	public void showLoadEmpty(View view) {
		if (task != null) {
			task.cancel(true);
		}
		type=2;
		task = new LoadDataTask();
		task.execute();
	}

	public void showLoadSuccess(View view) {
		if (task != null) {
			task.cancel(true);
		}
		type=0;
		task = new LoadDataTask();
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
				// 模拟2秒到5秒的等待时间
				SystemClock.sleep(3000);
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
				helper.showContent();
				adapter.notifyDataSetChanged();
			}
		}

	}
}
