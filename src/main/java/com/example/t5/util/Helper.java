package com.example.t5.util;

import com.example.t5.data.Units;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Калантаев Александр on 16.03.2017.
 */
public class Helper {



    public static String UnitsToString(Units units){
        if(units == Units.KG) return "кг";
        if(units == Units.M) return "м";
        if(units == Units.M2) return "м2";
        if(units == Units.M3) return "м3";
        if(units == Units.PM) return "пог.м";
        if(units == Units.SHT) return "шт.";
        if(units == Units.TN) return "тн";
        if(units == Units.List) return "листов";
        if(units == Units.Litr) return "л.";
        if(units == Units.Par) return "пар";
        if(units == Units.Upac) return "упак";
        return null;
    }
public static String getCodeFromUnits(Units units){
        if(units == Units.KG) return "";
        if(units == Units.M) return "006";
        if(units == Units.M2) return "055";
        if(units == Units.M3) return "";
        if(units == Units.PM) return "018";
        if(units == Units.SHT) return "796";
        if(units == Units.TN) return "168";
        if(units == Units.List) return "";
        if(units == Units.Litr) return "";
        if(units == Units.Par) return "";
        if(units == Units.Upac) return "778";
        return null;
    }

    public static Units StringToUnits(String str){
        if("кг".equals(str)) return Units.KG;
        if("м".equals(str)) return Units.M;
        if("м2".equals(str)) return Units.M2;
        if("м3".equals(str)) return Units.M3;
        if("пог.м".equals(str)) return Units.PM;
        if("шт".equals(str)) return Units.SHT;
        if("тн".equals(str)) return Units.TN;
        if("листов".equals(str)) return Units.List;
        if("л.".equals(str)) return Units.Litr;
        if("пар".equals(str)) return Units.Par;
        if("упак".equals(str)) return Units.Upac;
        return null;
    }



    public static String getStringFromData(Date date){
        if(date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
        return sdf.format(date);
    }
}
