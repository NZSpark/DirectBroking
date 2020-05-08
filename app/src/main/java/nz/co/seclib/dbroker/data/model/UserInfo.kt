package nz.co.seclib.dbroker.data.model

import androidx.room.*

@Entity (
    tableName = "UserInfo"
)
data class UserInfo (
    @PrimaryKey
    val userID : String,
    val userName : String,
    val password : String
)


@Entity (
    tableName = "UserStock",
    primaryKeys = arrayOf("userID","stockCode")
)
data class UserStock(
    val userID : String,
    val stockCode : String
)

