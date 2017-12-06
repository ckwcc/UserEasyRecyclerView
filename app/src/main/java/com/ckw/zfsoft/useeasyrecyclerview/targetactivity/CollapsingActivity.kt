package com.ckw.zfsoft.useeasyrecyclerview.targetactivity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ckw.zfsoft.useeasyrecyclerview.DataProvider
import com.ckw.zfsoft.useeasyrecyclerview.adapter.PersonAdapter
import com.ckw.zfsoft.useeasyrecyclerview.R
import com.ckw.zfsoft.useeasyrecyclerview.entites.Ad
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.jude.rollviewpager.adapter.StaticPagerAdapter
import com.jude.rollviewpager.hintview.ColorPointHintView
import kotlinx.android.synthetic.main.activity_collapsing.*

/**
 * 使用协调者布局，头部是一个viewpager，下面是普通的数据,中间有个floatActionBar
 */
class CollapsingActivity : AppCompatActivity() {

    private var mHandler: Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing)
        setSupportActionBar(toolbar)
        initEasyRecyclerView()
    }

    private fun initEasyRecyclerView() {

        val adapter: PersonAdapter = PersonAdapter(this)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        //设置适配器
        recyclerView.adapter = adapter
        //加载更多
        adapter.setMore(R.layout.view_more,RecyclerArrayAdapter.OnLoadMoreListener {
            mHandler.postDelayed(Runnable {
                adapter.addAll(DataProvider.getPersonList(0))
            },2000)
        })
        //加载初始化数据
        adapter.addAll(DataProvider.getPersonList(0))

        //设置viewpager的小圆点颜色，参数一：当前选中的小圆点颜色，参数二：没选中的小圆点
        rollPagerView.setHintView(ColorPointHintView(this, Color.YELLOW,Color.GRAY))
        rollPagerView.setAdapter(BannerAdapter())
    }

    private inner class BannerAdapter : StaticPagerAdapter() {
        private val list: List<Ad>

        init {
            list = DataProvider.getAdList()
        }

        override fun getView(container: ViewGroup, position: Int): View {
            val imageView = ImageView(this@CollapsingActivity)
            imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            //加载图片
            Glide.with(this@CollapsingActivity)
                    .load(list[position].getImage())
                    .placeholder(R.drawable.default_image)
                    .into(imageView)
            //点击事件
            imageView.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(list[position].getUrl()))) }
            return imageView
        }

        override fun getCount(): Int {
            return list.size
        }
    }
}
