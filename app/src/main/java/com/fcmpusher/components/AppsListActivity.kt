package com.fcmpusher.components

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fcmpusher.ApiService
import com.fcmpusher.R
import com.fcmpusher.adapter.AppListAdapter
import com.fcmpusher.base.BaseActivity
import com.fcmpusher.constants.Constants
import com.fcmpusher.databinding.ActivityAppsListBinding
import com.fcmpusher.model.App
import com.fcmpusher.viewmodel.AppListViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*

class AppsListActivity : BaseActivity<ActivityAppsListBinding>() {

    private val mAppListViewModel: AppListViewModel = AppListViewModel()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private lateinit var selectedApp: App

    override fun getLayoutId(): Int {
        return R.layout.activity_apps_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.vm = mAppListViewModel
        lifecycle.addObserver(mAppListViewModel)

        mAppListViewModel.getAppList()
    }

    override fun setupViews() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun setupEventListeners() {
        mBinding.fabAdd.setOnClickListener {

        }

        bottomSheetLayout.btnPush.setOnClickListener {
            if (validate()) {
                val params: MutableMap<String, Any> = HashMap()
                params[Constants.CONTENT_AVAILABLE] = true
                params[Constants.MUTABLE_CONTENT] = true
                params[Constants.PRIORITY] = "high"
                params[Constants.REGISTRATION_IDS] = listOf(selectedApp.fcmKey)

                val notification: MutableMap<String, Any> = HashMap()
                params[Constants.TITLE] = bottomSheetLayout.etTitle.text.toString()
                params[Constants.BODY] = bottomSheetLayout.etBody.text.toString()
                params[Constants.NOTIFICATION] = notification

                val headers: MutableMap<String, Any> = HashMap()
                headers[Constants.CONTENT_TYPE] = "application/json"
                headers[Constants.AUTHORIZATION] = "key=".plus(selectedApp.fcmKey)
                ApiService().pushNotification(headers, params) { returnValue: Boolean ->
                    if (returnValue) {
                        Toast.makeText(this, "Push Success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Push Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        if (bottomSheetLayout.etTitle.text.isNullOrEmpty()) {
            Toast.makeText(this, "Title is required!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (bottomSheetLayout.etBody.text.isNullOrEmpty()) {
            Toast.makeText(this, "Body is required!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (bottomSheetLayout.etFirebaseToken.text.isNullOrEmpty()) {
            Toast.makeText(this, "FCM Token is required!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    override fun setupObservers() {
        mAppListViewModel.appList.observe(this, Observer {
            mBinding.rvAppList.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = AppListAdapter(it) { app: App -> itemClicked(app) }
            }
            mBinding.rvAppList.addItemDecoration(
                DividerItemDecoration(
                    this,
                    LinearLayoutManager.VERTICAL
                )
            )
        })
    }

    private fun itemClicked(app: App) {
        selectedApp = app
        bottomSheetLayout.tvAppName.text = app.appName

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }
}
