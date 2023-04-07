package com.example.workschedule.data.database.permission

import androidx.room.*

@Dao
interface PermissionDao {
    //    получить список заключений для машиниста по id.
    @Query("SELECT * FROM PermissionEntity WHERE id_driver = :idDriver")
    suspend fun getPermissionsForDriver(idDriver: Int): List<PermissionEntity>
    //  добавить заключения машинисту
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPermissionToDriver(permissionList: PermissionEntity)
    //    удалить заключение
    @Delete
    suspend fun deletePermissionsToDriver(permission: PermissionEntity)
    //    удалить таблицу
    @Query("DELETE FROM PermissionEntity")
    suspend fun deleteAllPermissions()
}