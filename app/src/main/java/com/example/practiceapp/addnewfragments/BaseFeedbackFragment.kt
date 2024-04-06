package com.example.practiceapp.addnewfragments

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import com.example.practiceapp.R

abstract class BaseFeedbackFragment : Fragment() {

    abstract val context1: Context
    lateinit var smsSendReceiver: BroadcastReceiver
    lateinit var smsDeliveredReceiver: BroadcastReceiver
    var send = "Send_SMS"
    var delivered = "Delivered_SMS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(getLayoutId(), container, false)

        val backbutton = v.findViewById<Button>(R.id.backButtonLow)
        backbutton.setOnClickListener {
            if (activity?.checkSelfPermission(android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
                || activity?.checkSelfPermission(android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                || activity?.checkSelfPermission(android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
            ) {
                activity?.requestPermissions(
                    arrayOf(
                        android.Manifest.permission.RECEIVE_SMS,
                        android.Manifest.permission.SEND_SMS,
                        android.Manifest.permission.READ_SMS
                    ), PackageManager.PERMISSION_GRANTED
                )
            }

            activity?.finish()
        }

        return v
    }

    abstract fun getLayoutId(): Int
    override fun onResume() {
        super.onResume()
        smsSendReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> Toast.makeText(activity, "SMS_send", Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> Toast.makeText(activity, "Generic fail", Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_NO_SERVICE -> Toast.makeText(activity, "NO Service", Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_RADIO_OFF -> Toast.makeText(activity, "Radio off", Toast.LENGTH_SHORT).show()
                }
            }
        }

        smsDeliveredReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> Toast.makeText(activity, "SMS_Delivered", Toast.LENGTH_SHORT).show()
                    Activity.RESULT_CANCELED -> Toast.makeText(activity, "SMS not deliverd", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val listenToBroadcastsFromOtherApps = false
        val receiverFlags = if (listenToBroadcastsFromOtherApps) {
            ContextCompat.RECEIVER_EXPORTED
        } else {
            ContextCompat.RECEIVER_NOT_EXPORTED
        }
        registerReceiver(context1,smsSendReceiver, IntentFilter(send),receiverFlags)
        registerReceiver(context1,smsDeliveredReceiver, IntentFilter(delivered),receiverFlags)
    }

    override fun onPause() {
        super.onPause()
        context1.unregisterReceiver(smsSendReceiver)
        context1.unregisterReceiver(smsDeliveredReceiver)
    }
}