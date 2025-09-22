# API Documentation

## VoteKit

### voteKitHost

The main Composable function to initialize and manage the VoteKit.

```kotlin
@Composable
fun voteKitHost(
    config: VoteServiceConfig,
    onDismiss: (() -> Unit)? = null,
    onState: ((ViewModelState) -> Unit)? = null
): VoteKit
```

**Parameters:**
- `config`: Service configuration object
- `onDismiss`: Callback when dialog is dismissed
- `onState`: Callback for state changes

**Returns:** VoteKit instance

## VoteServiceConfig

Configuration class for the service layer.

```kotlin
data class VoteServiceConfig(
    var viewConfig: VoteViewConfig = VoteViewConfig(),
    var version: String = "0.0.0",
    var name: String,
    var appId: String,
    var lang: String = "en",
    var deviceId: String? = null,
    var timeOut: Long = 5000L,
    var timeRetryThreadSleep: Long = 1000L,
    var maxRetry: Int = 5,
)
```

**Properties:**
- `viewConfig`: View configuration object
- `version`: Current app version (required)
- `name`: App name identifier (required)
- `appId`: Unique app identifier (required)
- `lang`: Language code
- `deviceId`: Device identifier (auto-generated if null)
- `timeOut`: API timeout in milliseconds
- `timeRetryThreadSleep`: Retry delay
- `maxRetry`: Maximum retry attempts

## VoteViewConfig

Configuration class for UI customization.

```kotlin
data class VoteViewConfig(
    var viewStyle: VoteViewStyle = VoteViewStyle.Popover1,
    var popupViewLayoutModifier: Modifier? = null,
    var popupViewBackGroundColor: Color? = null,
    var popupViewCornerRadius: Dp? = null,
    var headerTitle: String = "Vote",
    var headerTitleColor: Color? = null,
    var headerTitleLayoutModifier: Modifier? = null,
    var headerTitleView: @Composable ((String) -> Unit)? = null,
    var questionlayoutTitleModifier: Modifier? = null,
    var questionlayoutTitleColor: Color? = null,
    var questionlayoutTitleView: @Composable ((String) -> Unit)? = null,
    var radioButtonModifier: Modifier? = null,
    var radioButtonColor: RadioButtonColors? = null,
    var radioButtonView: @Composable ((String) -> Unit)? = null,
    var submitButtonTitle: String = "Submit",
    var submitButtonColor: Color? = null,
    var submitButtonCornerRadius: Dp? = null,
    var submitButtonLayoutModifier: Modifier? = null,
    var submitButtonView: @Composable ((() -> Unit) -> Unit)? = null,
    var cancelButtonTitle: String = "Cancel",
    var cancelButtonColor: Color? = null,
    var cancelButtonCornerRadius: Dp? = null,
    var cancelButtonLayoutModifier: Modifier? = null,
    var cancelButtonView: @Composable ((() -> Unit) -> Unit)? = null,
    var buttonsLayoutModifier: Modifier? = null,
    var questionItemLayoutModifier: Modifier? = null,
    var toastErrorMessage: String = "Please select an option"
)
```

## VoteViewStyle

Enum for available UI styles.

```kotlin
enum class VoteViewStyle {
    FullScreen1,
    FullScreen2,
    Popover1,
    Popover2,
    Popover3,
    Popover4
}
```

## ViewModelState

Sealed class representing different states of the voting process.

```kotlin
sealed class ViewModelState {
    object Initial : ViewModelState()
    object NoContent : ViewModelState()
    data class ShowView(val data: VoteResponse?) : ViewModelState()
    data class Error(val data: ApiError?) : ViewModelState()
    data class Action(val force: Boolean) : ViewModelState()
    data class ActionError(val data: ApiError?) : ViewModelState()
}
```

## VoteResponse

Data class representing the vote response from the server.

```kotlin
data class VoteResponse(
    val id: String? = null,
    val name: String? = null,
    val title: String? = null,
    val buttonSubmit: String? = null,
    val buttonCancel: String? = null,
    val description: String? = null,
    val force: Boolean = false,
    val voteOptions: List<VoteOptions>? = null,
    val createdAt: String? = null
)
```

## VoteOptions

Data class representing individual vote options.

```kotlin
data class VoteOptions(
    val id: String,
    val title: String?,
    val order: Int,
    val createdAt: String
)
```

## VoteRequestAnswer

Data class representing the user's vote selection.

```kotlin
data class VoteRequestAnswer(
    val answerId: String,
    val questionId: String?
)
```

## ApiError

Data class representing API errors. Uses the external error handling library.

```kotlin
// From com.sepanta.errorhandler.ApiError
data class ApiError<T>(
    val message: String? = null,
    val code: Int? = null,
    val details: String? = null,
    val data: T? = null
)
```

