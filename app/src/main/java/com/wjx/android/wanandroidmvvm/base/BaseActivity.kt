package com.wjx.android.wanandroidmvvm.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.wjx.android.wanandroidmvvm.R
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 19:56
 */
abstract class BaseActivity : AppCompatActivity() {

    private var exitTime : Long = 0

    protected var disposable : Disposable? = null

    val loadService : LoadService<*> by lazy {
        LoadSir.getDefault().register(this) {
            reLoad()
        }
    }

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        AppManager.instance.addActivity(this)
        initView()
        initData()
    }

    open fun initView() {}
    open fun initData() {}

    abstract fun getLayoutId(): Int

    open fun reLoad() {}

    override fun onBackPressed() {
        val time = System.currentTimeMillis()

        if (time - exitTime > 2000) {
            toast(getString(R.string.exit_app))
            exitTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
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
        disposable?.dispose()
        AppManager.instance.removeActivity(this)
    }
}