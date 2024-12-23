import com.example.warehouseadmin.Item
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

fun addToFirebase(item: Item, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val itemsRef: DatabaseReference = database.getReference("items")

    val itemRef = itemsRef.child(item.sku)

    itemRef.setValue(item)
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}
