package com.belajar.githubusernavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belajar.githubusernavigation.data.adapter.SectionPageAdapter
import com.belajar.githubusernavigation.data.adapter.UserAdapter
import com.belajar.githubusernavigation.data.response.ItemsItem
import com.belajar.githubusernavigation.databinding.FragmentFollowingsFollowersBinding
import com.belajar.githubusernavigation.ui.viewmodel.DetailedViewModel


class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowingsFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingsFollowersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(), ViewModelProvider.AndroidViewModelFactory()
        )[DetailedViewModel::class.java]
        val position = arguments?.getInt(SectionPageAdapter.SECTION_NO)

        if (position == 0) {
            viewModel.followings.observe(viewLifecycleOwner) {
                recyclerViewTab(it)
                binding.rvContainerTab.recycledViewPool
            }
            viewModel.loading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        } else {
            viewModel.followers.observe(viewLifecycleOwner) {
                recyclerViewTab(it)
                binding.rvContainerTab.recycledViewPool
                viewModel.loading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
            }
        }

    }

    private fun recyclerViewTab(items: List<ItemsItem>) {
        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = UserAdapter()
        val rvContainerTab = requireView().findViewById<RecyclerView>(R.id.rv_container_tab)
        rvContainerTab.layoutManager = layoutManager
        rvContainerTab.adapter = adapter
        adapter.setUserList(items)
    }

    private fun showLoading(value: Boolean) {
        if (value) binding.progressBar.visibility =
            View.VISIBLE else binding.progressBar.visibility = View.INVISIBLE
    }

}