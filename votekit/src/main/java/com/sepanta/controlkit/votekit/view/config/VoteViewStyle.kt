package com.sepanta.controlkit.votekit.view.config

import com.sepanta.controlkit.votekit.view.ui.VoteViewFullScreen1
import com.sepanta.controlkit.votekit.view.ui.VoteViewFullScreen2
import com.sepanta.controlkit.votekit.view.ui.VoteViewPopover1
import com.sepanta.controlkit.votekit.view.ui.VoteViewPopover2
import com.sepanta.controlkit.votekit.view.ui.VoteViewPopover3
import com.sepanta.controlkit.votekit.view.ui.VoteViewPopover4


enum class VoteViewStyle {
    FullScreen1,
    FullScreen2,
    Popover1,
    Popover2,
    Popover3,
    Popover4;
    companion object {
        fun checkViewStyle(style: VoteViewStyle): VoteViewContract {
            return when (style) {
                FullScreen1 -> VoteViewFullScreen1()
                FullScreen2 -> VoteViewFullScreen2()
                Popover1 -> VoteViewPopover1()
                Popover2 -> VoteViewPopover2()
                Popover3 -> VoteViewPopover3()
                Popover4 -> VoteViewPopover4()
            }

        }

    }

}
