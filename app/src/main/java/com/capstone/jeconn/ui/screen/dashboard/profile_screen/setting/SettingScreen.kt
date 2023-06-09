package com.capstone.jeconn.ui.screen.dashboard.profile_screen.setting

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.card.HorizontalProfileCard
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.utils.navigateToTop
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SettingScreen(navHostController: NavHostController) {

    val context = LocalContext.current
    val auth = Firebase.auth
    val openSettings = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ){
        //Nothing
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {

        CustomNavbar {

            IconButton(
                onClick = { navHostController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Text(
                text = context.getString(R.string.setting),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            HorizontalProfileCard(
                subject = context.getString(R.string.language),
                icon = R.drawable.ic_language,
                isSetting = true
            ) {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                openSettings.launch(intent)
            }

            HorizontalProfileCard(
                subject = context.getString(R.string.logout),
                icon = Icons.Default.ExitToApp,
                isSetting = true
            ) {
                auth.signOut()
                navigateToTop(navHostController, NavRoute.ROOT)
            }
        }
    }
}