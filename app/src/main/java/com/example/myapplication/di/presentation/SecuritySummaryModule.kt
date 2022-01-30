package com.example.myapplication.di.presentation

import androidx.lifecycle.ViewModel
import com.example.myapplication.di.ViewModelBuilder
import com.example.myapplication.di.ViewModelKey
import com.example.myapplication.ui.allsecurities.AllSecurityFragment
import com.example.myapplication.ui.allsecurities.AllSecuritySummaryViewModel
import com.example.myapplication.ui.detailsecurity.DetailFragment
import com.example.myapplication.ui.detailsecurity.SecurityDetailViewModel
import com.example.myapplication.ui.home.HomeFragment
import com.example.myapplication.ui.home.SecuritySummaryViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SecuritySummaryModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun allSecurityFragment(): AllSecurityFragment

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun detailFragment(): DetailFragment

    @Binds
    @IntoMap
    @ViewModelKey(SecuritySummaryViewModel::class)
    abstract fun bindSecuritySummaryViewModel(viewmodel: SecuritySummaryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllSecuritySummaryViewModel::class)
    abstract fun bindAllSecuritySummaryViewModel(viewmodel: AllSecuritySummaryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SecurityDetailViewModel::class)
    abstract fun bindSecurityDetailViewModel(viewmodel: SecurityDetailViewModel): ViewModel
}