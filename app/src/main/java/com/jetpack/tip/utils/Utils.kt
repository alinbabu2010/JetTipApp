package com.jetpack.tip.utils

fun calculateAmount(
    splitBy: Int,
    tipPercentage: Int,
    totalBillAmount: Double,
    onCalculated: (Double, Double) -> Unit
) {
    val totalTip = if (totalBillAmount > 1 && totalBillAmount.toString().isNotBlank())
        (totalBillAmount * tipPercentage) / 100 else 0.0
    val totalPerPerson = (totalTip + totalBillAmount) / splitBy
    onCalculated(totalTip, totalPerPerson)
}