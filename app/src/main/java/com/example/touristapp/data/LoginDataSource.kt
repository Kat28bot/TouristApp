package com.example.touristapp.data

import android.util.Log
import android.widget.Toast
import com.example.touristapp.data.model.LoggedInUser
import com.example.touristapp.ui.login.LoginActivity
import com.google.firebase.database.*
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    var database: FirebaseDatabase? = null
    var dbreference: DatabaseReference? = null
    var user: LoggedInUser?=null

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication

            dbreference = database!!.reference.child("users")
           /* dbreference!!.child(username)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists() && password == dataSnapshot.getValue(LoggedInUser::class.java)?.password) {
                            user = dataSnapshot.getValue(LoggedInUser::class.java)
                            // ds.dataExists()
                            Log.i("login", "data exsist")
                            //  Toast.makeText(this, "Authentication failed.",Toast.LENGTH_SHORT).show()
                            //Toast.makeText(this, "I am a Toast message", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.i("login", "data NOT exsist")
                            //  ds.dataExistsNot()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.i("login", "database failure")
                        //  ds.databaseFailure()
                    }
                })*/
            //  database=FirebaseDatabase.getInstance();
            //dbreference= database!!.getReference().child("users")
            // val user = LoggedInUser(dbreference.child(username).)
            //   dbreference.child())
            //  dbreference.child(username).addListenerForSingleValueEvent(new )
            ///
            val geeks = listOf(1, 21, 10)
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")//,geeks,geeks)

              /*  val fuser = LoggedInUser(
                    user!!.displayName, user!!.password, user!!.placesDone,
                    user!!.placesToDo
                )*/
                return Result.Success(fakeUser)
            } catch (e: Throwable) {
            Log.i("here","here")
                return Result.Error(IOException("Error logging in", e))
            }

    }

    fun logout() {
        // TODO: revoke authentication
    }
}

