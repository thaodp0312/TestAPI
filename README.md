Rule

Players
1 đội bóng có tổng cộng 22 cầu thủ.

Marc-André ter Stegen - GK
Sergiño Dest - DF
Gerard Piqué - DF
Ronald Araújo - DF
Sergio Busquets - MF
Antoine Griezmann - FW
Miralem Pjanić - MF
Martin Braithwaite - FW
Lionel Messi - FW
Ousmane Dembélé - FW
Riqui Puig - MF
Neto - GK
Clément Lenglet - DF
Pedri - MF
Francisco Trincão - FW
Jordi Alba - DF
Matheus Fernandes - MF
Sergi Roberto - DF
Frenkie de Jong - MF
Ansu Fati - FW
Samuel Umtiti - DF
Junior Firpo - DF

Mỗi cầu thủ sẽ chơi ở 1 vị trí trên sân. Có 4 vị trí trên sâu như sau:

FW
MF
DF
GK

API GET /players sẽ trả về danh sách 22 cầu thủ với thông tin số áo, vị trí trên sân và tên.

Tạo team
Khi ra sân thì huấn luyện viên sẽ chọn ra đội hình gồm 11 cầu thủ và trong 11 cầu thủ đó luôn có 1 cầu thủ là GK.
API POST /team tạo team với 11 cầu thủ theo đội hình DEFAULT là 442.
API POST /team/{pattern} tạo team với 11 cầu thủ theo đội hình mong muốn ứng với {pattern}

độ dài pattern luôn bằng 3 ký tự.
Tổng các số trong pattern bằng 10, tương đương chọn 10 cầu thủ bất kỳ. API sẽ tự chọn 1 cầu thủ random ở vị trí thủ môn GK để đủ team 11 người.

Đội hình được xác định dựa trên pattern gồm 3 ký tự số (0-9).
Ví dụ:

Pattern 442 sẽ là 4 cầu thủ ở vị trí DF, 4 cầu thủ ở vị trí MF, 2 cầu thủ ở vị trí FW

Pattern 532 sẽ là 5 cầu thủ ở vị trí DF, 3 cầu thủ ở vị trí MF, 2 cầu thủ ở vị trí FW

Pattern 443 sẽ báo lỗi vì số lượng cầu thủ vượt quá 11 cầu thủ.


Thay người
Khi đội bóng được tạo và ra sân, huấn luyện viên có quyền thay người.

Chỉ được thay người sau khi đã tạo team
Tối đa được phép thay người 5 lần
Người đã được thay ra thì không được vào lại sân lần nữa
Khi vị trí cần thay không còn cầu thủ nào nữa, thì sẽ không thể thay người được.

API POST /substitue/{playerNo}/{position} sẽ tiến hành thay người như sau:


playerNo: số áo của cầu thủ đang chơi trên sân

position: vị trí cần thay thế
Chọn lấy bất kỳ cầu thủ dự bị nào chơi ở vị trí position để thay thế cho cầu thủ mang số áo playerNo chơi trên sân.

Ví dụ: khi tạo team, ta có cầu thủ Messi (số 10, vị trí FW), ta thay 1 cầu thủ khác vào chơi ở vị trí MF thì sử dụng
POST /substitue/10/MF
API GET /players/available sẽ trả về tất cả các cầu thủ đang dự bị và có thể được chọn để thay vào sân.

Kết thúc trận đấu
Khi kết thúc trận đấu, các cầu thủ trên sân sẽ không còn, và tất cả các cầu thủ sẽ available để tạo team mới.
API POST /finish kết thúc trận đấu và clear danh sách các cầu thủ đang chơi.
