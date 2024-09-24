package com.example.courseapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var textViewCourseNo: TextView
    private lateinit var textViewCourseName: TextView
    private lateinit var textViewCourseFees: TextView
    private lateinit var textViewTotalFees: TextView

    // buttons
    private lateinit var buttonCalculateTotalFees: Button
    private lateinit var buttonUpdateCourseDetails: Button
    private lateinit var buttonPrevious: Button
    private lateinit var buttonNext: Button

    // list
    private var courses = mutableListOf<Course>()
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        courses = mutableListOf(
            Course("MIS 101", "Intro. to Info. System", 140.0),
            Course("MIS 301", "System Analysis", 35.0),
            Course("MIS 441", "Database Management", 12.0),
            Course("CS 155", "Programming in C++", 90.0),
            Course("MIS 451", "Web-Based Systems", 30.0),
            Course("MIS 551", "Advanced Web", 30.0),
            Course("MIS 651", "Advanced Java", 30.0)
        )
        // link the files
        textViewCourseNo = findViewById(R.id.textview_course_no)
        textViewCourseName = findViewById(R.id.textview_course_name)
        textViewCourseFees = findViewById(R.id.textview_course_fees)
        textViewTotalFees = findViewById(R.id.textview_total_fees)

        buttonCalculateTotalFees = findViewById(R.id.button_calculate_total_fees)
        buttonUpdateCourseDetails = findViewById(R.id.button_update_course_details)

        buttonPrevious = findViewById(R.id.button_previous)
        buttonNext = findViewById(R.id.button_next)

        updateUI()

        buttonNext.setOnClickListener {
            showNext()
        }
        buttonPrevious.setOnClickListener {
            showPrevious()
        }

        buttonCalculateTotalFees.setOnClickListener {
            calculateTotalFees()
        }

        buttonUpdateCourseDetails.setOnClickListener {
            val intent = CourseUpdateActivity.newIntent(this@MainActivity, courses[index])
            intent.putExtra("course", courses[index])
            startActivityIntent.launch(intent)
        }
    }

    private fun showNext() {
        if (index < courses.size - 1) {
            index++
            updateUI()
        }
    }

    private fun showPrevious() {
        // check if index is greater than 0
        if (index > 0) {
            index--
            updateUI()
        }
    }

    private fun updateUI() {
        val course = courses[index]
        textViewCourseNo.text = course.courseNo
        textViewCourseName.text = course.courseName
        textViewCourseFees.text = course.courseFees.toString()
    }

    private fun calculateTotalFees() {
        var totalFees = 0.0
        for (course in courses) {
            totalFees += course.courseFees
        }
        textViewTotalFees.text = totalFees.toString()
    }

    // Acitivity result launcher.
    private val startActivityIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode != Activity.RESULT_OK) {
            return@registerForActivityResult
        } else {
            val courseUpdatedInfo = result.data?.let {
                CourseUpdateActivity.sentMessageCourseUpdateDetails(
                    it
                )
            }
            textViewCourseName.text =
                "${courseUpdatedInfo?.courseName}"
            textViewCourseNo.text = "${courseUpdatedInfo?.courseNo}"
            textViewCourseFees.text = "${courseUpdatedInfo?.courseFees}"

            Log.d("MainActivity", "courseUpdatedInfo: $courseUpdatedInfo")
            courses[index] = courseUpdatedInfo!!

//            Toast.makeText(
//                this@MainActivity,
//                "Updated course info is: ${allCourses[currentIndex].courseNo}. Updated Course Fee: ${allCourses[currentIndex].calculateCourseTotalFees()}",
//                Toast.LENGTH_SHORT
//            ).show()
        }
    }


}