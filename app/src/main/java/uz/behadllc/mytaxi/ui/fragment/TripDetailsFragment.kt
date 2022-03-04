package uz.behadllc.mytaxi.ui.fragment

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import uz.behadllc.mytaxi.R
import uz.behadllc.mytaxi.databinding.FragmentTripDetailsBinding
import uz.behadllc.mytaxi.utils.tripConvertor
import uz.behadllc.mytaxi.viewmodel.TripsViewModel


class TripDetailsFragment : Fragment() {

    private lateinit var _binding: FragmentTripDetailsBinding
    private val binding get() = _binding

    private val args: TripDetailsFragmentArgs by navArgs()


    val callback = OnMapReadyCallback {

        it.mapType = GoogleMap.MAP_TYPE_TERRAIN
        it.uiSettings.isMyLocationButtonEnabled = false
        setMapStyle(it)

        setPolyline(googleMap = it, coordinates = getCoordinates(trip = args.trip))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTripDetailsBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.trip_details_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        setUpBottomSheet(args.trip)

        binding.imgBackBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.tripDetailsBottomSheet)

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

                binding.containerRoot.animate().alpha((slideOffset * 0.5).toFloat()).setDuration(0)
                    .start()

            }
        })


    }

    private fun setUpBottomSheet(trip: String) {
        childFragmentManager.beginTransaction()
            .replace(R.id.bottom_sheet_fragment_container,
                BottomSheetFragment().newInstance(tripx = trip))
            .commit()
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

    private fun setPolyline(googleMap: GoogleMap, coordinates: ArrayList<LatLng>) {


        val polyline = PolylineOptions()
        polyline.addAll(coordinates)
        polyline.color(ContextCompat.getColor(requireActivity(), R.color.polyline_color))
        polyline.jointType(JointType.ROUND)
        polyline.startCap(SquareCap())
        polyline.endCap(SquareCap())
        polyline.geodesic(true)
        googleMap.addPolyline(polyline)

        googleMap.addMarker(MarkerOptions()
            .position(coordinates[0])
            .icon(bitmapDescriptorFromVector(requireActivity(), R.drawable.ic_start_point))
            .title("Marker start Point"))

        googleMap.addMarker(MarkerOptions()
            .position(coordinates[coordinates.size - 1])
            .icon(bitmapDescriptorFromVector(requireActivity(), R.drawable.ic_end_point))
            .title("Marker end Point"))

        val builder: LatLngBounds.Builder = LatLngBounds.Builder()
        for (coordinate in coordinates) {
            builder.include(coordinate)
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0))


    }
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    private fun getCoordinates(trip: String): ArrayList<LatLng> {
        val tripX = tripConvertor(trip)
        return ArrayList<LatLng>().apply {
            for (latlng in tripX!!.latlng) {
                add(LatLng(latlng.lat, latlng.lng))
            }
        }
    }

}