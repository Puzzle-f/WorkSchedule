package com.example.workschedule.ui.weekend

import android.os.Bundle
import androidx.navigation.findNavController
import com.example.workschedule.databinding.FragmentWeekendBinding
import com.example.workschedule.ui.base.BaseFragment
import com.example.workschedule.ui.driver_edit.DriverEditFragment
import org.koin.android.viewmodel.ext.android.viewModel

class WeekendFragment :
    BaseFragment<FragmentWeekendBinding>(FragmentWeekendBinding::inflate) {

    private val weekendViewModel: WeekendViewModel by viewModel()
    private var driverId: Int? = null

    override fun readArguments(bundle: Bundle) {
        driverId = bundle.getInt(DriverEditFragment.DRIVER_ID)
    }

    override fun initView() {

    }

    override fun initListeners() {
        with(binding) {
            driverEditFragmentCancelButton.setOnClickListener {
                it.findNavController().navigateUp()
            }

            driverEditFragmentSaveButton.setOnClickListener {

            }
        }
    }

    override fun initObservers() {

    }

}


//class MainActivity0 : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
//
//    private lateinit var emailView: TextView
//    private lateinit var dobView: TextView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        emailView = findViewById(R.id.email)
//        dobView = findViewById(R.id.dob)
//        dobView.setOnClickListener {
//            showDatePickerDialog()
//        }
//    }
//
//    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//        val calendar = Calendar.getInstance()
//        calendar.set(year, month, dayOfMonth)
//
//        val dateString = "${dayOfMonth}.${month + 1}.$year"
//        dobView.text = dateString
//    }
//
//    private fun showDatePickerDialog() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val dpd = DatePickerDialog(this, this, year, month, day)
//        dpd.show()
//    }
//
//    fun onSaveButtonClick(view: View) {
//        val email = emailView.text.toString()
//        val dob = dobView.text.toString()
//        // Добавьте ваш код сохранения email и dob в базе данных
//    }
//}