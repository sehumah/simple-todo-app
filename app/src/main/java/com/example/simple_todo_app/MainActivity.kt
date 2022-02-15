package com.example.simple_todo_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import org.apache.commons.io.FileUtils
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    // define list to hold tasks
    private var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. remove the item from the list
                listOfTasks.removeAt(position)

                // 2. notify the adapter that our dataset has changed
                adapter.notifyDataSetChanged()

                // save file after a tasks is deleted
                saveItems()
            }
        }

        // user should be able to edit a task


        // 1. detect when the user clicks on the add button

        /*
        // grab reference to the button view
        findViewById<Button>(R.id.button).setOnClickListener {
            Log.i("Stephen", "User clicked on button")
        }
        */

        // load tasks from file
        loadItems()

        // look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // attach adapter to recyclerView to populate items
        recyclerView.adapter = adapter

        // set layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up button & input field so that the user can enter a task and add it to the list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // get a reference to the button & set an onclick listener
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. grab the text the user had inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // 2. add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // 3. reset the text field
            inputTextField.setText("")

            // save inputted tasks
            saveItems()
        }
    }

    // save the data that the user has input by writing to and reading from a file


    // create a method to get the data file we need
    private fun getDataFile(): File {
        // every line in this file represents a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    // load the items by reading every line in our file
    private fun loadItems () {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // save/write items to the file
    fun saveItems () {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}
