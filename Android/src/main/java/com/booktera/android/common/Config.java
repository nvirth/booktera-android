package com.booktera.android.common;

import com.booktera.android.BuildConfig;

/**
 * Created by Norbert on 2015.02.07..
 */
public class Config
{
    private static final String _imagesUrlDir = "http://192.168.1.100:50308/Content/Images";
    public static final String ProductImagesUrlDir = _imagesUrlDir + "/ProductImages";
    public static final String ProductImagesUrlDirFormat = ProductImagesUrlDir + "/%1s";
    public static final String UserImagesUrlDir = _imagesUrlDir + "/UserImages";
    public static final String UserImagesUrlDirFormat = UserImagesUrlDir + "/%1s";

    public static final boolean IsDebug = BuildConfig.DEBUG;
}
