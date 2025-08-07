package com.example.mindmelody

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mindmelody.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
        private lateinit var binding : FragmentHomeBinding

        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
                binding = FragmentHomeBinding.inflate(inflater, container, false)
                return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)



                RetrofitClient.instance.searchTracks("chill")
                        .enqueue(object : Callback<DeezerResponse> {
                                override fun onResponse(
                                        call: Call<DeezerResponse>,
                                        response: Response<DeezerResponse>
                                ) {
                                        val tracks = response.body()?.data ?: emptyList()
                                        // TODO: Show tracks in RecyclerView
                                }

                                override fun onFailure(call: Call<DeezerResponse>, t: Throwable) {
                                        // Handle error
                                }
                        })
        }
}