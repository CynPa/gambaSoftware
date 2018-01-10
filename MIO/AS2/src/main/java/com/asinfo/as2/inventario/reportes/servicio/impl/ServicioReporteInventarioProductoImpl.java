/*   1:    */ package com.asinfo.as2.inventario.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteInventarioProducto;
/*   4:    */ import com.asinfo.as2.dao.InventarioProductoDao;
/*   5:    */ import com.asinfo.as2.dao.reportes.inventario.ReporteInventarioProductoDao;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.InventarioProducto;
/*   8:    */ import com.asinfo.as2.entities.Lote;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.Unidad;
/*  11:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  14:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteInventarioProducto;
/*  15:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  16:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  17:    */ import java.math.BigDecimal;
/*  18:    */ import java.math.RoundingMode;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.List;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.ejb.Stateless;
/*  24:    */ 
/*  25:    */ @Stateless
/*  26:    */ public class ServicioReporteInventarioProductoImpl
/*  27:    */   implements ServicioReporteInventarioProducto
/*  28:    */ {
/*  29:    */   @EJB
/*  30:    */   private InventarioProductoDao inventarioProductoDao;
/*  31:    */   @EJB
/*  32:    */   private ServicioProducto servicioProducto;
/*  33:    */   @EJB
/*  34:    */   private ReporteInventarioProductoDao reporteInventarioProductoDao;
/*  35:    */   @EJB
/*  36:    */   private ServicioBodega servicioBodega;
/*  37:    */   
/*  38:    */   public List<InventarioProducto> obtenerMovimientos(Integer idOrganizacion, Producto producto, Bodega bodega, Date fechaDesde, Date fechaHasta, int unidad)
/*  39:    */     throws ExcepcionAS2
/*  40:    */   {
/*  41: 58 */     BigDecimal saldoInicial = BigDecimal.ZERO;
/*  42: 59 */     saldoInicial = this.servicioProducto.getSaldoInicial(producto.getId(), bodega, fechaDesde).setScale(4, RoundingMode.HALF_UP);
/*  43: 61 */     if (unidad == 1) {
/*  44: 63 */       saldoInicial = this.servicioProducto.convierteUnidad(producto.getUnidad(), producto.getUnidadVenta(), producto.getId(), saldoInicial).setScale(4, RoundingMode.HALF_UP);
/*  45: 64 */     } else if (unidad == 2) {
/*  46: 66 */       saldoInicial = this.servicioProducto.convierteUnidad(producto.getUnidad(), producto.getUnidadAlmacenamiento(), producto.getId(), saldoInicial).setScale(4, RoundingMode.HALF_UP);
/*  47:    */     }
/*  48: 69 */     BigDecimal costoInicial = this.servicioProducto.getCostoInicial(producto.getId(), fechaDesde, bodega).setScale(4, RoundingMode.HALF_UP);
/*  49:    */     
/*  50: 71 */     List<InventarioProducto> lista = this.inventarioProductoDao.obtenerMovimientos(idOrganizacion.intValue(), producto, bodega, fechaDesde, fechaHasta);
/*  51:    */     
/*  52: 73 */     InventarioProducto ip = new InventarioProducto();
/*  53: 74 */     ip.setFecha(fechaDesde);
/*  54: 75 */     ip.setNumeroDocumento("Saldo Inicial");
/*  55: 76 */     ip.setOperacion(1);
/*  56: 77 */     ip.setCantidad(saldoInicial);
/*  57: 78 */     ip.setCantidadTotal(saldoInicial);
/*  58: 79 */     ip.setCosto(costoInicial);
/*  59: 80 */     ip.setCostoTotal(costoInicial);
/*  60:    */     
/*  61: 82 */     lista.add(0, ip);
/*  62:    */     
/*  63: 84 */     return lista;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public List<ReporteInventarioProducto> obtenerMovimientosInventarioProducto(int idOrganizacion, List<Producto> listaProducto, Bodega bodega, Date fechaDesde, Date fechaHasta, int unidad, Lote lote, List<Bodega> listBodegas, int numeroAtributos)
/*  67:    */     throws ExcepcionAS2
/*  68:    */   {
/*  69: 97 */     List<ReporteInventarioProducto> lista = null;
/*  70: 98 */     List<ReporteInventarioProducto> listaReporteInventarioProducto = new ArrayList();
/*  71: 99 */     boolean indicadorCostoPorBodega = ParametrosSistema.isCosteoPorBodega(idOrganizacion).booleanValue();
/*  72:101 */     for (Producto producto : listaProducto)
/*  73:    */     {
/*  74:103 */       lista = new ArrayList();
/*  75:    */       
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:112 */       BigDecimal saldoInicial = BigDecimal.ZERO;
/*  84:115 */       if (bodega != null)
/*  85:    */       {
/*  86:117 */         if (lote != null) {
/*  87:120 */           saldoInicial = this.servicioProducto.getSaldoLote(producto.getId(), bodega.getId(), lote.getIdLote(), FuncionesUtiles.sumarFechaDiasMeses(fechaDesde, -1)).setScale(4, RoundingMode.HALF_UP);
/*  88:    */         } else {
/*  89:122 */           saldoInicial = this.servicioProducto.getSaldoInicial(producto.getId(), bodega, fechaDesde).setScale(4, RoundingMode.HALF_UP);
/*  90:    */         }
/*  91:    */         BigDecimal costoInicial;
/*  92:    */         BigDecimal costoInicial;
/*  93:124 */         if (indicadorCostoPorBodega) {
/*  94:125 */           costoInicial = this.servicioProducto.getCostoUnitario(producto.getId(), FuncionesUtiles.sumarFechaDiasMeses(fechaDesde, -1), bodega).setScale(4, RoundingMode.HALF_UP);
/*  95:    */         } else {
/*  96:127 */           costoInicial = this.servicioProducto.getCostoUnitario(producto.getId(), FuncionesUtiles.sumarFechaDiasMeses(fechaDesde, -1), null).setScale(4, RoundingMode.HALF_UP);
/*  97:    */         }
/*  98:129 */         cargarSaldoInicialReporte(fechaDesde, lista, producto, costoInicial, saldoInicial, bodega);
/*  99:130 */         lista.addAll(cargarMovimientosReporte(idOrganizacion, bodega, fechaDesde, fechaHasta, lote, producto, numeroAtributos));
/* 100:    */       }
/* 101:    */       else
/* 102:    */       {
/* 103:133 */         BigDecimal saldoInicialTotal = BigDecimal.ZERO;
/* 104:135 */         for (Bodega b : listBodegas)
/* 105:    */         {
/* 106:136 */           if (lote != null) {
/* 107:139 */             saldoInicial = this.servicioProducto.getSaldoLote(producto.getId(), b.getId(), lote.getIdLote(), FuncionesUtiles.sumarFechaDiasMeses(fechaDesde, -1)).setScale(4, RoundingMode.HALF_UP);
/* 108:    */           } else {
/* 109:141 */             saldoInicial = this.servicioProducto.getSaldoInicial(producto.getId(), b, fechaDesde).setScale(4, RoundingMode.HALF_UP);
/* 110:    */           }
/* 111:145 */           saldoInicialTotal = saldoInicialTotal.add(saldoInicial);
/* 112:146 */           if (indicadorCostoPorBodega)
/* 113:    */           {
/* 114:147 */             BigDecimal costoInicial = this.servicioProducto.getCostoUnitario(producto.getId(), fechaDesde, b).setScale(12, RoundingMode.HALF_UP);
/* 115:148 */             cargarSaldoInicialReporte(fechaDesde, lista, producto, costoInicial, saldoInicial, b);
/* 116:149 */             lista.addAll(cargarMovimientosReporte(idOrganizacion, b, fechaDesde, fechaHasta, lote, producto, numeroAtributos));
/* 117:    */           }
/* 118:    */         }
/* 119:152 */         if (!indicadorCostoPorBodega)
/* 120:    */         {
/* 121:153 */           BigDecimal costoInicial = this.servicioProducto.getCostoUnitario(producto.getId(), fechaDesde, null).setScale(12, RoundingMode.HALF_UP);
/* 122:154 */           cargarSaldoInicialReporte(fechaDesde, lista, producto, costoInicial, saldoInicialTotal, null);
/* 123:155 */           lista.addAll(cargarMovimientosReporte(idOrganizacion, null, fechaDesde, fechaHasta, lote, producto, numeroAtributos));
/* 124:    */         }
/* 125:    */       }
/* 126:159 */       listaReporteInventarioProducto.addAll(lista);
/* 127:    */     }
/* 128:161 */     return listaReporteInventarioProducto;
/* 129:    */   }
/* 130:    */   
/* 131:    */   private List<ReporteInventarioProducto> cargarMovimientosReporte(int idOrganizacion, Bodega bodega, Date fechaDesde, Date fechaHasta, Lote lote, Producto producto, int numeroAtributos)
/* 132:    */   {
/* 133:167 */     return this.inventarioProductoDao.obtenerMovimientosInventarioProducto(idOrganizacion, producto, bodega, fechaDesde, fechaHasta, lote, numeroAtributos);
/* 134:    */   }
/* 135:    */   
/* 136:    */   private void cargarSaldoInicialReporte(Date fechaDesde, List<ReporteInventarioProducto> lista, Producto producto, BigDecimal costoInicial, BigDecimal saldoInicial, Bodega bodega)
/* 137:    */   {
/* 138:172 */     if (saldoInicial.compareTo(BigDecimal.ZERO) != 0)
/* 139:    */     {
/* 140:173 */       ReporteInventarioProducto rmi = new ReporteInventarioProducto();
/* 141:174 */       rmi.setIdProducto(producto.getIdProducto());
/* 142:175 */       rmi.setFecha(fechaDesde);
/* 143:176 */       rmi.setNumeroDocumento("Saldo Inicial");
/* 144:177 */       rmi.setOperacion(Integer.valueOf(1));
/* 145:178 */       rmi.setCantidad(saldoInicial);
/* 146:179 */       rmi.setCantidadTotal(saldoInicial);
/* 147:180 */       rmi.setNumeroDecimales(producto.getUnidad().getNumeroDecimales());
/* 148:181 */       rmi.setCosto(costoInicial.multiply(saldoInicial));
/* 149:182 */       rmi.setCostoTotal(costoInicial.multiply(saldoInicial));
/* 150:183 */       rmi.setBodega(bodega != null ? bodega.getNombre() : "");
/* 151:184 */       lista.add(rmi);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List getReporteListaMaterial(int idProducto)
/* 156:    */   {
/* 157:196 */     return this.reporteInventarioProductoDao.getReporteListaMaterial(idProducto);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public List getReporteListaMaterialSubproductos(int idProducto)
/* 161:    */   {
/* 162:207 */     return this.reporteInventarioProductoDao.getReporteListaMaterialSubproductos(idProducto);
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.impl.ServicioReporteInventarioProductoImpl
 * JD-Core Version:    0.7.0.1
 */