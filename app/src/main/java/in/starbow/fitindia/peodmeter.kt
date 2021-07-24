package `in`.starbow.fitindia

import `in`.starbow.fitindia.callback.stepsCallback
import `in`.starbow.fitindia.helper.GeneralHelper
import `in`.starbow.fitindia.service.StepDetectorService
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.pedometer.*

class peodmeter : AppCompatActivity(), stepsCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pedometer)
        checkPedometerPermission()
    }

    override fun subscribeSteps(steps: Int) {
        TV_STEPS.setText(steps.toString())
        TV_CALORIES.setText(GeneralHelper.getCalories(steps))
        TV_DISTANCE.setText(GeneralHelper.getDistanceCovered(steps))
    }

    private fun startPedometerService() {
        val intent = Intent(this, StepDetectorService::class.java)
        startService(intent)
        StepDetectorService.subscribe.register(this)
    }

    private fun checkPedometerPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
            } else {
                startPedometerService()
            }
        } else {
            startPedometerService()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startPedometerService()
        } else {
            Toast.makeText(
                applicationContext,
                "Permission needed for pedometer to work",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}