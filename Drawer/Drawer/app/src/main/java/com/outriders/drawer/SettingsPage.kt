package com.outriders.drawer

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button




class SettingsPage : AppCompatActivity() {

    private var SettingsPage: DrawingView? = null
    private var drawingView: DrawingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settingspage)

     //  val RedButton = findViewById<Button>(R.id.RedButton)
     // // val BlueButton = findViewById<Button>(R.id.blueblue)

     //  val ReturnButton = findViewById<Button>(R.id.ReturnButton)

        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(5.toFloat())


    //    RedButton.setOnClickListener {
    //        drawingView?.setSizeForBrush(10.toFloat())
    //        drawingView?.setColor("RED")
    //    }
//
    //    //BlueButton.setOnClickListener {
    //    //    drawingView?.setSizeForBrush(10.toFloat())
    //    //    drawingView?.setColor("BLUE")
    //    //}
//
    //    ReturnButton.setOnClickListener {
    //        val intent = Intent(this, MainActivity::class.java)
    //        startActivity(intent)
    //    }

    }


}