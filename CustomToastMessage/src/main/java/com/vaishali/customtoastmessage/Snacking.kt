package com.vaishali.customtoastmessage

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.vaishali.customtoastmessage.databinding.QuickSnackBarCustomLayoutBinding

class Snacking(private val parentView: View, private var message: String?) {
    lateinit var binding: QuickSnackBarCustomLayoutBinding

    @DrawableRes
    private var iconRes = 0

    @DrawableRes
    private var backgroundRes = 0
    private var backgroundColor = 0
    private var borderSize: Float = 0f
    private var borderColor: Int = 0
    private var durationSnackBar = BaseTransientBottomBar.LENGTH_SHORT
    private var useMargin = false
    private var position = BOTTOM
    private var landscapeStyle = MATCH
    private var landscapeWidth = 0
    private var handler: Handler? = null

    private var cornerTopLeft = 0f
    private var cornerTopRight = 0f
    private var cornerBottomLeft = 0f
    private var cornerBottomRight = 0f

    // SnackBar
    private var snackBar: Snackbar? = null

    // Callback
    private var callback: Callback? = null
    private var fab: View? = null

    @IntDef(NORMAL, BOLD, ITALIC, BOLD_ITALIC)
    @Retention(AnnotationRetention.SOURCE)
    annotation class TextStyle

    @IntDef(TOP, BOTTOM)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Position

    private var rotation = 0

    @IntDef(SORT, LONG, INDEFINITE)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Duration {
    }

    init {
        init()
    }

