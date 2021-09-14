package com.demo.mytask.startPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.demo.mytask.R
import com.demo.mytask.databinding.FragmentLoginBinding
import com.demo.mytask.databinding.FragmentStartBinding
import com.demo.mytask.login.LoginFragmentDirections

class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentStartBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_start, container, false
        )

        binding.signInButton.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragmentToLoginFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.signUpButton.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragmentToRegisterFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }

}