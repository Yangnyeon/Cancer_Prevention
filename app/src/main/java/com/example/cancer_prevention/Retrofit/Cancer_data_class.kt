package com.example.cancer_prevention.Retrofit

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

class Cancer_data_class(
    var currentCount:Int,

    var data:List<data>,

    var matchCount: String,

    var page: String,

    var perPage: String,

    var totalCount: String,
) {
    override fun toString(): String {
        return "Consult_class(currentCount=$currentCount, " +
                "data=$data, matchCount='$matchCount'," +
                " page='$page', perPage='$perPage', " +
                "totalCount='$totalCount')"
    }
}

data class data (
    @SerializedName("발생자수(명)") var Cancer_number:String,
    @SerializedName("분율(%)") val  Cancer_number2 :String,
    @SerializedName("암종(국제질병분류코드)") val Cancer_number3 :String,
    @SerializedName("연령표준화발생률(명/10만명)") val Cancer_number4 :String,
    @SerializedName("조발생률(명/10만명)") val Cancer_number5 :String,
) {
    override fun toString(): String {
        return "data(Cancer_number='$Cancer_number', " +
                "Cancer_number2='$Cancer_number2', " +
                "Cancer_number3='$Cancer_number3', " +
                "Cancer_number4='$Cancer_number4', " +
                "Cancer_number5='$Cancer_number5')"
    }
}