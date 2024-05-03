package com.ppb.pawspal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ppb.pawspal.databinding.FragmentCartBinding
import controller.ListItemAdapter
import data.Item

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val adapter = ListItemAdapter()
    private val list = ArrayList<Item>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvItem.setHasFixedSize(true)

        val myArrayList = arguments?.getSerializable("myArrayList") as? ArrayList<Item>
        if (myArrayList != null) {

            val getArrayListCart = arguments?.getSerializable("cartList") as? ArrayList<Pair<String, Int>>

            val arrayListCart = arrayListOf<Item>()

            for ((id, quantity) in getArrayListCart!!) {
                val item = myArrayList.find { it.id == id }
                if (item != null) {
                    repeat(quantity) {
                        arrayListCart.add(item)
                    }
                }
            }

            arrayListCart.forEach { println(it) }

            setListItems(arrayListCart)
            Log.w("HomeFragment Created", "Size: " + arrayListCart.count())

            setUpAction()
            showRecyclerList()
        } else {
            // Handle jika myArrayList null
            Log.w("ListItem Created", "Null")
        }

    }

    fun setListItems(listItem: ArrayList<Item>){
        list.addAll(listItem)
        adapter.setItemData(list)
    }

    private fun showRecyclerList() {
        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItem.adapter = adapter
    }

    private fun setUpAction(){
        adapter.setOnItemClickCallback(object: ListItemAdapter.OnItemClickCallBack{
            override fun onItemClick(data: Item) {
                showSelectedItem(data)
            }

        })
    }
    fun showSelectedItem(data: Item){
        val intent = Intent(requireContext(),DetailItemActivity::class.java)
        intent.putExtra("name", data)
        startActivity(intent)
    }







    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}