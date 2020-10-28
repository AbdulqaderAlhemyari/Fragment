package com.example.fragment

import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.random.Random

class StudentListViewModel  : ViewModel() {
    val students = mutableListOf<Student>()

    init {
        for (i in 0 until 6)
        {
            val student = Student()
            student.studName = "Student #$i"
            student.stuPass = i % 2 == 0
            students += student
        }

    }

    fun addStudent(student: Student)
    {
        students.add(student)
    }
}