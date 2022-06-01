package com.example.okhttptest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val okHttpClient = OkHttpClient()
    private var heroList = listOf<HeroInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadHeroInfo()
        while (heroList.isEmpty()) {
            continue
        }
        initRecyclerViewHero()
    }

    private fun loadHeroInfo() {
        val request = Request.Builder()
            .url(BASEURL)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val json: String = response.body.string()
                val moshi = Moshi.Builder().build()
                val listType = Types.newParameterizedType(List::class.java, HeroInfo::class.java)
                val adapter: JsonAdapter<List<HeroInfo>> = moshi.adapter(listType)
                heroList = adapter.fromJson(json)!!
            }

            override fun onFailure(call: Call, e: IOException) {
            }
        })
    }

    private fun initRecyclerViewHero() {
        recyclerViewHero.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerViewHero.adapter = HeroAdapter(object : HeroActionListener {
            override fun onHeroDetail(heroInfo: List<HeroInfo>, position: Int) {
                val intentHero = Intent(this@MainActivity, HeroDetailActivity::class.java)
                intentHero.putExtra(HERO_ID_KEY, position)
                val bundle = bundleOf(
                    HERO_LIST_KEY to heroInfo
                )
                intentHero.putExtras(bundle)

                startActivity(intentHero)
            }
        }, heroList)
    }

    companion object{
        const val BASEURL = "https://api.opendota.com/api/heroStats"
        const val HERO_LIST_KEY = "heroList"
        const val HERO_ID_KEY = "id"
    }
}