package com.example.yoginews.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.yoginews.R
import com.example.yoginews.databinding.ActivityMainBinding
import com.example.yoginews.utils.MyHelper
import com.example.yoginews.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val viewModel: NewsViewModel by viewModels()
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater).apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = this@MainActivity
        }.also {
            setContentView(it.root)
            toolbar = it.toolbarLayout as Toolbar
            setSupportActionBar(it.toolbarLayout as Toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        MyHelper(this).observe(this, Observer {
            viewModel.isConnected.value = it ?: false
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.action_search).apply {
            val title = toolbar.findViewById<TextView>(R.id.toolbar_title)
            setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    title.visibility = View.GONE
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    title.visibility = View.VISIBLE
                    return true
                }
            })
            (actionView as SearchView)
                .also {
                    it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            query?.let { viewModel.search(it) }
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            return true
                        }
                    })
                }
        }
        return true
    }
}