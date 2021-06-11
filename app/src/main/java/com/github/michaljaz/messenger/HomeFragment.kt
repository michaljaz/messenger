package com.github.michaljaz.messenger

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        (activity as MainActivity).enableDrawer()
        (activity as MainActivity).supportActionBar?.setTitle("Chats")
        auth=(activity as MainActivity).getFirebase()
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu);

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.Logout).setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.logout)
        }
    }
}
