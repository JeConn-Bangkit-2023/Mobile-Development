package com.capstone.jeconn.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.capstone.jeconn.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomIconTextField(
    modifier: Modifier = Modifier,
    label: String,
    length: Int = 50,
    type: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    leadingIcon: Any? = null,
    state: MutableState<String>,
) {
    val focusManager = LocalFocusManager.current


    val isPassword = remember {
        mutableStateOf(type == KeyboardType.Password || type == KeyboardType.NumberPassword)
    }
    val visible = remember {
        mutableStateOf(false)
    }
    val trailingIcon = when {
        isPassword.value -> {
            if (visible.value) {
                painterResource(id = R.drawable.ic_eye_visible)
            } else {
                painterResource(id = R.drawable.ic_eye_visibility_off)
            }
        }

        else -> {
            painterResource(id = R.drawable.ic_close)
        }
    }

    OutlinedTextField(
        value = state.value,
        onValueChange = {
            if (it.length <= length) state.value = it
        },
        label = {
            Text(text = label)
        },
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        leadingIcon = {
            if (leadingIcon != null) {
                when (leadingIcon) {
                    is Int -> {
                        Icon(
                            painter = painterResource(id = leadingIcon),
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    if (isPassword.value) {
                                        visible.value = !visible.value
                                    } else {
                                        state.value = ""
                                    }
                                },
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    is ImageVector -> {
                        Icon(
                            imageVector = leadingIcon,
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    if (isPassword.value) {
                                        visible.value = !visible.value
                                    } else {
                                        state.value = ""
                                    }
                                },
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        },
        trailingIcon =
        {
            if (state.value != "") {
                Icon(
                    painter = trailingIcon,
                    contentDescription = "",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            if (isPassword.value) {
                                visible.value = !visible.value
                            } else {
                                state.value = ""
                            }
                        },
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = type,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        visualTransformation = if (isPassword.value && !visible.value)
            PasswordVisualTransformation() else VisualTransformation.None,
    )
}