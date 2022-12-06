package com.kakaovx.practice.network

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type

class ConverterTypeFactory : Converter.Factory() {
    private val scalars: Converter.Factory = ScalarsConverterFactory.create()
    private val json: Converter.Factory = GsonConverterFactory.create()

    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        for (annotation in annotations) {
            return if (annotation.annotationClass.toString().contains(Scalars::class.java.toString())) {
                scalars.responseBodyConverter(type, annotations, retrofit)
            } else {
                json.responseBodyConverter(type, annotations, retrofit)
            }
        }

        return null
    }
}