package com.rizal.sqlitekotlin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.rizal.sqlitekotlin.R
import com.rizal.sqlitekotlin.model.EmployeeModel
import java.util.*
import kotlin.collections.ArrayList

class EmployeeAdapter: RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>(), Filterable {

    private var stdList: ArrayList<EmployeeModel> = ArrayList()

    private var stringList: ArrayList<String> = ArrayList()

    private var onClickItem: ((EmployeeModel) -> Unit)? = null
    private var onClickDeleteItem: ((EmployeeModel) -> Unit)? = null

    var employeeFilterList = ArrayList<String>()

    init {
        employeeFilterList = stringList
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems (items: ArrayList<EmployeeModel>) {
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem (callback: (EmployeeModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem (callback: (EmployeeModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EmployeeViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.layout_card_murid, parent, false)
    )

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)

        holder.itemView.setOnClickListener{ onClickItem?.invoke(std) }
        holder.button.setOnClickListener { onClickDeleteItem?.invoke(std) }
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    employeeFilterList = stringList
                } else {
                    val resultList = ArrayList<String>()
                    for (row in employeeFilterList) {
                        if (row.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    employeeFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = employeeFilterList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                employeeFilterList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }

    class EmployeeViewHolder (var view: View): RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.text_id)
        private var username = view.findViewById<TextView>(R.id.text_username)
        private var fullname = view.findViewById<TextView>(R.id.text_fullname)
        private var email = view.findViewById<TextView>(R.id.text_email)
        private var salary = view.findViewById<TextView>(R.id.text_salary)
        private var gender = view.findViewById<TextView>(R.id.text_gender)
        private var region = view.findViewById<TextView>(R.id.text_region)
        private var address = view.findViewById<TextView>(R.id.text_address)

        var button: MaterialButton = view.findViewById(R.id.btn_hapus)

        fun bindView (std: EmployeeModel) {

            std.let {
                id.text = it.id.toString()
            }

            id.text = std.id.toString()
            username.text = std.username
            fullname.text = std.fullname
            email.text = std.email
            salary.text = std.salary
            gender.text = std.gender
            region.text = std.region
            address.text = std.address
        }
    }
}