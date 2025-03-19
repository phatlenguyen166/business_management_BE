package vn.bookstore.app.service;

import vn.bookstore.app.dto.request.ReqBillDTO;
import vn.bookstore.app.dto.response.ResBillDTO;
import vn.bookstore.app.dto.response.ResBillDetailDTO;

public interface BillService {
    ResBillDTO createBill(ReqBillDTO request);

}
