package com.bruno13palhano.geminiappsample.ui.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bruno13palhano.geminiappsample.R
import com.bruno13palhano.geminiappsample.ui.theme.GeminiAppSampleTheme

@Composable
fun HomeRoute(onModelClick: (model: String) -> Unit) {
    HomeScreen(onModelClick = onModelClick)
}

@Composable
private fun HomeScreen(
    onModelClick: (model: String) -> Unit
) {

    val models = listOf(
        stringResource(id = R.string.less_random_label),
        stringResource(id = R.string.default_label),
        stringResource(id = R.string.more_random_label)
    )

    Scaffold {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()
        ) {
            models.forEach { model ->
                ListItem(
                    headlineContent = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = model,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    modifier = Modifier
                        .clickable { onModelClick(model) }
                        .padding(8.dp)
                        .fillMaxWidth()
                        .weight(weight = .33f, fill = true)
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomePreview() {
    GeminiAppSampleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen {}
        }
    }
}