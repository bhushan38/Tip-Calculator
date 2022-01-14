package com.bwm.tippy

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var edBaseAmount: EditText
    private lateinit var skTip: SeekBar
    private lateinit var tvTipPercLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edBaseAmount = findViewById(R.id.edBaseAmount)
        skTip = findViewById(R.id.seekBarTip)
        tvTipPercLabel = findViewById(R.id.tvTipPerc)
        tvTipAmount = findViewById(R.id.tvTipValue)
        tvTotalAmount = findViewById(R.id.tvTotalValue)
        tvTipDescription = findViewById(R.id.tvTipDescription)
        skTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tvTipPercLabel.text = "$p1%"
                updateTipDescription(p1)
                computeTipTotal()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        edBaseAmount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                computeTipTotal()
            }
        })
    }

    private fun updateTipDescription(tipPercentage: Int) {
        val tipDescription: String
        when(tipPercentage){
            in 0..9-> tipDescription = "Poor"
            in 10..14-> tipDescription = "Acceptable"
            in 15..19-> tipDescription = "Good"
            in 20..24-> tipDescription = "Great"
            else -> tipDescription = "Amazing"
        }
        tvTipDescription.text = tipDescription
        val color = ArgbEvaluator().evaluate(tipPercentage.toFloat()/skTip.max,ContextCompat.getColor(this,R.color.red),ContextCompat.getColor(this,R.color.green)) as Int
        tvTipDescription.setTextColor(color)
    }

    private fun computeTipTotal() {
        if(edBaseAmount.text.isEmpty()){
           return
        }
        val baseAmount = edBaseAmount.text.toString().toDouble()
        val tipPerent = skTip.progress
        val tipAmount = baseAmount * tipPerent/100
        val totalamount = baseAmount+tipAmount
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(totalamount)
    }
}