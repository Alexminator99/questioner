package com.everything.questioner.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.everything.questioner.databinding.NavigationCardItemBinding


public class NavigationCardAdapter(
    private val listOfNavigationCards: List<NavigationCard>,
    private val onClickAction: (NavDirections) -> Unit
) :
    RecyclerView.Adapter<NavigationCardAdapter
    .ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = NavigationCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfNavigationCards[position], onClickAction)
    }

    override fun getItemCount(): Int {
        return listOfNavigationCards.size
    }

    class ViewHolder(val binding: NavigationCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(navigationCard: NavigationCard, onClickAction: (NavDirections) -> Unit) {
            binding.apply {
                navigation = navigationCard
                imageViewNavigation.setImageResource(navigationCard.icon)
                root.setOnClickListener {
                    onClickAction(navigationCard.navigationAction)
                }
                executePendingBindings()
            }
        }
    }
}