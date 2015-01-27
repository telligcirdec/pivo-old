package santeclair.lunar.framework.cache.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import santeclair.lunar.framework.cache.dto.AdminCache;

@Path("/cache")
public interface AdminCacheManagerWebservice {
    /**
     * Liste les caches de l'application
     * 
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/lister")
    public List<AdminCache> listCaches();

    /**
     * Réinitialise le cache associé au nom passé en paramètre
     * 
     * @param name
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reinitialiser/{name}")
    public AdminCache clearCache(@PathParam("name") String name);

    /**
     * Réinitialise le cache associé au nom passé en paramètre
     * 
     * @param name
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/toutreinitialiser")
    public List<AdminCache> clearAllCache();
}
