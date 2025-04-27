package com.ngio.easyhire.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ngio.easyhire.R

class ActivitiesFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var header: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_activities, container, false)
        header = view.findViewById(R.id.txt_current_section)

        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        // Adaptador para el ViewPager
        val adapter = ViewPagerAdapter(requireActivity())
        viewPager.adapter = adapter

        // Asociar iconos a las pestañas
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.messages_icon)
                1 -> tab.setIcon(R.drawable.order_icon)
                2 -> tab.setIcon(R.drawable.notifications_icon)
            }
        }.attach()

        // Configurar callback para cambios de página
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                header.text = when (position) {
                    0 -> "Mensajes"
                    1 -> "Pedidos"
                    2 -> "Notificaciones"
                    else -> ""
                }
            }
        })

        return view
    }
}
class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentOne()
            //1 -> FragmentTwo()
            //2 -> FragmentThree()
            else -> FragmentOne()
        }
    }
}



