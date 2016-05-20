package com.nvisia.examples.camel.ordermanagement;

import java.util.*;

import org.apache.camel.component.servlet.*;
import org.apache.camel.model.rest.*;
import org.apache.camel.spring.boot.*;
import org.apache.camel.swagger.servlet.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.embedded.*;
import org.springframework.context.annotation.*;

/**
 * Spring boot application that defines the routes available for order
 * management.
 * 
 * @author Michael Hoffman, NVISIA
 *
 */
@SpringBootApplication
public class OrderManagementRouter extends FatJarRouter {

   @Override
   public void configure() {
      // Start by building an instance of RestConfigurationDefinition. Need to
      // specify the component we are going to use for enabling REST endpoints,
      // specifically CamelServlet in this case. Set the binding mode to JSON.
      restConfiguration().
            // Leverage the CamelServlet component for the REST DSL
            component("servlet").
            // Bind using JSON
            bindingMode(RestBindingMode.json).
            // I like pretty things...
            dataFormatProperty("prettyPrint", "true").
            // This is the context path to be used for Swagger API documentation
            apiContextPath("api-doc").
            // Properties for Swagger
            // Title of the API
      apiProperty("api.title", "Order Management API").
            // Version of the API
            apiProperty("api.version", "1.0.0").
            // CORS (resource sharing) enablement
            apiProperty("cors", "true").
            // Use localhost for calls
            apiProperty("host", "localhost:8082").
            // Set base path
            apiProperty("base.path", "nvisia-order-management-camel-service/api");

      // Definition of the post order endpoint
      rest("/order").
            // This is a POST method call for submitting an order using the
            // order form
      post().
            // Description of what the method does
            description("Submit a new order").
            // Define the type used for input
            type(OrderForm.class).
            // Define the type used for output
            outType(Order.class).
            // Route direct to process order by type
            to("direct:routeOrderByType");

      // Definition of the content based routing endpoint
      from("direct:routeOrderByType").
            // Use the Content Based Router EIP to process the order based on
            // the item type
      choice().
            // When this is a book, send it to the process book method
            when()
            .simple("${body.catalogItem.catalogItemType} == '"
                  + CatalogItemType.BOOK.toString() + "'")
            .to("bean:orderManagementService?method=processBookOrder(${body})").
            // When this is clothing, send it to the process clothing method
            when()
            .simple("${body.catalogItem.catalogItemType} == '"
                  + CatalogItemType.CLOTHING.toString() + "'")
            .to("bean:orderManagementService?method=processClothingOrder(${body})").
            // When this is electronics, send it to the process electronics
            // method
      when().simple("${body.catalogItem.catalogItemType} == '"
            + CatalogItemType.ELECTRONICS.toString() + "'")
            .to("bean:orderManagementService?method=processElectronicsOrder(${body})")
            .otherwise().throwException(new Exception("Invalid catalog item type"));
   }

   @Bean
   public ServletRegistrationBean camelServletRegistrationBean() {
      ServletRegistrationBean registration = new ServletRegistrationBean(
            new CamelHttpTransportServlet(), "/api/*");
      registration.setName("CamelServlet");
      return registration;
   }

   @Bean
   public ServletRegistrationBean swaggerServletRegistrationBean() {
      ServletRegistrationBean registration = new ServletRegistrationBean(
            new RestSwaggerServlet(), "/api-docs/*");
      registration.setName("SwaggerServlet");
      return registration;
   }

   @Bean
   public FilterRegistrationBean filterRegistrationBean() {
      FilterRegistrationBean filterBean = new FilterRegistrationBean();
      filterBean.setFilter(new RestSwaggerCorsFilter());
      List<String> urlPatterns = new ArrayList<String>();
      urlPatterns.add("/nvisia-order-management-camel-service/api-docs/*");
      urlPatterns.add("/nvisia-order-management-camel-service/api/*");
      filterBean.setUrlPatterns(urlPatterns);
      return filterBean;
   }

}
