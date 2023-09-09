package com.example.eventManager.todo.specialEvents

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import com.example.eventManager.auth.data.AuthRepository
import com.example.eventManager.core.TAG
import com.example.eventManager.databinding.FragmentSpecialEventListBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SpecialEventListFragment : Fragment(), SensorEventListener {
    private lateinit var itemListAdapter: SpecialEventsListAdapter
    private lateinit var itemsModel: SpecialEventListViewModel

    private var _binding: FragmentSpecialEventListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private var temp: Sensor? = null

    var onStart = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView")
        _binding = FragmentSpecialEventListBinding.inflate(inflater, container, false)

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated")
        if (!AuthRepository.isLoggedIn) {
            findNavController().navigate(R.id.FragmentLogin)
            return;
        }
        setupItemsList()
        binding.fab.setOnClickListener {
            Log.v(TAG, "Add new item")
            findNavController().navigate(R.id.SpecialEventEditFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroyView")
    }

    private fun setupItemsList() {
        itemListAdapter = SpecialEventsListAdapter(this)
        binding.specialEventsList.adapter = itemListAdapter
        itemsModel = ViewModelProvider(this).get(SpecialEventListViewModel::class.java)

        itemsModel.items.observe(viewLifecycleOwner, { value ->
            Log.i(TAG, "update items")
            itemListAdapter.items = value
        })

        itemsModel.loading.observe(viewLifecycleOwner, { loading ->
            Log.i(TAG, "update loading")
            binding.progress.visibility = if (loading) View.VISIBLE else View.GONE
        })

        itemsModel.loadingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.i(TAG, "update loading error")
                val message = "Loading exception ${exception.message}"
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        })

        itemsModel.refresh()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        Log.d(TAG, "onAccuracyChanged $accuracy");
    }

    override fun onSensorChanged(event: SensorEvent) {
        val temp = event.values[0]

        binding.temp.editableText.clear()
        binding.temp.editableText.append("${temp}°C")
    }

    override fun onResume() {
        super.onResume()
        temp?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}