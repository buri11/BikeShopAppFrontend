package com.example.bikeshopapp.product

import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bikeshopapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductTable : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_table)

        getProducts()
    }

    private fun getProducts() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://10.0.2.2:8080/api/v1/")
            .build()
            .create(ProductApiInterface::class.java)

        val products = retrofitBuilder.getProduct()

        products.enqueue(object : Callback<List<ProductDataItem>?> {
            override fun onResponse(
                call: Call<List<ProductDataItem>?>,
                response: Response<List<ProductDataItem>?>
            ) {
                val responseBody = response.body()!!

                val table = findViewById<TableLayout>(R.id.product_table)

                for (product in responseBody) {
                    val tableRow = TableRow(this@ProductTable)
                    tableRow.setPadding(0, 5, 0, 5)

                    val id = TextView(this@ProductTable)
                    id.text = product.id.toString()
                    id.setTextColor(resources.getColor(R.color.table_red))
                    id.textSize = 12f
                    id.gravity = Gravity.CENTER
//                    id.width = 20
                    id.textAlignment = View.TEXT_ALIGNMENT_INHERIT
                    //id.layout.alignment = Layout.Alignment.ALIGN_CENTER
                    tableRow.addView(id)

                    val name = TextView(this@ProductTable)
                    name.text = product.name
                    name.setTextColor(resources.getColor(R.color.table_red))
                    name.textSize = 12f
                    name.gravity = Gravity.CENTER
                    name.textAlignment = View.TEXT_ALIGNMENT_INHERIT
//                    name.width = 600
                    tableRow.addView(name)

                    val brand = TextView(this@ProductTable)
                    brand.text = product.brand
                    brand.setTextColor(resources.getColor(R.color.table_red))
                    brand.textSize = 12f
                    brand.gravity = Gravity.CENTER
//                    brand.width = 50
                    tableRow.addView(brand)

                    val quantity = TextView(this@ProductTable)
                    quantity.text = product.quantity.toString()
                    quantity.setTextColor(resources.getColor(R.color.table_red))
                    quantity.textSize = 12f
                    quantity.gravity = Gravity.CENTER
//                    quantity.width = 50
                    tableRow.addView(quantity)

                    val price = TextView(this@ProductTable)
                    price.text = product.price.toString()
                    price.setTextColor(resources.getColor(R.color.table_red))
                    price.textSize = 12f
                    price.gravity = Gravity.CENTER
//                    price.width = 50
                    tableRow.addView(price)

                    table.addView(tableRow)
                }
            }

            override fun onFailure(call: Call<List<ProductDataItem>?>, t: Throwable) {
                Log.d("ProductTable", "Error while fetching " + t.message)
            }

        })
    }
}