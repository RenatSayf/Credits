package com.zaimutest777.zaim.models.credits

data class CreditsList(
    val creditcards: List<Creditcard>,
    val credits: List<Credit>,
    val debitcards: List<Debitcard>,
    val loans: List<Loan>,
    val rasrochka: List<Rasrochka>
)