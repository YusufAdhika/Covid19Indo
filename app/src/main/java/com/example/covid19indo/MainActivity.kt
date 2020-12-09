package com.example.covid19indo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19indo.adapter.AdapterCountry
import com.example.covid19indo.model.DataItem
import com.example.covid19indo.model.ResponseCountry
import com.example.covid19indo.network.ApiService
import com.example.covid19indo.network.RetrofitBuilder.retrofit
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var adapterProvinsi: AdapterCountry
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapterProvinsi.filter.filter(newText)
                return false
            }
        })
        swipe_refresh.setOnRefreshListener {
            getProvinsi()
            swipe_refresh.isRefreshing = false
        }
        getProvinsi()
    }
    private fun getProvinsi(){
        val api = retrofit.create(ApiService::class.java)
        api.getAllNegara().enqueue(object : Callback<ResponseCountry>{
            override fun onFailure(call: Call<ResponseCountry>, t: Throwable) {
                progress_bar.visibility = View.GONE
            }
            override fun onResponse(
                call: Call<ResponseCountry>,
                response: Response<ResponseCountry>
            ) {
                if (response.isSuccessful){
                    rv_provinsi.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        progress_bar.visibility = View.GONE
                        adapterProvinsi = AdapterCountry(
                            response.body()!!.data as ArrayList<DataItem>
                        ){}
                        adapter = adapterProvinsi
                    }
                }else{
                    progress_bar?.visibility = View.GONE
                }
            }
        })
    }
}
