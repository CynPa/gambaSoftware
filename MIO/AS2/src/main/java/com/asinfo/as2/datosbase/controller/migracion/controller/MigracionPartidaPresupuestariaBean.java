/*   1:    */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.io.BufferedInputStream;
/*  11:    */ import java.io.InputStream;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.ViewScoped;
/*  15:    */ import org.apache.log4j.Logger;
/*  16:    */ import org.primefaces.event.FileUploadEvent;
/*  17:    */ import org.primefaces.model.UploadedFile;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @ViewScoped
/*  21:    */ public class MigracionPartidaPresupuestariaBean
/*  22:    */   extends PageControllerAS2
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = -3273378203564218226L;
/*  25:    */   @EJB
/*  26:    */   private ServicioMigracion servicioMigracion;
/*  27:    */   
/*  28:    */   public String editar()
/*  29:    */   {
/*  30: 41 */     return null;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String guardar()
/*  34:    */   {
/*  35: 47 */     return null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String eliminar()
/*  39:    */   {
/*  40: 53 */     return null;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String limpiar()
/*  44:    */   {
/*  45: 59 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String cargarDatos()
/*  49:    */   {
/*  50: 65 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void migrarPartidaPresupuestaria(FileUploadEvent event)
/*  54:    */   {
/*  55:    */     try
/*  56:    */     {
/*  57: 71 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  58: 72 */       this.servicioMigracion.migracionPartidasPresupuestarias(AppUtil.getOrganizacion().getId(), input, 4);
/*  59: 73 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  60:    */     }
/*  61:    */     catch (ExcepcionAS2Financiero e)
/*  62:    */     {
/*  63: 76 */       LOG.info("Error al migrar el plan de cuentas", e);
/*  64: 77 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  65: 78 */       e.printStackTrace();
/*  66:    */     }
/*  67:    */     catch (ExcepcionAS2 e)
/*  68:    */     {
/*  69: 80 */       LOG.info("Error al migrar el plan de cuentas", e);
/*  70: 81 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  71: 82 */       e.printStackTrace();
/*  72:    */     }
/*  73:    */     catch (Exception e)
/*  74:    */     {
/*  75: 84 */       LOG.error("Error al migrar el plan de cuentas", e);
/*  76: 85 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  77: 86 */       e.printStackTrace();
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getRutaPlantilla()
/*  82:    */   {
/*  83:100 */     return "/resources/plantillas/financiero/AS2 Partida Presupuestaria.xls";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getNombrePlantilla()
/*  87:    */   {
/*  88:105 */     return "AS2 Partida Presupuestaria.xls";
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionPartidaPresupuestariaBean
 * JD-Core Version:    0.7.0.1
 */