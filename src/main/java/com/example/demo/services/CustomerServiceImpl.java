package com.example.demo.services;

import com.example.demo.dtos.customer.CustomerRequest;
import com.example.demo.models.Customer;
import com.example.demo.models.Image;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.services.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CloudinaryService cloudinaryService) {
        this.customerRepository = customerRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer saveCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhone(customerRequest.getPhone());
        customer.setAddress(customerRequest.getAddress());
        customer.setCity(customerRequest.getCity());
        customer.setPassword(customerRequest.getPassword());

        try{
            String imageUrl =this.cloudinaryService.upload(customerRequest.getImage());
            customer.setImage(imageUrl);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        customer.setId(id);
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
