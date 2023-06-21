package com.example.sms_

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    lateinit var phoneEdt: EditText
    lateinit var messageEdt: EditText
    lateinit var sendMsgBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendMsgBtn = findViewById(R.id.idBtnSendMessage)
        phoneEdt = findViewById(R.id.idEdtPhone)
        messageEdt = findViewById(R.id.idEdtMessage)
        sendMsgBtn.setOnClickListener {
            demo()
        }
        var mobno:EditText=findViewById(R.id.edPhone)
        var btn:Button=findViewById(R.id.idBtncall)
        btn.setOnClickListener{

            var number: String = mobno.text.toString()
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + number)
            startActivity(dialIntent)
        }
    }
    private fun demo() {

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.SEND_SMS), 1)
        } else {
            val phoneNumber = phoneEdt.text.toString()
            val message = messageEdt.text.toString()
            try {
                val smsManager: SmsManager
                if (Build.VERSION.SDK_INT>=33) {
                    smsManager = this.getSystemService(SmsManager::class.java)
                }
                else{
                    smsManager = SmsManager.getDefault()
                }
                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                Toast.makeText(applicationContext, "Message Sent", Toast.LENGTH_LONG).show()

            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Please enter all the data.."+e.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}