package com.ricu.ricukotlin.domain.image.api

import com.ricu.ricukotlin.domain.image.dto.UploadFileRequest
import com.ricu.ricukotlin.domain.image.dto.UploadResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@RestController
@RequestMapping("/images")
class UploadController(
    @Value("\${org.ricu.upload.path}")
    val uploadPath: String
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(uploadFileRequest: UploadFileRequest): MutableList<UploadResponse>?
    {
        uploadFileRequest.files?.let {
            multipartFile ->
            val returnList = mutableListOf<UploadResponse>()
            var originName = multipartFile.originalFilename
            val names = originName?.split("_")
            val newName = names?.takeIf { it.size > 1 }
                ?.fold(StringBuilder()){builder, name -> builder.append(name).append("-")}
                ?.removeSuffix("-")?.toString()
            originName = newName ?: originName
            val uuid = UUID.randomUUID().toString()
            val savePath = Paths.get(uploadPath, uuid + "_" + originName)
            multipartFile.transferTo(savePath)
            returnList.add(UploadResponse(uuid, originName))
            return returnList
        }
        return null
    }
    @GetMapping("/{filename}")
    fun viewFile(@PathVariable filename: String): ResponseEntity<Resource>
    {
        val resource = FileSystemResource(uploadPath + File.separator + filename)
        val header = HttpHeaders().apply {
            this.add("Content-Type", Files.probeContentType(resource.file.toPath()))
        }
        return ResponseEntity.status(HttpStatus.OK)
            .headers(header)
            .body(resource)
    }
    @DeleteMapping("/{filename}")
    fun removeFile(@PathVariable filename: String): ResponseEntity<Map<String, Boolean>>
    {
        val removed = FileSystemResource(uploadPath + File.separator + filename).file.delete()
        val resultMap = mapOf("result" to removed)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(resultMap)
    }
}