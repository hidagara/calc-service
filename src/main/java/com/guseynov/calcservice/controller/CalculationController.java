package com.guseynov.calcservice.controller;

import com.guseynov.calcservice.model.Calculation;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CalculationController {


    /**
     * Генерируем тег для идентификации кеша исходя из полученных параметров
     * @param params - список параметров
     * @return - Etag
     */
    private EntityTag generateTag(List<PathSegment> params) {
        return new EntityTag(String.valueOf(params.toString().hashCode()));
    }


    @GET
    @Path("/add/{params: .*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@PathParam("params")List<PathSegment> params, @Context Request request) {

        double res = 0;
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(86400);
        EntityTag entityTag = generateTag(params);

        Response.ResponseBuilder builder = request.evaluatePreconditions(entityTag);

        if (builder != null) {
            return  builder.cacheControl(cacheControl).tag(entityTag).build();
        }

        for (PathSegment seg: params
             ) {
            try {
                res += Double.valueOf(String.valueOf(seg));
            } catch (NumberFormatException e) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).build());
            }
        }

        Calculation result = new Calculation("add", res);
        return Response.ok(result).cacheControl(cacheControl).tag(entityTag).build();
    }

    @GET
    @Path("/subtract/{params: .*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response subtract(@PathParam("params")List<PathSegment> params, @Context Request request) {

        double res = 0;
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(86400);
        EntityTag entityTag = generateTag(params);

        Response.ResponseBuilder builder = request.evaluatePreconditions(entityTag);

        if (builder != null) {
            return  builder.cacheControl(cacheControl).tag(entityTag).build();
        }

        try {
            res =   Integer.valueOf(String.valueOf(params.get(0)));

            for (int i = 1; i < params.size() ; i++) {
                res -=  Double.valueOf(String.valueOf(params.get(i)));
            }
        } catch (NumberFormatException e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).build());
        }


        Calculation result = new Calculation("subtract", res);
        return Response.ok(result).cacheControl(cacheControl).tag(entityTag).build();
    }

    @GET
    @Path("/divide/{params: .*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response divide(@PathParam("params")List<PathSegment> params, @Context Request request) {

        double res = 0;
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(86400);
        EntityTag entityTag = generateTag(params);

        Response.ResponseBuilder builder = request.evaluatePreconditions(entityTag);

        if (builder != null) {
            return  builder.cacheControl(cacheControl).tag(entityTag).build();
        }

        try {
            res =   Integer.valueOf(String.valueOf(params.get(0)));

            for (int i = 1; i < params.size() ; i++) {
                res /=  Double.valueOf(String.valueOf(params.get(i)));
            }
        } catch (NumberFormatException e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).build());
        }
        Calculation result = new Calculation("divide", res);
        return Response.ok(result).cacheControl(cacheControl).tag(entityTag).build();
    }

    @GET
    @Path("/multiply/{params: .*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response multiply(@PathParam("params")List<PathSegment> params, @Context Request request) {

        double res;
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(86400);
        EntityTag entityTag = generateTag(params);

        Response.ResponseBuilder builder = request.evaluatePreconditions(entityTag);

        if (builder != null) {
            return  builder.cacheControl(cacheControl).tag(entityTag).build();
        }

        try {
            res =   Integer.valueOf(String.valueOf(params.get(0)));

            for (int i = 1; i < params.size() ; i++) {
                res *=  Double.valueOf(String.valueOf(params.get(i)));
            }
        } catch (NumberFormatException e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).build());
        }

        Calculation result = new Calculation("multiply", res);
        return Response.ok(result).cacheControl(cacheControl).tag(entityTag).build();
    }
}
