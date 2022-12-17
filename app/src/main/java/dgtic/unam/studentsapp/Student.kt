package dgtic.unam.studentsapp

import java.io.Serializable

class Student(var name: String, var age: Int, var subjects:ArrayList<Subject>): Serializable {
}