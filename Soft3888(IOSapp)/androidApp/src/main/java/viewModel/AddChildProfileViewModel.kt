package viewModel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.databinding.BaseObservable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddChildProfileViewModel : BaseObservable() {

    fun addChild(auth: FirebaseAuth, childName: String, childBirthdate: String, theme: String, context: Context) {
        val userId = auth.currentUser?.uid ?: ""

//        Toast.makeText(context, "user id is: ${userId}", Toast.LENGTH_SHORT).show()

        val childInfo = hashMapOf(
            "name" to childName,
            "dateOfBirth" to childBirthdate,
            "theme" to theme,
            "parentId" to userId
        )

        val db = Firebase.firestore
        db.collection("users")
            .document(userId)
            .collection("children")
            .add(childInfo)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
    }

}