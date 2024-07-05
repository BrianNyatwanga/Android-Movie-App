package com.movies.streamy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.movies.streamy.databinding.FragmentSignupBinding
import com.movies.streamy.viewmodel.AuthViewModel
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class SignupFragment : Fragment() {
    private val signupResult: Any
        get() {
            TODO()
        }
    private lateinit var binding: FragmentSignupBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)

        // Assign viewModel to the binding variable
//        binding.viewModel = SignupFragment

        // Set the lifecycle owner for LiveData observation
        binding.lifecycleOwner = viewLifecycleOwner // Use viewLifecycleOwner for LiveData binding

        // Observe the signupResult LiveData
        viewModel.signupResult.observe(viewLifecycleOwner, {
            // Handle signup response
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignupFragment()
    }
}

// Corrected operator function
private operator fun <T, V> ReadOnlyProperty<T, V>.getValue(
    signupFragment: SignupFragment,
    property: KProperty<*>
): V {
    TODO("Not yet implemented")
}

private fun Any.observe(viewLifecycleOwner: LifecycleOwner, any: Any) {
    TODO("Not yet implemented")
}
