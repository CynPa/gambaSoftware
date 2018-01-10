/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   4:    */ import com.asinfo.as2.entities.InventarioProducto;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.SaldoProducto;
/*   8:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   9:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*  13:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  16:    */ import java.io.PrintStream;
/*  17:    */ import java.math.BigDecimal;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class ActualizarSaldoPorProductoBean
/*  28:    */ {
/*  29:    */   @EJB
/*  30:    */   private ServicioProducto servicioProducto;
/*  31:    */   @EJB
/*  32:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  33:    */   @EJB
/*  34:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  35:    */   @EJB
/*  36:    */   private ServicioInventarioProducto servicioInventarioProducto;
/*  37:    */   @EJB
/*  38:    */   private ServicioGenerico<SaldoProducto> servicioSaldoProducto;
/*  39:    */   private List<CategoriaProducto> listaCategoriaProducto;
/*  40:    */   private List<SubcategoriaProducto> listSubcategoriaProducto;
/*  41:    */   private List<Producto> listaProducto;
/*  42:    */   private Producto producto;
/*  43:    */   private SubcategoriaProducto subCategoriaProducto;
/*  44:    */   private CategoriaProducto categoriaProducto;
/*  45:    */   
/*  46:    */   @PostConstruct
/*  47:    */   public void init()
/*  48:    */   {
/*  49: 49 */     HashMap<String, String> filtros = new HashMap();
/*  50: 50 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  51: 51 */     this.listaProducto = this.servicioProducto.obtenerListaCombo("nombre", true, filtros);
/*  52: 52 */     this.listaCategoriaProducto = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filtros);
/*  53: 53 */     this.listSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filtros);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String actualizar()
/*  57:    */   {
/*  58: 57 */     List<InventarioProducto> listInventarioProducto = this.servicioInventarioProducto.obtenerMovimientos(AppUtil.getOrganizacion().getId(), this.producto, this.producto
/*  59: 58 */       .getTraBodega(), null, null);
/*  60: 59 */     BigDecimal sum = BigDecimal.ZERO;
/*  61: 60 */     FuncionesUtiles.ordenaLista(listInventarioProducto, "fecha");
/*  62: 61 */     for (InventarioProducto ip : listInventarioProducto) {
/*  63: 62 */       sum = sum.add(ip.getCantidad().multiply(BigDecimal.valueOf(ip.getOperacion())));
/*  64:    */     }
/*  65: 64 */     System.out.println(sum);
/*  66: 65 */     return null;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Producto getProducto()
/*  70:    */   {
/*  71: 69 */     return this.producto;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setProducto(Producto producto)
/*  75:    */   {
/*  76: 73 */     this.producto = producto;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public SubcategoriaProducto getSubCategoriaProducto()
/*  80:    */   {
/*  81: 77 */     return this.subCategoriaProducto;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setSubCategoriaProducto(SubcategoriaProducto subCategoriaProducto)
/*  85:    */   {
/*  86: 81 */     this.subCategoriaProducto = subCategoriaProducto;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public CategoriaProducto getCategoriaProducto()
/*  90:    */   {
/*  91: 85 */     return this.categoriaProducto;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/*  95:    */   {
/*  96: 89 */     this.categoriaProducto = categoriaProducto;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public List<CategoriaProducto> getListaCategoriaProducto()
/* 100:    */   {
/* 101: 93 */     return this.listaCategoriaProducto;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setListaCategoriaProducto(List<CategoriaProducto> listaCategoriaProducto)
/* 105:    */   {
/* 106: 97 */     this.listaCategoriaProducto = listaCategoriaProducto;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List<SubcategoriaProducto> getListSubcategoriaProducto()
/* 110:    */   {
/* 111:101 */     return this.listSubcategoriaProducto;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setListSubcategoriaProducto(List<SubcategoriaProducto> listSubcategoriaProducto)
/* 115:    */   {
/* 116:105 */     this.listSubcategoriaProducto = listSubcategoriaProducto;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<Producto> getListaProducto()
/* 120:    */   {
/* 121:109 */     return this.listaProducto;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setListaProducto(List<Producto> listaProducto)
/* 125:    */   {
/* 126:113 */     this.listaProducto = listaProducto;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.ActualizarSaldoPorProductoBean
 * JD-Core Version:    0.7.0.1
 */