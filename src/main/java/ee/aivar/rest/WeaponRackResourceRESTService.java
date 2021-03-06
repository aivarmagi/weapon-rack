package ee.aivar.rest;

import ee.aivar.data.WeaponRackRepository;
import ee.aivar.model.WeaponRack;
import ee.aivar.service.WeaponRackRegistration;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Logger;

@Path("/weaponracks")
@RequestScoped
public class WeaponRackResourceRESTService {

    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private WeaponRackRepository repository;

    @Inject
	WeaponRackRegistration registration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeaponRack> listAllWeaponRacks() {
        return repository.findAllOrderedByName();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WeaponRack lookupWeaponRackById(@PathParam("id") String id) {
        WeaponRack weaponRack = repository.findById(id);
        if (weaponRack == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return weaponRack;
    }

    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createWeaponRack(WeaponRack weaponRack) {

        Response.ResponseBuilder builder = null;

        try {
            validateWeaponRack(weaponRack);

            registration.register(weaponRack);

            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("id", "ID taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    private void validateWeaponRack(WeaponRack weaponRack) throws ConstraintViolationException, ValidationException {
        Set<ConstraintViolation<WeaponRack>> violations = validator.validate(weaponRack);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        if (idAlreadyExists(weaponRack.getId())) {
            throw new ValidationException("Unique WeaponRack ID Violation");
        }
    }

    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    public boolean idAlreadyExists(String id) {
        WeaponRack weaponRack = null;
        try {
            weaponRack = repository.findById(id);
        } catch (NoResultException e) {
            // ignore
        }
        return weaponRack != null;
    }
}
