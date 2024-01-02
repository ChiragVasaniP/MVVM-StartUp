package com.dr.rajkul.base

import android.content.Context
import android.content.Intent
import com.dr.rajkul.view.activities.authentication.LoginActivity
import com.dr.rajkul.view.activities.MainActivity
import com.dr.rajkul.view.activities.authentication.RegisterActivity
import com.dr.rajkul.view.activities.StartUpActivity
import com.dr.rajkul.view.activities.authentication.BasicInfoActivity
import com.dr.rajkul.view.activities.authentication.ChooseDynastyActivity
import com.dr.rajkul.view.activities.authentication.ChooseUsernameActivity
import com.dr.rajkul.view.activities.authentication.SelectProfileActivity
import com.dr.rajkul.view.activities.authentication.VerifyPhoneActivity
import com.dr.rajkul.view.activities.chat.ChattingViewActivity

object ActivityNavController {

    fun openChatViewActivity(mContext: Context) {
        mContext.startActivity(Intent(mContext, ChattingViewActivity::class.java))
    }

    fun openMainActivity(mContext: Context) =  mContext.startActivity(Intent(mContext, MainActivity::class.java))

    fun openLoginActivity(mContext: Context) =  mContext.startActivity(Intent(mContext, LoginActivity::class.java))

    fun openRegisterActivity(mContext: Context) =  mContext.startActivity(Intent(mContext, RegisterActivity::class.java))

    fun openStartUpActivity(mContext: Context) =  mContext.startActivity(Intent(mContext, StartUpActivity::class.java))

    fun openPhoneVerificationActivity(mContext: Context) =  mContext.startActivity(Intent(mContext, VerifyPhoneActivity::class.java))

    fun openChooseUsernameActivity(mContext: Context) =  mContext.startActivity(Intent(mContext, ChooseUsernameActivity::class.java))

    fun openBasicInfoActivity(mContext: Context) =  mContext.startActivity(Intent(mContext, BasicInfoActivity::class.java))

    fun openChooseDynastyActivity(mContext: Context) =  mContext.startActivity(Intent(mContext, ChooseDynastyActivity::class.java))

    fun openSelectProfileActivity(mContext: Context) =  mContext.startActivity(Intent(mContext, SelectProfileActivity::class.java))


}