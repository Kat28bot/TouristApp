package com.example.touristapp.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,


    val displayName: String
   /* val placesDone: List<Int>,
    val placesToDo: List<Int>*/
)
