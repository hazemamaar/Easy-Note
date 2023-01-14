@file:Suppress("UNCHECKED_CAST")

package com.android.easynote.core.extention

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type



fun Activity.hideKeyboard() =
    WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.ime())

fun View.hideKeyboard() = (this.context as? Activity)?.hideKeyboard()

fun View.gone()= run { this.visibility=View.GONE }
fun View.visible()= run { this.visibility=View.VISIBLE }
fun Fragment.snackBar(message: String) {
    requireView().hideKeyboard()
    Snackbar.make(
        requireView(),
        message,
        Snackbar.LENGTH_LONG
    ).show()
}
fun Fragment.toast(message: String) {
    requireView().hideKeyboard()
    Toast.makeText(
        requireContext(),
        message,
        Toast.LENGTH_LONG
    ).show()
}
infix fun View.snackBar(message: String) {
    hideKeyboard()
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).show()

}

fun <T : Any> Any.getTClass(): Class<T> {
    val type: Type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
    return type as Class<T>
}

fun <B : ViewBinding> LifecycleOwner.bindView(container: ViewGroup? = null): B {
    return if (this is Activity) {
        val inflateMethod = getTClass<B>().getMethod("inflate", LayoutInflater::class.java)
        val invokeLayout = inflateMethod.invoke(null, this.layoutInflater) as B
        this.setContentView(invokeLayout.root)
        invokeLayout
    } else {
        val fragment = this as Fragment
        val inflateMethod = getTClass<B>().getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
        }
        val invoke: B = inflateMethod.invoke(null, layoutInflater, container, false) as B
        invoke
    }
}

fun <T : Any?, L : SharedFlow<T>> LifecycleOwner.observe(sharedFlow: L, body: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            sharedFlow.collect {
                body.invoke(it)
            }
        }
    }
}