package com.wjx.android.wanandroidmvvm.ui.activity

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseActivity
import com.wjx.android.wanandroidmvvm.base.state.UserInfo
import com.wjx.android.wanandroidmvvm.base.state.callback.LoginSuccessListener
import com.wjx.android.wanandroidmvvm.base.state.callback.LoginSuccessState
import com.wjx.android.wanandroidmvvm.base.utils.Constant
import com.wjx.android.wanandroidmvvm.base.utils.Preference
import org.jetbrains.anko.startActivity
import com.wjx.android.wanandroidmvvm.ui.home.view.HomeFragment
import com.wjx.android.wanandroidmvvm.ui.navigation.view.NavigationFragment
import com.wjx.android.wanandroidmvvm.ui.project.view.ProjectFragment
import com.wjx.android.wanandroidmvvm.ui.search.SearchActivity
import com.wjx.android.wanandroidmvvm.ui.setting.SettingActivity
import com.wjx.android.wanandroidmvvm.ui.system.view.SystemFragment
import com.wjx.android.wanandroidmvvm.ui.wechat.view.WeChatFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_drawer_header.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : BaseActivity(), LoginSuccessListener {
    // 委托属性   将实现委托给了 -> Preference
    private var mUsername: String by Preference(Constant.USERNAME_KEY, "未登录")

    private lateinit var headView: View

    private val mHomeFragment by lazy { HomeFragment() }
    private val mWeChatFragment by lazy { WeChatFragment() }
    private val mSystemFragment by lazy { SystemFragment() }
    private val mNavigationFragment by lazy { NavigationFragment() }
    private val mProjectFragment by lazy { ProjectFragment() }

    // 当前显示的 fragment
    private lateinit var mCurrentFragment: Fragment

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        initToolBar()
        initDrawerLayout()
        initFabButton()
        initBottomNavigation()
        setDefaultFragment()
    }

    private fun initToolBar() {
        // 设置标题
        setToolBarTitle(toolbar, getString(R.string.navigation_home))
        //设置导航图标、按钮有旋转特效
        val toggle = ActionBarDrawerToggle(
            this, drawer_main, toolbar, R.string.app_name, R.string.app_name
        )
        drawer_main.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initDrawerLayout() {

        // 设置 登录成功 监听
        LoginSuccessState.addListener(this)

        // 直接获取报错   error -> mNavMain.mTvName
        headView = navigation_draw.getHeaderView(0)
        headView.circle_image_name.text = mUsername
        headView.circle_image.setColor(ContextCompat.getColor(this, R.color.colorPrimary))
        headView.circle_image.setIsShowBlurMask(false)
        headView.circle_image.setShowNameCount(1)
        headView.circle_image.setCircleName(mUsername)

        // 点击 登录
        headView.circle_image.setOnClickListener { UserInfo.instance.login(this) }

        navigation_draw.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_menu_collect -> {
                    UserInfo.instance.startCollectActivity(this)
                }
                R.id.nav_menu_todo -> {
                    UserInfo.instance.startTodoActivity(this)
                }
                R.id.nav_menu_setting -> {
                    startActivity<SettingActivity>()
                }
                R.id.nav_menu_logout -> {
                    UserInfo.instance.logoutSuccess()
                }
            }

            // 关闭侧边栏
            drawer_main.closeDrawers()
            true
        }
    }

    private fun initFabButton() {
        fab_add.setOnClickListener {
            UserInfo.instance.startEditTodoActivity(this)
        }
    }

    private fun initBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    switchFragment(Constant.HOME)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_wechat -> {
                    switchFragment(Constant.WECHAT)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_system -> {
                    switchFragment(Constant.SYSTEM)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_navigation -> {
                    switchFragment(Constant.NAVIGATION)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_project -> {
                    switchFragment(Constant.PROJECT)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setDefaultFragment() {
        mCurrentFragment = mHomeFragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.content, mHomeFragment).commit()
    }

    private fun switchFragment(position: Int) {
        when (position) {
            Constant.HOME -> {
                fab_add.visibility = View.VISIBLE
                setToolBarTitle(toolbar, getString(R.string.navigation_home))
                switchFragment(mHomeFragment)
            }
            Constant.WECHAT -> {
                fab_add.visibility = View.VISIBLE
                setToolBarTitle(toolbar, getString(R.string.navigation_wechat))
                switchFragment(mWeChatFragment)
            }

            Constant.SYSTEM -> {
                fab_add.visibility = View.VISIBLE
                setToolBarTitle(toolbar, getString(R.string.navigation_system))
                switchFragment(mSystemFragment)
            }
            Constant.NAVIGATION -> {
                fab_add.visibility = View.GONE
                setToolBarTitle(toolbar, getString(R.string.navigation_navigation))
                switchFragment(mNavigationFragment)
            }
            Constant.PROJECT -> {
                fab_add.visibility = View.GONE
                setToolBarTitle(toolbar, getString(R.string.navigation_project))
                switchFragment(mProjectFragment)
            }
        }
    }

    // 复用 fragment
    private fun switchFragment(to: Fragment) {
        if (mCurrentFragment != to) {
            val transaction = supportFragmentManager.beginTransaction()
            if (to.isAdded)
                transaction.hide(mCurrentFragment).show(to)
            else
                transaction.hide(mCurrentFragment).add(R.id.content, to)
            transaction.commit()
            mCurrentFragment = to
        }
    }

    @SuppressLint("WrongConstant")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            //将滑动菜单显示出来
            android.R.id.home -> {
                drawer_main.openDrawer(Gravity.START)
                return true
            }
            R.id.action_search -> {
                startActivity<SearchActivity>()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 登录成功 回调
    override fun loginSuccess(username: String, collectIds: List<Int>?) {
        // 进行 SharedPreference 存储
        mUsername = username
        headView.circle_image_name.text = username
        headView.circle_image.setCircleName(mUsername)
    }

    override fun onDestroy() {
        super.onDestroy()
        LoginSuccessState.removeListener(this)
    }
}
