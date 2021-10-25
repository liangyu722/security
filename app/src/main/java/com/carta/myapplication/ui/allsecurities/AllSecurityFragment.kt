package com.carta.myapplication.ui.allsecurities

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.carta.myapplication.R
import com.carta.myapplication.common.setupSnackbar
import com.carta.myapplication.databinding.AllSecuritySummaryFragmentBinding
import com.carta.myapplication.ui.SummaryViewModel
import com.carta.myapplication.ui.model.SecuritySummary
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import java.text.DateFormat
import javax.inject.Inject

class AllSecurityFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dateFormat: DateFormat

    private lateinit var viewModel: AllSecuritySummaryViewModel
    private lateinit var viewDataBinding: AllSecuritySummaryFragmentBinding
    private lateinit var adapter: AllSecuritySummaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(AllSecuritySummaryViewModel::class.java)
        viewDataBinding = AllSecuritySummaryFragmentBinding.inflate(inflater, container, false)
        viewDataBinding.viewmodel = viewModel
        return viewDataBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        viewModel.showFavConfirmDialog.observe(this.viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { securitySummary ->
                showDialog(securitySummary)
            }
        })
        view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
    }

    private fun setupListAdapter() {
        adapter = AllSecuritySummaryAdapter(viewModel, dateFormat)
        viewDataBinding.topSecurities.adapter = adapter
        viewDataBinding.topSecurities.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun showDialog(securitySummary: SecuritySummary) {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(
                getString(
                    R.string.add_favorite_dialog_message,
                    securitySummary.companyName
                )
            )
            builder.setPositiveButton(R.string.add_favorite) { dialog, _ ->
                viewModel.onClickSecuritySummaryFav(securitySummary, true)
                dialog.dismiss()
            }
            builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

    }
}
