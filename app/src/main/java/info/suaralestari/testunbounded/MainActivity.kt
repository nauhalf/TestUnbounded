package info.suaralestari.testunbounded

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.suaralestari.testunbounded.ui.theme.TestUnboundedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestUnboundedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var selected by remember {
        mutableStateOf<Item?>(null)
    }
    Column(modifier = modifier.height(100.dp)) {
        Text(text = "Judul")
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            content = {
                items((1..17).map {
                    Item(id = it, name = "$it")
                }) {
                    ItemGrid(
                        modifier = Modifier,
                        isSelected = it == selected,
                        onClick = {
                            selected = it
                        }
                    )
                }
            },
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        )

    }
}

@Composable
fun ItemGrid(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp)
            .border(border = BorderStroke(1.dp, Color.Green))
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight(
                    align = Alignment.Top,
                    unbounded = true
                )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .height(62.dp)
                    .background(Color.Red.copy(alpha = 0.5f))
                    .clickable {
                        onClick.invoke()
                    }
            ) {

            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(86.dp)
                        .background(Color.Green.copy(alpha = 0.5f))
                )
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestUnboundedTheme {
    }
}


data class Item(
    val id: Int,
    val name: String,
)
