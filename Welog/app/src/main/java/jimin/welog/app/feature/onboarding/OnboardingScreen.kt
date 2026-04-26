package jimin.com.welog.feature.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel) {
    var selectedName by rememberSaveable { mutableStateOf<String?>(null) }
    var emoji by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("나는 누구인가요?")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(top = 16.dp)) {
            Button(onClick = { selectedName = "정수한" }) { Text("정수한") }
            Button(onClick = { selectedName = "이지민" }) { Text("이지민") }
        }
        if (selectedName != null) {
            OutlinedTextField(
                value = emoji,
                onValueChange = { new ->
                    emoji = TextFieldValue(new.text.take(2))
                },
                label = { Text("이모지(1자)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )
            Button(
                onClick = { viewModel.complete(selectedName!!, emoji.text) },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("완료")
            }
        }
    }
}
