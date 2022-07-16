package com.example.gitreposearch.ui.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gitreposearch.adapter.NotificationRecyclerViewAdapter
import com.example.gitreposearch.databinding.FragmentNotificationBinding
import com.example.gitreposearch.viewmodel.MainViewModel


class NotificationFragment : Fragment() {
    val TAG = "NotificationFragment"
    private var binding: FragmentNotificationBinding? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var notificationRecyclerViewAdapter: NotificationRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading()
        initNotificationRecyclerView()
        initRefreshListener()
        getUserNotificationList()
        initObserve()
    }

    private fun initRefreshListener() {
        binding!!.layoutRefresh.setOnRefreshListener {
            getUserNotificationList()
        }
    }

    private fun showLoading() {
        with(binding!!){
            progressBarLoading.isGone = false
            tvLoading.isGone = false
        }
    }
    private fun hideLoading(){
        with(binding!!){
            progressBarLoading.isGone=true
            tvLoading.isGone = true
        }
    }

    private fun initNotificationRecyclerView() {
        notificationRecyclerViewAdapter = NotificationRecyclerViewAdapter()
        with(binding!!) {
            rcvNotificationList.layoutManager = LinearLayoutManager(activity)
            rcvNotificationList.adapter = notificationRecyclerViewAdapter
        }

    }

    private fun getUserNotificationList() {
        with(mainViewModel){
            val token = token.value
            if (token != null) {
                getNotificationList(token, true)
            }
        }
    }

    private fun initObserve() {
        mainViewModel.userNotificationList.observe(viewLifecycleOwner) { notificationList ->
            if(binding!!.layoutRefresh.isRefreshing){
                binding!!.layoutRefresh.isRefreshing = false
            }
            hideLoading()
            notificationRecyclerViewAdapter.setData(notificationList)

        }
    }
}