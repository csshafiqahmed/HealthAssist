package `in`.starbow.fitindia

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val mAuth = FirebaseAuth.getInstance()
        Handler(Looper.getMainLooper()).postDelayed({
            if (mAuth.currentUser != null) {
                startActivity(Intent(this@SplashActivity, dashboard::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }, 2700)
    }
}