package com.zaimutest777.zaim.ui.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.models.credits.Product

class ProductAdapter constructor(private val productsList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>()
{
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        context = parent.context
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.loan_layout, parent, false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val itemView = holder.itemView
        itemView.findViewById<TextView>(R.id.titleView).apply {
            text = productsList[position].title
        }
        val logoImageView = itemView.findViewById<ImageView>(R.id.logoImgView)
        Picasso.with(context).load(Uri.parse(productsList[position].imageUrl)).into(logoImageView)

        itemView.findViewById<TextView>(R.id.summTextView).apply {
            text = productsList[position].summText
        }

        itemView.findViewById<TextView>(R.id.betTextView).apply {
            text = productsList[position].betText
        }

        val starsLayout = itemView.findViewById<LinearLayoutCompat>(R.id.starsLayout)
        starsLayout.removeAllViews()
        val score = productsList[position].score.toInt()
        println("******************* score = $score ****************************")
        for (i in 1..score)
        {
            val starView = LayoutInflater.from(context).inflate(R.layout.star_layout, LinearLayoutCompat(context), false)
            starsLayout.addView(starView)
        }

        if (productsList[position].cash.isEmpty())
        {
            itemView.findViewById<ImageView>(R.id.cashIconView)?.visibility = View.GONE
        }

        if (productsList[position].mastercard.isEmpty())
        {
            itemView.findViewById<ImageView>(R.id.masterCardIconView)?.visibility = View.GONE
        }

        if (productsList[position].mir.isEmpty())
        {
            itemView.findViewById<ImageView>(R.id.mirIconView)?.visibility = View.GONE
        }

        if (productsList[position].visa.isEmpty())
        {
            itemView.findViewById<ImageView>(R.id.visaIconView)?.visibility = View.GONE
        }

        if (productsList[position].qiwi.isEmpty())
        {
            itemView.findViewById<ImageView>(R.id.qiviIconView)?.visibility = View.GONE
        }

        if (productsList[position].yandex.isEmpty())
        {
            itemView.findViewById<ImageView>(R.id.yandexIconView)?.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int
    {
        return productsList.size
    }
}