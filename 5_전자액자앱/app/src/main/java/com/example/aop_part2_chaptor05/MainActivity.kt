package com.example.aop_part2_chaptor05

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val addPhotoButton : Button by lazy { // 액자 추가하기 버튼
        findViewById<Button>(R.id.addPhotoButton)
    }

    private val uriImageList : MutableList<Uri> = mutableListOf() // 액자의 사진을 담을 uriImageList !!

    private val startPhotoFrameModeButton : Button by lazy { // 전자액자 실행하기 버튼
        findViewById<Button>(R.id.startPhotoFrameModeButton)
    }

    private val imageViewList : MutableList<ImageView> by lazy{ // 각 레이아웃을 List로 담아줄 수 있다.
        mutableListOf<ImageView>().apply {
            add(findViewById<ImageView>(R.id.photoImageView_11))
            add(findViewById<ImageView>(R.id.photoImageView_12))
            add(findViewById<ImageView>(R.id.photoImageView_13))
            add(findViewById<ImageView>(R.id.photoImageView_21))
            add(findViewById<ImageView>(R.id.photoImageView_22))
            add(findViewById<ImageView>(R.id.photoImageView_23))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAddPhotoButton() // 권한을 받아오기 위해
        initstartPhotoFrameModeButton() // 메소드로 만드는 이유는 가시성 좋아지기 위해 !! 코드 추상화 라고한당
    }

    private fun initAddPhotoButton(){
        addPhotoButton.setOnClickListener{ //사진 추가 버튼을 클릭 할 시 !
            when{
                ContextCompat.checkSelfPermission( // Android에서 권한 보유 여부를 확인하기 위해서는 ContextCompat.checkSelfPermission() 메서드를 사용하면 된다
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // todo 권한이 잘 부여 됐을 때 갤러리에서 사진을 선택하는 기능
                    navigatePhotos()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    // todo 교육용 팝업 확인 후 권환 확인을 띄우는 문
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
                // 권한 허가
                }
            }
        }
    }

    private fun initstartPhotoFrameModeButton(){
        startPhotoFrameModeButton.setOnClickListener {
            val intent = Intent(this,photoFrameActivity::class.java)
            uriImageList.forEachIndexed { // Index와 value를 한번에 가져다줌
                index, uri -> intent.putExtra("photo$index", uri.toString())
            }
            intent.putExtra("photoSize", uriImageList.size)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    navigatePhotos()
                }
                else{
                    Toast.makeText(this,"권한을 거부하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                //
            }
        }
    }

    private fun navigatePhotos(){
        val intent = Intent(Intent.ACTION_GET_CONTENT) // 앨범을 호출할 때, 인텐트에 넣는 액션! ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent,2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK){
            return
        }
        when(requestCode){
            2000 -> {
                val selectedImageUri : Uri? = data?.data

                if (selectedImageUri != null){
                    if(uriImageList.size == 6){
                        Toast.makeText(this,"이미 사진이 꽉 찼습니다.", Toast.LENGTH_SHORT).show()
                        return
                    }
                    uriImageList.add(selectedImageUri)
                    imageViewList[uriImageList.size-1].setImageURI(selectedImageUri)
                }
                else{
                    Toast.makeText(this,"사진을 가져오기 못하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this,"사진을 가져오기 못하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private  fun showPermissionContextPopup(){
        AlertDialog.Builder(this)
            .setTitle("권한이 필요 합니다.")
            .setMessage("전자액자에서 사진을 불러오기 위해 권한이 필요 합니다.")
            .setPositiveButton("권한 허가하기",{_, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            })
            .setNegativeButton("취소하기",{_, _ ->})
            .create()
            .show()
    }
}