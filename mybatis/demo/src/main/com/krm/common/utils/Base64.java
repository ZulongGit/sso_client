
package com.krm.common.utils;

import org.apache.shiro.codec.CodecSupport;

public class Base64 {
	static final int CHUNK_SIZE = 76;
	static final byte[] CHUNK_SEPARATOR;
	private static final int BASELENGTH = 255;
	private static final int LOOKUPLENGTH = 64;
	private static final int EIGHTBIT = 8;
	private static final int SIXTEENBIT = 16;
	private static final int TWENTYFOURBITGROUP = 24;
	private static final int FOURBYTE = 4;
	private static final int SIGN = -128;
	private static final byte PAD = 61;
	private static final byte[] base64Alphabet;
	private static final byte[] lookUpBase64Alphabet;

	private static boolean isBase64(final byte octect) {
		return octect == 61 || (octect >= 0 && Base64.base64Alphabet[octect] != -1);
	}

	public static boolean isBase64(byte[] arrayOctect) {
		arrayOctect = discardWhitespace(arrayOctect);
		final int length = arrayOctect.length;
		if (length == 0) {
			return true;
		}
		for (int i = 0; i < length; ++i) {
			if (!isBase64(arrayOctect[i])) {
				return false;
			}
		}
		return true;
	}

	static byte[] discardWhitespace(final byte[] data) {
		final byte[] groomedData = new byte[data.length];
		int bytesCopied = 0;
		for (final byte aByte : data) {
			switch (aByte) {
			case 9:
			case 10:
			case 13:
			case 32: {
				break;
			}
			default: {
				groomedData[bytesCopied++] = aByte;
				break;
			}
			}
		}
		final byte[] packedData = new byte[bytesCopied];
		System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
		return packedData;
	}

	public static String encodeToString(final byte[] bytes) {
		final byte[] encoded = encode(bytes);
		return CodecSupport.toString(encoded);
	}

	public static byte[] encodeChunked(final byte[] binaryData) {
		return encode(binaryData, true);
	}

	public static byte[] encode(final byte[] pArray) {
		return encode(pArray, false);
	}

	public static byte[] encode(final byte[] binaryData, final boolean isChunked) {
		final long binaryDataLength = binaryData.length;
		final long lengthDataBits = binaryDataLength * 8L;
		final long fewerThan24bits = lengthDataBits % 24L;
		final long tripletCount = lengthDataBits / 24L;
		int chunckCount = 0;
		long encodedDataLengthLong;
		if (fewerThan24bits != 0L) {
			encodedDataLengthLong = (tripletCount + 1L) * 4L;
		} else {
			encodedDataLengthLong = tripletCount * 4L;
		}
		if (isChunked) {
			chunckCount = ((Base64.CHUNK_SEPARATOR.length == 0) ? 0 : ((int) Math.ceil(encodedDataLengthLong / 76.0f)));
			encodedDataLengthLong += chunckCount * Base64.CHUNK_SEPARATOR.length;
		}
		if (encodedDataLengthLong > 2147483647L) {
			throw new IllegalArgumentException(
					"Input array too big, output array would be bigger than Integer.MAX_VALUE=2147483647");
		}
		final int encodedDataLength = (int) encodedDataLengthLong;
		final byte[] encodedData = new byte[encodedDataLength];
		int encodedIndex = 0;
		int nextSeparatorIndex = 76;
		int chunksSoFar = 0;
		int i;
		for (i = 0; i < tripletCount; ++i) {
			final int dataIndex = i * 3;
			final byte b1 = binaryData[dataIndex];
			final byte b2 = binaryData[dataIndex + 1];
			final byte b3 = binaryData[dataIndex + 2];
			final byte l = (byte) (b2 & 0xF);
			final byte k = (byte) (b1 & 0x3);
			final byte val1 = ((b1 & 0xFFFFFF80) == 0x0) ? ((byte) (b1 >> 2)) : ((byte) (b1 >> 2 ^ 0xC0));
			final byte val2 = ((b2 & 0xFFFFFF80) == 0x0) ? ((byte) (b2 >> 4)) : ((byte) (b2 >> 4 ^ 0xF0));
			final byte val3 = ((b3 & 0xFFFFFF80) == 0x0) ? ((byte) (b3 >> 6)) : ((byte) (b3 >> 6 ^ 0xFC));
			encodedData[encodedIndex] = Base64.lookUpBase64Alphabet[val1];
			encodedData[encodedIndex + 1] = Base64.lookUpBase64Alphabet[val2 | k << 4];
			encodedData[encodedIndex + 2] = Base64.lookUpBase64Alphabet[l << 2 | val3];
			encodedData[encodedIndex + 3] = Base64.lookUpBase64Alphabet[b3 & 0x3F];
			encodedIndex += 4;
			if (isChunked && encodedIndex == nextSeparatorIndex) {
				System.arraycopy(Base64.CHUNK_SEPARATOR, 0, encodedData, encodedIndex, Base64.CHUNK_SEPARATOR.length);
				++chunksSoFar;
				nextSeparatorIndex = 76 * (chunksSoFar + 1) + chunksSoFar * Base64.CHUNK_SEPARATOR.length;
				encodedIndex += Base64.CHUNK_SEPARATOR.length;
			}
		}
		final int dataIndex = i * 3;
		if (fewerThan24bits == 8L) {
			final byte b1 = binaryData[dataIndex];
			final byte k = (byte) (b1 & 0x3);
			final byte val1 = ((b1 & 0xFFFFFF80) == 0x0) ? ((byte) (b1 >> 2)) : ((byte) (b1 >> 2 ^ 0xC0));
			encodedData[encodedIndex] = Base64.lookUpBase64Alphabet[val1];
			encodedData[encodedIndex + 1] = Base64.lookUpBase64Alphabet[k << 4];
			encodedData[encodedIndex + 3] = (encodedData[encodedIndex + 2] = 61);
		} else if (fewerThan24bits == 16L) {
			final byte b1 = binaryData[dataIndex];
			final byte b2 = binaryData[dataIndex + 1];
			final byte l = (byte) (b2 & 0xF);
			final byte k = (byte) (b1 & 0x3);
			final byte val1 = ((b1 & 0xFFFFFF80) == 0x0) ? ((byte) (b1 >> 2)) : ((byte) (b1 >> 2 ^ 0xC0));
			final byte val2 = ((b2 & 0xFFFFFF80) == 0x0) ? ((byte) (b2 >> 4)) : ((byte) (b2 >> 4 ^ 0xF0));
			encodedData[encodedIndex] = Base64.lookUpBase64Alphabet[val1];
			encodedData[encodedIndex + 1] = Base64.lookUpBase64Alphabet[val2 | k << 4];
			encodedData[encodedIndex + 2] = Base64.lookUpBase64Alphabet[l << 2];
			encodedData[encodedIndex + 3] = 61;
		}
		if (isChunked && chunksSoFar < chunckCount) {
			System.arraycopy(Base64.CHUNK_SEPARATOR, 0, encodedData, encodedDataLength - Base64.CHUNK_SEPARATOR.length,
					Base64.CHUNK_SEPARATOR.length);
		}
		return encodedData;
	}

