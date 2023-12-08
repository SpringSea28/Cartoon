package com.buyi.cartoon.main.utils

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

object TextColorExpandTools {

    fun setPrompt(
        textView: TextView, text: String, key:String, colorId: Int
    ) {
        val spanned = SpannableStringBuilder(text)
        val start: Int =
            text.indexOf(key)
        if(start <0 ){
            return
        }

        spanned.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    textView.context,
                    colorId
                )
            ),
            start,
            start + key.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spanned
        textView.movementMethod = LinkMovementMethod.getInstance()
        avoidHintColor(textView)
    }

    fun setPrompt(
        textView: TextView, text: String,  colorId: Int,vararg keys:String
    ) {
        if(keys.isEmpty()){
            return
        }
        val spanned = SpannableStringBuilder(text)
        for (key in keys ){
            val start: Int =
                text.indexOf(key)
            if(start <0 ){
                break
            }

            spanned.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        textView.context,
                        colorId
                    )
                ),
                start,
                start + key.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }


        textView.text = spanned
        textView.movementMethod = LinkMovementMethod.getInstance()
        avoidHintColor(textView)
    }


    private fun avoidHintColor(view: View) {
        if (view is TextView) view.highlightColor =
            view.context.getResources()
                .getColor(android.R.color.transparent)
    }
}