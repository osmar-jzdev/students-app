package dgtic.unam.studentsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dgtic.unam.studentsapp.databinding.ActivityDeleteStudentBinding

class DeleteStudent : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}