package com.example.customtoast.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import com.example.customtoast.R
import com.example.customtoast.ui.theme.*

class MDToast(mContext: Context) : Toast(mContext), LifecycleOwner, ViewModelStoreOwner,
    SavedStateRegistryOwner {
    private var mView: View? = null
    var type: MdToastType = MdToastType.INFO
    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    companion object {
        @Composable
        fun makeTest(
            context: ComponentActivity,
            message: String,
            duration: Int = LENGTH_SHORT,
            type: MdToastType = MdToastType.INFO
        ): MDToast {
            val mdToast = MDToast(context)
            val state = remember { mutableStateOf(0) }
            val views: ComposeView? = ComposeView(context)
            val mviews: View? = View(context)
            when (type) {
                MdToastType.SUCCESS -> {
                    views?.setContent {
                        Surface(modifier = Modifier.wrapContentSize(),color = Color.Transparent) {
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = colorSuccess,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                                    painter = painterResource(id = R.drawable.ic_check_circle_white_24dp),
                                    contentDescription = "",
                                )
                                Text(
                                    text = message,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    style = TextStyle(color = whiteBackground)
                                )
                            }
                        }
                    }
                }
                MdToastType.WARNING -> {
                    views?.setContent {
                        Surface(modifier = Modifier.wrapContentSize(),color = Color.Transparent) {
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = colorWarning,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                                    painter = painterResource(id = R.drawable.ic_warning_white_24dp),
                                    contentDescription = "",
                                )
                                Text(
                                    text = message,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    style = TextStyle(color = whiteBackground)
                                )
                            }
                        }
                    }
                }

                MdToastType.INFO -> {
                    views?.setContent {
                        Surface(modifier = Modifier.wrapContentSize(),color = Color.Transparent) {
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = colorInfo,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                                    painter = painterResource(id = R.drawable.ic_info_white_24dp),
                                    contentDescription = "",
                                )
                                Text(
                                    text = message,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    style = TextStyle(color = whiteBackground)
                                )
                            }
                        }
                    }

                }

                MdToastType.ERROR -> {
                    views!!.setContent {
                        Surface(modifier = Modifier.wrapContentSize(),color = Color.Transparent) {
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = colorError,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                                    painter = painterResource(id = R.drawable.ic_error_white_24dp),
                                    contentDescription = "",
                                )
                                Text(
                                    text = message,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    style = TextStyle(color = whiteBackground)
                                )
                            }
                        }
                    }
                }
            }
         //   views?.setBackgroundColor(context.resources.getColor(R.color.black))
            ViewTreeLifecycleOwner.set(views!!, context)
            ViewTreeViewModelStoreOwner.set(views, context)
            ViewTreeSavedStateRegistryOwner.set(views, context)
            mdToast.duration = duration
            mdToast.view = views
            //(mdToast.view as ComposeView).setBackgroundColor(context.resources.getColor(R.color.black))
            mdToast.mView = views
            return mdToast
        }
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    private val savedStateRegistry = SavedStateRegistryController.create(this)
    override fun getSavedStateRegistry(): SavedStateRegistry = savedStateRegistry.savedStateRegistry

    //ViewModelStore Methods
    private val store = ViewModelStore()

    override fun getViewModelStore(): ViewModelStore = store
}