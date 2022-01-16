package com.mobile.weatherappglobalkenetic.ui.weather.forecast

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.weatherappglobalkenetic.R
import com.mobile.weatherappglobalkenetic.databinding.ForecastListItemBinding
import com.mobile.weatherappglobalkenetic.model.ForecastListItem

class ForecastAdapter(private val context: Context) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    private var forecasts: List<ForecastListItem> = ArrayList()

    class ViewHolder(binding: ForecastListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val day = binding.day
        val image = binding.image
        val temp = binding.temp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ForecastListItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.forecast_list_item, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = forecasts[position]

        if (forecasts.isNotEmpty()) {
            holder.day.text = forecast.day
            val imageDrawable = getImageDrawable(forecast.imageDrawableId)
            holder.image.setImageDrawable(imageDrawable)
            holder.temp.text = forecast.temp
        }
    }

    private fun getImageDrawable(drawableId: Int): Drawable? {
        return drawableId.let { drawable -> ResourcesCompat.getDrawable(context.resources, drawable, null) }
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }

    fun updateForecasts(newForecasts: List<ForecastListItem>) {
        forecasts = newForecasts
        notifyDataSetChanged()
    }
}