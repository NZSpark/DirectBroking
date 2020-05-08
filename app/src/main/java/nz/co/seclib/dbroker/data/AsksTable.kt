package nz.co.seclib.dbroker.data

class AsksTable {
    var asksList : MutableList<AsksRow> = mutableListOf()

    fun AddRow(row:AsksRow)
    {
        asksList.add(row)
    }

    fun GetAsksTableFromString(sIn: String){
//        <TABLE id="askdepth" class="dgtbl highlightevenrow" border="0" cellSpacing="0" cellPadding="0"
//        align="center">
//        <tr>
//        <td class="dghdtitlebg" colspan="3">
//        <table cellpadding="0" cellspacing="0" border="0" width="100%">
//        <tr>
//        <td class="dghdtitle">Asks</td>
//        <td class="dghdtitle-right"></td>
//        </tr>
//        </table>
//        </td>
//        </tr>
//        <tr class="dghdTR">
//        <td align="Right" class="dghdfirstcolumn">Price</td>
//        <td align="Right">No.</td>
//        <td align="Right" class="dghdlastcolumn">Quantity</td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">79</td>
//        <td align="Right" class=''>1</td>
//        <td align="Right" class='dgitlastcolumn '>50,000
//        <img src="../images/spacer.gif" alt="" border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">81</td>
//        <td align="Right" class=''>1</td>
//        <td align="Right" class='dgitlastcolumn '>5,553
//        <img src="../images/depth_before.gif" class="sn-hover-ask2" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">82</td>
//        <td align="Right" class=''>2</td>
//        <td align="Right" class='dgitlastcolumn '>2,115
//        <img src="../images/depth_before.gif" class="sn-hover-ask3" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">83</td>
//        <td align="Right" class=''>4</td>
//        <td align="Right" class='dgitlastcolumn '>33,427
//        <img src="../images/depth_before.gif" class="sn-hover-ask4" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">84</td>
//        <td align="Right" class=''>10</td>
//        <td align="Right" class='dgitlastcolumn '>91,902
//        <img src="../images/depth_before.gif" class="sn-hover-ask5" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">85</td>
//        <td align="Right" class=''>24</td>
//        <td align="Right" class='dgitlastcolumn '>110,227
//        <img src="../images/depth_before.gif" class="sn-hover-ask6" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">86</td>
//        <td align="Right" class=''>6</td>
//        <td align="Right" class='dgitlastcolumn '>70,012
//        <img src="../images/depth_before.gif" class="sn-hover-ask7" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">87</td>
//        <td align="Right" class=''>6</td>
//        <td align="Right" class='dgitlastcolumn '>10,711
//        <img src="../images/depth_before.gif" class="sn-hover-ask8" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">88</td>
//        <td align="Right" class=''>7</td>
//        <td align="Right" class='dgitlastcolumn '>58,952
//        <img src="../images/depth_before.gif" class="sn-hover-ask9" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">89</td>
//        <td align="Right" class=''>5</td>
//        <td align="Right" class='dgitlastcolumn '>35,665
//        <img src="../images/depth_before.gif" class="sn-hover-ask10" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">90</td>
//        <td align="Right" class=''>12</td>
//        <td align="Right" class='dgitlastcolumn '>133,606
//        <img src="../images/depth_before.gif" class="sn-hover-ask11" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">92</td>
//        <td align="Right" class=''>1</td>
//        <td align="Right" class='dgitlastcolumn '>15,000
//        <img src="../images/depth_before.gif" class="sn-hover-ask12" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">93</td>
//        <td align="Right" class=''>2</td>
//        <td align="Right" class='dgitlastcolumn '>560
//        <img src="../images/depth_before.gif" class="sn-hover-ask13" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">94</td>
//        <td align="Right" class=''>2</td>
//        <td align="Right" class='dgitlastcolumn '>31,200
//        <img src="../images/depth_before.gif" class="sn-hover-ask14" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">95</td>
//        <td align="Right" class=''>5</td>
//        <td align="Right" class='dgitlastcolumn '>24,285
//        <img src="../images/depth_before.gif" class="sn-hover-ask15" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        </tr>
//        </TABLE>

        if(sIn.indexOf("Quantity") < 0 ) return
        var sTemp = sIn.substring(sIn.indexOf("Quantity"))
        var iTagStartPos = sTemp.indexOf("<tr")
        asksList.clear()
        while ( iTagStartPos > 0 )
        {
            val iTagEndPos = sTemp.indexOf("</tr>",iTagStartPos) + 5
            if(iTagEndPos > 5) {
                val sRow = sTemp.substring(iTagStartPos, iTagEndPos )

                val asksRow = AsksRow()
                asksRow.GetRowFromString(sRow)
                asksList.add(asksRow)

                sTemp = sTemp.substring(iTagEndPos)
            }
            iTagStartPos = sTemp.indexOf("<tr")
        }
    }

}

class AsksRow{
    var quantity : String = ""
    var orderNumber : String = ""
    var price : String = ""

    fun GetRowFromString(sIn:String){
//        <tr class="dgitTR">
//        <td class='ask dgitfirstcolumn ' align="Right">79</td>
//        <td align="Right" class=''>1</td>
//        <td align="Right" class='dgitlastcolumn '>50,000
//        <img src="../images/spacer.gif" alt="" border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
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

        //2. extract order number
        iTagPos = sTemp.indexOf("<td")
        if(iTagPos >=0 ){
            val iValueStartPos = sTemp.indexOf(">",iTagPos)  + 1
            if(iValueStartPos > 0 ){
                val iValueEndPos = sTemp.indexOf("<",iValueStartPos)
                if(iValueEndPos>0) {
                    orderNumber = sTemp.substring(iValueStartPos, iValueEndPos).trim()
                    sTemp = sTemp.substring(iValueEndPos)
                }
            }

        }

        //3. extract quantity
        iTagPos = sTemp.indexOf("<td")
        if(iTagPos >=0 ){
            val iValueStartPos = sTemp.indexOf(">",iTagPos)  + 1
            if(iValueStartPos > 0 ){
                val iValueEndPos = sTemp.indexOf("<",iValueStartPos)
                if(iValueEndPos>0) {
                    quantity = sTemp.substring(iValueStartPos, iValueEndPos).trim()
                    sTemp = sTemp.substring(iValueEndPos)
                }
            }

        }
    }
}