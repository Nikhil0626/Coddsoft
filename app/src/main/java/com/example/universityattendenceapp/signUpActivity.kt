package com.example.universityattendenceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.widget.Toast
import com.example.universityattendenceapp.databinding.ActivitySignUpBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class signUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.btnStudentSignUp.setOnClickListener{
            val userSignUp = binding.signupUserName.text.toString()
            val passwordSignUp = binding.signupPassword.text.toString()

            if(userSignUp.isNotBlank() && passwordSignUp.isNotEmpty()){
                signUpUser(userSignUp, passwordSignUp)
            }else{
                Toast.makeText(this, "All blanks are mandatory", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnTeacherSignUp.setOnClickListener {
            val userSignUp = binding.signupUserName.text.toString()
            val passwordSignUp = binding.signupPassword.text.toString()

            if (userSignUp.isNotBlank() && passwordSignUp.isNotEmpty()) {
                signUpUser(userSignUp, passwordSignUp)
            } else {
                Toast.makeText(this, "All blanks are mandatory", Toast.LENGTH_SHORT).show()
            }
        }
        binding.LogInText.setOnClickListener{
            startActivity(Intent(this,loginActivity::class.java))
            finish()
        }
    }
    private fun signUpUser(username: String, password: String){
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists()){
                    val id = databaseReference.push().key
                    val userData = UserData(id, username, password)
                    databaseReference.child(id!!).setValue(userData)
                    Toast.makeText(this@signUpActivity,"SignUp is Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@signUpActivity,loginActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@signUpActivity, "User Already Exist",Toast.LENGTH_SHORT).show()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@signUpActivity,"Database Error: ${error.message}",Toast.LENGTH_SHORT ).show()
            }
        })
    }
}


