package edu.hm.weidacher.softarch.shareit.data.model;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class Copy extends AbstractUpdatableModel{

    private Medium medium;

    private String owner;

    public Copy() {
	super();
	// beans
    }
    
    public Copy(Medium medium, String owner) {
        super();
	this.medium = medium;
	this.owner = owner;
    }

    public Medium getMedium() {
	return medium;
    }

    public void setMedium(Medium medium) {
	this.medium = medium;
    }

    public String getOwner() {
	return owner;
    }

    public void setOwner(String owner) {
	this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o) return true;
	if (!(o instanceof Copy)) return false;
	if (!super.equals(o)) return false;

	Copy copy = (Copy) o;

	if (getMedium() != null ? !getMedium().equals(copy.getMedium()) : copy.getMedium() != null) return false;
	return getOwner() != null ? getOwner().equals(copy.getOwner()) : copy.getOwner() == null;
    }

    @Override
    public int hashCode() {
	int result = super.hashCode();
	result = 31 * result + (getMedium() != null ? getMedium().hashCode() : 0);
	result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
	return result;
    }
}
