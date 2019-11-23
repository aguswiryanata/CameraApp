package com.gmail.wiryanatha.agus.cameraapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    val REQUEST_PERMMISION_CODE = 100
    val REQUEST_CAMERA_CODE = 101
    var currentPhoto: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check perijinan
        val daftarIzin = mutableListOf<String>()
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            daftarIzin.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            daftarIzin.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            daftarIzin.add(Manifest.permission.CAMERA)
        }
        if(daftarIzin.size>0){
            val iz = arrayOfNulls<String>(daftarIzin.size)
            for(i in 0 until daftarIzin.size){
                iz[i]= daftarIzin.get(i)
            }
            ActivityCompat.requestPermissions(this, iz, REQUEST_PERMMISION_CODE)
        } else {
            //do nothing
        }
        //tambahkan fungsi click pada ImageView (dg id photo)
        //jadi, ketika di klik, akan memanggil fungsi TakePicture
        //yaitu: membuka aplikasi android
        // dan file photo disimpan dengan nama file foto1.jpg

        foto.setOnClickListener{
            takePicture("foto1.jpg")
        }
    }
    fun takePicture (namafile: String){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //Persiapan untuk buka aplikasi kamera bawaan android
        val filePhoto = File(getExternalFilesDir(null),namafile)
        //Siapkan file yang akan menyimpan hasil photo

        val uriPhoto = FileProvider.getUriForFile(this,"com.gmail.wiryanatha.agus.cameraapp.fileprovider",filePhoto)

        //Ambil lokasi file foto tersebut, untuk ditampilakan nanti di ImageView
        currentPhoto = filePhoto.absolutePath
        //Info ke aplikasi kamera, lokasi tempat penyimpanan hasil photonya
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,uriPhoto)
        // start aplikasi kamera
        startActivityForResult(cameraIntent,REQUEST_CAMERA_CODE)
    }
}
