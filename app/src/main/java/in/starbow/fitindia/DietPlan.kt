package `in`.starbow.fitindia

import `in`.starbow.fitindia.adapter.DietPlanAdapter
import `in`.starbow.fitindia.databinding.ActivityDietPlansBinding
import `in`.starbow.fitindia.model.Diet
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.*

class DietPlan : AppCompatActivity() {
    var binding: ActivityDietPlansBinding? = null
    var dietList: ArrayList<Diet>? = null
    var adapter: DietPlanAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDietPlansBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        dietList = ArrayList<Diet>()
        createDietList()
        adapter = DietPlanAdapter(this, dietList!!)
        binding!!.recyclerViewDiets.itemAnimator = DefaultItemAnimator()
        binding!!.recyclerViewDiets.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding!!.recyclerViewDiets.adapter = adapter
        binding!!.backBtn.setOnClickListener { view: View? -> finish() }
    }

    private fun createDietList() {
        dietList!!.add(
            Diet(
                "Intermittent fasting",
                resources.getString(R.string.intermittent_fast),
                R.drawable.intermittent_fast,
                "Diabetes"
            )
        )
        dietList!!.add(
            Diet(
                "Plant-based diets",
                resources.getString(R.string.plant_based_diets),
                R.drawable.plant_based_diets,
                "Diabetes"
            )
        )
        dietList!!.add(
            Diet(
                "Low-carb diets",
                resources.getString(R.string.low_carb_diets),
                R.drawable.low_carb_diets,
                "Diabetes"
            )
        )
        dietList!!.add(
            Diet(
                "The Paleo diet",
                resources.getString(R.string.the_paleo_diet),
                R.drawable.the_paleo_diet,
                "ABC"
            )
        )
        dietList!!.add(
            Diet(
                "Low-fat diets",
                resources.getString(R.string.low_fat_diet),
                R.drawable.low_fat_diet,
                "Diabetes"
            )
        )
        dietList!!.add(
            Diet(
                "The Mediterranean diet",
                resources.getString(R.string.the_mediterranean_diet),
                R.drawable.the_mediterranean_diet,
                "Diabetes"
            )
        )
        dietList!!.add(
            Diet(
                "WW (Weight Watchers)",
                resources.getString(R.string.the_weight_watcher),
                R.drawable.the_weight_watcher,
                "Diabetes"
            )
        )
        dietList!!.add(
            Diet(
                "The DASH diet",
                resources.getString(R.string.the_dash_diet),
                R.drawable.the_dash_diet,
                "Diabetes"
            )
        )
    }
}