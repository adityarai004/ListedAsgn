package com.example.listedasgn.ui.fragments.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.listedasgn.R
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import java.util.Locale


class LinkAdapter(val mContext: Context,val mList:List<LinkViewModel>):RecyclerView.Adapter<LinkAdapter.ViewHolder>() {

    private var onCopyClickListener: OnCopyClickListener? = null
    fun setOnCopyClickListener(listener: OnCopyClickListener) {
        onCopyClickListener = listener
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val iconIV: ImageView = itemView.findViewById(R.id.link_logo_iv)
        val linkNameTV: TextView = itemView.findViewById(R.id.link_name_tv)
        val linkTV: TextView = itemView.findViewById(R.id.link_tv)
        val dateTV: TextView = itemView.findViewById(R.id.link_date_tv)
        val clicks: TextView = itemView.findViewById(R.id.link_clicks_tv)
        val copyBtn:ImageButton = itemView.findViewById(R.id.copy_image_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.top_recent_rv,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val linkViewModel = mList[position]
        holder.iconIV.setImageResource(linkViewModel.icon)
        holder.linkNameTV.text = linkViewModel.linkName
        holder.linkTV.text = linkViewModel.link
        holder.clicks.text = linkViewModel.clicks.toString()

        val date = linkViewModel.date
        holder.dateTV.text = formatDate(date)

        holder.linkTV.setOnClickListener {
            val url = "https://"+holder.linkTV.text.toString()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            mContext.startActivity(intent)
        }


            holder.copyBtn.setOnClickListener {
                    val clipboardManager = mContext.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("label", "https://"+holder.linkTV.text)
                    clipboardManager.setPrimaryClip(clipData)
                    Toast.makeText(mContext, "Link copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(inputDate: String): String {
        val inputFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val outputFormat = DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH)
            .appendLiteral(" ")
            .appendText(ChronoField.MONTH_OF_YEAR, TextStyle.FULL_STANDALONE)
            .appendLiteral(" ")
            .appendValue(ChronoField.YEAR)
            .toFormatter(Locale.ENGLISH)

        val parsedDate = inputFormat.parse(inputDate)
        return outputFormat.format(parsedDate)
    }

    interface OnCopyClickListener {
        fun onCopyClick(text: String)
    }

}