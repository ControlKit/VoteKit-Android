package com.sepanta.controlkit.votekitexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sepanta.controlkit.votekit.config.VoteServiceConfig
import com.sepanta.controlkit.votekit.view.config.VoteViewConfig
import com.sepanta.controlkit.votekit.view.config.VoteViewStyle
import com.sepanta.controlkit.votekit.voteKitHost
import com.sepanta.controlkit.votekitexample.ui.theme.VoteKitExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoteKitExampleTheme {
                val kit = voteKitHost(
                    VoteServiceConfig(
                        version = "1.0.0",
                        name="test4",
                        appId = "9fee1663-e80e-46ad-8cd9-357263375a9c",
                        viewConfig = VoteViewConfig(
                            VoteViewStyle.Popover4
                        )
                    ),
                    onDismiss = {

                    }
                )
                kit.showView()

            }
        }
    }
}
