package com.kakaovx.practice.buildsrc

object Versions {
    internal const val RETROFIT = "2.9.0"
}

object Dependencies {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val retrofitConverterScalars = "com.squareup.retrofit2:converter-scalars:${Versions.RETROFIT}"
}