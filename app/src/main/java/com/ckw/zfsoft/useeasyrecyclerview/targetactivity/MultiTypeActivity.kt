package com.ckw.zfsoft.useeasyrecyclerview.targetactivity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ckw.zfsoft.useeasyrecyclerview.DataProvider
import com.ckw.zfsoft.useeasyrecyclerview.R
import com.ckw.zfsoft.useeasyrecyclerview.adapter.PersonWithAdAdapter
import com.jude.easyrecyclerview.EasyRecyclerView
import com.jude.easyrecyclerview.decoration.DividerDecoration
import com.jude.rollviewpager.Util

class MultiTypeActivity : AppCompatActivity() {

    private var recyclerView: EasyRecyclerView? = null
    private var adapter: PersonWithAdAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_type)
        recyclerView = findViewById<EasyRecyclerView>(R.id.recyclerView)
        recyclerView!!.setLayoutManager(LinearLayoutManager(this))
        //设置网络请求的时候的progress
        recyclerView!!.setProgressView(R.layout.view_progress)
        //设置分割线
        val itemDecoration = DividerDecoration(Color.GRAY, Util.dip2px(this, 0.5f), Util.dip2px(this, 72f), 0)
        recyclerView!!.addItemDecoration(itemDecoration)
        adapter = PersonWithAdAdapter(this)
        adapter!!.addAll(DataProvider.getPersonWithAds(0))

        recyclerView!!.setAdapterWithProgress(adapter)
    }
}
