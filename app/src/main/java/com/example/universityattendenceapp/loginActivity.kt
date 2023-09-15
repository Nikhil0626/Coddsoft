package com.example.universityattendenceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.universityattendenceapp.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class loginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.btnStudentLogIn.setOnClickListener{
            val loginUserName = binding.loginUserName.text.toString()
            val loginPassword = binding.loginPassword.text.toString()

            if(loginUserName.isNotEmpty() && loginPassword.isNotEmpty()){
                LogInUserStudent(loginUserName, loginPassword)
            }
            else{
                Toast.makeText(this,"All field are required",Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnTeacherLogIn.setOnClickListener{
            val loginUserName = binding.loginUserName.text.toString()
            val loginPassword = binding.loginPassword.text.toString()

            if(loginUserName.isNotEmpty() && loginPassword.isNotEmpty()){
                LogInUserTeacher(loginUserName, loginPassword)
            }
            else{
                Toast.makeText(this,"All field are required",Toast.LENGTH_SHORT).show()
            }
        }
        binding.SignUpText.setOnClickListener {
            startActivity(Intent(this, signUpActivity::class.java))
            finish()
        }
    }
    private fun LogInUserStudent(username: String, password: String){
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(UserSnapshot in snapshot.children){
                        val userData = UserSnapshot.getValue(UserData::class.java)

                        if(userData!=null && userData.password==password){
                            Toast.makeText(this@loginActivity,"LogIn is successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@loginActivity, MainActivity::class.java))
                            finish()
                            return
                        }
                    }
                }
                Toast.makeText(this@loginActivity, "LogIn failed",Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@loginActivity, "database Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun LogInUserTeacher(username:String, password: String){
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(UserSnapShot in snapshot.children){
                        val userData = UserSnapShot.getValue(UserData::class.java)

                        if(userData!=null && userData.password==password){
                            Toast.makeText(this@loginActivity,"LogIn is successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@loginActivity, MainActivityTeacher::class.java))
                            finish()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@loginActivity,"database Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}