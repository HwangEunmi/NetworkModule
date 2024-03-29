package com.kakaovx.practice.buildsrc

object Versions {
    internal const val ACTIVITY_KTX = "1.6.1"
    internal const val FRAGMENT_KTX = "1.5.4"
    internal const val VIEWMODEL_KTX = "2.5.1"
    internal const val LIVEDATA_KTX = "2.6.0-alpha03"
    internal const val HILT = "2.44"
    internal const val RETROFIT = "2.9.0"
    internal const val OKHTTP = "4.10.0"
    internal const val COROUTINE = "1.6.4"
    internal const val LIFECYCLE = "2.5.1"
}

object Dependencies {
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.VIEWMODEL_KTX}"
    const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIVEDATA_KTX}"

    const val hilt = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.HILT}"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val retrofitConverterScalars = "com.squareup.retrofit2:converter-scalars:${Versions.RETROFIT}"

    const val okhttpPlatform = "com.squareup.okhttp3:okhttp-bom:${Versions.OKHTTP}"
    const val okhttp = "com.squareup.okhttp3:okhttp"
    const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor"

    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINE}"
    const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINE}"

    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
}