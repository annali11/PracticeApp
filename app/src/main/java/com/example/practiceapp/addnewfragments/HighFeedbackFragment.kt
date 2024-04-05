package com.example.practiceapp.addnewfragments

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import com.example.practiceapp.AddNewActivity
import com.example.practiceapp.R
import com.example.practiceapp.UserInfoActivity


class HighFeedbackFragment : Fragment() {

    lateinit var sound_button: ImageButton
    lateinit var media_player: MediaPlayer
    lateinit var save_button: Button
    var MY_Permisson_Request_Code: Int = 1
    lateinit var smsSendReceiver: BroadcastReceiver
    lateinit var smsDeliveredReceiver: BroadcastReceiver
    lateinit var context: Context
    var send = "Send_SMS"
    var delivered = "Delivered_SMS"


    var sendPI: PendingIntent = PendingIntent.getBroadcast(context, 0, Intent(send),
        PendingIntent.FLAG_IMMUTABLE)
    var deliveredPI: PendingIntent = PendingIntent.getBroadcast(context,0, Intent(delivered),
        PendingIntent.FLAG_IMMUTABLE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_high_feedback, container, false)

        sound_button = v.findViewById<ImageButton>(R.id.sound_button)
        media_player = MediaPlayer.create(requireContext(), R.raw.highfeedback_n)

        sound_button.setOnClickListener(View.OnClickListener {
            media_player.start()
        })

        save_button = v.findViewById(R.id.backButtonHigh)

        save_button.setOnClickListener{
            //This code to check if permissions are granted (used in fragment) than request them if not
            if (activity?.checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
                || activity?.checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                || activity?.checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            {
                activity?.requestPermissions(
                    arrayOf(
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_SMS), PackageManager.PERMISSION_GRANTED
                )
            }

            val phoneNumber = UserInfoActivity.getPhysPhone()
            val message = AddNewActivity.getBPmessage()

            if (phoneNumber != null) {
                if (message != null) {
                    btn_sendSMS_OnClick()
                }
            }
        }
//            val subscriptionManager = context?.getSystemService(SubscriptionManager::class.java)
//
//            SmsMessage.cre(null,phoneNumber,message,true)
//            Toast.makeText(context, "Message Sent", Toast.LENGTH_LONG).show()
//            activity?.finish()
        return v
    }

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
        registerReceiver(context,smsSendReceiver, IntentFilter(send),receiverFlags)
        registerReceiver(context,smsDeliveredReceiver, IntentFilter(delivered),receiverFlags)
    }

    override fun onPause() {
        super.onPause()
        context.unregisterReceiver(smsSendReceiver)
        context.unregisterReceiver(smsDeliveredReceiver)
    }

    fun btn_sendSMS_OnClick() {
        val phone = UserInfoActivity.getPhysPhone().toString()
        val message = AddNewActivity.bpstring.toString()
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            activity?.let { ActivityCompat.requestPermissions(it.parent, arrayOf(Manifest.permission.SEND_SMS), MY_Permisson_Request_Code) }
        } else {
            val sms = context.getSystemService<SmsManager>(
                SmsManager::class.java
            )
            sms.sendTextMessage(phone, null, message, sendPI, deliveredPI)
        }
    }

//    fun sendSMS(
//        phoneNumber: String,
//        message: String,
//        id: Int,
//    ): Boolean {
//        val subscriptionManager = context.getSystemService(SubscriptionManager::class.java)
//        if (ActivityCompat.checkSelfPermission(context,
//                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
//        ) {
//
//            throw Exception("Permission not granted")
//        }
//
//        val subscriptionInfo = subscriptionManager.getActiveSubscriptionInfo(id)
//        if (subscriptionInfo != null) {
//            val subscriptionId = subscriptionInfo.subscriptionId
//            val smsManager = getSmsManagerForSubscriptionId(subscriptionId)
//
//            Log.wtf("METHOD_CHANNEL_UTILS", "Message content: $message")
//            Log.wtf("METHOD_CHANNEL_UTILS", "Message length: ${message.length}")
//
//            val iSentIntent = Intent()
//            val iDeliveryIntent = Intent()
//
//            val sentPendingIntent = PendingIntent.getBroadcast(
//                context,
//                0,
//                iSentIntent,
//                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE,
//            )
//
//            // Create a PendingIntent for delivery status
//            val deliveryPendingIntent = PendingIntent.getBroadcast(
//                context,
//                0,
//                iDeliveryIntent,
//                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE,
//            )
//
//            val length = message.length
//            if (length > 160) {
//                val messageParts = smsManager.divideMessage(message)
//                val sentIntentsArrayList = ArrayList<PendingIntent>()
//                val deliveryIntentsArrayList = ArrayList<PendingIntent>()
//
//                for (i in messageParts.indices) {
//                    sentIntentsArrayList.add(sentPendingIntent)
//                    deliveryIntentsArrayList.add(deliveryPendingIntent)
//                }
//
//                smsManager.sendMultipartTextMessage(
//                    phoneNumber,
//                    null,
//                    messageParts,
//                    sentIntentsArrayList,
//                    deliveryIntentsArrayList,
//                )
//
//            } else {
//                smsManager.sendTextMessage(
//                    phoneNumber,
//                    null,
//                    message,
//                    sentPendingIntent,
//                    deliveryPendingIntent,
//                )
//            }
//
//            // Register for SMS send action
//            context.registerReceiver(object : BroadcastReceiver() {
//                override fun onReceive(context: Context?, intent: Intent?) {
//                    if (intent?.action == INTENT_SENT_SMS_ACTION) {
//                        val bundle = intent.extras
//                        val surveyResponseIdX = bundle?.getString("surveyResponseId") ?: ""
//
//                        intent.putExtra("surveyResponseId", surveyResponseIdX)
//
//                    }
//                }
//            }, IntentFilter(INTENT_SENT_SMS_ACTION))
//
//            // Register for delivery action
//            context.registerReceiver(object : BroadcastReceiver() {
//                override fun onReceive(context: Context?, intent: Intent?) {
//                    if (intent?.action == INTENT_DELIVERED_SMS_ACTION) {
//                        val bundle = intent.extras
//                        val surveyResponseIdX = bundle?.getString("surveyResponseId") ?: ""
//
//                        intent.putExtra("surveyResponseId", surveyResponseIdX)
//                    }
//                }
//            }, IntentFilter("DELIVERED_SMS_ACTION"))
//
//            return true
//        } else {
//            throw Exception("Sim not found")
//        }
//    }

}