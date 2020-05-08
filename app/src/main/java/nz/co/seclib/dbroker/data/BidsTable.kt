package nz.co.seclib.dbroker.data

class BidsTable {
    var bidsList : MutableList<BidsRow> = mutableListOf()

    fun AddRow(row:BidsRow)
    {
        bidsList.add(row)
    }

    fun GetBidsTableFromString(sIn: String){
//        <TABLE id="biddepth" class="dgtbl highlightevenrow" border="0" cellSpacing="0" cellPadding="0"
//        align="center">
//        <tr>
//        <td class="dghdtitlebg" colspan="3">
//        <table cellpadding="0" cellspacing="0" border="0" width="100%">
//        <tr>
//        <td class="dghdtitle rtalign">Bids</td>
//        <td class="dghdtitle-right"></td>
//        </tr>
//        </table>
//        </td>
//        </tr>
//        <tr class="dghdTR">
//        <td align="Right" class="dghdfirstcolumn">Quantity</td>
//        <td align="Right">No.</td>
//        <td align="Right" class="dghdlastcolumn">Price</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>135,285
//        <img src="../images/spacer.gif" alt="" border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>26</td>
//        <td class='bid dgitlastcolumn ' align="Right">78</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>40,258
//        <img src="../images/depth_before.gif" class="sn-hover-bid2" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>12</td>
//        <td class='bid dgitlastcolumn ' align="Right">77</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>40,901
//        <img src="../images/depth_before.gif" class="sn-hover-bid3" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>10</td>
//        <td class='bid dgitlastcolumn ' align="Right">76</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>120,165
//        <img src="../images/depth_before.gif" class="sn-hover-bid4" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>27</td>
//        <td class='bid dgitlastcolumn ' align="Right">75</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>11,584
//        <img src="../images/depth_before.gif" class="sn-hover-bid5" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>6</td>
//        <td class='bid dgitlastcolumn ' align="Right">74</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>34,802
//        <img src="../images/depth_before.gif" class="sn-hover-bid6" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>5</td>
//        <td class='bid dgitlastcolumn ' align="Right">73</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>31,422
//        <img src="../images/depth_before.gif" class="sn-hover-bid7" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>7</td>
//        <td class='bid dgitlastcolumn ' align="Right">72</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>54,103
//        <img src="../images/depth_before.gif" class="sn-hover-bid8" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>5</td>
//        <td class='bid dgitlastcolumn ' align="Right">71</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>74,281
//        <img src="../images/depth_before.gif" class="sn-hover-bid9" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>30</td>
//        <td class='bid dgitlastcolumn ' align="Right">70</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>12,942
//        <img src="../images/depth_before.gif" class="sn-hover-bid10" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>5</td>
//        <td class='bid dgitlastcolumn ' align="Right">69</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>25,048
//        <img src="../images/depth_before.gif" class="sn-hover-bid11" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>10</td>
//        <td class='bid dgitlastcolumn ' align="Right">68</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>9,275
//        <img src="../images/depth_before.gif" class="sn-hover-bid12" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>3</td>
//        <td class='bid dgitlastcolumn ' align="Right">67</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>83,333
//        <img src="../images/depth_before.gif" class="sn-hover-bid13" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>3</td>
//        <td class='bid dgitlastcolumn ' align="Right">66</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>115,769
//        <img src="../images/depth_before.gif" class="sn-hover-bid14" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>20</td>
//        <td class='bid dgitlastcolumn ' align="Right">65</td>
//        </tr>
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>213,128
//        <img src="../images/depth_before.gif" class="sn-hover-bid15" alt=""  border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>14</td>
//        <td class='bid dgitlastcolumn ' align="Right">64</td>
//        </tr>
//        </TABLE>

        if(sIn.indexOf("Price") < 0 ) return
        var sTemp = sIn.substring(sIn.indexOf("Price"))
        var iTagStartPos = sTemp.indexOf("<tr")

        bidsList.clear()
        while ( iTagStartPos > 0 )
        {
            val iTagEndPos = sTemp.indexOf("</tr>",iTagStartPos) + 5
            if(iTagEndPos > 5) {
                val sRow = sTemp.substring(iTagStartPos, iTagEndPos )

                val bidsRow = BidsRow()
                bidsRow.GetRowFromString(sRow)
                bidsList.add(bidsRow)

                sTemp = sTemp.substring(iTagEndPos)
            }
            iTagStartPos = sTemp.indexOf("<tr")
        }
    }

}

class BidsRow{
    var quantity : String = ""
    var orderNumber : String = ""
    var price : String = ""

    fun GetRowFromString(sIn:String){
//        <tr class="dgitTR">
//        <td align="Right" class='dgitfirstcolumn '>135,285
//        <img src="../images/spacer.gif" alt="" border="0" style="height:10px;width:10px;" hspace="2"/>
//        </td>
//        <td align="Right" class=''>26</td>
//        <td class='bid dgitlastcolumn ' align="Right">78</td>
//        </tr>

        var sTemp = sIn

        //1. extract quantity
        var iTagPos = sTemp.indexOf("<td")
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

        //3. extract price
        iTagPos = sTemp.indexOf("<td")
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
    }
}