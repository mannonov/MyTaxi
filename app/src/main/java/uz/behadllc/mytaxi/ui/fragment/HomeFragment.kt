package uz.behadllc.mytaxi.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.behadllc.mytaxi.R
import uz.behadllc.mytaxi.databinding.FragmentHomeBinding
import uz.behadllc.mytaxi.model.GeoCodeInfo
import uz.behadllc.mytaxi.model.GeoResponse
import uz.behadllc.mytaxi.model.Latlng
import uz.behadllc.mytaxi.network.geoposition.GeoClient
import uz.behadllc.mytaxi.utils.Status
import uz.behadllc.mytaxi.utils.distance
import uz.behadllc.mytaxi.viewmodel.GeoViewModel

class HomeFragment : Fragment() {


    private val REQUEST_LOCATION_PERMISSION = 712

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val TAG = "HomeFragment"

//    private val geoViewModel by viewModel<GeoViewModel>()


    private val callback = OnMapReadyCallback { googleMap ->

        map = googleMap

        googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        googleMap.uiSettings.isMyLocationButtonEnabled = false


        setMapStyle(googleMap)
        enableMyLocation()
        cameraMoveStartedListener(googleMap)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.home_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.imgNavigationHome.setOnClickListener {
            getCurrentLocation()
        }

        binding.imgHomeMenu.setOnClickListener {
            binding.homeDrawer.openDrawer(Gravity.LEFT)
        }

        binding.trips.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_tripsFragment)
            binding.homeDrawer.closeDrawer(Gravity.LEFT)
        }


    }

    private fun getCurrentLocation(): Boolean {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null)
                    navigateCurrentLocation(location)
            }
        return true

    }

    private fun navigateCurrentLocation(location: Location) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude,
            location.longitude), 17.0f))
    }

    private fun cameraMoveStartedListener(googleMap: GoogleMap) {
        googleMap.setOnCameraMoveStartedListener {
            binding.mapPoint.playAnimation()
            binding.mapPoint.loop(true)
        }
        googleMap.setOnCameraIdleListener {
            binding.mapPoint.loop(false)
            //Bu yerda yaxshiroq logika bo'lishi mumkin edi,
            // lekin API clear text trafic bilan muammo sababli response yoki boshqa wrapper
            // class'lardan foydalanad olmadim, call ishlayabdi ))
            // GeoLocation label olishda free api service'dan foydalanilgan shuning uchun to'liq aniq
            // chiqarib bermasligi mumkin

            getCurrentLatLngLabel(latlng = googleMap.cameraPosition.target)

        }
    }

    private fun getCurrentLatLngLabel(latlng: LatLng) {
        with(latlng) {
            GeoClient.geoService.getGeoCodeInfo(getString(R.string.acces_key),
                Latlng(this.latitude,this.longitude)).enqueue(object : Callback<GeoResponse> {
                override fun onResponse(
                    call: Call<GeoResponse>,
                    response: Response<GeoResponse>,
                ) {
                    if (response.isSuccessful) {
                        binding.edtStartPoint.setText(calculateDestination(response = response.body()!!,
                            latlng)?.name)
                    }
                }

                override fun onFailure(call: Call<GeoResponse>, t: Throwable) {

                    Snackbar.make(binding.root,"${t.message}",Snackbar.LENGTH_INDEFINITE).show()

                }

            })
        }
    }

    private fun calculateDestination(response: GeoResponse, latlng: LatLng): GeoCodeInfo? {

        var geoCodeInfo: GeoCodeInfo? = null
        var minDistance = Double.MAX_VALUE

        for (datum in response.data) {
            if (minDistance > distance(datum.latitude,
                    datum.longitude,
                    latlng.latitude,
                    latlng.longitude)
            ) {
                minDistance =
                    distance(datum.latitude, datum.longitude, latlng.latitude, latlng.longitude)
                geoCodeInfo = datum
            }
        }

        return geoCodeInfo

    }

    private fun setMapStyle(map: GoogleMap) {
        try {
            map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireActivity(),
                    R.raw.map_style
                )
            )
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }
    }

    private fun isPermissionGranted(): Boolean {
        return checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true
            getCurrentLocation()
        } else {
            requestPermissions(
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}