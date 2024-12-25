package com.example.warehouseapp

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

val database: FirebaseDatabase = FirebaseDatabase.getInstance()
val itemsRef: DatabaseReference = database.getReference("items")

fun getItemsFromFirebase(onSuccess: (List<Item>) -> Unit, onFailure: (Exception) -> Unit) {
    itemsRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val items = mutableListOf<Item>()

            for (itemSnapshot in snapshot.children) {
                val item = itemSnapshot.getValue<Item>()
                if (item != null) {
                    items.add(item)
                }
            }
            onSuccess(items)
        }

        override fun onCancelled(error: DatabaseError) {
            onFailure(error.toException())
        }
    })
}

fun getItemFromFirebaseBySku(sku: String, onSuccess: (Item) -> Unit, onFailure: (Exception) -> Unit) {
    val itemRef: DatabaseReference = itemsRef.child(sku)

    itemRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val item = snapshot.getValue<Item>()
            if (item != null) {
                onSuccess(item)
            } else {
                onFailure(Exception("Item not found"))
            }
        }

        override fun onCancelled(error: DatabaseError) {
            onFailure(error.toException())
        }
    })
}

