package uz.behadllc.mytaxi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import uz.behadllc.mytaxi.R
import uz.behadllc.mytaxi.databinding.FragmentBottomSheetBinding
import uz.behadllc.mytaxi.model.TripX
import uz.behadllc.mytaxi.utils.TypeConvertor
import uz.behadllc.mytaxi.utils.getCarImage
import uz.behadllc.mytaxi.utils.tripConvertor
import uz.behadllc.mytaxi.viewmodel.TripsViewModel
import java.lang.reflect.Type


class BottomSheetFragment : Fragment() {

    private lateinit var _binding: FragmentBottomSheetBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.getString(getString(R.string.id_key))?.let {
            tripConvertor(it)?.let {
                binding.apply {
                    tvCarNumber.text = it.carNumber
                    imgCarImage.setImageResource(getCarImage(it.carType))
                    tvCarName.text = it.carName
                    tvStartPoint.text = it.startPoint
                    tvEndPoint.text = it.endPoint
                    tvDriverName.text = it.driverName
                    tvRatingDriverCount.text = it.driverRating.toString()
                    tvDriverTripsCount.text = it.driverTrips.toString()
                    tvTripRate.text = it.rate
                    tvTripPaymentMethod.text = it.paymentMethod
                    tvTripOrder.text = it.order.toString()
                    tvTripDate.text = it.dateAndTimeTrip
                    tvTripDuration.text = it.tripDuration
                    tvTripMinCost.text = it.minPrice
                    tvTripIncreasedDemand.text = it.increasedDemand
                    tvTripCost.text = it.tripPrice
                    tvTripPricePending.text = it.pricePending
                    tvTripTotalPrice.text = it.total
//                    Glide.with(imgDriverImage.context).asBitmap().placeholder(R.drawable.img).load(it.driverImage).into(imgDriverImage)
                }
            }
        }

    }


    fun newInstance(tripx: String): Fragment {
        return BottomSheetFragment().apply {
            arguments = Bundle().apply {
                putString("key_id", tripx)
            }
        }
    }



}