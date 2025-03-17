package vn.bookstore.app.service;

import java.util.List;

import vn.bookstore.app.dto.request.ReqGoodReceiptDTO;
import vn.bookstore.app.dto.response.ResGoodReceiptDTO;

public interface GoodReceiptService {

    ResGoodReceiptDTO createGoodReceipt(ReqGoodReceiptDTO request);

    List<ResGoodReceiptDTO> getListGoodReceipts();

    ResGoodReceiptDTO getGoodReceiptById(Long id);
}
