package com.example.trvavelguidassistant.model;

import android.annotation.SuppressLint;

import org.json.JSONException;
import org.json.JSONObject;

public class forecastWeather {

    private String one111,one112,one113,one121,one122,one123,one131,one132,one133,one141,one142,one143,one151,one152,one153,one161,one162,one163,one171,one172,one173,one181,one182,one183;
    private String one211,one212,one213,one221,one222,one223,one231,one232,one233,one241,one242,one243,one251,one252,one253,one261,one262,one263,one271,one272,one273,one281,one282,one283;
    private String one311,one312,one313,one321,one322,one323,one331,one332,one333,one341,one342,one343,one351,one352,one353,one361,one362,one363,one371,one372,one373,one381,one382,one383;
    private String one411,one412,one413,one421,one422,one423,one431,one432,one433,one441,one442,one443,one451,one452,one453,one461,one462,one463,one471,one472,one473,one481,one482,one483;
    private String one511,one512,one513,one521,one522,one523,one531,one532,one533,one541,one542,one543,one551,one552,one553,one561,one562,one563,one571,one572,one573,one581,one582,one583;

    private String dataReceivedTime,cityName,countryCode;

    @SuppressLint("DefaultLocale")
    public static forecastWeather fromJson(JSONObject jsonObject)
    {
        try
        {
            forecastWeather weatherD=new forecastWeather();

            weatherD.cityName=jsonObject.getJSONObject("city").getString("name");
            weatherD.countryCode=jsonObject.getJSONObject("city").getString("country");
            Double tz = jsonObject.getJSONObject("city").getDouble("timezone")/3600.00;
            weatherD.dataReceivedTime = Double.toString(tz);

            weatherD.one111=jsonObject.getJSONArray("list").getJSONObject(0).getString("dt_txt");
            double tempResult1=jsonObject.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one112=String.format("%.2f", tempResult1);
            weatherD.one113=jsonObject.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one121=jsonObject.getJSONArray("list").getJSONObject(1).getString("dt_txt");
            double tempResult2=jsonObject.getJSONArray("list").getJSONObject(1).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one122=String.format("%.2f", tempResult2);
            weatherD.one123=jsonObject.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one131=jsonObject.getJSONArray("list").getJSONObject(2).getString("dt_txt");
            double tempResult3=jsonObject.getJSONArray("list").getJSONObject(2).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one132=String.format("%.2f", tempResult3);
            weatherD.one133=jsonObject.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one141=jsonObject.getJSONArray("list").getJSONObject(3).getString("dt_txt");
            double tempResult4=jsonObject.getJSONArray("list").getJSONObject(3).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one142=String.format("%.2f", tempResult4);
            weatherD.one143=jsonObject.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one151=jsonObject.getJSONArray("list").getJSONObject(4).getString("dt_txt");
            double tempResult5=jsonObject.getJSONArray("list").getJSONObject(4).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one152=String.format("%.2f", tempResult5);
            weatherD.one153=jsonObject.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one161=jsonObject.getJSONArray("list").getJSONObject(5).getString("dt_txt");
            double tempResult6=jsonObject.getJSONArray("list").getJSONObject(5).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one162=String.format("%.2f", tempResult6);
            weatherD.one163=jsonObject.getJSONArray("list").getJSONObject(5).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one171=jsonObject.getJSONArray("list").getJSONObject(6).getString("dt_txt");
            double tempResult7=jsonObject.getJSONArray("list").getJSONObject(6).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one172=String.format("%.2f", tempResult7);
            weatherD.one173=jsonObject.getJSONArray("list").getJSONObject(6).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one181=jsonObject.getJSONArray("list").getJSONObject(7).getString("dt_txt");
            double tempResult8=jsonObject.getJSONArray("list").getJSONObject(7).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one182=String.format("%.2f", tempResult8);
            weatherD.one183=jsonObject.getJSONArray("list").getJSONObject(7).getJSONArray("weather").getJSONObject(0).getString("main");

            weatherD.one211=jsonObject.getJSONArray("list").getJSONObject(8).getString("dt_txt");
            double tempResult21=jsonObject.getJSONArray("list").getJSONObject(8).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one212=String.format("%.2f", tempResult21);
            weatherD.one213=jsonObject.getJSONArray("list").getJSONObject(8).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one221=jsonObject.getJSONArray("list").getJSONObject(9).getString("dt_txt");
            double tempResult22=jsonObject.getJSONArray("list").getJSONObject(9).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one222=String.format("%.2f", tempResult22);
            weatherD.one223=jsonObject.getJSONArray("list").getJSONObject(9).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one231=jsonObject.getJSONArray("list").getJSONObject(10).getString("dt_txt");
            double tempResult23=jsonObject.getJSONArray("list").getJSONObject(10).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one232=String.format("%.2f", tempResult23);
            weatherD.one233=jsonObject.getJSONArray("list").getJSONObject(10).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one241=jsonObject.getJSONArray("list").getJSONObject(11).getString("dt_txt");
            double tempResult24=jsonObject.getJSONArray("list").getJSONObject(11).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one242=String.format("%.2f", tempResult24);
            weatherD.one243=jsonObject.getJSONArray("list").getJSONObject(11).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one251=jsonObject.getJSONArray("list").getJSONObject(12).getString("dt_txt");
            double tempResult25=jsonObject.getJSONArray("list").getJSONObject(12).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one252=String.format("%.2f", tempResult25);
            weatherD.one253=jsonObject.getJSONArray("list").getJSONObject(12).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one261=jsonObject.getJSONArray("list").getJSONObject(13).getString("dt_txt");
            double tempResult26=jsonObject.getJSONArray("list").getJSONObject(13).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one262=String.format("%.2f", tempResult26);
            weatherD.one263=jsonObject.getJSONArray("list").getJSONObject(13).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one271=jsonObject.getJSONArray("list").getJSONObject(14).getString("dt_txt");
            double tempResult27=jsonObject.getJSONArray("list").getJSONObject(14).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one272=String.format("%.2f", tempResult27);
            weatherD.one273=jsonObject.getJSONArray("list").getJSONObject(14).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one281=jsonObject.getJSONArray("list").getJSONObject(15).getString("dt_txt");
            double tempResult28=jsonObject.getJSONArray("list").getJSONObject(15).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one282=String.format("%.2f", tempResult28);
            weatherD.one283=jsonObject.getJSONArray("list").getJSONObject(15).getJSONArray("weather").getJSONObject(0).getString("main");

            weatherD.one311=jsonObject.getJSONArray("list").getJSONObject(16).getString("dt_txt");
            double tempResult31=jsonObject.getJSONArray("list").getJSONObject(16).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one312=String.format("%.2f", tempResult31);
            weatherD.one313=jsonObject.getJSONArray("list").getJSONObject(16).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one321=jsonObject.getJSONArray("list").getJSONObject(17).getString("dt_txt");
            double tempResult32=jsonObject.getJSONArray("list").getJSONObject(17).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one322=String.format("%.2f", tempResult32);
            weatherD.one323=jsonObject.getJSONArray("list").getJSONObject(17).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one331=jsonObject.getJSONArray("list").getJSONObject(18).getString("dt_txt");
            double tempResult33=jsonObject.getJSONArray("list").getJSONObject(18).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one332=String.format("%.2f", tempResult33);
            weatherD.one333=jsonObject.getJSONArray("list").getJSONObject(18).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one341=jsonObject.getJSONArray("list").getJSONObject(19).getString("dt_txt");
            double tempResult34=jsonObject.getJSONArray("list").getJSONObject(19).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one342=String.format("%.2f", tempResult34);
            weatherD.one343=jsonObject.getJSONArray("list").getJSONObject(19).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one351=jsonObject.getJSONArray("list").getJSONObject(20).getString("dt_txt");
            double tempResult35=jsonObject.getJSONArray("list").getJSONObject(20).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one352=String.format("%.2f", tempResult35);
            weatherD.one353=jsonObject.getJSONArray("list").getJSONObject(20).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one361=jsonObject.getJSONArray("list").getJSONObject(21).getString("dt_txt");
            double tempResult36=jsonObject.getJSONArray("list").getJSONObject(21).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one362=String.format("%.2f", tempResult36);
            weatherD.one363=jsonObject.getJSONArray("list").getJSONObject(21).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one371=jsonObject.getJSONArray("list").getJSONObject(22).getString("dt_txt");
            double tempResult37=jsonObject.getJSONArray("list").getJSONObject(22).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one372=String.format("%.2f", tempResult37);
            weatherD.one373=jsonObject.getJSONArray("list").getJSONObject(22).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one381=jsonObject.getJSONArray("list").getJSONObject(23).getString("dt_txt");
            double tempResult38=jsonObject.getJSONArray("list").getJSONObject(23).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one382=String.format("%.2f", tempResult38);
            weatherD.one383=jsonObject.getJSONArray("list").getJSONObject(23).getJSONArray("weather").getJSONObject(0).getString("main");

            weatherD.one411=jsonObject.getJSONArray("list").getJSONObject(24).getString("dt_txt");
            double tempResult41=jsonObject.getJSONArray("list").getJSONObject(24).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one412=String.format("%.2f", tempResult41);
            weatherD.one413=jsonObject.getJSONArray("list").getJSONObject(24).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one421=jsonObject.getJSONArray("list").getJSONObject(25).getString("dt_txt");
            double tempResult42=jsonObject.getJSONArray("list").getJSONObject(25).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one422=String.format("%.2f", tempResult42);
            weatherD.one423=jsonObject.getJSONArray("list").getJSONObject(25).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one431=jsonObject.getJSONArray("list").getJSONObject(26).getString("dt_txt");
            double tempResult43=jsonObject.getJSONArray("list").getJSONObject(26).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one432=String.format("%.2f", tempResult43);
            weatherD.one433=jsonObject.getJSONArray("list").getJSONObject(26).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one441=jsonObject.getJSONArray("list").getJSONObject(27).getString("dt_txt");
            double tempResult44=jsonObject.getJSONArray("list").getJSONObject(27).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one442=String.format("%.2f", tempResult44);
            weatherD.one443=jsonObject.getJSONArray("list").getJSONObject(27).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one451=jsonObject.getJSONArray("list").getJSONObject(28).getString("dt_txt");
            double tempResult45=jsonObject.getJSONArray("list").getJSONObject(28).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one452=String.format("%.2f", tempResult45);
            weatherD.one453=jsonObject.getJSONArray("list").getJSONObject(28).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one461=jsonObject.getJSONArray("list").getJSONObject(29).getString("dt_txt");
            double tempResult46=jsonObject.getJSONArray("list").getJSONObject(29).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one462=String.format("%.2f", tempResult46);
            weatherD.one463=jsonObject.getJSONArray("list").getJSONObject(29).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one471=jsonObject.getJSONArray("list").getJSONObject(30).getString("dt_txt");
            double tempResult47=jsonObject.getJSONArray("list").getJSONObject(30).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one472=String.format("%.2f", tempResult47);
            weatherD.one473=jsonObject.getJSONArray("list").getJSONObject(30).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one481=jsonObject.getJSONArray("list").getJSONObject(31).getString("dt_txt");
            double tempResult48=jsonObject.getJSONArray("list").getJSONObject(31).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one482=String.format("%.2f", tempResult48);
            weatherD.one483=jsonObject.getJSONArray("list").getJSONObject(31).getJSONArray("weather").getJSONObject(0).getString("main");

            weatherD.one511=jsonObject.getJSONArray("list").getJSONObject(32).getString("dt_txt");
            double tempResult51=jsonObject.getJSONArray("list").getJSONObject(32).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one512=String.format("%.2f", tempResult51);
            weatherD.one513=jsonObject.getJSONArray("list").getJSONObject(32).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one521=jsonObject.getJSONArray("list").getJSONObject(33).getString("dt_txt");
            double tempResult52=jsonObject.getJSONArray("list").getJSONObject(33).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one522=String.format("%.2f", tempResult52);
            weatherD.one523=jsonObject.getJSONArray("list").getJSONObject(33).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one531=jsonObject.getJSONArray("list").getJSONObject(34).getString("dt_txt");
            double tempResult53=jsonObject.getJSONArray("list").getJSONObject(34).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one532=String.format("%.2f", tempResult53);
            weatherD.one533=jsonObject.getJSONArray("list").getJSONObject(34).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one541=jsonObject.getJSONArray("list").getJSONObject(35).getString("dt_txt");
            double tempResult54=jsonObject.getJSONArray("list").getJSONObject(35).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one542=String.format("%.2f", tempResult54);
            weatherD.one543=jsonObject.getJSONArray("list").getJSONObject(35).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one551=jsonObject.getJSONArray("list").getJSONObject(36).getString("dt_txt");
            double tempResult55=jsonObject.getJSONArray("list").getJSONObject(36).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one552=String.format("%.2f", tempResult55);
            weatherD.one553=jsonObject.getJSONArray("list").getJSONObject(36).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one561=jsonObject.getJSONArray("list").getJSONObject(37).getString("dt_txt");
            double tempResult56=jsonObject.getJSONArray("list").getJSONObject(37).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one562=String.format("%.2f", tempResult56);
            weatherD.one563=jsonObject.getJSONArray("list").getJSONObject(37).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one571=jsonObject.getJSONArray("list").getJSONObject(38).getString("dt_txt");
            double tempResult57=jsonObject.getJSONArray("list").getJSONObject(38).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one572=String.format("%.2f", tempResult57);
            weatherD.one573=jsonObject.getJSONArray("list").getJSONObject(38).getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.one581=jsonObject.getJSONArray("list").getJSONObject(39).getString("dt_txt");
            double tempResult58=jsonObject.getJSONArray("list").getJSONObject(39).getJSONObject("main").getDouble("temp")-273.15;
            weatherD.one582=String.format("%.2f", tempResult58);
            weatherD.one583=jsonObject.getJSONArray("list").getJSONObject(39).getJSONArray("weather").getJSONObject(0).getString("main");

            return weatherD;
        }

        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDataReceivedTime() {
        return dataReceivedTime+"h GMT";
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getOne111() {
        return one111;
    }

    public String getOne112() { return one112+" °C"; }

    public String getOne113() {
        return one113;
    }

    public String getOne121() {
        return one121;
    }

    public String getOne122() {
        return one122+" °C";
    }

    public String getOne123() {
        return one123;
    }

    public String getOne131() {
        return one131;
    }

    public String getOne132() {
        return one132+" °C";
    }

    public String getOne133() {
        return one133;
    }

    public String getOne141() {
        return one141;
    }

    public String getOne142() {
        return one142+" °C";
    }

    public String getOne143() {
        return one143;
    }

    public String getOne151() {
        return one151;
    }

    public String getOne152() {
        return one152+" °C";
    }

    public String getOne153() {
        return one153;
    }

    public String getOne161() {
        return one161;
    }

    public String getOne162() {
        return one162+" °C";
    }

    public String getOne163() {
        return one163;
    }

    public String getOne171() {
        return one171;
    }

    public String getOne172() {
        return one172+" °C";
    }

    public String getOne173() {
        return one173;
    }

    public String getOne181() {
        return one181;
    }

    public String getOne182() {
        return one182+" °C";
    }

    public String getOne183() {
        return one183;
    }

    public String getOne211() {
        return one211;
    }

    public String getOne212() {
        return one212+" °C";
    }

    public String getOne213() {
        return one213;
    }

    public String getOne221() {
        return one221;
    }

    public String getOne222() {
        return one222+" °C";
    }

    public String getOne223() {
        return one223;
    }

    public String getOne231() {
        return one231;
    }

    public String getOne232() {
        return one232+" °C";
    }

    public String getOne233() {
        return one233;
    }

    public String getOne241() {
        return one241;
    }

    public String getOne242() {
        return one242+" °C";
    }

    public String getOne243() {
        return one243;
    }

    public String getOne251() {
        return one251;
    }

    public String getOne252() {
        return one252+" °C";
    }

    public String getOne253() {
        return one253;
    }

    public String getOne261() {
        return one261;
    }

    public String getOne262() {
        return one262+" °C";
    }

    public String getOne263() {
        return one263;
    }

    public String getOne271() {
        return one271;
    }

    public String getOne272() {
        return one272+" °C";
    }

    public String getOne273() {
        return one273;
    }

    public String getOne281() {
        return one281;
    }

    public String getOne282() {
        return one282+" °C";
    }

    public String getOne283() {
        return one283;
    }

    public String getOne311() {
        return one311;
    }

    public String getOne312() {
        return one312+" °C";
    }

    public String getOne313() {
        return one313;
    }

    public String getOne321() {
        return one321;
    }

    public String getOne322() {
        return one322+" °C";
    }

    public String getOne323() {
        return one323;
    }

    public String getOne331() {
        return one331;
    }

    public String getOne332() {
        return one332+" °C";
    }

    public String getOne333() {
        return one333;
    }

    public String getOne341() {
        return one341;
    }

    public String getOne342() {
        return one342+" °C";
    }

    public String getOne343() {
        return one343;
    }

    public String getOne351() {
        return one351;
    }

    public String getOne352() {
        return one352+" °C";
    }

    public String getOne353() {
        return one353;
    }

    public String getOne361() {
        return one361;
    }

    public String getOne362() {
        return one362+" °C";
    }

    public String getOne363() {
        return one363;
    }

    public String getOne371() {
        return one371;
    }

    public String getOne372() {
        return one372+" °C";
    }

    public String getOne373() {
        return one373;
    }

    public String getOne381() {
        return one381;
    }

    public String getOne382() {
        return one382+" °C";
    }

    public String getOne383() {
        return one383;
    }

    public String getOne411() {
        return one411;
    }

    public String getOne412() {
        return one412+" °C";
    }

    public String getOne413() {
        return one413;
    }

    public String getOne421() {
        return one421;
    }

    public String getOne422() {
        return one422+" °C";
    }

    public String getOne423() {
        return one423;
    }

    public String getOne431() {
        return one431;
    }

    public String getOne432() {
        return one432+" °C";
    }

    public String getOne433() {
        return one433;
    }

    public String getOne441() {
        return one441;
    }

    public String getOne442() {
        return one442+" °C";
    }

    public String getOne443() {
        return one443;
    }

    public String getOne451() {
        return one451;
    }

    public String getOne452() {
        return one452+" °C";
    }

    public String getOne453() {
        return one453;
    }

    public String getOne461() {
        return one461;
    }

    public String getOne462() {
        return one462+" °C";
    }

    public String getOne463() {
        return one463;
    }

    public String getOne471() {
        return one471;
    }

    public String getOne472() {
        return one472+" °C";
    }

    public String getOne473() {
        return one473;
    }

    public String getOne481() {
        return one481;
    }

    public String getOne482() {
        return one482+" °C";
    }

    public String getOne483() {
        return one483;
    }

    public String getOne511() {
        return one511;
    }

    public String getOne512() {
        return one512+" °C";
    }

    public String getOne513() {
        return one513;
    }

    public String getOne521() {
        return one521;
    }

    public String getOne522() {
        return one522+" °C";
    }

    public String getOne523() {
        return one523;
    }

    public String getOne531() {
        return one531;
    }

    public String getOne532() {
        return one532+" °C";
    }

    public String getOne533() {
        return one533;
    }

    public String getOne541() {
        return one541;
    }

    public String getOne542() {
        return one542+" °C";
    }

    public String getOne543() {
        return one543;
    }

    public String getOne551() {
        return one551;
    }

    public String getOne552() {
        return one552+" °C";
    }

    public String getOne553() {
        return one553;
    }

    public String getOne561() {
        return one561;
    }

    public String getOne562() {
        return one562+" °C";
    }

    public String getOne563() {
        return one563;
    }

    public String getOne571() {
        return one571;
    }

    public String getOne572() {
        return one572+" °C";
    }

    public String getOne573() {
        return one573;
    }

    public String getOne581() {
        return one581;
    }

    public String getOne582() {
        return one582+" °C";
    }

    public String getOne583() {
        return one583;
    }
}
