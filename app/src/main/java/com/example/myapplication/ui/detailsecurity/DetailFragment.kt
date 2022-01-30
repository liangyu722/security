package com.example.myapplication.ui.detailsecurity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.myapplication.common.setupSnackbar
import com.example.myapplication.databinding.SecurityDetailFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import java.text.DateFormat
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dateFormat: DateFormat

    private lateinit var viewModel: SecurityDetailViewModel
    private lateinit var viewDataBinding: SecurityDetailFragmentBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SecurityDetailViewModel::class.java)
        viewDataBinding = SecurityDetailFragmentBinding.inflate(inflater, container, false)
        viewDataBinding.viewmodel = viewModel
        viewDataBinding.dateFormatter = dateFormat
        viewModel.getSecurityDetail(args.securityId)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
    }

}
