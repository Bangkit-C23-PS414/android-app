package com.bangkit.coffee.domain.entity

import androidx.annotation.StringRes
import com.bangkit.coffee.R
import com.bangkit.coffee.shared.const.LABEL_HEALTHY
import com.bangkit.coffee.shared.const.LABEL_MINER
import com.bangkit.coffee.shared.const.LABEL_PHOMA
import com.bangkit.coffee.shared.const.LABEL_RUST

data class DetectionResult(
    val id: String,
    @StringRes val name: Int,
) {
    companion object {
        val list = listOf(
            DetectionResult(LABEL_HEALTHY, R.string.label_healthy),
            DetectionResult(LABEL_MINER, R.string.label_miner),
            DetectionResult(LABEL_RUST, R.string.label_rust),
            DetectionResult(LABEL_PHOMA, R.string.label_phoma),
        )

        val set = list.toSet()
        val map = list.associateBy { it.id }

        @StringRes
        fun getName(id: String): Int {
            return map[id]?.name ?: R.string.unknown
        }
    }
}