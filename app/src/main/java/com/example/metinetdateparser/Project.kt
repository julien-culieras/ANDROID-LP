package com.example.metinetdateparser

class Project {

    lateinit var id:String
    lateinit var name:String
    lateinit var description:String
    var countStudents:Int = 0


    constructor(id: String, name: String, description: String, countStudents: Int) {
        this.id = id
        this.name = name
        this.description = description
        this.countStudents = countStudents
    }

    constructor()
}