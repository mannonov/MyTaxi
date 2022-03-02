package uz.behadllc.mytaxi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.behadllc.mytaxi.databinding.FragmentTripsBinding
import uz.behadllc.mytaxi.model.Trip
import uz.behadllc.mytaxi.ui.adapter.MiniTripsAdapter
import uz.behadllc.mytaxi.ui.adapter.TripsAdapter
import uz.behadllc.mytaxi.utils.Status
import uz.behadllc.mytaxi.viewmodel.TripsViewModel


class TripsFragment : Fragment() {

    private lateinit var _binding: FragmentTripsBinding
    private val binding get() = _binding
    private lateinit var tripsAdapter: TripsAdapter
    private lateinit var trips: ArrayList<Trip>

    private val tripsViewModel by viewModel<TripsViewModel>()

    private val TAG = "TripsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        trips = ArrayList()

        tripsViewModel.getTrips().observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    manageStates(false)
                }
                Status.ERROR -> {
                    Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_INDEFINITE).show()
                }
                Status.SUCCESS -> {

                    manageStates(true)

                    trips.addAll(it.data!!)

                    tripsAdapter.sumbitList(trips)
                }
            }
        }

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

        tripsAdapter = TripsAdapter(trips, MiniTripsAdapter.TripClickListener {
            findNavController().navigate(TripsFragmentDirections.actionTripsFragmentToTripDetailsFragment(
                Gson().toJson(it)))
        })

        binding.rvTrips.apply {
            adapter = tripsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        manageStates(trips.isNotEmpty())

    }

    private fun manageStates(boolean: Boolean) {
        if (boolean){
            binding.shimmerViewContainer.stopShimmerAnimation()
            binding.shimmerContainer.visibility = View.GONE
            binding.shimmerViewContainer.visibility = View.GONE
            binding.rvTrips.visibility = View.VISIBLE
        }else{
            binding.shimmerViewContainer.startShimmerAnimation()
            binding.shimmerContainer.visibility = View.VISIBLE
            binding.shimmerViewContainer.visibility = View.VISIBLE
            binding.rvTrips.visibility = View.GONE
        }
    }

}