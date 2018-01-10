/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioProvincia;
/*   6:    */ import com.asinfo.as2.controller.PageController;
/*   7:    */ import com.asinfo.as2.entities.Ciudad;
/*   8:    */ import com.asinfo.as2.entities.Pais;
/*   9:    */ import com.asinfo.as2.entities.Provincia;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.faces.bean.ManagedBean;
/*  13:    */ import javax.faces.bean.ViewScoped;
/*  14:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  15:    */ import org.primefaces.component.selectonemenu.SelectOneMenu;
/*  16:    */ 
/*  17:    */ @ManagedBean
/*  18:    */ @ViewScoped
/*  19:    */ public class UbicacionBean
/*  20:    */   extends PageController
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 3954292264332240116L;
/*  23:    */   private List<Pais> listaPais;
/*  24:    */   private List<Ciudad> listaCiudad;
/*  25:    */   private List<Provincia> listaProvincia;
/*  26:    */   @EJB
/*  27:    */   private transient ServicioPais servicioPais;
/*  28:    */   @EJB
/*  29:    */   private transient ServicioCiudad servicioCiudad;
/*  30:    */   @EJB
/*  31:    */   private transient ServicioProvincia servicioProvincia;
/*  32:    */   private Pais pais;
/*  33:    */   private Provincia provincia;
/*  34:    */   private Ciudad ciudad;
/*  35:    */   
/*  36:    */   public void obtieneListaProvincia(AjaxBehaviorEvent event)
/*  37:    */   {
/*  38: 58 */     SelectOneMenu selectOneMenu = (SelectOneMenu)event.getComponent();
/*  39: 59 */     Pais pais = (Pais)selectOneMenu.getValue();
/*  40: 60 */     this.listaProvincia = this.servicioProvincia.obtenerListaCombo("nombre", true, null, pais.getId());
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void obtieneListaCiudad(AjaxBehaviorEvent event)
/*  44:    */   {
/*  45: 64 */     SelectOneMenu selectOneMenu = (SelectOneMenu)event.getComponent();
/*  46: 65 */     Provincia provincia = (Provincia)selectOneMenu.getValue();
/*  47: 66 */     this.listaCiudad = this.servicioCiudad.obtenerListaCombo("nombre", true, null, provincia.getId());
/*  48:    */   }
/*  49:    */   
/*  50:    */   public List<Pais> getListaPais()
/*  51:    */   {
/*  52: 75 */     if (this.listaPais == null) {
/*  53: 76 */       this.listaPais = this.servicioPais.obtenerListaCombo("nombre", true, null);
/*  54:    */     }
/*  55: 79 */     return this.listaPais;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Pais getPais()
/*  59:    */   {
/*  60: 90 */     return this.pais;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setPais(Pais pais)
/*  64:    */   {
/*  65:100 */     this.pais = pais;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Provincia getProvincia()
/*  69:    */   {
/*  70:109 */     return this.provincia;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setProvincia(Provincia provincia)
/*  74:    */   {
/*  75:119 */     this.provincia = provincia;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public List<Ciudad> getListaCiudad()
/*  79:    */   {
/*  80:128 */     return this.listaCiudad;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public List<Provincia> getListaProvincia()
/*  84:    */   {
/*  85:137 */     return this.listaProvincia;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Ciudad getCiudad()
/*  89:    */   {
/*  90:144 */     return this.ciudad;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setCiudad(Ciudad ciudad)
/*  94:    */   {
/*  95:152 */     this.ciudad = ciudad;
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.UbicacionBean
 * JD-Core Version:    0.7.0.1
 */