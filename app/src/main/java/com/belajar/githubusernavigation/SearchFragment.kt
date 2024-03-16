package com.belajar.githubusernavigation

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.githubusernavigation.data.adapter.UserAdapter
import com.belajar.githubusernavigation.data.response.ItemsItem
import com.belajar.githubusernavigation.databinding.FragmentSearchBinding
import com.belajar.githubusernavigation.ui.viewmodel.MainAndSearchViewModel
import com.google.android.material.appbar.MaterialToolbar


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: MainAndSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { v, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    viewModel.getDataSearch(searchBar.text.toString())
                    viewModel.search.observe(viewLifecycleOwner) {
                        setRecyclerView(it)
                    }
                    true
                }
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setRecyclerView(items: List<ItemsItem>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvContainerSearch.layoutManager = layoutManager
        val adapter = UserAdapter()
        adapter.setUserList(items)
        binding.rvContainerSearch.adapter = adapter
    }

    private fun showLoading(value: Boolean) {
        if (value) binding.progressBar.visibility =
            View.VISIBLE else binding.progressBar.visibility = View.INVISIBLE
    }
}
