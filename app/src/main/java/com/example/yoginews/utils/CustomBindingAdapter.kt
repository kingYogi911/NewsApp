package com.example.yoginews.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.yoginews.R
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["url"], requireAll = false)
fun setImage(view: ImageView, url: String?) {
    if (url != null) {
        Picasso.get().load(url)
            .placeholder(R.drawable.loading_icon_foreground)
            .into(view)
    }
}