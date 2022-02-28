package uz.behadllc.mytaxi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.behadllc.mytaxi.R
import uz.behadllc.mytaxi.databinding.ItemMiniTripBinding
import uz.behadllc.mytaxi.model.MiniTrip
import uz.behadllc.mytaxi.utils.TaxiType

class MiniTripsAdapter(private val miniTrips: List<MiniTrip>) :
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
            imgCarType.setImageResource(getCarImage(miniTrip.taxiType))
        }
    }

    override fun getItemCount(): Int = miniTrips.size

    private fun getCarImage(taxiType: TaxiType) : Int{
        return when(taxiType){
            TaxiType.DELIVERY -> R.drawable.ic_car_delivery
            TaxiType.START -> R.drawable.ic_car_start
            TaxiType.COMFORT -> R.drawable.ic_car_comfort
        }
    }

    class ViewHolder(val binding: ItemMiniTripBinding) : RecyclerView.ViewHolder(binding.root)

}