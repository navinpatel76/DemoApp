package com.it.demoapp.view.fragments

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import com.google.gson.Gson
import com.it.demoapp.R
import com.it.demoapp.databinding.ListFragmentBinding
import com.it.demoapp.utils.OnItemClickListener
import com.it.demoapp.view.adapter.FavListAdapter
import com.it.demoapp.view.adapter.MyListAdapter
import com.it.demoproject.model.Product
import com.it.demoproject.model.ProductForStore
import ladd.marshall.androidmvvmexample.viewModel.viewModels.ListViewModel


class ListFragments : Fragment() {

    private val viewModel by viewModels<ListViewModel>()
    private lateinit  var binding : ListFragmentBinding
    private lateinit var list : List<Product>
    private lateinit var favlist : List<ProductForStore>
    lateinit var dialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         binding = ListFragmentBinding.bind(view)
//        viewModel.entriesState.observe(viewLifecycleOwner) {
//                Log.i(javaClass.name, "-->"+it.message)
//
//            val adapter = MyListAdapter(it.data, onClickCallbackPracticeArea)
//            binding.rvList.setHasFixedSize(true)
//            binding.rvList.setLayoutManager(LinearLayoutManager(requireContext()))
//            binding.rvList.setAdapter(adapter)
//
//
//        }
//        viewModel.getEntries()

        viewModel.productListState.observe(viewLifecycleOwner) {
            if(it.total > 0) {
                dialog.hide()
                list = it.products
                val adapter = MyListAdapter(it.products, requireActivity(), onClickCallbackPracticeArea)
                binding.rvList.setHasFixedSize(true)
                binding.rvList.setLayoutManager(LinearLayoutManager(requireContext()))
                binding.rvList.setAdapter(adapter)
            }
        }
         dialog = ProgressDialog(context)
        dialog.setMessage("message")
        dialog.setCancelable(false)
        dialog.setInverseBackgroundForced(false)
        dialog.show()
        if(NetworkIsConnected()) {
            binding.tvNoData.visibility = View.GONE
            viewModel.getProductList()
        } else {
            binding.rvFav.visibility = View.GONE
            binding.rvList.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
            binding.tvNoData.setText("No Internet!")
        }

        binding.tvHome.setOnClickListener {
            binding.rvList.visibility = View.VISIBLE
            binding.rvFav.visibility = View.GONE
            binding.tvHedding.setText("Home")
        }

        binding.tvFav.setOnClickListener {
            binding.rvList.visibility = View.GONE
            binding.rvFav.visibility = View.VISIBLE
            viewModel.getProduct()
            binding.tvHedding.setText("Favourite")
        }
        viewModel.authenticationGetState.observe(viewLifecycleOwner) {
            Log.i(javaClass.name, ""+it.size)
            favlist = it
            if(it.size > 0) {
                binding.tvNoData.visibility = View.GONE
                binding.rvFav.visibility = View.VISIBLE
                val adapter = FavListAdapter(favlist, requireActivity(), onClickCallbackFavArea)
                binding.rvFav.setHasFixedSize(true)
                binding.rvFav.setLayoutManager(LinearLayoutManager(requireContext()))
                binding.rvFav.setAdapter(adapter)
            } else {
                binding.rvFav.visibility = View.GONE
                binding.rvList.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
            }
        }


    }

    var onClickCallbackFavArea = object : OnItemClickListener.OnClickCallback {
        override fun onClicked(view: View?, position: Int, type: String?) {
            var fragment = SecoundFragments()
            var bundle = Bundle()
//            val jsonList: String = gson.toJson(youList)
            var clickData = favlist[position]
            Log.i(javaClass.name, "onClicked==>"+clickData)
            val gson = Gson()
            bundle.putString("ClickData", gson.toJson(clickData));
            bundle.putString("isFrom", "isFav");
            fragment.arguments = bundle
            addFragmentWithBack(R.id.navHostFragment,
                fragment!!, "SecoundFragments")
        }
    }

    var onClickCallbackPracticeArea = object : OnItemClickListener.OnClickCallback {
        override fun onClicked(view: View?, position: Int, type: String?) {
            var fragment = SecoundFragments()
            var bundle = Bundle()
//            val jsonList: String = gson.toJson(youList)
            var clickData = list[position]
            Log.i(javaClass.name, "onClicked==>"+clickData)
            val gson = Gson()
            bundle.putString("ClickData", gson.toJson(clickData));
            bundle.putString("isFrom", "isHome");
            fragment.arguments = bundle
            addFragmentWithBack(R.id.navHostFragment,
                fragment!!, "SecoundFragments")
        }
    }

    fun addFragmentWithBack(containerViewId: Int, fragment: Fragment, fragmentTag: String) {
        try {
            if (activity == null) return
            fragment.enterTransition = Slide(Gravity.END)
            fragment.exitTransition = Slide(Gravity.START)
            requireActivity().supportFragmentManager.beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }

    private fun NetworkIsConnected(): Boolean {
        val cm =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }
}