package com.unpa.calificaciones

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.unpa.calificaciones.fragmentViews.CalificacionesFragment
import com.unpa.calificaciones.fragmentViews.NotificacionFragment
import com.unpa.calificaciones.fragmentViews.PerfilFragment


class LayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val fragments = listOf(
            NotificacionFragment() ,
            CalificacionesFragment(),
            PerfilFragment(),
        )
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = fragments.size
            override fun createFragment(pos: Int) = fragments[pos]
        }
        viewPager.offscreenPageLimit = fragments.size
        // … después de ajustar offscreenPageLimit
        viewPager.offscreenPageLimit = fragments.size

        // **Fijar al iniciar que muestre Calificaciones (índice 1)**
        viewPager.setCurrentItem(1, false)
        bottomNav.selectedItemId = R.id.nav_calificaciones
        // Swipe -> menú
        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    bottomNav.selectedItemId = when (position) {
                        0 -> R.id.nav_notificaciones
                        1 -> R.id.nav_calificaciones
                        2 -> R.id.nav_perfil
                        else -> R.id.nav_calificaciones
                    }
                }
            }
        )

        // Menú -> ViewPager
        bottomNav.setOnItemSelectedListener { item ->
            viewPager.currentItem = when (item.itemId) {
                R.id.nav_notificaciones -> 0
                R.id.nav_calificaciones -> 1
                R.id.nav_perfil -> 2

                else -> 1
            }
            true
        }



    }
}