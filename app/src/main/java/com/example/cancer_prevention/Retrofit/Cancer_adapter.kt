package com.example.cancer_prevention.Retrofit

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.CancerHolderBinding

class Cancer_adapter(var myList: List<data> = ArrayList<data>()) : RecyclerView.Adapter<Cancer_adapter.MyViewHolder>(), Filterable{


    private var userList = emptyList<data>()


    class MyViewHolder(val binding: CancerHolderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cancer_adapter.MyViewHolder {
        val binding = CancerHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    //서치뷰

    var TAG = "PhoneBookListAdapter"

    var filteredPersons = ArrayList<data>()
    var itemFilter = ItemFilter()


    //


    override fun onBindViewHolder(holder: Cancer_adapter.MyViewHolder, position: Int) {

        holder.binding.CancerHolder1.text = myList[position].Cancer_number
        holder.binding.CancerHolder2.text = myList[position].Cancer_number2
        holder.binding.CancerHolder3.text = myList[position].Cancer_number3
        holder.binding.CancerHolder4.text = myList[position].Cancer_number4
        holder.binding.CancerHolder5.text = myList[position].Cancer_number5



        /*

        holder.binding.CancerHolder1.text = filteredPersons[position].Cancer_number
        holder.binding.CancerHolder2.text = filteredPersons[position].Cancer_number2
        holder.binding.CancerHolder3.text = filteredPersons[position].Cancer_number3
        holder.binding.CancerHolder4.text = filteredPersons[position].Cancer_number4
        holder.binding.CancerHolder5.text = filteredPersons[position].Cancer_number5

         */
    }


    fun setData(newList : List<data>){
        filteredPersons = newList as ArrayList<data>
        // 새로고침
        notifyDataSetChanged()
    }

    init {
        filteredPersons.addAll(myList)
    }


    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()
            Log.d(TAG, "charSequence : $charSequence")

            //검색이 필요없을 경우를 위해 원본 배열을 복제
            val filteredList: ArrayList<data> = ArrayList<data>()
            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = myList
                results.count = myList.size

                return results
                //공백제외 2글자 이인 경우 -> 이름으로만 검색
            } else if (filterString.trim { it <= ' ' }.length <= 2) {
                for (person in myList) {
                    if (person.Cancer_number.contains(filterString)) {
                        filteredList.add(person)
                    }
                }
                //그 외의 경우(공백제외 2글자 초과) -> 이름/전화번호로 검색
            } else {
                for (person in myList) {
                    if (person.Cancer_number.toString().contains(filterString) || person.Cancer_number2.contains(filterString)) {
                        filteredList.add(person)
                    }
                }
            }
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredPersons.clear()
            filteredPersons.addAll(filterResults.values as ArrayList<data>)
            notifyDataSetChanged()
        }
    }

    class ViewHolder (itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val Cancer_holer1 = itemView?.findViewById<TextView>(R.id.Cancer_holder1)
        val Cancer_holer2 = itemView?.findViewById<TextView>(R.id.Cancer_holder2)
        val Cancer_holer3 = itemView?.findViewById<TextView>(R.id.Cancer_holder3)
        val Cancer_holer4 = itemView?.findViewById<TextView>(R.id.Cancer_holder4)
        val Cancer_holer5 = itemView?.findViewById<TextView>(R.id.Cancer_holder5)
    }


    override fun getItemCount(): Int {
        return filteredPersons.size
    }


}