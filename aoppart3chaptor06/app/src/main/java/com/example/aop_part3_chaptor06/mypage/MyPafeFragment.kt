package com.example.aop_part3_chaptor06.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.aop_part3_chaptor06.R
import com.example.aop_part3_chaptor06.databinding.FragmentMypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyPafeFragment : Fragment(R.layout.fragment_mypage) {

    private var binding : FragmentMypageBinding?=null
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentMypageBinding = FragmentMypageBinding.bind(view)

        binding = fragmentMypageBinding

        fragmentMypageBinding.signInButton.setOnClickListener {
            binding?.let { binding ->
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()

                if(auth.currentUser == null){
                    // 로그인 과정
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()){ task ->
                            if (task.isSuccessful){
                                successSiginIn()
                            }
                            else{
                                Toast.makeText(context,"로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                else{
                    auth.signOut()
                    binding.emailEditText.text.clear()
                    binding.emailEditText.isEnabled = true
                    binding.passwordEditText.text.clear()
                    binding.passwordEditText.isEnabled = true

                    binding.signInButton.text = "로그인"
                    binding.signInButton.isEnabled = true
                    binding.signUpButton.isEnabled = false
                }
            }
        }

        fragmentMypageBinding.signUpButton.setOnClickListener {
            binding?.let { binding ->
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()){
                        task ->
                        if (task.isSuccessful){
                            Toast.makeText(context, "회원가입에 성공하였습니다. 로그인버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(context, "회원가입에 실패하였습니다. 다시한번 확인해주세", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }

        fragmentMypageBinding.emailEditText.addTextChangedListener {
            binding?.let { binding ->
                val enable = binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
                binding.signUpButton.isEnabled = enable
                binding.signInButton.isEnabled = enable
            }
        }

        fragmentMypageBinding.passwordEditText.addTextChangedListener {
            binding?.let { binding ->
                val enable = binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
                binding.signUpButton.isEnabled = enable
                binding.signInButton.isEnabled = enable
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if(auth.currentUser == null){
            //로그아웃일때
                binding?.let{ binding ->
                    binding.emailEditText.text.clear()
                    binding.passwordEditText.text.clear()
                    binding.emailEditText.isEnabled = true
                    binding.passwordEditText.isEnabled = true

                    binding.signInButton.text = "로그인"
                    binding.signInButton.isEnabled = true
                    binding.signUpButton.isEnabled = false
                }

        }
        else{
            binding?.let{ binding ->
                binding.emailEditText.setText(auth.currentUser!!.email)
                binding.passwordEditText.setText("*********")
                binding.emailEditText.isEnabled = false
                binding.passwordEditText.isEnabled = false

                binding.signInButton.text = "로그아웃"
                binding.signInButton.isEnabled = true
                binding.signUpButton.isEnabled = false
            }

        }
    }
    private fun successSiginIn() {
        if (auth.currentUser == null){
            Toast.makeText(context, "로그인에 실패 했습니다 다시 시도 해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        binding?.emailEditText?.isEnabled = false
        binding?.passwordEditText?.isEnabled = false
        binding?.signUpButton?.isEnabled = false
        binding?.signInButton?.text = "로그아웃"
    }
}