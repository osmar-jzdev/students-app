package dgtic.unam.studentsapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import dgtic.unam.studentsapp.databinding.ActivityAddSubjectBinding

class AddSubject : AppCompatActivity() {

    private lateinit var binding: ActivityAddSubjectBinding
    private var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        student = intent.getSerializableExtra("Student") as Student?
    }

    fun onClick(view: View) {
        with(binding){
            when{
                subjectName.text.toString().isEmpty() -> {
                    subjectName.error = resources.getString(R.string.errorEmptyMsg)
                    Toast.makeText(
                        this@AddSubject,
                        R.string.pleaseFillEverything,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                credits.text.toString().isEmpty() -> {
                    credits.error = resources.getString(R.string.errorEmptyMsg)
                    Toast.makeText(
                        this@AddSubject,
                        R.string.pleaseFillEverything,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    setResult(Activity.RESULT_OK, Intent().putExtra("Student", student))
                    finish()
                }
            }

        }
    }
}