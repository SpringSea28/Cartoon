package com.buyi.cartoon.main.utils

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

object TextColorExpandTools {

    fun setPrompt(
        textView: TextView, text: String, key: String, colorId: Int,
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
        textView: TextView, text: String, colorId: Int, vararg keys: String,
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


    fun setPromptWidthListener(
        textView: TextView, text: String, colorId: Int,
        key1: String, listener1: View.OnClickListener,
        key2: String, listener2: View.OnClickListener,
    ) {
        val spanned = SpannableStringBuilder(text)
        val start1: Int =
            text.indexOf(key1)
        if(start1 <0 ){
            return
        }

        spanned.setSpan(
            Clickable(listener1,ContextCompat.getColor(
                textView.context,
                colorId
            )),
            start1,
            start1 + key1.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spanned.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    textView.context,
                    colorId
                )
            ),
            start1,
            start1 + key1.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val start2: Int =
            text.indexOf(key2)
        if(start2 <0 ){
            return
        }

        spanned.setSpan(
            Clickable(listener2,ContextCompat.getColor(
                textView.context,
                colorId
            )),
            start2,
            start2 + key2.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spanned.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    textView.context,
                    colorId
                )
            ),
            start2,
            start2 + key2.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spanned
        textView.movementMethod = LinkMovementMethod.getInstance()
        avoidHintColor(textView)
    }


    private fun avoidHintColor(view: View) {
        if (view is TextView) view.highlightColor =
            view.context.getResources()
                .getColor(android.R.color.transparent)
    }


    //    private View.OnClickListener clickListenerPrivacy = (v) -> PrivacyAgreementActivity.startActivity(mContext,
    //            mContext.getString(R.string.privacy_policy), mContext.getString(R.string.url_privacy));
    internal class Clickable(private val mListener: View.OnClickListener?,val color:Int) :
        ClickableSpan() {
        override fun onClick(v: View) {
            mListener?.onClick(v)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = this.color
        }
    }
}