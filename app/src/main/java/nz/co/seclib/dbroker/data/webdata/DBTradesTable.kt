package nz.co.seclib.dbroker.data.webdata

import nz.co.seclib.dbroker.data.database.TradeLog

class TradesTable {
    var tradesList : MutableList<TradesRow> = mutableListOf()

    fun AddRow(row: TradesRow)
    {
        tradesList.add(row)
    }

    //convert table to list with certain stockCode.
    fun getTradeLogListFromTable(stockCode: String):List<TradeLog>{
        val tradeLogList = mutableListOf<TradeLog>()

        if (tradesList.size < 1) return tradeLogList

        tradesList.forEach{
            val tradeLog = TradeLog(
                0,
                stockCode,
                it.tradeVolume,
                it.tradeTime,
                it.price,
                it.tradeCondition
            )
            tradeLogList.add(tradeLog)
        }

        return tradeLogList
    }

    fun GetRecentTradesTableFromString(sIn: String){
//        <TABLE id="tblRecentTrades" class="dgtbl highlightevenrow" border="0" cellSpacing="0" cellPadding="0">
//        <tr>
//        <td class="dghdtitlebg" colspan="4">
//        <table cellpadding="0" cellspacing="0" border="0" width="100%">
//        <tr>
//        <td class="dghdtitle">Recent Trades</td>
//        <td class="dghdtitle-right"></td>
//        </tr>
//        </table>
//        </td>
//        </tr>
//        <tr class="dghdTR">
//        <td align="Right" class="dghdfirstcolumn">Price</td>
//        <td align="Right">Volume</td>
//        <td align="Center">Time</td>
//        <td align="Center" class="dghdlastcolumn">Cond</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">36,061</td>
//        <td align="Center">17:18</td>
//        <td align="Center" class="dgitlastcolumn">SP</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">1,952</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">15,000</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">15,000</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">11,208</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">6,857</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">15,000</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">1,874</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">32,707</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">2,544</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">1,981</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">91</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">4,254</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">10,746</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">8,656</td>
//        <td align="Center">17:00</td>
//        <td align="Center" class="dgitlastcolumn">  </td>
//        </tr>
//        </TABLE>

        if(sIn.indexOf("Cond") < 0 ) return
        var sTemp = sIn.substring(sIn.indexOf("Cond"))
        var iTagStartPos = sTemp.indexOf("<tr")

        tradesList.clear()
        while ( iTagStartPos > 0 )
        {
            val iTagEndPos = sTemp.indexOf("</tr>",iTagStartPos) + 5
            if(iTagEndPos > 5) {
                val sRow = sTemp.substring(iTagStartPos, iTagEndPos )

                val recentTradesRow =
                    TradesRow()
                recentTradesRow.GetRowFromString(sRow)
                tradesList.add(recentTradesRow)

                sTemp = sTemp.substring(iTagEndPos)
            }
            iTagStartPos = sTemp.indexOf("<tr")
        }
    }

}

class TradesRow{
    var tradeVolume : String = ""
    var tradeTime : String = ""
    var price : String = ""
    var tradeCondition: String = ""

    fun GetRowFromString(sIn:String){
//        <tr class="dgitTR">
//        <td align="Right" class="dgitfirstcolumn">79</td>
//        <td align="Right">36,061</td>
//        <td align="Center">17:18</td>
//        <td align="Center" class="dgitlastcolumn">SP</td>
//        </tr>

        var sTemp = sIn

        //1. extract price
        var iTagPos = sTemp.indexOf("<td")
        if(iTagPos >=0 ){
            val iValueStartPos = sTemp.indexOf(">",iTagPos)  + 1
            if(iValueStartPos > 0 ){
                val iValueEndPos = sTemp.indexOf("<",iValueStartPos)
                if(iValueEndPos>0) {
                    price = sTemp.substring(iValueStartPos, iValueEndPos).trim()
                    sTemp = sTemp.substring(iValueEndPos)
                }
            }

        }

        //2. extract trade volume
        iTagPos = sTemp.indexOf("<td")
        if(iTagPos >=0 ){
            val iValueStartPos = sTemp.indexOf(">",iTagPos)  + 1
            if(iValueStartPos > 0 ){
                val iValueEndPos = sTemp.indexOf("<",iValueStartPos)
                if(iValueEndPos>0) {
                    tradeVolume = sTemp.substring(iValueStartPos, iValueEndPos).trim()
                    sTemp = sTemp.substring(iValueEndPos)
                }
            }

        }

        //3. extract trade time
        iTagPos = sTemp.indexOf("<td")
        if(iTagPos >=0 ){
            val iValueStartPos = sTemp.indexOf(">",iTagPos)  + 1
            if(iValueStartPos > 0 ){
                val iValueEndPos = sTemp.indexOf("<",iValueStartPos)
                if(iValueEndPos>0) {
                    tradeTime = sTemp.substring(iValueStartPos, iValueEndPos).trim()
                    sTemp = sTemp.substring(iValueEndPos)
                }
            }

        }
        //4. extract trade condition
        iTagPos = sTemp.indexOf("<td")
        if(iTagPos >=0 ){
            val iValueStartPos = sTemp.indexOf(">",iTagPos)  + 1
            if(iValueStartPos > 0 ){
                val iValueEndPos = sTemp.indexOf("<",iValueStartPos)
                if(iValueEndPos>0) {
                    tradeCondition = sTemp.substring(iValueStartPos, iValueEndPos).trim()
                    sTemp = sTemp.substring(iValueEndPos)
                }
            }

        }
    }
}