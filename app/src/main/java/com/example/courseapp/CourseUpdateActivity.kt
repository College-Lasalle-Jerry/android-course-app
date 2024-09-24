package com.example.courseapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text

class CourseUpdateActivity : AppCompatActivity() {
    private lateinit var editTextCourseNo: TextInputEditText
    private lateinit var editTextCourseName: TextInputEditText
    private lateinit var editTextCourseFees: TextInputEditText

    private lateinit var buttonUpdateCourseDetails: Button

    private lateinit var course: Course

    companion object { // static
        fun newIntent(context: Context, course: Course): Intent {
            val intent = Intent(context, CourseUpdateActivity::class.java)
            intent.putExtra("course", course)
            return intent
        } // companion object

        fun sentMessageCourseUpdateDetails(resultIntent: Intent): Course {
            // create an empty course
            val courseUpdatedInfo = resultIntent.getParcelableExtra<Course>("course")!!
            return courseUpdatedInfo
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_course_update)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        course = intent.getParcelableExtra("course", Course::class.java) as Course

        // link the files

        editTextCourseNo = findViewById(R.id.edit_text_course_no)
        editTextCourseName = findViewById(R.id.edit_text_course_name)
        editTextCourseFees = findViewById(R.id.edit_text_course_fees)

        buttonUpdateCourseDetails = findViewById(R.id.button_update_course_details)

        editTextCourseNo.setText(course.courseNo)
        editTextCourseName.setText(course.courseName)
        editTextCourseFees.setText(course.courseFees.toString())

        buttonUpdateCourseDetails.setOnClickListener {
            setCourseUpdateDetails(course)
        }
    }

    fun setCourseUpdateDetails(course: Course) {
        course.courseNo = editTextCourseNo.text.toString()
        course.courseName = editTextCourseName.text.toString()
        course.courseFees = editTextCourseFees.text.toString().toDouble()

        val intent = Intent()
        intent.putExtra("course", course)
        setResult(RESULT_OK, intent)
        //finish() // destorys the activity.
    }


}