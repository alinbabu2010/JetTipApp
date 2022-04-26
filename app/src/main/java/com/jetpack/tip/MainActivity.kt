package com.jetpack.tip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.tip.components.InputField
import com.jetpack.tip.components.SplitRow
import com.jetpack.tip.components.TipPercentageSlider
import com.jetpack.tip.components.TipRow
import com.jetpack.tip.ui.theme.JetTipAppTheme
import com.jetpack.tip.ui.theme.Purple100
import com.jetpack.tip.utils.calculateTotalPerPerson
import com.jetpack.tip.utils.calculateTotalTip

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootView()
        }
    }
}

@Preview(showBackground = true)
@ExperimentalComposeUiApi
@Composable
fun RootView() {
    ContentView {
        MainContent()
    }
}

@Composable
fun ContentView(content: @Composable () -> Unit) {
    JetTipAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}

@Composable
fun TopHeader(totalPerPerson: Double = 120.00) {
    val total = "%.2f".format(totalPerPerson)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Purple100
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.total_per_person),
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "$$total",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold
            )
        }

    }
}


@ExperimentalComposeUiApi
//@Preview
@Composable
fun MainContent() {
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }
    val totalBillState = remember {
        mutableStateOf("")
    }
    BillForm(tipAmountState, totalBillState, totalPerPersonState)
}

@ExperimentalComposeUiApi
@Composable
fun BillForm(
    tipAmountState: MutableState<Double>,
    totalBillState: MutableState<String>,
    totalPerPersonState: MutableState<Double>
) {

    val splitByState = remember {
        mutableStateOf(1)
    }
    val tipPercentageState = remember {
        mutableStateOf(0)
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        TopHeader(totalPerPersonState.value)
        Surface(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(4.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                InputField(
                    valueState = totalBillState,
                    labelId = "Enter Bill",
                    enabled = true,
                    isSingleLine = true,
                    onAction = KeyboardActions {
                        if (!validState) return@KeyboardActions
                        keyboardController?.hide()
                    }
                )
                if (validState) {
                    SplitRow(splitByState) {
                        tipAmountState.value =
                            calculateTotalTip(tipPercentageState.value, totalBillState.value.toDouble())
                        totalPerPersonState.value = calculateTotalPerPerson(
                            splitByState.value,
                            tipPercentageState.value,
                            totalBillState.value.toDouble()
                        )
                    }
                    TipRow(tipAmountState.value)
                    TipPercentageSlider(tipPercentageState) {
                        tipAmountState.value =
                            calculateTotalTip(tipPercentageState.value, totalBillState.value.toDouble())
                        totalPerPersonState.value = calculateTotalPerPerson(
                            splitByState.value,
                            tipPercentageState.value,
                            totalBillState.value.toDouble()
                        )
                    }
                } else {
                    Box {}
                }
            }
        }
    }
}