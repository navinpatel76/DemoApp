package com.it.demoapp.view.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.it.demoapp.R
import com.it.demoapp.model.Data
import com.it.demoapp.utils.OnItemClickListener
import com.it.demoproject.model.Product
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

open class MyListAdapter (private val listdata: List<Product>,
                          var activity: Activity,
                          var onClickCallback: OnItemClickListener.OnClickCallback
                          ) : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem =
            layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (_, employee_name) = listdata[position]
        holder.tvbrand.setText(listdata[position].brand.toString())
        holder.tvTitle.setText(listdata[position].title.toString())
        holder.tvprice.setText(listdata[position].price.toString())
        holder.tvbrand.setText(listdata[position].brand.toString())
        holder.tvrating.setText(listdata[position].rating.toString())
        getBitmapFromURL(listdata[position].thumbnail, holder.imageView)
//        val thread = Thread {
//            try {
//                holder.imageView.setImageBitmap(getBitmapFromURL(listdata[position].thumbnail))
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//
//        thread.start()

        holder.lllinearlayout.setOnClickListener(
            OnItemClickListener(
                position,
                onClickCallback,
                ""
            )
        )
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var tvbrand: TextView
        var tvTitle: TextView
        var tvprice: TextView
        var tvrating: TextView
        var lllinearlayout: CardView

        init {
            imageView = itemView.findViewById(R.id.iv_product);
            lllinearlayout = itemView.findViewById<View>(R.id.ll_linear_layout) as CardView
            tvbrand = itemView.findViewById<View>(R.id.tv_brand) as TextView
            tvTitle = itemView.findViewById<View>(R.id.tv_title) as TextView
            tvprice = itemView.findViewById<View>(R.id.tv_price) as TextView
            tvrating = itemView.findViewById<View>(R.id.tv_rating) as TextView
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