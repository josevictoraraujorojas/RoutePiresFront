    package com.example.routepiresfront

    import android.annotation.SuppressLint
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.fragment.app.Fragment
    import com.example.routepiresfront.databinding.FragmentAvaliacaoBinding

    class AvaliacaoFragment : Fragment() {

        private var _binding: FragmentAvaliacaoBinding? = null
        private val binding get() = _binding!!

        @SuppressLint("SetTextI18n")
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentAvaliacaoBinding.inflate(inflater, container, false)

            binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                binding.textRating.text = "${rating.toInt()} estrelas"
            }

            return binding.root
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
