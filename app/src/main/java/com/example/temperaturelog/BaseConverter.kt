package com.example.temperaturelog

import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer

interface BaseConverter<T>: JsonSerializer<T>, JsonDeserializer<T> {
}

