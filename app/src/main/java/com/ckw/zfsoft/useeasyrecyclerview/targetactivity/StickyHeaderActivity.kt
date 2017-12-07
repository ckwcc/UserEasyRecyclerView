package com.ckw.zfsoft.useeasyrecyclerview.targetactivity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import com.ckw.zfsoft.useeasyrecyclerview.DataProvider
import com.ckw.zfsoft.useeasyrecyclerview.R
import com.ckw.zfsoft.useeasyrecyclerview.adapter.StickyHeaderAdapter
import com.ckw.zfsoft.useeasyrecyclerview.entites.Person
import com.ckw.zfsoft.useeasyrecyclerview.viewholder.PersonViewHolder
import com.github.clans.fab.FloatingActionButton
import com.jude.easyrecyclerview.EasyRecyclerView
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.jude.easyrecyclerview.decoration.DividerDecoration
import com.jude.easyrecyclerview.decoration.StickyHeaderDecoration
import com.jude.rollviewpager.Util

class StickyHeaderActivity : AppCompatActivity() , RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{


    private var mRecyclerView: EasyRecyclerView? = null
    private var mGoTop: FloatingActionButton? = null
    private val mHandler: Handler = Handler()
    private var mAdapter: RecyclerArrayAdapter<Person>? = null

    private var mHasNet: Boolean = true
    private var mPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticky_header)

        initView()
        initAdapter()
        initEasyRecyclerView()
        initFloatActionButton()

    }

    private fun initView() {
        mRecyclerView = findViewById(R.id.stickyRecyclerView)
        mGoTop = findViewById(R.id.stickyTop)
    }

    private fun initAdapter() {
        mAdapter = object :RecyclerArrayAdapter<Person>(this){
            override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> {
                return PersonViewHolder(parent)
            }
        }

        mAdapter!!.setMore(R.layout.view_more,this)
        mAdapter!!.setNoMore(R.layout.view_nomore,object : RecyclerArrayAdapter.OnNoMoreListener{
            override fun onNoMoreShow() {
                mAdapter!!.resumeMore()
            }

            override fun onNoMoreClick() {
                mAdapter!!.resumeMore()
            }
        })

        mAdapter!!.setOnItemLongClickListener(RecyclerArrayAdapter.OnItemLongClickListener {

            position ->
            mAdapter!!.remove(position)
            return@OnItemLongClickListener true
        })

        mAdapter!!.setError(R.layout.view_error,object : RecyclerArrayAdapter.OnErrorListener{
            override fun onErrorClick() {
                mAdapter!!.resumeMore()
            }

            override fun onErrorShow() {
                mAdapter!!.resumeMore()
            }

        })
    }

    private fun initEasyRecyclerView() {
        //设置线性管理器
        mRecyclerView!!.setLayoutManager(LinearLayoutManager(this))
        //分割线
        val itemDecoration = DividerDecoration(Color.GRAY, Util.dip2px(this, 0.5f), Util.dip2px(this, 72f), 0)
        itemDecoration.setDrawLastItem(false)
        mRecyclerView!!.addItemDecoration(itemDecoration)

        val decoration = StickyHeaderDecoration(StickyHeaderAdapter(this))
        decoration.setIncludeHeader(false)
        mRecyclerView!!.addItemDecoration(decoration)

        mRecyclerView!!.setAdapterWithProgress(mAdapter)

        mRecyclerView!!.setRefreshListener(this)
        onRefresh()

    }

    private fun initFloatActionButton() {
        mGoTop!!.setOnClickListener(View.OnClickListener { mRecyclerView!!.scrollToPosition(0) })
    }

    override fun onLoadMore() {
        mHandler!!.postDelayed(Runnable {

            //刷新
            if (!mHasNet) {
                mAdapter!!.pauseMore()
                return@Runnable
            }
            mAdapter!!.addAll(DataProvider.getPersonList(mPage))
            mPage++
        },2000)
    }

    override fun onRefresh() {
        mPage = 0
        mHandler.postDelayed(Runnable {
            mAdapter!!.clear()
            //刷新
            if (!mHasNet) {
                mAdapter!!.pauseMore()
                return@Runnable
            }
            mAdapter!!.addAll(DataProvider.getPersonList(mPage))
            mPage = 1
        }, 2000)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu!!.findItem(R.id.checkbox)
        val box = item.actionView as CheckBox
        box.isChecked = true
        box.text = "网络"
        box.setOnCheckedChangeListener { buttonView, isChecked -> mHasNet = isChecked }
        return true
    }
}
