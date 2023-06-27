package com.example.streamingapp.utils

import android.app.Activity
import android.content.Context
import android.os.SystemClock
import android.view.View
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment

private val Context.prefDataStore by preferencesDataStore(
    name = AppConstants.PREFERENCE_DATA_STORE_NAME
)

fun appPreference(context: Context) = AppPreference(context.prefDataStore)

val Fragment.canOpenDialog: Boolean
    get() = requireActivity().canOpenDialog

val Activity.canOpenDialog: Boolean
    get() = !isDestroyed && !isFinishing

fun android.app.AlertDialog.showIfPossible(activity: Activity) {
    if (activity.canOpenDialog && !isShowing) show()
}

private class SafeClickListener(
    private var defaultInterval: Int = 700,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

fun View.setSafeOnClickListener(
    minimumIntervalBeforeNextClick: Int = 700,
    onClick: (View) -> Unit
) {
    val safeClickListener = SafeClickListener(minimumIntervalBeforeNextClick) {
        onClick(it)
    }
    setOnClickListener(safeClickListener)
}
