package vn.bookstore.app.service;

import java.util.List;

import vn.bookstore.app.dto.request.ReqBillDTO;
import vn.bookstore.app.dto.response.ResBillDTO;

public interface BillService {

    ResBillDTO createBill(ReqBillDTO request);

    List<ResBillDTO> getListBills();

    ResBillDTO getBillById(Long id);

}
