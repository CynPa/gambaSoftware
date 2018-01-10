/*  1:   */ package com.asinfo.as2.finaciero.activos.procesos;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.controller.PageController;
/*  5:   */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*  6:   */ import com.asinfo.as2.entities.Organizacion;
/*  7:   */ import com.asinfo.as2.entities.Sucursal;
/*  8:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  9:   */ import com.asinfo.as2.util.AppUtil;
/* 10:   */ import java.io.BufferedInputStream;
/* 11:   */ import java.io.InputStream;
/* 12:   */ import javax.ejb.EJB;
/* 13:   */ import javax.faces.bean.ManagedBean;
/* 14:   */ import javax.faces.bean.ViewScoped;
/* 15:   */ import org.apache.log4j.Logger;
/* 16:   */ import org.primefaces.event.FileUploadEvent;
/* 17:   */ import org.primefaces.model.UploadedFile;
/* 18:   */ 
/* 19:   */ @ManagedBean
/* 20:   */ @ViewScoped
/* 21:   */ public class CargaCustodioActivosFijosBean
/* 22:   */   extends PageController
/* 23:   */ {
/* 24:   */   private static final long serialVersionUID = 1L;
/* 25:   */   @EJB
/* 26:   */   private ServicioMigracion servicioMigracion;
/* 27:   */   
/* 28:   */   public String migrarCustodioActivoFijo(FileUploadEvent event)
/* 29:   */   {
/* 30:   */     try
/* 31:   */     {
/* 32:47 */       String fileName = "migracion_custodio_activo_fijo_" + event.getFile().getFileName();
/* 33:48 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 34:49 */       this.servicioMigracion.migracionCustodioActivoFijo(AppUtil.getOrganizacion().getId(), AppUtil.getSucursal().getId(), fileName, input, 4);
/* 35:50 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 36:   */     }
/* 37:   */     catch (ExcepcionAS2 e)
/* 38:   */     {
/* 39:53 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 40:54 */       LOG.error("ERROR AL MIGRAR CUSTODIOS ACTIVO FIJO", e);
/* 41:   */     }
/* 42:   */     catch (Exception e)
/* 43:   */     {
/* 44:56 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 45:57 */       LOG.error("ERROR AL MIGRAR CUSTODIOS ACTIVO FIJO", e);
/* 46:   */     }
/* 47:59 */     return null;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String getRutaPlantilla()
/* 51:   */   {
/* 52:64 */     return "/resources/plantillas/activosFijos/AS2 Migracion Custodio Activo Fijos.xls";
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String getNombrePlantilla()
/* 56:   */   {
/* 57:69 */     return "AS2 Migracion Custodio Activo Fijos.xls";
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.procesos.CargaCustodioActivosFijosBean
 * JD-Core Version:    0.7.0.1
 */