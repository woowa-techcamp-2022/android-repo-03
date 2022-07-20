package com.example.gitreposearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitreposearch.R
import com.example.gitreposearch.data.Issue
import com.example.gitreposearch.databinding.RvIssueRowBinding
import com.example.gitreposearch.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class IssueListRecyclerViewAdapter() : RecyclerView.Adapter<IssueListRecyclerViewAdapter.ViewHolder>() {
    private var dataSet =listOf<Issue>()

    class ViewHolder(private val binding : RvIssueRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Issue){
            with(binding){
                issue = item
                setIssueStateImage(item.state.toString())
                executePendingBindings() // 강제 결합시키기
                setRepositoryUrl(item.repository_url.toString())
                setUpdatedTime(item.updated_at.toString())

            }
        }

        private fun setUpdatedTime(updated_at : String) {
            val updateTime = updated_at.substring(0 until 10) +" " + updated_at.substring(11 until 19)
            val sf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val date = sf.parse(updateTime)
            val currentTime = Calendar.getInstance()
            val timeDiff = (currentTime.time.time - date.time) / (60*60*24*1000)
            with(binding) {
                if(timeDiff > 30){
                    issueDateTv.text = (timeDiff /30 ).toString() + "달 전"
                }
                else {
                    issueDateTv.text = timeDiff.toString() +"일 전"
                }

            }
        }
        private fun setRepositoryUrl(repoUrl : String){
            val baseUrl = Constants.githubBaseUrl + "repos/"
            binding.issueRepoTv.text = repoUrl.substring(baseUrl.length until repoUrl.length)
        }
        private fun setIssueStateImage(state : String) {
            with(binding){
                when(state) {
                    "open" -> {
                        issueStateIv.setImageResource(R.drawable.ic_issue_open)
                    }
                    "closed" -> {
                        issueStateIv.setImageResource(R.drawable.ic_issue_closed)
                    }
                }
            }
        }

    }
    fun setData(data : List<Issue>){
        dataSet = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvIssueRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}