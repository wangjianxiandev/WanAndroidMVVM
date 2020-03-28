package com.wjx.android.wanandroidmvvm.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.SparseArray
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.afollestad.materialdialogs.color.setArgbColor
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseActivity
import com.wjx.android.wanandroidmvvm.base.state.UserInfo
import com.wjx.android.wanandroidmvvm.base.state.callback.LoginSuccessListener
import com.wjx.android.wanandroidmvvm.base.state.callback.LoginSuccessState
import com.wjx.android.wanandroidmvvm.base.utils.*
import com.wjx.android.wanandroidmvvm.ui.home.view.HomeFragment
import com.wjx.android.wanandroidmvvm.ui.navigation.view.NavigationFragment
import com.wjx.android.wanandroidmvvm.ui.project.view.ProjectFragment
import com.wjx.android.wanandroidmvvm.ui.question.view.QuestionArticleListActivity
import com.wjx.android.wanandroidmvvm.ui.search.SearchActivity
import com.wjx.android.wanandroidmvvm.ui.setting.SettingActivity
import com.wjx.android.wanandroidmvvm.ui.square.view.SquareActivity
import com.wjx.android.wanandroidmvvm.ui.system.view.SystemFragment
import com.wjx.android.wanandroidmvvm.ui.wechat.view.WeChatFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import kotlinx.android.synthetic.main.layout_drawer_header.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), LoginSuccessListener {
    // 委托属性   将实现委托给了 -> Preference
    private var mUsername: String by SPreference(Constant.USERNAME_KEY, "未登录")
    private var mUserId: String by SPreference(Constant.USERID_KEY, "--")
    private lateinit var headView: View
    private var mLastIndex: Int = -1
    private val mFragmentSparseArray = SparseArray<Fragment>()

    // 当前显示的 fragment
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        initToolBar()
        initDrawerLayout()
        initFabButton()
        initColor()
        initBottomNavigation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        // 判断当前是recreate还是新启动
        if (savedInstanceState == null) {
            switchFragment(Constant.HOME)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // recreate时保存当前页面位置
        outState.putInt("index", mLastIndex)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // 恢复recreate前的页面
        mLastIndex = savedInstanceState.getInt("index")
        switchFragment(mLastIndex)
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

    private fun initColor() {
        toolbar.setBackgroundColor(Util.getColor(this))
        headView.setBackgroundColor(Util.getColor(this))
        bottom_navigation.setItemIconTintList(Util.getColorStateList(this))
        bottom_navigation.setItemTextColor(Util.getColorStateList(this))
        bottom_navigation.setBackgroundColor(ContextCompat.getColor(this, R.color.white_bg))
        fab_add.setBackgroundTintList(Util.getOneColorStateList(this))
    }

    private fun initDrawerLayout() {

        // 设置 登录成功 监听
        LoginSuccessState.addListener(this)

        // 直接获取报错   error -> mNavMain.mTvName
        headView = navigation_draw.getHeaderView(0)
        headView.me_name.text = mUsername
        headView.me_image.setCircleName(mUsername)
        headView.me_info.text = "账户id: " + mUserId

        // 点击 登录
        headView.me_image.setOnClickListener { UserInfo.instance.login(this) }

        navigation_draw.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_menu_square -> {
                    startActivity<SquareActivity>()
                }
                R.id.nav_menu_collect -> {
                    UserInfo.instance.startCollectActivity(this)
                }
                R.id.nav_menu_share -> {
                    UserInfo.instance.startShareActivity(this)
                }
                R.id.nav_menu_question -> {
                    startActivity<QuestionArticleListActivity>()
                }
                R.id.nav_menu_todo -> {
                    UserInfo.instance.startTodoActivity(this)
                }
                R.id.nav_menu_theme -> {
                    MaterialDialog(this).show {
                        title(R.string.theme_color)
                        cornerRadius(16.0f)
                        colorChooser(
                            ColorUtil.ACCENT_COLORS,
                            initialSelection = Util.getColor(this@MainActivity),
                            subColors = ColorUtil.PRIMARY_COLORS_SUB
                        ) { dialog, color ->
                            Util.setColor(this@MainActivity, color)
                            ChangeThemeEvent().post()
                        }
                        positiveButton(R.string.done)
                        negativeButton(R.string.cancel)
                    }
                    false
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
            mCurrentFragment!!.mRvArticle!!.smoothScrollToPosition(0)
            val objectAnimatorX =
                ObjectAnimator.ofFloat(fab_add, "scaleX", 1.0f, 1.2f, 0.0f)
            objectAnimatorX.interpolator = AccelerateDecelerateInterpolator()
            val objectAnimatorY =
                ObjectAnimator.ofFloat(fab_add, "scaleY", 1.0f, 1.2f, 0.0f)
            objectAnimatorY.interpolator = AccelerateDecelerateInterpolator()
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(objectAnimatorX, objectAnimatorY)
            animatorSet.duration = 1000
            animatorSet.start()
        }
    }

    private fun initBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    fab_add.visibility = View.VISIBLE
                    setToolBarTitle(toolbar, getString(R.string.navigation_home))
                    switchFragment(Constant.HOME)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_wechat -> {
                    fab_add.visibility = View.GONE
                    setToolBarTitle(toolbar, getString(R.string.navigation_wechat))
                    switchFragment(Constant.WECHAT)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_system -> {
                    fab_add.visibility = View.GONE
                    setToolBarTitle(toolbar, getString(R.string.navigation_system))
                    switchFragment(Constant.SYSTEM)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_navigation -> {
                    fab_add.visibility = View.GONE
                    setToolBarTitle(toolbar, getString(R.string.navigation_navigation))
                    switchFragment(Constant.NAVIGATION)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_project -> {
                    fab_add.visibility = View.GONE
                    setToolBarTitle(toolbar, getString(R.string.navigation_project))
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

    private fun switchFragment(index: Int) {
        val fragmentManager = supportFragmentManager
        val transaction =
            fragmentManager.beginTransaction()
        // 将当前显示的fragment和上一个需要隐藏的fragment分别加上tag, 并获取出来
        // 给fragment添加tag,这样可以通过findFragmentByTag找到存在的fragment，不会出现重复添加
        mCurrentFragment = fragmentManager.findFragmentByTag(index.toString())
        mLastFragment = fragmentManager.findFragmentByTag(mLastIndex.toString())
        // 如果位置不同
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment!!)
            }
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            } else {
                transaction.show(mCurrentFragment!!)
            }
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            }
        }
        transaction.commit()
        mLastIndex = index
    }

    private fun getFragment(index: Int): Fragment {
        var fragment: Fragment? = mFragmentSparseArray.get(index)
        if (fragment == null) {
            when (index) {
                Constant.HOME -> fragment = HomeFragment.getInstance()
                Constant.SYSTEM -> fragment = SystemFragment.getInstance()
                Constant.NAVIGATION -> fragment = NavigationFragment.getInstance()
                Constant.WECHAT -> fragment = WeChatFragment.getInstance()
                Constant.PROJECT -> fragment = ProjectFragment.getInstance()
            }
            mFragmentSparseArray.put(index, fragment)
        }
        return fragment!!
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
    override fun loginSuccess(username: String, userId: String, collectIds: List<Int>?) {
        // 进行 SharedPreference 存储
        mUsername = username
        headView.me_name.text = username
        headView.me_image.setCircleName(mUsername)
        mUserId = userId
        headView.me_info.text = "账户id: " + mUserId
    }

    override fun onDestroy() {
        super.onDestroy()
        LoginSuccessState.removeListener(this)
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }

    @Subscribe
    fun recreateEvent(event: RecreateEvent) {
        recreate()
    }
}
