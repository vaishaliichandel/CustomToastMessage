package com.vaishali.customtoastmessage

import android.content.res.Resources
import android.util.Log
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.vaishali.customtoastmessage.Snacking.LandscapeStyle

open class SnackingState(private val parentView: View, @States state: Int) {
    private val snackBar: Snacking

    enum class State {
        SUCCESS, FAILED, WARNING, INFO, DISABLE
    }
    @Retention(
        AnnotationRetention.SOURCE
    )
    annotation class States

    private var currentState = SUCCESS

    init {
        this.currentState = state
        snackBar = Snacking(parentView)
        state(currentState)
    }

    private fun state(@States state: Int) {
        this.currentState = state
        var color = R.color.quick_snack_bar_state_success_background
        when (state) {
            DISABLE -> {
                color = R.color.quick_snack_bar_state_disable_background
            }
            INFO -> {
                color = R.color.quick_snack_bar_state_info_background
            }
            WARNING -> {
                color = R.color.quick_snack_bar_state_warning_background
            }
            FAILED -> {
                color = R.color.quick_snack_bar_state_failed_background
            }
        }
        snackBar.backgroundColor(color)
        snackBar.textColor(textColor)
    }

    fun message(@StringRes messageRes: Int): SnackingState {
        snackBar.message(messageRes)
        return this
    }

    fun message(message: String?): SnackingState {
        snackBar.message(message)
        return this
    }

    fun messageMaxLines(lines: Int): SnackingState {
        snackBar.messageMaxLines(lines)
        return this
    }

    fun icon(@DrawableRes iconRes: Int): SnackingState {
        var color = R.color.quick_snack_bar_state_success_icon
        if (currentState == DISABLE) {
            color = R.color.quick_snack_bar_state_disable_icon
        } else if (currentState == INFO) {
            color = R.color.quick_snack_bar_state_info_icon
        } else if (currentState == WARNING) {
            color = R.color.quick_snack_bar_state_warning_icon
        } else if (currentState == FAILED) {
            color = R.color.quick_snack_bar_state_failed_icon
        }
        snackBar.icon(iconRes, color)
        return this
    }

    fun action(@StringRes actionTextRes: Int, callback: Snacking.Callback): SnackingState {
        var color = R.color.quick_snack_bar_state_success_action
        when (currentState) {
            DISABLE -> {
                color = R.color.quick_snack_bar_state_disable_action
            }
            INFO -> {
                color = R.color.quick_snack_bar_state_info_action
            }
            WARNING -> {
                color = R.color.quick_snack_bar_state_warning_action
            }
            FAILED -> {
                color = R.color.quick_snack_bar_state_failed_action
            }
        }
        snackBar.action(actionTextRes, color, callback)
        return this
    }

    fun action(actionText: String, callback: Snacking.Callback): SnackingState {
        var color = R.color.quick_snack_bar_state_success_action
        when (currentState) {
            DISABLE -> {
                color = R.color.quick_snack_bar_state_disable_action
            }
            INFO -> {
                color = R.color.quick_snack_bar_state_info_action
            }
            WARNING -> {
                color = R.color.quick_snack_bar_state_warning_action
            }
            FAILED -> {
                color = R.color.quick_snack_bar_state_failed_action
            }
        }
        snackBar.action(actionText, color, callback)
        return this
    }

    fun cornerRadius(@DimenRes cornerRadiusRes: Int): SnackingState {
        snackBar.cornerRadius(cornerRadiusRes)
        return this
    }

    fun cornerRadius(cornerRadius: Float): SnackingState {
        snackBar.cornerRadius(cornerRadius)
        return this
    }

    fun cornerRadius(
        topLeft: Float, topRight: Float,
        bottomLeft: Float, bottomRight: Float
    ): SnackingState {
        snackBar.cornerRadius(
            topLeft, topRight, bottomLeft, bottomRight
        )
        return this
    }

