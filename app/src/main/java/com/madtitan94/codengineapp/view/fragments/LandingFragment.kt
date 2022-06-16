package com.madtitan94.codengineapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.madtitan94.codengineapp.R
import com.madtitan94.codengineapp.databinding.FragmentHomeBinding
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.repository.TestRepository
import com.madtitan94.codengineapp.utils.CodeEngineApplication
import com.madtitan94.codengineapp.utils.ProductCategory
import com.madtitan94.codengineapp.view.adapters.ProductsAdapter
import com.madtitan94.codengineapp.viewmodel.LandingViewModel
import com.madtitan94.codengineapp.viewmodel.LandingViewModelFactory

class LandingFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val landingViewModel : LandingViewModel by viewModels {
            LandingViewModelFactory((activity?.application as CodeEngineApplication).prodRepository)
        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.tabs.addTab(binding.tabs.newTab().setText(ProductCategory.BURGER.category).setIcon(R.drawable.burger1))
        binding.tabs.addTab(binding.tabs.newTab().setText(ProductCategory.SANDWITCH.category).setIcon(R.drawable.sandwich1))
        binding.tabs.addTab(binding.tabs.newTab().setText(ProductCategory.ICECREAM.category).setIcon(R.drawable.ice1))
        binding.tabs.addTab(binding.tabs.newTab().setText(ProductCategory.BEVERAGES.category).setIcon(R.drawable.coke))

        val recyclerView = binding.recyclerview
        val adapter = ProductsAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity,2)



        landingViewModel.getProductList().observe(this, Observer { products ->
            //Log.e("PRODUCTS ARE ","--"+products?.size)
            products?.let { adapter.submitList(it) }
        })

        binding.tabs.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                Toast.makeText(activity, "Tab " +tab?.text, Toast.LENGTH_SHORT).show();
                landingViewModel.getProductByCategory(tab.text.toString())

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}