package com.ernita.myapplication.server;

public class BaseURL {

    public static String baseUrl = "http://192.168.56.1:5050/";

    public static String login    = baseUrl + "user/login";
    public static String register = baseUrl + "user/registrasi";

    //Parfum
    public static String dataParfum = baseUrl + "parfum/dataparfum";
    public static String editDataParfum = baseUrl + "parfum/ubah/";
    public static String hapusData = baseUrl + "parfum/hapus/";
    public static String inputParfum = baseUrl + "parfum/input/";
}
