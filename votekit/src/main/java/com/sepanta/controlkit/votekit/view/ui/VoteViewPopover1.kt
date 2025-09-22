package com.sepanta.controlkit.votekit.view.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.sepanta.controlkit.votekit.theme.Black100
import com.sepanta.controlkit.votekit.theme.Black20
import com.sepanta.controlkit.votekit.theme.Pink10
import com.sepanta.controlkit.votekit.theme.Red80
import com.sepanta.controlkit.votekit.theme.Typography
import com.sepanta.controlkit.votekit.theme.White100
import com.sepanta.controlkit.votekit.theme.White80
import com.sepanta.controlkit.votekit.theme.Yellow80
import com.sepanta.controlkit.votekit.view.config.VoteViewConfig
import com.sepanta.controlkit.votekit.view.config.VoteViewContract
import com.sepanta.controlkit.votekit.view.viewModel.VoteViewModel

class VoteViewPopover1 : VoteViewContract {

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
            )
        ) {

            Surface(
                modifier = config.popupViewLayoutModifier ?: Modifier
                    .height(if ((response.voteOptions?.size ?: 0) > 1) 440.dp else 420.dp)
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(config.popupViewCornerRadius ?: 15.dp),
                color = config.popupViewBackGroundColor ?: White80
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    HeaderTitle(config, response)
                    QuestionItemLayout(config, response, viewModel)
                    Spacer(modifier = Modifier.weight(1f))
                    Buttons(config, response, viewModel)
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
                    top = 15.dp, end = 5.dp, start = 5.dp
                )
                .wrapContentHeight()
                .fillMaxWidth(),

            ) {

            config.headerTitleView ?: Text(
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
                .padding(start = 10.dp, end = 10.dp, top = 25.dp)
                .fillMaxWidth()
                .height(209.dp), shape = RoundedCornerShape(20.dp), color = White100
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
            config.questionlayoutTitleView ?: Text(
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
                        //                        val index = radioOptions.indexOf(selectedItem)
                    },
                )
                config.radioButtonView ?: Text(
                    text = vote.title.toString(),
                    style = Typography.titleMedium
                )

            }
        }

    }

    @Composable
    private fun Buttons(
        config: VoteViewConfig,
        response: VoteResponse,
        viewModel: VoteViewModel,

    ) {
        Row(
            modifier = config.buttonsLayoutModifier ?: Modifier.padding(
                start = 14.dp,
                end = 14.dp,
                bottom = 34.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonSubmit(config, response, viewModel)
            Spacer(modifier = Modifier.width(5.dp))
            ButtonCancel(config,viewModel,response)
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun RowScope.ButtonSubmit(
        config: VoteViewConfig,
        response: VoteResponse,
        viewModel: VoteViewModel

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
                containerColor = config.submitButtonColor ?: Red80
            ),
            modifier = config.submitButtonLayoutModifier ?: Modifier.weight(1f)
                .height(42.dp)
                .fillMaxWidth(),
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
    private fun RowScope.ButtonCancel(
        config: VoteViewConfig,
        viewModel: VoteViewModel,
        response: VoteResponse,



    ) {


        val onClickAction: () -> Unit = {
            viewModel.dismissDialog()
        }
        config.cancelButtonView?.let { button ->
            button(onClickAction)
        } ?: Button(
            onClick = onClickAction,
            shape = RoundedCornerShape(config.cancelButtonCornerRadius ?: 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = config.cancelButtonColor ?: Color.Transparent
            ),
            border = BorderStroke(1.dp, Red80),
            modifier = config.cancelButtonLayoutModifier ?: Modifier.weight(1f)
                .height(42.dp)
                .fillMaxWidth(),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = response.buttonCancel ?: config.cancelButtonTitle,
                style = Typography.titleMedium,
                color = Black20
            )
        }


    }

}