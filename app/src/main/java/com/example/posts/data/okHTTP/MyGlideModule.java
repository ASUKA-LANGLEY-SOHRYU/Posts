package com.example.posts.data.okHTTP;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;

@GlideModule
public class MyGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClientBuilder().build();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory((Call.Factory) okHttpClient));
    }
}
