package edu.hm.weidacher.softarch.shareit.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonSyntaxException;

import edu.hm.weidacher.softarch.shareit.data.dao.DiscDao;
import edu.hm.weidacher.softarch.shareit.data.model.Disc;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;
import edu.hm.weidacher.softarch.shareit.util.BarcodeUtil;

/**
 * Resource providing access to stored discs.
 * @author Simon Weidacher <weidache@hm.edu>
 */
@Path("/media/discs")
public class DiscResource extends AbstractResource {

    /**
     * DiscDao for data access.
     */
    private DiscDao discDao;

    /**
     * Ctor.
     */
    public DiscResource() {
        super();
	try {
	    this.discDao = ((DiscDao) getDatastore().getUpdatableDao(Disc.class));
	} catch (PersistenceException e) {
	    // can't happen
	    e.printStackTrace();
	}
    }

    /**
     * Returns all stored discs.
     * @return HTTP response
     * 		200 : returns a JSON array of all stored discs
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDiscs() {
        return Response.ok(getGson().toJson(discDao.getAll())).build();
    }

    /**
     * Returns a disc identified by the barcode.
     * @param barcode identifier
     * @return HTTP Response
     *		200 : ok. returns disc entity
     *		400 : barcode was invalid
     *		404 : no disc found for barcode
     */
    @GET
    @Path("/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("barcode") String barcode) {
        if (!BarcodeUtil.isValid(barcode)) {
            return error("Barcode invalid", Response.Status.BAD_REQUEST);
	}

	final Disc byBarcode = discDao.getByBarcode(barcode);

        if (byBarcode == null) {
            return error(Response.Status.NOT_FOUND);
	}

	return Response.ok(getGson().toJson(byBarcode)).build();
    }

    /**
     * Creates a new Disc.
     *
     * @param json model of the disc
     * @return HTTP Response
     * 		201 : disc created successfully. returns URI to the entity
     * 		400 : model contained errors
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDisc(String json) {
        try {
	    final Disc disc = getGson().fromJson(json, Disc.class);
	    discDao.store(disc);

	    return buildCreatedResponse(disc.getBarcode());
	} catch (PersistenceException e) {
	    return error(e);
	} catch (JsonSyntaxException e) {
            return error("Bad disc model given", Response.Status.BAD_REQUEST);
	}
    }

    /**
     * Updates a disc identified by its barcode.
     * @param json json model of a disc
     * @param barcode barcode identifying the model to update
     * @return HTTP Response
     * 		200 : updated. returns URI to updated entity
     * 		400 : bad model
     * 		404 : no entity found
     */
    @PUT
    @Path("/{barcode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDisc(@PathParam("barcode") String barcode, String json) {
        if (!BarcodeUtil.isValid(barcode)) {
            return error("Barcode invalid", Response.Status.BAD_REQUEST);
	}

	try {
	    final Disc disc = getGson().fromJson(json, Disc.class);
	    final Disc persisted = discDao.getByBarcode(barcode);

	    if (persisted == null) {
	        return error(Response.Status.NOT_FOUND);
	    }

	    discDao.update(disc, persisted.getId());

	    return okWithUri(getAbsolutePath(disc.getBarcode()));

	} catch (PersistenceException e) {
            return error(e);
	} catch (JsonSyntaxException e) {
            return error("Bad disc model", Response.Status.BAD_REQUEST);
	}

    }

}
