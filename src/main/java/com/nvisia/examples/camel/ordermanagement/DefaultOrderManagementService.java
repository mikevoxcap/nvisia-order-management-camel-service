package com.nvisia.examples.camel.ordermanagement;

import org.springframework.stereotype.*;

/**
 * Default implementation for order management.
 * 
 * @author Michael Hoffman, NVISIA
 *
 */
@Service("orderManagementService")
public class DefaultOrderManagementService implements OrderManagementService {

   @Override
   public String processBookOrder(OrderForm orderForm) {
      return "B-112345-" + orderForm.getCustomer().getId();
   }

   @Override
   public String processClothingOrder(OrderForm orderForm) {
      return "C-4623-" + orderForm.getCustomer().getId();
   }

   @Override
   public String processElectronicsOrder(OrderForm orderForm) {
      return "E-9999-" + orderForm.getCustomer().getId();
   }


}
