package de.esempe.rext.shared.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.common.base.Objects;

/**
 * Abstrakte Basisklasse für alle konkreten Entitäten.
 * 
 * @author Stefan M. Prechtl (www.esempe.de)
 *
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable
{
	protected static final long serialVersionUID = 1L;

	// ### Konstanten für Spaltenbezeichnungen ###
	protected final static String ColumnObjId = "objid";

	// ### Attribute ###
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	@Convert(converter = de.esempe.rext.shared.domain.UuidConverter.class)
	protected UUID objid;

	protected AbstractEntity()
	{
		this.id = -1;
	}

	// ### Getter-Methoden ###
	public UUID getObjId()
	{
		return this.objid;
	}

	public long getId()
	{
		return this.id;
	}

	public void setId(final long id)
	{
		this.id = id;
	}

	public abstract Key getKey();

	// ### Allgemeine Object-Methoden ###

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
		final AbstractEntity other = (AbstractEntity) obj;
		return Objects.equal(this.getId(), other.getId()) && Objects.equal(this.getObjId(), other.getObjId())
				&& Objects.equal(this.getKey().value, other.getKey().value);
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(this.getId(), this.getObjId(), this.getKey().value);
	}

	@Override
	// Jede Subklasse muss toString() implementieren!
	public String toString()
	{
		throw new UnsupportedOperationException("Abgeleitete Klasse hat keine Implementierung von 'toString()'");
	}

}
