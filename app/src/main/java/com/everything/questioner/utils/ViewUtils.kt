package com.everything.questioner.utils

import android.animation.*
import android.app.Activity
import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.provider.Settings
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import androidx.core.animation.addListener
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.everything.questioner.BuildConfig
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.math.roundToInt

fun View.gone() {
    this.visibility = View.GONE
}

fun View.show(show: Boolean) {
    if (show) visible() else gone()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun TextView.setNonEmptyText(s: String?) {
    if (s != null && s.isNotEmpty()) {
        visible()
        text = s
    } else gone()
}

fun View.pulseAnimate(count: Int = 3) {
    val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat("scaleX", 1.2f),
        PropertyValuesHolder.ofFloat("scaleY", 1.2f)
    )
    scaleDown.duration = 310

    scaleDown.repeatCount = count
    scaleDown.repeatMode = ObjectAnimator.REVERSE

    scaleDown.start()
}

fun View.setBgColorAnim(colorFrom: Int, colorTo: Int, duration: Long = 500): ValueAnimator {
    val view = this
    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    colorAnimation.duration = duration
    colorAnimation.addUpdateListener { animator -> view.setBackgroundColor(animator.animatedValue as Int) }
    colorAnimation.start()
    return colorAnimation
}

fun TextView.setCount(count: Int, duration: Long = 1000) {
    val tv = this
    val animator = ValueAnimator()
    animator.setObjectValues(0, count)
    animator.addUpdateListener { animation -> tv.text = "${animation.animatedValue}" }
    animator.duration = duration // here you set the duration of the anim
    animator.start()
}

// Animate change text size of TextView
fun TextView.animateTextSizeChange(
    endSize: Float,
    duration: Long = 300,
    interpolator: Interpolator = AccelerateDecelerateInterpolator()
) {
    val animator = ValueAnimator.ofFloat(this.textSize, endSize)
    animator.addUpdateListener { valueAnimator ->
        val animatedValue = valueAnimator.animatedValue as Float
        setTextSize(TypedValue.COMPLEX_UNIT_SP, animatedValue)
    }
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Fragment.closeSoftKeyboard() {
    val view = requireActivity().currentFocus
    if (view != null) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun View.goneWithFade(duration: Long = 300) {
    val view = this
    view.animate()
        .alpha(0f)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.gone()
            }
        })
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.visibleWithFade(duration: Long = 300) {
    val view = this
    view.alpha = 0f
    view.visible()
    view.animate()
        .alpha(1f)
        .setDuration(duration)
        .setListener(null)
}

fun View.hideKeyboard() {
    this.let { v ->
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}


fun Activity.showToast(text: String, debug: Boolean = false) {
    if (debug) {
        if (BuildConfig.DEBUG) Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        return
    }

    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

}

private fun logDebug(tag: String, message: String) {
    if (BuildConfig.DEBUG) Log.d(tag, message)
}

fun Any.logDebug(text: String) {
    if (BuildConfig.DEBUG) {
        logDebug(this.javaClass.name, text)
    }
}

fun Fragment.showToast(text: String) {
    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
}


fun View.slideDown() {
    // Prepare the View for the animation
    this.visibility = View.VISIBLE
    this.alpha = 0.0f

// Start the animation
    this.animate()
        .translationY(this.height.toFloat())
        .alpha(1.0f)
        .setListener(null)
}

fun View.slideUp() {
    val view = this
    view.animate()
        .translationY(0.0f)
        .alpha(0.0f)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.visibility = View.VISIBLE
            }
        })
}

fun EditText.queryListener(bindClear: View? = null, onQueryChanged: (String) -> Unit) {

    bindClear?.gone()
    bindClear?.setOnClickListener {
        this.setText("")
    }
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onQueryChanged(s.toString())
            if (s?.isNotEmpty() == true) bindClear?.visible() else bindClear?.gone()
        }

    })
}

fun View.setOnSingleClickListener(action: (v: View) -> Unit) {
    setOnClickListener { v ->
        isClickable = false
        action(v)
        postDelayed({ isClickable = true }, 700)
    }
}

fun FragmentActivity.emailIntent(
    email: String = "",
    subject: String = "",
    body: String = "Hi,"
) {
    startActivity(
        Intent(
            Intent.ACTION_SENDTO,
            Uri.fromParts("mailto", email, null)
        ).also { intent ->
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, body)
        })
}

fun ImageView.tint(color: Int) {
    setColorFilter(color)
}

/*fun Context.openAppInPlayStore() {
    val uri = Uri.parse("market://details?id=$packageName")
    val goToMarketIntent = Intent(Intent.ACTION_VIEW, uri)

    var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    flags = flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
    goToMarketIntent.addFlags(flags)

    try {
        startActivity(goToMarketIntent, null)
    } catch (e: ActivityNotFoundException) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
        )

        startActivity(intent, null)
    }
}*/

