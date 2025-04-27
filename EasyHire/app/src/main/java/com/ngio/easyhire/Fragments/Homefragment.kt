package com.ngio.easyhire.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ngio.easyhire.Adapters.HomeCarouselAdapter
import com.ngio.easyhire.Adapters.ImageAdapter
import com.ngio.easyhire.R
import com.ngio.easyhire.Activities.MoreElementsActivity
import com.ngio.easyhire.Models.home_carrousel_model
import kotlin.math.abs

class Homefragment : Fragment() {
    private lateinit var carouselAdapter_cat: HomeCarouselAdapter
    private lateinit var carousel_cat: List<home_carrousel_model>
    private lateinit var carouselAdapter_1: HomeCarouselAdapter
    private lateinit var carousel_1: List<home_carrousel_model>
    private lateinit var carouselAdapter_2: HomeCarouselAdapter
    private lateinit var carousel_2: List<home_carrousel_model>

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var imageList: List<String>

    private var handler: Handler? = null
    private lateinit var runnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        //Encabezado
        val scrollView = view.findViewById<NestedScrollView>(R.id.nested_scroll)
        val appBarLayout = view.findViewById<AppBarLayout>(R.id.appBarLayout)
        val firstMenu = view.findViewById<LinearLayout>(R.id.first_menu)

        appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            val alpha = 1.0f - (abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange)
            firstMenu.alpha = alpha
        }

        // Carruseles horizontales
        val categories = view.findViewById<RecyclerView>(R.id.carrousel_categories)
        val near_you = view.findViewById<RecyclerView>(R.id.carrousel_1)
        val top_rated = view.findViewById<RecyclerView>(R.id.carrousel_2)

        //Botones de carrusel
        val more_categories: ImageButton = view.findViewById<ImageButton>(R.id.more_categories).apply {
            setOnClickListener{
                EnterElements("Mas categorias")
            }
        }
        val more_near_you: ImageButton = view.findViewById<ImageButton>(R.id.more_near_you).apply {
            setOnClickListener{
                EnterElements("Mas cerca de ti")
            }
        }

        // Inicialización de carruseles horizontales
        categories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        carousel_cat = get_categories()
        carouselAdapter_cat = HomeCarouselAdapter(carousel_cat,1)
        categories.adapter = carouselAdapter_cat

        near_you.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        carousel_1 = get_carrousel_1()
        carouselAdapter_1 = HomeCarouselAdapter(carousel_1,0)
        near_you.adapter = carouselAdapter_1

        top_rated.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        carousel_2 = get_carrousel_2()
        carouselAdapter_2 = HomeCarouselAdapter(carousel_2,0)
        top_rated.adapter = carouselAdapter_2

        // Configuración del ViewPager2 y TabLayout
        viewPager = view.findViewById(R.id.viewPagerCarousel)
        tabLayout = view.findViewById(R.id.tabLayoutIndicators)
        imageList = getImageUrls()
        imageAdapter = ImageAdapter(requireContext(), imageList)
        viewPager.adapter = imageAdapter
        viewPager.currentItem = 0

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.customView = ImageView(requireContext()).apply {
                setImageResource(if (position == 0) R.drawable.active_icon else R.drawable.inactive_icon)
            }
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                for (i in 0 until tabLayout.tabCount) {
                    val imageView = tabLayout.getTabAt(i)?.customView as? ImageView
                    imageView?.setImageResource(
                        if (i == position) R.drawable.active_icon else R.drawable.inactive_icon
                    )
                }
            }
        })
        return view
    }
    private fun EnterElements(element :String ){
                val intent = Intent(requireContext(), MoreElementsActivity::class.java).apply {
                    putExtra("element", element)
                }
            startActivity(intent)
        }

    // Método para obtener URLs de las imágenes desde la base de datos (simulado)
    private fun getImageUrls(): List<String> {
        return listOf(
            "https://i5.walmartimages.com/dfw/4ff9c6c9-e459/k2-_856031f4-3339-4141-b50e-f23a68eb8f8e.v1.jpg?odnHeight=318&odnWidth=568&odnBg=&odnDynImageQuality=70",
            "https://www.terra.com.mx/u/fotografias/m/2024/4/16/f768x1-110596_110723_5050.png",
            "https://res.cloudinary.com/spydeals/image/upload/w_475,f_auto,q_auto,c_limit/cdn/pZdyQDU1SGXYFaABqpciKvVdp5XIOx5c322SsJlt.png",
            "https://i5.walmartimages.com/dfw/4ff9c6c9-9cf4/k2-_befda269-bd17-4295-b360-da9a4bfa7046.v1.jpg"
        )
    }
    private fun get_categories(): List<home_carrousel_model>{
        return listOf(
            home_carrousel_model("Reparacion", "electronica", "https://cdn-icons-png.flaticon.com/512/181/181806.png" ),
            home_carrousel_model("Comida", "pizzeria", "https://images.vexels.com/media/users/3/157205/isolated/preview/5dd5e3530e81a4d5afdd883d27d43de2-icono-de-pizza-blanco-y-negro.png"),
            home_carrousel_model("Limpieza", "mantenimiento", "https://cdn-icons-png.flaticon.com/512/291/291402.png")
            )
    }
    private fun get_carrousel_1(): List<home_carrousel_model> {
        return listOf(
            home_carrousel_model("Barberia Alex", "Belleza", "https://static.vecteezy.com/system/resources/previews/006/685/315/non_2x/vintage-barbershop-logo-and-label-template-vector.jpg"),
            home_carrousel_model("Rosticeria El ranchero", "Comida rapida", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4WybtRUy2uPS0Mu9mXEtZS2nrD5dFkdfJ9A&s"),
            home_carrousel_model("Real", "veradero gay", "https://www.biografia.de/wp-content/uploads/Esen-Alvarado.jpg")
        )
    }

    private fun get_carrousel_2(): List<home_carrousel_model> {
        return listOf(
            home_carrousel_model("Pumbi", "panzon", "https://static.wikia.nocookie.net/disney/images/9/96/Pumbaa2.png/revision/latest?cb=20240611183205&path-prefix=es"),
            home_carrousel_model("Crood", "cavernicola", "https://i.pinimg.com/736x/b9/8a/2f/b98a2fb38a41df4e34ae1b85c20b543e.jpg"),
            home_carrousel_model("Real", "veradero gay", "https://www.biografia.de/wp-content/uploads/Esen-Alvarado.jpg")
        )
    }
    override fun onResume() {
        super.onResume()
        handler?.postDelayed(runnable, 5000)
    }
    override fun onPause() {
        super.onPause()
        handler?.removeCallbacks(runnable)
    }
}
