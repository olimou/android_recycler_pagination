package com.olimou.android.recycler_pagination;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private Handler            mHandler;
	private ExamplerPagination mPagination;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	private void getItens(int _index) {
		if (_index == 1) {
			mSwipeRefreshLayout.post(new Runnable() {
				@Override
				public void run() {
					mSwipeRefreshLayout.setRefreshing(true);
				}
			});
		}

		mHandler.sendEmptyMessageDelayed(_index, 5000);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

		RecyclerView lRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

		mPagination = new ExamplerPagination(20, new RecyclerViewPagination.PaginationListener() {
			@Override
			public void onPagination(int index) {
				getItens(index);
			}
		});

		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getItens(1);
			}
		});

		mPagination.setLoadingItem("loading", android.R.layout.simple_list_item_1);
		mPagination.setLastItem("lastitem", android.R.layout.simple_list_item_1);

		mHandler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				int lWhat = msg.what;

				ArrayList<String> lStrings = new ArrayList<>();

				int lI = 20;

				if (lWhat == 4) {
					lI = 15;
				}

				for (int i = 0; i < lI; i++) {
					String lItem = "Ola " + mPagination.getListItems().size();

					lStrings.add(lItem);
				}

				mSwipeRefreshLayout.setRefreshing(false);

				mPagination.addList(lStrings, lWhat);

				return true;
			}
		});

		lRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		lRecyclerView.setAdapter(mPagination);

		getItens(1);
	}
}
