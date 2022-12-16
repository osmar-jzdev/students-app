package dgtic.unam.studentsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dgtic.unam.studentsapp.databinding.ActivityDisplayStudentsBinding

class DisplayStudents : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayStudentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayStudentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}