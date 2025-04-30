package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "staff_statistic_of_month")
@Getter
@Setter
public class StaffStatisticOfMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String monthOfYear;
    // Số nhân viên đang làm việc (hợp đồng còn hiệu lực)
    private int activeEmployees;
    // Số nhân viên mới gia nhập trong kỳ
    private int newEmployees;
    // Số nhân viên nghỉ việc trong kỳ
    private int resignedEmployees;
    // Số nhân viên hết hạn hợp đồng chưa gia hạn
    private int contractExpired;
    // Tuổi trung bình của nhân viên
    private float avgAge;
    // Tỷ lệ giới tính (ví dụ: tỷ lệ nam/nữ, tính bằng phần trăm nam)
    private float genderRatio;
    // Số nhân viên theo loại hợp đồng (chính thức, thử việc, ngắn hạn)
    private int permanentEmployees; // Nhân viên chính thức
    private int probationEmployees; // Nhân viên thử việc
    // Thâm niên làm việc trung bình (tính bằng năm)
    private float avgTenure;
    // Tỷ lệ nghỉ việc trong kỳ (phần trăm)
    private float turnoverRate;
    // Số nhân viên được thăng chức trong kỳ
    private int promotedEmployees;


}
