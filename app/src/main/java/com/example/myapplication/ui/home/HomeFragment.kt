package com.example.myapplication.ui.home

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.myapplication.R
import com.example.myapplication.common.setupSnackbar
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.SummaryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import java.text.DateFormat
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dateFormat: DateFormat

    private lateinit var viewModel: SecuritySummaryViewModel
    private lateinit var viewDataBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SecuritySummaryViewModel::class.java)
        viewDataBinding = FragmentHomeBinding.inflate(inflater, container, false)
        viewDataBinding.viewmodel = viewModel
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupNavigation()
        view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            val topSecurityAdapter = SecuritySummaryAdapter(viewModel, dateFormat)
            viewDataBinding.topSecurities.adapter = topSecurityAdapter
            viewDataBinding.topSecurities.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )

            val favSecurityAdapter = FavSecuritySummaryAdapter(viewModel, dateFormat)
            viewDataBinding.favSecurities.adapter = favSecurityAdapter
            viewDataBinding.topSecurities.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun setupNavigation() {
        viewDataBinding.viewAll.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_AllSecurityFragment)
        }

        viewModel.clickSecuritySummaryEvent.observe(this.viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { securitySummary ->
                val action =
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(securitySummary.id)
                findNavController().navigate(action)
            }
        })
    }
}
