/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Atributo;
/*   4:    */ import com.asinfo.as2.entities.ValorAtributo;
/*   5:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*   6:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.faces.bean.ManagedBean;
/*  13:    */ import javax.faces.bean.ViewScoped;
/*  14:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  15:    */ 
/*  16:    */ @ManagedBean
/*  17:    */ @ViewScoped
/*  18:    */ public class AbstractInventarioReportBean
/*  19:    */   extends AbstractBaseReportBean
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1584134027790279238L;
/*  22:    */   @EJB
/*  23:    */   private ServicioValorAtributo servicioValorAtributo;
/*  24:    */   @EJB
/*  25:    */   private ServicioAtributo servicioAtributo;
/*  26:    */   private Atributo atributo;
/*  27:    */   private ValorAtributo valorAtributoSeleccionado;
/*  28: 48 */   private String valorAtributo = "";
/*  29:    */   private List<Atributo> listaAtributo;
/*  30:    */   
/*  31:    */   public List<ValorAtributo> autocompletarValorAtributo(String consulta)
/*  32:    */   {
/*  33: 53 */     if (getAtributo() != null) {
/*  34: 54 */       return this.servicioValorAtributo.autocompletarValorAtributo(consulta, getAtributo());
/*  35:    */     }
/*  36: 56 */     List<ValorAtributo> listaVacia = new ArrayList();
/*  37: 57 */     return listaVacia;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void asignarValorAtributo(ValorAtributo valorAtributoSeleccionado)
/*  41:    */   {
/*  42: 63 */     if (valorAtributoSeleccionado != null) {
/*  43: 64 */       setValorAtributo(valorAtributoSeleccionado.getNombre());
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected JRDataSource getJRDataSource()
/*  48:    */   {
/*  49: 72 */     return null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected String getCompileFileName()
/*  53:    */   {
/*  54: 78 */     return null;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String execute()
/*  58:    */   {
/*  59: 84 */     return null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public ValorAtributo getValorAtributoSeleccionado()
/*  63:    */   {
/*  64: 91 */     return this.valorAtributoSeleccionado;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setValorAtributoSeleccionado(ValorAtributo valorAtributoSeleccionado)
/*  68:    */   {
/*  69: 95 */     this.valorAtributoSeleccionado = valorAtributoSeleccionado;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Atributo getAtributo()
/*  73:    */   {
/*  74: 99 */     return this.atributo;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setAtributo(Atributo atributo)
/*  78:    */   {
/*  79:103 */     this.atributo = atributo;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getValorAtributo()
/*  83:    */   {
/*  84:107 */     return this.valorAtributo;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setValorAtributo(String valorAtributo)
/*  88:    */   {
/*  89:111 */     this.valorAtributo = valorAtributo;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public List<Atributo> getListaAtributo()
/*  93:    */   {
/*  94:120 */     if (this.listaAtributo == null)
/*  95:    */     {
/*  96:121 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/*  97:122 */       filters.put("indicadorProducto", "true");
/*  98:123 */       this.listaAtributo = this.servicioAtributo.obtenerListaCombo("nombre", true, filters);
/*  99:    */     }
/* 100:125 */     return this.listaAtributo;
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.AbstractInventarioReportBean
 * JD-Core Version:    0.7.0.1
 */