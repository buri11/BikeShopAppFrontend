package com.example.bikeshopapp.product

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bikeshopapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductUpdate : AppCompatActivity() {
    private lateinit var selectedProduct: ProductDataItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_update)

        val listView : ListView = findViewById(com.example.bikeshopapp.R.id.product_list_view_delete)
        var list : List<ProductDataItem> = ArrayList()

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://10.0.2.2:8080/api/v1/")
            .build()
            .create(ProductApiInterface::class.java)

        val data = retrofitBuilder.getProduct()

        data.enqueue(object : Callback<List<ProductDataItem>?> {
            override fun onResponse(
                call: Call<List<ProductDataItem>?>,
                response: Response<List<ProductDataItem>?>
            ) {
                val products : List<ProductDataItem>? = response.body()
                if(products != null){
                    list = products
                }

                val arrayAdapter : ArrayAdapter<ProductDataItem?> = ArrayAdapter<ProductDataItem?>(
                    applicationContext, android.R.layout.simple_list_item_1, list)

                listView.adapter = arrayAdapter
            }

            override fun onFailure(call: Call<List<ProductDataItem>?>, t: Throwable) {
                Toast.makeText(this@ProductUpdate, "No products in the database", Toast.LENGTH_LONG).show();
            }
        })

        listView.onItemClickListener = AdapterView.OnItemClickListener {
                _, _, i, _ ->
            val selectedProduct : ProductDataItem = listView.getItemAtPosition(i) as ProductDataItem
            //Toast.makeText(this@ProductUpdate, "Selected product: $selectedProduct", Toast.LENGTH_SHORT).show();

            val intent = Intent(this, ProductUpdateForm::class.java)

            intent.putExtra("id", selectedProduct.id)
            intent.putExtra("name", selectedProduct.name)
            intent.putExtra("brand", selectedProduct.brand)
            intent.putExtra("quantity", selectedProduct.quantity)
            intent.putExtra("price", selectedProduct.price)

            startActivity(intent)
        }
    }
}