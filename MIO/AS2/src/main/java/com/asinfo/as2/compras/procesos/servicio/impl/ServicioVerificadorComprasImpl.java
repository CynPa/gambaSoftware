/*   1:    */ package com.asinfo.as2.compras.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioVerificadorCompras;
/*   6:    */ import com.asinfo.as2.dao.CuentaPorPagarDao;
/*   7:    */ import com.asinfo.as2.dao.DetalleFacturaProveedorDao;
/*   8:    */ import com.asinfo.as2.dao.DetalleRecepcionProveedorDao;
/*   9:    */ import com.asinfo.as2.dao.FacturaProveedorDao;
/*  10:    */ import com.asinfo.as2.dao.PedidoProveedorDao;
/*  11:    */ import com.asinfo.as2.dao.ProductoDao;
/*  12:    */ import com.asinfo.as2.dao.ProductoUltimaCompraDao;
/*  13:    */ import com.asinfo.as2.dao.RecepcionProveedorDao;
/*  14:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*  15:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*  16:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoProveedor;
/*  17:    */ import com.asinfo.as2.entities.DetallePago;
/*  18:    */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*  19:    */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*  20:    */ import com.asinfo.as2.entities.Empresa;
/*  21:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  22:    */ import com.asinfo.as2.entities.LiquidacionAnticipoProveedor;
/*  23:    */ import com.asinfo.as2.entities.Pago;
/*  24:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*  25:    */ import com.asinfo.as2.entities.Producto;
/*  26:    */ import com.asinfo.as2.entities.ProductoUltimaCompra;
/*  27:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  28:    */ import com.asinfo.as2.entities.Sucursal;
/*  29:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioLiquidacionAnticipoProveedor;
/*  30:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPago;
/*  31:    */ import java.math.BigDecimal;
/*  32:    */ import java.util.ArrayList;
/*  33:    */ import java.util.List;
/*  34:    */ import javax.ejb.EJB;
/*  35:    */ import javax.ejb.Lock;
/*  36:    */ import javax.ejb.LockType;
/*  37:    */ import javax.ejb.Singleton;
/*  38:    */ 
/*  39:    */ @Singleton
/*  40:    */ public class ServicioVerificadorComprasImpl
/*  41:    */   implements ServicioVerificadorCompras
/*  42:    */ {
/*  43:    */   @EJB
/*  44:    */   private transient PedidoProveedorDao pedidoProveedorDao;
/*  45:    */   @EJB
/*  46:    */   private transient DetalleFacturaProveedorDao detalleFacturaProveedorDao;
/*  47:    */   @EJB
/*  48:    */   private transient DetalleRecepcionProveedorDao detalleRecepcionProveedorDao;
/*  49:    */   @EJB
/*  50:    */   private transient FacturaProveedorDao facturaProveedorDao;
/*  51:    */   @EJB
/*  52:    */   private transient RecepcionProveedorDao recepcionProveedorDao;
/*  53:    */   @EJB
/*  54:    */   private transient ServicioPedidoProveedor servicioPedidoProveedor;
/*  55:    */   @EJB
/*  56:    */   private transient CuentaPorPagarDao cuentaPorPagarDao;
/*  57:    */   @EJB
/*  58:    */   private transient ServicioPago servicioPago;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioLiquidacionAnticipoProveedor servicioLiquidacionAnticipoProveedor;
/*  61:    */   @EJB
/*  62:    */   private transient ProductoDao productoDao;
/*  63:    */   @EJB
/*  64:    */   private transient ProductoUltimaCompraDao productoUltimaCompraDao;
/*  65:    */   
/*  66:    */   public void actualizarCantidadPorFacturar(DetalleFacturaProveedor dfp, boolean indicadorReverso, List<Integer> hmDPC)
/*  67:    */   {
/*  68: 91 */     dfp = (DetalleFacturaProveedor)this.detalleFacturaProveedorDao.buscarPorId(Integer.valueOf(dfp.getId()));
/*  69:    */     
/*  70: 93 */     BigDecimal cantidad = dfp.getCantidad();
/*  71: 94 */     if (indicadorReverso) {
/*  72: 95 */       cantidad = cantidad.negate();
/*  73:    */     }
/*  74: 98 */     if (dfp.getDetallePedidoProveedor() != null)
/*  75:    */     {
/*  76:100 */       if (!hmDPC.contains(Integer.valueOf(dfp.getDetallePedidoProveedor().getPedidoProveedor().getId()))) {
/*  77:101 */         hmDPC.add(Integer.valueOf(dfp.getDetallePedidoProveedor().getPedidoProveedor().getId()));
/*  78:    */       }
/*  79:104 */       actualizarCantidadPorFacturar(dfp.getDetallePedidoProveedor(), cantidad);
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83:107 */       for (DetalleRecepcionProveedor detalleRecepcionProveedor : dfp.getListaDetalleRecepcionProveedor()) {
/*  84:109 */         if ((detalleRecepcionProveedor != null) && (detalleRecepcionProveedor.getDetallePedidoProveedor() != null))
/*  85:    */         {
/*  86:111 */           if (!hmDPC.contains(Integer.valueOf(detalleRecepcionProveedor.getDetallePedidoProveedor().getPedidoProveedor().getId()))) {
/*  87:112 */             hmDPC.add(Integer.valueOf(detalleRecepcionProveedor.getDetallePedidoProveedor().getPedidoProveedor().getId()));
/*  88:    */           }
/*  89:115 */           actualizarCantidadPorFacturar(detalleRecepcionProveedor.getDetallePedidoProveedor(), cantidad);
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void actualizarCantidadPorRecibir(DetalleRecepcionProveedor drp, boolean indicadorReverso, List<Integer> hmDPC)
/*  96:    */   {
/*  97:131 */     drp = (DetalleRecepcionProveedor)this.detalleRecepcionProveedorDao.buscarPorId(Integer.valueOf(drp.getId()));
/*  98:    */     
/*  99:133 */     BigDecimal cantidad = drp.getCantidad();
/* 100:134 */     if (indicadorReverso) {
/* 101:135 */       cantidad = cantidad.negate();
/* 102:    */     }
/* 103:137 */     if (drp.getDetallePedidoProveedor() != null)
/* 104:    */     {
/* 105:138 */       if (!hmDPC.contains(Integer.valueOf(drp.getDetallePedidoProveedor().getPedidoProveedor().getId()))) {
/* 106:139 */         hmDPC.add(Integer.valueOf(drp.getDetallePedidoProveedor().getPedidoProveedor().getId()));
/* 107:    */       }
/* 108:142 */       actualizarCantidadPorRecibir(drp.getDetallePedidoProveedor(), cantidad);
/* 109:    */     }
/* 110:144 */     else if ((drp.getDetalleFacturaProveedor() != null) && (drp.getDetalleFacturaProveedor().getDetallePedidoProveedor() != null))
/* 111:    */     {
/* 112:146 */       if (!hmDPC.contains(Integer.valueOf(drp.getDetalleFacturaProveedor().getDetallePedidoProveedor().getPedidoProveedor().getId()))) {
/* 113:147 */         hmDPC.add(Integer.valueOf(drp.getDetalleFacturaProveedor().getDetallePedidoProveedor().getPedidoProveedor().getId()));
/* 114:    */       }
/* 115:150 */       actualizarCantidadPorRecibir(drp.getDetalleFacturaProveedor().getDetallePedidoProveedor(), cantidad);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   private void actualizarCantidadPorFacturar(DetallePedidoProveedor dpp, BigDecimal cantidad)
/* 120:    */   {
/* 121:156 */     this.pedidoProveedorDao.actualizarCantidadPorFacturar(dpp, cantidad);
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void actualizarCantidadPorRecibir(DetallePedidoProveedor dpp, BigDecimal cantidad)
/* 125:    */   {
/* 126:160 */     this.pedidoProveedorDao.actualizarCantidadPorRecibir(dpp, cantidad);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void actualizarCantidadPorFacturar(FacturaProveedor facturaProveedor, boolean indicadorReverso)
/* 130:    */   {
/* 131:172 */     if ((facturaProveedor.getId() > 0) || (!indicadorReverso))
/* 132:    */     {
/* 133:    */       List<DetalleFacturaProveedor> listataDetalle;
/* 134:    */       List<DetalleFacturaProveedor> listataDetalle;
/* 135:176 */       if (indicadorReverso) {
/* 136:177 */         listataDetalle = this.facturaProveedorDao.cargarDetalleFactura(facturaProveedor.getId());
/* 137:    */       } else {
/* 138:179 */         listataDetalle = facturaProveedor.getListaDetalleFacturaProveedor();
/* 139:    */       }
/* 140:182 */       List<Integer> hmDPC = new ArrayList();
/* 141:183 */       for (DetalleFacturaProveedor dfp : listataDetalle) {
/* 142:184 */         if (!dfp.isEliminado()) {
/* 143:185 */           actualizarCantidadPorFacturar(dfp, indicadorReverso, hmDPC);
/* 144:    */         }
/* 145:    */       }
/* 146:190 */       for (Integer idPedidoProveedor : hmDPC) {
/* 147:191 */         this.servicioPedidoProveedor.cierreAutomatico(idPedidoProveedor.intValue());
/* 148:    */       }
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void actualizarCantidadPorRecibir(RecepcionProveedor recepcionProveedor, boolean indicadorReverso)
/* 153:    */   {
/* 154:205 */     if ((recepcionProveedor.getId() > 0) || (!indicadorReverso))
/* 155:    */     {
/* 156:    */       List<DetalleRecepcionProveedor> listataDetalle;
/* 157:    */       List<DetalleRecepcionProveedor> listataDetalle;
/* 158:209 */       if (indicadorReverso) {
/* 159:210 */         listataDetalle = this.recepcionProveedorDao.cargarDetalleRecepcion(recepcionProveedor.getId());
/* 160:    */       } else {
/* 161:212 */         listataDetalle = recepcionProveedor.getListaDetalleRecepcionProveedor();
/* 162:    */       }
/* 163:215 */       List<Integer> hmDPC = new ArrayList();
/* 164:216 */       for (DetalleRecepcionProveedor drp : listataDetalle) {
/* 165:217 */         if (!drp.isEliminado())
/* 166:    */         {
/* 167:218 */           if ((drp.getDetallePedidoProveedor() != null) && (!hmDPC.contains(Integer.valueOf(drp.getDetallePedidoProveedor().getPedidoProveedor().getId())))) {
/* 168:219 */             hmDPC.add(Integer.valueOf(drp.getDetallePedidoProveedor().getPedidoProveedor().getId()));
/* 169:    */           }
/* 170:222 */           actualizarCantidadPorRecibir(drp, indicadorReverso, hmDPC);
/* 171:    */         }
/* 172:    */       }
/* 173:227 */       for (Integer idPedidoProveedor : hmDPC) {
/* 174:228 */         this.servicioPedidoProveedor.cierreAutomatico(idPedidoProveedor.intValue());
/* 175:    */       }
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void actualizarCuentaPorPagar(Pago pago, boolean indicadorReverso)
/* 180:    */     throws ExcepcionAS2Compras
/* 181:    */   {
/* 182:241 */     if ((pago.getId() > 0) || (!indicadorReverso))
/* 183:    */     {
/* 184:    */       List<DetallePago> lista;
/* 185:    */       List<DetallePago> lista;
/* 186:245 */       if ((indicadorReverso) && (this.servicioPago.buscarPorId(Integer.valueOf(pago.getId())) != null)) {
/* 187:246 */         lista = this.servicioPago.cargarDetalle(pago.getId()).getListaDetallePago();
/* 188:    */       } else {
/* 189:248 */         lista = pago.getListaDetallePago();
/* 190:    */       }
/* 191:251 */       for (DetallePago detallePago : lista) {
/* 192:252 */         if ((!detallePago.isEliminado()) && (!detallePago.getPago().isIndicadorRetencionAsumida())) {
/* 193:253 */           actualizarCuentaPorPagar(detallePago.getCuentaPorPagar(), detallePago.getValor(), indicadorReverso, pago.getTolerancia());
/* 194:    */         }
/* 195:    */       }
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void actualizarCuentaPorPagar(LiquidacionAnticipoProveedor liquidacion, boolean indicadorReverso)
/* 200:    */     throws ExcepcionAS2Compras
/* 201:    */   {
/* 202:269 */     if ((liquidacion.getId() > 0) || (!indicadorReverso))
/* 203:    */     {
/* 204:    */       List<DetalleLiquidacionAnticipoProveedor> lista;
/* 205:    */       List<DetalleLiquidacionAnticipoProveedor> lista;
/* 206:272 */       if ((indicadorReverso) && (this.servicioLiquidacionAnticipoProveedor.buscarPorId(Integer.valueOf(liquidacion.getId())) != null)) {
/* 207:273 */         lista = this.servicioLiquidacionAnticipoProveedor.cargarDetalle(liquidacion.getId()).getListaDetalleLiquidacionAnticipoProveedor();
/* 208:    */       } else {
/* 209:275 */         lista = liquidacion.getListaDetalleLiquidacionAnticipoProveedor();
/* 210:    */       }
/* 211:278 */       for (DetalleLiquidacionAnticipoProveedor dlap : lista) {
/* 212:279 */         if (!dlap.isEliminado()) {
/* 213:280 */           actualizarCuentaPorPagar(dlap.getCuentaPorPagar(), dlap.getValor(), indicadorReverso, BigDecimal.ZERO);
/* 214:    */         }
/* 215:    */       }
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   @Lock(LockType.WRITE)
/* 220:    */   private void actualizarCuentaPorPagar(CuentaPorPagar cxp, BigDecimal valor, boolean indicadorReverso, BigDecimal toletancia)
/* 221:    */     throws ExcepcionAS2Compras
/* 222:    */   {
/* 223:298 */     if (indicadorReverso) {
/* 224:299 */       valor = valor.negate();
/* 225:    */     }
/* 226:302 */     BigDecimal saldo = this.cuentaPorPagarDao.getSaldo(cxp);
/* 227:304 */     if (saldo.subtract(valor).add(toletancia).compareTo(BigDecimal.ZERO) >= 0)
/* 228:    */     {
/* 229:305 */       this.cuentaPorPagarDao.actualizarCuentaPorPagar(cxp, valor);
/* 230:    */     }
/* 231:    */     else
/* 232:    */     {
/* 233:308 */       String mensaje = valor.abs().toString() + " > " + saldo.toString();
/* 234:309 */       throw new ExcepcionAS2Compras("msg_info_cuenta_por_pagar_negativa", mensaje);
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   @Lock(LockType.WRITE)
/* 239:    */   public void actualizarPrecioFechaUltimaCompra(FacturaProveedor facturaProveedor)
/* 240:    */   {
/* 241:323 */     ProductoUltimaCompra productoUltimaCompra = null;
/* 242:324 */     for (DetalleFacturaProveedor dfp : facturaProveedor.getListaDetalleFacturaProveedor()) {
/* 243:325 */       if (!dfp.isEliminado())
/* 244:    */       {
/* 245:326 */         productoUltimaCompra = this.productoUltimaCompraDao.obtenerPrecioUltimaCompra(facturaProveedor.getEmpresa().getId(), dfp.getProducto()
/* 246:327 */           .getId());
/* 247:328 */         if (productoUltimaCompra != null)
/* 248:    */         {
/* 249:330 */           productoUltimaCompra.setPrecio(dfp.getPrecio());
/* 250:331 */           productoUltimaCompra.setFecha(facturaProveedor.getFecha());
/* 251:332 */           this.productoUltimaCompraDao.guardar(productoUltimaCompra);
/* 252:    */         }
/* 253:    */         else
/* 254:    */         {
/* 255:335 */           productoUltimaCompra = new ProductoUltimaCompra();
/* 256:336 */           productoUltimaCompra.setIdOrganizacion(facturaProveedor.getIdOrganizacion());
/* 257:337 */           productoUltimaCompra.setIdSucursal(facturaProveedor.getSucursal().getId());
/* 258:338 */           productoUltimaCompra.setProducto(dfp.getProducto());
/* 259:339 */           productoUltimaCompra.setEmpresa(facturaProveedor.getEmpresa());
/* 260:340 */           productoUltimaCompra.setPrecio(dfp.getPrecio());
/* 261:341 */           productoUltimaCompra.setFecha(facturaProveedor.getFecha());
/* 262:342 */           this.productoUltimaCompraDao.guardar(productoUltimaCompra);
/* 263:    */         }
/* 264:344 */         this.productoDao.actualizarPrecioFechaUltimaCompra(facturaProveedor.getFecha(), dfp.getPrecio(), dfp.getProducto().getIdProducto());
/* 265:    */       }
/* 266:    */     }
/* 267:    */   }
/* 268:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.impl.ServicioVerificadorComprasImpl
 * JD-Core Version:    0.7.0.1
 */