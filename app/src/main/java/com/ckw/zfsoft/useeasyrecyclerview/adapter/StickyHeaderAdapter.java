package com.ckw.zfsoft.useeasyrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ckw.zfsoft.useeasyrecyclerview.R;
import com.jude.easyrecyclerview.decoration.StickyHeaderDecoration;

/**
 * Created by ckw
 * on 2017/12/7.
 * 悬浮headerAdapter
 */

public class StickyHeaderAdapter implements StickyHeaderDecoration.IStickyHeaderAdapter<StickyHeaderAdapter.HeaderHolder>{

    private LayoutInflater mLayoutInflater;

    public StickyHeaderAdapter(Context context) {
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public long getHeaderId(int position) {
        return position/4;
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mLayoutInflater.inflate(R.layout.header_item,parent,false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        viewholder.header.setText("第"+getHeaderId(position)+"组");
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView;
        }
    }
}
