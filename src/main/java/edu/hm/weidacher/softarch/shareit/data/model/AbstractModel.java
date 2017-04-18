package edu.hm.weidacher.softarch.shareit.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public abstract class AbstractModel implements Serializable{

    private final UUID id;

    private final Date creationDate;

    public AbstractModel() {
        id = UUID.randomUUID();
        creationDate = new Date();
    }

    public UUID getId() {
	return id;
    }


    public Date getCreationDate() {
	return creationDate;
    }

    @Override
    public String toString() {
	return "id=" + id +
	    ", creationDate=" + creationDate;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o) return true;
	if (!(o instanceof AbstractModel)) return false;

	AbstractModel that = (AbstractModel) o;

	if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
	return getCreationDate() != null ? getCreationDate().equals(that.getCreationDate()) : that.getCreationDate() == null;
    }

    @Override
    public int hashCode() {
	int result = getId() != null ? getId().hashCode() : 0;
	result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
	return result;
    }
}
