package com.ppb.pawspal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ppb.pawspal.databinding.FragmentHomeBinding
import controller.ListItemAdapter
import data.Item

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ListItemAdapter
    private val list = ArrayList<Item>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//        binding.rvItem.setHasFixedSize(true)
//        list.addAll(getListItems())
//
//        adapter = ListItemAdapter(list)
//
//        showRecyclerList(list)
//
//        setUpAction()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvItem.setHasFixedSize(true)

        list.addAll(getListItems())

        adapter = ListItemAdapter(list)

        showRecyclerList(list)

        setUpAction()
    }

    fun getListItems(): ArrayList<Item> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)

        val listItem = ArrayList<Item>()
        for (position in dataName.indices) {
            val item = Item(
                position,
                dataName[position],
                dataDescription[position],
                dataPhoto[position]
            )
            listItem.add(item)
        }
        return listItem
    }

    private fun showRecyclerList(list: ArrayList<Item>) {
        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())
        val listItemAdapter = ListItemAdapter(list)
        binding.rvItem.adapter = listItemAdapter
    }

    private fun setUpAction(){
        adapter.setOnItemClickCallback(object: ListItemAdapter.OnItemClickCallBack{
            override fun onItemClick(data: Item) {
                Log.d("AAAA", "onItemClick: " + data.name)
                showSelectedItem(data)
            }

        })
    }
    fun showSelectedItem(data: Item){
        val intent = Intent(requireActivity(),DetailItemActivity::class.java)
        intent.putExtra("name", data.name)
        startActivity(intent)
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}