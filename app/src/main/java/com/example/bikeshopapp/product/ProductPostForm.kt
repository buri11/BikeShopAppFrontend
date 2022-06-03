package com.example.bikeshopapp.product

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bikeshopapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductPostForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_form)

        val submitBttn = findViewById<Button>(R.id.add_product_button)
        val name = findViewById<EditText>(R.id.product_name)
        val brand = findViewById<EditText>(R.id.product_brand)
        val quantityText = findViewById<EditText>(R.id.product_quantity)
        val priceText = findViewById<EditText>(R.id.product_price)

        submitBttn.setOnClickListener {
            val quantity = Integer.parseInt(quantityText.text.toString())
            val price = priceText.text.toString().toFloat()
            if(quantity < 0){
                Toast.makeText(this@ProductPostForm, "Quantity is negative!", Toast.LENGTH_LONG).show()
            }
            else if(price < 0 ){
                Toast.makeText(this@ProductPostForm, "Quantity is negative!", Toast.LENGTH_LONG).show()
            }
            else{
                val product = ProductDataItem(
                    -1,
                    name.text.toString(),
                    brand.text.toString(),
                    quantity,
                    price)

                val retrofitBuilder = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://10.0.2.2:8080/api/v1/")
                    .build()
                    .create(ProductApiInterface::class.java)

                val response = retrofitBuilder.postProduct(product)
                response.enqueue(object : Callback<ProductDataItem> {
                    override fun onResponse(
                        call: Call<ProductDataItem>,
                        response: Response<ProductDataItem>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(
                                this@ProductPostForm,
                                "Product successfully added",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else{
                            Toast.makeText(
                                this@ProductPostForm,
                                "Product could not be added",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ProductDataItem>, t: Throwable) {
                        Log.d("Error in adding a product to database", t.message.toString())
                    }

                })
            }
        }
    }
}