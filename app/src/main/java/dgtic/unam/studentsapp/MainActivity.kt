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
    private lateinit var volleyAPI: VolleyAPI
    private lateinit var url: String
    private lateinit var drawer: DrawerLayout
    private val springRestApp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        print("Spring = "+ springRestApp)
        volleyAPI = VolleyAPI(this)
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

    /*

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        print("Spring = "+ springRestApp)
        volleyAPI = VolleyAPI(this)
        url = ""
        binding.search.setOnClickListener {
            binding.outText.text = ""
            url = "https://www.google.es/search?q=" + URLEncoder.encode(binding.searchText.text.toString(), "UTF-8")
            buscar()
        }

        binding.restxml.setOnClickListener {
            studentXML()
        }

        binding.restjson.setOnClickListener {
            studentJSON()
        }

        binding.restjsonid.setOnClickListener {
            studentID()
        }

        binding.restjsonadd.setOnClickListener {
            studentAdd()
        }

        binding.restjsondelete.setOnClickListener {
            studentDelete()
        }
    }

    private fun buscar() {
        val stringRequest = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                binding.outText.text = response
            },
            Response.ErrorListener { binding.outText.text = getString(R.string.error) }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyAPI.add(stringRequest)
    }

    private fun studentXML() {
        val urlXML = "http://"+springRestApp+"/estudiantesXML"
        var stringRequest=object: StringRequest(
            Request.Method.GET,urlXML, Response.Listener<String>{ response->
                binding.outText.text=response
            }, Response.ErrorListener { binding.outText.text="No se encuentra la información, revice su conexión" }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["User-Agent"]="Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyAPI.add(stringRequest)
    }

    private fun studentJSON() {
        val urlJSON = "http://"+springRestApp+"/estudiantesJSON"
        var cadena = ""

        var stringRequest = object: JsonArrayRequest(
            urlJSON,
            Response.Listener<JSONArray>{ response ->
                (0 until response.length()).forEach{
                    val estudiante = response.getJSONObject(it)
                    val materia = estudiante.getJSONArray("materias")
                    cadena += estudiante.get("cuenta").toString() + "<"
                    (0 until materia.length()).forEach{
                        val datos = materia.getJSONObject(it)
                        cadena += datos.get("nombre").toString() + "**" +datos.get("creditos").toString() + "--"
                    }
                    cadena += "> \n"
                }
                binding.outText.text = cadena
            },
            Response.ErrorListener {
                binding.outText.text = "No se encuentra la información, revise la conexión"
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["User-Agent"]="Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyAPI.add(stringRequest)
    }

    private fun studentID() {
        val urlJSON = "http://" + springRestApp + "/id/" + binding.searchText.text.toString()
        val jsonRequest = object : JsonObjectRequest(
            Method.GET,
            urlJSON,
            null,
            Response.Listener<JSONObject> { response ->
                binding.outText.text = response.get("cuenta")
                    .toString() + "----" + response.get("nombre").toString() + "\n"
            },
            Response.ErrorListener {
                binding.outText.text = "No se encuentra la información, revice la conexión"
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyAPI.add(jsonRequest)
    }

    private fun studentAdd() {
        val urlJSON = "http://"+springRestApp+"/agregarestudiante"
        var cadena = ""

        var stringRequest = object: JsonArrayRequest(
            urlJSON,
            Response.Listener<JSONArray>{ response ->
                (0 until response.length()).forEach{
                    val estudiante = response.getJSONObject(it)
                    val materia = estudiante.getJSONArray("materias")
                    cadena += estudiante.get("cuenta").toString() + "<"
                    (0 until materia.length()).forEach{
                        val datos = materia.getJSONObject(it)
                        cadena += datos.get("nombre").toString() + "**" +datos.get("creditos").toString() + "--"
                    }
                    cadena += "> \n"
                }
                binding.outText.text = cadena
            },
            Response.ErrorListener {
                binding.outText.text = "No se encuentra la información, revise la conexión"
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["User-Agent"]="Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }

            override fun getBody(): ByteArray {
                val estudiante =  JSONObject()
                estudiante.put("cuenta", "A00")
                estudiante.put("nombre", "Android")
                estudiante.put("edad", "200")
                val materias =  JSONArray()
                val itemMaterias = JSONObject()
                itemMaterias.put("id", "1")
                itemMaterias.put("nombre", "Nueva materia")
                itemMaterias.put("creditos", "100")
                materias.put(itemMaterias)
                estudiante.put("materias", materias)
                return estudiante.toString().toByteArray(charset = Charsets.UTF_8)
            }

            override fun getMethod(): Int {
                return Method.POST
            }
        }
        volleyAPI.add(stringRequest)
    }

    private fun studentDelete(){
        val urlJSON = "http://"+springRestApp+"/borrarestudiante/"+binding.searchText.text.toString()
        var cadena = ""

        val stringRequest = object: JsonArrayRequest(
            urlJSON,
            Response.Listener<JSONArray>{ response ->
                (0 until response.length()).forEach{
                    val estudiante = response.getJSONObject(it)
                    val materia = estudiante.getJSONArray("materias")
                    cadena += estudiante.get("cuenta").toString() + "<"
                    (0 until materia.length()).forEach{
                        val datos = materia.getJSONObject(it)
                        cadena += datos.get("nombre").toString() + "**" +datos.get("creditos").toString() + "--"
                    }
                    cadena += "> \n"
                }
                binding.outText.text = cadena
            },
            Response.ErrorListener {
                binding.outText.text = "No se encuentra la información, revise la conexión"
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["User-Agent"]="Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }

            override fun getMethod(): Int {
                return Method.DELETE
            }
        }
        volleyAPI.add(stringRequest)
    }*/
}