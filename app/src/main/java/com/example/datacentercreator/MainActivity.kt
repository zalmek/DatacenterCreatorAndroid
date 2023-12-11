package com.example.datacentercreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.datacentercreator.model.Component
import com.example.datacentercreator.ui.theme.DatacenterCreatorTheme
import com.example.datacentercreator.vm.ComponentViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatacenterCreatorTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val viewModel: ComponentViewModel = viewModel()
                    var filterText by remember {
                        mutableStateOf<String?>(null)
                    }
                    val components by viewModel.componentsUiState.collectAsState()
                    val component by viewModel.componentUiState.collectAsState()
                    val keyboard = LocalSoftwareKeyboardController.current
                    var tempValue by remember {
                        mutableStateOf("")
                    }

                    fun onSearch() {
                        filterText = tempValue
                        keyboard?.hide()
                    }
                    InputField(
                        text = tempValue,
                        onValueChange = { tempValue = it },
                        label = "Поиск",
                        onSearch = {
                            onSearch()
                            keyboard?.hide()
                        }
                    )

                    LaunchedEffect(filterText) {
                        viewModel.getAllComponents(filterText)
                    }
                    if (component != null) {
                        ComponentCard(component = component!!)
                    } else {
                        ComponentsScreen(components = components, viewModel::getComponent)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DatacenterCreatorTheme {
        Greeting("Android")
    }
}

@Composable
fun ComponentCard(
    component: Component,
    onFullScreen: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(horizontal = 16.dp)
            .clip(
                RoundedCornerShape(35.dp)
            )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = component.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
//                onState = {if (it is AsyncImagePainter.State.Loading) { CircularProgressIndicator() }},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(
                        RoundedCornerShape(35.dp)
                    )
            )
            Text(
                text = component.name,
                style = TextStyle(
                    fontSize = 11.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    color = Color(0xFF818C99),
                    letterSpacing = 0.3.sp,
                ),
                modifier = Modifier.padding(top = 12.dp, start = 16.dp)

            )
            Text(
                text = component.price.toString(),
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                    letterSpacing = 0.1.sp,
                ),
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )
            if (onFullScreen) {
                Text(
                    text = component.description.toString(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        letterSpacing = 0.1.sp,
                    ),
                    modifier = Modifier.padding(top = 4.dp, start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ComponentsScreen(
    components: List<Component>,
    onClick: (String) -> Unit
) {
    LazyColumn {
        items(components) {
            ComponentCard(
                component = it, modifier = Modifier.clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { onClick(it.id.toString()) },
                )
            )
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    label: String? = null,
    text: String = "",
    onValueChange: (String) -> Unit,
    onSearch: (KeyboardActionScope.() -> Unit)
) {
    Column {
        if (label != null) {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF6D7885),
                    letterSpacing = 0.2.sp,
                ),
                modifier = modifier.padding(start = 16.dp)
            )
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 16.dp, bottom = 26.dp, top = 8.dp, end = 16.dp)
            .border(
                width = 0.5.dp,
                color = Color(0x1F000000),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .height(44.dp)
            .background(color = Color(0xFFF2F3F5), shape = RoundedCornerShape(size = 8.dp))
            .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
//            placeholder = { Text(text = "Ввести $label")},
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 16.sp,
                lineHeight = 20.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF303030),
                letterSpacing = 0.1.sp,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = onSearch)
        )
    }
}