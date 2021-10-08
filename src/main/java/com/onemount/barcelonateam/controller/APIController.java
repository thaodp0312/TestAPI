package com.onemount.barcelonateam.controller;

import com.onemount.barcelonateam.model.Player;
import com.onemount.barcelonateam.model.TeamStatus;
import com.onemount.barcelonateam.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
//@RequestMapping("/api/public") //đường dẫn chung
public class APIController {

    @Autowired
    private CoachService coachService;
    //Hãy viết một API có đường dẫn
    // http://localhost:8080/api/public/chooseteam/
    // hãy trả về danh sách 11 cầu thủ sẽ ra sân

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(coachService.getAllPlayer());
    }

    @PostMapping("/team")
    public ResponseEntity<Set<Player>> team() {
        return ResponseEntity.ok(coachService.chooseTeam());
    }

    @GetMapping("/team")
    public ResponseEntity<Set<Player>> curentTeam() {
        return ResponseEntity.ok(coachService.currentTeam());
    }

    @GetMapping("/players/available")
    public ResponseEntity<List<Player>> players() {
        return ResponseEntity.ok(coachService.availablePlayers());
    }

    // http://localhost:8080/api/public/chooseteam/343
    //3 hậu vệ 4 trung vệ 3 tiền đạo
    @PostMapping("/team/{pattern}")
    public ResponseEntity<Set<Player>> chooseTeam(@PathVariable("pattern") String pattern) {
        return ResponseEntity.ok(coachService.chooseTeam(pattern));
    }

    @PostMapping("substitute/{playerNo}/{position}")
    /**
     * Rule:
     * - được phép thay tối đa 5 lần
     * - Cầu thủ ra sân không được vào sân lại
     *
     * Điều kiện tiên quyết:
     * - Đã chọn xong danh sách cầu thủ
     *
     * Yêu cầu hiển thị:
     * - Danh sách đội hình mới
     * - Danh sách các lần thay đổi cầu thủ
     *
     * Ngoại lệ - lỗi
     * - Thay quá số lần cho phép
     * - Vị trí muốn thay không còn cầu thủ nào
     * - Số áo cầu thủ không tồn tại
     * - Số áo cầu thủ không đá trên sân
     *
     */
    public ResponseEntity<TeamStatus> substitute(@PathVariable("playerNo") String playerNo, @PathVariable("position") String position) {

        // check
        return ResponseEntity.ok(coachService.substitute(playerNo, position));
    }

    @PostMapping("/finish")
    public ResponseEntity<Set<Player>> finish(){
        return ResponseEntity.ok(coachService.matchFinished());
    }



}
