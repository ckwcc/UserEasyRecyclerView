package com.ckw.zfsoft.useeasyrecyclerview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ckw.zfsoft.useeasyrecyclerview.targetactivity.CollapsingActivity
import com.ckw.zfsoft.useeasyrecyclerview.targetactivity.MultiTypeActivity
import com.ckw.zfsoft.useeasyrecyclerview.targetactivity.RefreshMoreActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()
    }

    private fun initListener() {
        btn_refresh_more.setOnClickListener {
            val intent = Intent(this@MainActivity, RefreshMoreActivity::class.java)
            startActivity(intent)
        }

        btn_collapsing.setOnClickListener {
            val intent = Intent(this, CollapsingActivity::class.java)
            startActivity(intent)
        }

        btn_multiType.setOnClickListener {
            val intent = Intent(this, MultiTypeActivity::class.java)
            startActivity(intent)
        }


    }


}
