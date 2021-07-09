package com.zaimutest777.zaim.models.credits

data class Creditcard(
    override val betText: String,
    override val cash: String,
    override val description: String,
    override val imageUrl: String,
    override val mastercard: String,
    override val mir: String,
    override val qiwi: String,
    override val score: String,
    override val summText: String,
    override val title: String,
    override val url: String,
    override val visa: String,
    override val yandex: String
) : Product()