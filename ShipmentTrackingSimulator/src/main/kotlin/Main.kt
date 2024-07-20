import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
@Preview
fun app() {
    var shipmentIdFieldContent by remember { mutableStateOf("") }
    val shipments = remember { mutableStateListOf<TrackerViewHelper>() }
    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        TrackingSimulator.runSimulation()
    }

    MaterialTheme {
        Column {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
            ){
                TextField(
                    shipmentIdFieldContent,
                    onValueChange = { shipmentIdFieldContent = it},
                    placeholder = {Text("Shipment ID")},
                )

                Button(onClick = {
                    shipments.add(TrackerViewHelper(
                        id=shipmentIdFieldContent
                    ))
                    shipmentIdFieldContent = ""
                }) {
                    Text("Track")
                }
            }

            LazyColumn {
                items(shipments, key = {it}) {
                    Row (
                        modifier = Modifier
                            .padding(8.dp)
                            .border(1.dp, Color.Blue, RoundedCornerShape(4.dp))
                            .padding(4.dp)
                            .fillMaxWidth()
                    ) {
                        if (it.shipment != null) {
                            Column ( modifier = Modifier.padding(10.dp)){
                                Button(onClick = {
                                    it.stopTracking()
                                    shipments.remove(it)
                                }) {
                                    Text("X")
                                }
                            }
                            Column {
                                Text("Tracking Shipment: ${it.shipmentId}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp)
                                if (it.shipmentStatus != null) {
                                    Text("Status: ${it.shipmentStatus}")
                                }
                                else {
                                    Text("Status: --")
                                }
                                if (it.shipmentLocation != null) {
                                Text("Location: ${it.shipmentLocation}")
                                }
                                else {
                                    Text("Location: --")
                                }
                                if (it.expectedShipmentDeliveryDate != null) {
                                Text("Expected Delivery: ${it.expectedShipmentDeliveryDate}")
                                }
                                else {
                                    Text("Expected Delivery: --")
                                }
                                Text("")
                                Text("Status Updates:")
                                for (s in it.shipmentUpdateHistory) {
                                    Text(s)
                                }
                                Text("")
                                Text("Notes:")
                                for (s in it.shipmentNotes) {
                                    Text(s)
                                }
                            }
                        }
                        else {
                            Column ( modifier = Modifier.padding(10.dp)) {
                                Button(onClick = {
                                    shipments.remove(it)
                                }) {
                                    Text("X")
                                }
                            }
                            Column {
                                Text("Shipment with id = ${it.shipmentId} not found")
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        app()
    }
}
