import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameManager {
    private static final Pattern patBot = Pattern.compile("(\\d{4}/\\d{2})-([^-@#]*)-([^-@#]*)");
    private static final Pattern patAtk = Pattern.compile("(\\d{4}/\\d{2})-([^-@#]*)@([^-@#]*)-([^-@#]*)");
    private static final Pattern patAoe = Pattern.compile("(\\d{4}/\\d{2})-([^-@#]*)@#-([^-@#]*)");
    private HashMap<String, Adventurer> adventurersInFight;
    private HashMap<Integer, Adventurer> adventurers;
    private HashMap<String, ArrayList<LoggerBase>> loggers;

    public GameManager() {
        adventurers = new HashMap<>();
        loggers = new HashMap<>();
        adventurersInFight = new HashMap<>();
    }

    public HashMap<Integer, Adventurer> getAdventurers() {
        return adventurers;
    }

    public int update(ArrayList<String> input) {
        int type = Integer.parseInt(input.get(Constants.OP_IDX_TYPE));
        int advID = 0;
        Adventurer adventurer = null;
        if (type != Constants.ADD_LOG && type != 15) {
            advID = Integer.parseInt(input.get(Constants.OP_IDX_ADV_ID));
            adventurer = adventurers.get(advID);
        }
        switch (type) {
            case Constants.ADD_ADVENTURER: {
                adventurers.put(advID, new Adventurer(advID, input.get(Constants.OP_IDX_ADV_NAME)));
                break;
            }
            case Constants.OBTAIN_BOTTLE:
            case Constants.OBTAIN_EQUIPMENT:
            case Constants.OBTAIN_FOOD: {
                update(adventurer, type, argParserObtain(input));
                break;
            }
            case Constants.DROP_BOTTLE:
            case Constants.DROP_EQUIPMENT:
            case Constants.STAR_UP_EQUIPMENT:
            case Constants.DROP_FOOD:
            case Constants.FETCH_BOTTLE:
            case Constants.FETCH_EQUIPMENT:
            case Constants.FETCH_FOOD: {
                update(adventurer, type, argParserSingleID(input));
                break;
            }
            case Constants.USE_BOTTLE:
            case Constants.USE_FOOD: {
                update(adventurer, type, argParserSingleName(input));
                break;
            }
            case Constants.ADD_LOG: {
                System.out.println("Enter Fight Mode");
                return update(
                        Integer.parseInt(input.get(Constants.OP_IDX_LOG_M)),
                        Integer.parseInt(input.get(Constants.OP_IDX_LOG_K)),
                        input.subList(Constants.OP_IDX_LOG_NAME_BEGIN, input.size())
                );
            }
            default: {
            }
        }
        return 0;
    }

    private void update(Adventurer adv, int type, Pair<String, Pair<Integer, Integer>> args) {
        switch (type) {
            case Constants.OBTAIN_BOTTLE: {
                adv.obtainBottle(args);
                break;
            }
            case Constants.OBTAIN_EQUIPMENT: {
                adv.obtainEquipment(args);
                break;
            }
            case Constants.OBTAIN_FOOD: {
                adv.obtainFood(args);
                break;
            }
            default: {
            }
        }
    }

    private void update(Adventurer adv, int type, int arg) {
        switch (type) {
            case Constants.DROP_BOTTLE: {
                adv.dropBottle(arg);
                break;
            }
            case Constants.DROP_EQUIPMENT: {
                adv.dropEquipment(arg);
                break;
            }
            case Constants.STAR_UP_EQUIPMENT: {
                adv.enhanceEquipment(arg);
                break;
            }
            case Constants.DROP_FOOD: {
                adv.dropFood(arg);
                break;
            }
            case Constants.FETCH_BOTTLE: {
                adv.fetchBottle(arg);
                break;
            }
            case Constants.FETCH_EQUIPMENT: {
                adv.fetchEquipment(arg);
                break;
            }
            case Constants.FETCH_FOOD: {
                adv.fetchFood(arg);
                break;
            }
            default: {
            }
        }
    }

    private void update(Adventurer adv, int type, String arg) {
        switch (type) {
            case Constants.USE_BOTTLE: {
                adv.useBottle(arg);
                break;
            }
            case Constants.USE_FOOD: {
                adv.useFood(arg);
                break;
            }
            default: {
            }
        }
    }

    private int update(int ignored, int k, List<String> adventuresNames) {
        adventuresNames.forEach(s -> adventurersInFight.put(s, getAdventurerByName(s)));
        return k;
    }

    public void clearFightMode() {
        adventurersInFight.clear();
    }

    private Pair<String, Pair<Integer, Integer>> argParserObtain(ArrayList<String> input) {
        return new Pair<>(input.get(Constants.OP_IDX_OBJ_NAME), new Pair<>(Integer.parseInt(input.get(Constants.OP_IDX_OBJ_ID)), Integer.parseInt(input.get(Constants.OP_IDX_OBJ_ATTR))));
    }

    private int argParserSingleID(ArrayList<String> input) {
        return Integer.parseInt(input.get(Constants.OP_IDX_OBJ_ID));
    }

    private String argParserSingleName(ArrayList<String> input) {
        return input.get(Constants.OP_IDX_USE_OBJ_NAME);
    }

    public void dispatchLog(String log) {
        Matcher matcherBot = patBot.matcher(log);
        Matcher matcherAtk = patAtk.matcher(log);
        Matcher matcherAoe = patAoe.matcher(log);
        if (matcherBot.find()) {
            String date = matcherBot.group(1);
            Adventurer adventurer = adventurersInFight.get(matcherBot.group(2));
            String botName = matcherBot.group(3);
            if (adventurer == null || !adventurer.checkBottle(botName)) {
                errorFightLog();
                return;
            }
            adventurer.useBottleInFight(botName);
            addLog(date, new LoggerBottles(date, adventurer.getName(), botName));
        } else if (matcherAtk.find()) {
            String date = matcherAtk.group(1);
            Adventurer attacker = adventurersInFight.get(matcherAtk.group(2));
            Adventurer attackee = adventurersInFight.get(matcherAtk.group(3));
            String equName = matcherAtk.group(4);
            System.out.println("Atk: " + date + equName);
            if (attacker == null || attackee == null || !attacker.checkEquipment(equName)) {
                errorFightLog();
                return;
            }
            int damage = attacker.useEquipmentInFight(equName);
            LoggerNormalAttack logger =
                    new LoggerNormalAttack(date, attacker.getName(), attackee.getName(), equName);
            attacker.doAttack(logger);
            int restPower = attackee.beAttacked(damage, logger);
            System.out.println(attackee.getId() + " " + restPower);
        } else if (matcherAoe.find()) {
            String date = matcherAoe.group(1);
            Adventurer attacker = adventurersInFight.get(matcherAoe.group(2));
            String equName = matcherAoe.group(3);
            if (attacker == null || !attacker.checkEquipment(equName)) {
                errorFightLog();
                return;
            }
            int damage = attacker.useEquipmentInFight(equName);
            LoggerAoeAttack logger =
                    new LoggerAoeAttack(date, attacker.getName(), equName);
            attacker.doAttack(logger);
            ArrayList<String> outList = new ArrayList<>();
            adventurersInFight.forEach((ignored, adventurer) -> {
                if (adventurer == attacker) {
                    return;
                }
                outList.add(Integer.toString(adventurer.beAttacked(damage, logger)));
            });
            System.out.println(String.join(" ", outList));
        }
    }

    private void errorFightLog() {
        System.out.println("Fight log error");
    }

    private void addLog(String date, LoggerBase logger) {
        if (!loggers.containsKey(date)) {
            loggers.put(date, new ArrayList<>());
        }
        loggers.get(date).add(logger);
    }

    private Adventurer getAdventurerByName(String name) {
        for (Adventurer adv : adventurers.values()) {
            if (adv.getName().equals(name)) {
                return adv;
            }
        }
        return null;  // Unreachable!
    }
}
