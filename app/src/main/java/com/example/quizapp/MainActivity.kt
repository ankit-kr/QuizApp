package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.quizapp.common.Constants
import com.example.quizapp.common.Utility
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.question.QuestionPageActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.btnStartQuiz.setOnClickListener(this)
        initView()
    }

    private fun initView() {
        binding.tvTotalTime.text = getString(
            R.string.start_quiz_time_format,
            Utility.getTimeFormat(Constants.QUIZ_TOTAL_TIME)
        )
        binding.tvTotalQuestions.text =
            getString(R.string.total_question_format, Constants.TOTAL_QUESTION_SIZE.toString())
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_start_quiz) {
            val intent = Intent(this, QuestionPageActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }
    }
}
