package com.akshat.sahijpal.healthtracer.ui.fragments.home

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.akshat.sahijpal.healthtracer.R
import com.akshat.sahijpal.healthtracer.adapters.BottomNavFragmentAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var pager: ViewPager2
    private lateinit var btmNav: BottomNavigationView

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager = view.findViewById(R.id.viewPager2)
        btmNav = view.findViewById(R.id.NavBarBtm)
        setUpBottomNav()

    }


    fun setUpBottomNav() {
        pager.adapter =
            BottomNavFragmentAdapter(requireActivity().supportFragmentManager, lifecycle)
        btmNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> pager.currentItem = 0
                R.id.offersFragment -> pager.currentItem = 1
                R.id.earnMoreFragment -> pager.currentItem = 2
                R.id.cashFragment -> pager.currentItem = 3
                R.id.friendsFragment -> pager.currentItem = 4

            }
            return@setOnNavigationItemSelectedListener true
        }
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> btmNav.menu.findItem(R.id.homeFragment).isChecked = true
                    1 -> btmNav.menu.findItem(R.id.offersFragment).isChecked = true
                    2 -> btmNav.menu.findItem(R.id.earnMoreFragment).isChecked = true
                    3 -> btmNav.menu.findItem(R.id.cashFragment).isChecked = true
                    4 -> btmNav.menu.findItem(R.id.friendsFragment).isChecked = true
                }
            }
        })
    }
}
