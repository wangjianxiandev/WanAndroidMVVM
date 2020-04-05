package com.wjx.android.wanandroidmvvm.ui.todo.view

import androidx.lifecycle.Observer
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.base.utils.*
import com.wjx.android.wanandroidmvvm.ui.todo.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_edit_todo.*
import kotlinx.android.synthetic.main.custom_bar.view.*
import org.greenrobot.eventbus.Subscribe
import java.util.*

class EditTodoActivity : BaseLifeCycleActivity<TodoViewModel>(), View.OnClickListener {

    private val mActivityType: Int? by lazy { intent.getStringExtra(Constant.KEY_TODO_HANDLE_TYPE)?.toInt() }

    private val mTitle: String? by lazy { intent?.getStringExtra(Constant.KEY_TODO_TITLE) }

    private val mContent: String? by lazy { intent?.getStringExtra(Constant.KEY_TODO_CONTENT) }

    private val mDateStr: String? by lazy { intent?.getStringExtra(Constant.KEY_TODO_DATE) }

    private val mTodoId: Int? by lazy { intent?.getStringExtra(Constant.KEY_TODO_ID)?.toInt() }

    private val mTodoType: Int? by lazy { intent?.getStringExtra(Constant.KEY_TODO_TYPE)?.toInt() }

    private val mPriority: Int? by lazy { intent?.getStringExtra(Constant.KEY_TODO_PRIORITY)?.toInt()}

    private var todoType : Int = 0

    private var todoPriority : Int = 0

    override fun initDataObserver() {
        mViewModel.mTodoAddData.observe(this, Observer {
            finish()
        })
        mViewModel.mTodoUpdateData.observe(this, Observer {
            finish()
        })
    }

    override fun getLayoutId(): Int = R.layout.activity_edit_todo

    override fun initView() {
        super.initView()
        initHeadView()
        initContentView()
        edit_todo_date.setOnClickListener(this)
        edit_todo_priority.setOnClickListener(this)
        edit_todo_type.setOnClickListener(this)
        edit_todo_submit.setOnClickListener(this)
        showSuccess()
    }

    override fun showCreateReveal(): Boolean = false

    override fun showDestroyReveal(): Boolean = false

    private fun initHeadView() {
        todo_bar.detail_title.text =
            if (mActivityType == Constant.EDIT_TODO.toInt()) "编辑待办" else "添加待办"
        todo_bar.detail_back.visibility = View.VISIBLE
        todo_bar.detail_search.visibility = View.GONE
        todo_bar.detail_back.setOnClickListener(this)
        initColor()
    }

    private fun initColor() {
        todo_bar.setBackgroundColor(ColorUtil.getColor(this))
        edit_todo_submit.setBackgroundColor(ColorUtil.getColor(this))
    }

