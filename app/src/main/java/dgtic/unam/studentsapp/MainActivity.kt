package dgtic.unam.studentsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.google.android.material.navigation.NavigationView
import dgtic.unam.studentsapp.databinding.ActivityMainBinding
import dgtic.unam.studentsapp.databinding.BarMainBinding
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawer: DrawerLayout
    private val springRestApp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inicioToolsBar()
    }

    private fun inicioToolsBar(){
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.abrir, R.string.cerrar)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)
        iniciarNavegacionView()
    }

    private fun iniciarNavegacionView() {
        val navegacionView: NavigationView = binding.navView
        navegacionView.setNavigationItemSelectedListener(this)
        val headerView: View = LayoutInflater.from(this).inflate(R.layout.header_main, navegacionView, false)
        navegacionView.addHeaderView(headerView)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addStudent -> {
                startActivity(Intent(this,AddStudent::class.java))
            }
            R.id.displayStudents->{
                startActivity(Intent(this,DisplayStudents::class.java))
            }
            R.id.searchStudent->{
                startActivity(Intent(this,SearchStudent::class.java))
            }
            R.id.deleteStudent->{
                startActivity(Intent(this,DeleteStudent::class.java))
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}