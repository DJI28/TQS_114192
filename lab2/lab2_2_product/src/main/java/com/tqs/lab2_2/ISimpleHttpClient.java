package com.tqs.lab2_2;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public interface ISimpleHttpClient {
    String doHttpGet(String url) throws IOException, ParseException;
}