package com.ricu.ricukotlin.domain.gallery.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.StringExpression
import com.querydsl.jpa.JPQLQuery
import com.ricu.ricukotlin.domain.gallery.dto.GallerySearchKeywordDTO
import com.ricu.ricukotlin.domain.gallery.model.Gallery
import com.ricu.ricukotlin.domain.gallery.model.QGallery
import com.ricu.ricukotlin.global.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import kotlin.reflect.full.memberProperties

class QueryDslGalleryRepositoryImpl: QueryDslSupport(), QueryDslGalleryRepository {
    private val gallery = QGallery.gallery
    override fun searchGallery(keywordDTO: GallerySearchKeywordDTO, pageable: Pageable): Page<Gallery> {
        val whereClause = BooleanBuilder()
        val (title,explanation) = keywordDTO
        title?.let {whereClause.or(gallery.title.contains(it))}
        explanation?.let { whereClause.or(gallery.explanation.contains(it)) }

        val content = queryFactory.selectFrom(gallery)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(gallery.createdTime.asc())
            .fetch()
        return PageImpl(content, pageable, queryFactory.select(gallery.count()).from(gallery).fetchOne() ?: 0L)
    }
}