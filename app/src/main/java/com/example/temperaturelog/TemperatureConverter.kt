package com.example.temperaturelog

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import java.lang.reflect.Type

class TemperatureConverter():BaseConverter<Temperature> {
    override fun serialize(
        src: Temperature?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val obj = JsonObject()
        if (src != null) {
            obj.addProperty("temperature", src.temp)
            obj.addProperty("timestamp", src.time)
        }
        return obj
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Temperature {
        val obj = json!!.asJsonObject
        val temp = obj["temperature"].asDouble
        val time = obj["timestamp"].asString
        return Temperature(temp, time)
    }
}