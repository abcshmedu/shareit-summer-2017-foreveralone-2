package edu.hm.weidacher.softarch.shareit.data.model;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class Medium extends AbstractUpdatableModel{

    private String title;

    public Medium() {
        // beans
    }

    public Medium(String title) {
	this.title = title;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
        update();
	this.title = title;
    }

    @Override
    public String toString() {
	return "Medium{" + super.toString() +
	    " title='" + title + "'}";
    }

    @Override
    public boolean equals(Object o) {
	if (this == o) return true;
	if (!(o instanceof Medium)) return false;
	if (!super.equals(o)) return false;

	Medium medium = (Medium) o;

	return getTitle() != null ? getTitle().equals(medium.getTitle()) : medium.getTitle() == null;
    }

    @Override
    public int hashCode() {
	int result = super.hashCode();
	result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
	return result;
    }
}
