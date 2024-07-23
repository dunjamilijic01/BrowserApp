package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.myapplication.MainActivity

import com.google.android.material.snackbar.Snackbar

private const val TAG = "MyBroadcastReceiver"

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(Intent.ACTION_BOOT_COMPLETED == intent.action){
           // Toast.makeText(context,"USOOO",Toast.LENGTH_SHORT).show()
            Log.d("LOG","USOOO")
            val i:Intent=Intent(context, LauncherActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)

        }

    }
}