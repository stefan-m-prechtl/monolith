package de.esempe.rext.shared.domain;

import java.nio.ByteBuffer;
import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA Konverter zu automatischen Umwandlung von ObjektID als Binary(16) der
 * Datenbank (MySQL) in UUID (Plain Java) und umgekehrt.
 */
@Converter(autoApply = false)
public class UuidConverter implements AttributeConverter<UUID, byte[]>
{
	@Override
	public byte[] convertToDatabaseColumn(final UUID uuid)
	{
		final ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits()); // order is important here!
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}

	@Override
	public UUID convertToEntityAttribute(final byte[] byteArray)
	{
		long msb = 0;
		long lsb = 0;
		for (int i = 0; i < 8; i++)
		{
			msb = (msb << 8) | (byteArray[i] & 0xff);
		}
		for (int i = 8; i < 16; i++)
		{
			lsb = (lsb << 8) | (byteArray[i] & 0xff);
		}
		final UUID result = new UUID(msb, lsb);
		return result;
	}

}
