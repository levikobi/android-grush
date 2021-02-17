package com.comas.grush.model;

import com.comas.grush.model.product.ProductModel;

public class Model {

    public final static ProductModel products = ProductModel.instance;

    private Model() { }

}
