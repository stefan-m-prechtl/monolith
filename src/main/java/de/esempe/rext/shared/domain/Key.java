package de.esempe.rext.shared.domain;

import com.google.common.base.Objects;

public class Key
{
	public final String name;
	public final String value;

	public static final Key EMPTY = new Key("EmptyKey", "EmptyValue");

	public Key(final String name, final String value)
	{
		this.name = name;
		this.value = value;
	}

	@Override
	public boolean equals(final Object obj)
	{

		if (obj == null)
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		final Key other = (Key) obj;
		return Objects.equal(this.name, other.name) && Objects.equal(this.value, other.value);

	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(this.name, this.value);
	}
}
