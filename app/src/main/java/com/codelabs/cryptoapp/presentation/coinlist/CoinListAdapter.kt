package com.codelabs.cryptoapp.presentation.coinlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.cryptoapp.R
import com.codelabs.cryptoapp.domain.model.Coin

class CoinListAdapter(
    private val callback: (Coin) -> Unit
) : RecyclerView.Adapter<CoinListAdapter.CoinViewHolder>() {
    private val coins = mutableListOf<Coin>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coin, parent, false)

        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(coins[position], callback)
    }

    override fun getItemCount(): Int = coins.size

    fun updateItems(coins: List<Coin>) {
        this.coins.apply {
            clear()
            addAll(coins)
        }
        notifyDataSetChanged()
    }

    class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        private val tvActive = itemView.findViewById<TextView>(R.id.tv_active)

        fun bind(coin: Coin, callback: (Coin) -> Unit) {
            tvName.text = "${coin.rank}. ${coin.name} (${coin.symbol})"
            tvActive.text = if (coin.isActive) "active" else "inactive"

            itemView.setOnClickListener { callback(coin) }
        }
    }
}