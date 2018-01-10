/*  1:   */ package com.asinfo.as2.rs.datosbase.rest;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  5:   */ import com.asinfo.as2.rs.datosbase.dto.EnvioEmailRequestDto;
/*  6:   */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  7:   */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import java.util.HashMap;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.ws.rs.Consumes;
/* 12:   */ import javax.ws.rs.POST;
/* 13:   */ import javax.ws.rs.Path;
/* 14:   */ import javax.ws.rs.Produces;
/* 15:   */ 
/* 16:   */ @Path("/email")
/* 17:   */ public class EnvioEmailServicioRest
/* 18:   */ {
/* 19:   */   @EJB
/* 20:   */   private ServicioEnvioEmail servicioEnvioEmail;
/* 21:25 */   private LanguageController languageController = new LanguageController();
/* 22:   */   
/* 23:   */   public LanguageController getLanguageController()
/* 24:   */   {
/* 25:28 */     return this.languageController;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setLanguageController(LanguageController languageController)
/* 29:   */   {
/* 30:32 */     this.languageController = languageController;
/* 31:   */   }
/* 32:   */   
/* 33:   */   @POST
/* 34:   */   @Path("/enviarEmail")
/* 35:   */   @Consumes({"application/json"})
/* 36:   */   @Produces({"application/json"})
/* 37:   */   public ProcesosResponseDto enviarEmail(EnvioEmailRequestDto request)
/* 38:   */     throws AS2Exception
/* 39:   */   {
/* 40:40 */     String error = null;
/* 41:41 */     this.languageController.setAccesoWeb(false);
/* 42:42 */     this.languageController.setUrlHost(request.getUrlApp());
/* 43:43 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 44:   */     try
/* 45:   */     {
/* 46:46 */       this.servicioEnvioEmail.enviarEmail(request.getIdOrganizacion().intValue(), request.getPara(), request.getTituloMensaje(), request.getMensajeCuerpo(), new ArrayList(), new HashMap(), request
/* 47:47 */         .getAdjuntoByte(), request.getNombreAdjuntoByte(), request
/* 48:48 */         .getTipoAdjuntoByte());
/* 49:   */       
/* 50:50 */       response.setSuccsess(true);
/* 51:   */       
/* 52:52 */       return response;
/* 53:   */     }
/* 54:   */     catch (Exception e)
/* 55:   */     {
/* 56:54 */       e.printStackTrace();
/* 57:55 */       error = e.getMessage();
/* 58:   */       
/* 59:57 */       response.setSuccsess(false);
/* 60:58 */       response.setError(error);
/* 61:   */     }
/* 62:59 */     return response;
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.rest.EnvioEmailServicioRest
 * JD-Core Version:    0.7.0.1
 */