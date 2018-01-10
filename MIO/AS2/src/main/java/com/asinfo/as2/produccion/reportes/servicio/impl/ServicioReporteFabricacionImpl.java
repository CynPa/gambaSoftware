/*   1:    */ package com.asinfo.as2.produccion.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.MovimientoInventarioDao;
/*   4:    */ import com.asinfo.as2.dao.reportes.produccion.ReporteFabricacionDao;
/*   5:    */ import com.asinfo.as2.entities.Bodega;
/*   6:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   8:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*   9:    */ import com.asinfo.as2.entities.PresentacionProducto;
/*  10:    */ import com.asinfo.as2.entities.Producto;
/*  11:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.entities.Unidad;
/*  13:    */ import com.asinfo.as2.entities.ValorAtributo;
/*  14:    */ import com.asinfo.as2.entities.produccion.DetallePlanProduccion;
/*  15:    */ import com.asinfo.as2.entities.produccion.Maquina;
/*  16:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  17:    */ import com.asinfo.as2.entities.produccion.PlanProduccion;
/*  18:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  19:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioPlanProduccion;
/*  20:    */ import com.asinfo.as2.produccion.reportes.servicio.ServicioReporteFabricacion;
/*  21:    */ import com.asinfo.as2.utils.NodoArbol;
/*  22:    */ import java.io.Serializable;
/*  23:    */ import java.math.BigDecimal;
/*  24:    */ import java.math.RoundingMode;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.Date;
/*  27:    */ import java.util.HashMap;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import java.util.Map.Entry;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.ejb.Stateless;
/*  33:    */ 
/*  34:    */ @Stateless
/*  35:    */ public class ServicioReporteFabricacionImpl
/*  36:    */   implements ServicioReporteFabricacion, Serializable
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 6825024170922611499L;
/*  39:    */   @EJB
/*  40:    */   private ReporteFabricacionDao reporteFabricacionDao;
/*  41:    */   @EJB
/*  42:    */   private ServicioPlanProduccion servicioPlanProduccion;
/*  43:    */   @EJB
/*  44:    */   private ServicioProducto servicioProducto;
/*  45:    */   @EJB
/*  46:    */   private MovimientoInventarioDao movimientoInventarioDao;
/*  47:    */   
/*  48:    */   public List getReporteOrdenSalidaMaterialvsConsumo(Date fechaDesde, Date fechaHasta, OrdenFabricacion ordenFabricacion)
/*  49:    */   {
/*  50: 54 */     return this.reporteFabricacionDao.getReporteSalidaMaterialvsConsumo(fechaDesde, fechaHasta, ordenFabricacion);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List getReporteCostoFabricacion(Date fechaDesde, Date fechaHasta, OrdenFabricacion ordenFabricacion)
/*  54:    */   {
/*  55: 60 */     return this.reporteFabricacionDao.getReporteCostoFabricacion(fechaDesde, fechaHasta, ordenFabricacion);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List getListaComboOrdenFabricacion(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/*  59:    */   {
/*  60: 66 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public List getReporteParteProduccion(Date fechaDesde, Date fechaHasta)
/*  64:    */   {
/*  65: 72 */     return this.reporteFabricacionDao.getReporteParteProduccion(fechaDesde, fechaHasta);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<Object[]> getReporteProduccion(Date fechaDesde, Date fechaHasta, Bodega bodega, CategoriaProducto categoriaProducto, SubcategoriaProducto subCategoriaProducto, Maquina maquina, PersonaResponsable personaResponsable, List<ValorAtributo> listValoresAtributos, boolean agrupado, int tipoReporte, int numeroAtributosOrganizacion)
/*  69:    */   {
/*  70: 80 */     return this.reporteFabricacionDao.getReporteProduccion(fechaDesde, fechaHasta, bodega, categoriaProducto, subCategoriaProducto, maquina, personaResponsable, listValoresAtributos, agrupado, tipoReporte, numeroAtributosOrganizacion);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List getReporteIRBPNR(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/*  74:    */   {
/*  75: 86 */     return this.reporteFabricacionDao.getReporteIRBPNR(idOrganizacion, fechaDesde, fechaHasta);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public List getReporteMateriales(PlanProduccion planProduccion)
/*  79:    */   {
/*  80: 91 */     Map<Producto, BigDecimal> mapaMateriales = new HashMap();
/*  81: 92 */     List<Object[]> lista = new ArrayList();
/*  82: 93 */     planProduccion = this.servicioPlanProduccion.cargarDetalle(planProduccion.getId());
/*  83: 94 */     for (DetallePlanProduccion detallePlanProduccion : planProduccion.getListaDetallePlanProduccion())
/*  84:    */     {
/*  85: 95 */       BigDecimal boom = detallePlanProduccion.getProducto().getCantidadProduccion();
/*  86: 96 */       BigDecimal totalBatch = BigDecimal.ZERO;
/*  87: 97 */       totalBatch = totalBatch.add(detallePlanProduccion.getVentaLunes());
/*  88: 98 */       totalBatch = totalBatch.add(detallePlanProduccion.getVentaMartes());
/*  89: 99 */       totalBatch = totalBatch.add(detallePlanProduccion.getVentaMiercoles());
/*  90:100 */       totalBatch = totalBatch.add(detallePlanProduccion.getVentaJueves());
/*  91:101 */       totalBatch = totalBatch.add(detallePlanProduccion.getVentaViernes());
/*  92:102 */       totalBatch = totalBatch.add(detallePlanProduccion.getVentaSabado());
/*  93:103 */       totalBatch = totalBatch.add(detallePlanProduccion.getVentaDomingo());
/*  94:    */       
/*  95:    */ 
/*  96:106 */       BigDecimal totalProducir = boom.multiply(totalBatch).setScale(2, RoundingMode.HALF_UP);
/*  97:107 */       detallePlanProduccion.getProducto().setCantidadProducir(totalProducir);
/*  98:108 */       detallePlanProduccion.getProducto().setArbolComponentes(this.servicioProducto.obtenerArbolComponentes(detallePlanProduccion.getProducto()));
/*  99:109 */       for (NodoArbol<Producto> nodo : detallePlanProduccion.getProducto().getArbolComponentes().getHojas())
/* 100:    */       {
/* 101:110 */         BigDecimal cantidad = ((Producto)nodo.getValor()).getCantidadProducir();
/* 102:111 */         if (mapaMateriales.containsKey(nodo.getValor())) {
/* 103:112 */           cantidad = cantidad.add((BigDecimal)mapaMateriales.get(nodo.getValor()));
/* 104:    */         }
/* 105:114 */         mapaMateriales.put(nodo.getValor(), cantidad);
/* 106:    */       }
/* 107:    */     }
/* 108:118 */     for (Map.Entry<Producto, BigDecimal> entry : mapaMateriales.entrySet())
/* 109:    */     {
/* 110:119 */       Producto producto = (Producto)entry.getKey();
/* 111:120 */       BigDecimal cantidad = (BigDecimal)entry.getValue();
/* 112:121 */       Object[] objeto = new Object[6];
/* 113:122 */       objeto[0] = producto.getCodigo();
/* 114:123 */       objeto[1] = producto.getCodigoBarras();
/* 115:124 */       objeto[2] = producto.getNombre();
/* 116:125 */       objeto[3] = producto.getUnidad().getNombre();
/* 117:126 */       objeto[4] = (producto.getPresentacionProducto() != null ? producto.getPresentacionProducto().getNombre() : "");
/* 118:127 */       objeto[5] = cantidad;
/* 119:128 */       lista.add(objeto);
/* 120:    */     }
/* 121:130 */     return lista;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public List<Object[]> getReporteTransformacionProducto(MovimientoInventario transformacionProducto)
/* 125:    */   {
/* 126:135 */     return this.movimientoInventarioDao.getReporteTransformacionProducto(transformacionProducto);
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.servicio.impl.ServicioReporteFabricacionImpl
 * JD-Core Version:    0.7.0.1
 */