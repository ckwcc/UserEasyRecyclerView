package com.ckw.zfsoft.useeasyrecyclerview.targetactivity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.ViewGroup
import android.widget.CheckBox
import com.ckw.zfsoft.useeasyrecyclerview.DataProvider
import com.ckw.zfsoft.useeasyrecyclerview.R
import com.ckw.zfsoft.useeasyrecyclerview.entites.Person
import com.ckw.zfsoft.useeasyrecyclerview.viewholder.PersonViewHolder
import com.github.clans.fab.FloatingActionButton
import com.jude.easyrecyclerview.EasyRecyclerView
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.jude.easyrecyclerview.decoration.DividerDecoration
import com.jude.rollviewpager.Util

/**
 * 下拉刷新上拉加载更多功能
 */
class RefreshMoreActivity : AppCompatActivity(),RecyclerArrayAdapter.OnLoadMoreListener , SwipeRefreshLayout.OnRefreshListener{

    private var mRecyclerView: EasyRecyclerView? = null
    private var mGoTop: FloatingActionButton? = null//悬浮控件
    private val mHander: Handler = Handler()//用于刷新和加载更多
    private var mAdapter: RecyclerArrayAdapter<Person>? = null//数据源
    private var hasNetWork = true
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh_more)

        initView()
        initEasyRecyclerView()
        initTop()

    }

    private fun initView(){
        mRecyclerView = findViewById<EasyRecyclerView>(R.id.recyclerView)
        mGoTop = findViewById<FloatingActionButton>(R.id.btn_toTop)
    }

    private fun initTop() {
        mGoTop!!.setOnClickListener { mRecyclerView!!.scrollToPosition(0) }
    }

    private fun initEasyRecyclerView() {
        //模式为线性布局
        val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        mRecyclerView !!.setLayoutManager(layoutManager)
        //分割线
        val dividerDecoration: DividerDecoration = DividerDecoration(Color.GRAY, Util.dip2px(this, 16f), Util.dip2px(this, 72f), 0)
        dividerDecoration.setDrawLastItem(false)
        mRecyclerView !!.addItemDecoration(dividerDecoration)

        //类似于java的匿名内部类，需要用到object关键字
        mAdapter = object : RecyclerArrayAdapter<Person>(this) {
            override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
                return PersonViewHolder(parent)
            }
        }

        mRecyclerView!!.setAdapterWithProgress(mAdapter)

        //设置底部加载更多布局
        mAdapter!!.setMore(R.layout.view_more,this)
        //设置底部没有更多布局
        mAdapter!!.setNoMore(R.layout.view_nomore)
        //长按删除
        mAdapter!!.setOnItemLongClickListener { position ->
            mAdapter!!.remove(position)
            true
        }

        //设置无网络布局
        mAdapter!!.setError(R.layout.view_error, object : RecyclerArrayAdapter.OnErrorListener {
            override fun onErrorShow() {
                mAdapter!!.resumeMore()
            }

            override fun onErrorClick() {
                mAdapter!!.resumeMore()
            }
        })

        //设置刷新监听
        mRecyclerView!!.setRefreshListener(this)

        onRefresh()
    }

    override fun onRefresh() {
        page = 0
        mHander.postDelayed(Runnable {
            mAdapter!!.clear()
            if (!hasNetWork){
                mAdapter!!.pauseMore()
                return@Runnable
            }
            mAdapter!!.addAll(DataProvider.getPersonList(page))
            page = 1

        },2000)
    }

    override fun onLoadMore() {
        mHander.postDelayed(Runnable {
            if (!hasNetWork) {
                mAdapter!!.pauseMore()
                return@Runnable
            }
            mAdapter!!.addAll(DataProvider.getPersonList(page))
            page++
        }, 2000)
    }

    /**
     * 模拟网络开关
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu.findItem(R.id.checkbox)
        val box = item.actionView as CheckBox
        box.isChecked = true
        box.text = "网络"
        box.setOnCheckedChangeListener { buttonView, isChecked -> hasNetWork = isChecked }
        return true
    }
}
