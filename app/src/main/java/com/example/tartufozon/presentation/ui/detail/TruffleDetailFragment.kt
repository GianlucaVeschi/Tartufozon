package com.example.tartufozon.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tartufozon.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TruffleDetailFragment : Fragment() {

    private val truffleDetailViewModel: TruffleDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        truffleDetailViewModel.truffeDetail.observe(viewLifecycleOwner, {
            Timber.d("observe truffle $it.image_url")
        })

        return ComposeView(requireContext()).apply {
            setContent {
                Column(modifier = Modifier.padding(10.dp)) {
                    Image(
                        bitmap = imageFromResource(
                            res = resources,
                            resId = R.drawable.tartufo_bianco
                        ),
                        modifier = Modifier.height(300.dp),
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = "Hello from TruffleDetailFragment",
                        style = TextStyle(fontSize = TextUnit.Sp(21))
                    )
                }
            }
        }
    }
}