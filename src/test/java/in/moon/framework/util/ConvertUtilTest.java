package in.moon.framework.util;

import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Created by miaoever on 9/26/15.
 */
public class ConvertUtilTest extends TestCase {

    public void testConvert() throws Exception {
        Long val1 = 1024L;
        Assert.assertEquals("1024", ConvertUtil.convert(val1).toStr());
        Assert.assertEquals("0000", ConvertUtil.convert(null).toStrByDeault("0000"));

        String val2 = "1024";
        Assert.assertEquals(1024L, ConvertUtil.convert(val2).toLong());
        Assert.assertEquals(1024L, ConvertUtil.convert(null).toLongByDefault(1024L));

        String val3 = "10.24";
        Assert.assertEquals(10.24D, ConvertUtil.convert(val3).toDouble(), 0.001);
        Assert.assertEquals(10.24D, ConvertUtil.convert(null).toDoubleByDefault(10.24D), 0.001);
    }
}