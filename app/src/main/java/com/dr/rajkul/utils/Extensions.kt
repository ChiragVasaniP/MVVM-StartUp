package com.dr.rajkul.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.*
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.AnimRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.dr.rajkul.R
import com.fondesa.kpermissions.coroutines.sendSuspend
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.isDenied
import com.google.android.material.button.MaterialButton
import java.lang.reflect.Field
import java.text.DecimalFormat
import java.util.*

fun Any.toHashMap(): HashMap<String, Any?> {
    val hashMap = HashMap<String, Any?>()
    val fields: Array<Field> = this.javaClass.declaredFields

    for (field in fields) {
        field.isAccessible = true
        hashMap[field.name] = field.get(this)
    }

    return hashMap
}

inline fun EditText.afterTextChanged(crossinline listener: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            listener(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}

inline fun EditText.onTextChanged(crossinline listener: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener(s, start, before, count)
        }
    })
}

/**
 * Enable/Disable EditText to editable
 * */
fun EditText.setEditable(enable: Boolean) {
    isFocusable = enable
    isFocusableInTouchMode = enable
    isClickable = enable
    isCursorVisible = enable
}

/*
* Make EditText Scrollable inside scrollview
* */
@SuppressLint("ClickableViewAccessibility")
fun EditText.makeScrollableInScrollView() {
    setOnTouchListener(View.OnTouchListener { v, event ->
        if (hasFocus()) {
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_SCROLL -> {
                    v.parent.requestDisallowInterceptTouchEvent(false)
                    return@OnTouchListener true
                }
            }
        }
        false
    })
}


/*
* Execute block if OS version is greater or equal Lolipop(21)
* */
inline fun lollipopAndAbove(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        block()
    }
}

/*
* Execute block if OS version is greater than or equal Naugat(24)
* */
inline fun nougatAndAbove(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        block()
    }
}

/**
 * Check internet connection.
 * */
//inline fun Context.withNetwork(block: () -> Unit, blockError: () -> Unit) {
//    val connectivityManager = this
//        .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
//    connectivityManager?.let { it ->
//        val netInfo = it.activeNetworkInfo
//        netInfo?.let {
//            if (netInfo.isConnected)
//                block()
//            else
//                blockError()
//        }
//    }
//}

/*
* Execute block into try...catch
* */
inline fun <T> justTry(tryBlock: () -> T) = try {
    tryBlock()
} catch (e: Exception) {
    e.printStackTrace()
}

// Start new Activity functions

/*
* Start Activity from Activity
* */
inline fun <reified T : Any> Context.launchActivity(
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent)
}

/*
* Start Activity from Activity
* */
inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    if (requestCode == -1) startActivity(intent)
    else startActivityForResult(intent, requestCode)
}

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    extras: Bundle.() -> Unit = {},
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    intent.putExtras(Bundle().apply(extras))
    if (requestCode == -1) startActivity(intent)
    else startActivityForResult(intent, requestCode)
}


inline fun <reified T : Any> Fragment.launchActivity(
    requestCode: Int = -1,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this.requireContext())
    intent.init()
    if (requestCode == -1)
        startActivity(intent)
    else
        startActivityForResult(intent, requestCode)

}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

fun Context.openPdfFromUrl(pdfUrl: String?) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl))
    startActivity(browserIntent)
}

fun Fragment.openPdfFromUrl(pdfUrl: String?) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl))
    startActivity(browserIntent)
}

fun Context.openCall(call: String?) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$call")
    startActivity(intent)
}

fun Fragment.openCall(call: String?) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$call")
    startActivity(intent)
}

