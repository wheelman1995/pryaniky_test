package ru.wheelman.pryaniky_test.model.network

import com.google.gson.Gson
import com.google.gson.JsonParser
import ru.wheelman.pryaniky_test.model.entities.*
import ru.wheelman.pryaniky_test.model.entities.Result.Success

object RemoteDataMapper {

    private const val OBJECT_DATA = "data"
    private const val OBJECT_VIEW = "view"
    private const val DATA_TYPE_HZ = "hz"
    private const val DATA_TYPE_PICTURE = "picture"
    private const val DATA_TYPE_SELECTOR = "selector"
    private val jsonParser = JsonParser()
    private val gson = Gson()

    /**
     * returns Success or throws an Exception
     */
    fun mapRawData(data: String): Success {
        val jsonObject = jsonParser.parse(data).asJsonObject
        val viewObject = jsonObject.get(OBJECT_VIEW)
        val dataObject = jsonObject.get(OBJECT_DATA).asJsonArray
        val hz = gson.fromJson(dataObject[0], Hz::class.java)
        val picture = gson.fromJson(dataObject[1], Picture::class.java)
        val selector = gson.fromJson(dataObject[2], Selector::class.java)
        val view = (gson.fromJson(viewObject, List::class.java) as List<String>).map {
            when (it) {
                DATA_TYPE_HZ -> DataType.HZ
                DATA_TYPE_PICTURE -> DataType.PICTURE
                DATA_TYPE_SELECTOR -> DataType.SELECTOR
                else -> throw IllegalArgumentException("Unknown data type")
            }
        }
        return Success(Data(hz, picture, selector), view)
    }
}