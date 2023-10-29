import java.util.ArrayList;
import java.util.Arrays;
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
    private final LinkedHashMap<String, Adventurer> adventurersInFight;
    private final HashMap<Integer, Adventurer> adventurers;
    private final HashMap<String, ArrayList<LoggerBase>> loggers;
    private final List<BiConsumer<Adventurer, InputWrapper>> fn;

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
                /* 17 */ this::queryLogByAttackee,
                /* 18 */ this::hireAdventurer,
                /* 19 */ this::countCommodity,
                /* 20 */ this::maxCommodity,
                /* 21 */ this::queryCommodityById
        );
    }

    public HashMap<Integer, Adventurer> getAdventurers() {
        return adventurers;
    }

    public int update(ArrayList<String> input) {
        InputWrapper wrapper = new InputWrapper(input);
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
        Bottle bottle;

        int botId = wrapper.getInt(Indexes.OBJ_ID);
        String botName = wrapper.get(Indexes.OBJ_NAME);
        int botCapacity = wrapper.getInt(Indexes.OBJ_ATTR);
        long botPrice = wrapper.getLong(Indexes.OBJ_PRICE);
        String type = wrapper.get(Indexes.OBJ_TYPE);

        if (type.equals("ReinforcedBottle")) {
            double botRatio = wrapper.getDouble(Indexes.OBJ_OTHER);
            bottle = new BottleReinforced(botId, botName, botCapacity, botPrice, botRatio);
        } else if (type.equals("RecoverBottle")) {
            double botRatio = wrapper.getDouble(Indexes.OBJ_OTHER);
            bottle = new BottleRecover(botId, botName, botCapacity, botPrice, botRatio);
        } else {
            bottle = new BottleRegular(botId, botName, botCapacity, botPrice);
        }

        adventurer.obtainBottle(botId, bottle);
    }

    private void dropBottle(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.dropBottle(wrapper.getInt(Indexes.OBJ_ID));
    }

    private void obtainEquipment(Adventurer adventurer, InputWrapper wrapper) {
        Equipment equipment;

        int equId = wrapper.getInt(Indexes.OBJ_ID);
        String equName = wrapper.get(Indexes.OBJ_NAME);
        int equCapacity = wrapper.getInt(Indexes.OBJ_ATTR);
        long equPrice = wrapper.getLong(Indexes.OBJ_PRICE);
        String type = wrapper.get(Indexes.OBJ_TYPE);

        if (type.equals("CritEquipment")) {
            int critical = wrapper.getInt(Indexes.OBJ_OTHER);
            equipment = new EquipmentCrit(equId, equName, equCapacity, equPrice, critical);
        } else if (type.equals("EpicEquipment")) {
            double ratio = wrapper.getDouble(Indexes.OBJ_OTHER);
            equipment = new EquipmentEpic(equId, equName, equCapacity, equPrice, ratio);
        } else {
            equipment = new EquipmentRegular(equId, equName, equCapacity, equPrice);
        }

        adventurer.obtainEquipment(wrapper.getInt(Indexes.OBJ_ID), equipment);
    }

    private void dropEquipment(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.dropEquipment(wrapper.getInt(Indexes.OBJ_ID), true);
    }

    private void enhanceEquipment(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.enhanceEquipment(wrapper.getInt(Indexes.OBJ_ID));
    }

    private void obtainFood(Adventurer adventurer, InputWrapper wrapper) {
        adventurer.obtainFood(
            wrapper.getInt(Indexes.OBJ_ID),
            new Food(
                wrapper.getInt(Indexes.OBJ_ID),
                wrapper.get(Indexes.OBJ_NAME),
                wrapper.getInt(Indexes.OBJ_ATTR),
                wrapper.getLong(Indexes.OBJ_PRICE)
            )
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
        System.out.println("Enter Fight Mode");
        wrapper.subList(Indexes.LOG_NAME_BEGIN).forEach(
            s -> {
                Adventurer adventurer = getAdventurerByName(s);
                if (adventurer == null) {
                    return;
                }
                adventurer.enterFightMode();
                adventurersInFight.put(s, adventurer);
            }
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

    private void hireAdventurer(Adventurer adventurer, InputWrapper wrapper) {
        Adventurer employee = adventurers.get(wrapper.getInt(Indexes.ADV_2_ID));
        adventurer.hireAdventurer(employee);
    }

    private void countCommodity(Adventurer adventurer, InputWrapper ignored) {
        long sumCommodity = adventurer.getCommodity();
        int countCommodity = adventurer.countCommodity();
        System.out.println(countCommodity + " " + sumCommodity);
    }

    private void maxCommodity(Adventurer adventurer, InputWrapper ignored) {
        System.out.println(adventurer.maxCommodity());
    }

    private void queryCommodityById(Adventurer adventurer, InputWrapper wrapper) {
        int comId = wrapper.getInt(Indexes.COM_ID);
        String belonging = adventurer.queryCommodityBelongingById(comId);
        System.out.println("Commodity whose id is " + comId + " belongs to " + belonging);
    }

    public void clearFightMode() {
        if (adventurersInFight.isEmpty()) {
            return;
        }
        adventurersInFight.forEach((s, adventurer) -> adventurer.leaveFightMode());
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
            int damage = attacker.useEquipmentInFight(equName, attackee.getPower());
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
            LoggerAoeAttack logger =
                    new LoggerAoeAttack(date, attacker.getName(), equName);
            addLog(date, logger);
            attacker.doAttack(logger);
            ArrayList<String> outList = new ArrayList<>();
            adventurersInFight.forEach((ignored, adventurer) -> {
                if (adventurer == attacker) {
                    return;
                }
                outList.add(Integer.toString(adventurer.beAttacked(
                        attacker.useEquipmentInFight(equName, adventurer.getPower()),
                        logger
                )));
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
