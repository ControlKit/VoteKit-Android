# Examples

This document provides comprehensive examples of using VoteKit in various scenarios.

## Basic Examples

### Simple Integration

```kotlin
@Composable
fun MyApp() {
    val kit = voteKitHost(
        config = VoteServiceConfig(
            version = "1.0.0",
            name = "MyApp",
            appId = "com.example.myapp",
            viewConfig = VoteViewConfig(
                viewStyle = VoteViewStyle.Popover4
            )
        )
    )
    
    kit.showView()
}
```

### With State Handling

```kotlin
@Composable
fun MyApp() {
    val kit = voteKitHost(
        config = VoteServiceConfig(
            version = "1.0.0",
            name = "MyApp",
            appId = "com.example.myapp",
            viewConfig = VoteViewConfig(
                viewStyle = VoteViewStyle.FullScreen1
            )
        ),
        onState = { state ->
            when (state) {
                is ViewModelState.ShowView -> {
                    Log.d("VoteKit", "Showing vote dialog")
                }
                is ViewModelState.NoContent -> {
                    Log.d("VoteKit", "No vote available")
                }
                is ViewModelState.Error -> {
                    Log.e("VoteKit", "Error: ${state.data?.message}")
                }
                is ViewModelState.Action -> {
                    Log.d("VoteKit", "Vote submitted successfully")
                }
                is ViewModelState.ActionError -> {
                    Log.e("VoteKit", "Vote submission failed: ${state.data?.message}")
                }
            }
        }
    )
    
    kit.showView()
}
```

## UI Style Examples

### FullScreen Styles

#### FullScreen1 - Clean Design
```kotlin
VoteViewConfig(
    viewStyle = VoteViewStyle.FullScreen1,
    headerTitle = "User Satisfaction Survey",
    submitButtonTitle = "Submit Vote",
    cancelButtonTitle = "Skip",
    popupViewBackGroundColor = Color.White,
    submitButtonColor = Color(0xFF2196F3),
    cancelButtonColor = Color.Transparent
)
```

#### FullScreen2 - Detailed Layout
```kotlin
VoteViewConfig(
    viewStyle = VoteViewStyle.FullScreen2,
    headerTitle = "Product Feedback",
    submitButtonTitle = "Submit Feedback",
    cancelButtonTitle = "Not Now",
    popupViewBackGroundColor = Color(0xFFF5F5F5),
    headerTitleColor = Color(0xFF2E7D32),
    submitButtonColor = Color(0xFF4CAF50)
)
```

### Popover Styles

#### Popover1 - Compact Dialog
```kotlin
VoteViewConfig(
    viewStyle = VoteViewStyle.Popover1,
    headerTitle = "Quick Poll",
    submitButtonTitle = "Vote",
    cancelButtonTitle = "Cancel",
    popupViewCornerRadius = 20.dp,
    popupViewBackGroundColor = Color.White,
    submitButtonColor = Color(0xFF1976D2)
)
```

#### Popover2 - Enhanced Dialog
```kotlin
VoteViewConfig(
    viewStyle = VoteViewStyle.Popover2,
    headerTitle = "Feature Rating",
    submitButtonTitle = "Submit Rating",
    cancelButtonTitle = "Skip",
    popupViewCornerRadius = 16.dp,
    popupViewBackGroundColor = Color(0xFFF8F9FA),
    headerTitleColor = Color(0xFF1A1A1A),
    submitButtonColor = Color(0xFF007AFF)
)
```

#### Popover3 - Alternative Layout
```kotlin
VoteViewConfig(
    viewStyle = VoteViewStyle.Popover3,
    headerTitle = "Service Feedback",
    submitButtonTitle = "Send Feedback",
    cancelButtonTitle = "Later",
    popupViewBackGroundColor = Color(0xFF1E1E1E),
    headerTitleColor = Color.White,
    submitButtonColor = Color(0xFFBB86FC)
)
```

#### Popover4 - Modern Design
```kotlin
VoteViewConfig(
    viewStyle = VoteViewStyle.Popover4,
    headerTitle = "App Experience",
    submitButtonTitle = "Submit",
    cancelButtonTitle = "Cancel",
    popupViewBackGroundColor = Color(0xFFFAFAFA),
    headerTitleColor = Color(0xFF333333),
    submitButtonColor = Color(0xFFE91E63),
    cancelButtonColor = Color.Transparent
)
```

