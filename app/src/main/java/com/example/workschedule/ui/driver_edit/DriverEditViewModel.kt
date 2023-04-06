package com.example.workschedule.ui.driver_edit

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Direction
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Permission
import com.example.workschedule.domain.usecases.driver.GetDriverUseCase
import com.example.workschedule.domain.usecases.driver.SaveDriverUseCase
import com.example.workschedule.domain.usecases.driver.UpdateDriverUseCase
import com.example.workschedule.domain.usecases.permission.AddPermissionsUseCase
import com.example.workschedule.domain.usecases.permission.DeletePermissionUseCase
import com.example.workschedule.domain.usecases.permission.GetPermissionsForDriverUseCase
import com.example.workschedule.domain.usecases.train.GetAllDirectionsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DriverEditViewModel(
    private val getDriverUseCase: GetDriverUseCase,
    private val getAllDirectionsListUseCase: GetAllDirectionsListUseCase,
    private val saveDriverUseCase: SaveDriverUseCase,
    private val savePermissions: AddPermissionsUseCase,
    private val getPermissionsForDriverUseCase: GetPermissionsForDriverUseCase,
    private val updateDriverUseCase: UpdateDriverUseCase,
    private val deletePermissionUseCase: DeletePermissionUseCase,
) : ViewModel() {
    private var _driver = MutableStateFlow<Driver?>(null)
    val driver: StateFlow<Driver?> = _driver.asStateFlow()
    private val bundlePermissions = Bundle()

    private var _directions = MutableStateFlow<List<Direction>>(emptyList())
    val directions: StateFlow<List<Direction>> = _directions.asStateFlow()

    private var _permissions = MutableStateFlow<List<Permission>>(emptyList())
    val permissions: StateFlow<List<Permission>> = _permissions.asStateFlow()

    fun getDriver(driverId: Int) {
        viewModelScope.launch {
            _driver.emit(withContext(Dispatchers.IO) { getDriverUseCase.execute(driverId) })
        }
    }

    fun getDirections() {
        viewModelScope.launch {
            _directions.emit(withContext(Dispatchers.IO) { getAllDirectionsListUseCase.execute() })
        }
    }

    fun getPermissions(driverId: Int) {
        viewModelScope.launch {
            _permissions.emit(withContext(Dispatchers.IO) {
                getPermissionsForDriverUseCase.execute(
                    driverId
                )
            })
        }
        bundlePermissions.putIntegerArrayList(KEY_PERMISSION,
            permissions.value.map { it.idDirection } as ArrayList<Int>)
    }

    fun saveDriver(driver: Driver) {
        viewModelScope.launch(Dispatchers.IO) {
            saveDriverUseCase.execute(driver)
        }
    }

    fun updateDriver(driver: Driver) {
        viewModelScope.launch(Dispatchers.IO) {
            updateDriverUseCase.execute(driver)
        }
    }

    fun savePermissions(p: List<Int>) {
        viewModelScope.launch {
            p.forEach { idPermission ->
                if (driver.value != null && !permissions.value.map { it.idDirection }
                        .contains(idPermission)) {
                    savePermissions.execute(Permission(driver.value!!.id, idPermission))
                }
            }
            permissions.value.forEach { permissionOld ->
                if (!p.contains(permissionOld.idDirection)) {
                    deletePermissionUseCase.execute(permissionOld)
                }
            }

        }
    }
}
