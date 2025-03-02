package com.vaishali.customtoastmessage

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import com.vaishali.customtoastmessage.Snacking.LandscapeStyle

open class SnackingBuilder {
    private val snackBar: Snacking

    constructor(parentView: View) {
        snackBar = Snacking(parentView)
        snackBar.useMargin()
        snackBar.backgroundColor(R.color.quick_snack_bar_color_white)
        snackBar.cornerRadius(20F)
    }

    constructor(parentView: View, message: String?) {
        snackBar = Snacking(parentView, message)
        snackBar.useMargin()
        snackBar.backgroundColor(R.color.quick_snack_bar_color_white)
        snackBar.cornerRadius(20F)

    }

    constructor(parentView: View, message: String?, @DrawableRes iconRes: Int) {
        snackBar = Snacking(parentView, message, iconRes)
        snackBar.useMargin()
        snackBar.backgroundColor(R.color.quick_snack_bar_color_white)
        snackBar.cornerRadius(20F)

    }

    fun message(@StringRes messageRes: Int): SnackingBuilder {
        snackBar.message(messageRes)
        return this
    }

    fun message(message: String?): SnackingBuilder {
        snackBar.message(message)
        return this
    }

    fun messageMaxLines(lines: Int): SnackingBuilder {
        snackBar.messageMaxLines(lines)
        return this
    }

    fun textColor(@ColorRes colorRes: Int): SnackingBuilder {
        snackBar.textColor(colorRes)
        return this
    }

    fun textColor(colorCode: String): SnackingBuilder {
        snackBar.textColor(colorCode)
        return this
    }

    fun textStyle(style: Int): SnackingBuilder {
        snackBar.textStyle(style)
        return this
    }

    fun icon(@DrawableRes iconRes: Int): SnackingBuilder {
        snackBar.icon(iconRes)
        return this
    }

    fun elevation( elevation: Float): SnackingBuilder {
        snackBar.elevation(elevation)
        return this
    }

    fun icon(@DrawableRes iconRes: Int, @ColorRes colorRes: Int): SnackingBuilder {
        snackBar.icon(iconRes, colorRes)
        return this
    }

    fun icon(@DrawableRes iconRes: Int, colorCode: String): SnackingBuilder {
        snackBar.icon(iconRes, colorCode)
        return this
    }

    fun background(@DrawableRes drawableRes: Int): SnackingBuilder {
        snackBar.background(drawableRes)
        return this
    }

    fun action(@StringRes actionTextRes: Int, callback: Snacking.Callback): SnackingBuilder {
        snackBar.action(actionTextRes, callback)
        return this
    }

    fun action(actionText: String, callback: Snacking.Callback): SnackingBuilder {
        snackBar.action(actionText, callback)
        return this
    }

    fun action(
        actionText: String,
        @ColorRes textColorRes: Int,
        callback: Snacking.Callback
    ): SnackingBuilder {
        snackBar.action(actionText, textColorRes, callback)
        return this
    }

    fun action(
        @StringRes actionTextRes: Int,
        @ColorRes textColorRes: Int,
        callback: Snacking.Callback
    ): SnackingBuilder {
        snackBar.action(actionTextRes, textColorRes, callback)
        return this
    }

    fun action(
        actionText: String,
        textColorCode: String,
        callback: Snacking.Callback
    ): SnackingBuilder {
        snackBar.action(actionText, textColorCode, callback)
        return this
    }

    fun action(
        @StringRes actionTextRes: Int,
        textColorCode: String,
        callback: Snacking.Callback
    ): SnackingBuilder {
        snackBar.action(actionTextRes, textColorCode, callback)
        return this
    }

    fun cornerRadius(@DimenRes cornerRadiusRes: Int): SnackingBuilder {
        snackBar.cornerRadius(cornerRadiusRes)
        return this
    }

    fun cornerRadius(cornerRadius: Float): SnackingBuilder {
        snackBar.cornerRadius(cornerRadius)
        return this
    }

    fun cornerRadius(
        @DimenRes topLeftRes: Int,
        @DimenRes topRightRes: Int,
        @DimenRes bottomLeftRes: Int,
        @DimenRes bottomRightRes: Int
    ): SnackingBuilder {
        snackBar.cornerRadius(
            topLeftRes, topRightRes, bottomLeftRes, bottomRightRes
        )
        return this
    }

    fun cornerRadius(
        topLeft: Float, topRight: Float,
        bottomLeft: Float, bottomRight: Float
    ): SnackingBuilder {
        snackBar.cornerRadius(
            topLeft, topRight, bottomLeft, bottomRight
        )
        return this
    }

    fun border(@DimenRes borderRes: Int, @ColorRes colorRes: Int): SnackingBuilder {
        snackBar.border(borderRes, colorRes)
        return this
    }

    fun border(borderSize: Float, colorCode: String): SnackingBuilder {
        snackBar.border(borderSize, colorCode)
        return this
    }

    fun backgroundColor(@ColorRes colorRes: Int): SnackingBuilder {
        snackBar.backgroundColor(colorRes)
        return this
    }

    fun backgroundColor(colorCode: String): SnackingBuilder {
        snackBar.backgroundColor(colorCode)
        return this
    }

    fun useMargin(): SnackingBuilder {
        snackBar.useMargin()
        return this
    }

    fun withCloseIcon(): SnackingBuilder {
        snackBar.useMargin()
        return this
    }

    fun height(height: Int): SnackingBuilder {
        snackBar.height(height)
        return this
    }

    fun anchorView(anchorView: View?): SnackingBuilder {
        snackBar.anchorView(anchorView)
        return this
    }

    fun duration(@Snacking.Duration duration: Int): SnackingBuilder {
        snackBar.duration(duration)
        return this
    }

    fun fontFamily(@FontRes fontRes: Int): SnackingBuilder {
        snackBar.fontFamily(fontRes)
        return this
    }

    fun fontFamily(@FontRes fontRes: Int, @DimenRes fontSizeRes: Int): SnackingBuilder {
        snackBar.fontFamily(fontRes, fontSizeRes)
        return this
    }

    fun fontFamily(@FontRes fontRes: Int, fontSize: Float): SnackingBuilder {
        snackBar.fontFamily(fontRes, fontSize)
        return this
    }

    fun position(@Snacking.Position position: Int): SnackingBuilder {
        snackBar.position(position)
        return this
    }

    fun landscapeStyle(@LandscapeStyle landscapeStyle: Int): SnackingBuilder {
        snackBar.landscapeStyle(landscapeStyle)
        return this
    }

    fun landscapeWidthRes(@DimenRes landscapeWidthRes: Int): SnackingBuilder {
        snackBar.landscapeWidthRes(landscapeWidthRes)
        return this
    }

    fun landscapeWidth(landscapeWidth: Int): SnackingBuilder {
        snackBar.landscapeWidth(landscapeWidth)
        return this
    }

    fun swipeToDismiss(swipeToDismiss: Boolean): SnackingBuilder {
        snackBar.swipeToDismiss(swipeToDismiss)
        return this
    }

    fun build(): Snacking {
        return snackBar
    }
}
