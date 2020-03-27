package com.wjx.android.wanandroidmvvm.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.wjx.android.wanandroidmvvm.base.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.base.utils.Util
import org.greenrobot.eventbus.Subscribe

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 19:35
 */

abstract class BaseFragment : Fragment() {

    protected lateinit var loadService : LoadService<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(getLayoutId(), null)
        loadService = LoadSir.getDefault().register(rootView) {reLoad()}
        return loadService.loadLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initStatusColor()
        initView()
        initData()
    }

    abstract fun initView()

    open fun initData() {}

    // 重新加载
    open fun reLoad() {
        initData()
    }

    abstract fun getLayoutId(): Int

    private fun initStatusColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity!!.window.statusBarColor = Util.getColor(activity!!)
        }
        if (ColorUtils.calculateLuminance(Color.TRANSPARENT) >= 0.5) { // 设置状态栏中字体的颜色为黑色
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else { // 跟随系统
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    @Subscribe
    fun changeThemeEvent(event: ChangeThemeEvent) {
        initStatusColor()
    }
}