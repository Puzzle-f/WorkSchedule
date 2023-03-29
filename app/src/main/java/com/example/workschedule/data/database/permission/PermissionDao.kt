package com.example.workschedule.data.database.permission

import androidx.room.*

@Dao
interface PermissionDao {
    // Получить весь список заключений
    @Query("SELECT * FROM PermissionEntity ORDER BY id")
    suspend fun getAllPermissions(): List<PermissionEntity>

    //    получить список заключений для машиниста по id
    @Query("SELECT * FROM PermissionEntity WHERE id_driver = idDriver")
    suspend fun getAllPermissionForIdDriver(idDriver: Int): List<PermissionEntity>

    //  добавить заключения машинисту
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPermissionToDriver(permissionList: List<PermissionEntity>)

    //    удалить заключения у машиниста
    @Query("DELETE * FROM PermissionEntity WHERE id_driver = driverId" +
            "AND idDirection IN (:directionIdList)")
    suspend fun deletePermissionsToDriver(driverId: Int, directionIdList: List<Int>)

    //    удалить таблицу
    @Query("DELETE * FROM PermissionEntity")
    suspend fun deleteAllPermissions()
}