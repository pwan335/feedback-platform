package com.elec5619.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.elec5619.domain.user.AuthUserDto;
import com.elec5619.domain.pm.AuthPmDto;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SendCloudAPI {

    public static void sendEmail(AuthUserDto userDto, AuthPmDto pmDto) throws IOException {

        final String url = "https://api2.sendcloud.net/api/mail/send";

        final String apiUser = "Manson_test_1hgFTc";
        final String apiKey = "6eda293ddf1180156bac292eb76fd3f8";
        final String rcptTo;
        final String rcptFrom = "123@Ajae349U5F54PqY1eOeMkFVzE2VOvS7f.sendcloud.org";
        final String fromName = "SendCloud";

//        String code = "Ma1Dc";

        String subject = "EasyFeedback Register";
        String address;

        if(userDto != null){
            rcptTo = userDto.getEmail();
            address = "users/activate/" + userDto.getUserName();
        }else{
            rcptTo = pmDto.getEmail();
            address = "pm/activate/" + pmDto.getPmName();
        }

        String link = "<a href=\"http://localhost:8080/" + address + "\">Click here to activate!</a>";

        String html = "<P>Thanks for you register!</p>" +
                "<p>Here is your activate link: " + link +"</p>";

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("apiUser", apiUser));
        params.add(new BasicNameValuePair("apiKey", apiKey));
        params.add(new BasicNameValuePair("to", rcptTo));
        params.add(new BasicNameValuePair("from", rcptFrom));
        params.add(new BasicNameValuePair("fromName", fromName));
        params.add(new BasicNameValuePair("subject", subject));
        params.add(new BasicNameValuePair("html", html));

        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = httpClient.execute(httpPost);


        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        } else {
            System.err.println("error");
        }
        httpPost.releaseConnection();
    }

    public static void sendPwd(AuthUserDto userDto, AuthPmDto pmDto) throws IOException {

        final String url = "https://api2.sendcloud.net/api/mail/send";

        final String apiUser = "Manson_test_1hgFTc";
        final String apiKey = "6eda293ddf1180156bac292eb76fd3f8";
        final String rcptTo;
        final String rcptFrom = "123@Ajae349U5F54PqY1eOeMkFVzE2VOvS7f.sendcloud.org";
        final String fromName = "SendCloud";

//        String code = "Ma1Dc";

        String subject = "EasyFeedback Forgot Password";
        String password;

        if(userDto != null){
            rcptTo = userDto.getEmail();
            password = userDto.getPassword();

        }else{
            rcptTo = pmDto.getEmail();
            password = pmDto.getPassword();
        }

        String html = "<p>You want to get new temporary password!</p>" +
                "<p>Here is your new password: " + password +"</p>";

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("apiUser", apiUser));
        params.add(new BasicNameValuePair("apiKey", apiKey));
        params.add(new BasicNameValuePair("to", rcptTo));
        params.add(new BasicNameValuePair("from", rcptFrom));
        params.add(new BasicNameValuePair("fromName", fromName));
        params.add(new BasicNameValuePair("subject", subject));
        params.add(new BasicNameValuePair("html", html));

        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = httpClient.execute(httpPost);


        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        } else {
            System.err.println("error");
        }
        httpPost.releaseConnection();
    }

//    public static void main(String[] args) throws Exception {
//        send_common(null);
//        //send_common_with_attachment();
//        //send_template();
//        //send_template_maillist();
////        send_template_with_attachment();
//
//    }
}
