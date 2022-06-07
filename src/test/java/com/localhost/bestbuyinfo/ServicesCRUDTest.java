package com.localhost.bestbuyinfo;

import com.localhost.testbase.TestBase;
import com.localhost.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ServicesCRUDTest extends TestBase {
    static String name = "Manual Tester" + TestUtils.getRandomValue();
    static int serviceID;

    @Steps
    ServicesSteps servicesSteps;

    @Title("This will create a New Service")
    @Test
    public void test001() {
        ValidatableResponse response = servicesSteps.createService(name);
        response.log().all().statusCode(201);
        serviceID = response.log().all().extract().path("id");
        System.out.println(serviceID);
    }

    @Title("Verify if the Product was added to the application")
    @Test
    public void test002() {
        HashMap<String, ?> storeMap = servicesSteps.getServiceInfoByName(serviceID);
        Assert.assertThat(storeMap, hasValue(name));
        System.out.println(storeMap);
    }

    @Title("Update the product information")
    @Test
    public void test003() {
        name = name + "_updated";
        HashMap<Object, Object> servicesData = new HashMap<>();
        servicesSteps.updateService(serviceID,name);
        HashMap<String, ?> productList = servicesSteps.getServiceInfoByName(serviceID);
        Assert.assertThat(productList, hasValue(name));
        System.out.println(productList);
    }

    @Title("Delete the product by ID")
    @Test
    public void test004() {
        servicesSteps.deleteService(serviceID).statusCode(200);
        servicesSteps.getServiceByID(serviceID).statusCode(404);
    }

}

