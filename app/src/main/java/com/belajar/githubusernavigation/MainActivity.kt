package com.belajar.githubusernavigation

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.githubusernavigation.data.adapter.UserAdapter
import com.belajar.githubusernavigation.data.response.ItemsItem
import com.belajar.githubusernavigation.databinding.ActivityMainBinding
import com.belajar.githubusernavigation.ui.viewmodel.MainAndSearchViewModel



class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "mainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainAndSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            v.paddingBottom
            insets
        }

        supportActionBar?.hide()

        binding.rvContainer.layoutManager = LinearLayoutManager(this)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    val fragmentManager = supportFragmentManager
                    val searchFragment = SearchFragment()
                    fragmentManager.commit {
                        addToBackStack(TAG)
                        replace(
                            R.id.fragment_container,
                            searchFragment,
                            SearchFragment::class.java.simpleName
                        )
                        binding.rvContainer.visibility = View.GONE
                    }
                    true
                }

                R.id.menu_view -> {
                    if (binding.rvContainer.layoutManager is GridLayoutManager) {
                        binding.rvContainer.layoutManager = LinearLayoutManager(this)
                    } else {
                        binding.rvContainer.layoutManager = GridLayoutManager(this, 2)
                    }
                    binding.rvContainer.adapter?.notifyDataSetChanged()
                    true
                }

                else -> false
            }
        }

        viewModel.data.observe(this) {
            setRecyclerView(it)
        }
        viewModel.loading.observe(this) {
            showLoading(it)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setRecyclerView(items: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.setUserList(items)
        binding.rvContainer.adapter = adapter
    }

    private fun showLoading(value: Boolean) {
        if (value) binding.progressBar.visibility =
            View.VISIBLE else binding.progressBar.visibility = View.INVISIBLE
    }
}