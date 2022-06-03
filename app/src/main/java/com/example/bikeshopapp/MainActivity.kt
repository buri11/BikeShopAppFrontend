package com.example.bikeshopapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.bikeshopapp.product.ProductDelete
import com.example.bikeshopapp.product.ProductPostForm
import com.example.bikeshopapp.product.ProductTable
import com.example.bikeshopapp.product.ProductUpdate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getButton = findViewById<Button>(R.id.get_products)
        val postButton = findViewById<Button>(R.id.post_product)
        val updateButton = findViewById<Button>(R.id.update_product)
        val deleteButton = findViewById<Button>(R.id.delete_product)

        getButton.width = 500
        postButton.width = 500
        updateButton.width = 500
        deleteButton.width = 500

        getButton.setOnClickListener {
            val intent = Intent(this, ProductTable::class.java)
            startActivity(intent)
        }

        postButton.setOnClickListener {
            val intent = Intent(this, ProductPostForm::class.java)
            startActivity(intent)
        }

        updateButton.setOnClickListener {
            val intent = Intent(this, ProductUpdate::class.java)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            val intent = Intent(this, ProductDelete::class.java)
            startActivity(intent)
        }


    }
}