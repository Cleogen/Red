package com.infinity.ishkhan.red

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.infinity.ishkhan.red.addFragment.Add
import com.infinity.ishkhan.red.heartFragment.Heart
import com.infinity.ishkhan.red.listFragment.List
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragments = arrayOf(Heart(), Add(), List())
    private var prevIndex = 0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                switchFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                switchFragment(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        switchFragment(0)
    }

    private fun switchFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()

        // if the fragment has not yet been added to the container, add it first
        if (!fragments[index].isAdded)
            transaction.add(R.id.container, fragments[index])

        transaction.hide(fragments[prevIndex])
        transaction.show(fragments[index])
        transaction.commit()
        prevIndex = index
    }
}
