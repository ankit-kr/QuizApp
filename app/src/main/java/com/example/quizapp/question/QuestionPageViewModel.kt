package com.example.quizapp.question

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class QuestionPageViewModel : ViewModel(){


    var totalTimeTaken: Long = 0
    val questionsDataList = ArrayList<QuestionData>()
    val currentQuestion = ObservableField<QuestionData>()
    var currentPosition:Int = 0

    init {
        questionsDataList.apply {
            add(
                QuestionData(
                questionText = "Who is cricketer out of below celebrity ?",
                answeredPosition = -1,
                    answerDataOptions = arrayListOf(
                        AnswerData(
                        "APJ Abdul Kalam",
                        false
                        ),
                        AnswerData(
                        "Salman Khan",
                        false
                        ),
                        AnswerData(
                        "Sachin",
                        true
                        ),
                        AnswerData(
                            "None of them",
                            false
                        )
                    )
                )
            )
            add(
                QuestionData(
                    questionText = "What is corona virus disease name?",
                    answeredPosition = -1,
                    answerDataOptions = arrayListOf(
                        AnswerData(
                            "CAVD-20",
                            false
                        ),
                        AnswerData(
                            "COVID-19",
                            true
                        ),
                        AnswerData(
                            "Corona-42",
                            false
                        ),
                        AnswerData(
                            "None of them",
                            false
                        )
                    )
                )
            )

        }
    }

    fun setInitialQuestion(){
        currentPosition = 0
        currentQuestion.set(questionsDataList[currentPosition])
    }

    fun handleOnPreviousClick() {
        if(currentPosition > 0){
            currentPosition--
            currentQuestion.set(questionsDataList[currentPosition])
        }
    }
    fun handleOnNextClick() : Boolean{
        if(currentPosition < questionsDataList.size-1){
            currentPosition++
            currentQuestion.set(questionsDataList[currentPosition])
            return true
        }
        return false
    }

    fun calculateCorrectAnswer(): Int {
        var correctAnswers = 0
        for(question : QuestionData in questionsDataList){
            if(question.answeredPosition != -1 && question.answerDataOptions[question.answeredPosition].correctAns){
                correctAnswers++
            }
        }
        return correctAnswers
    }
}