package com.example.practiceapp

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var signinPhone: EditText
    private lateinit var signinPassword: EditText
    private lateinit var verifyphoneButton: Button
    private lateinit var signinButton: Button
    private lateinit var verificationId: String
    lateinit var context: Context
    var sendotp = "Send_OTP"
    var deliveredotp = "Delivered_OTP"
    lateinit var otp: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()
        signinPhone = findViewById(R.id.signin_phone)
        signinPassword = findViewById(R.id.signin_password)
        verifyphoneButton = findViewById(R.id.verifyphone_button)
        signinButton = findViewById(R.id.signin_button)
        context = this

        verifyphoneButton.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(signinPhone.text.toString()) || signinPhone.text.toString().length != 10) {
                signinPhone.setError("Please enter valid phone number")
            } else {
                phone = signinPhone.text.toString().trim { it <= ' ' }
                //sendVerificationCode(phone!!)

                if (this.checkSelfPermission(android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                    || this.checkSelfPermission(android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(arrayOf(android.Manifest.permission.SEND_SMS,android.Manifest.permission.RECEIVE_SMS), PackageManager.PERMISSION_GRANTED)
                } else {
                    sendOTP_OnClick()
                }
            }
        })

        signinButton.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(signinPassword.text.toString())) {
                Toast.makeText(
                    this@SignInActivity,
                    "Please Enter One-Time Password",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //verifyCode(signinPassword.text.toString())
                if (signinPassword.text.toString() == otp) {
                    val i = Intent(this@SignInActivity, HomeActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(this@SignInActivity, "Invalid OTP", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun sendVerificationCode(number: String) {
        // this method is used for getting
        // OTP on user phone number.
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // below method is use to verify code from Firebase.
    private fun verifyCode(code: String) {
        // below line is used for getting
        // credentials from our verification id and code.
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)

        // after getting credential we are calling sign in method.
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        // inside this method we are checking if the code entered is correct or not.
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // if the code is correct and the task is successful we are sending our user to new activity.
                    val i = Intent(this@SignInActivity, HomeActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    // if the code is not correct then we are displaying an error message to the user.
                    Toast.makeText(this@SignInActivity, task.exception!!.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    // callback method is called on Phone auth provider.
    private val   // initializing our callbacks for on verification callback method.
            mCallBack: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            // below method is used when OTP is sent from Firebase
            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s
            }

            // this method is called when user receive OTP from Firebase.
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                // below line is used for getting OTP code which is sent in phone auth credentials.
                val code = phoneAuthCredential.smsCode

                // checking if the code is null or not.
                if (code != null) {
                    // if the code is not null then we are setting that code to our OTP edittext field.
                    signinPassword.setText(code)

                    // after setting this code to OTP edittext field we are calling our verifycode method.
                    verifyCode(code)
                }
            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                // displaying error message with firebase exception.
                Toast.makeText(this@SignInActivity, e.message, Toast.LENGTH_LONG).show()
                val i = Intent(this@SignInActivity, HomeActivity::class.java)
                startActivity(i)
                finish()
            }
        }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            intent = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {

        var phone: String? = null
        fun getValue(): String? {
            return phone
        }
        fun getOTP(): String? {
            val random = java.util.Random()
            val number = random.nextInt(90000) + 10000
            val numberString = number.toString()
            return numberString
        }
    }

    fun sendOTP_OnClick() {
        var MY_Permisson_Request_Code: Int = 1
        var sendPI: PendingIntent = PendingIntent.getBroadcast(context, 0, Intent(sendotp),
            PendingIntent.FLAG_IMMUTABLE)
        var deliveredPI: PendingIntent = PendingIntent.getBroadcast(context,0, Intent(deliveredotp),
            PendingIntent.FLAG_IMMUTABLE)

        val phone = SignInActivity.phone.toString()
        otp = getOTP().toString()

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            this.let { ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS), MY_Permisson_Request_Code) }
        } else {
            val sms = context.getSystemService<SmsManager>(
                SmsManager::class.java
            )
            sms.sendTextMessage(phone, null, otp, sendPI,deliveredPI)
        }
    }
}