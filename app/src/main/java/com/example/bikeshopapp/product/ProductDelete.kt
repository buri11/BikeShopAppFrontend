package com.example.bikeshopapp.product

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductDelete : AppCompatActivity() {
    var selectedID : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.bikeshopapp.R.layout.activity_product_delete)

        val delete = findViewById<View>(com.example.bikeshopapp.R.id.delete_button)
        val listView : ListView = findViewById(com.example.bikeshopapp.R.id.product_list_view_delete)
        var list : List<ProductDataItem> = ArrayList()

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://10.0.2.2:8080/api/v1/")
            .build()
            .create(ProductApiInterface::class.java)

        val data = retrofitBuilder.getProduct()

        data.enqueue(object : Callback<List<ProductDataItem>?>{
            override fun onResponse(
                call: Call<List<ProductDataItem>?>,
                response: Response<List<ProductDataItem>?>
            ) {
                val products : List<ProductDataItem>? = response.body()
                if(products != null){
                    list = products
                }

                val arrayAdapter : ArrayAdapter<ProductDataItem?> = ArrayAdapter<ProductDataItem?>(
                    applicationContext, R.layout.simple_list_item_1, list)

                listView.adapter = arrayAdapter
            }

            override fun onFailure(call: Call<List<ProductDataItem>?>, t: Throwable) {
                Toast.makeText(this@ProductDelete, "No products in the database", Toast.LENGTH_LONG).show();
            }
        })

        listView.onItemClickListener = AdapterView.OnItemClickListener {
            _, _, i, _ ->
            val selectedProduct : ProductDataItem = listView.getItemAtPosition(i) as ProductDataItem
            selectedID = selectedProduct.id.toLong()
            Toast.makeText(this@ProductDelete, "Selected product: $selectedProduct", Toast.LENGTH_SHORT).show();
        }

        delete.setOnClickListener{
            val call : Call<ProductDataItem> = retrofitBuilder.deleteProduct(selectedID)
            call.enqueue(object : Callback<ProductDataItem?>{
                override fun onResponse(
                    call: Call<ProductDataItem?>,
                    response: Response<ProductDataItem?>
                ) {
                    Toast.makeText(this@ProductDelete,"Product deleted!", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<ProductDataItem?>, t: Throwable) {
                    Log.d("Activity DELETE", "ON FAILURE $call")
                    Toast.makeText(this@ProductDelete,"Product deleted!", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }

            })
        }
    }
}