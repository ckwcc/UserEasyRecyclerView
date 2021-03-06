package com.ckw.zfsoft.useeasyrecyclerview.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.ckw.zfsoft.useeasyrecyclerview.entites.Person;
import com.ckw.zfsoft.useeasyrecyclerview.viewholder.PersonViewHolder;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Mr.Jude on 2015/7/18.
 */
public class PersonAdapter extends RecyclerArrayAdapter<Person> {
    public PersonAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PersonViewHolder(parent);
    }
}
