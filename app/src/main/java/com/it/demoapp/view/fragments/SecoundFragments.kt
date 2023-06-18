package com.it.demoapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.gson.Gson
import com.it.demoapp.R
import com.it.demoapp.databinding.SecoundFragmentBinding
import com.it.demoapp.view.adapter.MyListAdapter
import com.it.demoapp.view.adapter.ViewPager2Adapter
import com.it.demoproject.model.Product
import com.it.demoproject.model.ProductForStore
import ladd.marshall.androidmvvmexample.viewModel.viewModels.ListViewModel


class SecoundFragments : Fragment() {

    private val viewModel by viewModels<ListViewModel>()
    private lateinit  var binding : SecoundFragmentBinding
    private lateinit var clickData : Product
    private var isLike = false
    private var isFrom = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null) {
            val gson = Gson()
            val strObj = getArguments()?.getString("ClickData")
             clickData = gson.fromJson(strObj, Product::class.java)

             isFrom = getArguments()?.getString("isFrom").toString()
            Log.i(javaClass.name, "----"+clickData)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.secound_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SecoundFragmentBinding.bind(view)
        if(isFrom.equals("isFav")) {
            binding.ivLikeUnlike.visibility = View.GONE
        }
        binding.tvTitle.setText(clickData.title)
        binding.tvDescription.setText(clickData.description)
        binding.tvBrand.setText("Brand - "+clickData.brand)
        binding.tvCategory.setText("Category - "+clickData.category)
        binding.tvPrice.setText("Price - "+clickData.price.toString())
        binding.tvRating.setText("Rating - "+clickData.rating.toString())

        val viewPager2Adapter = ViewPager2Adapter(requireContext(), requireActivity(), clickData.images)

        binding.viewpager.setAdapter(viewPager2Adapter)


        binding.viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        binding.ivLikeUnlike.setOnClickListener {
            if(isLike) {
                viewModel.deleteById(clickData.id.toString())
            } else{
                var productForStore = ProductForStore(
                    clickData.brand,
                    clickData.category,
                    clickData.description,
                    clickData.discountPercentage,
                    clickData.id,
                    clickData.images,
                    clickData.price,
                    clickData.rating,
                    clickData.stock,
                    clickData.thumbnail,
                    clickData.title

                )

                viewModel.addProduct(productForStore)
            }
        }

        viewModel.getProduct(clickData.id.toString())
        viewModel.authenticationGetByIdState.observe(viewLifecycleOwner) {
            if(it != null){
                isLike = true
                binding.ivLikeUnlike.setImageResource(R.drawable.like)
            } else {
                isLike = false
                binding.ivLikeUnlike.setImageResource(R.drawable.unlike)
            }
        }
        viewModel.authenticationDeleteState.observe(viewLifecycleOwner) {
                isLike = false
                binding.ivLikeUnlike.setImageResource(R.drawable.unlike)
        }
        viewModel.authenticationAddUserState.observe(viewLifecycleOwner) {
            isLike = true
            binding.ivLikeUnlike.setImageResource(R.drawable.like)
        }

    }

}