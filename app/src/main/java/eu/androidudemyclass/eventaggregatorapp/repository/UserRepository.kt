package eu.androidudemyclass.eventaggregatorapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import eu.androidudemyclass.eventaggregatorapp.model.Result
import eu.androidudemyclass.eventaggregatorapp.model.User
import kotlinx.coroutines.tasks.await

class UserRepository(
        private val auth: FirebaseAuth,
        private val firestore: FirebaseFirestore
    ) {
        suspend fun signUp(email: String, password: String, firstName: String, lastName: String,collegeName:String): Result<Boolean> =
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                saveUserToFirestore(User(email, firstName, lastName,collegeName))
                Result.Success(true)
            } catch (e: Exception) {
                Result.Error(e)
            }
        suspend fun login(email: String, password: String): Result<Boolean> =
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                Result.Success(true)
            } catch (e: Exception) {
                Result.Error(e)
            }
        //    Firestore saves data as documents within a collection.
//    For each user document we will save them in a collection called users providing their email as a
//    key rather than letting firestore generate random characters.
//    With this email we can then manage each user better in the chatrooms.
        private suspend fun saveUserToFirestore(user: User) {
            firestore.collection("users").document(user.email).set(user).await()
        }
    suspend fun getCurrentUser(): Result<User> = try {
        val uid = auth.currentUser?.email
        if (uid != null) {
            val userDocument = firestore.collection("users").document(uid).get().await()
            val user = userDocument.toObject(User::class.java)
            if (user != null) {
                Result.Success(user)
            } else {
                Result.Error(Exception("User data not found"))
            }
        } else {
            Result.Error(Exception("User not authenticated"))
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
    }
