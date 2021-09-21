package com.example.customtoast.util

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import java.util.regex.Pattern

object CommonUtil {
    @Composable
    fun showToast(activity: ComponentActivity, message: String, types: MdToastType) {
        val mdToast = MDToast.makeTest(
            activity,
            message,
            duration = Toast.LENGTH_SHORT,
            type = types
        )
        mdToast.show()
    }
    val EMAIL_PATTERN: String =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    fun emailValidation(email: String): Boolean {
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

}