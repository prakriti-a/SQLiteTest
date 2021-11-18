package com.prakriti.sqlitetest

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.prakriti.sqlitetest.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
// Windows -> use backslash in paths (\), Linux -> use forward slashes (/)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val database = baseContext.openOrCreateDatabase("sqlite-test-1.db", MODE_PRIVATE, null)
        // drop table so insert is not performed every time app runs
//        database.execSQL("DROP TABLE IF EXISTS contacts")
//
//        var sqlCREATE = "CREATE TABLE IF NOT EXISTS contacts(_id INTEGER PRIMARY KEY NOT NULL, name TEXT, phone INTEGER, email TEXT)"
//        Log.i(TAG, "onCreate: create: $sqlCREATE")
//        database.execSQL(sqlCREATE) // executes any sql statement that doesn't return data
//
//        var sqlINSERT = "INSERT INTO contacts(name, phone, email) VALUES('amy', 9876, 'amy@gmail.com')"
//        Log.i(TAG, "onCreate: insert: $sqlINSERT")
//        database.execSQL(sqlINSERT)
//
//        val values = ContentValues().apply {
//            // apply is an extension fn, runs just like a fn of the ContentValues class
//            // apply returns the object it was called on
//            put("name", "terry")
//            put("phone", 1234)
//            put("email", "terry@gmail.com")
//        } // ContentValues is a wrapper class around a hash map
//
//        val generatedID = database.insert("contacts", null, values) // pass table name & values, returns ID
//        Log.i(TAG, "onCreate: inserted record with ID: $generatedID")

        // cursor only retrieves one record at a time
        val query = database.rawQuery("SELECT * FROM contacts", null)
        query.use {
            // use can be called on closeable objects like query -> here query obj is referred to as 'Receiver'
            // executes this block fn on the resource & then closes it, similar to try with resources in java
            while(it.moveToNext()) { // does the same thing as moveToFirst at first usage
                // loop thru records
                with(it) {
                    // it -> ref to 'query'
                    // use it, not query -> causes extra instructions to be added to stack, inefficient code
                    val id = getLong(0)
                    val name = getString(1)
                    val phone = getInt(2)
                    val email = getString(3)
                    val result = "ID: $id, Name: $name, Phone: $phone, Email: $email"
                    Log.i(TAG, "onCreate: cursor result: $result")
                }
            }
        }
        database.close() // this is done at close of the app

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}