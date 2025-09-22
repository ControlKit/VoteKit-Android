package com.sepanta.controlkit.votekit.view.ui

import android.content.Context
import android.widget.Toast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sepanta.controlkit.votekit.service.model.VoteRequestAnswer
import com.sepanta.controlkit.votekit.service.model.VoteResponse
import com.sepanta.controlkit.votekit.view.config.VoteViewConfig
import com.sepanta.controlkit.votekit.theme.Black100
import com.sepanta.controlkit.votekit.theme.Black20
import com.sepanta.controlkit.votekit.theme.Pink10
import com.sepanta.controlkit.votekit.theme.Typography
import com.sepanta.controlkit.votekit.theme.White100
import com.sepanta.controlkit.votekit.theme.White80
import com.sepanta.controlkit.votekit.view.config.VoteViewContract
import com.sepanta.controlkit.votekit.view.viewModel.VoteViewModel

class VoteViewFullScreen1 : VoteViewContract {

    @Composable
    override fun ShowView(
        config: VoteViewConfig, response: VoteResponse, viewModel: VoteViewModel
    ) {

        val openDialog = viewModel.openDialog.collectAsState()
        if (!openDialog.value) return
        Dialog(
            onDismissRequest = { viewModel.dismissDialog() },
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = false,
                usePlatformDefaultWidth = false
            )
        ) {

            Surface(
                modifier = config.popupViewLayoutModifier ?: Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(config.popupViewCornerRadius ?: 0.dp),
                color = config.popupViewBackGroundColor ?: White100
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    CloseButton(config, viewModel)
                    HeaderTitle(config, response)
                    QuestionItemLayout(config, response, viewModel)
                    Spacer(modifier = Modifier.weight(1f))
                    ButtonSubmit(config, response, viewModel)

                }

            }

        }

    }

    private fun mToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    @Composable
    private fun HeaderTitle(
        config: VoteViewConfig, response: VoteResponse
    ) {
        Box(
            modifier = config.headerTitleLayoutModifier ?: Modifier
                .padding(
                    top = 15.dp, start = 20.dp
                )
                .wrapContentHeight()
                .fillMaxWidth(),

            ) {
            config.headerTitleView?.let { textView ->
                textView(response.title ?: config.headerTitle)
            } ?: Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(end = 10.dp, start = 10.dp),
                text = response.title ?: config.headerTitle,
                style = Typography.titleLarge,
                color = config.headerTitleColor ?: Typography.titleLarge.color

            )

        }

    }


    @Composable
    private fun QuestionItemLayout(
        config: VoteViewConfig, response: VoteResponse, viewModel: VoteViewModel
    ) {
        Surface(
            modifier = config.questionItemLayoutModifier ?: Modifier
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .fillMaxWidth()
                .height(209.dp), shape = RoundedCornerShape(20.dp), color = White80
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),

                horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top
            ) {
                QuestionLayoutTiltle(config, response.title.toString())
                RadioGroup(config, viewModel, response)
            }
        }


    }

    @Composable
    private fun QuestionLayoutTiltle(
        config: VoteViewConfig, question: String

    ) {
        Surface(
            modifier = config.questionlayoutTitleModifier ?: Modifier
                .padding(
                    top = 10.dp, end = 10.dp, start = 10.dp, bottom = 8.dp
                )
                .wrapContentSize(),
            color = Color.Transparent,

            ) {
            config.questionlayoutTitleView?.let { textView ->
                textView(question)
            } ?: Text(
                text = question,
                style = Typography.titleMedium,
                color = config.questionlayoutTitleColor ?: Typography.titleMedium.color

            )
        }

    }


    @Composable
    fun RadioGroup(config: VoteViewConfig, viewModel: VoteViewModel, response: VoteResponse) {
        var selectedItem by rememberSaveable {
            mutableStateOf("")
        }
        response.voteOptions?.forEach { vote ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {

                RadioButton(
                    modifier = config.radioButtonModifier ?: Modifier
                        .size(35.dp)
                        .scale(0.85f),
                    selected = vote.id == selectedItem,
                    colors = config.radioButtonColor ?: RadioButtonDefaults.colors(
                        selectedColor = Black100, unselectedColor = Black100
                    ),
                    onClick = {
                        selectedItem = vote.id
                        viewModel.setAnswer(
                            VoteRequestAnswer(
                                answerId = vote.id,
                                questionId = response.id
                            )
                        )
                    },
                )
                config.radioButtonView?.let { textView ->
                    textView(vote.title.toString())
                } ?: Text(
                    text = vote.title.toString(),
                    style = Typography.titleMedium
                )

            }
        }

    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ButtonSubmit(
        config: VoteViewConfig,
        response: VoteResponse,
        viewModel: VoteViewModel,
    ) {
        val mContext = LocalContext.current

        val onClickAction: () -> Unit = {
            response.voteOptions?.let {
                if (viewModel.checkAnswer()) mToast(
                    mContext,
                    config.toastErrorMessage
                ) else viewModel.submit()
            }
        }

        config.submitButtonView?.let { button ->
            button(onClickAction)
        } ?: Button(
            onClick = onClickAction,
            shape = RoundedCornerShape(config.submitButtonCornerRadius ?: 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = config.submitButtonColor ?: Pink10
            ),
            modifier = config.submitButtonLayoutModifier ?: Modifier
                .padding(bottom = 35.dp)
                .height(52.dp)
                .width(190.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = response.buttonSubmit ?: config.submitButtonTitle,
                style = Typography.titleMedium,
                color = Color.White
            )
        }


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CloseButton(
        config: VoteViewConfig,
        viewModel: VoteViewModel

    ) {


        val onClickAction: () -> Unit = {
            viewModel.dismissDialog()
        }

        Surface(
            modifier = config.closeImageLayoutModifier ?: Modifier
                .padding(top = 10.dp, end = 10.dp)
                .wrapContentSize(),
            color = Color.Transparent,
        ) {
            config.closeButtonView?.let { button ->
                button(onClickAction)
            } ?: Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Surface(
                    modifier = Modifier
                        .width(28.dp)
                        .height(28.dp)
                        .align(Alignment.CenterEnd),
                    color = Color.Transparent,
                    onClick = {
                        onClickAction()
                    }
                ) {
                    Icon(
                        config.closeImageVector ?: Icons.Filled.Close,
                        modifier = Modifier
                            .width(28.dp)
                            .height(28.dp),
                        contentDescription = null,
                        tint = config.closeImageColor ?: Black20
                    )
                }
            }
        }

    }

}