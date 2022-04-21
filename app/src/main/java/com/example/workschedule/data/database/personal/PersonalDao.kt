package com.example.workschedule.data.database.personal

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalDao {
    @Query("SELECT * FROM PersonalEntity")
    suspend fun all(): List<PersonalEntity>

    @Query("SELECT * FROM PersonalEntity WHERE id LIKE :id")
    suspend fun getPersonalById(id: Int): PersonalEntity

    @Query("SELECT * FROM PersonalEntity WHERE second_name LIKE :secondName")
    suspend fun getPersonBySecondName(secondName: String): PersonalEntity

    @Query("SELECT * FROM PersonalEntity WHERE if_work LIKE :ifWork AND if_resting LIKE :ifResting")
    suspend fun getPersonReadyToGo(ifWork: Boolean, ifResting: Boolean): List<PersonalEntity>

    @Query("SELECT * FROM PersonalEntity WHERE if_work LIKE:ifWork OR if_resting LIKE :ifResting")
    suspend fun getPersonNotReadyToGo(ifWork: Boolean, ifResting: Boolean): List<PersonalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PersonalEntity)

    @Delete
    suspend fun delete(entity: PersonalEntity)
}