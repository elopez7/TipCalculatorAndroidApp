package com.example.tipcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var billAmount : BigDecimal
    private lateinit var tipRate : BigDecimal
    private lateinit var divisor : BigDecimal
    private lateinit var tip : BigDecimal
    private lateinit var totalAmount : BigDecimal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeMainLayout()
        setClickListeners()
    }

    private fun initializeMainLayout() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setClickListeners() {
        binding.buttonCalculateTip.setOnClickListener{takeUserInput()}
    }

    private fun takeUserInput() {
        processInput()
        hideKeyboard()
    }

    private fun processInput() {
        if(binding.editTextBillAmount.text.isNullOrBlank() ||
                binding.editTextTipRate.text.isNullOrBlank()) {
            setTextViews()
            return
        }
        calculateTotals()
        setTextViews(totalAmount, tip)
    }

    private fun calculateTotals() {
        billAmount = BigDecimal.valueOf(binding.editTextBillAmount.text.toString().toDouble())
        tipRate = BigDecimal.valueOf(binding.editTextTipRate.text.toString().toDouble())
        divisor = BigDecimal.valueOf(100)

        tip = billAmount * (tipRate/divisor)
        totalAmount = (billAmount + tip)
    }

    private fun setTextViews(totalAmount: BigDecimal = BigDecimal.valueOf(0), tipAmount: BigDecimal = BigDecimal.valueOf(0)) {
        binding.textViewTotalToPay.text = getString(R.string.string_final_amount, totalAmount)
        binding.textViewTotalTip.text = getString(R.string.string_tip_final_amount, tipAmount)
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.buttonCalculateTip.windowToken, 0)
    }

}