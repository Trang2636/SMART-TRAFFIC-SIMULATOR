package com.ra.bt.ex5;

public class Main {
    public static void main(String[] args) {
        ReceptionView view = new ReceptionView();
        view.start();
    }
}

/*
1. Phân tích rủi ro & bẫy lỗi (Edge Cases)
Nhập sai kiểu dữ liệu
    Ví dụ nhân viên gõ "Năm trăm" vào ô tiền tạm ứng hoặc gõ chữ vào ô tuổi.
    Nếu không bắt lỗi, chương trình sẽ bị crash do lỗi chuyển kiểu dữ liệu.
Chọn giường không tồn tại hoặc giường đã có người
    Nếu không kiểm tra trước, hệ thống có thể tạo hồ sơ bệnh nhân nhưng không xếp được giường, dẫn đến dữ liệu sai.
Thêm bệnh nhân thành công nhưng cập nhật giường thất bại
    Nếu không dùng Transaction, bệnh nhân đã được lưu nhưng trạng thái giường không đổi.
    Kết quả là bệnh nhân có hồ sơ nhưng không có giường nằm.
Cập nhật giường xong nhưng ghi phiếu thu thất bại
    Nếu không rollback, hệ thống sẽ giữ giường cho bệnh nhân nhưng lại không có thông tin tiền tạm ứng.
Nhập tuổi âm hoặc tiền âm
    Nếu không validate input, dữ liệu lưu vào database sẽ không hợp lệ, gây sai nghiệp vụ.
 */


/*
2. Các bước xử lý logic cho chức năng “Tiếp nhận bệnh nhân”
    Hiển thị form nhập thông tin tiếp nhận
    Nhập tên bệnh nhân
    Nhập tuổi
    Nhập mã giường muốn chọn
    Nhập số tiền tạm ứng
    Kiểm tra dữ liệu nhập có hợp lệ hay không
    Mở kết nối database
    Tắt AutoCommit để bắt đầu Transaction
    Kiểm tra giường có tồn tại và đang trống hay không
    Thêm mới hồ sơ bệnh nhân
    Cập nhật trạng thái giường thành “Đã có người”
    Thêm phiếu thu tiền tạm ứng vào bảng tài chính
    Nếu cả 3 bước thành công thì commit
    Nếu có lỗi ở bất kỳ bước nào thì rollback
    In thông báo thành công hoặc thất bại
    Đóng kết nối và tài nguyên
 */

/*
3. Mô tả ngắn gọn cấu trúc các bảng trong Database
Bảng beds
    bed_id: mã giường
    bed_name: tên giường
    status: trạng thái giường
Bảng patients
    patient_id: mã bệnh nhân
    full_name: tên bệnh nhân
    age: tuổi
    bed_id: mã giường được xếp
    created_at: thời gian tiếp nhận
Bảng finance_records
    record_id: mã phiếu thu
    patient_id: mã bệnh nhân
    amount: số tiền tạm ứng
    created_at: thời gian lập phiếu
 */