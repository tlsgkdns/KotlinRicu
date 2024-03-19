package com.ricu.ricukotlin.domain.image.model

import com.ricu.ricukotlin.global.common.BaseTimeEntity
import com.ricu.ricukotlin.global.exception.exception.InvalidImageNameException
import jakarta.persistence.*
import java.util.UUID

@Entity
class Image(
    @Id
    var uuid: String = UUID.randomUUID().toString(),
    var filename: String = ""
): BaseTimeEntity()
{
    fun getLink(): String
    {
        return uuid + "_" + filename
    }
    companion object
    {
        fun from(imageName: String?): Image?
        {
            imageName ?: return null
            val imageNameArray = imageName.split("_")
                .also { if(it.size < 2) throw InvalidImageNameException() }
            return Image(imageNameArray[0], imageNameArray[1])
        }
    }
}