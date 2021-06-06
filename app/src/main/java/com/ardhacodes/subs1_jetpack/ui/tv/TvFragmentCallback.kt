package com.ardhacodes.subs1_jetpack.ui.tv

import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity

interface TvFragmentCallback {
    fun onItemClicked(tvEntity: TvEntity)
}
