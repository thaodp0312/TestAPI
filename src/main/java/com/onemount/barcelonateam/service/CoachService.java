package com.onemount.barcelonateam.service;

import com.onemount.barcelonateam.exceptions.TeamException;
import com.onemount.barcelonateam.model.Player;
import com.onemount.barcelonateam.model.Position;
import com.onemount.barcelonateam.model.Substitute;
import com.onemount.barcelonateam.model.TeamStatus;
import com.onemount.barcelonateam.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CoachService {
    //Hãy tạo method chọn team
    //Chọn ngẫu nhiên cho từng vị trí
    @Autowired
    private PlayerRepository playerRepository;

    private static final String DEFAULT_TEAM_PATTERN = "442";

    private Set<Player> currentTeam;
    private List<Substitute> substituteHistory;
    private Random rand;

    private Set<Player> selectedGk;
    private Set<Player> selectedDf;
    private Set<Player> selectedMf;
    private Set<Player> selectedFw;

    public CoachService() {
        currentTeam = new HashSet<>();
        substituteHistory = new ArrayList<>();
        rand = new Random();
        selectedGk = new HashSet<>();
        selectedDf = new HashSet<>();
        selectedMf = new HashSet<>();
        selectedFw = new HashSet<>();
    }


    // team pattern: 442, 4321
    public Set<Player> chooseTeam(String teamPattern) {
        currentTeam.clear();
        substituteHistory.clear();

        if (teamPattern.length() != 3)
            throw new TeamException("Invalid team pattern", "Pattern's length must be equals 3");

        int gkNum = 1; // always
        int dfNum = convertToInt(teamPattern.substring(0, 1));
        int mfNum = convertToInt(teamPattern.substring(1, 2));
        int fwNum = convertToInt(teamPattern.substring(2));

        if (gkNum + dfNum + mfNum + fwNum != 11)
            throw new TeamException("Invalid team pattern", dfNum + " + " + mfNum + " + " + fwNum + " must be 10");

        selectedGk = getPlayers(playerRepository.getPlayers(Position.GK), gkNum, Position.GK);
        selectedDf = getPlayers(playerRepository.getPlayers(Position.DF), dfNum, Position.DF);
        selectedMf = getPlayers(playerRepository.getPlayers(Position.MF), mfNum, Position.MF);
        selectedFw = getPlayers(playerRepository.getPlayers(Position.FW), fwNum, Position.FW);

        if (selectedGk.size() + selectedDf.size() + selectedMf.size() + selectedFw.size() != 11) {
            throw new TeamException("", "Unable to find players for team. There can be only " + currentTeam.size() + " players selected.");
        }
        currentTeam.addAll(selectedGk);
        currentTeam.addAll(selectedDf);
        currentTeam.addAll(selectedMf);
        currentTeam.addAll(selectedFw);
        return currentTeam;
    }

    private int convertToInt(String num) throws TeamException {
        try {
            int n = Integer.parseInt(num);
            if (n == 0) {
                throw new TeamException("Invalid team pattern", num + " is not a number or invalid");
            }
            return n;
        } catch (NumberFormatException ex) {
            throw new TeamException("Invalid team pattern", num + " is not a number or invalid");
        }
    }

    private Set<Player> getPlayers(List<Player> playerList, int num, Position position) {
        if (num > playerList.size())
            throw new TeamException("Cannot make team", "Do not have enough " + position + " players");
        Set<Player> players = new HashSet<>();
        while (num > 0) {
            int randomIndex = rand.nextInt(playerList.size());
            Player p = playerList.get(randomIndex);
            if (!players.contains(p)) {
                players.add(p);
                num--;
            }
        }
        return players;
    }

    public Set<Player> chooseTeam() throws TeamException {
        return chooseTeam(DEFAULT_TEAM_PATTERN);
    }

    /**
     * Rule:
     * - được phép thay tối đa 5 lần
     * - Cầu thủ ra sân không được vào sân lại
     * <p>
     * Điều kiện tiên quyết:
     * - Đã chọn xong danh sách cầu thủ
     * <p>
     * Yêu cầu hiển thị:
     * - Danh sách đội hình mới
     * - Danh sách các lần thay đổi cầu thủ
     * <p>
     * Ngoại lệ - lỗi
     * - Thay quá số lần cho phép
     * - Vị trí muốn thay không còn cầu thủ nào
     * - Số áo cầu thủ không tồn tại
     * - Số áo cầu thủ không đá trên sân
     */
    public TeamStatus substitute(String playerNo, String position) {
        // Đã chọn xong danh sách cầu thủ
        if (currentTeam == null || currentTeam.size() != 11) {
            throw new TeamException("Team must be made first", "");
        }

        // check maximum of sub
        if (substituteHistory == null || substituteHistory.size() == 5) {
            throw new TeamException("Team reach maximum 5 times of substitute", "");
        }

        int number = convertToInt(playerNo);
        Position pos = getPosition(position);

        // check player with playNo is playing
        Player outPlayer = currentTeam.stream()
                .filter(p -> p.getNumber() == number)
                .findAny()
                .orElse(null);
        if (outPlayer == null) {
            throw new TeamException("Player with number " + playerNo + " is not playing or invalid", "");
        }

        List<Player> availablePlayerForSub = getAvailablePlayers(number, pos);

        if (availablePlayerForSub.isEmpty()) {
            throw new TeamException("There is no player available for substitute", "");
        }

        Player inPlayer = availablePlayerForSub.get(rand.nextInt(availablePlayerForSub.size()));
        substituteHistory.add(new Substitute(inPlayer, outPlayer));
        currentTeam.remove(outPlayer);
        currentTeam.add(inPlayer);

        TeamStatus teamsStatus = new TeamStatus();
        teamsStatus.setCurrentTeam(currentTeam);
        teamsStatus.setSubstituteHistory(substituteHistory);
        return teamsStatus;
    }

    private Position getPosition(String position) throws TeamException {
        try {
            return Position.valueOf(position.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new TeamException("Position '" + position + "' is not valid.", "");
        }
    }

    private List<Player> getAvailablePlayers(int playerNo, Position position) {

        // using stream
        List<Player> subPlayers = substituteHistory.stream()
                .map(Substitute::getOutPlayer)
                .collect(Collectors.toList());

        return playerRepository.getPlayers()
                .stream()
                .filter(p -> p.getPosition() == position
                        && p.getNumber() != playerNo
                        && !currentTeam.contains(p)
                        && !subPlayers.contains(p))
                .collect(Collectors.toList());
    }

    public Set<Player> getTeam() throws TeamException {
        // Đã chọn xong danh sách cầu thủ
        if (currentTeam == null || currentTeam.size() != 11) {
            throw new TeamException("Team must be made first", "");
        }
        return currentTeam;
    }

    /**
     * Nếu chưa form team thì form team trước
     *
     * @return
     * @throws TeamException
     */
    public Map<String, Set<Player>> playerGroup() throws TeamException {
        if (currentTeam.isEmpty()) {
            chooseTeam("442");
        }

        Map<String, Set<Player>> result = new HashMap<>();
        result.put("GK", selectedGk);
        result.put("DF", selectedDf);
        result.put("MF", selectedMf);
        result.put("FW", selectedFw);

        return result;
    }

    public List<Player> getAllPlayer() {
        return playerRepository.getPlayers();
    }

    public List<Player> availablePlayers() {
        if (currentTeam.isEmpty()) {
            throw new TeamException("Team must be made first", "");
        }
        return playerRepository.getPlayers().stream().filter(p -> !currentTeam.contains(p))
                .collect(Collectors.toList());
    }

    public Set<Player> currentTeam() {
        if (currentTeam.isEmpty()) {
            throw new TeamException("Team must be made first", "");
        }
        return currentTeam;
    }

    public Set<Player> matchFinished(){
        currentTeam.clear();
        return currentTeam;
    }
}
