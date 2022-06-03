package com.example.bikeshopapp.product

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bikeshopapp.R
import com.example.bikeshopapp.network.NullOnEmptyConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductUpdateForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_update_form)

        val updateBttn = findViewById<Button>(R.id.update_button)
        val name = findViewById<EditText>(R.id.product_name_update)
        val brand = findViewById<EditText>(R.id.product_brand_update)
        val quantityText = findViewById<EditText>(R.id.product_quantity_update)
        val priceText = findViewById<EditText>(R.id.product_price_update)

        val origId = intent.extras?.get("id")
        val origName = intent.extras?.get("name")
        val origBrand = intent.extras?.get("brand")
        val origQuantity = intent.extras?.get("quantity")
        val origPrice = intent.extras?.get("price")

        name.setText(origName.toString())
        brand.setText(origBrand.toString())
        quantityText.setText(origQuantity.toString())
        priceText.setText(origPrice.toString())

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://10.0.2.2:8080/api/v1/")
            .build()
            .create(ProductApiInterface::class.java)

        updateBttn.setOnClickListener {
            val quantity = Integer.parseInt(quantityText.text.toString())
            val price = priceText.text.toString().toFloat()

            if(quantity < 0){
                Toast.makeText(this@ProductUpdateForm, "Quantity is negative!", Toast.LENGTH_LONG).show()
            }
            else if(price < 0 ){
                Toast.makeText(this@ProductUpdateForm, "Quantity is negative!", Toast.LENGTH_LONG).show()
            }
            else{
                val data = retrofitBuilder.updateProduct(
                    (origId as Int).toLong(), name.text.toString(),
                    brand.text.toString(), Integer.parseInt(quantityText.text.toString()),
                    priceText.text.toString().toFloat())

                data.enqueue(object : Callback<ProductDataItem> {
                    override fun onResponse(
                        call: Call<ProductDataItem>,
                        response: Response<ProductDataItem>
                    ) {
                        Toast.makeText(
                            this@ProductUpdateForm,
                            "Product successfully updated!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(call: Call<ProductDataItem>, t: Throwable) {
                        Toast.makeText(
                            this@ProductUpdateForm,
                            "Product could not be updated!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
            }
        }





    }
}