fun Context.openMap(latitude: Double?, longitude: Double?) {
    try {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude('')")
        )
        startActivity(intent)
    } catch (ane: ActivityNotFoundException) {
        Toast.makeText(this, "Please Install Google Maps ", Toast.LENGTH_LONG).show()
    } catch (ex: java.lang.Exception) {
        ex.message
    }
}
//
//fun Context.openMapNavigation(startLatLng: LatLng, endLatLng: LatLng) {
//    try {
//        val uri: String = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", startLatLng.latitude, startLatLng.longitude, endLatLng.latitude, endLatLng.longitude)
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//        intent.setPackage("com.google.android.apps.maps")
//        startActivity(intent)
//    } catch (ane: ActivityNotFoundException) {
//        Toast.makeText(this, "Please Install Google Maps ", Toast.LENGTH_LONG).show()
//    } catch (ex: java.lang.Exception) {
//        ex.message
//    }
//}

fun Fragment.openMap(latitude: Double?, longitude: Double?) {
    try {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude('')")
        )
        startActivity(intent)
    } catch (ane: ActivityNotFoundException) {
        Toast.makeText(context, "Please Install Google Maps ", Toast.LENGTH_LONG).show()
    } catch (ex: java.lang.Exception) {
        ex.message
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

/**
 * Return simple class name
 * */
fun Any.getClassName(): String {
    return this::class.java.simpleName
}


fun Intent.getInt(key: String, defaultValue: Int = 0): Int {
    return extras?.getInt(key, defaultValue) ?: defaultValue
}

fun Intent.getString(key: String, defaultValue: String = ""): String {
    return extras?.getString(key, defaultValue) ?: defaultValue
}

/*
* Return activity main content view
* */
/*val Activity.contentView: View?
    get() = findViewById<ViewGroup>(R.id.content)?.getChildAt(0)*/


/**
 * Hide/Show view with scale animation
 * */
/*fun View.setVisibilityWithScaleAnim(visibility: Int) {
    this.clearAnimation()
    this.visibility = View.VISIBLE
    val scale = if (visibility == View.GONE)
        0f
    else
        1f

    val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat("scaleX", scale),
        PropertyValuesHolder.ofFloat("scaleY", scale)
    )
    scaleDown.duration = 300
    scaleDown.interpolator = DecelerateInterpolator()
    scaleDown.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            this@setVisibilityWithScaleAnim.visibility = visibility
        }
    })
    scaleDown.start()
}*/

fun View.setAnimationWithVisibility(@AnimRes animationRes: Int, visibility: Int) {
    setVisibility(visibility)
    clearAnimation()
    val viewAnim = AnimationUtils.loadAnimation(context, animationRes)
    animation = viewAnim
    viewAnim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {
            setVisibility(visibility)
        }

        override fun onAnimationStart(animation: Animation?) {
            setVisibility(View.VISIBLE)
        }
    })
    // viewAnim.start()
}

fun Context.getAppVersionName(): String {
    return packageManager.getPackageInfo(packageName, 0).versionName
}

fun Context.showToast(
    message: String?,
    duration: Int = Toast.LENGTH_SHORT,
    gravity: Int = Gravity.CENTER
) {
    if (!message.isNullOrEmpty())
        Toast.makeText(this, message, duration).run {
            setGravity(gravity, 0, 0)
            show()
        }
}

/*fun setSpannableColor(){
    var word: SpannableString =  SpannableString("Your message");

    word.setSpan(new ForegroundColorSpan(Color.BLUE), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    textView.setText(word);
    Spannable wordTwo = new SpannableString("Your new message");

    wordTwo.setSpan(new ForegroundColorSpan(Color.RED), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    textView.append(wordTwo);
}*/

