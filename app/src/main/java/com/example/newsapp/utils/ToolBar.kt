package com.example.newsapp.utils


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.ui.theme.green
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun onSideMenuClick(drawerState: DrawerState, scope: CoroutineScope) {
    scope.launch {
        drawerState.open()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(
    titleResId: Int,
    titleString: String? = null, scope: CoroutineScope, drawerState: DrawerState,
    shouldDisplaySearchIcon: Boolean,
    shouldDisplayMenuIcon: Boolean,
    onSearchClick: (() -> Unit)? = null
) {
    TopAppBar(
        navigationIcon = {
            if (shouldDisplayMenuIcon)
                Image(
                    painter = painterResource(id = R.drawable.icon_feather_menu),
                    contentDescription = "Icon Menu",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {

                            onSideMenuClick(drawerState, scope)

                        }
                )


        },
        title = {
            Text(
                text = stringResource(titleResId), modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = green,
            titleContentColor = Color.White
        ),
        modifier = Modifier.clip(
            RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 30.dp,
                bottomEnd = 30.dp
            )
        ), actions = {
            if (shouldDisplaySearchIcon)
                Image(
                    painter = painterResource(id = R.drawable.icon_feather_search),
                    contentDescription = "Icon Search",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            if (onSearchClick != null) {
                                onSearchClick()
                            }
                        }

                )
        }
    )
}



