package vn.bookstore.app.service;

import vn.bookstore.app.dto.request.ReqGoodReceiptDTO;
import vn.bookstore.app.dto.response.ResGoodReceiptDTO;
import vn.bookstore.app.model.GoodReceipt;

public interface GoodReceiptService {
    ResGoodReceiptDTO createGoodReceipt(ReqGoodReceiptDTO request);
}