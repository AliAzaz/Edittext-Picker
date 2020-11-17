package com.edittextpicker.aliazaz.utils

import android.view.View
import java.util.concurrent.atomic.AtomicInteger

fun createViewId(): Int {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
        return View.generateViewId()
    } else {
        val generatedID = AtomicInteger(1)
        while (true) {
            val output: Int = generatedID.get()
            var newValue = output + 1
            if (newValue > 0x00FFFFFF) newValue = 1
            if (generatedID.compareAndSet(output, newValue)) {
                return output
            }
        }
    }
}