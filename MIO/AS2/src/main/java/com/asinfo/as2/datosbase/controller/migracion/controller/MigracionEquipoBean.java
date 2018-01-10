/*  1:   */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.controller.PageController;
/*  5:   */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*  6:   */ import com.asinfo.as2.entities.Organizacion;
/*  7:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  8:   */ import com.asinfo.as2.util.AppUtil;
/*  9:   */ import java.io.BufferedInputStream;
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
/* 20:   */ public class MigracionEquipoBean
/* 21:   */   extends PageController
/* 22:   */ {
/* 23:   */   private static final long serialVersionUID = 1L;
/* 24:   */   @EJB
/* 25:   */   private ServicioMigracion servicioMigracion;
/* 26:   */   
/* 27:   */   public String migrarEquipo(FileUploadEvent event)
/* 28:   */   {
/* 29:   */     try
/* 30:   */     {
/* 31:41 */       String fileName = "migracion_equipo_" + event.getFile().getFileName();
/* 32:42 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 33:43 */       this.servicioMigracion.migracionEquipos(AppUtil.getOrganizacion().getId(), fileName, input, 4);
/* 34:44 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 35:   */     }
/* 36:   */     catch (ExcepcionAS2 e)
/* 37:   */     {
/* 38:47 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 39:48 */       LOG.error("ERROR AL MIGRAR EQUIPOS", e);
/* 40:   */     }
/* 41:   */     catch (Exception e)
/* 42:   */     {
/* 43:50 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 44:51 */       LOG.error("ERROR AL MIGRAR EQUIPOS", e);
/* 45:   */     }
/* 46:53 */     return null;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String getRutaPlantilla()
/* 50:   */   {
/* 51:58 */     return "/resources/plantillas/equipos/AS2 Migracion Equipo.xls";
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String getNombrePlantilla()
/* 55:   */   {
/* 56:63 */     return "AS2 Migracion Equipo.xls";
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionEquipoBean
 * JD-Core Version:    0.7.0.1
 */