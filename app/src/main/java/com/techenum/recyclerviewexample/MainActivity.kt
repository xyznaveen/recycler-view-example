package com.techenum.recyclerviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.techenum.recyclerviewexample.kotlin.OnProductClickListener
import com.techenum.recyclerviewexample.kotlin.ProductListAdapter
import com.techenum.recyclerviewexample.kotlin.ProductModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var addProduct: Button
    private lateinit var updateProduct: Button
    private lateinit var productName: EditText
    private lateinit var productUnit: EditText

    private lateinit var productList: RecyclerView

    /**
     * The adapter which we have prepared.
     */
    private lateinit var mProductListAdapter: ProductListAdapter

    /**
     * To hold the reference to the items to be updated as a stack.
     * We can just remove and get the item with [Stack] in one shot.
     */
    private var modelToBeUpdated: Stack<ProductModel> = Stack()

    /**
     * The listener which we have defined in [OnProductClickListener]. Will be added to the adapter
     * which constructing the adapter
     */
    private val mOnProductClickListener = object : OnProductClickListener {
        override fun onUpdate(position: Int, model: ProductModel) {

            // we want to update
            modelToBeUpdated.add(model)

            // set the value of the clicked item in the edit text
            productName.setText(model.name)
            productUnit.setText(model.price)
        }

        override fun onDelete(model: ProductModel) {

            mProductListAdapter.removeProduct(model)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize the recycler view
        productList = findViewById(R.id.product_list_recycler_view)
        productList.layoutManager = LinearLayoutManager(this)
        productList.setHasFixedSize(true)

        mProductListAdapter = ProductListAdapter(this, mOnProductClickListener)
        productList.adapter = mProductListAdapter

        productName = findViewById(R.id.product_name)
        productUnit = findViewById(R.id.product_price)

        updateProduct = findViewById(R.id.update_product)
        updateProduct.setOnClickListener {

            // we have nothing to update
            if (modelToBeUpdated.isEmpty()) return@setOnClickListener

            val name = productName.text.toString()
            val price = productUnit.text.toString()

            if (!name.isBlank() && !price.isBlank()) {
                val model = modelToBeUpdated.pop()
                model.name = name
                model.price = price
                mProductListAdapter.updateProduct(model)

                // reset the input
                productName.setText("")
                productUnit.setText("")
            }
        }

        addProduct = findViewById(R.id.add_product)
        addProduct.setOnClickListener {

            val name = productName.text.toString()
            val price = productUnit.text.toString()

            if (!name.isBlank() && !price.isBlank()) {

                // prepare id on incremental basis
                val id = mProductListAdapter.getNextItemId()

                // prepare model for use
                val model = ProductModel(id, name, price)

                // add model to the adapter
                mProductListAdapter.addProduct(model)

                // reset the input
                productName.setText("")
                productUnit.setText("")
            }
        }

    }

}