    fun cornerRadius(
        @DimenRes topLeftRes: Int,
        @DimenRes topRightRes: Int,
        @DimenRes bottomLeftRes: Int,
        @DimenRes bottomRightRes: Int
    ): SnackingState {
        snackBar.cornerRadius(
            topLeftRes, topRightRes, bottomLeftRes, bottomRightRes
        )
        return this
    }

    fun border(@DimenRes borderRes: Int): SnackingState {
        var color = R.color.quick_snack_bar_state_success_border
        when (currentState) {
            DISABLE -> {
                color = R.color.quick_snack_bar_state_disable_border
            }
            INFO -> {
                color = R.color.quick_snack_bar_state_info_border
            }
            WARNING -> {
                color = R.color.quick_snack_bar_state_warning_border
            }
            FAILED -> {
                color = R.color.quick_snack_bar_state_failed_border
            }
        }
        snackBar.border(borderRes, color)
        return this
    }

    fun border(borderSize: Float): SnackingState {
        var id = R.color.quick_snack_bar_state_success_border
        when (currentState) {
            DISABLE -> {
                id = R.color.quick_snack_bar_state_disable_border
            }
            INFO -> {
                id = R.color.quick_snack_bar_state_info_border
            }
            WARNING -> {
                id = R.color.quick_snack_bar_state_warning_border
            }
            FAILED -> {
                id = R.color.quick_snack_bar_state_failed_border
            }
        }
        val getColor = getColor(id)
        if (getColor != 0) snackBar.border(borderSize, "#" + Integer.toHexString(getColor))
        return this
    }

    fun useMargin(isUseMargin: Boolean): SnackingState {
        snackBar.useMargin()
        return this
    }

    fun heightRes(@DimenRes heightRes: Int): SnackingState {
        snackBar.heightRes(heightRes)
        return this
    }

    fun height(height: Int): SnackingState {
        snackBar.height(height)
        return this
    }

    fun setAnchorView(anchorView: View?): SnackingState {
        snackBar.anchorView(anchorView)
        return this
    }

    fun duration(@Snacking.Duration duration: Int): SnackingState {
        snackBar.duration(duration)
        return this
    }

    fun fontFamily(@FontRes fontRes: Int): SnackingState {
        snackBar.fontFamily(fontRes)
        return this
    }

    fun fontFamily(@FontRes fontRes: Int, @DimenRes fontSizeRes: Int): SnackingState {
        snackBar.fontFamily(fontRes, fontSizeRes)
        return this
    }

    fun fontFamily(@FontRes fontRes: Int, fontSize: Float): SnackingState {
        snackBar.fontFamily(fontRes, fontSize)
        return this
    }

    fun position(@Snacking.Position position: Int): SnackingState {
        snackBar.position(position)
        return this
    }

    fun landscapeStyle(@LandscapeStyle landscapeStyle: Int): SnackingState {
        snackBar.landscapeStyle(landscapeStyle)
        return this
    }

    fun landscapeWidthRes(@DimenRes landscapeWidthRes: Int): SnackingState {
        snackBar.landscapeWidthRes(landscapeWidthRes)
        return this
    }

    fun landscapeWidth(landscapeWidth: Int): SnackingState {
        snackBar.landscapeWidth(landscapeWidth)
        return this
    }

    fun swipeToDismiss(swipeToDismiss: Boolean): SnackingState {
        snackBar.swipeToDismiss(swipeToDismiss)
        return this
    }

    fun show() {
        snackBar.show()
    }

    private val textColor: Int get() = R.color.quick_snack_bar_color_black

    private fun getColor(id: Int): Int {
        var finalColor = 0
        try {
            finalColor = ContextCompat.getColor(parentView.context, id)
        } catch (e: Resources.NotFoundException) {
            Log.d("QuickSnackBar", "Color resource not found")
        }
        return finalColor
    }

    companion object {
        const val SUCCESS: Int = 0
        const val FAILED: Int = 1
        const val WARNING: Int = 2
        const val INFO: Int = 3
        const val DISABLE: Int = 4
    }
}
