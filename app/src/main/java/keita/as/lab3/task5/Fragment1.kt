package keita.`as`.lab3.task5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import keita.`as`.lab3.R
import keita.`as`.lab3.databinding.Fragment1Binding


class Fragment1 : Fragment() {
    private lateinit var binding: Fragment1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = Fragment1Binding.inflate(layoutInflater)

        binding.fragment1To2.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_firstFragment_to_secondFragment)
        }
        return binding.root
    }
}