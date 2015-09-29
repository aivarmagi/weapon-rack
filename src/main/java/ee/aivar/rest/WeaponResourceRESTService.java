package ee.aivar.rest;

import ee.aivar.data.WeaponRepository;
import ee.aivar.model.Weapon;
import ee.aivar.service.WeaponRegistration;

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

@Path("/weapons")
@RequestScoped
public class WeaponResourceRESTService {

    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private WeaponRepository repository;

    @Inject
	WeaponRegistration registration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Weapon> listAllWeapons() {
        return repository.findAllOrderedByName();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Weapon lookupWeaponById(@PathParam("id") String id) {
        Weapon weapon = repository.findById(id);
        if (weapon == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return weapon;
    }

    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createWeapon(Weapon weapon) {

        Response.ResponseBuilder builder = null;

        try {
            validateWeapon(weapon);

            registration.register(weapon);

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

    private void validateWeapon(Weapon weapon) throws ConstraintViolationException, ValidationException {
        Set<ConstraintViolation<Weapon>> violations = validator.validate(weapon);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        if (idAlreadyExists(weapon.getId())) {
            throw new ValidationException("Unique Weapon ID Violation");
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
        Weapon weapon = null;
        try {
            weapon = repository.findById(id);
        } catch (NoResultException e) {
            // ignore
        }
        return weapon != null;
    }
}
