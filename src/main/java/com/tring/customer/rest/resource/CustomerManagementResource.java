package com.tring.customer.rest.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tring.customer.dto.CustomerDto;
import com.tring.customer.rest.exception.CustomerResourceErrorCodes;
import com.tring.customer.rest.exception.CustomerResourceException;
import com.tring.customer.service.CustomerService;

/**
 * @author akula
 * 
 */
@Path("/customers")
public class CustomerManagementResource {
	Logger logger = LogManager.getLogger(CustomerManagementResource.class);

	@Autowired
	CustomerService customerService;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllCustomers() {
		logger.debug("getAllCustomers");
		List<CustomerDto> customerDtosList = null;
		try {
			customerDtosList = customerService.getAllCustomers();
		} catch (Exception ex) {
			logger.error(ex);
			throw new CustomerResourceException("Failed while getting list of customers...", CustomerResourceErrorCodes.INTERNAL_SERVER_ERROR);
		}
		if (customerDtosList != null && !customerDtosList.isEmpty()) {
			GenericEntity<List<CustomerDto>> genericEntityList = new GenericEntity<List<CustomerDto>>(customerDtosList){
			};
			return Response.ok(genericEntityList).build();
		} else {
			throw new CustomerResourceException("No customers found", CustomerResourceErrorCodes.NOT_FOUND);
		}
	}

	@GET
	@Path("{customerId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getCustomerById(@PathParam("customerId") int customerId) {
		logger.debug("getCustomerById");
		CustomerDto customerDto = null;
		try {
			customerDto = customerService.getCustomerById(customerId);
		} catch (Exception ex) {
			logger.error(ex);
			throw new CustomerResourceException("Failed in getting customer with id: ["+customerId+"]", CustomerResourceErrorCodes.INTERNAL_SERVER_ERROR);
		}
		if (customerDto != null ) {
			return Response.ok(customerDto).build();
		} else {
			throw new CustomerResourceException("There is no customer exists with customer id: " + customerId, CustomerResourceErrorCodes.NOT_FOUND);
		}
	}

	@PUT
	@Path("{customerId}")
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response udpateCustomerById(@PathParam("customerId") int customerId, CustomerDto customerDto) {
		logger.debug("updateCustomerById");
		try {
			CustomerDto dto=customerService.getCustomerById(customerId);
			if(dto!=null){
				customerService.updateCustomerById(customerDto);
				return Response.ok("Customer with id [ " + customerId + " ] updated successfully").build();
			}else{
				throw new CustomerResourceException("No customer found with id:[" + customerId+"] to update" , CustomerResourceErrorCodes.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw new CustomerResourceException("Failed in updating customer with id: " + customerId, CustomerResourceErrorCodes.INTERNAL_SERVER_ERROR);
		}
	}

	@DELETE
	@Path("{customerId}")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response deleteCustomerById(@PathParam("customerId") int customerId) {
		logger.debug("deleteCustomerById");
		CustomerDto customerDto=null;
		try {
			customerDto = customerService.getCustomerById(customerId);
		} catch (Exception ex) {
			logger.error(ex);
			throw new CustomerResourceException("Failed in deleting customer with id: " + customerId, CustomerResourceErrorCodes.INTERNAL_SERVER_ERROR);
		}
		if (customerDto != null) {
			customerService.deleteCustomerById(customerId);
			return Response.ok("Customer with id [ " + customerId + " ] deleted successfully").build();
		} else {
			throw new CustomerResourceException("There is no customer exists with customer id: [" + customerId+"]", CustomerResourceErrorCodes.NOT_FOUND);
		}
	}
}
