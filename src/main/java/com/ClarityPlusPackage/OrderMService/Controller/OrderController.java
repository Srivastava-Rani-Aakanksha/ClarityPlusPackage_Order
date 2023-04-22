package com.ClarityPlusPackage.OrderMService.Controller;

import com.ClarityPlusPackage.OrderMService.Entity.Order;
import com.ClarityPlusPackage.OrderMService.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    //API Call to Recipient Details

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderService orderService;

    @GetMapping("/ordersOfInstituteID/{InstituteID}/")
    public List<String> getOrderByInstituteID(@PathVariable("InstituteID") String InstituteID){
        System.out.println("Inside Controller");
        List<String> recipientDetailsList = this.restTemplate.getForObject("http://recipient-microservice/recipient/search/"+InstituteID+"/", List.class);
        List<String> orderList = this.orderService.findOrderByOrderID(recipientDetailsList, InstituteID);
        System.out.println("Outside Controller");
        return orderList;
    }

    @GetMapping("/emailOfInstituteID/{InstituteID}/")
    public String getEmailOfInstituteID(@PathVariable ("InstituteID") String InstituteID){
        System.out.println("Inside Controller");
        String emailID = this.restTemplate.getForObject("http://idtoemailid-microservice/institute/getEmailID/"+InstituteID+"/",String.class);
        System.out.println(emailID);
        System.out.println("Outside Controller");
        return emailID;
    }

    @GetMapping("/search/logs/{InstituteID}/")
    public List<String> getLogsByInstituteID(@PathVariable("InstituteID") String InstituteID) {
        System.out.println("Inside Controller");
        List<String> logs = this.restTemplate.getForObject("http://recipient-microservice/recipient/search/logs/" + InstituteID + "/", List.class);
        System.out.println("Outside Controller");
        return logs;
    }



    //Will be implemented by Double A
    @PostMapping("/saveorderdata")
    public ResponseEntity<String> saveOrder(@RequestBody Order order)
    {
        String success=this.orderService.saveOrder(order);
        return ResponseEntity.ok(success);
    }
}
