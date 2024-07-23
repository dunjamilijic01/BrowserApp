package com.example.myapplication

import android.app.Dialog
import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity


class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       if (!Settings.canDrawOverlays(this)) {
           val intent = Intent(
               Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse(
                   "package:$packageName"
               )
           )
           startActivityForResult(intent, 0)

       }
           setContentView(R.layout.activity_launcher)
           supportActionBar!!.hide()
            val handler: Handler = Handler()


            val prefs = getPreferences(0)
            var restoredUrl = prefs.getString("url", "www.infotech.rs")
            var restoredScale=prefs.getString("scale","30")
            var restoredBrightness=prefs.getString("brightness","100")
            var restoredStartTime=prefs.getString("startTime","00:00:00")
            var restoredEndTime=prefs.getString("endTime","00:00:00")


            val settingsIcon = findViewById<TextView>(R.id.settingsIcon)
            settingsIcon.setOnClickListener {
                handler.removeCallbacksAndMessages(null)

                val dialog = Dialog(this)
                val view = layoutInflater.inflate(R.layout.popup_layout, null, false)
                view.findViewById<EditText>(R.id.urlEditText).setText(restoredUrl!!)
                view.findViewById<EditText>(R.id.scaleEditText).setText(restoredScale!!)
                view.findViewById<EditText>(R.id.brightnessEditText).setText(restoredBrightness!!)
                view.findViewById<EditText>(R.id.startTimeEditText).setText(restoredStartTime!!)
                view.findViewById<EditText>(R.id.endTimeEditText).setText(restoredEndTime!!)


                val btn = view.findViewById<Button>(R.id.goBtn)
                btn.setOnClickListener {
                    val url = view.findViewById<EditText>(R.id.urlEditText).text.toString()
                    val scale = view.findViewById<EditText>(R.id.scaleEditText).text.toString()
                    val brightness=view.findViewById<EditText>(R.id.brightnessEditText).text.toString()
                    val startTime=view.findViewById<EditText>(R.id.startTimeEditText).text.toString()
                    val endTime=view.findViewById<EditText>(R.id.endTimeEditText).text.toString()
                    if (url != "" && url != null) {
                        if (scale != "" && scale != null && brightness!="" && brightness!=null && startTime!="" && startTime!=null && endTime!="" && endTime!=null) {
                            val intent: Intent =
                                Intent(applicationContext, MainActivity::class.java)
                            val editor = getPreferences(0).edit()
                            editor.clear();
                            editor.putString("url", url)
                            editor.putString("scale",scale)
                            editor.putString("brightness",brightness)
                            editor.putString("startTime",startTime)
                            editor.putString("endTime",endTime)
                            //Toast.makeText(applicationContext, url, Toast.LENGTH_SHORT).show()
                            //editor.putInt("idName", 12)
                            editor.commit()


                            //val bundle=Bundle()
                            intent.putExtra("url", url)
                            intent.putExtra("scale", scale)
                            intent.putExtra("brightness",brightness)
                            intent.putExtra("startTime",startTime)
                            intent.putExtra("endTime",endTime)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Morate popuniti sva polja!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this, "Url ne sme biti prazan", Toast.LENGTH_SHORT).show()
                    }
                }

                dialog.setContentView(view)

                val lp = WindowManager.LayoutParams()
                lp.copyFrom(dialog.getWindow()!!.getAttributes())
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.MATCH_PARENT
                dialog.show()
                dialog.window!!.attributes = lp

            }


            handler.postDelayed(object : Runnable {
                override fun run() {
                    //Toast.makeText(applicationContext, editText.text.toString(), Toast.LENGTH_SHORT).show()

                    if (restoredUrl == "" || restoredUrl == null) {
                        Toast.makeText(applicationContext, "null je", Toast.LENGTH_SHORT).show()
                        restoredUrl = "www.infotech.rs"
                    }

                    Toast.makeText(applicationContext,restoredUrl+" "+restoredScale+" "+restoredBrightness+" "+restoredStartTime+" "+restoredEndTime,Toast.LENGTH_SHORT).show()

                    val intent: Intent = Intent(applicationContext, MainActivity::class.java)
                    intent.putExtra("url", restoredUrl)
                    intent.putExtra("scale", restoredScale)
                    intent.putExtra("brightness",restoredBrightness)
                    intent.putExtra("startTime",restoredStartTime)
                    intent.putExtra("endTime",restoredEndTime)
                    startActivity(intent)
                }
            }, 10000)

        }
    //}

}