package com.olimou.android.recycler_pagination;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmersonMoura on 9/2/16.
 */

public abstract class RecyclerViewPagination<ViewHolder extends RecyclerView.ViewHolder, ListType extends Object> extends RecyclerView.Adapter<ViewHolder> {

	public static final String REFRESH_ACTION = "com.olimou.android.recycler_pagination.refresh";
	public static final String TAG            = RecyclerViewPagination.class.getSimpleName();
	public static final int    TYPE_LAST_POST = 1344;
	public static final int    TYPE_LOADING   = 1343;
	private final Context        mContext;
	protected List<ListType>     mListItems;
	protected boolean            mLoading;
	protected int                mPaginationIndex;
	protected int                mPaginationSize;
	private   ListType           mLastItem;
	private   int                mLastItemLayoutRes;
	private   ListType           mLoadingItem;
	private   int                mLoadingItemLayoutRes;
	private   View               mStatusNull;

	public RecyclerViewPagination(Context _context, int _paginationSize) {
		mContext = _context;
		mListItems = new ArrayList<>();
		mPaginationSize = _paginationSize;
		mPaginationIndex = 1;
	}

	public void addItem(ListType _item) {
		mListItems.add(_item);

		notifyItemInserted(mListItems.indexOf(_item));

		verifyStatusNull();
	}

	public void addItem(ListType _listType, int _index) {
		mListItems.add(_index, _listType);

		notifyItemInserted(_index);
	}

	public void addList(List<? extends ListType> _listItems) {
		int lIndex = mListItems.indexOf(mLoadingItem);

		mListItems.remove(mLoadingItem);

		if (lIndex >= 0) {
			notifyItemRemoved(lIndex);
		}

		if (_listItems == null) {
			mPaginationIndex = -1;

			mListItems.clear();
		} else {
			mListItems.addAll(_listItems);

			if (mPaginationSize > _listItems.size()) {
				mPaginationIndex = -1;

				if (mLastItem != null) {
					mListItems.add(mLastItem);
				}
			} else {
				if (mLoadingItem != null) {
					mListItems.add(mLoadingItem);
				}

				mPaginationIndex += 1;
			}
		}

		notifyDataSetChanged();

		verifyStatusNull();

		mLoading = false;
	}

	public void addList(List<? extends ListType> _listItems, int _page) {
		mPaginationIndex = _page;

		if (mPaginationIndex == 1) {
			mListItems.clear();
		}

		addList(_listItems);
	}

	public Context getContext() {
		return mContext;
	}

	@Override
	public int getItemCount() {
		return mListItems.size();
	}

	@Override
	public int getItemViewType(int position) {
		if (mListItems.get(position).equals(mLoadingItem)) {
			return TYPE_LOADING;
		} else if (mListItems.get(position).equals(mLastItem)) {
			return TYPE_LAST_POST;
		}

		return -1;
	}

	public ListType getLastItem() {
		return mLastItem;
	}

	public List<ListType> getListItems() {
		return mListItems;
	}

	public void setListItems(List<ListType> _listItems) {
		mListItems = _listItems;
	}

	public ListType getLoadingItem() {
		return mLoadingItem;
	}

	public int getPaginationIndex() {
		return mPaginationIndex;
	}

	public void setPaginationIndex(int _paginationIndex) {
		mPaginationIndex = _paginationIndex;
	}

	public int getPaginationSize() {
		return mPaginationSize;
	}

	public void setPaginationSize(int _paginationSize) {
		mPaginationSize = _paginationSize;
	}

	public View getStatusNull() {
		return mStatusNull;
	}

	public void setStatusNull(View _statusNull) {
		mStatusNull = _statusNull;

		mStatusNull.setVisibility(View.GONE);
	}

	public void loadMore(int _i) {
		getContext().sendBroadcast(new Intent(REFRESH_ACTION));

		mLoading = true;
	}

	public int minSizeList() {
		return 0;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if (getItemCount() > 0 && position == getItemCount() - 1 && mPaginationIndex >= 0 && !mLoading) {
			loadMore(mPaginationIndex);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater lInflater = LayoutInflater.from(parent.getContext());
		View lView;

		switch (viewType) {
			case TYPE_LOADING:
				lView = lInflater.inflate(mLoadingItemLayoutRes, parent, false);
				return (ViewHolder) new SimpleViewHolder(lView);
			case TYPE_LAST_POST:
				lView = lInflater.inflate(mLastItemLayoutRes, parent, false);
				return (ViewHolder) new SimpleViewHolder(lView);
		}

		return null;
	}

	public void removeItem(int _index) {
		getListItems().remove(_index);

		notifyItemRemoved(_index);

		verifyStatusNull();
	}

	public void setLastItem(ListType _lastItem, int _layoutRes) {
		mLastItem = _lastItem;
		mLastItemLayoutRes = _layoutRes;
	}

	public void setLoadingItem(ListType _loadingItem, int _loadingRes) {
		mLoadingItem = _loadingItem;
		mLoadingItemLayoutRes = _loadingRes;
	}

	public void updateItem(ListType _listType, int _index) {
		mListItems.remove(_index);

		mListItems.add(_index, _listType);

		notifyItemChanged(_index);
	}

	public void verifyStatusNull() {
		if (mStatusNull != null) {
			int lMinSizeList = minSizeList();
			int lListItemsSize = getListItems().size();

			if (lListItemsSize <= lMinSizeList) {
				mStatusNull.setVisibility(View.VISIBLE);
			} else {
				mStatusNull.setVisibility(View.GONE);
			}
		}
	}

	class SimpleViewHolder extends RecyclerView.ViewHolder {

		public SimpleViewHolder(View itemView) {
			super(itemView);
		}
	}
}
