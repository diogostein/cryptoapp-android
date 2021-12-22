package com.codelabs.cryptoapp.presentation.coinlist

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.cryptoapp.R
import com.codelabs.cryptoapp.common.UiStateController
import com.codelabs.cryptoapp.domain.model.Coin
import com.codelabs.cryptoapp.presentation.coindetail.CoinDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinListFragment : Fragment(R.layout.fragment_coin_list) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var uiStateController: UiStateController<RecyclerView>

    private val viewModel: CoinListViewModel by viewModels()
    private val coinListAdapter = CoinListAdapter { onItemClick(it) }

    companion object {
        fun newInstance() = CoinListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = coinListAdapter

        uiStateController = UiStateController(
            recyclerView,
            view.findViewById(R.id.progress_bar),
            view.findViewById(R.id.tv_message)
        )

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CoinListState.Loading -> uiStateController.showProgress()
                is CoinListState.Error -> uiStateController.showError(state.message)
                is CoinListState.Success -> {
                    uiStateController.showMainView {
                        coinListAdapter.updateItems(state.coins)
                    }
                }
            }
        }

        if (savedInstanceState == null) {
            viewModel.getCoins()
        }
    }

    private fun onItemClick(coin: Coin) {
        activity?.run {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_fragment, CoinDetailFragment.newInstance(coin.id), "TAG")
                .addToBackStack("TAG")
                .commit()

        }
    }
}