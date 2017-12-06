package org.projektmanagement.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class HausSonderwunsch {

	@Embeddable
	public static class HausSonderwunschId implements Serializable {
		@Column(name = "fk_haus")
		protected Long hausId;

		@Column(name = "fk_sonderwunsch")
		protected Long sonderwunschId;

		public HausSonderwunschId() {

		}

		public HausSonderwunschId(long hausId, long sonderunwschId) {
			this.sonderwunschId = sonderunwschId;
			this.hausId = hausId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((hausId == null) ? 0 : hausId.hashCode());
			result = prime * result + ((sonderwunschId == null) ? 0 : sonderwunschId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;

			HausSonderwunschId other = (HausSonderwunschId) obj;

			if (hausId == null) {
				if (other.hausId != null)
					return false;
			} else if (!hausId.equals(other.hausId))
				return false;

			if (sonderwunschId == null) {
				if (other.sonderwunschId != null)
					return false;
			} else if (!sonderwunschId.equals(other.sonderwunschId))
				return false;

			return true;
		}
	}

	@EmbeddedId
	private HausSonderwunschId id;

	private int menge;

	@ManyToOne
	@JoinColumn(name = "fk_haus", insertable = false, updatable = false)
	private Haus haus;

	public Haus getHaus() {
		return haus;
	}

	public void setHaus(Haus haus) {
		this.haus = haus;
	}

	@ManyToOne
	@JoinColumn(name = "fk_sonderwunsch", insertable = false, updatable = false)
	private Sonderwunsch sonderwunsch;

	public Sonderwunsch getSonderwunsch() {
		return sonderwunsch;
	}

	public void setSonderwunsch(Sonderwunsch sonderwunsch) {
		this.sonderwunsch = sonderwunsch;
	}

	public HausSonderwunsch(Haus h, Sonderwunsch s, int m) {
		this.id = new HausSonderwunschId(h.getId(), s.getId());

		// initialize attributes
		this.haus = h;
		this.sonderwunsch = s;
		this.menge = m;

		// update relationships to assure referential integrity
		h.getHausSonderwunsch().add(this);
		s.getHausSonderwunsch().add(this);
	}

	public HausSonderwunsch() {

	}

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

}
