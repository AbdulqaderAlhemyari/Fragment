package com.example.fragment

import java.util.*

data class Student ( val stuId: UUID = UUID.randomUUID(),
                    var studName: String = "",
                    var stuNumber: Int = 1,
                    var stuPass: Boolean = false)