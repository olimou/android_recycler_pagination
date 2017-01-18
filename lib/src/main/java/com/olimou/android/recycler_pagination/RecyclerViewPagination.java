package com.olimou.android.recycler_pagination;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmersonMoura on 9/2/16.
 */

public class RecyclerViewPagination<ViewHolder extends RecyclerView.ViewHolder, ListType extends Object> extends RecyclerView.Adapter<ViewHolder> {

	public static final String TAG = RecyclerViewPagination.class.getSimpleName();
	private final PaginationListener mPaginationListener;
	protected     List<ListType>     mListItems;
	protected     boolean            mLoading;
	protected     int                mPaginationIndex;
	protected     int                mPaginationSize;
	private       View               mStatusNull;

	public RecyclerViewPagination(int _paginationSize, PaginationListener _paginationListener) {
		mListItems = new ArrayList<>();
		mPaginationSize = _paginationSize;
		mPaginationListener = _paginationListener;
		mPaginationIndex = 1;
	}

	public void addItem(ListType _item) {
		mListItems.add(_item);

		verifyStatusNull();

		notifyItemInserted(mListItems.indexOf(_item));
	}

	public void addItem(ListType _listType, int _index) {
		mListItems.add(_index, _listType);

		notifyItemInserted(_index);
	}

	public void addList(List<? extends ListType> _listItems) {
		if (_listItems == null) {
			mPaginationIndex = -1;

			mListItems.clear();
		} else {
			mListItems.addAll(_listItems);

			if (mPaginationSize > _listItems.size()) {
				mPaginationIndex = -1;
			} else {
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

	@Override
	public int getItemCount() {
		return mListItems.size();
	}

	public List<ListType> getListItems() {
		return mListItems;
	}

	public void setListItems(List<ListType> _listItems) {
		mListItems = _listItems;
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
		mPaginationListener.onPagination(_i);

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
		return null;
	}

	public void removeItem(int _index) {
		getListItems().remove(_index);

		notifyItemRemoved(_index);

		verifyStatusNull();
	}

	public void updateItem(ListType _listType, int _index) {
		mListItems.remove(_index);

		mListItems.add(_index, _listType);

		notifyItemChanged(_index);
	}

	public void verifyStatusNull() {
		if (mStatusNull != null) {
			int lI = minSizeList();
			int lSize = getListItems().size();

			if (lSize <= lI) {
				mStatusNull.setVisibility(View.VISIBLE);
			} else {
				mStatusNull.setVisibility(View.GONE);
			}
		}
	}

	public interface PaginationListener {
		void onPagination(int index);
	}
}
