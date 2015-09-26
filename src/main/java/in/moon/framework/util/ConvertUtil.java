package in.moon.framework.util;

import  org.apache.commons.lang3.StringUtils;

/** Type Convert utility
 * Created by miaoever on 9/26/15.
 */
public class ConvertUtil {

    private ConvertUtil(){};
    private static Converter converter;
    public static Converter convert(Object obj) {
        converter = new Converter(obj);
        return converter;
    }

    public static class Converter {
        private Object obj;

        public Converter(Object obj) {
            this.obj = obj;
        }

        public String toStr() {
            return toStrByDeault("");
        }

        public String toStrByDeault(String val) {
            return obj != null ? String.valueOf(obj) : val;
        }

        public double toDouble() {
            return toDoubleByDefault(0);
        }

        public double toDoubleByDefault(double val) {
            double value = val;
            if (obj != null) {
                String strVal = toStr();
                if (StringUtils.isNotEmpty(strVal)) {
                    try {
                        value = Double.parseDouble(strVal);
                    } catch (NumberFormatException e) {
                        value = val;
                    }
                }
            }
            return value;
        }

        public long toLong() {
            return toLongByDefault(0);
        }

        public long toLongByDefault(long val) {
            long value = val;
            if (obj != null) {
                String strVal = toStr();
                if (StringUtils.isNotEmpty(strVal)) {
                    try {
                        value = Long.parseLong(strVal);
                    } catch (NumberFormatException e) {
                        value = val;
                    }
                }
            }
            return value;
        }

        public int toInt() {
            return toIntByDefault(0);
        }

        public int toIntByDefault(int val) {
            int value = val;
            if (obj != null) {
                String strVal = toStr();
                if (StringUtils.isNotEmpty(strVal)) {
                    try {
                        value = Integer.parseInt(strVal);
                    } catch (NumberFormatException e) {
                        value = val;
                    }
                }
            }
            return value;
        }

        public boolean toBool() {
            return toBoolByDefault(false);
        }

        public boolean toBoolByDefault(boolean val) {
            boolean value = val;
            if (obj != null) {
                value = Boolean.parseBoolean(toStr());
            }
            return value;
        }
    }
}
