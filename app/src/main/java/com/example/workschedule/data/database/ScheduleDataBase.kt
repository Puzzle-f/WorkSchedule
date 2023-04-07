package com.example.workschedule.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.workschedule.data.database.block.BlockEntity
import com.example.workschedule.data.database.direction.DirectionDao
import com.example.workschedule.data.database.direction.DirectionEntity
import com.example.workschedule.data.database.driver.DriverDao
import com.example.workschedule.data.database.driver.DriverEntity
import com.example.workschedule.data.database.permission.PermissionDao
import com.example.workschedule.data.database.permission.PermissionEntity
import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.data.database.trainrun.TrainRunDao
import com.example.workschedule.data.database.trainrun.TrainRunEntity
import com.example.workschedule.data.database.weekend.WeekendDao
import com.example.workschedule.data.database.weekend.WeekendEntity

@Database(
    entities = [BlockEntity::class, DirectionEntity::class, DriverEntity::class, PermissionEntity::class,
                StatusEntity::class, TrainRunEntity::class, WeekendEntity::class],
    version = 2,
    exportSchema = true
)

abstract class ScheduleDataBase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
    abstract fun directionDao(): DirectionDao
    abstract fun trainRunDao(): TrainRunDao
    abstract fun permissionDao(): PermissionDao
    abstract fun weekendDao(): WeekendDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE TrainRunEntity ADD COLUMN isEditManually BIT"
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
//        создаем новую таблицу сo startTime типа INTEGER (будем хранить Long)
        database.execSQL(
            "CREATE TABLE train_run_new (id INTEGER, " +
                    "trainID INTEGER, " +
                    "trainNumber INTEGER, " +
                    "trainDirection TEXT, " +
                    "trainPeriodicity INTEGER, " +
                    "driverId INTEGER, " +
                    "driverName TEXT, " +
                    "startTime INTEGER, " +
                    "travelTime INTEGER, " +
                    "travelRestTime INTEGER, " +
                    "backTravelTime INTEGER, " +
                    "isEditManually BIT)"
        )
//      копируем данные в новую таблицу из старой
        database.execSQL(
            "INSERT INTO train_run_new(id, trainID, trainNumber, " +
                    "trainDirection, trainPeriodicity, driverId, " +
                    "driverName, startTime, travelTime, travelRestTime, " +
                    "backTravelTime, isEditManually) " +
                    "SELECT id, trainID, " +
                    "trainNumber, trainDirection, trainPeriodicity, " +
                    "driverId, driverName, startTime, travelTime, " +
                    "travelRestTime, backTravelTime, isEditManually FROM TrainRunEntity"
        )
//        удаляем старую таблицу
        database.execSQL(
            "DROP TABLE TrainRunEntity"
        )
//        переименовываем новую таблицу в старую
        database.execSQL(
            "ALTER TABLE train_run_new RENAME TO TrainRunEntity"
        )
    }
}
