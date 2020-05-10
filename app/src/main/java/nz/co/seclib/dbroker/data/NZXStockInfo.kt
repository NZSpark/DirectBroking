package nz.co.seclib.dbroker.data

class NZXInterDayInfo {
    var year = 0
    var quarter = 0
    var month = 0
    var week = 0
    var day = 0
    var nearest_year: String = ""
    var nearest_quarter: String = ""
    var nearest_month: String = ""
    var nearest_week: String = ""
    var weekday: String = ""
    var volume = 0
    var open_price = 0f
    var high_price = 0f
    var low_price = 0f
    var close_price = 0f
    var trade_count = 0
    var date: String = ""
}

class NZXIntraDayInfo{
    var price = 0f
    var trade_count = 0
    var volume = 0
    var time : String = ""
}