/*  1:   */ package com.asinfo.as2.datosbase.controller.migracion.controller;
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
/* 21:   */ public class MigracionLecturaMantenimientoBean
/* 22:   */   extends PageController
/* 23:   */ {
/* 24:   */   private static final long serialVersionUID = -7213077706907154319L;
/* 25:   */   @EJB
/* 26:   */   private ServicioMigracion servicioMigracion;
/* 27:   */   
/* 28:   */   public String migrarLecturaMantenimiento(FileUploadEvent event)
/* 29:   */   {
/* 30:   */     try
/* 31:   */     {
/* 32:47 */       String fileName = "migracion_lectura_mantenimiento" + event.getFile().getFileName();
/* 33:48 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 34:49 */       int idOrganizacio = AppUtil.getOrganizacion().getIdOrganizacion();
/* 35:50 */       int idSucursal = AppUtil.getSucursal().getIdSucursal();
/* 36:51 */       this.servicioMigracion.migracionLecturaMantenimiento(idOrganizacio, idSucursal, fileName, input, 4);
/* 37:52 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 38:   */     }
/* 39:   */     catch (ExcepcionAS2 e)
/* 40:   */     {
/* 41:55 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 42:56 */       LOG.error("ERROR AL MIGRAR LECTURAS MANTENIMIENTO", e);
/* 43:   */     }
/* 44:   */     catch (Exception e)
/* 45:   */     {
/* 46:58 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 47:59 */       LOG.error("ERROR AL MIGRAR LECTURAS MANTENIMIENTO", e);
/* 48:   */     }
/* 49:61 */     return null;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String getRutaPlantilla()
/* 53:   */   {
/* 54:66 */     return "/resources/plantillas/equipos/AS2 Migracion Lectura Mantenimiento.xls";
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String getNombrePlantilla()
/* 58:   */   {
/* 59:71 */     return "AS2 Migracion Lectura Mantenimiento.xls";
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionLecturaMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */