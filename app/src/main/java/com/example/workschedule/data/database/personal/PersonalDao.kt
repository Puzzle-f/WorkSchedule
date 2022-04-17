package com.example.workschedule.data.database.personal

import androidx.room.*

@Dao
interface PersonalDao {
    @Query("SELECT * FROM PersonalEntity")
    suspend fun all(): List<PersonalEntity>

    @Query("SELECT * FROM PersonalEntity WHERE id LIKE :id")
    suspend fun getPersonalById(id: Int): PersonalEntity

    @Query("SELECT * FROM PersonalEntity WHERE second_name LIKE :secondName")
    suspend fun getPersonBySecondName(secondName: String): PersonalEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: PersonalEntity)

    @Delete
    suspend fun delete(entity: PersonalEntity)
}