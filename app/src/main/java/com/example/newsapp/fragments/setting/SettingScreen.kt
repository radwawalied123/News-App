package com.example.newsapp.fragments.setting

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.NewsActivity
import com.example.newsapp.R
import com.example.newsapp.ui.theme.green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(vm: SettingViewModel = viewModel()) {
    Scaffold(topBar = {
        SettingTopBar {


        }
    })
    { paddingValues ->
        paddingValues
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxWidth()
                .paint(painterResource(id = R.drawable.pattern), contentScale = ContentScale.Crop)
                .padding(30.dp)
        ) {
            Text(
                text = stringResource(id = R.string.language),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))


            val activity = (LocalContext.current) as NewsActivity
            ExposedDropdownMenuBox(
                expanded = vm.isExpanded,
                onExpandedChange = { vm.isExpanded = it },
                modifier = Modifier.fillMaxSize()
            ) {
                OutlinedTextField(
                    value = vm.selectedLanguage, onValueChange = {}, readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = vm.isExpanded
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = green,
                        unfocusedBorderColor = Color.Black,
                        focusedTextColor = green,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,

                        ), modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()


                )
                ExposedDropdownMenu(
                    expanded = vm.isExpanded,
                    onDismissRequest = { vm.isExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.english)) },
                        onClick = {
                            vm.selectedLanguage = "English"
                            vm.isExpanded = false
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(
                                    "en"

                                )

                            )
                            activity.finish()
                            activity.startActivity(activity.intent)
                        }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )

                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.arabic)) },
                        onClick = {
                            vm.selectedLanguage = "العربية"
                            vm.isExpanded = false
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(
                                    "ar"
                                )
                            )
                            activity.finish()
                            activity.startActivity(activity.intent)
                        }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }


            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTopBar(
    onSideMenuClick: (() -> Unit)? = null,
) {
    TopAppBar(
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.icon_feather_menu),
                contentDescription = "Icon Menu",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        if (onSideMenuClick != null) {
                            onSideMenuClick()
                        }
                    }
            )
        },
        title = {
            Text(
                text = stringResource(R.string.settings1), modifier = Modifier.fillMaxWidth(),
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
        )
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingScreen()
}

@Preview
@Composable
fun SettingsToolBarPreview() {
    SettingTopBar()
}