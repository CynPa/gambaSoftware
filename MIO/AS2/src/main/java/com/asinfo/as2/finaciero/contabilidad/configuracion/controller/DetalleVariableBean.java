/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   4:    */ import com.asinfo.as2.entities.DetalleVariable;
/*   5:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDetalleVariable;
/*   6:    */ import javax.ejb.EJB;
/*   7:    */ import javax.faces.bean.ManagedBean;
/*   8:    */ import javax.faces.bean.ViewScoped;
/*   9:    */ import org.primefaces.component.datatable.DataTable;
/*  10:    */ import org.primefaces.model.LazyDataModel;
/*  11:    */ 
/*  12:    */ @ManagedBean
/*  13:    */ @ViewScoped
/*  14:    */ public class DetalleVariableBean
/*  15:    */   extends PageControllerAS2
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 1L;
/*  18:    */   @EJB
/*  19:    */   private ServicioDetalleVariable servicioDetalleVariable;
/*  20:    */   private DetalleVariable detalleVariable;
/*  21:    */   private LazyDataModel<DetalleVariable> listaDetalleVariable;
/*  22:    */   private DataTable dtDetalleVariable;
/*  23:    */   private Integer idDetalleVariable;
/*  24:    */   
/*  25:    */   public String editar()
/*  26:    */   {
/*  27: 66 */     return "";
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String guardar()
/*  31:    */   {
/*  32: 70 */     return "";
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String eliminar()
/*  36:    */   {
/*  37: 75 */     return "";
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String limpiar()
/*  41:    */   {
/*  42: 80 */     return null;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String cargarDatos()
/*  46:    */   {
/*  47: 85 */     return "";
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ServicioDetalleVariable getServicioDetalleVariable()
/*  51:    */   {
/*  52: 89 */     return this.servicioDetalleVariable;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setServicioDetalleVariable(ServicioDetalleVariable servicioDetalleVariable)
/*  56:    */   {
/*  57: 94 */     this.servicioDetalleVariable = servicioDetalleVariable;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public DetalleVariable getDetalleVariable()
/*  61:    */   {
/*  62: 98 */     return this.detalleVariable;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setDetalleVariable(DetalleVariable detalleVariable)
/*  66:    */   {
/*  67:102 */     this.detalleVariable = detalleVariable;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public LazyDataModel<DetalleVariable> getListaDetalleVariable()
/*  71:    */   {
/*  72:106 */     return this.listaDetalleVariable;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setListaDetalleVariable(LazyDataModel<DetalleVariable> listaDetalleVariable)
/*  76:    */   {
/*  77:111 */     this.listaDetalleVariable = listaDetalleVariable;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public DataTable getDtDetalleVariable()
/*  81:    */   {
/*  82:115 */     return this.dtDetalleVariable;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setDtDetalleVariable(DataTable dtDetalleVariable)
/*  86:    */   {
/*  87:119 */     this.dtDetalleVariable = dtDetalleVariable;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Integer getIdDetalleVariable()
/*  91:    */   {
/*  92:123 */     return this.idDetalleVariable;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdDetalleVariable(Integer idDetalleVariable)
/*  96:    */   {
/*  97:127 */     this.idDetalleVariable = idDetalleVariable;
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.DetalleVariableBean
 * JD-Core Version:    0.7.0.1
 */