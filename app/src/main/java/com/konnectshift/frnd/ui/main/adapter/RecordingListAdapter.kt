package com.konnectshift.frnd.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.konnectshift.frnd.R
import com.konnectshift.frnd.model.RecordingObject

class RecordingListAdapter(var mOnItemClickListener: OnItemClickListener) : ListAdapter<RecordingObject, RecordingListAdapter.ViewHolder>(RECORDING_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recordings, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.tvName.text = item.id.toString()
    }

    /**
     * The type View holder.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tvRecordingName)
        var btnPlay: Button = itemView.findViewById(R.id.btnPlay)

        init {
            btnPlay.setOnClickListener {
                mOnItemClickListener.onItemClick(adapterPosition, getItem(adapterPosition))
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: RecordingObject)
    }

    companion object {
        private val RECORDING_COMPARATOR = object : DiffUtil.ItemCallback<RecordingObject>() {
            override fun areItemsTheSame(oldItem: RecordingObject, newItem: RecordingObject): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RecordingObject, newItem: RecordingObject): Boolean =
                oldItem == newItem
        }
    }

}
