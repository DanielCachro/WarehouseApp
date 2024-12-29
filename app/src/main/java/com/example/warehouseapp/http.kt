package com.example.warehouseapp

import android.os.Handler
import android.os.Looper
import com.google.firebase.database.*

val database: FirebaseDatabase = FirebaseDatabase.getInstance()
val itemsRef: DatabaseReference = database.getReference("items")

private fun fetchDataFromFirebase(
    reference: DatabaseReference,
    onFailure: (Exception) -> Unit,
    onDataChange: (DataSnapshot) -> Unit
) {
    val handler = Handler(Looper.getMainLooper())
    val timeoutRunnable = Runnable {
        onFailure(Exception("Request timed out"))
    }

    handler.postDelayed(timeoutRunnable, 5000)

    reference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            handler.removeCallbacks(timeoutRunnable)
            onDataChange(snapshot)
        }

        override fun onCancelled(error: DatabaseError) {
            handler.removeCallbacks(timeoutRunnable)
            onFailure(error.toException())
        }
    })
}

fun getItemsFromFirebase(onSuccess: (List<Item>) -> Unit, onFailure: (Exception) -> Unit) {
    fetchDataFromFirebase(itemsRef, onFailure) { snapshot ->
        val items = mutableListOf<Item>()
        for (itemSnapshot in snapshot.children) {
            val item = itemSnapshot.getValue<Item>()
            if (item != null) {
                items.add(item)
            }
        }
        onSuccess(items)
    }
}

fun getItemFromFirebaseBySku(
    sku: String,
    onSuccess: (Item) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val itemRef: DatabaseReference = itemsRef.child(sku)
    fetchDataFromFirebase(itemRef, onFailure) { snapshot ->
        val item = snapshot.getValue<Item>()
        if (item != null) {
            onSuccess(item)
        } else {
            onFailure(Exception("Item not found"))
        }
    }
}