## VoteKit

Main class for managing the voting process.

### Methods

#### showView()
Triggers the vote check and shows the appropriate UI.

```kotlin
fun showView()
```

#### viewModel
Access to the underlying ViewModel for advanced usage.

```kotlin
val viewModel: VoteViewModel
```

## VoteViewModel

ViewModel class for managing the vote state and API calls.

### Methods

#### getData()
Fetches vote information from the server.

```kotlin
fun getData()
```

#### setViewAction()
Sends a view action to the server.

```kotlin
fun setViewAction()
```

#### setSubmitAction()
Sends a submit action to the server.

```kotlin
fun setSubmitAction()
```

#### setAnswer(voteRequestAnswer: VoteRequestAnswer)
Sets the user's vote selection.

```kotlin
fun setAnswer(voteRequestAnswer: VoteRequestAnswer)
```

#### submit()
Submits the vote and dismisses the dialog.

```kotlin
fun submit()
```

#### dismissDialog()
Dismisses the vote dialog.

```kotlin
fun dismissDialog()
```

#### clearState()
Clears the current state.

```kotlin
fun clearState()
```

#### checkAnswer()
Checks if an answer has been selected.

```kotlin
fun checkAnswer(): Boolean
```

### Properties

#### state
StateFlow containing the current state.

```kotlin
val state: StateFlow<ViewModelState>
```

#### openDialog
StateFlow indicating if the dialog is open.

```kotlin
val openDialog: StateFlow<Boolean>
```

#### cancelButtonEvent
Flow for cancel button events.

```kotlin
val cancelButtonEvent: Flow<Unit>
```

## VoteApi

API service class for handling network requests.

### Methods

#### getData()
Fetches vote data from the server.

```kotlin
suspend fun getData(
    route: String,
    appId: String,
    version: String,
    deviceId: String,
    sdkVersion: String,
    name: String
): NetworkResult<ApiVoteResponse>
```

#### setViewAction()
Sends a view action to the server.

```kotlin
suspend fun setViewAction(
    route: String,
    appId: String,
    version: String,
    deviceId: String,
    sdkVersion: String
): NetworkResult<ActionResponse>
```

#### setSubmitAction()
Sends a submit action to the server.

```kotlin
suspend fun setSubmitAction(
    route: String,
    appId: String,
    version: String,
    deviceId: String,
    sdkVersion: String,
    optionId: String
): NetworkResult<ActionResponse>
```

## LocalDataSource

Local storage service for caching vote data.

### Methods

#### saveLastId(token: String)
Saves the last vote ID to prevent duplicate displays.

```kotlin
suspend fun saveLastId(token: String)
```

#### getLastId()
Retrieves the last vote ID.

```kotlin
suspend fun getLastId(): String?
```

#### clearLastId()
Clears the stored last vote ID.

```kotlin
suspend fun clearLastId()
```

## NetworkResult

Sealed class representing network operation results.

```kotlin
sealed class NetworkResult<out T> {
    data class Success<T>(val value: T?) : NetworkResult<T>()
    data class Error(val error: ApiError?) : NetworkResult<Nothing>()
}
```

## ActionResponse

Data class representing action response from the server.

```kotlin
data class ActionResponse(
    val success: Boolean? = null,
    val message: String? = null
)
```

## VoteResponseMapper

Utility class for mapping API responses to domain models.

### Methods

#### toDomain()
Converts API response to domain model.

```kotlin
fun ApiVoteResponse.toDomain(lang: String? = null): VoteResponse?
```

## UniqueUserIdProvider

Utility class for generating unique user IDs.

### Methods

#### getOrCreateUserId(context: Context)
Gets or creates a unique user ID for the device.

```kotlin
fun getOrCreateUserId(context: Context): String
```

## VoteViewContract

Interface for vote view implementations.

```kotlin
interface VoteViewContract {
    @Composable
    fun ShowView(
        config: VoteViewConfig,
        response: VoteResponse,
        viewModel: VoteViewModel
    )
}
```

## VoteViewModelFactory

Factory class for creating VoteViewModel instances.

```kotlin
class VoteViewModelFactory(
    private val api: VoteApi,
    private val localDataSource: LocalDataSource
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T
}
```

## BuildConfig

Generated configuration class containing build-time constants.

### Properties

- `LIB_VERSION_CODE`: Library version code
- `LIB_VERSION_NAME`: Library version name
- `API_URL`: API base URL

## Usage Examples

### Basic Implementation

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
            }
        }
    )
    
    kit.showView()
}
```

This API documentation provides comprehensive information about all the classes, methods, and properties available in the VoteKit library.