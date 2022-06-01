package com.example.okhttptest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import kotlinx.android.synthetic.main.activity_hero_info.*

class HeroDetailActivity: AppCompatActivity() {
    private var heroInfo = listOf<HeroInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_info)

        loadHeroDetail()

    }
    private fun loadHeroDetail(){
        heroInfo = this.intent.extras?.getParcelableArrayList(HERO_LIST_KEY)!!
        val count = intent.getIntExtra(HERO_ID_KEY,0)
        val baseURl = "https://api.opendota.com${heroInfo[count].img}"
        imageViewHero.load(baseURl)
        textViewNameHero.text = heroInfo[count].localized_name
        textViewAgi.text = heroInfo[count].agi_gain.toString()
        textViewStr.text = heroInfo[count].str_gain.toString()
        textViewInt.text = heroInfo[count].int_gain.toString()
        textViewSpeed.text = heroInfo[count].move_speed.toString()
        textViewRange.text = heroInfo[count].attack_range.toString()
        textViewHealth.text = heroInfo[count].base_health.toString()
        textViewMana.text = heroInfo[count].base_mana.toString()
        val attack = heroInfo[count].base_attack_min + heroInfo[count].base_attack_max
        textViewBaseAttack.text = attack.toString()
        textViewPrimaryAttr.text = when(heroInfo[count].primary_attr) {
            getString(R.string.str_reduction) -> getString(R.string.strength_text)
            getString(R.string.agi_reduction) -> getString(R.string.agility_text)
            getString(R.string.int_reduction) -> getString(R.string.intelligence_text)
            else -> {
                getString(R.string.error_text)
            }
        }
    }

    companion object{
        const val HERO_LIST_KEY = "heroList"
        const val HERO_ID_KEY = "id"
    }
}