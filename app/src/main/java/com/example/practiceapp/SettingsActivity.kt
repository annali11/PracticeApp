package com.example.practiceapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.practiceapp.databinding.ActivitySettingsBinding
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity(), LanguageDialogOnClickListener {
    private lateinit var binding: ActivitySettingsBinding
    var selectedLang: String = ""
    var selectedLangCode: String = ""
    lateinit var listener: LanguageDialogOnClickListener
    private lateinit var settingsManager: SettingsManager

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let { context ->
            settingsManager = SettingsManager(context)
            val appLanguage = settingsManager.currentLanguage
            selectedLang = appLanguage.selectedLang
            selectedLangCode = appLanguage.selectedLangCode
            LanguageHelper.changeLanguage(context, selectedLangCode)

        }
        super.attachBaseContext(newBase)
    }

    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.selectedItemId = R.id.bottomSettings

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottomhome -> {
                    startActivity(
                        Intent(applicationContext, HomeActivity::class.java)
                    )
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottomAddNew -> {
                    val intent = Intent(applicationContext, AddNewActivity::class.java);
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottomProfile -> {
                    startActivity(
                        Intent(applicationContext, ProfileActivity::class.java)
                    )
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottomSettings -> {
                    return@setOnItemSelectedListener true
                }

                else -> false
            }
        }

        listener = this

        val languages = resources.getStringArray(R.array.languages)
        val arrayAdapter = ArrayAdapter(this, R.layout.language_list_item, languages)
        binding.autoComplete.setAdapter(arrayAdapter)

        binding.autoComplete.setOnItemClickListener { parent, view, position, id ->
            selectedLang = parent.getItemAtPosition(position).toString()
            if (selectedLang == "Yoruba") {
                selectedLangCode = "yo-rNG"
                LanguageHelper.changeLanguage(this, selectedLangCode)
                listener?.onSubmitBtnClicked(AppLanguage(selectedLang,selectedLangCode))
            }
            if (selectedLang == "English") {
                selectedLangCode = "en"
                LanguageHelper.changeLanguage(this, selectedLangCode)
                listener?.onSubmitBtnClicked(AppLanguage(selectedLang,selectedLangCode))
            }
//            if (selectedLang == "Hausa") {
//                selectedLangCode = "ha-rNG"
//                LanguageHelper.changeLanguage(this, selectedLangCode)
//                listener?.onSubmitBtnClicked(AppLanguage(selectedLang,selectedLangCode))
//            }
//            if (selectedLang == "Igbo") {
//                selectedLangCode = "ig"
//                LanguageHelper.changeLanguage(this, selectedLangCode)
//                listener?.onSubmitBtnClicked(AppLanguage(selectedLang,selectedLangCode))
//            }
        }

        observerLanguageChanges()

        binding.patientphone.text = SignInActivity.getValue()
        binding.patientname.text = UserInfoActivity.name
        binding.physname.text = UserInfoActivity.physname
        binding.physphone.text = UserInfoActivity.physphone

        binding.buttonchangeinfo.setOnClickListener {
            intent = Intent(this@SettingsActivity, UserInfoActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonsignout.setOnClickListener {
            intent = Intent(this@SettingsActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(34)
    private fun observerLanguageChanges() {
        lifecycleScope.launch {
            settingsManager.observeLanguageChanges().collect { it: AppLanguage ->
                if (selectedLangCode != it.selectedLangCode) {
                    restartActivity()
                }
            }
        }
    }
    @RequiresApi(34)
    private fun restartActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN,R.anim.slide_out_right,R.anim.slide_in_left)
        finish()
    }

    override fun onSubmitBtnClicked(appLanguage: AppLanguage) {

            if (selectedLangCode != appLanguage.selectedLangCode) {
                lifecycleScope.launch {
                    settingsManager.saveSelectedLanguage(appLanguage)

                }
            }
    }

}

//have to place outside of the class to not have circular reference
interface LanguageDialogOnClickListener {
    fun onSubmitBtnClicked(appLanguage: AppLanguage)
}