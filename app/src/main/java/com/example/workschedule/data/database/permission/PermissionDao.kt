package com.example.workschedule.data.database.permission

import androidx.room.*

@Dao
interface PermissionDao {
    // Получить весь список заключений
    @Query("SELECT * FROM PermissionEntity ORDER BY id")
    suspend fun getAllPermissions(): List<PermissionEntity>

    //    получить список заключений для машиниста по id.
    @Query("SELECT * FROM PermissionEntity WHERE id_driver = :idDriver")
    suspend fun getPermissionsForDriver(idDriver: Int): List<PermissionEntity>

    //  добавить заключения машинисту
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPermissionToDriver(permissionList: List<PermissionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPermToDriverIfNotAvailable(permission: PermissionEntity)

    //    удалить заключение
    @Delete
    suspend fun deletePermissionsToDriver(permission: PermissionEntity)
    //    удалить таблицу
    @Query("DELETE FROM PermissionEntity")
    suspend fun deleteAllPermissions()
}