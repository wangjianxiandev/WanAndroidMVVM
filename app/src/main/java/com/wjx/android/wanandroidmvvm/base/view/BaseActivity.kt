package com.wjx.android.wanandroidmvvm.base.view

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.ColorUtils
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.wjx.android.wanandroidmvvm.Custom.graylayout.GrayFrameLayout
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.utils.AppManager
import com.wjx.android.wanandroidmvvm.base.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.base.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.base.utils.RevealUtil.circularFinishReveal
import com.wjx.android.wanandroidmvvm.base.utils.RevealUtil.setReveal
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast
import java.util.*


/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 19:56
 */
abstract class BaseActivity : AppCompatActivity() {

    private var mExitTime: Long = 0

    protected var mDisposable: Disposable? = null

    lateinit var mRootView: View

    val loadService: LoadService<*> by lazy {
        LoadSir.getDefault().register(this) {
            reLoad()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mRootView = (findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
        AppManager.instance.addActivity(this)
        initView()
        initData()
        if (showCreateReveal()) {
            setUpReveal(savedInstanceState)
        }
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        if (Date().month + 1 == 4 && Date().date == 4) {
            initStatusColor(getColor(R.color.colorGray666))
            if ("FrameLayout" == name) {
                val count: Int = attrs.getAttributeCount()
                for (i in 0 until count) {
                    val attributeName: String = attrs.getAttributeName(i)
                    val attributeValue: String = attrs.getAttributeValue(i)
                    if (attributeName == "id") {
                        val id = attributeValue.substring(1).toInt()
                        val idVal = resources.getResourceName(id)
                        if ("android:id/content" == idVal) {
                            return GrayFrameLayout(context, attrs)
                        }
                    }
                }
            }
        } else {
            initStatusColor(0)
        }
        return super.onCreateView(name, context, attrs)
    }


    open fun showCreateReveal(): Boolean = true

    open fun showDestroyReveal(): Boolean = false

    open fun initView() {}
    open fun initData() {}

    abstract fun getLayoutId(): Int

    open fun reLoad() {}

    override fun onBackPressed() {
        val time = System.currentTimeMillis()

        if (time - mExitTime > 2000) {
            toast(getString(R.string.exit_app))
            mExitTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
    }


    fun setUpReveal(savedInstanceState: Bundle?) {
        setReveal(savedInstanceState)
    }

    /**
     *  设置标题
     */
    fun setToolBarTitle(toolbar: Toolbar, title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    /**
     *  设置返回
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable?.dispose()
        EventBus.getDefault().unregister(this)
        AppManager.instance.removeActivity(this)
    }

    override fun onPause() {
        super.onPause()
        if (showDestroyReveal()) {
            circularFinishReveal(mRootView)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
    }

    private fun initStatusColor(color : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.statusBarColor = if (color == 0) ColorUtil.getColor(this) else color
        }
        if (ColorUtils.calculateLuminance(Color.TRANSPARENT) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            // 跟随系统
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    @Subscribe
    fun changeThemeEvent(event: ChangeThemeEvent) {
        initStatusColor(0)
    }
}