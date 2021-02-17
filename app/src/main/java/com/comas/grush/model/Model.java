package com.comas.grush.model;

import com.comas.grush.model.product.ProductModel;
import com.comas.grush.model.user.UserModel;

public class Model {

    public final static ProductModel products = ProductModel.instance;
    public final static UserModel users = UserModel.instance;

    private Model() { }

}
