package com.it.demoapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.it.demoapp.R
import com.it.demoapp.view.fragments.ListFragments

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragmentToActivity(ListFragments())
//        replaceFragment(ListFragments(), "ListFragments")
    }

    private fun addFragmentToActivity(fragment: Fragment?){
        if (fragment == null) return
        val fm = supportFragmentManager
        val tr = fm.beginTransaction()
        tr.add(R.id.navHostFragment, fragment)
        tr.commitAllowingStateLoss()
    }



    private fun replaceFragment(fragment: Fragment, fragmentTag: String) {
        try {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.navHostFragment, fragment, fragmentTag)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }
}