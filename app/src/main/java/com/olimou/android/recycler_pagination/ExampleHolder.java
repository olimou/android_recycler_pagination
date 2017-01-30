package com.olimou.android.recycler_pagination;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by EmersonMoura on 30/01/17.
 */

public class ExampleHolder extends RecyclerView.ViewHolder {

	public ExampleHolder(View itemView) {
		super(itemView);
	}

	public void setup(String _s) {
		TextView lTextView = (TextView) itemView.findViewById(android.R.id.text1);

		lTextView.setText(_s);
	}
}
