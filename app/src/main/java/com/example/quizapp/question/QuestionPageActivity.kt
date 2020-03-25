package com.example.quizapp.question

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProviders
import com.example.quizapp.R
import com.example.quizapp.common.Constants
import com.example.quizapp.common.Constants.QUIZ_TIME_INTERVAL
import com.example.quizapp.common.Constants.QUIZ_TOTAL_TIME
import com.example.quizapp.common.Utility
import com.example.quizapp.databinding.ActivityQuestionPageBinding
import com.example.quizapp.result.ResultActivity
import java.util.concurrent.TimeUnit

class QuestionPageActivity : AppCompatActivity(), View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {

    private lateinit var viewModel: QuestionPageViewModel

    private lateinit var binding : ActivityQuestionPageBinding
    private var countDownTimer : CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_question_page)
        viewModel = ViewModelProviders.of(this)[QuestionPageViewModel::class.java]
        binding.ivNext.setOnClickListener(this)
        binding.ivPrevious.setOnClickListener(this)
        setupObserver()
        initCountdownTimer()
        viewModel.setInitialQuestion()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.question_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_finish ->{
                finishQuiz()
                return true
            }
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun initCountdownTimer() {
        countDownTimer = object:CountDownTimer(QUIZ_TOTAL_TIME,QUIZ_TIME_INTERVAL){
            override fun onFinish() {
                finishQuiz()
            }

            override fun onTick(millisUntilFinished: Long) {
                runOnUiThread {
                    viewModel.totalTimeTaken = millisUntilFinished
                    binding.tvTimer.text =  Utility.getTimeFormat(millisUntilFinished)
                }
            }
        }
        countDownTimer?.start()
    }



    private fun setupObserver() {
        viewModel.currentQuestion.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            @SuppressLint("SetTextI18n")
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val questionData : QuestionData? = viewModel.currentQuestion.get()
                questionData?.let {question ->
                    binding.tvQuestion.text = question.questionText
                    if(question.answerDataOptions.isNotEmpty()){
                        binding.radioBtnOption1.text = question.answerDataOptions[0].answerText
                        binding.radioBtnOption2.text = question.answerDataOptions[1].answerText
                        binding.radioBtnOption3.text = question.answerDataOptions[2].answerText
                        binding.radioBtnOption4.text = question.answerDataOptions[3].answerText
                    }
                }

                binding.radioGroupOptions.clearCheck()
                binding.radioGroupOptions.setOnCheckedChangeListener(this@QuestionPageActivity)
                binding.tvQuestionCounter.text = "${viewModel.currentPosition+1}/${viewModel.questionsDataList.size}"
            }
        })
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.iv_previous ->{
                viewModel.handleOnPreviousClick()
            }
            R.id.iv_next ->{
                if(!viewModel.handleOnNextClick()){
                    //no more question left.
                    finishQuiz()
                }
            }
        }
    }

    private fun finishQuiz() {
        val builder : AlertDialog.Builder = AlertDialog.Builder(this).setTitle(R.string.alert)
            .setMessage(R.string.finsh_dialog_message)
        builder.setPositiveButton(R.string.ok
        ) { dialog, _ ->
            dialog.dismiss()
            countDownTimer?.cancel()
            val totalQuestion = viewModel.questionsDataList.size
            val correctAnswer = viewModel.calculateCorrectAnswer()
            val totalTimeTaken = viewModel.totalTimeTaken
            val intent = Intent(this,ResultActivity::class.java)
            intent.putExtra(Constants.TOTAL_QUESTION, totalQuestion)
            intent.putExtra(Constants.CORRECT_ANS, correctAnswer)
            intent.putExtra(Constants.TOTAL_TIME_TAKEN, totalTimeTaken)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton(R.string.cancel
        ) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        val questionData = viewModel.currentQuestion.get()
        questionData?.let {question ->
            when (checkedId) {
                R.id.radio_btn_option1 -> {
                    question.answeredPosition = 0
                }
                R.id.radio_btn_option2 -> {
                    question.answeredPosition = 1
                }
                R.id.radio_btn_option3 -> {
                    question.answeredPosition = 2
                }
                R.id.radio_btn_option4 -> {
                    question.answeredPosition = 3
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
