package com.techenum.recyclerviewexample.kotlin

interface OnProductClickListener {

    /**
     * When the user clicks on each row this method will be invoked.
     */
    fun onUpdate(position: Int, model: ProductModel)

    /**
     * when the user clicks on delete icon this method will be invoked to remove item at position.
     */
    fun onDelete(model: ProductModel)

}