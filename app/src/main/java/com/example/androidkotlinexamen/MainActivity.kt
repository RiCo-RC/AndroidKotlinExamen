package com.example.androidkotlinexamen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun calculatorButtonGrid(): List<List<String>> {
    val grid = listOf(
        listOf("%","CE","C","Supp"),
        listOf("7","8","9","*"),
        listOf("4","5","6","-"),
        listOf("1","2","3","+"),
        listOf("/","0",",","=")
    )
    return grid
}

@Composable
fun layoutButtonGrid() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val calculatorRow = calculatorButtonGrid()

        calculatorRow.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                row.forEach { button ->
                    Button(
                        onClick = { null },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(button)
                    }
                }
            }
        }
    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    // ---- VARIABLES ---- \\

    // ---- LAYOUT ---- \\
    Column(
        modifier = modifier.padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --> Button grid
        layoutButtonGrid()
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    AndroidKotlinExamenTheme {
        Calculator()
    }
}