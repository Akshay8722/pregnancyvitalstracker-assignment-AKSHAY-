package com.example.pregnancyvitals

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pregnancyvitals.data.Vital
import com.example.pregnancyvitals.viewmodel.VitalViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun VitalsScreen(viewModel: VitalViewModel) {
    val context = LocalContext.current
    val vitals by viewModel.vitals.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Text(
                text = "Pregnancy Vitals",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(vitals) { vital ->
                    VitalCard(vital)
                }
            }
        }

        if (showDialog) {
            AddVitalDialog(
                onDismiss = { showDialog = false },
                onSave = { systolic, diastolic, heartRate, weight, babyKicks ->
                    if (systolic != null && diastolic != null && heartRate != null && weight != null && babyKicks != null) {
                        viewModel.addVital(
                            Vital(
                                systolicBP = systolic,
                                diastolicBP = diastolic,
                                heartRate = heartRate,
                                weight = weight,
                                babyKicks = babyKicks
                            )
                        )
                        Toast.makeText(context, "Vital added!", Toast.LENGTH_SHORT).show()
                        showDialog = false
                    } else {
                        Toast.makeText(context, "Fill all values", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

@Composable
fun VitalCard(vital: Vital) {
    val formattedTime = remember(vital.timestamp) {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        sdf.format(Date(vital.timestamp))
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        backgroundColor = Color(0xFFEDE7F6),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("📅 $formattedTime", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("🩸 BP: ${vital.systolicBP}/${vital.diastolicBP} mmHg")
            Text("❤️ Heart Rate: ${vital.heartRate} bpm")
            Text("⚖️ Weight: ${vital.weight} kg")
            Text("👶 Baby Kicks: ${vital.babyKicks}")
        }
    }
}

@Composable
fun AddVitalDialog(
    onDismiss: () -> Unit,
    onSave: (Int?, Int?, Int?, Float?, Int?) -> Unit
) {
    var systolic by remember { mutableStateOf("") }
    var diastolic by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var kicks by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Vital") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = systolic,
                    onValueChange = { systolic = it },
                    label = { Text("Systolic BP") }
                )
                OutlinedTextField(
                    value = diastolic,
                    onValueChange = { diastolic = it },
                    label = { Text("Diastolic BP") }
                )
                OutlinedTextField(
                    value = heartRate,
                    onValueChange = { heartRate = it },
                    label = { Text("Heart Rate") }
                )
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)") }
                )
                OutlinedTextField(
                    value = kicks,
                    onValueChange = { kicks = it },
                    label = { Text("Baby Kicks") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(
                    systolic.toIntOrNull(),
                    diastolic.toIntOrNull(),
                    heartRate.toIntOrNull(),
                    weight.toFloatOrNull(),
                    kicks.toIntOrNull()
                )
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
