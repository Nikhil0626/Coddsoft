package com.example.universityattendenceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.universityattendenceapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SumitAttendence.setOnClickListener{
            val studentRollNo = binding.etRollNo.text.toString()
            val date = binding.etDate.text.toString()
            val status = binding.etStatus.text.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("Student Attendence")
            val studentData = studentData(studentRollNo, date,status)
            databaseReference.child(studentRollNo).child(date).setValue(studentData).addOnSuccessListener {
                binding.etRollNo.text.clear()
                binding.etDate.text.clear()
                binding.etStatus.text.clear()

                Toast.makeText(this, "Data is saved",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,loginActivity::class.java))
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed to upload",Toast.LENGTH_SHORT).show()
            }
        }
    }
}