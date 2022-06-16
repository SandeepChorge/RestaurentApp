package com.madtitan94.codengineapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.madtitan94.codengineapp.databinding.FragmentGalleryBinding
import com.madtitan94.codengineapp.utils.CartManager.makeLog
import com.madtitan94.codengineapp.utils.CodeEngineApplication
import com.madtitan94.codengineapp.view.adapters.ProductsAdapter
import com.madtitan94.codengineapp.view.adapters.TransactionsAdapter
import com.madtitan94.codengineapp.viewmodel.LandingViewModel
import com.madtitan94.codengineapp.viewmodel.LandingViewModelFactory
import com.madtitan94.codengineapp.viewmodel.TransactionViewModelFactory
import com.madtitan94.codengineapp.viewmodel.TransactionsViewModel

class TransactionsFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val transactionsViewModel =
            ViewModelProvider(this).get(TransactionsViewModel::class.java)
*/
        val transactionsViewModel : TransactionsViewModel by viewModels {
            TransactionViewModelFactory((activity?.application as CodeEngineApplication).transactionRepository)
        }

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerview
        val adapter = TransactionsAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity,2)

/*
        val textView: TextView = binding.textGallery
        transactionsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        transactionsViewModel.getTransactions().observe(this, Observer {trans->
            makeLog("Total TRansction Size is "+trans.size)
            trans?.let { adapter.submitList(trans) }
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}