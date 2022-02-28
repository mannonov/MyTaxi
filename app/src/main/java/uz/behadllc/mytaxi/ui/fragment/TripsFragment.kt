package uz.behadllc.mytaxi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.behadllc.mytaxi.R
import uz.behadllc.mytaxi.databinding.FragmentTripsBinding
import uz.behadllc.mytaxi.model.MiniTrip
import uz.behadllc.mytaxi.model.Trip
import uz.behadllc.mytaxi.ui.adapter.TripsAdapter
import uz.behadllc.mytaxi.utils.TaxiType


class TripsFragment : Fragment() {

    private lateinit var _binding: FragmentTripsBinding
    private val binding get() = _binding
    private lateinit var trips: ArrayList<Trip>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        trips = ArrayList()
        trips.add(Trip("6 Июля, Вторник",generateMiniTrips(count = 2)))
        trips.add(Trip("5 Июля, Вторник",generateMiniTrips(count = 1)))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTripsBinding.inflate(layoutInflater)
        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTrips.apply {
            adapter = TripsAdapter(trips)
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun generateMiniTrips(count:Int): ArrayList<MiniTrip> {
        return ArrayList<MiniTrip>().apply {
            for (i in 1..count){
                add(MiniTrip(startPoint = "Яшнабадский район, улица Sharof Rashidov, Ташкент",
                    endPoint = "Юнусабадский район, м-в юнусабад-19, ул. дехканабад, 17",
                    time = "22:22", price = "11 100 сум", TaxiType.DELIVERY))
                add(MiniTrip(startPoint = "улица Sharof Rashidov, Ташкент",
                    endPoint = "5a улица Асадуллы Ходжаева",
                    time = "22:22", price = "11 100 сум", TaxiType.START))
            }
        }
    }

}