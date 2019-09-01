package com.mapsexperience

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mapsexperience.adapter.CarListAdapter
import com.mapsexperience.dagger.Injector
import com.mapsexperience.viewmodel.ListActivityViewModel
import com.mapsexperience.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_list.*
import javax.inject.Inject

class ListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: ListActivityViewModel

    val adapter: CarListAdapter = CarListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        carList.layoutManager = layoutManager
        carList.adapter = adapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListActivityViewModel::class.java)
        viewModel.getCarsInListLiveData().observe(this, Observer { carList ->
            adapter.setItems(carList)
        })

        viewModel.getCarsInList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_list_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.show_on_map) {
            startActivity(Intent(this, MapActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}