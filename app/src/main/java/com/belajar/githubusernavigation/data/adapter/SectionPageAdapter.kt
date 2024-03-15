package com.belajar.githubusernavigation.data.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.belajar.githubusernavigation.FollowersFragment

class SectionPageAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    companion object {
        const val SECTION_NO = "section_no"
    }


    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        val fragment = FollowersFragment()
        val bundle = Bundle()
        fragment.arguments = bundle.apply {
            putInt(SECTION_NO, position)
        }


        return fragment
    }
}