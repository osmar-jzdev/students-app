package dgtic.unam.studentsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import dgtic.unam.studentsapp.databinding.ActivityAddStudentBinding
import org.json.JSONArray
import org.json.JSONObject

class AddStudent : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding
    private lateinit var volleyAPI: VolleyAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
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
        with(binding) {
            when {
                name.text.toString().isEmpty() -> {
                    name.error = resources.getString(R.string.errorEmptyMsg)
                    Toast.makeText(
                        this@AddStudent,
                        R.string.pleaseFillEverything,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                age.text.toString().isEmpty() -> {
                    age.error = resources.getString(R.string.errorEmptyMsg)
                    Toast.makeText(
                        this@AddStudent,
                        R.string.pleaseFillEverything,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                numMaterias.toString().isEmpty() -> {
                    numMaterias.error = resources.getString(R.string.errorEmptyMsg)
                    Toast.makeText(
                        this@AddStudent,
                        R.string.pleaseFillEverything,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    var subjects: ArrayList<Subject> = ArrayList<Subject>()
                    var student: Student = Student(name.text.toString(), Integer.valueOf(age.text.toString()), subjects)
                    var num = Integer.valueOf(numMaterias.text.toString())
                    (1..num).forEach{
                        val intent = Intent(this@AddStudent, AddSubject::class.java)
                        intent.putExtra("Student", student)
                        startActivity(intent)
                    }
                    name.setText("")
                    age.setText("")
                    numMaterias.setText("")
                    addStudent()
                }
            }
        }
    }

    private fun addStudent(){
        val urlJSON = "http://"+Constants.IP_LOCAL_BACKEND_APPLICATION+"/agregarestudiante"
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
//                binding.outText.text = cadena
            },
            Response.ErrorListener {
//                binding.outText.text = "No se encuentra la información, revise la conexión"
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
        finish()
    }
}