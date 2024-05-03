package com.ppb.pawspal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.ppb.pawspal.databinding.FragmentPaymentBinding
import data.Item

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var paramSavedId: String? = null
    private var param2: String? = null


    val db = Firebase.firestore
    private lateinit var savedID: String
    private lateinit var listCart: ArrayList<Pair<String, Int>>

    private var totalAmount = 0

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramSavedId = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaymentBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myArrayList = arguments?.getSerializable("myArrayList") as? ArrayList<Item>
        if (myArrayList != null) {
            //Sorting

            var getlistCart = arguments?.getSerializable("cartList") as? ArrayList<Pair<String, Int>>

            if (getlistCart != null) {
                listCart = getlistCart
            }else{
                binding.textDescription.text = "There are no items in the shopping cart"
                binding.textPrice.text = "-"
                return
            }

            var shoppingDetails = "Item details\n"

            for ((id, quantity) in listCart!!) {
                // Cari detail barang dari ArrayList A
                val item = myArrayList.find { it.id == id }

                // Hitung total harga
                val totalPrice = item?.price?.times(quantity)

                // Jika detail barang ditemukan
                if (item != null && totalPrice != null) {

                    totalAmount += totalPrice

                    // Buat string detail belanja
                    val detail = "${item.name} (${item.price} x $quantity) = Rp. $totalPrice\n"

                    // Tambahkan ke string shoppingDetails
                    shoppingDetails += detail
                }
            }


            Log.w("ListItem Created", shoppingDetails)
            binding.textDescription.text = shoppingDetails
            binding.textPrice.text = "Rp. " + totalAmount

        } else {
            // Handle jika myArrayList null
            Log.w("ListItem Created", "Null")
        }

        binding.buttonPayment.setOnClickListener{
            val intent = Intent(requireContext(),PaymentConfirmActivity::class.java)
            intent.putExtra("totalPrice", totalAmount)
            intent.putExtra("listCart", listCart)
            startActivity(intent)
        }
    }







    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PaymentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}