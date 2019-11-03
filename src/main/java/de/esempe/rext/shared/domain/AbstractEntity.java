package de.esempe.rext.shared.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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

	// ### Allgemeine Object-Methoden ###

	@Override
	// Jede Subklasse muss hashCode() implementieren!
	public int hashCode()
	{
		throw new UnsupportedOperationException("Abgeleitete Klasse hat keine Implementierung von 'hashCode()'");
	}

	@Override
	// Jede Subklasse muss equals() implementieren!
	public boolean equals(final Object obj)
	{
		throw new UnsupportedOperationException("Abgeleitete Klasse hat keine Implementierung von 'equals()'");
	}

	@Override
	// Jede Subklasse muss toString() implementieren!
	public String toString()
	{
		throw new UnsupportedOperationException("Abgeleitete Klasse hat keine Implementierung von 'toString()'");
	}

}