	public static String decodeToString(final String base64Encoded) {
		final byte[] encodedBytes = CodecSupport.toBytes(base64Encoded);
		return decodeToString(encodedBytes);
	}

	public static String decodeToString(final byte[] base64Encoded) {
		final byte[] decoded = decode(base64Encoded);
		return CodecSupport.toString(decoded);
	}

	public static byte[] decode(final String base64Encoded) {
		final byte[] bytes = CodecSupport.toBytes(base64Encoded);
		return decode(bytes);
	}

	public static byte[] decode(byte[] base64Data) {
		base64Data = discardNonBase64(base64Data);
		if (base64Data.length == 0) {
			return new byte[0];
		}
		final int numberQuadruple = base64Data.length / 4;
		int encodedIndex = 0;
		int lastData = base64Data.length;
		while (base64Data[lastData - 1] == 61) {
			if (--lastData == 0) {
				return new byte[0];
			}
		}
		final byte[] decodedData = new byte[lastData - numberQuadruple];
		for (int i = 0; i < numberQuadruple; ++i) {
			final int dataIndex = i * 4;
			final byte marker0 = base64Data[dataIndex + 2];
			final byte marker2 = base64Data[dataIndex + 3];
			final byte b1 = Base64.base64Alphabet[base64Data[dataIndex]];
			final byte b2 = Base64.base64Alphabet[base64Data[dataIndex + 1]];
			if (marker0 != 61 && marker2 != 61) {
				final byte b3 = Base64.base64Alphabet[marker0];
				final byte b4 = Base64.base64Alphabet[marker2];
				decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
				decodedData[encodedIndex + 1] = (byte) ((b2 & 0xF) << 4 | (b3 >> 2 & 0xF));
				decodedData[encodedIndex + 2] = (byte) (b3 << 6 | b4);
			} else if (marker0 == 61) {
				decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
			} else {
				final byte b3 = Base64.base64Alphabet[marker0];
				decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
				decodedData[encodedIndex + 1] = (byte) ((b2 & 0xF) << 4 | (b3 >> 2 & 0xF));
			}
			encodedIndex += 3;
		}
		return decodedData;
	}

	static byte[] discardNonBase64(final byte[] data) {
		final byte[] groomedData = new byte[data.length];
		int bytesCopied = 0;
		for (final byte aByte : data) {
			if (isBase64(aByte)) {
				groomedData[bytesCopied++] = aByte;
			}
		}
		final byte[] packedData = new byte[bytesCopied];
		System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
		return packedData;
	}

	static {
		CHUNK_SEPARATOR = "\r\n".getBytes();
		base64Alphabet = new byte[255];
		lookUpBase64Alphabet = new byte[64];
		for (int i = 0; i < 255; ++i) {
			Base64.base64Alphabet[i] = -1;
		}
		for (int i = 90; i >= 65; --i) {
			Base64.base64Alphabet[i] = (byte) (i - 65);
		}
		for (int i = 122; i >= 97; --i) {
			Base64.base64Alphabet[i] = (byte) (i - 97 + 26);
		}
		for (int i = 57; i >= 48; --i) {
			Base64.base64Alphabet[i] = (byte) (i - 48 + 52);
		}
		Base64.base64Alphabet[43] = 62;
		Base64.base64Alphabet[47] = 63;
		for (int i = 0; i <= 25; ++i) {
			Base64.lookUpBase64Alphabet[i] = (byte) (65 + i);
		}
		for (int i = 26, j = 0; i <= 51; ++i, ++j) {
			Base64.lookUpBase64Alphabet[i] = (byte) (97 + j);
		}
		for (int i = 52, j = 0; i <= 61; ++i, ++j) {
			Base64.lookUpBase64Alphabet[i] = (byte) (48 + j);
		}
		Base64.lookUpBase64Alphabet[62] = 43;
		Base64.lookUpBase64Alphabet[63] = 47;
	}
}