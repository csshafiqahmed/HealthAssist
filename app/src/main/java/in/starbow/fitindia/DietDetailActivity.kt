package `in`.starbow.fitindia

import `in`.starbow.fitindia.databinding.ActivityDietDetailBinding
import `in`.starbow.fitindia.model.Diet
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DietDetailActivity : AppCompatActivity() {
    var binding: ActivityDietDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDietDetailBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val diet: Diet? = intent.getSerializableExtra("diet") as Diet?
        if (diet != null) {
            binding!!.imgDiet.setImageResource(diet.image)
            binding!!.tvTitle.setText(diet.title)
            binding!!.tvDesc.setText(diet.desc)
        }
        binding!!.backBtn.setOnClickListener { view: View? -> finish() }
    }
}