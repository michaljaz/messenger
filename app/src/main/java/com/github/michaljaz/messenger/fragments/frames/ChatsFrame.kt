package com.github.michaljaz.messenger.fragments.frames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.github.michaljaz.messenger.R
import com.github.michaljaz.messenger.activities.MainActivity
import com.github.michaljaz.messenger.adapters.ChatsAdapter
import com.google.android.material.textfield.TextInputEditText

class ChatsFrame : Fragment() {
    private lateinit var m: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frame_chats, container, false)
        m = activity as MainActivity

        //change menu
        m.home.changeMenu("chats")

        //not allow to go back
        m.allowBack=false

        //set toolbar title
        m.setToolbarTitle("Chats")

        //show action bar
        m.supportActionBar!!.show()

        //on click search
        view.findViewById<TextInputEditText>(R.id.Search).setOnClickListener {
            Navigation.findNavController(m, R.id.nav_host_fragment).navigate(R.id.search_on)
        }

        val list = view.findViewById<ListView>(R.id.list)
        val displayNames = ArrayList<String>()
        displayNames.add("John")
        displayNames.add("Richard")
        val photoUrls = ArrayList<String>()
        photoUrls.add("default")
        photoUrls.add("default")
        val userIds = ArrayList<String>()
        userIds.add("default")
        userIds.add("default")
        val lastMessages = ArrayList<String>()
        lastMessages.add("m1")
        lastMessages.add("m2")
        list.adapter= ChatsAdapter(requireContext(),displayNames,photoUrls,userIds,lastMessages)

        return view
    }
}
