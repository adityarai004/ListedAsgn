package com.example.listedasgn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.listedasgn.databinding.ActivityMainBinding
import com.example.listedasgn.fragments.CampaignFragment
import com.example.listedasgn.fragments.CoursesFragment
import com.example.listedasgn.fragments.LinksFragment
import com.example.listedasgn.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        replaceFragment(LinksFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.links -> replaceFragment(LinksFragment())
                R.id.courses -> replaceFragment(CoursesFragment())
                R.id.campaign -> replaceFragment(CampaignFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

                else ->{

                }
            }

            true
        }
    }

    override fun onResume() {
        super.onResume()
        replaceFragment(LinksFragment())
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}