    @SuppressLint("RestrictedApi")
    private fun init() {
        val context = parentView.context
        val inflater = LayoutInflater.from(context)
        snackBar = Snackbar.make(parentView, "", durationSnackBar)
        rotation = getRotation(context)

       binding = QuickSnackBarCustomLayoutBinding.inflate(inflater)
        val mMessage = if (message == null) "This is null message" else message
        binding.textMessage.text = mMessage
        applyIcon()
        binding.textAction.setOnClickListener {
            if (callback != null) {
                try {
                    Thread.sleep(300)
                    callback?.onActionClick(this)
                } catch (e: InterruptedException) {
                    log("Callback thread interrupted")
                }
            }
        }

        binding.closeIcon.setOnClickListener {
            if (callback != null) {
                try {
                    Thread.sleep(300)
                    callback?.onActionClick(this)
                } catch (e: InterruptedException) {
                    log("Callback thread interrupted")
                }
            }
        }
        if (snackBar != null) {
            val getDrawable = getDrawables(R.drawable.bg_custom_quick_snack_bar)
            val drawable = getDrawable as GradientDrawable?
            setSnackBarElevation(0f)
            if (drawable != null) {
                drawable.cornerRadius = 0f
                drawable.setStroke(0, null)
                drawable.setColor(getColor(R.color.quick_snack_bar_default_background))
            }
            setSnackBarBackground(drawable)
            val snackBarLayout = snackBar?.view as SnackbarLayout
            snackBarLayout.addView(binding.root)
            snackBar?.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)

            if (parentView is CoordinatorLayout) {
                for (i in 0 until parentView.getChildCount()) {
                    val child = parentView.getChildAt(i)
                    if (child is FloatingActionButton) {
                        fab = child
                        break
                    } else if (child is ExtendedFloatingActionButton) {
                        fab = child
                        break
                    }
                }
            }
        }
    }

    fun elevation(elevation: Float) {
        setSnackBarElevation(elevation)

    }


    fun message(@StringRes messageRes: Int) {
        this.message = getString(messageRes)
        binding.textMessage.text = message
    }

    fun message(message: String?) {
        this.message = message
        binding.textMessage.text = message
    }

    fun messageMaxLines(lines: Int) {
        if (lines > 0) {
           binding.textMessage.maxLines = lines
           binding.textMessage.ellipsize = TextUtils.TruncateAt.END
        }
    }

    fun textColor(@ColorRes colorRes: Int) {
        val color = getColor(colorRes)
        if (color != 0) binding.textMessage.setTextColor(color)
    }

    fun textColor(colorCode: String) {
        val color = parseColor(colorCode)
        if (color != 0) binding.textMessage.setTextColor(color)
    }

    fun textStyle(@TextStyle style: Int) {
        when (style) {
            BOLD -> {
                binding.textMessage.setTypeface(binding.textMessage.typeface, Typeface.BOLD)
            }

            ITALIC -> {
                binding.textMessage.setTypeface(binding.textMessage.typeface, Typeface.ITALIC)
            }

            BOLD_ITALIC -> {
                binding. textMessage.setTypeface(binding.textMessage.typeface, Typeface.BOLD_ITALIC)
            }

            else -> {
                binding.textMessage.setTypeface(binding.textMessage.typeface, Typeface.NORMAL)
            }
        }
    }

    fun icon(@DrawableRes iconRes: Int) {
        this.iconRes = iconRes
        applyIcon()
    }

    fun icon(@DrawableRes iconRes: Int, @ColorRes colorRes: Int) {
        this.iconRes = iconRes
        applyIcon()
        val color = getColor(colorRes)
        if (color != 0) binding.imgIcon.setColorFilter(color)
    }

    fun icon(@DrawableRes iconRes: Int, colorCode: String) {
        this.iconRes = iconRes
        val color = parseColor(colorCode)
        if (color != 0) binding.imgIcon.setColorFilter(color)
        applyIcon()
    }

    private fun applyIcon() {
        if (iconRes != 0) {
            binding.imgIcon.visibility = View.VISIBLE
            binding.imgIcon.setImageResource(iconRes)
        } else {
            binding.imgIcon.visibility = View.GONE
        }
    }

    fun action(actionText: String, callback: Callback) {
        this.callback = callback
        binding.textAction.visibility = View.VISIBLE
        binding. textAction.text = actionText
    }

    fun action(@StringRes actionTextRes: Int, callback: Callback) {
        this.callback = callback
        binding. textAction.visibility = View.VISIBLE
        binding. textAction.text = getString(actionTextRes)
    }

    fun actionClose(@DrawableRes actionTextRes: Int, callback: Callback) {
        this.callback = callback
        binding.closeIcon.visibility = View.VISIBLE
        binding.closeIcon.setImageResource(actionTextRes)
    }

    fun action(actionText: String, @ColorRes textColorRes: Int, callback: Callback) {
        action(actionText, callback)
        val color = getColor(textColorRes)
        if (color != 0)binding. textAction.setTextColor(color)
    }

    fun action(@StringRes actionTextRes: Int, @ColorRes textColorRes: Int, callback: Callback) {
        action(actionTextRes, callback)
        val color = getColor(textColorRes)
        if (color != 0) binding.textAction.setTextColor(color)
    }

    fun actionClose(@DrawableRes actionTextRes: Int, @ColorRes textColorRes: Int, callback: Callback) {
        actionClose(actionTextRes, callback)
        val color = getColor(textColorRes)
        if (color != 0) binding.textAction.setTextColor(color)
    }

    fun action(actionText: String, textColorCode: String, callback: Callback) {
        action(actionText, callback)
        val color = parseColor(textColorCode)
        if (color != 0) binding.textAction.setTextColor(color)
    }

    fun action(@StringRes actionTextRes: Int, textColorCode: String, callback: Callback) {
        action(actionTextRes, callback)
        val color = parseColor(textColorCode)
        if (color != 0) binding.textAction.setTextColor(color)
    }

    fun cornerRadius(cornerRadius: Float) {
        if (cornerRadius != 0f) {
            cornerTopLeft = cornerRadius
            cornerTopRight = cornerRadius
            cornerBottomLeft = cornerRadius
            cornerBottomRight = cornerRadius
        }
    }

    fun cornerRadius(
        topLeft: Float, topRight: Float,
        bottomLeft: Float, bottomRight: Float
    ) {
        cornerTopLeft = topLeft
        cornerTopRight = topRight
        cornerBottomLeft = bottomLeft
        cornerBottomRight = bottomRight
    }

    private fun cornerRadii(
        topLeft: Float, topRight: Float,
        bottomLeft: Float, bottomRight: Float
    ): FloatArray {
        return floatArrayOf(
            topLeft, topLeft, topRight, topRight,
            bottomRight, bottomRight, bottomLeft, bottomLeft
        )
    }

    private fun applyBackgroundBitmap(
        bitmapDrawable: BitmapDrawable,
        topLeft: Float, topRight: Float, bottomLeft: Float, bottomRight: Float
    ) {
        if (bitmapDrawable.bitmap != null) {
            binding.root.post {
                var scaledBitmap: Bitmap? = null
                val sWidth = binding.root.context.resources.displayMetrics.widthPixels
                val padding =
                    getDimenInt(R.dimen.quick_snack_bar_margin_start_bottom_end)
                val totalWidth = sWidth - (if (useMargin) (padding * 2) else 0)
                try {
                    scaledBitmap = matchBitmap(bitmapDrawable, totalWidth)
                } catch (e: Exception) {
                    log("Bitmap size error: " + e.message)
                } catch (e: OutOfMemoryError) {
                    log("Bitmap size error: " + e.message)
                }
                if (scaledBitmap != null) {
                    val snackBarTotalHeight = snackBar?.view?.height?:0
                    val halfBitmap = scaledBitmap.height.toFloat() / 2f
                    val halfSnackBarHeight = snackBarTotalHeight.toFloat() / 2f
                    val startY = halfBitmap - halfSnackBarHeight

                    val croppedBitmap =
                        Bitmap.createBitmap(
                            scaledBitmap,
                            0,
                            startY.toInt(),
                            totalWidth,
                            snackBarTotalHeight
                        )

                    val output = Bitmap.createBitmap(
                        totalWidth,
                        snackBarTotalHeight,
                        Bitmap.Config.ARGB_8888
                    )
                    val canvas = Canvas(output)

                    val paint = Paint()
                    paint.isAntiAlias = true
                    paint.color = -0xbdbdbe
                    val arrCorner =
                        cornerRadii(topLeft, topRight, bottomLeft, bottomRight)
                    val rect =
                        Rect(0, 0, output.width, output.height)
                    val rectF = RectF()
                    rectF[0f, 0f, totalWidth.toFloat()] = snackBar?.view?.height?.toFloat()?:0f
                    val path = Path()
                    path.addRoundRect(rectF, arrCorner, Path.Direction.CW)
                    canvas.drawARGB(0, 0, 0, 0)
                    canvas.drawPath(path, paint)

                    paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
                    canvas.drawBitmap(croppedBitmap, rect, rectF, paint)
                    if (borderSize > 0 && borderColor != 0) {
                        val paintBorder = Paint()
                        paintBorder.style = Paint.Style.STROKE
                        paintBorder.color = borderColor
                        paintBorder.strokeWidth = borderSize
                        paintBorder.isAntiAlias = true
                        paintBorder.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
                        canvas.drawPath(path, paintBorder)
                    }
                    val finalDrawable: Drawable =
                        BitmapDrawable(binding.root.context.resources, output)
                    setSnackBarBackground(finalDrawable)
                }
            }
        }
    }

    private fun matchBitmap(drawable: Drawable, outputWidth: Int): Bitmap {
        // Get Drawable Size
        val getDrawableWidth = drawable.intrinsicWidth
        val drawableWidth = if (getDrawableWidth > 0) getDrawableWidth else 1
        val getDrawableHeight = drawable.intrinsicHeight
        val drawableHeight = if (getDrawableHeight > 0) getDrawableHeight else 1
        // Create Canvas from drawable
        val bitmap = Bitmap.createBitmap(drawableWidth, drawableHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        // Bitmap Size
        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height
        // Output Size
        val outputHeight = bitmapHeight * outputWidth / bitmapWidth
        // Create New Bitmap
        return Bitmap.createScaledBitmap(bitmap, outputWidth, outputHeight, false)
    }

    fun border(@DimenRes borderRes: Int, @ColorRes borderColorRes: Int) {
        val size = getDimenInt(borderRes)
        if (size > 0) {
            this.borderSize = size.toFloat()
            val color = getColor(borderColorRes)
            if (color != 0) this.borderColor = color
        }
    }

    fun border(borderSize: Float, @ColorRes borderColorRes: Int) {
        if (borderSize > 0) {
            this.borderSize = borderSize
            val color = getColor(borderColorRes)
            if (color != 0) this.borderColor = color
        }
    }

    fun background(@DrawableRes drawableRes: Int) {
        this.backgroundRes = drawableRes
    }

    fun backgroundColor(@ColorRes colorRes: Int) {
        val color = getColor(colorRes)
        if (color != 0) this.backgroundColor = color
    }

    fun backgroundColor(colorCode: String) {
        val color = parseColor(colorCode)
        if (color != 0) this.backgroundColor = color
    }

    fun useMargin() {
        this.useMargin = true
    }

    fun removeMargin() {
        this.useMargin = false
    }

    @SuppressLint("RestrictedApi")
    private fun applyMargin() {
        if (snackBar != null) {
            val params = snackBar?.view?.layoutParams as MarginLayoutParams
            if (!useMargin) {
                params.setMargins(0, 0, 0, 0)
                val snackBarLayout = snackBar?.view as SnackbarLayout
                snackBarLayout.setPadding(0, 0, 0, 0)
            } else {
                val size = getDimenInt(R.dimen.quick_snack_bar_margin_start_bottom_end)
                params.setMargins(size, 0, size, size)
            }
            snackBar?.view?.layoutParams = params
        }
    }

    private fun applyPosition() {
        if (snackBar != null) {
            if (position == TOP) {
                if (rotation == 90 || rotation == -90) {
                    if (landscapeStyle != MATCH) {
                        setOnLandscape(0)
                    } else {
                        setOnPosition()
                    }
                } else {
                    setOnPosition()
                }
            } else {
                if (rotation == 90 || rotation == -90) {
                    if (landscapeWidth > 0 && landscapeStyle == MATCH) landscapeStyle = LEFT
                    if (landscapeStyle != MATCH) setOnLandscape(1)
                }
            }

            binding.textMessage.post {
                if (binding.textMessage.lineCount > 2 || binding.textAction.length() > 10) {
                    val paramsIcon =
                        binding.imgIcon.layoutParams as RelativeLayout.LayoutParams
                    paramsIcon.removeRule(RelativeLayout.CENTER_VERTICAL)
                    paramsIcon.addRule(RelativeLayout.ALIGN_TOP,binding. textMessage.id)

                    val getMarginTop =
                        getDimenInt(R.dimen.quick_snack_bar_vertical_padding)
                    val countMarginTop = getMarginTop / 6f
                    val marginTop = getMarginTop - countMarginTop.toInt()
                    paramsIcon.setMargins(
                        getDimenInt(R.dimen.quick_snack_bar_icon_margin_start),
                        marginTop, 0, 0
                    )
                    binding.imgIcon.layoutParams = paramsIcon

                    val paramsMessage =
                        binding.textMessage.layoutParams as RelativeLayout.LayoutParams
                    paramsMessage.removeRule(RelativeLayout.START_OF)
                 binding.  textMessage.layoutParams = paramsMessage
                 binding.  textMessage.setPadding(
                 binding.      textMessage.paddingStart,
                 binding.      textMessage.paddingTop,
                 binding.      textMessage.paddingEnd,
                        getDimenInt(R.dimen.quick_snack_bar_default_elevation)
                    )

                    val paramsAction =
                        binding.textAction.layoutParams as RelativeLayout.LayoutParams
                    paramsAction.removeRule(RelativeLayout.ALIGN_TOP)
                    paramsAction.removeRule(RelativeLayout.ALIGN_BOTTOM)
                    paramsAction.addRule(RelativeLayout.BELOW, binding.textMessage.id)

                    binding.textAction.layoutParams = paramsAction
                    val getActionPaddingVertical = binding.textAction.paddingTop
                    val count = getActionPaddingVertical / 2.5f
                    val actionPaddingVertical = getActionPaddingVertical - count.toInt()
                    binding.textAction.setPadding(
                        binding.textAction.paddingStart , actionPaddingVertical,
                        binding. textAction.paddingEnd , actionPaddingVertical
                    )
                    binding.root.setPadding(
                        0,
                        0,
                        0,
                        getDimenInt(R.dimen.quick_snack_bar_icon_margin_start)
                    )
                }
            }
        }
    }

    private fun setOnPosition() {
        val param = snackBar?.view?.layoutParams
        snackBar?.setDuration(BaseTransientBottomBar.LENGTH_INDEFINITE)
        if (param != null) {
            when (param) {
                is CoordinatorLayout.LayoutParams -> {
                    paramCoordinator(param).gravity = Gravity.TOP
                }

                is FrameLayout.LayoutParams -> {
                    paramFrame(param).gravity = Gravity.TOP
                }

                is RelativeLayout.LayoutParams -> {
                    paramRelative(param).addRule(
                        RelativeLayout.ALIGN_PARENT_TOP,
                        RelativeLayout.TRUE
                    )
                }
            }
            snackBar?.view?.layoutParams = param
            snackBar?.view?.startAnimation(
                AnimationUtils.loadAnimation(
                    binding.root.context,
                    R.anim.anim_quick_snack_bar_slide_bottom
                )
            )
        }
    }

    private fun setOnLandscape(type: Int) {
        val param = snackBar?.view?.layoutParams
        val screenWidth = getWidthScreen(binding.root.context)
        if (landscapeWidth > 0) {
            if (landscapeWidth < screenWidth) param?.width = landscapeWidth
        } else {
            param?.width = screenWidth - (screenWidth / 2.5f).toInt()
        }
        if (param is CoordinatorLayout.LayoutParams) {
            if (landscapeStyle == LEFT) {
                if (type == 1) paramCoordinator(param).gravity = Gravity.BOTTOM
                else paramCoordinator(param).gravity = Gravity.TOP
            } else if (landscapeStyle == RIGHT) {
                if (type == 1) paramCoordinator(param).gravity = Gravity.BOTTOM or Gravity.END
                else paramCoordinator(param).gravity = Gravity.TOP or Gravity.END
            } else {
                if (type == 1) paramCoordinator(param).gravity =
                    Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                else paramCoordinator(param).gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            }
        } else if (param is FrameLayout.LayoutParams) {
            if (landscapeStyle == LEFT) {
                if (type == 1) paramFrame(param).gravity = Gravity.BOTTOM
                else paramFrame(param).gravity = Gravity.TOP
            } else if (landscapeStyle == RIGHT) {
                if (type == 1) paramFrame(param).gravity = Gravity.BOTTOM or Gravity.END
                else paramFrame(param).gravity = Gravity.TOP or Gravity.END
            } else {
                if (type == 1) paramFrame(param).gravity =
                    Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                else paramFrame(param).gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            }
        } else if (param is RelativeLayout.LayoutParams) {
            if (landscapeStyle == LEFT) {
                if (type == 1) paramRelative(param).addRule(
                    RelativeLayout.ALIGN_PARENT_BOTTOM,
                    RelativeLayout.TRUE
                )
                else paramRelative(param).addRule(
                    RelativeLayout.ALIGN_PARENT_TOP,
                    RelativeLayout.TRUE
                )
            } else if (landscapeStyle == RIGHT) {
                if (type == 1) paramRelative(param).addRule(
                    RelativeLayout.ALIGN_PARENT_BOTTOM,
                    RelativeLayout.TRUE
                )
                else paramRelative(param).addRule(
                    RelativeLayout.ALIGN_PARENT_TOP,
                    RelativeLayout.TRUE
                )
                paramRelative(param).addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
            } else {
                if (type == 1) paramRelative(param).addRule(
                    RelativeLayout.ALIGN_PARENT_BOTTOM,
                    RelativeLayout.TRUE
                )
                else paramRelative(param).addRule(
                    RelativeLayout.ALIGN_PARENT_TOP,
                    RelativeLayout.TRUE
                )
                paramRelative(param).addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
            }
        }
        snackBar?.view?.layoutParams = param
        if (type == 0) snackBar?.view?.startAnimation(
            AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_quick_snack_bar_slide_bottom)
        )
    }

    fun heightRes(@DimenRes heightRes: Int) {
        val mHeight = getDimenInt(heightRes)
        if (mHeight > 0) {
            val param = binding.root.layoutParams
            param.height = mHeight
            binding.root.layoutParams = param
        }
    }

    fun height(height: Int) {
        if (height > 0) {
            val param = binding.root.layoutParams
            param.height = height
            binding.root.layoutParams = param
        }
    }

    fun anchorView(anchorView: View?) {
        if (anchorView != null) snackBar?.setAnchorView(anchorView)
    }

    fun duration(@Duration duration: Int) {
        durationSnackBar = when (duration) {
            LONG -> {
                BaseTransientBottomBar.LENGTH_LONG
            }

            INDEFINITE -> {
                BaseTransientBottomBar.LENGTH_INDEFINITE
            }

            else -> {
                BaseTransientBottomBar.LENGTH_SHORT
            }
        }
        if (snackBar != null) snackBar?.setDuration(durationSnackBar)
    }

    fun position(@Position position: Int) {
        this.position = position
    }

    fun fontFamily(@FontRes fontRes: Int) {
        try {
            val typeface =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) binding.root.context.resources.getFont(
                    fontRes
                )
                else ResourcesCompat.getFont(binding.root.context, fontRes)
           binding.textMessage.typeface = typeface
           binding.textAction.typeface = typeface
        } catch (e: Resources.NotFoundException) {
            log("Font resource not found")
        }
    }

    fun fontFamily(@FontRes fontRes: Int, @DimenRes fontSizeRes: Int) {
        fontFamily(fontRes)
        val size = getDimen(fontSizeRes)
        if (size > 0) {
           binding. textMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
           binding. textAction.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        }
    }

    fun fontFamily(@FontRes font: Int, fontSize: Float) {
        fontFamily(font)
        if (fontSize > 0) {
           binding. textMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
           binding. textAction.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
        }
    }

    private fun applyBackground() {
        snackBar?.view?.backgroundTintList = null
        var getDrawable = getDrawable(backgroundRes)
        if (getDrawable is BitmapDrawable) {
            applyBackgroundBitmap(
                getDrawable,
                cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight
            )
        } else if (getDrawable is VectorDrawable || getDrawable is VectorDrawableCompat) {
            val bitmap = Bitmap.createBitmap(
                getDrawable.intrinsicWidth, getDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            getDrawable.setBounds(0, 0, getDrawable.intrinsicWidth, getDrawable.intrinsicHeight)
            getDrawable.draw(canvas)
            val bitmapDrawable = BitmapDrawable(binding.root.context.resources, bitmap)
            applyBackgroundBitmap(
                bitmapDrawable, cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight
            )
        } else {
            getDrawable = getDrawables(R.drawable.bg_custom_quick_snack_bar)
            val drawable = getDrawable as GradientDrawable?
            if (drawable != null) {
                if (backgroundColor != 0) drawable.setColor(backgroundColor)
                val corners =
                    cornerRadii(cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight)
                drawable.cornerRadii = corners
                if (borderSize > 0 && borderColor != 0) drawable.setStroke(
                    borderSize.toInt(),
                    borderColor
                )
                setSnackBarBackground(drawable)
            }
        }
        val getDrawableButton = binding.textAction.background
        if (getDrawableButton != null) {
            if (getDrawableButton is RippleDrawable) {
                val getDrawableRipple = getDrawableButton.getDrawable(0)
                if (getDrawableRipple != null) {
                    if (getDrawableRipple is GradientDrawable) {
                        getDrawableRipple.cornerRadii =
                            cornerRadii(
                                cornerTopLeft,
                                cornerTopRight,
                                cornerBottomLeft,
                                cornerBottomRight
                            )
                        binding.textAction.background = getDrawableButton
                    }
                }
            }
        }
    }

    fun show() {
        if (snackBar?.isShown != true) {
            applyMargin()
            applyPosition()
            applyBackground()
            if (position == TOP) {
                showWithTopPosition()
            } else {
                snackBar?.show()
            }
        }
    }

    private fun showWithTopPosition() {
        if (parentView is CoordinatorLayout) {
            for (i in 0 until parentView.childCount) {
                val child = parentView.getChildAt(i)
                fab =
                    if (child is FloatingActionButton) child else if (child is ExtendedFloatingActionButton) child else null
                if (fab != null) {
                    val anim = AnimationUtils.loadAnimation(
                        binding.root.context, R.anim.anim_quick_snack_bar_hide_fab
                    )
                    anim.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation) {}
                        override fun onAnimationEnd(animation: Animation) {
                            fab?.visibility = View.INVISIBLE
                            snackBar?.show()
                        }

                        override fun onAnimationRepeat(animation: Animation) {}
                    })
                    fab?.startAnimation(anim)
                    break
                }
            }
            if (fab == null) snackBar?.show()
        } else {
            snackBar?.show()
        }
        if (durationSnackBar != BaseTransientBottomBar.LENGTH_INDEFINITE) {
            snackBar?.addCallback(object : Snackbar.Callback() {
                override fun onShown(sb: Snackbar) {
                    super.onShown(sb)
                    val duration =
                        if (durationSnackBar == BaseTransientBottomBar.LENGTH_SHORT) 2000 else 3250
                    handler = Handler(Looper.getMainLooper())
                    handler?.postDelayed({
                        if (snackBar?.isShown == true) {
                            snackBar?.view?.startAnimation(
                                AnimationUtils.loadAnimation(
                                    binding.root.context, R.anim.anim_quick_snack_bar_slide_top
                                )
                            )
                            snackBar?.dismiss()
                        }
                    }, duration.toLong())
                }

                override fun onDismissed(transientBottomBar: Snackbar, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    if (fab != null) {
                        fab?.visibility = View.VISIBLE
                        val anim = AnimationUtils.loadAnimation(
                            binding.root.context, R.anim.anim_quick_snack_bar_show_fab
                        )
                        fab?.startAnimation(anim)
                    }
                }
            })
        }
    }

    fun dismiss() {
        if (snackBar?.isShown == true) {
            if (position == TOP) {
                snackBar?.view?.startAnimation(
                    AnimationUtils.loadAnimation(
                        binding.root.context, R.anim.anim_quick_snack_bar_slide_top
                    )
                )
            }
            snackBar?.dismiss()
            if (handler != null) handler?.removeCallbacksAndMessages(null)
        }
    }

    private fun getDimenInt(id: Int): Int {
        var finalValue = 0
        try {
            finalValue = binding.root.context.resources.getDimensionPixelSize(id)
        } catch (e: Resources.NotFoundException) {
            log("Dimen not found")
        }
        return finalValue
    }

    private fun getDimen(id: Int): Float {
        var finalValue = 0f
        try {
            finalValue = binding.root.context.resources.getDimension(id)
        } catch (e: Resources.NotFoundException) {
            log("Dimen not found")
        }
        return finalValue
    }

    private fun getColor(id: Int): Int {
        var finalColor = 0
        try {
            finalColor = ContextCompat.getColor(binding.root.context, id)
        } catch (e: Resources.NotFoundException) {
            log("Color resource not found")
        }
        return finalColor
    }

    private fun parseColor(colorCode: String): Int {
        var color = 0
        try {
            color = Color.parseColor(colorCode)
        } catch (e: NumberFormatException) {
            Log.d("QuickSnackBar", "Parse color error")
        }
        return color
    }

    private fun getDrawable(@DrawableRes id: Int): Drawable? {
        var drawable = getDrawables(R.drawable.bg_custom_quick_snack_bar)
        if (id == 0 || id == -1) return null
        try {
            drawable = getDrawables(id)
        } catch (e: Resources.NotFoundException) {
            log("Drawable resource not found.")
        }
        return drawable
    }

    private fun getDrawables(id: Int): Drawable? {
        return binding.root.context?.resources?.let { ResourcesCompat.getDrawable(it, id, null) }
    }

    private fun setSnackBarBackground(drawable: Drawable?) {
        if (snackBar != null) snackBar?.view?.background = drawable
    }

    private fun setSnackBarElevation(elevation: Float) {
        if (snackBar != null) snackBar?.view?.elevation = elevation
    }

    private fun log(message: String?) {
        var mMessage = "Default message"
        if (message != null) mMessage = message
        Log.d("QuickSnackBar", mMessage)
    }

    private fun getString(id: Int): String {
        var message = "null"
        try {
            message = binding.root.context.resources.getString(id)
        } catch (e: Resources.NotFoundException) {
            log("String resource not found")
        }
        return message
    }

    private fun getRotation(context: Context): Int {
        var angle = 0
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val rotation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.display.rotation
        } else {
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.rotation
        }
        when (rotation) {
            Surface.ROTATION_90 -> angle = -90
            Surface.ROTATION_180 -> angle = 180
            Surface.ROTATION_270 -> angle = 90
            Surface.ROTATION_0 -> {}
        }
        return angle
    }

    private fun paramCoordinator(param: ViewGroup.LayoutParams): CoordinatorLayout.LayoutParams {
        return param as CoordinatorLayout.LayoutParams
    }

    private fun paramFrame(param: ViewGroup.LayoutParams): FrameLayout.LayoutParams {
        return param as FrameLayout.LayoutParams
    }

    private fun paramRelative(param: ViewGroup.LayoutParams): RelativeLayout.LayoutParams {
        return param as RelativeLayout.LayoutParams
    }

    private fun getWidthScreen(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    interface Callback {
        fun onActionClick(snackBar: Snacking?)
    }

    companion object {
        const val NORMAL: Int = 0
        const val BOLD: Int = 1
        const val ITALIC: Int = 2
        const val BOLD_ITALIC: Int = 3

        const val MATCH: Int = 0
        const val LEFT: Int = 1
        const val CENTER: Int = 2
        const val RIGHT: Int = 3

        const val TOP: Int = 0
        const val BOTTOM: Int = 1

        const val SORT: Int = 0
        const val LONG: Int = 1
        const val INDEFINITE: Int = 2
    }
}
