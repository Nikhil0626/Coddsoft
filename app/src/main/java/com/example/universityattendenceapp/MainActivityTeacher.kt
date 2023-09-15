package com.example.universityattendenceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.universityattendenceapp.databinding.ActivityMainTeacherBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivityTeacher : AppCompatActivity() {
    private lateinit var binding: ActivityMainTeacherBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener{
            val searchRollNo = binding.etTeacherRollNo.text.toString()
            val searchDate = binding.etTeacherDate.text.toString()
            if(searchRollNo.isNotEmpty() && searchDate.isNotEmpty()){
                readDate(searchRollNo, searchDate)
            }else{
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun readDate(studentRollNo: String, date: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Student Attendence")
        databaseReference.child(studentRollNo).child(date).get().addOnSuccessListener {
            if(it.exists()){
                val status = it.child("status").value
                Toast.makeText(this,"Data Found",Toast.LENGTH_SHORT).show()
                binding.etTeacherRollNo.text.clear()
                binding.etTeacherDate.text.clear()
                binding.tvStatus.text = status.toString()
            }else {
                Toast.makeText(this,"RollNo not found",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }
    }
}