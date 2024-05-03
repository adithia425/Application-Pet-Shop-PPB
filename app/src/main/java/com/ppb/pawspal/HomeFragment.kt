package com.ppb.pawspal

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.ppb.pawspal.databinding.FragmentHomeBinding
import controller.ListItemAdapter
import data.Item
import io.grpc.okhttp.internal.framed.Header
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    val db = Firebase.firestore

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter = ListItemAdapter()
    private val list = ArrayList<Item>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvItem.setHasFixedSize(true)

        val myArrayList = arguments?.getSerializable("myArrayList") as? ArrayList<Item>
        if (myArrayList != null) {
            // Lakukan sesuatu dengan myArrayList
            setListItems(myArrayList)
            Log.w("HomeFragment Created", "Size: " + list.count())

            setUpAction()
            showRecyclerList()
        } else {
            // Handle jika myArrayList null
            Log.w("ListItem Created", "Null")
        }



    }
//    private fun getProducts(): ArrayList<Item> {
//        //bd.progressBar.visibility = View.VISIBLE
//
//        val listItem = ArrayList<Item>()
//
//
//        val client = AsyncHttpClient()
//        val url = "http://127.0.0.1:8888/products"
//        client.get(url, object : AsyncHttpResponseHandler() {
//            override fun onSuccess(
//                statusCode: Int,
//                headers: Array<out cz.msebera.android.httpclient.Header>?,
//                responseBody: ByteArray
//            ) {
//                val result = String(responseBody)
//                Log.d(TAG, result)
//                try {
//                    val responseObject = JSONObject(result)
//
//                    val id = responseObject.getString("id")
//                    val name = responseObject.getString("name")
//                    Log.d("Products", "produk : " + name)
//                    val desc = responseObject.getString("description")
//                    val photo = responseObject.getString("photo")
//
//                    val item = Item(
//                        id.toInt(),
//                        name,
//                        desc,
//                        photo
//                    )
//                    listItem.add(item)
//
//                } catch (e: Exception) {
//                    Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onFailure(
//                statusCode: Int,
//                headers: Array<out cz.msebera.android.httpclient.Header>?,
//                responseBody: ByteArray?,
//                error: Throwable
//            ) {
//                val errorMessage = when (statusCode) {
//                    401 -> "$statusCode : Bad Request"
//                    403 -> "$statusCode : Forbidden"
//                    404 -> "$statusCode : Not Found"
//                    else -> "$statusCode : ${error.message}"
//                }
//                Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show()
//            }
//
//        })
//
//
//        return listItem
//    }

    fun setListItems(listItem: ArrayList<Item>){
        list.addAll(listItem)
        adapter.setItemData(list)
    }
//    fun getListItems(): ArrayList<Item> {
//        val dataName = resources.getStringArray(R.array.data_name)
//        val dataDescription = resources.getStringArray(R.array.data_description)
//        val dataPhoto = resources.getStringArray(R.array.data_photo)
//
//        val listItem = ArrayList<Item>()
//        db.collection("products")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
//
//                    val item = Item(
//                        document.id,
//                        document.get("name").toString(),
//                        document.get("description").toString(),
//                        document.getLong("price")?.toInt() ?: 0,
//                        document.get("photo").toString()
//                    )
//                    listItem.add(item)
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(ContentValues.TAG, "Error getting documents.", exception)
//            }
//
////        for (position in dataName.indices) {
////            val item = Item(
////                "a",
////                dataName[position],
////                dataDescription[position],
////                1,
////                dataPhoto[position]
////            )
////            listItem.add(item)
////        }
//
//
//        adapter.setItemData(listItem)
//        setUpAction()
//
//        showRecyclerList()
//        return listItem
//    }

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


        private val TAG = HomeFragment::class.java.simpleName
    }
}