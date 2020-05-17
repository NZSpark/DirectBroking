package nz.co.seclib.dbroker.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.util.StringUtil

@Entity(
    tableName = "SystemConfigInfo"
)
data class SystemConfigInfo(
    @PrimaryKey
    val propertyName:String,
    val propertyValue:String
)