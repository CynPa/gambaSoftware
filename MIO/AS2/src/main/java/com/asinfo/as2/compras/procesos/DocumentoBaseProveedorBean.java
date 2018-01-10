/*   1:    */ package com.asinfo.as2.compras.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   8:    */ import com.asinfo.as2.entities.CondicionPago;
/*   9:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.List;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedProperty;
/*  19:    */ 
/*  20:    */ public abstract class DocumentoBaseProveedorBean
/*  21:    */   extends PageControllerAS2
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = -1132891806602997106L;
/*  24:    */   @EJB
/*  25:    */   protected transient ServicioProducto servicioProducto;
/*  26:    */   @EJB
/*  27:    */   protected transient ServicioDocumento servicioDocumento;
/*  28:    */   @EJB
/*  29:    */   protected transient ServicioEmpresa servicioEmpresa;
/*  30:    */   @EJB
/*  31:    */   protected transient ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  32:    */   @EJB
/*  33:    */   protected transient ServicioListaPrecios servicioListaPrecios;
/*  34:    */   @EJB
/*  35:    */   protected transient ServicioCondicionPago servicioCondicionPago;
/*  36:    */   private List<Empresa> listaProveedor;
/*  37:    */   private List<CondicionPago> listaCondicionPago;
/*  38:    */   private List<DireccionEmpresa> listaDireccionEmpresa;
/*  39:    */   private Producto producto;
/*  40:    */   private Producto[] productosSeleccionados;
/*  41:    */   @ManagedProperty("#{listaProductoBean}")
/*  42:    */   private ListaProductoBean listaProductoBean;
/*  43:    */   
/*  44:    */   public List<Empresa> autocompletarProveedores(String consulta)
/*  45:    */   {
/*  46: 75 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public List<Empresa> getListaProveedor()
/*  50:    */   {
/*  51: 86 */     if (this.listaProveedor == null) {
/*  52: 87 */       this.listaProveedor = this.servicioEmpresa.obtenerProveedores();
/*  53:    */     }
/*  54: 89 */     return this.listaProveedor;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setListaProveedor(List<Empresa> listaProveedor)
/*  58:    */   {
/*  59: 99 */     this.listaProveedor = listaProveedor;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<CondicionPago> getListaCondicionPago()
/*  63:    */   {
/*  64:108 */     if (this.listaCondicionPago == null) {
/*  65:109 */       this.listaCondicionPago = this.servicioCondicionPago.obtenerListaCombo("", false, null);
/*  66:    */     }
/*  67:111 */     return this.listaCondicionPago;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setListaCondicionPago(List<CondicionPago> listaCondicionPago)
/*  71:    */   {
/*  72:121 */     this.listaCondicionPago = listaCondicionPago;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/*  76:    */   {
/*  77:130 */     if (this.listaDireccionEmpresa == null) {
/*  78:131 */       this.listaDireccionEmpresa = new ArrayList();
/*  79:    */     }
/*  80:133 */     return this.listaDireccionEmpresa;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/*  84:    */   {
/*  85:143 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Producto getProducto()
/*  89:    */   {
/*  90:152 */     if (this.producto == null) {
/*  91:153 */       this.producto = new Producto();
/*  92:    */     }
/*  93:155 */     return this.producto;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setProducto(Producto producto)
/*  97:    */   {
/*  98:165 */     this.producto = producto;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Producto[] getProductosSeleccionados()
/* 102:    */   {
/* 103:174 */     return this.productosSeleccionados;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setProductosSeleccionados(Producto[] productosSeleccionados)
/* 107:    */   {
/* 108:184 */     this.productosSeleccionados = productosSeleccionados;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public ListaProductoBean getListaProductoBean()
/* 112:    */   {
/* 113:193 */     return this.listaProductoBean;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 117:    */   {
/* 118:203 */     this.listaProductoBean = listaProductoBean;
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.DocumentoBaseProveedorBean
 * JD-Core Version:    0.7.0.1
 */