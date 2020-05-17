package nz.co.seclib.dbroker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//version 6: remove autogenrate ID of SystemConfigInfo table.
//version 5: add tradeList into AskBidLog table.
@Database(
    entities = arrayOf(
        TradeLog::class,
        UserInfo::class,
        UserStock::class,
        AskBidLog::class,
        SystemConfigInfo::class
    ),
    version = 6,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
public abstract class  DBrokerRoomDatabase() : RoomDatabase() {
    abstract fun dbrokerDAO() : DBrokerDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DBrokerRoomDatabase? = null

        fun getDatabase(context: Context): DBrokerRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        DBrokerRoomDatabase::class.java,
                        "dbroker_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}