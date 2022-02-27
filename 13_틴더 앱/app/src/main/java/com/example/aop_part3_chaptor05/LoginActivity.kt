package com.example.aop_part3_chaptor05

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        callbackManager = CallbackManager.Factory.create()

        initLoginButton()
        initSignUpButton()
        initEmailAndPasswordInitText()
        intiFacebookLoginButton()
    }



    // Facebook 은 Activity Callback 필요

    private fun initEmailAndPasswordInitText() {
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        emailEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            signUpButton.isEnabled = enable
        }

        passwordEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            signUpButton.isEnabled = enable
        }

    }

    private fun initLoginButton() {
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) { // 로그인이 성공적으로 이루어 졌다면 !
                        handleSuccessLogin()
                    } else {
                        Toast.makeText(this, "로그인에 실패 하였습니다. 이메일 혹은 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }

    private fun initSignUpButton() { // 커멘드 옵션 M으로 바로 fun 만들 수 있음.
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "회원가입을 성공했습니다. 로그인 버튼을 눌러 로그인해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "이미 가입한 이메일이거나, 회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }

    private fun intiFacebookLoginButton() {
        val facebookLoginButton = findViewById<LoginButton>(R.id.facebookLoginButton)
        facebookLoginButton.setPermissions("email", "public_profile") // 페이스북에서 email과 public_profile을 가져오 겠다 .!
        facebookLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                // 로그인 성공적 !
                val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        if(task.isSuccessful){
                            handleSuccessLogin()
                            finish()
                        }
                        else{
                            Toast.makeText(this@LoginActivity, "페이스북 로그인이 실패했습니다..", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

            override fun onCancel() {}

            override fun onError(error: FacebookException?) {
                Toast.makeText(this@LoginActivity, "페이스북 로그인이 실패했습니다..", Toast.LENGTH_SHORT).show()
            }


        })
    }

    private fun getInputEmail(): String {
        return findViewById<EditText>(R.id.emailEditText).text.toString()
    }

    private fun getInputPassword(): String {
        return findViewById<EditText>(R.id.passwordEditText).text.toString()
    }

    private fun handleSuccessLogin(){
        if(auth.currentUser == null){
            Toast.makeText(this, "로그인에 실패 하였습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = auth.currentUser?.uid.orEmpty()
        val currentUserDB = Firebase.database.reference.child("Users")
        // json 형식으로 저장이 된걸 가져옴 child로 가져올 수 있음 ! 레퍼런스는 최상위 레퍼런스를 가져온다.

        val user = mutableMapOf<String, Any>()
        user["userId"] = userId
        currentUserDB.updateChildren(user) // userId라는 값을 최상위로 하는 children 추가 해준다.

        startActivity(Intent(this,MainActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

}