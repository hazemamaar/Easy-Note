package com.android.easynote.core.base

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.android.easynote.core.extention.bindView


abstract class BaseActivity <B : ViewBinding,VM:ViewModel> : AppCompatActivity() {
    companion object {
        const val SCREEN_ID = "SCREEN_ID"
    }

    val baseShowProgress = false
    lateinit var binding: B
    protected abstract val mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindView()
    }

    fun setOpacityBackground(view: View, @ColorRes color: Int) {
        view.setBackgroundColor(
            try {
                ContextCompat.getColor(this, color)
            } catch (e: Resources.NotFoundException) {
                0
            }
        )
    }

}