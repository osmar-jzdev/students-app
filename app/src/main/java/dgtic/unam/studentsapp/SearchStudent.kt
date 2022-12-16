package dgtic.unam.studentsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dgtic.unam.studentsapp.databinding.ActivitySearchStudentBinding

class SearchStudent : AppCompatActivity() {

    private lateinit var binding: ActivitySearchStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}