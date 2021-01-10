package org.martin.inventory.utils;

public class UUIDUtils {

    private UUIDUtils() {}

    public static String Dashify(String nonDashedUUID) {
        return nonDashedUUID.replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                "$1-$2-$3-$4-$5");
    }
}
