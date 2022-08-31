package com.outriders.drawer

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.activity.result.ActivityResultLauncher
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
//import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private var drawingView: DrawingView? = null


    val openGallaryLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
            if(result.resultCode == RESULT_OK && result.data!=null) {
                val imagebackground: ImageView = findViewById(R.id.iv_background)

                imagebackground.setImageURI(result.data?.data)
            }
        }

    val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            permissions ->
            permissions.entries.forEach{
                val permissionsName = it.key
                val isGranted = it.value

                if(isGranted) {
                    Toast.makeText(
                        this@MainActivity,
                            "granted",
                        Toast.LENGTH_LONG
                    ).show()

                    val pickIntent = Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    openGallaryLauncher.launch(pickIntent)

                }else{
                    Toast.makeText(
                        this@MainActivity,
                        "denied",
                        Toast.LENGTH_LONG).show()
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


            val `SettingsButton` = findViewById<Button>(R.id.SettingsPage)



        SettingsButton.setOnClickListener {
            showBrushSizeOption()
        }



    }

    private fun showBrushSizeOption() {
        val brushDialog = Dialog(this)
            brushDialog.setContentView(R.layout.settingspage)
            brushDialog.setTitle("settings here")
            drawingView = findViewById(R.id.drawing_view)
            drawingView?.setSizeForBrush(5.toFloat())



            val UndoButton = brushDialog.findViewById<Button>(R.id.Undo_Button)
            val PermissionButton = brushDialog.findViewById<Button>(R.id.PermissionsButton)
            val ExportButton = brushDialog.findViewById<Button>(R.id.ExportButton)
            val SeekBarBrushSize = brushDialog.findViewById<SeekBar>(R.id.seekBar)

            val IB_RedButton = brushDialog.findViewById<ImageButton>(R.id.IB_Red_Button)
            val IB_BlueButton = brushDialog.findViewById<ImageButton>(R.id.IB_Blue_Button)
            val IB_GreenButton = brushDialog.findViewById<ImageButton>(R.id.IB_Green_Button)
            val IB_YellowButton = brushDialog.findViewById<ImageButton>(R.id.IB_Yellow_Button)



            UndoButton.setOnClickListener {
                drawingView?.onClickUndo()
            }


            PermissionButton.setOnClickListener {
                requestStoragePermission()
            }


            SeekBarBrushSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                val brushSizeTxt = brushDialog.findViewById<TextView>(R.id.BrushSizeTxt)

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    brushSizeTxt.text = progress.toString()
                    drawingView?.setSizeForBrush(progress.toFloat())
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    //startPoint=seekBar.progress
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })

            IB_YellowButton.setOnClickListener {
                drawingView?.setColor("YELLOW")
                brushDialog.dismiss()

            }
            IB_GreenButton.setOnClickListener {
                drawingView?.setColor("GREEN")
                brushDialog.dismiss()

            }
            IB_BlueButton.setOnClickListener {
                drawingView?.setColor("BLUE")
                brushDialog.dismiss()

            }
            IB_RedButton.setOnClickListener {
                drawingView?.setColor("RED")
                brushDialog.dismiss()

            }





        brushDialog.show()
    }

    private fun isReadStorageAllowed(): Boolean {
        val result = ContextCompat.checkSelfPermission(this,
        Manifest.permission.READ_EXTERNAL_STORAGE
            )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE)


        ){
           // showRationaleDialog("drawer", "Drawer "+"Needs access to your external Storage")
        }else{
            requestPermission.launch(arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                //Manifest.permission.WRITE_EXTERNAL_STORAGE

            ))
        }
    }

    private fun getBitmapFromView(view: View) : Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width,view.height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background

        if(bgDrawable != null) {
            bgDrawable.draw(canvas)
        }else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }



    }

