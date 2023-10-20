import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameManager {
    private static final Pattern patBot = Pattern.compile("(\\d{4}/\\d{2})-([^-@#]*)-([^-@#]*)");
    private static final Pattern patAtk = Pattern.compile(
            "(\\d{4}/\\d{2})-([^-@#]*)@([^-@#]*)-([^-@#]*)"
    );
    private static final Pattern patAoe = Pattern.compile("(\\d{4}/\\d{2})-([^-@#]*)@#-([^-@#]*)");
    private LinkedHashMap<String, Adventurer> adventurersInFight;
    private HashMap<Integer, Adventurer> adventurers;
    private HashMap<String, ArrayList<LoggerBase>> loggers;
    private List<BiConsumer<Adventurer, InputWrapper>> fn;

    public GameManager() {
        adventurers = new HashMap<>();
        loggers = new HashMap<>();
        adventurersInFight = new LinkedHashMap<>();
        fn = Arrays.asList(
                /* 0 */ null,
                /* 1 */ this::addAdventurer,
                /* 2 */ this::obtainBottle,
                /* 3 */ this::dropBottle,
                /* 4 */ this::obtainEquipment,
                /* 5 */ this::dropEquipment,
                /* 6 */ this::enhanceEquipment,
                /* 7 */ this::obtainFood,
                /* 8 */ this::dropFood,
                /* 9 */ this::fetchEquipment,
                /* 10 */ this::fetchBottle,
                /* 11 */ this::fetchFood,
                /* 12 */ this::useBottle,
                /* 13 */ this::useFood,
                /* 14 */ this::enterFightMode,
                /* 15 */ this::queryLogByDate,
                /* 16 */ this::queryLogByAttacker,
                /* 17 */ this::queryLogByAttackee
        );
    }

    public HashMap<Integer, Adventurer> getAdventurers() {
        return adventurers;
    }

    public int update(ArrayList<String> input) {
        InputWrapper wrapper =  new InputWrapper(input);
        int type = wrapper.getInt(Indexes.TYPE);

        // Only ADD_LOG(14) & QUERY_LOG_DATE(15) have no adventurer
        Adventurer adventurer = null;
        if (type != Indexes.TYPE_ADD_LOG && type != Indexes.TYPE_QUERY_LOG_DATE) {
            adventurer = adventurers.get(Integer.parseInt(input.get(Indexes.ADV_ID)));
        }

        // Do actions by type
        fn.get(type).accept(adventurer, wrapper);

        // Return values to read logs
        if (type == Indexes.TYPE_ADD_LOG) {
            return wrapper.getInt(Indexes.LOG_K);
        } else {
            return 0;
        }
    }

    private void addAdventurer(Adventurer ignored, InputWrapper wrapper) {
        int advID = wrapper.getInt(Indexes.ADV_ID);
        String advName = wrapper.get(Indexes.ADV_NAME);
        adventurers.put(advID, new Adventurer(advID, advName));
    }

    private void obtainBottle(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.obtainBottle(
                wrapper.getInt(Indexes.OBJ_ID),
                wrapper.get(Indexes.OBJ_NAME),
                wrapper.getInt(Indexes.OBJ_ATTR)
        );
    }

    private void dropBottle(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.dropBottle(wrapper.getInt(Indexes.OBJ_ID));
    }

    private void obtainEquipment(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.obtainEquipment(
                wrapper.getInt(Indexes.OBJ_ID),
                wrapper.get(Indexes.OBJ_NAME),
                wrapper.getInt(Indexes.OBJ_ATTR)
        );
    }

    private void dropEquipment(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.dropEquipment(wrapper.getInt(Indexes.OBJ_ID));
    }

    private void enhanceEquipment(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.enhanceEquipment(wrapper.getInt(Indexes.OBJ_ID));
    }

    private void obtainFood(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.obtainFood(
                wrapper.getInt(Indexes.OBJ_ID),
                wrapper.get(Indexes.OBJ_NAME),
                wrapper.getInt(Indexes.OBJ_ATTR)
        );
    }

    private void dropFood(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.dropFood(wrapper.getInt(Indexes.OBJ_ID));
    }

    private void fetchEquipment(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.fetchEquipment(wrapper.getInt(Indexes.OBJ_ID));
    }

    private void fetchBottle(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.fetchBottle(wrapper.getInt(Indexes.OBJ_ID));
    }

    private void fetchFood(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.fetchFood(wrapper.getInt(Indexes.OBJ_ID));
    }

    private void useBottle(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.useBottle(wrapper.get(Indexes.USE_OBJ_NAME));
    }

    private void useFood(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.useFood(wrapper.get(Indexes.USE_OBJ_NAME));
    }

    private void enterFightMode(Adventurer ignored, InputWrapper wrapper) {
        wrapper.subList(Indexes.LOG_NAME_BEGIN).forEach(
            s -> adventurersInFight.put(s, getAdventurerByName(s))
        );
    }

    private void queryLogByDate(Adventurer ignored, InputWrapper wrapper) {
        String date = wrapper.get(Indexes.QUERY_DATE);
        if (!loggers.containsKey(date) || loggers.get(date).isEmpty()) {
            System.out.println("No Matched Log");
            return;
        }
        loggers.get(date).forEach(System.out::println);
    }

    private void queryLogByAttacker(Adventurer adventurer, InputWrapper ignored) {
        adventurer.queryLoggerAttacker();
    }

    private void queryLogByAttackee(Adventurer adventurer, InputWrapper ignored) {
        adventurer.queryLoggerAttackee();
    }

    public void clearFightMode() {
        adventurersInFight.clear();
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
            if (attacker == null || attackee == null || !attacker.checkEquipment(equName)) {
                errorFightLog();
                return;
            }
            int damage = attacker.useEquipmentInFight(equName);
            LoggerNormalAttack logger =
                    new LoggerNormalAttack(date, attacker.getName(), equName, attackee.getName());
            addLog(date, logger);
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
            addLog(date, logger);
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
