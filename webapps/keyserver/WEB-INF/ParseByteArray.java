import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class ParseByteArray {
    public static byte[] fromChar(char value) {
        int arraySize = Character.SIZE / Byte.SIZE;
        ByteBuffer buffer = ByteBuffer.allocate(arraySize);
    	buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.putChar(value).array();
    }
    public static byte[] fromShort(short value) {
        int arraySize = Short.SIZE / Byte.SIZE;
        ByteBuffer buffer = ByteBuffer.allocate(arraySize);
    	buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.putShort(value).array();
    }
 
    public static byte[] fromInt(int value) {
        int arraySize = Integer.SIZE / Byte.SIZE;
        ByteBuffer buffer = ByteBuffer.allocate(arraySize);
    	buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.putInt(value).array();
    }
 
    public static byte[] fromLong(long value) {
        int arraySize = Long.SIZE / Byte.SIZE;
        ByteBuffer buffer = ByteBuffer.allocate(arraySize);
    	buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.putLong(value).array();
    }
 
    public static byte[] fromFloat(float value) {
        int arraySize = Float.SIZE / Byte.SIZE;
        ByteBuffer buffer = ByteBuffer.allocate(arraySize);
    	buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.putFloat(value).array();
    }
 
    public static byte[] fromDouble(double value) {
        int arraySize = Double.SIZE / Byte.SIZE;
        ByteBuffer buffer = ByteBuffer.allocate(arraySize);
    	buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.putDouble(value).array();
    }
}