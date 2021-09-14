package com.demo.mytask.userDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.mytask.R
import com.demo.mytask.databinding.UserDetailsFragmentBinding
import com.demo.mytask.database.AddUserDatabase
import com.demo.mytask.database.UserRepository
import com.demo.mytask.login.LoginFragmentDirections
import com.example.mydatabaseapp.userDetails.UserDetalisFactory
import java.lang.reflect.InvocationHandler

class UserDetailsFragment : Fragment() {

    private val TAG = "UserDetailsFragment"

    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private lateinit var binding: UserDetailsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.user_details_fragment, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = AddUserDatabase.getInstance(application).userDao

        val repository = UserRepository(dao)

        val factory = UserDetalisFactory(repository, application)

        userDetailsViewModel =
            ViewModelProvider(this, factory).get(UserDetailsViewModel::class.java)

        binding.userDelailsLayout = userDetailsViewModel

        binding.lifecycleOwner = this



        userDetailsViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                val action =
                    UserDetailsFragmentDirections.actionUserDetailsFragmentToLoginFragment()
                NavHostFragment.findNavController(this).navigate(action)
                userDetailsViewModel.doneNavigating()
            }
        })

        userDetailsViewModel.navigatetoRegister.observe(
            viewLifecycleOwner,
            Observer { hasFinished ->
                if (hasFinished == true) {
                    Log.i(TAG, "insidi observe")
                    addUser()
                    userDetailsViewModel.doneNavigatingRegiter()
                }
            })

        initRecyclerView()

        return binding.root

    }

    private fun addUser() {
        Log.i(TAG, "insidisplayUsersList")
        val action = UserDetailsFragmentDirections.actionUserDetailsFragmentToRegisterFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

    var adapter: MyRecycleViewAdapter? = null

    private fun initRecyclerView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this.context)

        userDetailsViewModel.users.observe(viewLifecycleOwner, Observer {
            displayUsersList()

            val helper = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(
                    0,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                ) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(
                        viewHolder: RecyclerView.ViewHolder,
                        direction: Int
                    ) {
                        val position = viewHolder.adapterPosition
                        val myWord = adapter?.getUser(position)
                        Toast.makeText(
                            requireContext(), "Deleting " +
                                    myWord?.firstName, Toast.LENGTH_LONG
                        ).show()

                        // Delete the word
                        userDetailsViewModel.delete(myWord!!)
                    }
                })

            helper.attachToRecyclerView(binding.usersRecyclerView)

        })
    }


    private fun displayUsersList() {
        Log.i(TAG, "Inside ...UserDetails..Fragment")
        userDetailsViewModel.users.observe(viewLifecycleOwner, Observer {
            binding.usersRecyclerView.adapter = MyRecycleViewAdapter(it)
            adapter = MyRecycleViewAdapter(it)
            val adapter = MyRecycleViewAdapter(it)

        })

    }

}