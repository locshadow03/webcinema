package com.webcinema.service.bill;

import com.webcinema.model.Bill;
import com.webcinema.model.Customer;
import com.webcinema.model.Movie;
import com.webcinema.model.Ticket;

import java.util.List;

public interface IBillService {
    Bill addBill(Customer customer, List<Ticket> tickets);

    Bill billDetail(Long bill_id);

    List<Bill> getAllBillByCustomerId(Long customer_id);

    void deleteBill(Long bill_id);

    Bill updateStatusBill(Long bill_id, String status);
}
