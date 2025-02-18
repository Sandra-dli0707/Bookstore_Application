package com.example.Bookstore.Controller;

import com.example.Bookstore.DTO.CustomersDetailsDto;
import com.example.Bookstore.Model.CustomerDetails;
import com.example.Bookstore.Repository.UsersRepo;
import com.example.Bookstore.Service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerDetailsController {
    @Autowired
    CustomerDetailsService customerDetailsService;

    @Autowired
    UsersRepo usersRepo;

    @GetMapping("/fetch")
    public List<CustomerDetails> fetchAllCustomer(){
        return customerDetailsService.readAll();
    }

    @PostMapping("/addcustomer")
    public String addNewCustomer(@RequestBody CustomersDetailsDto customerDetailsDto){
        CustomerDetails customerDetails=new CustomerDetails();
        customerDetails.setCustomerName(customerDetailsDto.getCustomerName());
        customerDetails.setEmail(customerDetailsDto.getEmail());
        customerDetails.setAddress(customerDetailsDto.getAddress());
        customerDetails.setPhoneNo(customerDetails.getPhoneNo());
        customerDetails.setUsers(usersRepo.findById(customerDetailsDto.getUser_id()).orElse(null));
       return customerDetailsService.addCustomer(customerDetails);
    }

    @PutMapping("/updatecustomerbyid/{id}")
    public String updateingCustomerById(@PathVariable("id")Long id,@RequestBody CustomerDetails customerDetails){
        return customerDetailsService.updateCustomerById(id, customerDetails);
    }
}
