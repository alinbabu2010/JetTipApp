package com.jetpack.tip.utils

fun calculateTotalTip(
    tipPercentage: Int,
    totalBillAmount: Double
): Double = if (totalBillAmount > 1 && totalBillAmount.toString().isNotBlank())
    (totalBillAmount * tipPercentage) / 100 else 0.0

fun calculateTotalPerPerson(
    splitBy: Int, tipPercentage: Int,
    totalBillAmount: Double
): Double {
    val bill = calculateTotalTip(tipPercentage, totalBillAmount) + totalBillAmount
    return bill / splitBy
}