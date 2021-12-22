package com.codelabs.cryptoapp.common

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

class UiStateController<T : View>(
    private val mainView: T,
    private val progressBar: ProgressBar,
    private val tvMessage: TextView
) {
    init {
        hideAll()
    }

    fun showMainView(onVisible: ((view: T) -> Unit)? = null) {
        hideAll()
        mainView.visibility = View.VISIBLE
        onVisible?.invoke(mainView)
    }

    fun showProgress() {
        hideAll()
        progressBar.visibility = View.VISIBLE
    }

    fun showError(message: String) {
        hideAll()
        tvMessage.apply {
            text = message
            visibility = View.VISIBLE
        }
    }

    private fun hideAll() {
        mainView.visibility = View.GONE
        progressBar.visibility = View.GONE
        tvMessage.apply {
            text = ""
            visibility = View.GONE
        }
    }
}