package com.vaishali.customblinkbutton.helper

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.vaishali.customblinkbutton.R
import com.vaishali.customtoastmessage.Snacking
import com.vaishali.customtoastmessage.SnackingState


class MainActivityHelper(parentView: View, private var fab: ExtendedFloatingActionButton?) {

    private var parentView: View? = parentView
    private var context: Context? = null

    init {
        context = parentView.context
    }

    fun snackBarBasic() {
        parentView?.let {
            Snacking.Builder(it, "Hello! this is basic message").build().show()
        }
    }

    fun snackBarWithElevation() {
        parentView?.let {
            Snacking.Builder(it, "Hello! this is basic message").elevation(5F).build().show()
        }
    }

    fun snackBarIcon() {
        parentView?.let {
            Snacking.Builder(it, "This message with icon")
                .icon(R.drawable.ic_info, R.color.teal_200).build().show()
        }
    }

    fun snackBarAction() {
        parentView?.let {
            Snacking.Builder(it, "Click to dismiss message")
                .action("Dismiss", R.color.teal_200, object : Snacking.Callback {
                    override fun onActionClick(snackBar: Snacking?) {
                        snackBar?.dismiss()
                        toast("Action Click")
                    }

                }).build().show()
        }
    }

    fun snackBarCorner() {
        parentView?.let {
            Snacking.Builder(it, "This message with corner")
                .cornerRadius(R.dimen.snack_bar_corner_radius).build().show()
        }
    }

    fun snackBarCornerCustom() {
        parentView?.let {
            Snacking.Builder(it, "This message with custom corner").useMargin().cornerRadius(
                R.dimen.snack_bar_corner_radius, 0, 0, R.dimen.snack_bar_corner_radius
            ).build().show()
        }
    }

    fun snackBarWithCloseIcon() {
        parentView?.let {
            Snacking.Builder(it, "This message with margin").useMargin().build().show()
        }
    }

    fun snackBarBackground() {
        parentView?.let {
            Snacking.Builder(it, "This is custom background color")
                .backgroundColor(R.color.purple_200).build().show()
        }
    }
    fun snackBarBorder() {
        parentView?.let {
            Snacking.Builder(it, "This message with border")
                .border(R.dimen.snack_bar_border_size, R.color.colorPrimary).useMargin()
                .cornerRadius(R.dimen.snack_bar_corner_radius_small).build().show()
        }
    }

    fun snackBarTextColor() {
        parentView?.let {
            Snacking.Builder(it, "This is custom text color").textColor("#ffca28").build().show()
        }
    }

    fun snackBarFont() {
        parentView?.let {
            Snacking.Builder(it, "This is custom font family").fontFamily(R.font.montserrat).build()
                .show()
        }
    }

    fun snackBarBold() {
        parentView?.let {
            Snacking.Builder(it, "This is bold italic text").fontFamily(R.font.montserrat)
                .textStyle(Snacking.BOLD_ITALIC).build().show()
        }
    }

    fun snackBarAnchor() {
        parentView?.let {
            Snacking.Builder(it, "This message with anchor view").anchorView(fab).build().show()
        }
    }

    fun snackBarPosition() {
        parentView?.let {
            Snacking.State(it, SnackingState.WARNING).message("This message is on top position")
                .icon(R.drawable.ic_info).position(Snacking.TOP).useMargin(true)
                .cornerRadius(R.dimen.snack_bar_corner_radius_large)
                .border(R.dimen.snack_bar_border_size).action("Cancel", object : Snacking.Callback {
                    override fun onActionClick(snackBar: Snacking?) {
                        toast("Action Click")

                    }
                }).show()
        }
    }

    fun snackBarState() {
        parentView?.let {
            Snacking.State(it, SnackingState.State.INFO.ordinal)
                .message("This message is using state").icon(R.drawable.ic_info).useMargin(true)
                .cornerRadius(R.dimen.snack_bar_corner_radius_small)
                .action("CLOSE", object : Snacking.Callback {
                    override fun onActionClick(snackBar: Snacking?) {
                        toast("Action Clicked")
                        snackBar?.dismiss()

                    }
                })
        }
    }

    fun snackBarMaxLines() {
        parentView?.let {
            Snacking.Builder(
                it,
                "This is long message, this is long message, this is long message, this is long message, this is long message, this is long message"
            ).action("LONG BUTTON TEXT", object : Snacking.Callback {
                override fun onActionClick(snackBar: Snacking?) {
                    toast("Action Click")

                }
            }).messageMaxLines(2).build().show()
        }
    }

    fun snackBarLandscape() {
        parentView?.let {
            Snacking.State(it, SnackingState.State.INFO.ordinal)
                .message("This message is on landscape screen").icon(R.drawable.ic_info)
                .useMargin(true).cornerRadius(R.dimen.snack_bar_corner_radius_small)
                .action("CLOSE", object : Snacking.Callback {
                    override fun onActionClick(snackBar: Snacking?) {

                        toast("Action Click")
                        snackBar?.dismiss()
                    }
                }).landscapeStyle(Snacking.CENTER).border(R.dimen.snack_bar_border_size).show()
        }
    }

    private fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}