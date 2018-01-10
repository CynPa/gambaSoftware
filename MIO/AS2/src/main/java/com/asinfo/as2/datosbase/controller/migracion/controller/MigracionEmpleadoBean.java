/*  1:   */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.controller.PageController;
/*  5:   */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*  6:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  7:   */ import java.io.BufferedInputStream;
/*  8:   */ import java.io.InputStream;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.faces.bean.ManagedBean;
/* 11:   */ import javax.faces.bean.ViewScoped;
/* 12:   */ import org.primefaces.event.FileUploadEvent;
/* 13:   */ import org.primefaces.model.UploadedFile;
/* 14:   */ 
/* 15:   */ @ManagedBean
/* 16:   */ @ViewScoped
/* 17:   */ public class MigracionEmpleadoBean
/* 18:   */   extends PageController
/* 19:   */ {
/* 20:   */   private static final long serialVersionUID = 2807721355378186692L;
/* 21:   */   @EJB
/* 22:   */   private ServicioMigracion servicioMigracion;
/* 23:   */   
/* 24:   */   public String migrarEmpleado(FileUploadEvent event)
/* 25:   */   {
/* 26:   */     try
/* 27:   */     {
/* 28:50 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 29:51 */       this.servicioMigracion.migracionEmpleado(input, 4);
/* 30:52 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 31:   */     }
/* 32:   */     catch (ExcepcionAS2 e)
/* 33:   */     {
/* 34:55 */       e.printStackTrace();
/* 35:56 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 36:   */     }
/* 37:   */     catch (Exception e)
/* 38:   */     {
/* 39:58 */       e.printStackTrace();
/* 40:59 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 41:   */     }
/* 42:61 */     return null;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String getRutaPlantilla()
/* 46:   */   {
/* 47:66 */     return "/resources/plantillas/nomina/AS2 Migracion Empleados.xls";
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String getNombrePlantilla()
/* 51:   */   {
/* 52:71 */     return "AS2 Migracion Empleados.xls";
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */