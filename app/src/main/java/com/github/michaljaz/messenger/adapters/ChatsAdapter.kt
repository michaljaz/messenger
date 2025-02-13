package com.github.michaljaz.messenger.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.michaljaz.messenger.R
import com.github.michaljaz.messenger.utils.RoundedTransformation
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList


class Chat(
    val displayName: String,
    val photoUrl: String,
    val userId: String,
    val lastMessage: String,
    val lastMessageTimeStamp: String,
    val lastMessageSender: String) {

}

class ChatsDiffCallback(
    private val mOldChatsList: List<Chat>,
    private val mNewChatsList: List<Chat>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldChatsList.size
    }

    override fun getNewListSize(): Int {
        return mNewChatsList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldChat: Chat = mOldChatsList[oldItemPosition]
        val newChat: Chat = mNewChatsList[newItemPosition]
        return oldChat.userId == newChat.userId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldChat: Chat = mOldChatsList[oldItemPosition]
        val newChat: Chat = mNewChatsList[newItemPosition]
        return oldChat == newChat
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}

class ChatsAdapter (val mChats: ArrayList<Chat>) : RecyclerView.Adapter<ChatsAdapter.ViewHolder>()
{
    var onItemClick: ((Chat)->Unit) ?= null
    var onItemLongClick: ((Chat)->Unit) ?= null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val displayName: TextView = itemView.findViewById(R.id.DisplayName)
        val lastMessage: TextView = itemView.findViewById(R.id.LastMessage)
        val icon: ImageView = itemView.findViewById(R.id.imgIcon)
        val date: TextView=itemView.findViewById(R.id.textView)

        init{
            itemView.setOnClickListener {
                onItemClick?.invoke(mChats[adapterPosition])
            }
            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(mChats[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.row_chat, parent, false))
    }
    override fun getItemViewType(position: Int): Int {
        return if(position==0){ 0 }else if(position==1){ 1 }else{2}
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ChatsAdapter.ViewHolder, position: Int) {
        val chat: Chat = mChats[position]
        viewHolder.displayName.text=chat.displayName
        viewHolder.lastMessage.text=chat.lastMessageSender+chat.lastMessage
        if(chat.photoUrl=="default"){
            viewHolder.icon.setImageResource(R.drawable.ic_profile_user)
        }else{
            Picasso
                .get()
                .load(chat.photoUrl)
                .transform(RoundedTransformation(100, 0))
                .into(viewHolder.icon)
        }
        val c = Calendar.getInstance()
        c.timeInMillis=chat.lastMessageTimeStamp.toLong()

        val cc = Calendar.getInstance()
        cc.timeInMillis=System.currentTimeMillis()

        if(c.get(Calendar.YEAR)==cc.get(Calendar.YEAR)){
            if(c.get(Calendar.DAY_OF_YEAR)==cc.get(Calendar.DAY_OF_YEAR)){
                viewHolder.date.text=android.text.format.DateFormat.format(" · HH:mm", c)
                return
            }
        }
        viewHolder.date.text=android.text.format.DateFormat.format(" · dd MMM", c)
    }
    fun updateListItems(chats:ArrayList<Chat>){
        val diffCallback = ChatsDiffCallback(mChats, chats)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        mChats.clear()
        mChats.addAll(chats)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return mChats.size
    }
}