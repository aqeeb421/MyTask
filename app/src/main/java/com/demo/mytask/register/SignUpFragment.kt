package com.demo.mytask.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.demo.mytask.R
import com.demo.mytask.database.AddUserDatabase
import com.demo.mytask.database.UserRepository
import com.demo.mytask.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var signUpViewModel: SignUpViewModel
    private val TAG = "SignUpFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSignUpBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = AddUserDatabase.getInstance(application).userDao

        val repository = UserRepository(dao)

        val factory = SignUpViewModelFactory(repository, application)

        signUpViewModel = ViewModelProvider(this, factory).get(SignUpViewModel::class.java)

        binding.myViewModel = signUpViewModel

        binding.lifecycleOwner = this

        signUpViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                Log.i(TAG, "insidi observe")
                displayUsersList()
                signUpViewModel.doneNavigating()
            }
        })

        signUpViewModel.userDetailsLiveData.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, it.toString() + "000000000000000000000000")
        })


        signUpViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
                signUpViewModel.donetoast()
            }
        })

        signUpViewModel.errotoastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "UserName Already taken", Toast.LENGTH_SHORT)
                    .show()
                signUpViewModel.donetoastUserName()
            }
        })

        return binding.root
    }

    private fun displayUsersList() {
        Log.i(TAG, "insidisplayUsersList")
        val action = SignUpFragmentDirections.actionRegisterFragmentToUserDetailsFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

}