fun SpannableString.setClickableSpan(
    start: Int,
    end: Int,
    @ColorInt color: Int,
    block: (view: View?) -> Unit
) {
    setSpan(object : ClickableSpan() {
        override fun onClick(view: View) {
            block(view)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false // set to false to remove underline
        }

    }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}


/*
* Set enabled/disable
* */
fun View.setEnabledWithAlpha(enabled: Boolean, disabledAlpha: Float = 0.5f) {
    isEnabled = enabled
    alpha = if (isEnabled) 1f else disabledAlpha
}


fun String?.nullSafe(defaultValue: String = ""): String {
    return this ?: defaultValue
}

fun Int?.nullSafe(defaultValue: Int = 0): Int {
    return this ?: defaultValue
}

fun Float?.nullSafe(defaultValue: Float = 0f): Float {
    return this ?: defaultValue
}

fun Long?.nullSafe(defaultValue: Long = 0L): Long {
    return this ?: defaultValue
}

fun Double?.nullSafe(defaultValue: Double = 0.0): Double {
    return this ?: defaultValue
}

fun Boolean?.nullSafe(defaultValue: Boolean = false): Boolean {
    return this ?: defaultValue
}

fun <T> List<T>?.nullSafe(): List<T> {
    return this ?: ArrayList()
}


fun String?.toLongOrDefaultValue(defaultValue: Long = 0L): Long {
    return if (isNullOrEmpty()) {
        defaultValue
    } else {
        try {
            this?.toLong().nullSafe()
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }
}


@SuppressWarnings("deprecation")
fun String?.fromHtml(): Spanned {
    if (this == null)
        return SpannableString("")
    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
        // we are using this flag to give a consistent behaviour
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        return Html.fromHtml(this)
    }
}

/**
 * Return ActionBar height
 * */
/*fun Activity.getActionBarHeight(): Int {
    val tv = TypedValue()
    return if (theme.resolveAttribute(R.attr.actionBarSize, tv, true))
        TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    else 0
}*/

fun View.measureWidthHeight(onCompleteMeasure: (width: Int, height: Int) -> Unit) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            onCompleteMeasure.invoke(measuredWidth, measuredHeight)
            return true
        }
    })
}

fun getDummyList(length: Int): List<String> {
    val list: MutableList<String> =
        ArrayList()
    for (i in 0 until length) {
        list.add("test $i")
    }
    return list
}

fun getListHour(length: Int): List<String> {
    val list: MutableList<String> =
        ArrayList()
    for (i in 1 until length) {
        list.add("$i Hour")
    }
    return list
}

fun Drawable.setTintColor(@ColorInt colorTint: Int): Drawable {
    colorFilter = PorterDuffColorFilter(colorTint, PorterDuff.Mode.SRC_ATOP)
    return this
}

fun SearchView.setHintColor(@ColorInt hintColor: Int) {
    (findViewById<EditText>(androidx.appcompat.R.id.search_src_text)).setHintTextColor(hintColor)
}

/**
 * Convert string to int, If string is empty or null return default value
 * */
fun String?.toIntOrDefaultValue(defaultValue: Int = 0): Int {
    return if (isNullOrEmpty()) {
        defaultValue
    } else {
        try {
            this.toInt().nullSafe()
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }
}

/**
 * Convert string to double, If string is empty or null return default value
 * */
fun String?.toDoubleOrDefaultValue(defaultValue: Double = 0.0): Double {
    return if (isNullOrEmpty()) {
        defaultValue
    } else {
        try {
            this.toDouble().nullSafe()
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }
}

/**
 * Return Tag of view as string
 * */
fun View.getStringTag(): String = if (tag == null) "" else tag.toString()

fun RadioGroup.getCheckedButtonText(): String {
    return if (checkedRadioButtonId != -1)
        findViewById<RadioButton>(checkedRadioButtonId).text.toString()
    else
        ""
}

/**
 * Return trimmed text of EditText
 * */
fun EditText.getTrimText(): String = text.toString().trim()

fun TextView.getTrimText(): String = text.toString().trim()



fun Location.getPostalCode(context: Context): String {
    var postalCode = "000000"
    try {
        val geocoder: Geocoder? = Geocoder(context)
        if (geocoder != null) {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                for (element in addresses) {
                    if (element.postalCode != null) {
                        postalCode = element.postalCode
                        break
                    }
                }
                return postalCode
            }
        }
    } catch (e: Exception) {
        return postalCode
    }
    return postalCode
}

fun Context.openPlayStore() {
    val appPackageName = Const.appId
    try {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackageName")
            )
        )
    } catch (e: Exception) {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
            )
        )
    }
}

fun View.getLocationOnScreen(): Point {
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    return Point(location[0], location[1])
}

