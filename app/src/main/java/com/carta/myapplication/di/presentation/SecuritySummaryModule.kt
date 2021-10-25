package com.carta.myapplication.di.presentation

import androidx.lifecycle.ViewModel
import com.carta.myapplication.di.ViewModelBuilder
import com.carta.myapplication.di.ViewModelKey
import com.carta.myapplication.ui.allsecurities.AllSecurityFragment
import com.carta.myapplication.ui.allsecurities.AllSecuritySummaryViewModel
import com.carta.myapplication.ui.detailsecurity.SecurityDetailViewModel
import com.carta.myapplication.ui.home.HomeFragment
import com.carta.myapplication.ui.home.SecuritySummaryViewModel
import com.carta.myapplication.ui.detailsecurity.DetailFragment
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