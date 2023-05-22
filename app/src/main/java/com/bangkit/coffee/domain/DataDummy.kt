package com.bangkit.coffee.domain

import com.bangkit.coffee.domain.entity.Disease
import com.bangkit.coffee.domain.entity.ImageDetection
import java.util.Date
import java.util.UUID.randomUUID
import kotlin.random.Random

val DiseaseDummies get() = List(6) { DiseaseDummy }
val DiseaseDummy
    get() = Disease(
        randomUUID().toString(),
        "Cercospora Leaf Spot ${Random.nextInt(100)}",
        "Cercospora coffeicola",
        "This fungal disease occurs in coffee plantations that lack proper nutrient balance. It spreads through wind and rain splash and thrives in humid and warm environments. The symptoms can be observed in newly generated leaves and tissue, appearing as brown spots that start at the edges of the coffee leaves and spread toward the center. The disease can also be seen on the branches, starting at the spots where leaves have fallen.",
        listOf(
            "Keep a balance and controlled fertilization plan, add organic matter to your soil, and balance the shadow& lighting of your plantation.",
            "Fungicides that contain copper and triazoles are effective in combating this disease."
        ),
        "https://picsum.photos/300"
    )

val ImageDetectionDummies get() = List(6) { ImageDetectionDummy }
val ImageDetectionDummy
    get() = ImageDetection(
        randomUUID().toString(),
        randomUUID().toString(),
        "https://picsum.photos/300",
        "Cercospora Leaf Spot",
        Date(),
        Date()
    )