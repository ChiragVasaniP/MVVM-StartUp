package com.dr.rajkul.utils

import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import java.util.regex.Pattern

object Validator {
    const val specialCharacters = "-@%\\[\\}+'!/#$^?:;,\\(\"\\)~`.*=&\\{>\\]<_"
    const val PASSWORD_PATTERN =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$specialCharacters])(?=\\S+$).{8,}$"

    /* fun isValidPassword(password: String?): Boolean {
         if (password.isNullOrEmpty()) return false
         val matcher = Pattern.compile(PASSWORD_PATTERN).matcher(password)
         return matcher.matches()
     }*/

 /*   fun isValidPasswordNew(password: String?): Boolean {
        if (password.isNullOrEmpty()) return false
        return password.length >= 6
    }
*/
    fun isValidPassword(password: String?): Boolean {
        if (password.isNullOrEmpty()) return false
        val matcher = Pattern.compile(PASSWORD_PATTERN).matcher(password)
        return matcher.matches()
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        if (target == null) return false
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isValidPhone(target: CharSequence?): Boolean {
        if (target == null) return false
        return target.length in 8..14
    }

    fun isEmptyFieldValidate(strField: String): Boolean {
        var isEmptyField = false
        if (strField.isEmpty()) isEmptyField = true
        return isEmptyField
    }


    private val UUID_PATTERN: String =
        "[a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}_?.*"

    fun checkUUID(uuid: String): Boolean {
        val pattern = Pattern.compile(UUID_PATTERN)
        val matcher = pattern.matcher(uuid)
        if (matcher.matches()) {
            return true
        }
        return false
    }

    fun setError(edtView: AppCompatEditText, msg: String?) {
        edtView.error = msg
        edtView.requestFocus()
    }

    fun isPasswordSame(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}