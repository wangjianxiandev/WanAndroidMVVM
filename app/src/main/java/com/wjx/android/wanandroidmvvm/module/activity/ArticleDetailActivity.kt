package com.wjx.android.wanandroidmvvm.module.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.just.agentweb.AgentWeb
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.view.BaseActivity
import com.wjx.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.common.utils.DisplayUtil
import com.yzq.zxinglibrary.encode.CodeCreator
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.custom_bar.*
import kotlinx.android.synthetic.main.dialog_qrcode.view.*
import org.greenrobot.eventbus.Subscribe


class ArticleDetailActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mAgentWeb: AgentWeb

    private val mUrl: String? by lazy { intent?.getStringExtra("url") }

    private val mTitle: String? by lazy { intent?.getStringExtra("title") }

    private lateinit var mPopupWindow: PopupWindow

    override fun getLayoutId(): Int = R.layout.activity_article_detail

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initView() {
        initHeaderView()
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(article_detail, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(mUrl)
    }

    private fun initHeaderView() {
        custom_bar.setBackgroundColor(ColorUtil.getColor(this))
        detail_back.apply {
            visibility = View.VISIBLE
            setOnClickListener(this@ArticleDetailActivity)
        }
        detail_search.apply {
            setImageResource(R.drawable.ic_more)
            setOnClickListener(this@ArticleDetailActivity)
        }
        detail_title.text =
            Html.fromHtml(intent.getStringExtra("title"), Html.FROM_HTML_MODE_COMPACT)
    }

    override fun showDestroyReveal(): Boolean = false

    override fun onBackPressed() = finish()

    override fun onClick(v: View?) {
        v?.let { v ->
            when (v.id) {
                R.id.detail_back -> {
                    onBackPressed()
                }
                R.id.detail_search -> {
                    showPopupWindow()
                }
                R.id.qrcode_linear -> {
                    showQrCode()
                    mPopupWindow.dismiss()
                }
                R.id.share_linear -> {
                    showShare()
                    mPopupWindow.dismiss()
                }
                R.id.browser_linear -> {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mUrl)))
                    mPopupWindow.dismiss()
                }
            }
        }
    }

    /**
     * 利用反射机制调用MenuBuilder的setOptionalIconsVisible方法设置mOptionalIconsVisible为true，
     * 给菜单设置图标时才可见 让菜单同时显示图标和文字
     */
    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
        if (menu != null) {
            if (menu.javaClass.simpleName.equals("MenuBuilder", ignoreCase = true)) {
                try {
                    val method = menu.javaClass.getDeclaredMethod(
                        "setOptionalIconsVisible",
                        java.lang.Boolean.TYPE
                    )
                    method.isAccessible = true
                    method.invoke(menu, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return super.onMenuOpened(featureId, menu)
    }

    private fun showPopupWindow() {
        val popRootView = LayoutInflater.from(this).inflate(R.layout.layout_popup, null)
        mPopupWindow = PopupWindow(popRootView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        val qrcodeLinear = popRootView.findViewById<ViewGroup>(R.id.qrcode_linear)
        val shareLinear = popRootView.findViewById<ViewGroup>(R.id.share_linear)
        val browserLinear = popRootView.findViewById<ViewGroup>(R.id.browser_linear)
        qrcodeLinear.setOnClickListener(this)
        shareLinear.setOnClickListener(this)
        browserLinear.setOnClickListener(this)
        mPopupWindow.showAsDropDown(detail_search)
    }

    private fun showShare() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(
            Intent.EXTRA_TEXT, getString(
                R.string.app_title_url,
                getString(R.string.app_name), mTitle, mUrl
            )
        )
        intent.type = "text/plain"
        startActivity(intent)
    }

    private fun showQrCode() {
        val bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_qrcode, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels - DisplayUtil.dp2Px(this, 16)
        params.bottomMargin = DisplayUtil.dp2Px(this, 8)
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        val bitmap = CodeCreator.createQRCode(
            mUrl,
            600,
            600,
            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        )
        contentView.qrcode_image.setImageBitmap(bitmap)
        contentView.findViewById<View>(R.id.btn_cancel).apply {
            setBackgroundColor(ContextCompat.getColor(this@ArticleDetailActivity, R.color.white_bg))
            setOnClickListener { v: View? ->
                bottomDialog.dismiss()
            }
        }

        contentView.findViewById<View>(R.id.btn_confirm).apply {
            setBackgroundColor(ContextCompat.getColor(this@ArticleDetailActivity, R.color.white_bg))
            setOnClickListener { v: View? ->
                showShare()
                bottomDialog.dismiss()
            }
        }
        bottomDialog.show()
    }


    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        custom_bar.setBackgroundColor(ColorUtil.getColor(this))
    }
}