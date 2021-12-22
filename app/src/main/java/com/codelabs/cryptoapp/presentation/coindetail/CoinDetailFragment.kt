package com.codelabs.cryptoapp.presentation.coindetail

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.codelabs.cryptoapp.R
import com.codelabs.cryptoapp.common.UiStateController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : Fragment(R.layout.fragment_coin_detail) {
    private var coinId: String? = null
    private lateinit var uiStateController: UiStateController<ViewGroup>

    private val viewModel: CoinDetailViewModel by viewModels()

    companion object {
        private const val ARG_COIN_ID = "ARG_COIN_ID"

        fun newInstance(coinId: String) = CoinDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_COIN_ID, coinId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        coinId = arguments?.getString(ARG_COIN_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiStateController = UiStateController(
            view.findViewById(R.id.view_group_main),
            view.findViewById(R.id.progress_bar),
            view.findViewById(R.id.tv_message)
        )

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CoinDetailState.Loading -> uiStateController.showProgress()
                is CoinDetailState.Error -> uiStateController.showError(state.message)
                is CoinDetailState.Success -> {
                    uiStateController.showMainView {
                        it.findViewById<TextView>(R.id.tv_name).text =
                            "${state.coinDetail.name} - ${state.coinDetail.symbol}"
                        it.findViewById<TextView>(R.id.tv_description).text =
                            state.coinDetail.description
                        it.findViewById<TextView>(R.id.tv_tags).text =
                            state.coinDetail.tags.joinToString(", ")
                        it.findViewById<TextView>(R.id.tv_team).text =
                            state.coinDetail.team.joinToString(", ") { team -> team.name }
                    }
                }
            }
        }

        if (savedInstanceState == null) {
            viewModel.getCoinDetail(coinId!!)
        }
    }
}