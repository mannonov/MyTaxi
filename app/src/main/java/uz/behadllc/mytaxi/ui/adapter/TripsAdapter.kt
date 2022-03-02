package uz.behadllc.mytaxi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.behadllc.mytaxi.databinding.ItemTripsBinding
import uz.behadllc.mytaxi.model.Trip

class TripsAdapter(
    private var trips: List<Trip>,
    private val tripClickListener: MiniTripsAdapter.TripClickListener,
) :
    RecyclerView.Adapter<TripsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTripsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.apply {
            tvTripDate.text = trips[position].date
            rvTripsAdapter.apply {
                adapter = MiniTripsAdapter(miniTrips = trips[position].trips, tripClickListener)
                layoutManager = LinearLayoutManager(context)
            }
        }

    }


    override fun getItemCount(): Int = trips.size

    fun sumbitList(trips:List<Trip>){
        this.trips = emptyList()
        this.trips = trips
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemTripsBinding) : RecyclerView.ViewHolder(binding.root)

}