/*   1:    */ package com.asinfo.as2.internacionalizacion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.auditoria.ViewHelperAS2;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.entities.Idioma;
/*   7:    */ import com.asinfo.as2.internacionalizacion.datamanager.InternacionalizacionDataManager;
/*   8:    */ import com.asinfo.as2.internacionalizacion.servicio.ServicioInternacionalizacionBean;
/*   9:    */ import java.util.LinkedList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Locale;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.ManagedProperty;
/*  15:    */ import javax.faces.bean.SessionScoped;
/*  16:    */ import javax.faces.model.SelectItem;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @SessionScoped
/*  21:    */ public class InternacionalizacionBean
/*  22:    */   extends PageController
/*  23:    */ {
/*  24:    */   @EJB
/*  25:    */   private ServicioInternacionalizacionBean servicioInternacionalizacionBean;
/*  26:    */   private List<Idioma> idiomas;
/*  27:    */   private List<SelectItem> idiomasItems;
/*  28:    */   @ManagedProperty("#{internacionalizacionDataManager}")
/*  29:    */   private InternacionalizacionDataManager internacionalizacionDataManager;
/*  30:    */   
/*  31:    */   public void cambiarIdioma()
/*  32:    */   {
/*  33: 52 */     ViewHelperAS2.cambiarIdioma(this.internacionalizacionDataManager.getIdiomaSeleccionado());
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void cargarDatos()
/*  37:    */   {
/*  38:    */     try
/*  39:    */     {
/*  40: 61 */       this.idiomas = this.servicioInternacionalizacionBean.getIdiomas();
/*  41: 62 */       this.idiomasItems = new LinkedList();
/*  42: 63 */       for (Idioma idioma : this.idiomas)
/*  43:    */       {
/*  44: 64 */         String valor = idioma.getLocale().toString();
/*  45: 65 */         String label = idioma.getNombre();
/*  46: 66 */         SelectItem item = new SelectItem(valor, label);
/*  47: 67 */         this.idiomasItems.add(item);
/*  48:    */       }
/*  49:    */     }
/*  50:    */     catch (Exception e)
/*  51:    */     {
/*  52: 70 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  53: 71 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<SelectItem> getIdiomasItems()
/*  58:    */   {
/*  59: 80 */     if (this.idiomasItems == null) {
/*  60: 82 */       cargarDatos();
/*  61:    */     }
/*  62: 84 */     return this.idiomasItems;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdiomasItems(List<SelectItem> idiomasItems)
/*  66:    */   {
/*  67: 92 */     this.idiomasItems = idiomasItems;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public InternacionalizacionDataManager getInternacionalizacionDataManager()
/*  71:    */   {
/*  72: 99 */     return this.internacionalizacionDataManager;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setInternacionalizacionDataManager(InternacionalizacionDataManager internacionalizacionDataManager)
/*  76:    */   {
/*  77:109 */     this.internacionalizacionDataManager = internacionalizacionDataManager;
/*  78:    */   }
/*  79:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.internacionalizacion.controller.InternacionalizacionBean
 * JD-Core Version:    0.7.0.1
 */