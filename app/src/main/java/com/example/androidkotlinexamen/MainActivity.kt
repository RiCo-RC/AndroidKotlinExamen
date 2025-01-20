package com.example.androidkotlinexamen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidkotlinexamen.ui.theme.AndroidKotlinExamenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidKotlinExamenTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    // ---- VARIABLES ---- \\
    var beforeOperator by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }
    var afterOperator by remember { mutableStateOf("") }
    var displayText by remember { mutableStateOf("0") }

    // ---- LAYOUT ---- \\
    Column(
        modifier = modifier.padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --> Operation Display Screen:
        CalculatorOperationDisplayScreen(displayText = displayText)

        // --> Button grid:
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val calculatorRow = calculatorButtonGrid()
            calculatorRow.forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { button ->
                        Button(
                            onClick = {
                                when {
                                    button in listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9") -> manageNumberInput(
                                        button,
                                        beforeOperator = beforeOperator,
                                        onChangeBeforeOperator = { newBeforeOperator -> beforeOperator = newBeforeOperator },
                                        operator = operator,
                                        afterOperator = afterOperator,
                                        onChangeAfterOperator = { newAfterOperator -> afterOperator = newAfterOperator },
                                        displayText = displayText,
                                        onDisplayTextChange = { newDisplayText -> displayText = newDisplayText }
                                    )
                                    button in listOf("+", "-", "*", "/", "%") -> manageOperatorInput(
                                        button,
                                        beforeOperator = beforeOperator,
                                        operator = operator,
                                        onChangeOperator = { newOperator -> operator = newOperator },
                                        afterOperator = afterOperator,
                                        displayText = displayText,
                                        onDisplayTextChange = { newDisplayText -> displayText = newDisplayText }
                                    )
                                    button == "=" -> manageEqualInput(
                                        beforeOperator = beforeOperator,
                                        operator = operator,
                                        afterOperator = afterOperator,
                                        onDisplayTextChange = { newDisplayText -> displayText = newDisplayText }
                                    )
                                    button == "C" -> manageClear(
                                        beforeOperator = beforeOperator,
                                        onChangeBeforeOperator = { newBeforeOperator -> beforeOperator = newBeforeOperator },
                                        operator = operator,
                                        onChangeOperator = { newOperator -> operator = newOperator },
                                        afterOperator = afterOperator,
                                        onChangeAfterOperator = { newAfterOperator -> afterOperator = newAfterOperator },
                                        displayText = displayText,
                                        onDisplayTextChange = { newDisplayText -> displayText = newDisplayText }
                                    )
                                }
                            },
                            modifier = modifier
                        ) {
                            Text(button)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorOperationDisplayScreen(displayText: String) {
    Text(
        text = displayText,
        fontSize = 30.sp,
        textAlign = TextAlign.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    )
}

@Composable
fun calculatorButtonGrid(): List<List<String>> {
    return listOf(
        listOf("%", "CE", "C", "Supp"),
        listOf("7", "8", "9", "*"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("/", "0", ",", "=")
    )
}

fun manageNumberInput(
    button: String,
    beforeOperator: String,
    onChangeBeforeOperator: (String) -> Unit,
    afterOperator: String,
    onChangeAfterOperator: (String) -> Unit,
    operator: String,
    displayText: String,
    onDisplayTextChange: (String) -> Unit
) {
    if (beforeOperator.isEmpty()) { onChangeBeforeOperator(beforeOperator + button) }
    if (beforeOperator.isNotEmpty()) { onChangeAfterOperator(afterOperator + button) }
    onDisplayTextChange(beforeOperator + operator + afterOperator)
}

fun manageOperatorInput(
    button: String,
    beforeOperator: String,
    operator: String,
    onChangeOperator: (String) -> Unit,
    afterOperator: String,
    displayText: String,
    onDisplayTextChange: (String) -> Unit
) {
    if (beforeOperator.isNotEmpty() && afterOperator.isNotEmpty()) {
        onDisplayTextChange(beforeOperator + operator + afterOperator)
    }
    onChangeOperator(button)
}

fun manageEqualInput(
    beforeOperator: String,
    operator: String,
    afterOperator: String,
    onDisplayTextChange: (String) -> Unit
) {
    val result = when (operator) {
        "+" -> (beforeOperator.toInt() + afterOperator.toInt()).toString()
        "-" -> (beforeOperator.toInt() - afterOperator.toInt()).toString()
        "*" -> (beforeOperator.toInt() * afterOperator.toInt()).toString()
        "/" -> (beforeOperator.toInt() / afterOperator.toInt()).toString()
        "%" -> (beforeOperator.toInt() % afterOperator.toInt()).toString()
        else -> "0"
    }
    onDisplayTextChange(result)
}

fun manageClear(
    beforeOperator: String,
    onChangeBeforeOperator: (String) -> Unit,
    operator: String,
    onChangeOperator: (String) -> Unit,
    afterOperator: String,
    onChangeAfterOperator: (String) -> Unit,
    displayText: String,
    onDisplayTextChange: (String) -> Unit
) {
    onChangeBeforeOperator("")
    onChangeOperator("")
    onChangeAfterOperator("")
    onDisplayTextChange("0")
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    AndroidKotlinExamenTheme {
        Calculator()
    }
}