package com.lsc.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static com.alibaba.fastjson.JSON.DEFAULT_PARSER_FEATURE;

/**
 * @description:工具类
 * @author: yuchao
 * @date: 2021/11/1 14:59
 */
public class CommonUtil {
    private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);
    /**
     *
     */
    private static final String DATE_FORMAT = "yyyyMMdd";

    /**
     *
     */
    public static final String BIRTHDAY_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * data format
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * PROCESS_TIME_FORMAT
     */
    public static final String PROCESS_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * PROCESS_TIME_FORMAT
     */
    public static final String TASK_PROCESS_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * TASK_PROCESS_YEAR
     */
    public static final String TASK_PROCESS_YEAR = "2000-01-01";

    /**
     * legacy facade package prefix
     */
    public final static String LEGACY_PACKAGE_PREFIX = "com.ipay.ibizecoprod.common.service.legacy.facade";

    /**
     * password format
     */
    private static final String PASSWORD_FORMAT = "^.*(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$";

    /**
     * email format
     */
    private static String EMAIL_FORMAT = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    /**
     * phone number format
     **/
    private static String PHONE_NUMBER_FORMAT = "^[0-9]*";

    /**
     * phone country code format
     **/
    private static String PHONE_COUNTRY_CODE_FORMAT = "^[0-9]*";

    /**
     * PARSER_CONFIG
     * fastJson ParserConfig need to be used in static or maybe result in OOM.
     * Refer:{http://jarvis.alipay.net/xdoc/module_config
     * .htm?docSpaceId=20181031110901000589473110&docModuleId=20181031110910009280657955#20190626110912000718946275}
     */
    private static ParserConfig PARSER_CONFIG = new ParserConfig();

    static {
        PARSER_CONFIG.setSafeMode(true);//set safe mode
    }


    /**
     * @param pattern
     * @return
     */
    public static DateFormat getNewDateFormat(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        df.setLenient(false);
        return df;
    }

    /**
     * Get date formatter with locale
     *
     * @param pattern
     * @return
     */
    public static DateFormat getNewDateFormat(String pattern, Locale locale) {
        DateFormat df = new SimpleDateFormat(pattern, locale);
        df.setLenient(false);
        return df;
    }

    /**
     * getTodayString
     *
     * @return
     */
    public static String getTodayString() {
        DateFormat dateFormat = getNewDateFormat(DATE_FORMAT);
        return getDateString(new Date(), dateFormat);
    }

    /**
     * getDateString
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static String getDateString(Date date, DateFormat dateFormat) {
        return date != null && dateFormat != null ? dateFormat.format(date) : null;
    }

    /**
     * getPartition
     *
     * @param date
     * @return
     */
    public static String getPartition(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }

    /**
     * @param str
     * @param size
     * @return
     */
    public static boolean lessThan(String str, int size) {
        if (StringUtils.isBlank(str)) {
            return true;
        }
        return str.length() <= size;
    }

    /**
     * @param str
     * @param size
     * @return
     */
    public static boolean moreThan(String str, int size) {
        if (StringUtils.isBlank(str)) {
            return true;
        }
        return str.length() >= size;
    }

    /**
     * @param str
     * @return
     */
    public static boolean passwordCheck(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        return str.matches(PASSWORD_FORMAT);
    }

    /**
     * checkBirthDay
     *
     * @param birthDay
     * @return
     */
    public static boolean checkBirthDay(String birthDay) {
        if (StringUtils.isBlank(birthDay)) {
            return false;
        }
        DateFormat dateFormat = getNewDateFormat(BIRTHDAY_DATE_FORMAT);
        try {
            dateFormat.parse(birthDay);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    /**
     * checkEmail
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        Pattern pat = Pattern.compile(EMAIL_FORMAT);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    /**
     * checkCountry
     *
     * @param country
     * @return
     */
    public static boolean checkCountry(String country) {
        // get ISO countries

        String[] countries = Locale.getISOCountries();
        for (int i = 0; i < countries.length; i++) {
            if (countries[i].equals(country)) {
                return true;
            }
        }
        return false;
    }


    /**
     * getCountryList
     *
     * @return
     */
    public static Map<String, String> getCountryList(Locale locale) {
        Map<String, String> countryMap = new HashMap<>();
        for (String region : Arrays.asList(Locale.getISOCountries())) {
            // create a new locale
            Locale localeValue = new Locale(locale.getLanguage(), region);
            countryMap.put(region, localeValue.getDisplayCountry());
        }
        return countryMap;
    }

    /**
     * @param localeString
     * @return
     */
    public static Locale getLocale(String localeString) {
        Locale[] ls = Locale.getAvailableLocales();
        Locale locale;
        for (Locale localeTmp : ls) {
            if (StringUtils.equals(localeTmp.toString(), localeString)) {
                locale = localeTmp;
                return locale;
            }
        }
        return new Locale("zh", "CN");
    }

    /**
     * checkTouTncppAgree
     *
     * @param touTncppAgree
     * @return
     */
    public static boolean checkTouTncppAgree(String touTncppAgree) {
        if (StringUtils.equals(touTncppAgree, "0") || StringUtils.equals(touTncppAgree, "1")) {
            return true;
        }
        return false;
    }


    /**
     * checkMarking
     *
     * @param marking
     * @return
     */
    public static boolean checkMarking(String marking) {
        if (StringUtils.equals(marking, "0") || StringUtils.equals(marking, "1")) {
            return true;
        }
        return false;
    }


    /**
     * getLocalFromString
     *
     * @param lang
     * @return
     */
    public static Locale getLocalFromString(String lang) {
        Locale defaultLocal = new Locale("zh", "CN");
        if (StringUtils.isEmpty(lang)) {
            return defaultLocal;
        } else {
            String[] arrays = lang.split("-");
            if (arrays.length == 2) {
                return new Locale(arrays[0], arrays[1]);
            }
        }
        return defaultLocal;
    }

    /**
     * printProcessTime
     *
     * @param porcessTime
     * @return
     */
    public static String printProcessTime(Date porcessTime) {
        if (porcessTime == null) {
            return "null";
        }
        return new SimpleDateFormat(PROCESS_TIME_FORMAT).format(porcessTime);
    }


    /**
     * passTaskProcessTime
     */
    public static Date passTaskProcessTime(String timeStr, String paramName, Logger logger) {
        DateFormat dateFormat = getNewDateFormat(TASK_PROCESS_TIME_FORMAT);
        try {
            return dateFormat.parse(TASK_PROCESS_YEAR + " " + timeStr);
        } catch (ParseException ex) {
            log.error("parse time error! paramName:{} ,timeStr:{}  ", paramName, timeStr, ex);
        }
        return null;
    }

    /**
     * checkTaskProcessTimeValidate
     *
     * @param loadStartTime
     * @param loadEndTime
     * @return
     */
    public static boolean checkTaskProcessTimeValidate(Logger logger, List<Long> minuteList, Date loadStartTime, Date loadEndTime) {

        String currentTimeStr = getNewDateFormat(TASK_PROCESS_TIME_FORMAT).format(Calendar.getInstance().getTime());
        currentTimeStr = TASK_PROCESS_YEAR + currentTimeStr.substring(10, currentTimeStr.length());
        try {
            Date currentTime = getNewDateFormat(TASK_PROCESS_TIME_FORMAT).parse(currentTimeStr);
            boolean checkResult = true;
            if (loadStartTime != null) {
                checkResult = loadStartTime.before(currentTime);
            }
            if (loadEndTime != null) {
                checkResult = checkResult && loadEndTime.after(currentTime);
            }
            long minute = currentTime.getTime() / 1000 / 60 % 60;
            return checkResult && minuteList.contains(minute);
        } catch (Exception ex) {
            log.warn("check task process time validate error! ", ex);
        }
        return false;
    }

    /**
     * parseObject
     *
     * @param text
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String text, TypeReference<T> type) {
        return JSON.parseObject(text, type.getType(), PARSER_CONFIG, DEFAULT_PARSER_FEATURE, null);
    }

    /**
     * parseObject
     *
     * @param text
     * @param type
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String text, TypeReference<T> type, T defaultValue) {
        if (StringUtils.isBlank(text)) {
            return defaultValue;
        }
        return JSON.parseObject(text, type.getType(), PARSER_CONFIG, DEFAULT_PARSER_FEATURE, null);
    }

    /**
     * parseObject
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz, PARSER_CONFIG, DEFAULT_PARSER_FEATURE, null);
    }

    /**
     * parseObject
     *
     * @param text
     * @param clazz
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String text, Class<T> clazz, T defaultValue) {
        if (StringUtils.isBlank(text)) {
            return defaultValue;
        }
        return JSON.parseObject(text, clazz, PARSER_CONFIG, DEFAULT_PARSER_FEATURE, null);
    }

    /**
     * @param clazz
     * @return
     */
    public static List<Field> extractFieldFromClassHierarchy(Class<?> clazz) {
        // field list
        List<Field> result = new ArrayList<Field>();
        // check class is null
        while (clazz != null) {
            //  filed is ;ist
            if (null != clazz.getDeclaredFields()) {
                CollectionUtils.addAll(result, Arrays.stream(clazz.getDeclaredFields()).filter(field -> {
                    return !Modifier.isStatic(field.getModifiers());
                }).toArray());
            }
            clazz = clazz.getSuperclass();
            if (!clazz.getName().startsWith("com.ipay.ibizecoprod")) {
                break;
            }
        }
        return result;
    }


    /**
     * getNameByFirstAndLastName
     *
     * @param firstName
     * @param lastName
     * @return
     */
    public static String getNameByFirstAndLastName(String firstName, String lastName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(StringUtils.isBlank(firstName) ? "" : firstName);
        stringBuilder.append(StringUtils.isBlank(lastName) ? "" : lastName);
        return stringBuilder.toString();
    }

    /**
     * @param dateStr
     * @return
     */
    public static String changeDateFormat(String dateStr) {
        if (StringUtils.isBlank(dateStr) || dateStr.length() != 8) {
            return "";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(dateStr.substring(0, 4)).append("-").append(dateStr.substring(4, 6)).append("-").append(
                    dateStr.substring(6, 8));
            return stringBuilder.toString();
        }
    }


    /**
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName) {
        if (!StringUtils.isBlank(fileName)) {
            String[] token = fileName.split("\\.");
            if (token.length == 2) {
                return token[1];
            }
        }
        return fileName;
    }


    /**
     * @param method
     * @return
     */
    public static boolean isLegacyFacade(Method method) {
        String className = method.getDeclaringClass().getName();
        return className.startsWith(LEGACY_PACKAGE_PREFIX);
    }


    public static String addOrUpdateFieldToExtendInfo(String oldExtendInfo, Map<String, Object> newValue) {
        Map<String, Object> extendInfo = CommonUtil.parseObject(oldExtendInfo, new TypeReference<HashMap<String, Object>>() {
        });
        if (extendInfo == null) {
            extendInfo = new HashMap<String, Object>();
        }
        if (null != newValue && newValue.size() > 0) {
            extendInfo.putAll(newValue);
        }
        return JSON.toJSONString(extendInfo);

    }

    public static Map<String, Object> addOrUpdateFieldToExtendMap(String oldExtendInfo, Map<String, Object> newValue) {
        Map<String, Object> extendInfo = CommonUtil.parseObject(oldExtendInfo, new TypeReference<HashMap<String, Object>>() {
        });
        if (extendInfo == null) {
            extendInfo = new HashMap<String, Object>();
        }
        if (null != newValue && newValue.size() > 0) {
            extendInfo.putAll(newValue);
        }
        return extendInfo;

    }


}
