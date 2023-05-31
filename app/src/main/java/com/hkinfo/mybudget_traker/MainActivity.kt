package com.hkinfo.mybudget_traker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hkinfo.mybudget_traker.Fragments.BudgetFragment
import com.hkinfo.mybudget_traker.Fragments.HomeFragment
import com.hkinfo.mybudget_traker.Fragments.StatsFragment
import com.hkinfo.mybudget_traker.databinding.ActivityMainBinding

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var Type = arrayOf("home","Budget","Stats")
        var Fragments = arrayOf(HomeFragment(),BudgetFragment(),StatsFragment())
        loadFragment(HomeFragment())
        binding.bottomNav.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when(item.itemId) {
                    R.id.home->{
                        loadFragment(HomeFragment())
                    }
                }
                when(item.itemId) {
                    R.id.budget->{
                        loadFragment(BudgetFragment())
                    }
                }
                when(item.itemId) {
                    R.id.stats->{
                        loadFragment(StatsFragment())
                    }
                }
                return true
            }
        })
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame, fragment)
            .commit()
    }
}