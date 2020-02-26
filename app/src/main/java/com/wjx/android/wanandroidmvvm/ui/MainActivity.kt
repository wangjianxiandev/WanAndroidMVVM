package com.wjx.android.wanandroidmvvm.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseActivity
import com.wjx.android.wanandroidmvvm.base.state.callback.LoginSuccessListener
import com.wjx.android.wanandroidmvvm.base.state.callback.LoginSuccessState
import com.wjx.android.wanandroidmvvm.base.utils.Constant
import com.wjx.android.wanandroidmvvm.base.utils.Preference
import com.wjx.android.wanandroidmvvm.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : BaseActivity(), LoginSuccessListener {
    private var clickTime: Long = 0

    private val mNotLogin: String = "未登录"
    // 委托属性   将实现委托给了 -> Preference
    private var mUsername: String by Preference(Constant.USERNAME_KEY, mNotLogin)

    private val mHomeFragment by lazy { HomeFragment() }
//    private val mWeChatFragment by lazy { WeChatFragment() }
//    private val mSystemFragment by lazy { SystemFragment() }
//    private val mNavigationFragment by lazy { NavigationFragment() }
//    private val mProjectFragment by lazy { ProjectFragment() }

    // 当前显示的 fragment
    private lateinit var mCurrentFragment: Fragment

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        initToolBar()
        initDrawerLayout()
        initNavigationBar()
        initFloatButton()
        setDefaultFragment()
    }

    private fun initToolBar() {
        // 设置标题

        //设置导航图标、按钮有旋转特效
        val toggle = ActionBarDrawerToggle(
            this, mDrawerMain, toolbar, R.string.app_name, R.string.app_name)
        mDrawerMain.addDrawerListener(toggle)
        toggle.syncState()

        toolbar.setOnClickListener {
            val nowTime = System.currentTimeMillis()
            if (nowTime - clickTime > 1000) {
                clickTime = nowTime
            } else {

            }
        }
    }

    private fun initDrawerLayout() {

        // 设置 登录成功 监听
        LoginSuccessState.addListener(this)

//        // 直接获取报错   error -> mNavMain.mTvName
//        headView = mNavMain.getHeaderView(0)
//        headView.mTvName.text = mUsername
//
//        // 点击 登录
//        headView.mCivIcon.setOnClickListener { UserContext.instance.login(this) }
//
//        mNavMain.setNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.nav_menu_collect -> {
//                    UserContext.instance.goCollectActivity(this)
//                }
//                R.id.nav_menu_todo -> {
//                    UserContext.instance.goTodoActivity(this)
//                }
//                R.id.nav_menu_about -> {
//                    startActivity<AboutActivity>()
//                }
//                R.id.nav_menu_setting -> {
//                    toast(getString(R.string.setting))
//                }
//                R.id.nav_menu_logout -> {
//                    UserContext.instance.logoutSuccess()
//                }
//            }
//
//            // 关闭侧边栏
//            mDrawerMain.closeDrawers()
//            true
//        }
    }

    private fun initNavigationBar() {
        with(mBottomNavigationBar) {
            setMode(BottomNavigationBar.MODE_FIXED)

            addItem(BottomNavigationItem(R.mipmap.navigation_home, "首页"))
            addItem(BottomNavigationItem(R.mipmap.navigation_wechat, "微信"))
            addItem(BottomNavigationItem(R.mipmap.navigation_system, "体系"))
            addItem(BottomNavigationItem(R.mipmap.navigation_navigation, "导航"))
            addItem(BottomNavigationItem(R.mipmap.nagivation_project, "项目"))

            // 设置底部 BottomBar 默认选中 home
            setFirstSelectedPosition(Constant.HOME)
            // 初始化
            initialise()

            setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
                override fun onTabReselected(position: Int) {}
                override fun onTabUnselected(position: Int) {}

                override fun onTabSelected(position: Int) = switchFragment(position)
            })
        }
    }

    private fun initFloatButton() {
        mFabAdd.setOnClickListener {
            Log.e("WJX", "fab")}
    }

    /**
     *  创建 search 搜索 icon
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 设置 toolbar   search 图标
        // search 图标大小 -> 通过 drawable-hdpi 文件夹  还有 原图片大小来设置这里 32dp
        // 如果直接放入 drawable 会偏大
//        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    // 设置默认选中 fragment
    private fun setDefaultFragment() {
        mCurrentFragment = mHomeFragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.content, mHomeFragment).commit()
    }

    private fun switchFragment(position: Int) {
        when (position) {
            Constant.HOME -> {
                goTo(mHomeFragment)
            }
//            Constant.WECHAT -> {
//                goTo(mWeChatFragment)
//            }
//
//            Constant.SYSTEM -> {
//
//                goTo(mSystemFragment)
//            }
//            Constant.NAVIGATION -> {
//
//                goTo(mNavigationFragment)
//            }
//            Constant.PROJECT -> {
//                goTo(mProjectFragment)
//            }
        }
    }

    // 复用 fragment
    private fun goTo(to: Fragment) {
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
                mDrawerMain.openDrawer(Gravity.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 登录成功 回调
    override fun loginSuccess(username: String, collectIds: List<Int>?) {
        // 进行 SharedPreference 存储
        mUsername = username
//        headView.mTvName.text = username
    }

    override fun onDestroy() {
        super.onDestroy()
        LoginSuccessState.removeListsner(this)
    }
}
