package com.example.palliativecare.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecare.R
import com.example.palliativecare.model.Topic
import com.example.palliativecare.view.activity.EditTopicActivity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class TopicAdapter(val context: Context, val data: ArrayList<Topic>) :
    RecyclerView.Adapter<TopicAdapter.MyViewHolder>() {

    val db = Firebase.firestore

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.item_topic_img_logo)
        val title = itemView.findViewById<TextView>(R.id.item_topic_tv_name)
        val description = itemView.findViewById<TextView>(R.id.item_topic_tv_textual)
        val btnDisplay = itemView.findViewById<Button>(R.id.item_topic_btn_display)
        val btnEdit = itemView.findViewById<Button>(R.id.item_topic_btn_edit)
        val btnHide = itemView.findViewById<Button>(R.id.item_topic_btn_hide)
        val btnDelete = itemView.findViewById<Button>(R.id.item_topic_btn_delete)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_item_topic,parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val topic = data[position]

        Picasso.get().load(topic.img).into(holder.img)

        holder.title.text = topic.title
        holder.description.text = topic.desc

        holder.btnDisplay.setOnClickListener {

        holder.btnEdit.setOnClickListener {

        }

        holder.btnHide.setOnClickListener {

        }

        holder.btnDelete.setOnClickListener {
            getidDelete(topic.title,position)
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun getidDelete(topicTitle: String,pos:Int) {
        val db = Firebase.firestore
        db.collection("topics")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val title = document.get("title").toString()
                    if (title == topicTitle) {
                        val id = document.id
                        db.collection("topics").document(id).delete()
                    }
                }
                data.removeAt(pos)
                notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("ContentValues", "Error getting documents.", exception)
            }
    }

    fun getidUpdate(topicTitle: String, desc: String, hidden: Boolean) {
        val db = Firebase.firestore
        db.collection("topics")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val title = document.get("title").toString()
                    val desc = document.get("description").toString()
                    if (title == topicTitle) {
                        val id = document.id
                        db.collection("topics").document(id).update("hidden", hidden)
                        val intent = Intent(context, EditTopicActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        context.startActivity(intent)
                    }

                }
            }
            .addOnFailureListener { exception ->
                Log.w("ContentValues", "Error getting documents.", exception)
            }
    }
}