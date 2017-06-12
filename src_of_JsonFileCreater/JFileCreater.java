
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Panasonic
 */
public class JFileCreater {

    private static JSONObject RootJason = null;
    private static Date Today = new Date();

    public static void main(String[] args) throws JSONException {
        String OriginalFileLink = args[0];
        String CorporaFileLink = args[1];
        String KeyWord = args[2];

        String pointFileName = "history.json" ;

        StringBuffer originaldatabuffer = new StringBuffer();
        if(ReadJsonFile(pointFileName, originaldatabuffer))        RootJason = new JSONObject(originaldatabuffer.toString());
        else RootJason = new JSONObject();
        //read & edit
        if (RootJason.optJSONObject("jsonName") == null) {
            try {
                RootJason.put("jsonName", "corpora_creation");
            } catch (JSONException ex) {
                Logger.getLogger(JFileCreater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //history & array
        if (RootJason.optJSONArray("history") == null) {
            try {
                JSONArray dataarray = new JSONArray();
                dataarray.put(createCorporaFileJObject(OriginalFileLink, CorporaFileLink, KeyWord));
                RootJason.put("history", dataarray);
            } catch (JSONException ex) {
                Logger.getLogger(JFileCreater.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                JSONArray dataarray = RootJason.getJSONArray("history");
                dataarray.put(createCorporaFileJObject(OriginalFileLink, CorporaFileLink, KeyWord));

            } catch (JSONException ex) {
                Logger.getLogger(JFileCreater.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        System.out.println(RootJason.toString());
        WriteJsonFile(pointFileName,RootJason.toString());
    }

    public static JSONObject createCorporaFileJObject(String a, String b, String c) throws JSONException {
        JSONObject rtjobj = new JSONObject();
        rtjobj.put("orig_file", a);
        rtjobj.put("corpora_file", b);
        rtjobj.put("word", c);
        rtjobj.put("time", getTimeStringbyFormat(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"), Today));

        return rtjobj;
    }

    public static boolean WriteJsonFile(String FN, String strbuff) {
        FileWriter writeFileJSONTXT = null;
        BufferedWriter writeFileJSONTXTBUFFER = null;

        try {
            writeFileJSONTXT = new FileWriter(FN);
            writeFileJSONTXTBUFFER = new BufferedWriter(writeFileJSONTXT);
        } catch (IOException ex) {
            Logger.getLogger(JFileCreater.class.getName()).log(Level.SEVERE, null, ex);
        }

        String tmpstr = RootJason.toString();
        try {
            writeFileJSONTXTBUFFER.write(tmpstr, 0, tmpstr.length());
        } catch (IOException ex) {
            Logger.getLogger(JFileCreater.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            writeFileJSONTXTBUFFER.close();
            writeFileJSONTXT.close();
        } catch (IOException ex) {
            Logger.getLogger(JFileCreater.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public static boolean ReadJsonFile(String FN, StringBuffer strbuff) {
        FileReader readFileJSONTXT = null;
        BufferedReader readFileJSONTXTBUFFER = null;
        try {
            readFileJSONTXT = new FileReader(FN);
            readFileJSONTXTBUFFER = new BufferedReader(readFileJSONTXT);
        } catch (Exception ex) {
            // System.out.println("input error");
            return false;
        }
        try {
            while (readFileJSONTXTBUFFER.ready()) {
                strbuff.append(readFileJSONTXTBUFFER.readLine());
            }
        } catch (IOException ex) {
            System.out.println("readline error");
            return false;
        }

        try {
            readFileJSONTXTBUFFER.close();
            readFileJSONTXT.close();
        } catch (IOException ex) {
            Logger.getLogger(JFileCreater.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    private static String getTimeStringbyFormat(SimpleDateFormat sdf, Date dat) {
        return sdf.format(dat);
    }
}
