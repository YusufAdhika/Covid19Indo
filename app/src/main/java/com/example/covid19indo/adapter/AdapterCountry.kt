package com.example.covid19indo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.covid19indo.R
import com.example.covid19indo.model.DataItem
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.list_country.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterCountry(
    private val provinsi: ArrayList<DataItem>,
    private val clickListener:(DataItem) -> Unit ) :
    RecyclerView.Adapter<CountryViewHolder>(), Filterable {

    var provinsilist = ArrayList<DataItem>()
    init {
        provinsilist = provinsi
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return provinsilist.size

    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(provinsilist[position],clickListener)
    }
    override fun getFilter(): Filter{
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                provinsilist = if (charSearch.isEmpty()){
                    provinsi
                } else{
                    val resultList = ArrayList<DataItem>()
                    for (row in provinsi){
                        val search = row.provinsi?.toLowerCase(Locale.ROOT) ?: ""
                        if (search.contains(charSearch.toLowerCase(Locale.ROOT))){
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                var filterResults = FilterResults()
                filterResults.values = provinsilist
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                provinsilist = results?.values as ArrayList<DataItem>
                notifyDataSetChanged()
            }
        }
    }
}

class CountryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(negara: DataItem, clickListener: (DataItem) -> Unit) {
        val namaProvinsi: TextView = itemView.tv_ProvinsiName
        val TotalCovid: TextView = itemView.tv_totalCovid
        val TotalSembuh: TextView = itemView.tv_provinsiSembuh
        val TotalKematian: TextView = itemView.tv_totalKematian

        val formatter: NumberFormat = DecimalFormat("#,###")

        namaProvinsi.tv_ProvinsiName.text = negara.provinsi
        TotalCovid.tv_totalCovid.text = formatter.format(negara.kasusPosi?.toDouble())
        TotalSembuh.tv_provinsiSembuh.text = formatter.format(negara.kasusSemb?.toDouble())
        TotalKematian.tv_totalKematian.text = formatter.format(negara.kasusMeni?.toDouble())

    }

}
