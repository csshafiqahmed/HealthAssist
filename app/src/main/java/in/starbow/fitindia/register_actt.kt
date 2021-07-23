package `in`.starbow.fitindia

import `in`.starbow.fitindia.databinding.ActivityRegisterActtBinding
import `in`.starbow.fitindia.dialogs.DatePickerFragment
import `in`.starbow.fitindia.helper.AppPrefs
import `in`.starbow.fitindia.helper.PrefsHelper
import `in`.starbow.fitindia.model.User
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register_act.*
import java.text.DateFormat
import java.util.*

class register_actt : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var auth: FirebaseAuth
    var binding: ActivityRegisterActtBinding? = null
    lateinit var goalList: Array<String>
    lateinit var diseaseList: Array<String>
    var goal = ""
    var disease = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterActtBinding.inflate(getLayoutInflater())
        setContentView(binding!!.root)
        auth = FirebaseAuth.getInstance()
        goalList = getResources().getStringArray(R.array.goal)
        diseaseList = getResources().getStringArray(R.array.disease)
        binding!!.spinnerGoal.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                goal = goalList[i]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
        binding!!.spinnerDisease.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                disease = diseaseList[i]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
        binding!!.etDob.setOnClickListener { view ->
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(getSupportFragmentManager(), "Date Picker")
        }
        binding!!.radioGroupGender.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioGroup: RadioGroup, i: Int) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.radio_male) {
                    binding!!.linearPregnant.visibility = View.GONE
                } else {
                    binding!!.linearPregnant.visibility = View.VISIBLE
                }
            }
        })
        signup_to_home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding!!.registerBtn.setOnClickListener { view -> signUpUser() }
    }

    fun signUpUser() {
        val name = binding!!.etName.text.toString().trim()
        val dob = binding!!.etDob.text.toString().trim()
        var gender = ""
        var isPregnant: Boolean
        isPregnant = binding!!.radioGroupPreg.checkedRadioButtonId === R.id.radio_preg_yes
        if (binding!!.radioGroupGender.checkedRadioButtonId === R.id.radio_male) {
            gender = "male"
            isPregnant = false
        } else {
            gender = "female"
        }
        val weight = binding!!.etWeight.text.toString().trim()
        val height = binding!!.etHeight.text.toString().trim()
        val email = binding!!.registerEmail.text.toString().trim()
        val password = binding!!.registerPassw.text.toString().trim()
        var isError = false
        if (name == "" || name.length <= 2) {
            isError = true
            binding!!.etName.error = "Enter full name"
        } else if (dob == "") {
            isError = true
            binding!!.etDob.error = "Enter date of birth"
        } else if (weight == "") {
            isError = true
            binding!!.etWeight.error = "Enter weight"
        } else if (height == "") {
            isError = true
            binding!!.etHeight.error = "Enter height"
        } else if (email == "") {
            isError = true
            binding!!.registerEmail.error = "Enter email address"
        } else if (password == "") {
            isError = true
            binding!!.registerPassw.error = "Enter password"
        }
        if (!isError) {
            val appUser =
                User(name, dob, gender, weight, height, goal, disease, isPregnant, email, password)
            Log.d("Register", "User: $appUser")
            auth.createUserWithEmailAndPassword(registerEmail.text.toString().trim(),register_passw.text.toString().trim())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Tag1", "createUserWithEmail:success")
                        Toast.makeText(this, "Authentication Done.",
                            Toast.LENGTH_SHORT).show()

                        val user : FirebaseUser? = auth.currentUser
                        user!!.sendEmailVerification()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    AppPrefs.setUserData(appUser)
                                    Log.d("Email Tag", "Email sent.")
                                    startActivity(Intent(this, loginPage::class.java))
                                    finish()
                                }
                            }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Tag2", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }

                    // ...
                }
        }
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val c = Calendar.getInstance()
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month
        c[Calendar.DAY_OF_MONTH] = dayOfMonth
        val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
        binding!!.etDob.setText(currentDateString)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null) {
            startActivity(Intent(this, dashboard::class.java))
            finish()
        }
    }
}