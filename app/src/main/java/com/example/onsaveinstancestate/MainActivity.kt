package com.example.onsaveinstancestate

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.onsaveinstancestate.databinding.ActivityMainBinding
import kotlin.properties.Delegates.notNull
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private var counterValue by notNull<Int>()
    private var counterTextColor by notNull<Int>()
    private var counterIsVisible by notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.bIncrement.setOnClickListener { increment() }
        binding.bRandom.setOnClickListener { setRandomColor() }
        binding.bSwitch.setOnClickListener { switchVisibility() }

        if (savedInstanceState == null) {
            counterValue = 0
            counterTextColor = ContextCompat.getColor(this, R.color.teal_200)
            counterIsVisible = true
        } else {
            counterValue = savedInstanceState.getInt(KEY_COUNTER)
            counterTextColor = savedInstanceState.getInt(KEY_COLOR)
            counterIsVisible = savedInstanceState.getBoolean(KEY_IS_VISIBLE)
        }
        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_COUNTER, counterValue)
        outState.putInt(KEY_COLOR, counterTextColor)
        outState.putBoolean(KEY_IS_VISIBLE, counterIsVisible)
    }

    private fun increment() {
        counterValue++
        renderState()
    }

    private fun setRandomColor() {
        counterTextColor = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
        renderState()
    }

    private fun switchVisibility() {
        counterIsVisible = !counterIsVisible
        renderState()
    }

    private fun renderState() = with(binding) {
        textView.setText(counterValue.toString())
        textView.setTextColor(counterTextColor)
        textView.visibility = if (counterIsVisible) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        @JvmStatic private val KEY_COUNTER = "COUNTER"
        @JvmStatic private val KEY_COLOR = "COLOR"
        @JvmStatic private val KEY_IS_VISIBLE = "IS VISIBLE"
    }
}