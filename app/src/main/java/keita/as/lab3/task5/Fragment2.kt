package keita.`as`.lab3.task5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import keita.`as`.lab3.R
import keita.`as`.lab3.databinding.Fragment1Binding
import keita.`as`.lab3.databinding.Fragment2Binding


class Fragment2 : Fragment() {

    private lateinit var binding: Fragment2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = Fragment2Binding.inflate(layoutInflater)
        binding.fragment2To1.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_secondFragment_to_firstFragment)
        }
        binding.fragment2To3.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_secondFragment_to_thirdFragment)
        }
        return binding.root
    }
}