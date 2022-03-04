package uz.behadllc.mytaxi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.behadllc.mytaxi.databinding.ItemMiniTripBinding
import uz.behadllc.mytaxi.model.TripX
import uz.behadllc.mytaxi.utils.getCarImage

class MiniTripsAdapter(private val miniTrips: List<TripX>, private val tripClickListener:TripClickListener) :
    RecyclerView.Adapter<MiniTripsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMiniTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val miniTrip = miniTrips[position]

        holder.binding.apply {
            tvStartPoint.text = miniTrip.startPoint
            tvEndPoint.text = miniTrip.endPoint
            tvItemPrice.text = miniTrip.price
            tvItemTime.text = miniTrip.time
            imgCarType.setImageResource(getCarImage(miniTrip.carType))
        }

        holder.binding.root.setOnClickListener {
            tripClickListener.onClick(miniTrip)
        }

    }

    override fun getItemCount(): Int = miniTrips.size



    class TripClickListener(val onClick: (miniTrip: TripX) -> Unit)

    class ViewHolder(val binding: ItemMiniTripBinding) : RecyclerView.ViewHolder(binding.root)

}