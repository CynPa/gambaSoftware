/*   1:    */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import java.io.BufferedInputStream;
/*  12:    */ import java.io.InputStream;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import org.apache.log4j.Logger;
/*  17:    */ import org.primefaces.event.FileUploadEvent;
/*  18:    */ import org.primefaces.model.UploadedFile;
/*  19:    */ 
/*  20:    */ @ManagedBean
/*  21:    */ @ViewScoped
/*  22:    */ public class AsignacionCuentaContableAPartidaPresupuestariaBean
/*  23:    */   extends PageControllerAS2
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -3465896114825246824L;
/*  26:    */   @EJB
/*  27:    */   private ServicioMigracion servicioMigracion;
/*  28:    */   
/*  29:    */   public String editar()
/*  30:    */   {
/*  31: 44 */     return null;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String guardar()
/*  35:    */   {
/*  36: 50 */     return null;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String eliminar()
/*  40:    */   {
/*  41: 56 */     return null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String limpiar()
/*  45:    */   {
/*  46: 62 */     return null;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String cargarDatos()
/*  50:    */   {
/*  51: 68 */     return null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void asignarPartidaPresupuestaria(FileUploadEvent event)
/*  55:    */   {
/*  56:    */     try
/*  57:    */     {
/*  58: 74 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  59: 75 */       this.servicioMigracion.asignacionPartidasPresupuestarias(AppUtil.getOrganizacion().getId(), input, 4);
/*  60: 76 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  61:    */     }
/*  62:    */     catch (ExcepcionAS2Financiero e)
/*  63:    */     {
/*  64: 79 */       LOG.info("Error al asignar partidas presupuestarias", e);
/*  65: 80 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  66: 81 */       e.printStackTrace();
/*  67:    */     }
/*  68:    */     catch (AS2Exception e)
/*  69:    */     {
/*  70: 83 */       LOG.info("Error al asignar partidas presupuestarias", e);
/*  71: 84 */       addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/*  72: 85 */       e.printStackTrace();
/*  73:    */     }
/*  74:    */     catch (ExcepcionAS2 e)
/*  75:    */     {
/*  76: 87 */       LOG.info("Error al asignar partidas presupuestarias", e);
/*  77: 88 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  78: 89 */       e.printStackTrace();
/*  79:    */     }
/*  80:    */     catch (Exception e)
/*  81:    */     {
/*  82: 91 */       LOG.error("Error al asignar partidas presupuestarias", e);
/*  83: 92 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  84: 93 */       e.printStackTrace();
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getRutaPlantilla()
/*  89:    */   {
/*  90:107 */     return "/resources/plantillas/financiero/AS2 Asignacion Partida Presupuestaria.xls";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getNombrePlantilla()
/*  94:    */   {
/*  95:112 */     return "AS2 Asignacion Partida Presupuestaria.xls";
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.AsignacionCuentaContableAPartidaPresupuestariaBean
 * JD-Core Version:    0.7.0.1
 */