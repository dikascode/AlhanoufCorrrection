package com.example.alhanoufalghayhab_codingdojo_notesappfragment.RecView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.alhanoufalghayhab_codingdojo_notesappfragment.EditFragment
import com.example.alhanoufalghayhab_codingdojo_notesappfragment.HomeFragment
import com.example.alhanoufalghayhab_codingdojo_notesappfragment.Models.NotesData
import com.example.alhanoufalghayhab_codingdojo_notesappfragment.Models.PassData
import com.example.alhanoufalghayhab_codingdojo_notesappfragment.databinding.RecViewNotesAppBinding

class RecNotesApp(val listOfNotes: ArrayList<NotesData>, val activity: Context): RecyclerView.Adapter<RecNotesApp.RecNotesAppView>() {
    private lateinit var communication:PassData

    inner class RecNotesAppView(val Binding: RecViewNotesAppBinding): RecyclerView.ViewHolder(Binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecNotesAppView {

        return RecNotesAppView(RecViewNotesAppBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecNotesAppView, position: Int) {

        holder.Binding.apply {
            notebodyfragment.text = listOfNotes[position].notes
            notebodyfragment .setOnClickListener {

                communication.passDataToFragment(listOfNotes[position].pk,listOfNotes[position].notes)
            }
        }
    }
    override fun getItemCount(): Int {
        return listOfNotes.size
    }
}
