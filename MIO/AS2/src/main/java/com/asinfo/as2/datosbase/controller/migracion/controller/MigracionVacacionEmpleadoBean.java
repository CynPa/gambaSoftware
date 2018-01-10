/*   1:    */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*   8:    */ import javax.annotation.PostConstruct;
/*   9:    */ import javax.ejb.EJB;
/*  10:    */ import javax.faces.bean.ManagedBean;
/*  11:    */ import javax.faces.bean.ViewScoped;
/*  12:    */ import org.primefaces.event.FileUploadEvent;
/*  13:    */ import org.primefaces.model.UploadedFile;
/*  14:    */ 
/*  15:    */ @ManagedBean
/*  16:    */ @ViewScoped
/*  17:    */ public class MigracionVacacionEmpleadoBean
/*  18:    */   extends PageControllerAS2
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = -1255109611667371602L;
/*  21:    */   @EJB
/*  22:    */   private ServicioMigracion servicioMigracion;
/*  23:    */   
/*  24:    */   @PostConstruct
/*  25:    */   public void init() {}
/*  26:    */   
/*  27:    */   private void crearEntidad() {}
/*  28:    */   
/*  29:    */   public String editar()
/*  30:    */   {
/*  31: 91 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  32:    */     
/*  33: 93 */     return "";
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String guardar()
/*  37:    */   {
/*  38:111 */     return "";
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String eliminar()
/*  42:    */   {
/*  43:128 */     return "";
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String cargarDatos()
/*  47:    */   {
/*  48:137 */     return "";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String limpiar()
/*  52:    */   {
/*  53:146 */     crearEntidad();
/*  54:147 */     return "";
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String migrarVacacionEmpleado(FileUploadEvent event)
/*  58:    */   {
/*  59:    */     try
/*  60:    */     {
/*  61:152 */       this.servicioMigracion.migracionVacacionEmpleado(event.getFile().getInputstream(), 4);
/*  62:153 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  63:    */     }
/*  64:    */     catch (ExcepcionAS2Nomina e)
/*  65:    */     {
/*  66:155 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  67:    */     }
/*  68:    */     catch (ExcepcionAS2 e)
/*  69:    */     {
/*  70:157 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  71:    */     }
/*  72:    */     catch (Exception e)
/*  73:    */     {
/*  74:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  75:    */     }
/*  76:161 */     return null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getRutaPlantilla()
/*  80:    */   {
/*  81:174 */     return "/resources/plantillas/nomina/AS2 Vacacion.xls";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getNombrePlantilla()
/*  85:    */   {
/*  86:179 */     return "AS2 Vacacion.xls";
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionVacacionEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */