package com.example.alhanoufalghayhab_codingdojo_notesappfragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alhanoufalghayhab_codingdojo_notesappfragment.Models.NotesData
import com.example.alhanoufalghayhab_codingdojo_notesappfragment.Models.PassData
import com.example.alhanoufalghayhab_codingdojo_notesappfragment.RecView.RecNotesApp
import com.example.alhanoufalghayhab_codingdojo_notesappfragment.databinding.FragmentHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    lateinit var Bindings: FragmentHomeBinding
    lateinit var recViewNotesFragment: RecyclerView
    lateinit var theContext: Context
    lateinit var communicationfrag: PassData
    val db = Firebase.firestore
    val noteData = ArrayList<NotesData>()
    var pk = 0


    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Bindings = FragmentHomeBinding.inflate(inflater, container, false)
        theContext = container!!.context
        buttonAdd()
        //updateNotes()
        retrieveNotes()
        passDataToFragment(0, "")


        recViewNotesFragment = Bindings.recviewnotefragment
        recViewNotesFragment.layoutManager = LinearLayoutManager(theContext)


        // Inflate the layout for this fragment
        return Bindings.root

    }

    fun buttonAdd() {
        //val uuid = UUID.randomUUID().toString() is not work cause i should know how to reach it
        Bindings.addfragment.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_editFragment2)
            ///FragmentTransaction transaction =g
//            val supportFragment = SupportFragment()
//            requireActivity().supportFragmentManager.beginTransaction()
//                .add(this.id, supportFragment)
//                .addToBackStack("ok")
//                .commit()




            var noteSend = Bindings.notetextfieldfragment.text
            val noteUser = hashMapOf(
                "$pk" to "${noteSend.toString()}",
            )
            db.collection("FragmentNoteUser").document("$pk")
                .set(noteUser)
                .addOnSuccessListener {
                    Log.d("TAG", "DocumentSnapshot successfully written")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error writing document", e)
                }
            retrieveNotes()
            pk++

        }
    }

    fun retrieveNotes() {
        db.collection("FragmentNoteUser")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    for (value in document.data.values) {
                        noteData.add(NotesData(document.id.toInt(), value.toString()))
                        Log.d("", "${document.data}")
                        recViewNotesFragment.adapter = RecNotesApp(noteData, theContext)
                        recViewNotesFragment.adapter!!.notifyDataSetChanged()
                    }
                }

            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
        noteData.clear()

    }

    fun passDataToFragment(pk: Int, note: String) {
        var frag = EditFragment()

        var bundle = Bundle()
        bundle.putString("note", note)
        bundle.putInt("pk", pk)
        frag.arguments = bundle

    }
}