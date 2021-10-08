# Examination (100 điểm)

Yêu cầu:
- Viết đủ test case: chiếm 40% số điểm của mỗi bài
- Code: chiếm 60% của mỗi bài
    - Các step/business logic cần đặt trong package `com.omc.api.barcelona` ở src/main/java
    - Các step definition cần đặt trong package `com.omc.api.barcelona.test` ở src/test/java
    - Đầy đủ assert cho các phần cần verify
    - Sử dụng exception nếu cần thiết cho flow API
    - Cần xác định rõ các thông tin nào có thể fix và có thể thay đổi.
    - Các thông tin data có thể thay đổi không được hardcode trong file java.

# Exam 01: single API (30 điểm)
Sử dụng cucumber để tạo `TẤT CẢ` các test case cho 1 API: `POST /team/{pattern}`

# Exam 02: flow APIs (70 điểm)
Sử dụng cucumber để tạo test case cho kịch bản sau

| No    | Step                      | Expected result                                           |
| ----- | ------------------------- | --------------------------------------------------------- | 
| 1     | Get danh sách các cầu thủ | Response trả về kết quả 22 cầu thủ                        |
| 2     | Tạo team                  | Response trả về 11 cầu thủ với đội hình DEFAULT là 442    | 
| 3     | Thay người hợp lệ 5 lần   | Số áo cầu thủ ra sân và vị trí cầu thủ vào sân bất kỳ. <br> Kết quả trả về đội hình hiện tại và lịch sử thay người. <br> Để thay người hợp lệ nên kiểm tra số cầu thủ ở vị trí     | 
| 4     | Kết thúc trận đấu         | Không có cầu thủ nào đang chơi                            |

