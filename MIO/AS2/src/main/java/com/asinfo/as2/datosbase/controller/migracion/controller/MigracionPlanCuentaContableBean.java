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
/*  12:    */ import javax.annotation.PostConstruct;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import org.apache.log4j.Logger;
/*  17:    */ import org.primefaces.event.FileUploadEvent;
/*  18:    */ import org.primefaces.model.UploadedFile;
/*  19:    */ 
/*  20:    */ @ManagedBean
/*  21:    */ @ViewScoped
/*  22:    */ public class MigracionPlanCuentaContableBean
/*  23:    */   extends PageControllerAS2
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @EJB
/*  27:    */   private ServicioMigracion servicioMigracion;
/*  28:    */   
/*  29:    */   @PostConstruct
/*  30:    */   public void init() {}
/*  31:    */   
/*  32:    */   private void crearEntidad() {}
/*  33:    */   
/*  34:    */   public String editar()
/*  35:    */   {
/*  36: 92 */     return "";
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String guardar()
/*  40:    */   {
/*  41:109 */     return "";
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String eliminar()
/*  45:    */   {
/*  46:125 */     return "";
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String cargarDatos()
/*  50:    */   {
/*  51:134 */     return "";
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String limpiar()
/*  55:    */   {
/*  56:143 */     crearEntidad();
/*  57:144 */     return "";
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void migrarPlanCuentaContable(FileUploadEvent event)
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64:151 */       String fileName = "migracion_plan_cuenta_contable_" + event.getFile().getFileName();
/*  65:152 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  66:153 */       this.servicioMigracion.migracionPlanDeCuentas(AppUtil.getOrganizacion().getId(), fileName, input, 4);
/*  67:154 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  68:    */     }
/*  69:    */     catch (ExcepcionAS2Financiero e)
/*  70:    */     {
/*  71:157 */       LOG.info("Error al migrar el plan de cuentas", e);
/*  72:158 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  73:159 */       e.printStackTrace();
/*  74:    */     }
/*  75:    */     catch (ExcepcionAS2 e)
/*  76:    */     {
/*  77:161 */       LOG.info("Error al migrar el plan de cuentas", e);
/*  78:162 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  79:163 */       e.printStackTrace();
/*  80:    */     }
/*  81:    */     catch (Exception e)
/*  82:    */     {
/*  83:165 */       LOG.error("Error al migrar el plan de cuentas", e);
/*  84:166 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  85:167 */       e.printStackTrace();
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getRutaPlantilla()
/*  90:    */   {
/*  91:181 */     return "/resources/plantillas/financiero/AS2 Plan de Cuentas.xls";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getNombrePlantilla()
/*  95:    */   {
/*  96:186 */     return "AS2 Plan de Cuentas.xls";
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionPlanCuentaContableBean
 * JD-Core Version:    0.7.0.1
 */