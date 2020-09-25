package com.example.touristapp

import java.io.Serializable
import java.util.*

class User : Serializable{
    var login: String? = null
    var password: String? = null
    var eMail: String? = null
    var coordinates: String? = null

    var placesDone: ArrayList<Int>? = null
    var placesUndone: ArrayList<Int>? = null

   // fun getPassword(): String? {
     //   return password
    //}
    constructor(){
       coordinates=""
       placesDone = ArrayList<Int>()
       placesUndone = ArrayList<Int>()
   }

   constructor(login: String?, password: String? ) {
       this.login = login
       this.password = password
       eMail=""
       coordinates=""
       placesDone = ArrayList<Int>()
       placesUndone = ArrayList<Int>()
       for (i in 10..200){
           placesUndone!!.add(i)
       }
   }
    constructor(login: String?, password: String?,coordinates: String? ) {
        this.login = login
        this.password = password
        this.eMail = coordinates
        this.coordinates=""
    }
    fun User(login:String?,password:String?) {
        this.login=login
        this.password=password
    }

    fun updateDatabase(){
        //val fbdb = DatabaseHandler(this)
    }
    fun isUndone(i: Int): Boolean {
        return if (placesUndone != null && placesUndone!!.contains(i)) true else false
    }

    fun isDone(i: Int): Boolean {
        return if (placesDone != null && placesDone!!.contains(i)) true else false
    }
}