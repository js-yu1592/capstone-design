package com.example.teamproject;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.ArrayList;

public class NaverCafe {
    public static final String APP_NAVER_APPSTORE = "com.nhn.android.appstore";
    public static final String CAFE_INSTALL_URL = "market://details?id=com.nhn.android.navercafe";
    public static final String CAFE_INSTALL_URL_NAVER = "appstore://store?version=7&action=view&packageName=com.nhn.android.navercafe";

    public static final String SCHEME_NAVERCAFE = "navercafe";
    public static final String AUTHORITY_CAFE = "cafe";
    public static final String AUTHORITY_READ = "read";
    public static final String AUTHORITY_WRITE = "write";
    public static final String QUERY_CAFEURL = "cafeUrl";
    public static final String QUERY_ARTICLEID = "articleId";
    public static final String QUERY_MENUID = "menuId";
    public static final String QUERY_SUBJECT = "subject";
    public static final String QUERY_CONTENTS = "contents";
    public static final String QUERY_ATTACHMENT = "attachment";
    public static final String QUERY_APPID = "appId";

    private Context context;
    private String appId;

    public NaverCafe(Context context, String appId) {
        this.context = context;
        this.appId = appId;
    }

    /**특정카페 메인
     * @param cafeUrl 카페URL
     */
    public void cafe(String cafeUrl) {
        Uri cafeUri = CafeUriBuilder.cafe(appId, cafeUrl);
        Intent cafeIntent = new Intent();
        cafeIntent.setData(cafeUri);
        try {
            context.startActivity(cafeIntent);
        } catch (ActivityNotFoundException e) {

            // 카페앱이 설치 되지 않은 경우, 모바일 웹으로 이동시,
			/*
			if (!isAppInstalled("com.nhn.android.navercafe")) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://m.cafe.naver.com/" + cafeUrl));
				context.startActivity(intent);
				return;
			}
			*/

            gotoMarket();
        }
    }

    /**특정카페 게시글 읽기
     * @param cafeUrl 카페URL
     * @param articleId 게시글ID
     */
    public void read(String cafeUrl, String articleId) {
        Uri readUri = CafeUriBuilder.read(appId, cafeUrl, articleId);
        Intent readIntent = new Intent();
        readIntent.setData(readUri);
        try {
            context.startActivity(readIntent);
        } catch (ActivityNotFoundException e) {
            gotoMarket();
        }
    }

    /**특정카페에 글쓰기
     * @param cafeUrl 카페URL
     * @param menuId 게시판ID
     * @param subject 제목
     * @param content 내용
     */
    public void write(String cafeUrl, String menuId, String subject, String content) {
        Uri writeUri = CafeUriBuilder.write(appId, cafeUrl, menuId, subject, content, null);
        Intent writeIntent = new Intent();
        writeIntent.setData(writeUri);
        try {
            context.startActivity(writeIntent);
        } catch (ActivityNotFoundException e) {
            gotoMarket();
        }
    }

    /**특정카페에 사진,동영상,첨부파일 등을 첨부하여 글쓰기
     * @param cafeUrl 카페URL
     * @param menuId 게시판ID
     * @param subject 제목
     * @param content 내용
     * @param attachments 첨부파일의 SD카드경로(다중첨부 가능, ';'로 구분)
     */
    public void write(String cafeUrl, String menuId, String subject, String content, ArrayList<String> attachments) {
        Uri writeUri = CafeUriBuilder.write(appId, cafeUrl, menuId, subject, content, attachments);
        Intent writeIntent = new Intent();
        writeIntent.setData(writeUri);
        try {
            context.startActivity(writeIntent);
        } catch (ActivityNotFoundException e) {
            gotoMarket();
        }
    }

    public static class CafeUriBuilder {

        /**카페메인으로 이동
         * @param cafeUrl 카페URL
         * @return uri
         */
        public static Uri cafe(String appId, String cafeUrl) {
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme(SCHEME_NAVERCAFE);
            uriBuilder.authority(AUTHORITY_CAFE);
            uriBuilder.appendQueryParameter(QUERY_CAFEURL, cafeUrl);
            uriBuilder.appendQueryParameter(QUERY_APPID, appId);

            return uriBuilder.build();
        }

        /**글읽기
         * @param cafeUrl 카페URL
         * @param articleId 게시물ID
         * @return uri
         */
        public static Uri read(String appId, String cafeUrl, String articleId) {
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme(SCHEME_NAVERCAFE);
            uriBuilder.authority(AUTHORITY_READ);
            uriBuilder.appendQueryParameter(QUERY_CAFEURL, cafeUrl);
            uriBuilder.appendQueryParameter(QUERY_ARTICLEID, articleId);
            uriBuilder.appendQueryParameter(QUERY_APPID, appId);

            return uriBuilder.build();
        }

        /**글쓰기
         * @param cafeUrl 카페URL
         * @param menuId 게시판ID
         * @param subject 제목
         * @param content 내용
         * @param attachments 첨부파일의 SD카드경로
         * @return uri
         */
        public static Uri write(String appId, String cafeUrl, String menuId, String subject, String content, ArrayList<String> attachments) {

            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme(SCHEME_NAVERCAFE);
            uriBuilder.authority(AUTHORITY_WRITE);
            uriBuilder.appendQueryParameter(QUERY_CAFEURL, cafeUrl);
            uriBuilder.appendQueryParameter(QUERY_MENUID, menuId);
            uriBuilder.appendQueryParameter(QUERY_SUBJECT, subject);
            uriBuilder.appendQueryParameter(QUERY_CONTENTS, content);

            if (attachments != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String aAttachment : attachments) {
                    stringBuilder.append(aAttachment);
                    stringBuilder.append(';');
                }
                String attachment = stringBuilder.length() > 0 ? stringBuilder.substring(0, stringBuilder.length() - 1) : "";
                uriBuilder.appendQueryParameter(QUERY_ATTACHMENT, attachment);
            }

            uriBuilder.appendQueryParameter(QUERY_APPID, appId);
            return uriBuilder.build();
        }
    }

    /**앱 설치 확인
     */
    public boolean isAppInstalled(String packname) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    packname, 0);
            if (info != null) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    /**마켓으로 이동
     */
    public void gotoMarket() {
        // 플레이 스토어로 바로 이동시,
        // gotoPlayStore();
        // return;

        // 네이버 앱스토어로 갈 수 없으면, 플레이 스토어로 이동시,
        if (!gotoNaverAppStore()) {
            gotoPlayStore();
        }
    }

    public boolean gotoNaverAppStore() {
        if (isAppInstalled(APP_NAVER_APPSTORE)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(CAFE_INSTALL_URL_NAVER));
            context.startActivity(intent);
            return true;
        }

        return false;
    }

    public void gotoPlayStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(CAFE_INSTALL_URL));
        context.startActivity(intent);
    }
}