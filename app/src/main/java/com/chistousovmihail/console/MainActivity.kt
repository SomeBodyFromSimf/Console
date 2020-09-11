package com.chistousovmihail.console

import android.R.id.edit
import android.R.id.mask
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etInput.addTextChangedListener(MyWatcher())
    }


}


class MyWatcher : TextWatcher {

    private val mask = "0000 0000 0000 0000"

    private var isRunning = false
    private var isRemoveSpacing = false
    private var selection: Int = 0
    override fun beforeTextChanged(
        charSequence: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
        if(listOf(4,9,14).contains(start) && count==1 && !isRunning) {
            isRemoveSpacing = true
            selection = start
        }
        //Log.d("OkHttp","$charSequence, $start, $count, $after")
    }

    override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
        //Log.d("OkHttp","$charSequence, $start, $before, $count")
    }

    override fun afterTextChanged(editable: Editable) {
        if (isRunning) {
            return
        }
        isRunning = true
        if(isRemoveSpacing) {
            editable.delete(selection-1,selection)
            isRemoveSpacing = false
        }
        var index = editable.length - 1
        while (index >= 0) {
            if (editable[index] == ' ') {
                editable.delete(index, index+1)
            }
            index--
        }
        index = 0
        while (index != editable.length) {
            when (index) {
                4, 9, 14 -> {
                    if (editable[index].isDigit())
                        editable.insert(index, " ")
                }
            }
            index++
        }

        if(mask.length < editable.length) {
            editable.delete(mask.length,editable.length)
        }
        Log.d("OkHttp", editable.toString())
//
//        val editableLength = editable.length
//        if (editableLength < mask.length) {
//            if (mask[editableLength] != '#') {
//                editable.append(mask[editableLength])
//            } else if (mask[editableLength - 1] != '#') {
//                editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
//            }
//        }
        isRunning = false
    }
}