fun FragmentActivity.activeFragment(): Fragment? {
    var activeFragment: Fragment? = null
    for (fragment in supportFragmentManager.fragments) {
        if (fragment != null && fragment.isVisible) {
            activeFragment = fragment
        }
    }
    return activeFragment
}

fun View.changeBackgroundColorWithAnim(
    fromColor: Int,
    toColor: Int
) {
    val view = this
    val valueAnimator =
        ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
    valueAnimator.duration = 700
    valueAnimator.addUpdateListener { animation -> view.setBackgroundColor((animation.animatedValue as Int)) }
    valueAnimator.start()
}


fun Activity.goToAppSetting(requestCode: Int) {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivityForResult(intent, requestCode)
}

fun Context.hasPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED


fun View.showSnack(text: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, text, duration).show()
}

fun TextView.span(queryText: String) {
    try {
        val startPos = text.toString().lowercase(Locale.ROOT).indexOf(queryText)
        val endPos = startPos + queryText.length
        val spannable =
            Spannable.Factory.getInstance().newSpannable(text)
        spannable.setSpan(
            ForegroundColorSpan(Color.RED),
            startPos,
            endPos,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setText(spannable, TextView.BufferType.SPANNABLE)
    } catch (_: Exception) {
    }
}

fun Activity.checkPermissionUtil(
    permission: String,
    sharedPreferences: SharedPreferences,
    listener: (ask: Boolean, permanentlyDenied: Boolean) -> Unit
) {
    //CHECK THIS PERMISSION
    if (ActivityCompat.checkSelfPermission(
            this,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                permission
            )
        ) {
            //Ask Permission
            listener(true, false)
        } else if (!sharedPreferences.getBoolean(permission, false)
        ) {
            //Ask Permission First Time
            //and save Asked true in pref
            listener(true, false)
            sharedPreferences.edit().putBoolean(permission, true).apply()

        } else {
            //already asked and not rationale i.e. denies with do not ask
            //go to settings
            listener(true, true)
        }
    } else {
        listener(false, false)
    }
}

fun <T> List<T>.merge(list: List<T>): List<T> {
    return mutableListOf<T>().also {
        it.addAll(this)
        it.addAll(list)
    }
}

fun Activity.copyClipboard(label: String?, value: String?, showToast: Boolean = true) {
    val clipboard =
        getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, value)
    clipboard.setPrimaryClip(clip)

    if (showToast)
        Toast.makeText(this, "$label copied", Toast.LENGTH_SHORT).show()
}

fun View.changeWidth(endWidth: Int, duration: Long = 400, onComplete: (() -> Unit)? = null) {
    val anim = ValueAnimator.ofInt(this.measuredWidth, endWidth)
    anim.addUpdateListener { valueAnimator ->
        val `val` = valueAnimator.animatedValue as Int
        val layoutParams: ViewGroup.LayoutParams = layoutParams
        layoutParams.width = `val`
        setLayoutParams(layoutParams)
    }
    anim.duration = duration
    onComplete?.let {
        anim.addListener(onEnd = {
            onComplete()
        })
    }
    anim.start()
}

fun Float.toDp(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics
).roundToInt()

fun View.disableView(disable: Boolean, disableColor: Int = -1, enableColor: Int = -1) {
    if (disable) {
        isClickable = false
        if (disableColor == -1) {
            alpha = 0.5f
        } else {
            //setBackgroundResource(disableColor)
            backgroundTintList = ColorStateList.valueOf(disableColor)
        }
    } else {
        isClickable = true
        if (enableColor == -1) {
            alpha = 1.0f
        } else {
            //setBackgroundResource(enableColor)
            backgroundTintList = ColorStateList.valueOf(enableColor)
        }
    }
}

fun Activity.dpToPx(@Dimension(unit = DP) dp: Int): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics)
}

fun View.disableView(disable: Boolean) {
    isClickable = disable
    alpha = if (disable) 0.5f else 1.0f
}

fun EditText.trimText() = text.toString().trim()

fun EditText.clearText() = this.setText("")

fun Activity.startNewActivity(clazz: Class<*>) = startActivity(Intent(this, clazz).also {
    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
})

fun Fragment.startNewActivity(clazz: Class<*>) = activity!!.startNewActivity(clazz)

fun Boolean.toInt() = if (this) 1 else 0

fun Int.toBoolean() = this > 0

fun String.isEmail() = this.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"))

fun String.isMobileNumber() = this.matches(Regex("^[7-9][0-9]{9}\$"))

fun String.isFullName() = this.matches(Regex("^[\\\\p{L} .'-]+\$"))

fun EditText.setNonNullText(text: String?) {
    text?.let { this.setText(it) }
}

fun TextView.normal() {
    setTypeface(typeface, Typeface.NORMAL)
}

fun TextView.bold() {
    setTypeface(typeface, Typeface.BOLD)
}

fun TextView.italic() {
    setTypeface(typeface, Typeface.ITALIC)
}

fun TextView.boldItalic() {
    setTypeface(typeface, Typeface.BOLD_ITALIC)
}