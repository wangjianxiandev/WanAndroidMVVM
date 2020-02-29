package com.wjx.android.wanandroidmvvm.Custom.behavior

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.wjx.android.wanandroidmvvm.R

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/28
 * Time: 16:27
 */

abstract class SimplePopWindow  @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    PopupWindow(context, attrs, defStyleAttr) {

    lateinit var activity : Context

    private var onCreate :(()->Int)? = null

    private var onView :((view: View)->Unit)? = null

    abstract fun build() :Int

    private var owner : LifecycleOwner? = null

    private  var popupWidth = 0
    private  var popupHeight = 0


    init {
        init(context)
    }

    fun init(context: Context?){
        val mWidth = build()
        val viewId = onCreate?.invoke()
        viewId?.apply {
            val view = View.inflate(context, viewId, null)
            width = mWidth
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            contentView = view
            isFocusable = true
            isOutsideTouchable = true
            animationStyle = R.style.AnimAlpha
            setBackgroundDrawable(ColorDrawable(0x00000000))

            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            popupWidth = view.measuredWidth
            popupHeight = view.measuredHeight


            view.apply {
                onView?.invoke(this)
            }
        }
    }



    fun showAtEnd(v : View){
        val location = IntArray(2)
        v.getLocationOnScreen(location)
        showAtLocation(v, Gravity.NO_GRAVITY, location[0] + v.width, location[1])
    }


    fun showAtStart(v : View){
        val location = IntArray(2)
        v.getLocationOnScreen(location)
        showAtLocation(v, Gravity.NO_GRAVITY, location[0] - popupWidth, location[1])
    }


    fun showAtTop(v : View){
        val location = IntArray(2)
        v.getLocationOnScreen(location)
        showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.width / 2) - popupWidth / 2, location[1] - popupHeight)
    }


    fun showAtBottom(view: View) {
        showAsDropDown(view)
    }



    fun buildDialog(onCreate :(()->Int)) : SimplePopWindow{
        this.onCreate = onCreate
        return this
    }



    fun <T : ViewDataBinding> View.onBindingView(onBindingView :((binding : T?)->Unit)){
        onBindingView.invoke(DataBindingUtil.bind<T>(this))
    }


    fun onView(onView :((view: View)->Unit)) : SimplePopWindow{
        this.onView = onView
        return this
    }
}