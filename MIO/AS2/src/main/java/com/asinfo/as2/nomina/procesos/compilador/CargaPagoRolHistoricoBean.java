/*  1:   */ package com.asinfo.as2.nomina.procesos.compilador;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.controller.PageController;
/*  5:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  6:   */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  7:   */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  8:   */ import java.io.BufferedInputStream;
/*  9:   */ import java.io.IOException;
/* 10:   */ import java.io.InputStream;
/* 11:   */ import javax.ejb.EJB;
/* 12:   */ import javax.faces.bean.ManagedBean;
/* 13:   */ import javax.faces.bean.ViewScoped;
/* 14:   */ import org.apache.log4j.Logger;
/* 15:   */ import org.primefaces.event.FileUploadEvent;
/* 16:   */ import org.primefaces.model.UploadedFile;
/* 17:   */ 
/* 18:   */ @ManagedBean
/* 19:   */ @ViewScoped
/* 20:   */ public class CargaPagoRolHistoricoBean
/* 21:   */   extends PageController
/* 22:   */ {
/* 23:   */   private static final long serialVersionUID = 1L;
/* 24:   */   @EJB
/* 25:   */   private ServicioPagoRol servicioPagoRol;
/* 26:   */   
/* 27:   */   public String cargarPagosRol(FileUploadEvent event)
/* 28:   */   {
/* 29:   */     try
/* 30:   */     {
/* 31:55 */       String fileName = event.getFile().getFileName();
/* 32:56 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 33:57 */       this.servicioPagoRol.cargarPagosRol(fileName, input, 4);
/* 34:58 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 35:   */     }
/* 36:   */     catch (ExcepcionAS2Financiero e)
/* 37:   */     {
/* 38:61 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 39:62 */       LOG.error("ERROR AL CARGA DATOS COBROS", e);
/* 40:   */     }
/* 41:   */     catch (IOException e)
/* 42:   */     {
/* 43:65 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos") + " " + e.getMessage());
/* 44:66 */       LOG.error("ERROR AL CARGA DATOS COBROS", e);
/* 45:   */     }
/* 46:   */     catch (ExcepcionAS2 e)
/* 47:   */     {
/* 48:69 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 49:70 */       LOG.error("ERROR AL CARGA DATOS COBROS", e);
/* 50:   */     }
/* 51:   */     catch (Exception e)
/* 52:   */     {
/* 53:73 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 54:74 */       LOG.error("ERROR AL CARGA DATOS COBROS", e);
/* 55:   */     }
/* 56:77 */     return null;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public String getRutaPlantilla()
/* 60:   */   {
/* 61:82 */     return "/resources/plantillas/nomina/AS2 Pago Rol Historico.xls";
/* 62:   */   }
/* 63:   */   
/* 64:   */   public String getNombrePlantilla()
/* 65:   */   {
/* 66:87 */     return "AS2 Pago Rol Historico.xls";
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.compilador.CargaPagoRolHistoricoBean
 * JD-Core Version:    0.7.0.1
 */