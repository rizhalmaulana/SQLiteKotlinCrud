package com.rizal.sqlitekotlin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.rizal.sqlitekotlin.R
import com.rizal.sqlitekotlin.model.StudentModel

class StudentAdapter: RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var stdList: ArrayList<StudentModel> = ArrayList()
    private var onClickItem: ((StudentModel) -> Unit)? = null
    private var onClickDeleteItem: ((StudentModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun addItems (items: ArrayList<StudentModel>) {
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem (callback: (StudentModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem (callback: (StudentModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.layout_card_murid, parent, false)
    )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)

        holder.itemView.setOnClickListener{ onClickItem?.invoke(std) }
        holder.button.setOnClickListener { onClickDeleteItem?.invoke(std) }
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class StudentViewHolder (var view: View): RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.text_id)
        private var nama = view.findViewById<TextView>(R.id.text_nama)
        private var email = view.findViewById<TextView>(R.id.text_email)
        var button = view.findViewById<MaterialButton>(R.id.btn_hapus)

        fun bindView (std: StudentModel) {
            id.text = std.id.toString()
            nama.text = std.name
            email.text = std.email
        }
    }
}