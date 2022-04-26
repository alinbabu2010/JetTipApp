package com.jetpack.tip.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.tip.ui.widgets.RoundIconButton

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        label = { Text(text = labelId) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.AttachMoney,
                contentDescription = "Money Icon"
            )
        },
        singleLine = isSingleLine,
        enabled = enabled,
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground)
    )

}

@Composable
fun SplitRow(splitByState: MutableState<Int>, onValueChanged: () -> Unit) {
    var splitCount by remember(splitByState) {
        splitByState
    }
    val splitRange = IntRange(0, 100)
    Row(
        modifier = Modifier.padding(3.dp),
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(text = "Split", modifier = Modifier.align(CenterVertically))
        Spacer(modifier = Modifier.width(120.dp))
        Row(
            modifier = Modifier.padding(horizontal = 3.dp),
            horizontalArrangement = Arrangement.End
        ) {
            RoundIconButton(imageVector = Icons.Default.Remove, onClick = {
                splitCount = if (splitCount > 1) splitCount - 1 else 1
                onValueChanged()
            })
            Text(
                text = splitCount.toString(),
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 9.dp, end = 9.dp)
            )
            RoundIconButton(imageVector = Icons.Default.Add, onClick = {
                if (splitCount < splitRange.last) splitCount += 1
                onValueChanged()
            })
        }
    }
}

@Composable
fun TipRow(tipAmount : Double = 0.0) {
    Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
        Text(text = "Tip", modifier = Modifier.align(CenterVertically))
        Spacer(modifier = Modifier.width(200.dp))
        Text(text = "$ $tipAmount", modifier = Modifier.align(CenterVertically))
    }
}

@Composable
fun TipPercentageSlider(tipPercentageState: MutableState<Int>, onValueChanged: () -> Unit) {
    var sliderPositionState by remember(tipPercentageState){
        mutableStateOf(0f)
    }
    tipPercentageState.value = (sliderPositionState*100).toInt()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${tipPercentageState.value}%")
        Spacer(modifier = Modifier.height(14.dp))

        //Slider
        Slider(value = sliderPositionState, onValueChange = { newValue ->
            sliderPositionState = newValue
            onValueChanged()
        },
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            steps = 5)
    }
}