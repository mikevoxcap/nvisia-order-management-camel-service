package com.nvisia.examples.camel.ordermanagement;

/**
 * Interface for services to process orders as part of order management.
 * 
 * @author Michael Hoffman
 *
 */
public interface OrderManagementService {

   /**
    * Processes a book order and returns an order number.
    * 
    * @param orderForm
    * @return
    */
   String processBookOrder(OrderForm orderForm);
   
   /**
    * Processes a clothing order and returns an order number.
    * 
    * @param orderForm
    * @return
    */
   String processClothingOrder(OrderForm orderForm);

   /**
    * Processes an electronics order and returns an order number.
    * 
    * @param orderForm
    * @return
    */
   String processElectronicsOrder(OrderForm orderForm);

}
