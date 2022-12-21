package com.android.easynote.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.android.easynote.core.base.BaseActivity
import com.android.easynote.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(){
    override val mViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}