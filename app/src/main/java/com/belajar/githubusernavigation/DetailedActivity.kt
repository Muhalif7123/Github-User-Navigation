package com.belajar.githubusernavigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.belajar.githubusernavigation.data.adapter.SectionPageAdapter
import com.belajar.githubusernavigation.data.response.DataDetail
import com.belajar.githubusernavigation.databinding.ActivityDetailedBinding
import com.belajar.githubusernavigation.ui.viewmodel.DetailedViewModel
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailedActivity : AppCompatActivity() {

    private val viewModel: DetailedViewModel by viewModels()
    private lateinit var binding: ActivityDetailedBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val apiId = intent.getStringExtra("id")
        viewModel.getDataComplete(apiId)
        viewModel.getFollowers(apiId)
        viewModel.getFollowings(apiId)
        viewModel.dataDetail.observe(this) {
            setDataComplete(it)
        }
        viewModel.loading.observe(this) {
            showLoading(it)
        }


        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
            supportActionBar!!
        }

        val sectionPageAdapter = SectionPageAdapter(this)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.viewpager)
        viewPager.adapter = sectionPageAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(SECTION_TITLE[position])
            Log.d("TabLayoutDebug", "Setting tab text: ${tab.text} at position: $position")
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    private fun setDataComplete(dataDetail: DataDetail) {
        binding.tvNameDetailed.text = dataDetail.name
        binding.tvUsernameDetailed.text = dataDetail.login
        Glide.with(this@DetailedActivity)
            .load(dataDetail.avatarUrl)
            .into(binding.ivDetailedPicture)

        binding.tvFollowers.text =
            this@DetailedActivity.resources.getString(R.string.followers, dataDetail.followers)
        binding.tvFollowings.text =
            this@DetailedActivity.resources.getString(R.string.followings, dataDetail.following)

        binding.tvCompany.text = dataDetail.company
        binding.toolbar.title = dataDetail.login

        val menuOpen = findViewById<View>(R.id.menu_open)
        menuOpen.setOnClickListener {
            val open = Intent(Intent.ACTION_VIEW, Uri.parse(dataDetail.htmlUrl))
            startActivity(open)
        }

    }

    private fun showLoading(value: Boolean) {
        if (value) binding.progressBar.visibility =
            View.VISIBLE else binding.progressBar.visibility = View.INVISIBLE
    }
    companion object {
        @StringRes
        private val SECTION_TITLE = intArrayOf(R.string.following, R.string.follower)
    }
}