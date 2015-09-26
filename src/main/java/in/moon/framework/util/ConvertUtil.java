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

        public String toStrByDeault(String val) {
            return obj != null ? String.valueOf(obj) : val;
        }

        public String toStr() {
            return toStrByDeault("");
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

        public double toDouble() {
            return toDoubleByDefault(0);
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
    }
}
