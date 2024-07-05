package com.movies.streamy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.model.Model
import com.movies.streamy.databinding.FragmentLoginBinding
import com.movies.streamy.model.dataSource.network.apiService.auth
import com.movies.streamy.model.dataSource.network.apiService.auth.AuthViewModel // Adjusted import
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by ViewModels() // Corrected ViewModel import

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewModel = com.movies.streamy.viewmodel.AuthViewModel()
        binding.lifecycleOwner = this

        viewModel.loginResult.observe(viewLifecycleOwner, {
            // Handle login response
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}

class ViewModels :
    ReadOnlyProperty<LoginFragment, AuthViewModel> {
    override fun getValue(thisRef: LoginFragment, property: KProperty<*>): AuthViewModel {
        TODO("Not yet implemented")
    }

}

private fun <T, V> ReadOnlyProperty<T, V>.getValue(t: T, property: KProperty<V?>): V {
    TODO("Not yet implemented")
}

private operator fun Any.getValue(loginFragment: LoginFragment, property: KProperty<*>): Any {
    TODO("Not yet implemented")
}
