package com.infinity.ishkhan.red

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                inflateFragment(Heart())

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {

                inflateFragment(Add())

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

                inflateFragment(List())

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        inflateFragment(Heart())

    }

    fun inflateFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, fragment).commit()
    }


}
