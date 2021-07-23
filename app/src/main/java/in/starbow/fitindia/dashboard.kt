package `in`.starbow.fitindia

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*


class dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        bmi.setOnClickListener {
            Toast.makeText(applicationContext, "BMI", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, `in`.starbow.fitindia.bmi::class.java)
            startActivity(intent);
        }
        pedometer.setOnClickListener {
            Toast.makeText(applicationContext, "Pedometer", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, peodmeter::class.java)
            startActivity(intent);
        }
        diets.setOnClickListener {
            Toast.makeText(applicationContext, "Diets", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DietPlan::class.java)
            startActivity(intent)
        }
        image_selector.setOnClickListener {
            val intent = Intent(this, FoodSelectActivity::class.java)
            startActivity(intent)
        }
        btn_logout.setOnClickListener{
            var auth: FirebaseAuth
            auth = FirebaseAuth.getInstance()
            auth.signOut()
            val intent = Intent(this, loginPage::class.java)
            startActivity(intent)
            finish()
        }
    }
}