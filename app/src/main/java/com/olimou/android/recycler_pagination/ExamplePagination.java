package com.olimou.android.recycler_pagination;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by EmersonMoura on 30/01/17.
 */

public class ExamplePagination extends RecyclerViewPagination<RecyclerView.ViewHolder, String> {

	public ExamplePagination(int _paginationSize, PaginationListener _paginationListener) {
		super(_paginationSize, _paginationListener);
	}

	@Override
	public int getItemViewType(int position) {
		return super.getItemViewType(position);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		super.onBindViewHolder(holder, position);

		((ExampleHolder) holder).setup(getListItems().get(position));
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		LayoutInflater lInflater = LayoutInflater.from(parent.getContext());

		View lView = lInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

		return new ExampleHolder(lView);
	}
}
