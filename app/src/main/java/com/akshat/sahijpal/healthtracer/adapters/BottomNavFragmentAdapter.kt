package com.akshat.sahijpal.healthtracer.adapters

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.akshat.sahijpal.healthtracer.ui.fragments.cash.CashFragment
import com.akshat.sahijpal.healthtracer.ui.fragments.earnmore.EarnMoreFragment
import com.akshat.sahijpal.healthtracer.ui.fragments.friends.FriendsFragment
import com.akshat.sahijpal.healthtracer.ui.fragments.home.HomeFragment
import com.akshat.sahijpal.healthtracer.ui.fragments.offers.OffersFragment

class BottomNavFragmentAdapter(fgm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fgm, lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int) = when (position) {
        0 -> HomeFragment()
        1 -> OffersFragment()
        2 -> EarnMoreFragment()
        3 -> CashFragment()
        else -> FriendsFragment()
    }
}