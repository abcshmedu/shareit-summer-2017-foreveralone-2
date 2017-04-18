package edu.hm.weidacher.softarch.shareit.data.model;

import java.util.Date;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public abstract class AbstractUpdatableModel extends AbstractModel {

    private Date lastUpdate;

    public AbstractUpdatableModel() {
        super();
        update();
    }

    public Date getLastUpdate() {
	return lastUpdate;
    }

    public void update() {
        setLastUpdate(new Date());
    }

    public void setLastUpdate(Date lastUpdate) {
	this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
	return super.toString() + " lastUpdate=" + lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o) return true;
	if (!(o instanceof AbstractUpdatableModel)) return false;
	if (!super.equals(o)) return false;

	AbstractUpdatableModel that = (AbstractUpdatableModel) o;

	return getLastUpdate() != null ? getLastUpdate().equals(that.getLastUpdate()) : that.getLastUpdate() == null;
    }

    @Override
    public int hashCode() {
	int result = super.hashCode();
	result = 31 * result + (getLastUpdate() != null ? getLastUpdate().hashCode() : 0);
	return result;
    }
}
