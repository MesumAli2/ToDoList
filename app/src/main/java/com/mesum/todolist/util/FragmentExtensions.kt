package com.mesum.todolist.util


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController

@SuppressLint("CommitTransaction")
fun FragmentManager.addFragment(fragment: Fragment, frameId: Int) {
    beginTransaction().run {
        add(frameId, fragment)
        commit()
    }
}

fun Fragment.setupToolbarWithNavigation(
    toolbarId: Int,
    title: String?,
    displayHomeAsUpEnabled: Boolean = true
) {
    val toolbar = view?.findViewById<Toolbar>(toolbarId)
    val appCompatActivity = activity as? AppCompatActivity

    appCompatActivity?.apply {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
        supportActionBar?.title = title
    }

    toolbar?.setNavigationOnClickListener {
        findNavController().navigateUp()
    }
}
