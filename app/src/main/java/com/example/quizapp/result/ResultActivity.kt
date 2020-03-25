package com.example.quizapp.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.quizapp.MainActivity
import com.example.quizapp.R
import com.example.quizapp.common.Constants
import com.example.quizapp.common.Utility
import com.example.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_result)
        binding.btnRetry.setOnClickListener(this)
        initView()
    }

    private fun initView() {
        val totalQuestionCount = intent.getIntExtra(Constants.TOTAL_QUESTION,0)
        val correctAnsCount = intent.getIntExtra(Constants.CORRECT_ANS,0)
        val timeTakenMilli = intent.getLongExtra(Constants.TOTAL_TIME_TAKEN,0)
        binding.tvTotalQuestion.text = getString(R.string.total_question_format, totalQuestionCount.toString())
        binding.tvCorrectAnswer.text = getString(R.string.total_correct_answer_format,correctAnsCount.toString())
        binding.tvTimeTaken.text = getString(R.string.total_time_taken,Utility.getTimeFormat(Constants.QUIZ_TOTAL_TIME - timeTakenMilli).toString())
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_retry -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}
