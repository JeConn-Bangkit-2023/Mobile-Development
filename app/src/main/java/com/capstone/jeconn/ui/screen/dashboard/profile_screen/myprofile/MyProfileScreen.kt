package com.capstone.jeconn.ui.screen.dashboard.profile_screen.myprofile

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomDialogBoxLoading
import com.capstone.jeconn.component.CustomFlatIconButton
import com.capstone.jeconn.component.CustomLabel
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.HorizontalDivider
import com.capstone.jeconn.component.OpenImageDialog
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.CropToSquareImage
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.PICK_IMAGE_PERMISSION_REQUEST_CODE
import com.capstone.jeconn.utils.ProfileViewModelFactory
import com.capstone.jeconn.utils.navigateTo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyProfileScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val currentUser = Firebase.auth.currentUser
    val showDialogStateProfileImage = rememberSaveable { mutableStateOf(false) }
    val showDialogStatePostImage = rememberSaveable { mutableStateOf(false) }
    val postImageUrl = rememberSaveable {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    val myProfileViewModel: MyProfileViewModel = remember {
        ProfileViewModelFactory(Injection.provideProfileRepository(context)).create(
            MyProfileViewModel::class.java
        )
    }
    var loadingState by remember {
        mutableStateOf(false)
    }

    if (loadingState) {
        CustomDialogBoxLoading()
    }

    val imageProfileState = remember {
        mutableStateOf("")
    }
    val emailState = remember {
        mutableStateOf("")
    }

    val fullNameState = remember {
        mutableStateOf("")
    }

    val aboutState = remember {
        mutableStateOf("")
    }

    val categoryState = remember {
        mutableStateListOf<Int>()
    }
    val jobImageState = remember {
        mutableStateListOf<String>()
    }
    val offersStatus = remember {
        mutableStateOf(false)
    }

    val updateProfileImageState by rememberUpdatedState(newValue = myProfileViewModel.updateProfileImageState.value)

    LaunchedEffect(updateProfileImageState) {
        when (val currentState = updateProfileImageState) {
            is UiState.Loading -> {
                loadingState = true
            }

            is UiState.Success -> {
                MakeToast.short(context, currentState.data)
                loadingState = false
            }

            is UiState.Error -> {
                loadingState = false
                MakeToast.long(context, currentState.errorMessage)
            }

            else -> {
                loadingState = false
                //Nothing
            }
        }
    }

    val getPublicDataState by rememberUpdatedState(newValue = myProfileViewModel.getPublicDataState.value)

    LaunchedEffect(getPublicDataState) {
        when (val currentState = getPublicDataState) {

            is UiState.Loading -> {
                loadingState = true
            }

            is UiState.Success -> {
                loadingState = false
                currentState.data.profile_image_url?.let {
                    imageProfileState.value = it
                }
                emailState.value = currentUser!!.email!!
                currentState.data.full_name?.let {
                    fullNameState.value = it
                }
                currentState.data.detail_information?.about_me?.let {
                    aboutState.value = it
                }
                currentState.data.jobInformation?.categories?.let { categoryList ->
                    categoryState.clear()
                    categoryList.map { categoryState.add(it) }
                }
                currentState.data.jobInformation?.imagesUrl?.let { imagesUrl ->
                    jobImageState.clear()
                    imagesUrl.values.sortedByDescending { it.post_image_uid }.map { image ->
                        image.post_image_url?.let {
                            jobImageState.add(it)
                        }
                    }
                }
                currentState.data.jobInformation?.isOpenToOffer?.let {
                    offersStatus.value = it
                }
            }

            is UiState.Error -> {
                loadingState = false

            }

            else -> {
                loadingState = false
                //Nothing
            }
        }
    }

    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                scope.launch {
                    myProfileViewModel.updateProfile(uri)
                }
            }
        }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CustomNavbar(
            modifier = Modifier
                .padding(bottom = 24.dp)
        ) {

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
                text = context.getString(R.string.my_profile),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
            )
        }

        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {

            Box() {
                CropToSquareImage(
                    imageUrl = imageProfileState.value,
                    contentDescription = null,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .clickable {
                            showDialogStateProfileImage.value = true
                        }
                )
                OpenImageDialog(
                    showDialogState = showDialogStateProfileImage,
                    imageUrl = imageProfileState.value
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary)
                        .align(Alignment.BottomEnd)
                        .clickable {
                            val activity = context as ComponentActivity
                            val permission = Manifest.permission.READ_EXTERNAL_STORAGE
                            if (ContextCompat.checkSelfPermission(
                                    activity,
                                    permission
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                pickImageLauncher.launch("image/*")
                            } else {
                                ActivityCompat.requestPermissions(
                                    activity,
                                    arrayOf(permission),
                                    PICK_IMAGE_PERMISSION_REQUEST_CODE
                                )
                            }
                        }
                        .padding(6.dp)
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            Text(
                text = "Email", style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Text(
                text = emailState.value, style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            Text(
                text = context.getString(R.string.fullName), style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Text(
                text = fullNameState.value, style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            HorizontalDivider()

            CustomFlatIconButton(
                icon = Icons.Default.Edit,
                label = context.getString(R.string.edit)
            ) {
                navigateTo(navHostController, NavRoute.EditDetailInfoScreen)
            }

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            Text(
                text = context.getString(R.string.about), style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Text(
                text = aboutState.value, style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            if (!categoryState.isEmpty()) {
                Spacer(modifier = Modifier.padding(vertical = 6.dp))

                Text(
                    text = context.getString(R.string.category),
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categoryState.map { id ->
                        val getCategoryById = DummyData.entertainmentCategories
                        CustomLabel(text = getCategoryById[id]!!)
                    }
                }
            }

            if (jobImageState.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(vertical = 6.dp))

                Text(
                    text = context.getString(R.string.profession_supporting_picture),
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(jobImageState) { url ->
                        CropToSquareImage(
                            imageUrl = url,
                            contentDescription = null,
                            modifier = Modifier
                                .size(148.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    postImageUrl.value = url
                                    showDialogStatePostImage.value = true
                                }
                        )
                        OpenImageDialog(
                            showDialogState = showDialogStatePostImage,
                            imageUrl = postImageUrl.value
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            Text(
                text = context.getString(R.string.status),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )



            if (offersStatus.value) {
                Row {
                    Text(
                        text = "${context.getString(R.string.you_are)} ",

                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )

                    Text(
                        text = "${context.getString(R.string.open)} ",

                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = Color.Green
                        )
                    )

                    Text(
                        text = context.getString(R.string.to_offers),

                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            } else {
                Row {
                    Text(
                        text = "${context.getString(R.string.you_are)} ",

                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )

                    Text(
                        text = "${context.getString(R.string.closed)} ",

                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Red
                        )
                    )

                    Text(
                        text = context.getString(R.string.to_offers),

                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 24.dp))
        }
    }
}