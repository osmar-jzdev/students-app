package dgtic.unam.studentsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import dgtic.unam.studentsapp.databinding.ActivityDisplayStudentsBinding
import org.json.JSONArray

class DisplayStudents : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayStudentsBinding
    private lateinit var volleyAPI: VolleyAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayStudentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        volleyAPI = VolleyAPI(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onClick(view: View) {
        val urlJSON = "http://"+Constants.IP_LOCAL_BACKEND_APPLICATION+"/estudiantesJSON"
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
}