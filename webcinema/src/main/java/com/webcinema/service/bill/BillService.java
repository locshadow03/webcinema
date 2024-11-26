package com.webcinema.service.bill;

import com.webcinema.extension.ResourceNotFoundException;
import com.webcinema.model.Bill;
import com.webcinema.model.Customer;
import com.webcinema.model.Movie;
import com.webcinema.model.Ticket;
import com.webcinema.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillService implements IBillService{
    private final BillRepository billRepository;
    @Override
    public Bill addBill(Customer customer, List<Ticket> tickets) {
        Bill bill = new Bill();

        bill.setTickets(tickets);
        int totalMoney = 0;

        for(Ticket ticket : tickets){
            totalMoney += ticket.getSeatSchedule().getSchedule().getPrice();
        }

        bill.setTotalAmount(totalMoney);
        bill.setPaymentStatus("Chưa thanh toán!");
        bill.setCustomer(customer);

        return billRepository.save(bill);
    }

    @Override
    public Bill billDetail(Long bill_id) {
        Optional<Bill> bill = billRepository.findById(bill_id);

        if(bill.isEmpty()){
            throw new ResourceNotFoundException("Không tìm thấy bill!");
        } else{
            return bill.get();
        }
    }

    @Override
    public List<Bill> getAllBillByCustomerId(Long customer_id) {

        List<Bill> listBill = billRepository.findAllBillByCustomer_id(customer_id);
        return listBill;
    }

    @Override
    public void deleteBill(Long bill_id) {
        Optional<Bill> bill = billRepository.findById(bill_id);

        if(bill.isEmpty()){
            throw new ResourceNotFoundException("Không tìm thấy bill!");
        } else{
            billRepository.deleteById(bill_id);
        }
    }

    @Override
    public Bill updateStatusBill(Long bill_id, String status) {
        Optional<Bill> bill = billRepository.findById(bill_id);

        if(bill.isEmpty()){
            throw new ResourceNotFoundException("Không tìm thấy bill!");
        } else{
            Bill theBill = bill.get();
            theBill.setPaymentStatus(status);
            return billRepository.save(theBill);
        }
    }
}
