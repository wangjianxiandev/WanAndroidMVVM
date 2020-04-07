package com.wjx.android.wanandroidmvvm.ui.activity

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.just.agentweb.AgentWeb
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.view.BaseActivity
import com.wjx.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.custom_bar.*
import org.greenrobot.eventbus.Subscribe

class ArticleDetailActivity : BaseActivity() {

    private lateinit var mAgentWeb : AgentWeb
    override fun getLayoutId(): Int = R.layout.activity_article_detail

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initView() {
        custom_bar.setBackgroundColor(ColorUtil.getColor(this))
        detail_back.visibility = View.VISIBLE
        detail_back.setOnClickListener{finish()}
        detail_search.visibility = View.GONE
        val url = intent.getStringExtra("url")
        detail_title.text = Html.fromHtml(intent.getStringExtra("title"), Html.FROM_HTML_MODE_COMPACT)
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(article_detail, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)
    }

    override fun showDestroyReveal(): Boolean = true

    override fun onBackPressed() {
        finish()
    }

    @Subscribe
    fun changeEvent(event: ChangeThemeEvent) {
        custom_bar.setBackgroundColor(ColorUtil.getColor(this))
    }
}