## Customization Examples

### Custom Colors and Styling

```kotlin
VoteViewConfig(
    viewStyle = VoteViewStyle.FullScreen1,
    
    // Colors
    popupViewBackGroundColor = Color(0xFF1A1A1A),
    headerTitleColor = Color.White,
    questionlayoutTitleColor = Color(0xFFCCCCCC),
    submitButtonColor = Color(0xFF007AFF),
    cancelButtonColor = Color.Transparent,
    submitButtonCornerRadius = 12.dp,
    cancelButtonCornerRadius = 12.dp,
    
    // Text Content
    headerTitle = "🗳️ Your Opinion Matters",
    submitButtonTitle = "Submit Vote",
    cancelButtonTitle = "Skip This Time",
    toastErrorMessage = "Please select an option before submitting"
)
```

### Custom Layout Modifiers

```kotlin
VoteViewConfig(
    viewStyle = VoteViewStyle.Popover4,
    
    // Layout Modifiers
    popupViewLayoutModifier = Modifier
        .fillMaxWidth(0.9f)
        .height(500.dp),
    headerTitleLayoutModifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
    questionItemLayoutModifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth(),
    buttonsLayoutModifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
    
    // Button Modifiers
    submitButtonLayoutModifier = Modifier
        .height(48.dp)
        .fillMaxWidth(),
    cancelButtonLayoutModifier = Modifier
        .height(48.dp)
        .fillMaxWidth()
)
```

### Custom Views (Advanced)

```kotlin
VoteViewConfig(
    viewStyle = VoteViewStyle.FullScreen1,
    
    // Custom header
    headerTitleView = { title ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    },
    
    // Custom question title
    questionlayoutTitleView = { question ->
        Text(
            text = question,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    },
    
    // Custom radio button
    radioButtonView = { optionText ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = false, // This will be managed by the library
                onClick = { /* Handled by library */ },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = optionText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    },
    
    // Custom buttons
    submitButtonView = { onClick ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { /* Handle cancel */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Cancel")
            }
            
            Button(
                onClick = onClick,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Submit")
            }
        }
    }
)
```

## Error Handling Examples

### Basic Error Handling

```kotlin
val kit = voteKitHost(
    config = VoteServiceConfig(
        version = "1.0.0",
        name = "MyApp",
        appId = "com.example.myapp",
        maxRetry = 3,
        timeRetryThreadSleep = 2000L
    ),
    onState = { state ->
        when (state) {
            is ViewModelState.Error -> {
                // Show user-friendly error message
                showSnackbar("Unable to load poll. Please check your internet connection.")
            }
            is ViewModelState.ActionError -> {
                // Handle vote submission error
                showSnackbar("Failed to submit vote. Please try again later.")
            }
        }
    }
)
```

### Advanced Error Handling with Custom Retry

```kotlin
@Composable
fun MyApp() {
    var showCustomError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    val kit = voteKitHost(
        config = VoteServiceConfig(
            version = "1.0.0",
            name = "MyApp",
            appId = "com.example.myapp",
            maxRetry = 2,
            timeRetryThreadSleep = 3000L
        ),
        onState = { state ->
            when (state) {
                is ViewModelState.Error -> {
                    errorMessage = "Network error: ${state.data?.message ?: "Unknown error"}"
                    showCustomError = true
                }
                is ViewModelState.ActionError -> {
                    errorMessage = "Vote failed: ${state.data?.message ?: "Unknown error"}"
                    showCustomError = true
                }
            }
        }
    )
    
    if (showCustomError) {
        CustomErrorDialog(
            message = errorMessage,
            onRetry = {
                showCustomError = false
                kit.showView()
            },
            onDismiss = {
                showCustomError = false
            }
        )
    }
    
    kit.showView()
}

@Composable
fun CustomErrorDialog(
    message: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Vote Error") },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text("Retry")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
```

## Multi-language Examples

### Persian (Farsi) Support

```kotlin
VoteServiceConfig(
    version = "1.0.0",
    name = "MyApp",
    appId = "com.example.myapp",
    lang = "fa",  // Persian
    viewConfig = VoteViewConfig(
        viewStyle = VoteViewStyle.FullScreen1,
        headerTitle = "نظرسنجی رضایت کاربران",
        submitButtonTitle = "ارسال نظر",
        cancelButtonTitle = "رد کردن",
        toastErrorMessage = "لطفاً یک گزینه انتخاب کنید"
    )
)
```

