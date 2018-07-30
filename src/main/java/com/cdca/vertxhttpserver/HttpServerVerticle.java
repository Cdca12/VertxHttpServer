/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdca.vertxhttpserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 *
 * @author sergioc
 */
public class HttpServerVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        super.start();
        
        Router router = Router.router(this.vertx);
        
        router.get("/").handler(this::getRaiz);
        
        router.post("/").handler(BodyHandler.create());
        router.post("/").handler(this::transform);
        
        
        this.vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(8480, reply -> {
            if (reply.succeeded()) {
                System.out.println("Servidor arrancado en: " + 8480);
            } else {
                System.out.println("Servidor no pudo arrancar, pellisquín.");
            }
        });
     
        
            
        }
    
    private void getRaiz(RoutingContext context) {
        JsonObject response = new JsonObject();
        response.put("name", "Carlos")
                .put("lastName", "Contreras");
        
        
        
        context
                .response()
                .putHeader("Content-Type", "application/json")
                .end(Json.encode(response));
    }
    
    private void transform (RoutingContext context) {
        JsonObject body = context.getBodyAsJson();
        String nombre = body.getString("nombre");
        Float dinero = body.getFloat("dinero");
        int edad = body.getInteger("edad", 18);
        
        JsonObject response = new JsonObject()
                .put("nombreCompleto", nombre + " Contreras")
                .put("edad", edad)
                .put("dinero", dinero * 5);
        
        context
                .response()
                .putHeader("Content-Type", "application/json")
                .end(Json.encode(response));
    }
            
    
    
    
}
