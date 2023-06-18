package com.it.demoapp.view.adapter

import android.R.attr.port
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.it.demoapp.R
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


internal class ViewPager2Adapter     // Constructor of our ViewPager2Adapter class
    (private val ctx: Context,
     private val activity: Activity,
     private val img: List<String>) : RecyclerView.Adapter<ViewPager2Adapter.ViewHolder>() {
    // Array of images
    // Adding images from drawable folder


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.view_pagger_item, parent, false)
        return ViewHolder(view)
    }

    // This method binds the screen with the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Handler(Looper.getMainLooper()).post(Runnable {
        Log.i(javaClass.name, "==>"+getBitmapFromURL(img[position], holder.images))
        })
    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return img.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var images: ImageView

        init {
            images = itemView.findViewById(R.id.images)
        }
    }

    open fun getBitmapFromURL(src: String?, img: ImageView): Bitmap? {
        var bitmap : Bitmap ?= null
        val thread = Thread {
         try {
            Log.e("src", src!!)
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            val myBitmap = BitmapFactory.decodeStream(input)
            Log.e("Bitmap", "returned"+myBitmap)
             //accessing it from ui-thread

             //accessing it from ui-thread
             activity.runOnUiThread(Runnable {
                 img.setImageBitmap(myBitmap)
             })
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("Exception", e.message!!)
            null
        }
        }
        thread.start()
        return bitmap
    }
}