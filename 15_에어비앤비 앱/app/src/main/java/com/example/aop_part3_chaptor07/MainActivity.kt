package com.example.aop_part3_chaptor07

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.naver.maps.map.widget.LocationButtonView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnMapReadyCallback, Overlay.OnClickListener {
    private val viewPager: ViewPager2 by lazy {
        findViewById(R.id.houseViewPager)
    }

    private val viewPagerAdapter = HouseViewPagerAdapter(itemClicked = {
        // 공유 기능
        val intent = Intent()
            .apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "[지금 이 가격에 예약하세요 !] ${it.title} ${it.price} 사진보기 : ${it.imgUrl}")
                type = "text/palin"
            }
        startActivity(Intent.createChooser(intent, null))
    })

    private val currentLocationButton: LocationButtonView by lazy {
        findViewById(R.id.currentLocationButtonView)
    }

    private val recyclerViewAdapter = HouseListAdapter()

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val mapView: MapView by lazy {
        findViewById(R.id.mapView)
    }

    private val bottomSheetTitleTextView : TextView by lazy {
        findViewById(R.id.bottomSheetTitleTextView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewPager.adapter = viewPagerAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val selelctedHouseModel = viewPagerAdapter.currentList[position]
                val cameraUpdate =
                    CameraUpdate.scrollTo(LatLng(selelctedHouseModel.lat, selelctedHouseModel.lng))
                        .animate(CameraAnimation.Easing) // 카메라가 이동할 때 애니메이션 주기 !
                naverMap.moveCamera(cameraUpdate)
            }
        })
    }

    override fun onMapReady(navermap: NaverMap) {
        naverMap = navermap

        navermap.maxZoom = 18.0
        navermap.minZoom = 10.0

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.447981, 126.884462)) // 위도 경도
        navermap.moveCamera(cameraUpdate)

        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false

        currentLocationButton.map = naverMap

        locationSource = FusedLocationSource(this@MainActivity, LOCATION_PERMISSION_REQUEST)
        naverMap.locationSource = locationSource

        getHouseListFromApi()
    }

    private fun updateMarker(houses: List<HouseModel>) {
        houses.forEach { house ->
            val marker = Marker()
            marker.position = LatLng(house.lat, house.lng)
            marker.onClickListener = this
            marker.map = naverMap
            marker.tag = house.id
            marker.icon = MarkerIcons.BLACK
            marker.iconTintColor = Color.RED
        }
    }

    private fun getHouseListFromApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(HouseService::class.java).also {
            it.getHouseList()
                .enqueue(object : Callback<HouseDto> {
                    override fun onResponse(call: Call<HouseDto>, response: Response<HouseDto>) {
                        if (response.isSuccessful.not()) {
                            //실패
                            Log.e("tag", "실패 !! 대실패 !!")
                            return
                        }

                        response.body()?.let { qwe ->
                            updateMarker(qwe.items)
                            viewPagerAdapter.submitList(qwe.items)
                            recyclerViewAdapter.submitList(qwe.items)
                            Log.d("This","dto: ${qwe.items}")
                            bottomSheetTitleTextView.text = "${qwe.items.size}개의 숙소"
                        }
                    }

                    override fun onFailure(call: Call<HouseDto>, t: Throwable) {

                    }

                }
                )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != LOCATION_PERMISSION_REQUEST) {
            return
        }

        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }

    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    // 메모리가 별로 없을 때 호출 되는 함수
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST = 1000
    }

    override fun onClick(overlay: Overlay): Boolean {
        val selectedModel = viewPagerAdapter.currentList.firstOrNull {
            it.id == overlay.tag
        }

        selectedModel?.let {
            val position = viewPagerAdapter.currentList.indexOf(it)
            viewPager.currentItem = position

        }

        return true
    }


}