### Arabic Support

```kotlin
VoteServiceConfig(
    version = "1.0.0",
    name = "MyApp",
    appId = "com.example.myapp",
    lang = "ar",  // Arabic
    viewConfig = VoteViewConfig(
        viewStyle = VoteViewStyle.FullScreen1,
        headerTitle = "استطلاع رضا المستخدمين",
        submitButtonTitle = "إرسال الرأي",
        cancelButtonTitle = "تجاهل",
        toastErrorMessage = "يرجى اختيار خيار"
    )
)
```

## Integration Examples

### With Material 3 Theme

```kotlin
@Composable
fun MyApp() {
    val kit = voteKitHost(
        config = VoteServiceConfig(
            version = "1.0.0",
            name = "MyApp",
            appId = "com.example.myapp",
            viewConfig = VoteViewConfig(
                viewStyle = VoteViewStyle.Popover4,
                
                // Use Material 3 colors
                popupViewBackGroundColor = MaterialTheme.colorScheme.surface,
                headerTitleColor = MaterialTheme.colorScheme.onSurface,
                questionlayoutTitleColor = MaterialTheme.colorScheme.onSurfaceVariant,
                submitButtonColor = MaterialTheme.colorScheme.primary,
                cancelButtonColor = MaterialTheme.colorScheme.outline,
                submitButtonCornerRadius = 12.dp,
                cancelButtonCornerRadius = 12.dp
            )
        )
    )
    
    kit.showView()
}
```

### With Custom Theme

```kotlin
@Composable
fun MyApp() {
    val customColors = CustomColors(
        primary = Color(0xFF6200EE),
        secondary = Color(0xFF03DAC6),
        surface = Color(0xFF121212),
        onSurface = Color.White
    )
    
    val kit = voteKitHost(
        config = VoteServiceConfig(
            version = "1.0.0",
            name = "MyApp",
            appId = "com.example.myapp",
            viewConfig = VoteViewConfig(
                viewStyle = VoteViewStyle.FullScreen1,
                popupViewBackGroundColor = customColors.surface,
                headerTitleColor = customColors.onSurface,
                questionlayoutTitleColor = customColors.onSurface.copy(alpha = 0.7f),
                submitButtonColor = customColors.primary,
                cancelButtonColor = customColors.secondary
            )
        )
    )
    
    kit.showView()
}
```

## Performance Examples

### Lazy Loading

```kotlin
@Composable
fun MyApp() {
    val kit by remember {
        derivedStateOf {
            voteKitHost(
                config = VoteServiceConfig(...)
            )
        }
    }
    
    LaunchedEffect(Unit) {
        // Only check for votes when app starts
        kit.showView()
    }
}
```

### Conditional Loading

```kotlin
@Composable
fun MyApp() {
    var shouldCheckVote by remember { mutableStateOf(false) }
    
    if (shouldCheckVote) {
        val kit = voteKitHost(
            config = VoteServiceConfig(...)
        )
        
        LaunchedEffect(Unit) {
            kit.showView()
        }
    }
    
    // Trigger vote check based on conditions
    LaunchedEffect(Unit) {
        delay(5000) // Wait 5 seconds after app start
        shouldCheckVote = true
    }
}
```

### Force Vote Example

```kotlin
// When server returns force: true, user cannot dismiss the dialog
VoteServiceConfig(
    version = "1.0.0",
    name = "MyApp",
    appId = "com.example.myapp",
    viewConfig = VoteViewConfig(
        viewStyle = VoteViewStyle.FullScreen1,
        headerTitle = "Important Survey",
        submitButtonTitle = "Submit Response",
        cancelButtonTitle = "Skip" // This button will be disabled for force votes
    )
)
```

### Custom Toast Message

```kotlin
VoteViewConfig(
    viewStyle = VoteViewStyle.Popover4,
    toastErrorMessage = "Please select an option before submitting your vote",
    submitButtonTitle = "Submit Vote",
    cancelButtonTitle = "Cancel"
)
```

These examples demonstrate the flexibility and power of VoteKit. Choose the approach that best fits your app's needs and design requirements.

---

Copyright (c) 2024 ControlKit