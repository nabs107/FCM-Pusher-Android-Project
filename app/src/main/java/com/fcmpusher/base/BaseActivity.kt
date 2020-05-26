package com.fcmpusher.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), LifecycleOwner {

    lateinit var mBinding: B

    abstract
    @LayoutRes
    fun getLayoutId(): Int

    override fun getLifecycle(): Lifecycle {
        return super.getLifecycle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.setLifecycleOwner { this.lifecycle }

        setup()
    }

    private fun setup() {
        setupViews()
        setupEventListeners()
        setupObservers()
    }

    abstract fun setupEventListeners()

    abstract fun setupViews()

    abstract fun setupObservers()

    override fun onBackPressed() {
        if (isKeyboardOpened() == true) {
            hideKeyboard()
        } else {
            super.onBackPressed()
        }
    }

    fun isKeyboardOpened(): Boolean? {
        val view = currentFocus

        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
            if (inputMethodManager != null) {
                return (inputMethodManager as? InputMethodManager)?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
        return false
    }

    fun hideKeyboard() {
        val view = currentFocus

        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
            if (inputMethodManager != null) {
                (inputMethodManager as? InputMethodManager)?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}