    private fun initContentView() {
        if (mActivityType == Constant.EDIT_TODO.toInt()) {
            edit_todo_title.setText(mTitle)
            edit_todo_content.setText(mContent)
            edit_todo_date.setText(mDateStr)
            edit_todo_type.text = if (mTodoType == 1 || mTodoType == 0) getString(R.string.todo_work) else getString(R.string.todo_study)
            edit_todo_priority.text = if (mPriority == 1) getString(R.string.todo_piority_important) else getString(R.string.todo_piority_normal)
            todoType = mTodoType?.toInt()!!
            todoPriority = mPriority?.toInt()!!
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.edit_todo_date -> {
                // 日期选择器，月份加一
                val datePickerDialog = DatePickerDialog(
                    this,
                    OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                        edit_todo_date.setText(
                            year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
                        )
                    },
                    Calendar.getInstance()[Calendar.YEAR],
                    Calendar.getInstance()[Calendar.MONTH],
                    Calendar.getInstance()[Calendar.DAY_OF_MONTH]
                )
                datePickerDialog.datePicker
                datePickerDialog.show()
            }

            R.id.edit_todo_priority -> {
                // 底部弹出对话框
                val bottomDialog =
                    Dialog(this, R.style.BottomDialog)
                val contentView: View =
                    LayoutInflater.from(this).inflate(R.layout.dialog_content_circle, null)
                bottomDialog.setContentView(contentView)
                val params = contentView.layoutParams as MarginLayoutParams
                params.width =
                    resources.displayMetrics.widthPixels - DisplayUtil.dp2Px(this, 16)
                params.bottomMargin = DisplayUtil.dp2Px(this, 8)
                contentView.layoutParams = params
                bottomDialog.window!!.setGravity(Gravity.BOTTOM)
                bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
                bottomDialog.show()
                contentView.findViewById<View>(R.id.important)
                    .setOnClickListener { v: View? ->
                        todoPriority = 1
                        edit_todo_priority.setText(R.string.todo_piority_important)
                        bottomDialog.dismiss()
                    }

                contentView.findViewById<View>(R.id.normal)
                    .setOnClickListener { v: View? ->
                        todoPriority = 2
                        edit_todo_priority.setText(R.string.todo_piority_normal)
                        bottomDialog.dismiss()
                    }
            }

            R.id.edit_todo_type -> {
                // 底部弹出对话框
                val bottomDialog =
                    Dialog(this, R.style.BottomDialog)
                val contentView =
                    LayoutInflater.from(this).inflate(R.layout.dialog_content_circle, null)
                bottomDialog.setContentView(contentView)
                val params = contentView.layoutParams as MarginLayoutParams
                params.width =
                    resources.displayMetrics.widthPixels - DisplayUtil.dp2Px(this, 16)
                params.bottomMargin = DisplayUtil.dp2Px(this, 8)
                contentView.layoutParams = params
                bottomDialog.window!!.setGravity(Gravity.BOTTOM)
                bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
                bottomDialog.show()
                val work = contentView.findViewById<TextView>(R.id.important)
                work.setText(R.string.todo_work)
                val study = contentView.findViewById<TextView>(R.id.normal)
                study.setText(R.string.todo_study)
                contentView.findViewById<View>(R.id.normal)
                    .setOnClickListener { v: View? ->
                        todoType = Constant.TODO_STUDY
                        edit_todo_type.setText(R.string.todo_study)
                        bottomDialog.dismiss()
                    }

                contentView.findViewById<View>(R.id.important)
                    .setOnClickListener { v: View? ->
                        todoType = Constant.TODO_WORK
                        edit_todo_type.setText(R.string.todo_work)
                        bottomDialog.dismiss()
                    }
            }
            R.id.edit_todo_submit -> {
                if (mActivityType == Constant.EDIT_TODO.toInt()) {
                    if (edit_todo_title.text.toString().isNullOrEmpty() || edit_todo_content.text.toString().isNullOrEmpty()
                        || edit_todo_date.text.toString().isNullOrEmpty()) {
                        Toast.makeText(this, R.string.todo_empty, Toast.LENGTH_SHORT).show()
                    } else {
                        mViewModel.updateTodo(
                            mTodoId,
                            edit_todo_title.text.toString(),
                            edit_todo_content.text.toString(),
                            edit_todo_date.text.toString(),
                            todoType,
                            todoPriority
                        )
                    }
                } else if (mActivityType == Constant.ADD_TODO.toInt()) {
                    if (edit_todo_title.text.toString().isNullOrEmpty() || edit_todo_content.text.toString().isNullOrEmpty()
                        || edit_todo_date.text.toString().isNullOrEmpty()) {
                        Toast.makeText(this, R.string.todo_empty, Toast.LENGTH_SHORT).show()
                    } else {
                        mViewModel.addTodo(
                            edit_todo_title.text.toString(),
                            edit_todo_content.text.toString(),
                            edit_todo_date.text.toString(),
                            todoType,
                            todoPriority
                        )
                    }
                }
            }

            R.id.detail_back -> {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }
}