//@SuppressLint("ClickableViewAccessibility", "UseCompatTextViewDrawableApis")
//fun EditText.setPasswordVisibilityIcon() {
//    this.setOnTouchListener { v, event ->
//        if (event.action == MotionEvent.ACTION_UP) {
//            val drawableEnd = this.compoundDrawables[2]
//            if (event.rawX >= this.right - drawableEnd.bounds.width() * 2) {
//                // Toggle password visibility
//                val inputType =
//                    if (this.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
//                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//                    } else {
//                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//                    }
//                this.inputType = inputType
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    this.compoundDrawableTintList =
//                        ColorStateList.valueOf(Color.parseColor("#05463D"))
//                }
//                this.setSelection(this.text?.length ?: 0)
//                // Update eye icon drawable
//                val drawableResId =
//                    if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
//                        R.drawable.ic_eye
//                    } else {
//                        R.drawable.ic_eye_close
//                    }
//                val drawable = ContextCompat.getDrawable(context, drawableResId)
//                this.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
//                return@setOnTouchListener true
//            }
//        }
//        return@setOnTouchListener false
//    }
//}

fun Float.formatDecimal(): String? {
    val dec = DecimalFormat("#,###.00")
    return dec.format(this)
}

fun String?.showPrice(): String {
    val amount = this?.toDouble() ?: 0.0f
    return String.format("$%.2f", amount)
}

fun String?.showQuantity(): String {
    val amount = this?.toDouble() ?: 0f
    return String.format("%.2f", amount)
}

fun String?.showPricePerKg(): String {
    return this?.replace("per", "/") ?: ""
}

fun View.hideKeyboard() {
    val inputMethodManager =
        this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
            ?: return
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Activity.hideKeyboard() {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager? ?: return
    inputMethodManager.hideSoftInputFromWindow(this.window.decorView.windowToken, 0)
}


fun RecyclerView.onScrolled(onScroll: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            onScroll()
        }
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }
    })
}


fun Date.getTimeAgo(): String {
    val SECOND_MILLIS = 1000
    val MINUTE_MILLIS = 60 * SECOND_MILLIS
    val HOUR_MILLIS = 60 * MINUTE_MILLIS
    val DAY_MILLIS = 24 * HOUR_MILLIS

    val now = Date().time
    if (time > now || time <= 0) {
        return "in the future"
    }

    val diff = now - time
    return when {
        diff < MINUTE_MILLIS -> "moments ago"
        diff < 2 * MINUTE_MILLIS -> "a minute ago"
        diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
        diff < 2 * HOUR_MILLIS -> "an hour ago"
        diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} hours ago"
        diff < 48 * HOUR_MILLIS -> "yesterday"
        else -> "${diff / DAY_MILLIS} days ago"
    }
}

fun String.colorString(colorFullString: String, colorId: Int): SpannableString {
    val spannableString = SpannableString(this)
    val fullText = spannableString.toString()
    val firstMatchingIndex = fullText.indexOf(colorFullString)
    val lastMatchingIndex = firstMatchingIndex + colorFullString.length
    val color = ForegroundColorSpan(colorId)
    val text = spannableString.setSpan(
        color,
        firstMatchingIndex,
        lastMatchingIndex,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString
}

//fun MaterialButton.showLoading() {
//    showProgress {
//        progressColor = Color.WHITE
//        gravity = DrawableButton.GRAVITY_CENTER
//    }
//    icon = null
//    isEnabled = false
//}
//
//fun MaterialButton.hideLoading(@DrawableRes icon: Int? = null, newText: String = "") {
//    hideProgress(newText)
//    this.icon = if(icon == null) null else ContextCompat.getDrawable(context, icon)
//    isEnabled = true
//}

suspend fun FragmentActivity.mCheckPermissions(vararg permissions: String): Boolean {
    if (permissions.isEmpty()) return false
    val result = permissionsBuilder(permissions.first(), *permissions).build().sendSuspend()
    result.forEach { if (it.isDenied()) return false }
    return true
}

fun Double.currencyFormat(): String {
    return String.format("%.3f", this) + "₹"
}