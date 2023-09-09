package com.example.eventManager.todo.specialEvent

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eventManager.R
import com.example.eventManager.core.TAG
import com.example.eventManager.core.createNotification
import com.example.eventManager.databinding.FragmentSpecialEventEditBinding
import com.example.eventManager.todo.data.Item
import java.util.*
import java.util.Calendar.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SpecialEventEditFragment : Fragment() {

    companion object {
        const val ITEM_ID = "SPECIAL_EVENT_ID"
    }

    private lateinit var viewModel: SpecialEventEditViewModel
    private var itemId: String? = null
    private var item: Item? = null

    private var _binding: FragmentSpecialEventEditBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.i(TAG, "onCreateView")
        arguments?.let {
            if (it.containsKey(ITEM_ID)) {
                itemId = it.getString(ITEM_ID).toString()
            }
        }

        _binding = FragmentSpecialEventEditBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated")
        setupViewModel()
        binding.fab.setOnClickListener {
            Log.v(TAG, "Save item")

            val title = binding.specialEventTitle.text.toString()

            if(title.isEmpty()){
                Toast.makeText(activity, "Title cannot be empty!", Toast.LENGTH_LONG).show()
                //animateTitleView()
                //animateNumberOfPeopleView()
                return@setOnClickListener
            }

            val isbnString = binding.specialEventNumberOfPeople.text.toString()
            val isbnLen = binding.specialEventNumberOfPeople.text.length
            val onlyDigits = isbnString.all { it in '0'..'9'}
            if(! onlyDigits || !(isbnLen == 8)){

                Toast.makeText(activity, "Invalid ISBN - it must contain only digits and have length 8!", Toast.LENGTH_LONG).show()
                //animateTitleView()
                //animateNumberOfPeopleView()
            }
            else{
                val isbn = Integer.parseInt(isbnString)
                val day: Int = binding.specialEventDate.dayOfMonth
                val month: Int = binding.specialEventDate.month
                val year: Int = binding.specialEventDate.year
                val calendar: Calendar = getInstance()
                calendar.set(year, month, day)
                val date = calendar.time
                val isReturned = binding.specialEventIsApproved.isChecked

                val it = item
                if(it != null){
                    it.title = title
                    it.isbn = isbn
                    it.date = date
                    it.isReturned = isReturned

                    //if (it._id.isEmpty()) createNotification(this, "BookRow created", "BookRow $title successfully created")

                    viewModel.saveOrUpdateItem(it)
                }

            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroyView")
    }



    private fun animateNumberOfPeopleView() {
        ValueAnimator.ofFloat(0f, 200f, 0f).apply {
            duration = 500
            repeatCount = 1
            start()
            addUpdateListener {
                binding.specialEventNumberOfPeople.translationX = it.animatedValue as Float
            }
        }
    }

    private fun animateTitleView(){
        binding.specialEventTitle.apply {
            translationX = 0f
            visibility = View.VISIBLE
            animate().rotation(360f)
                .setDuration(1000)
                .setListener(null)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(SpecialEventEditViewModel::class.java)

        viewModel.specialEvent.observe(viewLifecycleOwner, { specialEvent ->
            Log.v(TAG, "update items")
            binding.specialEventTitle.setText(specialEvent.title)
            binding.specialEventNumberOfPeople.setText(specialEvent.isbn.toString())
            binding.specialEventIsApproved.isChecked = specialEvent.isReturned


            val calendar = getInstance()
            calendar.time = specialEvent.date

            binding.specialEventDate.updateDate(calendar.get(YEAR), calendar.get(MONTH), calendar.get(
                DAY_OF_MONTH))

        })

        viewModel.fetching.observe(viewLifecycleOwner, { fetching ->
            Log.v(TAG, "update fetching")
            binding.progress.visibility = if (fetching) View.VISIBLE else View.GONE
        })

        viewModel.fetchingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.v(TAG, "update fetching error")
                val message = "Fetching exception ${exception.message}"
                val parentActivity = activity?.parent
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.completed.observe(viewLifecycleOwner, { completed ->
            if (completed) {
                Log.v(TAG, "completed, navigate back")
                findNavController().navigate(R.id.action_SpecialEventEditFragment_to_SpecialEventListFragment)
            }
        })


        val id = itemId
        if (id == null) {
            item = Item("", "", 0, Date(), false)
        } else {
            viewModel.getItemById(id).observe(viewLifecycleOwner, { it ->
                Log.v(TAG, "update items")
                if (it != null) {
                    item = it
                    binding.specialEventTitle.setText(it.title)
                    binding.specialEventNumberOfPeople.setText(it.isbn.toString())
                    binding.specialEventIsApproved.isChecked = it.isReturned
                    val calendar = getInstance()
                    calendar.time = it.date

                    binding.specialEventDate.updateDate(calendar.get(YEAR), calendar.get(MONTH), calendar.get(
                        DAY_OF_MONTH))
                }
            })
        }
    }
}