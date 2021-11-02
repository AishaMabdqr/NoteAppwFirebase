package com.example.noteappwfirebase

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var rvItems : RecyclerView
    lateinit var eInput : EditText
    lateinit var bAdd : Button
    lateinit var rvAdapter: RVAdapter


     val myViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvItems = findViewById(R.id.rvItems)
        eInput = findViewById(R.id.eInput)
        bAdd = findViewById(R.id.bAdd)


        myViewModel.getNotes().observe(this, {
                notes -> rvAdapter.update(notes)
        })



        bAdd.setOnClickListener{
            var note = eInput.text.toString()
            myViewModel.addNote(note)
            eInput.text.clear()

        }

        rvAdapter = RVAdapter(this)
        rvItems.adapter = rvAdapter
        rvItems.layoutManager = LinearLayoutManager(this)

    }


    fun dialog(pk : String){
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new note"

        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ -> myViewModel.updateNote(pk, updatedNote.text.toString())

            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
}