package ru.jonik.counterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import ru.jonik.counterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)

        binding.btnIncrement.setOnClickListener { viewModel.increment() }
        binding.btnRandomColor.setOnClickListener { viewModel.setRandomColor() }
        binding.bntSwitchVisibility.setOnClickListener { viewModel.switchVisibility() }
        if (viewModel.state.value == null) {
            viewModel.initState(
                MainActivityViewModel.State(
                    counterValue = 0,
                    counterTextColor = ContextCompat.getColor(this, R.color.purple_700),
                    counterIsVisible = true
                )
            )
        }
        viewModel.state.observe(this, Observer {
            renderState(it)
        })
    }

    private fun renderState(state: MainActivityViewModel.State) = with(binding) {
        tvCounter.text = state.counterValue.toString()
        tvCounter.setTextColor(state.counterTextColor)
        tvCounter.visibility = if (state.counterIsVisible) View.VISIBLE else View.INVISIBLE
    }
}