package com.example.quizapp.question

class QuestionData(
    val questionText: String,
    var answeredPosition :Int = -1,
    val answerDataOptions : ArrayList<AnswerData>
)

class AnswerData(
    val answerText : String,
    val correctAns : Boolean = false
)