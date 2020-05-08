package nz.co.seclib.dbroker.data

import nz.co.seclib.dbroker.data.model.SystemConfigInfo

class SystemConfigRepository(private val dbBrokerDao: DBrokerDAO) {

    fun getPropertyValuebyPropertyName(propertyName:String):String{
        val propertyList = dbBrokerDao.selectSystemConfigInfoByPropertyName(propertyName)
        if(propertyList.size == 0) return ""
        return propertyList[0].propertyValue.toString()
    }

    suspend fun saveProperty(propertyName: String,propertyValue:String){
        val propertyList = dbBrokerDao.selectSystemConfigInfoByPropertyName(propertyName)
        if(propertyList.size == 0) {
            val newSystemConfigInfo = SystemConfigInfo(propertyName,propertyValue)
            dbBrokerDao.insertSystemConfigInfo(newSystemConfigInfo)
        }else{
            val newSystemConfigInfo = SystemConfigInfo(propertyName,propertyValue)
            dbBrokerDao.updateSystemConfigInfo(newSystemConfigInfo)
        }
    }
}