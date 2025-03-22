package vn.bookstore.app.service;

import vn.bookstore.app.dto.request.ReqBillDTO;
import vn.bookstore.app.dto.response.ResBillDTO;
import vn.bookstore.app.dto.response.ResGoodReceiptDTO;
import vn.bookstore.app.model.Bill;

import java.util.List;

public interface BillService {
    ResBillDTO createBill(ReqBillDTO request);

    List<ResBillDTO> getListBills();

    ResBillDTO getBillById(Long id);

}
