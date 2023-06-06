package com.bangkit.coffee.data.repository

import android.content.Context
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.entity.Disease
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiseaseRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val indexer = HashMap<String, Int>().apply {
        context.resources
            .getStringArray(R.array.diseases_id)
            .forEachIndexed { index, value -> set(value, index) }
    }

    private fun index(id: String): Int {
        return indexer[id] ?: throw IndexOutOfBoundsException()
    }

    fun getAll(): List<Disease> = indexer.map { (id, _) ->
        getOne(id)
    }

    fun getOne(id: String): Disease = with(context.resources) {
        val typedArray = obtainTypedArray(R.array.diseases_controls)
        val disease = Disease(
            id = id,
            name = getStringArray(R.array.diseases_name)[index(id)],
            description = getStringArray(R.array.diseases_description)[index(id)],
            imageURL = getStringArray(R.array.diseases_image_url)[index(id)],
            blurHash = getStringArray(R.array.diseases_blurhash)[index(id)],
            controls = getStringArray(typedArray.getResourceId(index(id), -1)).toList(),
        )

        typedArray.recycle()
        disease
    }

}