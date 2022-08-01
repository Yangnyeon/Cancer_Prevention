package com.example.cancer_prevention.Nutrient

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.cancer_prevention.databinding.ActivityNutrientScreenBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.database.core.Repo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class nutrient_repository {

    lateinit var instance : Repo

    lateinit var arrayList : ArrayList<nutrient_model>

    lateinit var context : Context
    /*

    fun getSavedAddress(): Task<QuerySnapshot> {

       var collectionReference = firestoreDB.collection("Nutrient")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        binding.NutrientRecyclerView.adapter = nutrient_adapter(context, nutrient_model)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "실패", Toast.LENGTH_SHORT).show()
                }

        return collectionReference
    }

     */


    /*
       // save address to firebase
    fun saveAddressItem(addressItem: AddressItem): Task<Void> {
        //var
        var documentReference = firestoreDB.collection("users").document(user!!.email.toString())
            .collection("saved_addresses").document(addressItem.addressId)
        return documentReference.set(addressItem)
    }

    // get saved addresses from firebase
    fun getSavedAddress(): CollectionReference {
        var collectionReference = firestoreDB.collection("users/${user!!.email.toString()}/saved_addresses")
        return collectionReference
    }

    fun deleteAddress(addressItem: AddressItem): Task<Void> {
        var documentReference =  firestoreDB.collection("users/${user!!.email.toString()}/saved_addresses")
            .document(addressItem.addressId)

        return documentReference.delete()
    }

     */


}