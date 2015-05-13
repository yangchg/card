package com.owl.card.common.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Random;

public class BytesTools {
	static Random m_Rand = new Random();

	public static int readInt(DataInputStream br) throws IOException {
		byte[] buf = new byte[4];
		br.read(buf, 0, 4);

		if ((buf[0] == 13) && (buf[1] == 10)) {
			br.read();
			return 10;
		}

		int d1 = buf[3];
		d1 = d1 >= 0 ? d1 : 256 + d1;
		d1 <<= 24;

		int d2 = buf[2];
		d2 = d2 >= 0 ? d2 : 256 + d2;
		d2 <<= 16;

		int d3 = buf[1];
		d3 = d3 >= 0 ? d3 : 256 + d3;
		d3 <<= 8;

		int d4 = buf[0];
		d4 = d4 >= 0 ? d4 : 256 + d4;

		return d1 + d2 + d3 + d4;
	}

	public static String readString(DataInputStream br, int len) throws IOException {
		byte[] buf = new byte[len];
		br.read(buf, 0, len);

		return new String(buf);
	}

	public static byte[] intToByte(Integer value) {
		byte[] buf = new byte[4];

		buf[0] = (byte) (value.intValue() & 0xFF);
		buf[1] = (byte) ((value.intValue() & 0xFF00) >> 8);
		buf[2] = (byte) ((value.intValue() & 0xFF0000) >> 16);
		buf[3] = (byte) ((value.intValue() & 0xFF000000) >> 24);
		return buf;
	}

	public static void shortToByte(byte[] buf, int index, short value) {
		buf[index] = (byte) ((value & 0xFF00) >> 8);
		buf[(index + 1)] = (byte) (value & 0xFF);
	}

	public static void intToByte(byte[] buf, int index, Integer value) {
		buf[index] = (byte) ((value.intValue() & 0xFF000000) >> 24);
		buf[(index + 1)] = (byte) ((value.intValue() & 0xFF0000) >> 16);
		buf[(index + 2)] = (byte) ((value.intValue() & 0xFF00) >> 8);
		buf[(index + 3)] = (byte) (value.intValue() & 0xFF);
	}

	public static void floatToByte(byte[] buf, int index, Float value) {
		int ivalue = Float.floatToIntBits(value.floatValue());
		buf[index] = (byte) (ivalue & 0xFF);
		buf[(index + 1)] = (byte) ((ivalue & 0xFF00) >> 8);
		buf[(index + 2)] = (byte) ((ivalue & 0xFF0000) >> 16);
		buf[(index + 3)] = (byte) ((ivalue & 0xFF000000) >> 24);
	}

	public static void longToByte(byte[] buf, int index, Long value) {
		int int1 = (int) (value.longValue() & 0x7FFFFFFF);
		int int2 = (int) (value.longValue() - int1 >> 31);

		intToByte(buf, index, Integer.valueOf(int2));
		intToByte(buf, index + 4, Integer.valueOf(int1));
	}

	public static long intToLong(Long int1, Long int2) {
		long a = int1.longValue() << 31;
		a |= int2.longValue();
		return a;
	}

	public static int randInt(int begin, int end) {
		return begin + m_Rand.nextInt(end - begin + 1);
	}

	public static byte[] encodeBuf(byte[] data, int key) {
		byte[] keybuf = intToByte(Integer.valueOf(key));
		int len = data.length;

		int j = 0;
		for (int i = 0; i < len; i++) {
			int tmp26_24 = i;
			data[tmp26_24] = (byte) (data[tmp26_24] ^ keybuf[(j++)]);
			if (j != 4)
				continue;
			j = 0;
		}

		return data;
	}

	public static byte[] decodeBuf(byte[] data, int key) {
		byte[] keybuf = intToByte(Integer.valueOf(key));
		int len = data.length;

		int j = 0;
		for (int i = 0; i < len; i++) {
			int tmp26_24 = i;
			data[tmp26_24] = (byte) (data[tmp26_24] ^ keybuf[(j++)]);
			if (j != 4)
				continue;
			j = 0;
		}

		return data;
	}

	public static short readShort(byte[] buf, int index) {
		short d1 = buf[index + 1];
		d1 = (short) (d1 >= 0 ? d1 : 256 + d1);

		short d2 = buf[index];
		d2 = (short) (d2 >= 0 ? d2 : 256 + d2);
		d2 <<= 8;

		return (short) (d1 + d2);
	}

	public static int readInt(byte[] buf, int index) {
		int d1 = buf[(index + 3)];
		d1 = d1 >= 0 ? d1 : 256 + d1;

		int d2 = buf[(index + 2)];
		d2 = d2 >= 0 ? d2 : 256 + d2;
		d2 <<= 8;

		int d3 = buf[(index + 1)];
		d3 = d3 >= 0 ? d3 : 256 + d3;
		d3 <<= 16;

		int d4 = buf[(index + 0)];
		d4 = d4 >= 0 ? d4 : 256 + d4;
		d4 <<= 24;

		return d1 + d2 + d3 + d4;
	}

	public static float readFloat(byte[] buf, int index) {
		int ivalue = readInt(buf, index);
		return Float.intBitsToFloat(ivalue);
	}

	public static long readLong(byte[] buf, int index) {
		long int1 = readInt(buf, index);
		long int2 = readInt(buf, index + 4);

		return intToLong(Long.valueOf(int1), Long.valueOf(int2));
	}

	public static String readString(byte[] buf, int index, int len) {
		byte[] s = new byte[len];
		int i = index;
		for (int j = 0; i < index + len; j++) {
			s[j] = buf[i];

			i++;
		}

		return new String(s);
	}

	public static byte[] readBytes(byte[] buf, int index, int len) {
		byte[] dest = new byte[len];
		System.arraycopy(buf, index, dest, 0, len);

		return dest;
	}
}
