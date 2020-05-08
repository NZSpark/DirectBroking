package nz.co.seclib.dbroker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import nz.co.seclib.dbroker.data.model.AskBidLog
import nz.co.seclib.dbroker.data.model.SystemConfigInfo
import nz.co.seclib.dbroker.data.model.TradeLog
import nz.co.seclib.dbroker.data.model.UserStock
import java.sql.RowId

@Dao
interface DBrokerDAO {

    // Table: SystemConfigInfo --begin
    @Query("SELECT * from SystemConfigInfo WHERE propertyName = :propertyName")
    fun selectSystemConfigInfoByPropertyName(propertyName:String) : List<SystemConfigInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSystemConfigInfo(newSystemConfigInfo:SystemConfigInfo)

    @Update
    suspend fun updateSystemConfigInfo(newSystemConfigInfo:SystemConfigInfo)
    // Table: SystemConfigInfo --end

    // Table: UserStock   ----- begin
    @Query("SELECT stockCode from UserStock WHERE userID = :userID")
    fun selectStockCodeByUserID(userID:String) : List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserStock(newStockCode:UserStock)

    @Delete()
    suspend fun deleteUserStock(newStockCode:UserStock)
    // Table: UserStock   ----- end

    // Table: TradeLog    ----- begin
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTradeLog(newTradeLog:TradeLog)

    @Query("SELECT * from TradeLog WHERE tradeTime > :startTime and tradeTime < :endTime and stockCode = :stockCode")
    fun selectTradeLogByTime(startTime:String,endTime:String,stockCode:String) : List<TradeLog>

    @Query("SELECT * from TradeLog WHERE stockCode = :stockCode ORDER BY id DESC LIMIT 15 ")
    fun selectTop15TradeLog(stockCode:String) : List<TradeLog>

    @Delete()
    suspend fun deleteTradeLog(tradeLog: TradeLog)
    // Table: TradeLog    ----- end

    // Table: AskBidLog   ----- begin
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAskBidLog(newAskBidLog:AskBidLog)

    @Query("SELECT * from AskBidLog WHERE tradeTime > :startTime and tradeTime < :endTime and stockCode = :stockCode")
    fun selectAskBidLogByTime(startTime:String,endTime:String,stockCode:String) : List<AskBidLog>

    @Query("SELECT * from AskBidLog WHERE stockCode = :stockCode ORDER BY id DESC LIMIT 1")
    fun selectTop1AskBidLog(stockCode:String) : List<AskBidLog>

    @Delete()
    suspend fun deleteAskBidLog(tradeLog: AskBidLog)
    // Table: AskBidLog   ----- end

}