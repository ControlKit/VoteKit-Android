package com.sepanta.controlkit.votekit.view.config

import androidx.compose.material3.CardColors
import androidx.compose.material3.RadioButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

data class VoteViewConfig(
    var viewStyle: VoteViewStyle = VoteViewStyle.Popover1,
    var closeImageDrawble:   Int? = null,
    var updateImageDrawble:   Int? = null,
    var updateImageColor:   Color? = null,
    var closeImageColor:   Color? = null,
    var closeImageLayoutModifier:   Modifier? = null,
    var closeImageVector: ImageVector ? = null,
    var updateImageLayoutModifier:   Modifier? = null,
    var popupViewLayoutModifier: Modifier? = null,
    var popupViewBackGroundColor: Color? = null,
    var popupViewCornerRadius: Dp? = null,
    var headerTitle: String = "DON'T MISS OFF",
    var headerTitleColor: Color? = null,
    var headerTitleLayoutModifier:   Modifier? = null,
    var descriptionTitle: String = "FREE VOUCHER free shipping \n" +
            "you can shop as much as you want until tomorrow !",
    var questionlayoutTitleColor: Color? = null,
    var questionlayoutTitleModifier:   Modifier? = null,
    var questionListLayoutModifier:   Modifier? = null,
    var questionItemLayoutModifier:   Modifier? = null,
    var buttonsLayoutModifier:   Modifier? = null,
    var radioButtonModifier:   Modifier? = null,
    var submitButtonTitle: String = "SUBMIT",
    var cancelButtonTitle: String = "CANCEL",
    var updateButtonTitleColor: Color? = null,
    var submitButtonColor: Color? = null,
    var cancelButtonColor: Color? = null,
    var radioButtonColor: RadioButtonColors? = null,
    var cancelButtonCornerColor: Color? = null,
    var submitButtonCornerRadius: Dp? = null,
    var cancelButtonCornerRadius: Dp? = null,
    var updateButtonBorderColor: Color? = null,
    var submitButtonLayoutModifier:   Modifier? = null,
    var cancelButtonLayoutModifier:   Modifier? = null,
    var versionTitle: String = "Up to 12.349 version Apr 2024.",
    var toastErrorMessage: String = "All questions should have one answer.",
    var versionTitleColor: Color? = null,
    var versionTitleLayoutModifier:   Modifier? = null,
    var imageView: @Composable (() -> Unit)? = null,
    var closeButtonView: (@Composable (libraryOnClick: () -> Unit) -> Unit)? = null,
    var headerTitleView: @Composable ((String) -> Unit)? = null,
    var questionlayoutTitleView:  @Composable ((String) -> Unit)? = null,
    var versionTitleView: @Composable (() -> Unit)? = null,
    var submitButtonView: (@Composable (libraryOnClick: () -> Unit) -> Unit)? = null,

    var cancelButtonView: (@Composable (libraryOnClick: () -> Unit) -> Unit)? = null,
    var radioButtonView: @Composable ((String) -> Unit)? = null,
)
