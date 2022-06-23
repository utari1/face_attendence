package com.satria.uas.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.satria.uas.Adapters.RekapAdapter
import com.satria.uas.Models.RekapModel
import com.satria.uas.R
import com.satria.uas.Util.ApiRetrofit
import kotlinx.android.synthetic.main.activity_rekap.*
import kotlinx.android.synthetic.main.activity_rekap.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RekapActivity : AppCompatActivity() {

    private val api by lazy { ApiRetrofit().endpoint }
    private lateinit var rekapAdapter: RekapAdapter
    private lateinit var rvHistoryMasuk :RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rekap)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Rekap Tamu"

        setupList()
    }

    override fun onStart() {
        super.onStart()
        getHistoryMasuk()
    }

    private fun setupList(){
        rvHistoryMasuk = rv_hist_masuk
        rekapAdapter = RekapAdapter(arrayListOf())
        rvHistoryMasuk.adapter = rekapAdapter
    }
    private fun getHistoryMasuk(){
        api.dataHistoryMasuk().enqueue(object: Callback<RekapModel>{
            override fun onResponse(call: Call<RekapModel>, response: Response<RekapModel>) {
                if (response.isSuccessful){
                    val listHistoryMasuk = response.body()!!.data_tamu
                    rekapAdapter.setData(listHistoryMasuk)
                }
            }
            override fun onFailure(call: Call<RekapModel>, t: Throwable) {
                Log.e("RecapActivity",t.toString())
            }

        })
    }
}