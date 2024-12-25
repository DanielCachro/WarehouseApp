package com.example.warehouseapp

import android.os.Handler
import android.os.Looper
import com.google.firebase.database.*

val database: FirebaseDatabase = FirebaseDatabase.getInstance()
val itemsRef: DatabaseReference = database.getReference("items")

fun <T> fetchFromFirebase(
    query: DatabaseReference,
    timeoutMillis: Long = 5000,
    parseSnapshot: (DataSnapshot) -> T?,
    onSuccess: (T) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val timeoutHandler = Handler(Looper.getMainLooper())
    var isCompleted = false

    val timeoutRunnable = Runnable {
        if (!isCompleted) {
            isCompleted = true
            onFailure(Exception("Request timed out"))
        }
    }

    timeoutHandler.postDelayed(timeoutRunnable, timeoutMillis)

    query.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (!isCompleted) {
                timeoutHandler.removeCallbacks(timeoutRunnable)
                isCompleted = true

                val result = parseSnapshot(snapshot)
                if (result != null) {
                    onSuccess(result)
                } else {
                    onFailure(Exception("No data found"))
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            if (!isCompleted) {
                timeoutHandler.removeCallbacks(timeoutRunnable)
                isCompleted = true
                onFailure(error.toException())
            }
        }
    })
}

fun getItemsFromFirebase(onSuccess: (List<Item>) -> Unit, onFailure: (Exception) -> Unit) {
    fetchFromFirebase(
        query = itemsRef,
        parseSnapshot = { snapshot ->
            snapshot.children.mapNotNull { it.getValue<Item>() }
        },
        onSuccess = onSuccess,
        onFailure = onFailure
    )
}

fun getItemFromFirebaseBySku(sku: String, onSuccess: (Item) -> Unit, onFailure: (Exception) -> Unit) {
    fetchFromFirebase(
        query = itemsRef.child(sku),
        parseSnapshot = { it.getValue<Item>() },
        onSuccess = onSuccess,
        onFailure = onFailure
    )
}
