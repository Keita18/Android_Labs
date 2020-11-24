package keita.`as`.lab3.task5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import keita.`as`.lab3.R
import keita.`as`.lab3.databinding.Fragment3Binding


class Fragment3 : Fragment() {

    private lateinit var binding: Fragment3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = Fragment3Binding.inflate(layoutInflater)

        binding.fragment3To1.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_thirdFragment_to_firstFragment)
        }
        binding.fragment3To2.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_thirdFragment_to_secondFragment)
        }
        return binding.root
    }
}