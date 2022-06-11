package com.example.footsetmove.adapters

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.footsetmove.ui.fragments.cash.CashFragment
import com.example.footsetmove.ui.fragments.earnmore.EarnMoreFragment
import com.example.footsetmove.ui.fragments.friends.FriendsFragment
import com.example.footsetmove.ui.fragments.home.HomeFragment
import com.example.footsetmove.ui.fragments.offers.OffersFragment

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