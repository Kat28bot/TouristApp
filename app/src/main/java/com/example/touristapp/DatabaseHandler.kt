package com.example.touristapp

import android.util.Log
import com.google.firebase.database.*

class DatabaseHandler {
    //var database: FirebaseDatabase? = null
    private  var database: FirebaseDatabase
   // private var mAuth:FirebaseAuth?=null
   private  var dbreference: DatabaseReference
   // var dbreference: DatabaseReference? = null
   lateinit var user: User
    // var user: User? = null
    var userExsist:Boolean=false

    interface DataStatus {
        fun dataInserted()
        fun dataUpdated()
        fun dataLoaded()
        fun dataExists()
        fun databaseFailure()
        fun dataExistsNot()
    }

    constructor(){
       // database = Firebase.database.reference
        database= FirebaseDatabase.getInstance()
        dbreference= database.reference//!!.getReference()
    }
    constructor(user:User){
        database= FirebaseDatabase.getInstance()
        dbreference= database.reference//!!.getReference()
        this.user=user
    }

    fun checkIfUserExistsAndLogin(
        l: String?,
        p: String,
        ds: DataStatus
    ) {
        dbreference = database.reference.child("users")
        dbreference.child(l!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists() && p == dataSnapshot.getValue(User::class.java)?.password ) {
                    user = dataSnapshot.getValue(User::class.java)!!
                    Log.i("logIn","data Exsists")
                    ds.dataExists()
                } else {
                    Log.i("logIn","data Exsists Not")
                    ds.dataExistsNot()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                ds.databaseFailure()
            }
        })
    }

    fun checkIfUserExistsAndRegister(
        l: String?,
        ds: DataStatus
    ) {
        dbreference = database.reference.child("users")
        dbreference.child(l!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists() ) {
                    //user = dataSnapshot.getValue(User::class.java)!!
                    Log.i("logIn","data Exsists")
                    ds.dataExists()
                } else {
                    Log.i("logIn","data Exsists Not")
                    ds.dataExistsNot()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //ds.databaseFailure()
            }
        })
    }

   /* public fun addUser(user:User){
        dbreference = database.reference.child("users")
        dbreference.child(user.login.toString()).setValue(user).addOnSuccessListener {
            //ds.dataInserted()
             }
           // .addOnFailureListener { ds.databaseFailure() }
    }*/
   open fun addUser(us: User, ds: DataStatus): Unit {
       dbreference = database.reference.child("users")
       dbreference.child(us.login.toString()).setValue(us).addOnSuccessListener { ds.dataInserted() }
           .addOnFailureListener { ds.databaseFailure() }
   }

    public fun SignIn(login:String,password:String){
    user=User(login,password)
        dbreference = database.reference.child("users")
        dbreference.child(login).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    userExsist=true
                    Log.i("signIn","user Exsists")
                } else {
                    userExsist=false
                    Log.i("signIn","user Exsists Not")
                    //ds.dataExistsNot()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //ds.databaseFailure()
                Log.i("signIn","Database Error")
                //Toast.makeText(StartActivity, "Database failed.",
                  //  Toast.LENGTH_SHORT).show()
            }
        })

    }
    public  fun SignUp(username:String,email:String,password:String){
        dbreference = database.reference.child("users")
        dbreference.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                 //   ds.dataExists()
                } else {
                    user=User(username,email,password)
                   // ds.dataExistsNot()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //ds.databaseFailure()
            }
        })
    }
    public fun logOut(){

    }
}