/*  1:   */ package com.asinfo.as2.rs.asistencia.rest;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Empleado;
/*  4:   */ import com.asinfo.as2.entities.nomina.asistencia.MarcacionReloj;
/*  5:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  6:   */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  7:   */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  8:   */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  9:   */ import java.io.PrintStream;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.ws.rs.Consumes;
/* 12:   */ import javax.ws.rs.GET;
/* 13:   */ import javax.ws.rs.Path;
/* 14:   */ import javax.ws.rs.QueryParam;
/* 15:   */ 
/* 16:   */ @Path("/marcacionReloj")
/* 17:   */ public class MarcacionRelojServicioRest
/* 18:   */ {
/* 19:   */   @EJB
/* 20:   */   private ServicioGenerico<MarcacionReloj> servicioMarcacionReloj;
/* 21:   */   @EJB
/* 22:   */   private ServicioEmpleado servicioEmpleado;
/* 23:   */   
/* 24:   */   @GET
/* 25:   */   @Path("/ingresarMarcacionReloj")
/* 26:   */   @Consumes({"text/plain"})
/* 27:   */   public void ingresarAsistencia(@QueryParam("fecha") String fecha, @QueryParam("hora") String hora, @QueryParam("identificacion") String identificacion, @QueryParam("idOrganizacion") String idOrganizacion)
/* 28:   */   {
/* 29:   */     try
/* 30:   */     {
/* 31:31 */       System.out.println("fecha--------->" + fecha + "\nhora--------->" + hora + "\nidentificacion--------->" + identificacion + "\nidOrganizacion--------->" + idOrganizacion);
/* 32:32 */       MarcacionReloj marcacionReloj = new MarcacionReloj();
/* 33:33 */       Empleado emp = this.servicioEmpleado.bucarEmpleadoPorIdentificacion(identificacion, Integer.parseInt(idOrganizacion));
/* 34:34 */       if (emp == null) {
/* 35:35 */         throw new AS2Exception("com.asinfo.as2.rs.asistencia.rest.MarcacionRelojServicioRest.ERROR_IDENTIFICACION_NO_ENCONTRADA", new String[] { identificacion, idOrganizacion });
/* 36:   */       }
/* 37:37 */       marcacionReloj.setIdOrganizacion(Integer.parseInt(idOrganizacion));
/* 38:38 */       marcacionReloj.setEmpleado(emp);
/* 39:39 */       marcacionReloj.setFecha(FuncionesUtiles.stringToDate(fecha, "yyyy-MM-dd"));
/* 40:40 */       marcacionReloj.setMarcacion(FuncionesUtiles.stringToDate(hora, "HH:mm:ss"));
/* 41:41 */       marcacionReloj.setIdentificacion(identificacion);
/* 42:42 */       this.servicioMarcacionReloj.guardar(marcacionReloj);
/* 43:   */     }
/* 44:   */     catch (AS2Exception e)
/* 45:   */     {
/* 46:45 */       System.out.println(e.getMessage());
/* 47:46 */       e.printStackTrace();
/* 48:   */     }
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.asistencia.rest.MarcacionRelojServicioRest
 * JD-Core Version:    0.7.0.1
 */