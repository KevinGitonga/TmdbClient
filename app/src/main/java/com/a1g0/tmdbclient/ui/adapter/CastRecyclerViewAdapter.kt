package com.a1g0.tmdbclient.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.a1g0.tmdbclient.R
import com.a1g0.tmdbclient.data.model.Cast
import com.a1g0.tmdbclient.databinding.ItemCastBinding
import com.a1g0.tmdbclient.utils.Constants
import timber.log.Timber

class CastRecyclerViewAdapter(
    val context: Context,
    private val castList: ArrayList<Cast>
) : RecyclerView.Adapter<CastRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCastBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {

            if (position == 0) {
                binding.spacingStart.visibility = View.VISIBLE
            } else if (position == Math.min(20, castList.size) - 1) {
                binding.spacingEnd.visibility = View.VISIBLE
            } else {
                binding.spacingEnd.visibility = View.GONE
                binding.spacingStart.visibility = View.GONE
            }

            binding.castImage.load(Constants.IMAGE_BASE_URL + castList[position].profile_path) {
                placeholder(Constants.actorPlaceHolder[position % 4])
                error(Constants.actorPlaceHolder[position % 4])
            }

            binding.castName.text = castList[position].name

            binding.root.setOnClickListener {
                val bundle = bundleOf(Constants.cast to castList[position])
                Timber.e("cast data" + castList[position])

                it.findNavController().navigate(R.id.action_movieDetailsFragment_to_actorDetailsFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int = Math.min(20, castList.size)
}
