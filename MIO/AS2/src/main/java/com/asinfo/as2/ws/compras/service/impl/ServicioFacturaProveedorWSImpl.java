/*   1:    */ package com.asinfo.as2.ws.compras.service.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.CondicionPago;
/*   8:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   9:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.Empresa;
/*  12:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  13:    */ import com.asinfo.as2.entities.ImpuestoProductoFacturaProveedor;
/*  14:    */ import com.asinfo.as2.entities.Producto;
/*  15:    */ import com.asinfo.as2.entities.Proveedor;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  18:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  21:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  22:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  23:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  24:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  25:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  26:    */ import com.asinfo.as2.ws.compras.model.DetalleFacturaProveedorWSEntity;
/*  27:    */ import com.asinfo.as2.ws.compras.model.FacturaProveedorWSEntity;
/*  28:    */ import com.asinfo.as2.ws.compras.service.ServicioFacturaProveedorWS;
/*  29:    */ import java.math.BigDecimal;
/*  30:    */ import java.util.Calendar;
/*  31:    */ import java.util.HashMap;
/*  32:    */ import java.util.List;
/*  33:    */ import java.util.Map;
/*  34:    */ import javax.ejb.EJB;
/*  35:    */ import javax.jws.WebService;
/*  36:    */ 
/*  37:    */ @WebService(endpointInterface="com.asinfo.as2.ws.compras.service.ServicioFacturaProveedorWS")
/*  38:    */ public class ServicioFacturaProveedorWSImpl
/*  39:    */   implements ServicioFacturaProveedorWS
/*  40:    */ {
/*  41:    */   @EJB
/*  42:    */   private ServicioFacturaProveedor servicioFacturaProveedor;
/*  43:    */   @EJB
/*  44:    */   private ServicioEmpresa servicioEmpresa;
/*  45:    */   @EJB
/*  46:    */   private ServicioCondicionPago servicioCondicionPago;
/*  47:    */   @EJB
/*  48:    */   private ServicioDocumento servicioDocumento;
/*  49:    */   @EJB
/*  50:    */   private ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  51:    */   @EJB
/*  52:    */   private ServicioProducto servicioProducto;
/*  53:    */   
/*  54:    */   public boolean isEditable(Long idFacturaProveedor)
/*  55:    */     throws AS2Exception
/*  56:    */   {
/*  57:    */     try
/*  58:    */     {
/*  59: 81 */       FacturaProveedor facturaProveedor = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(idFacturaProveedor.intValue()));
/*  60: 82 */       this.servicioFacturaProveedor.esEditable(facturaProveedor);
/*  61:    */     }
/*  62:    */     catch (ExcepcionAS2 e)
/*  63:    */     {
/*  64: 85 */       throw new AS2Exception("msg_error_editar", new String[] { "" });
/*  65:    */     }
/*  66: 88 */     return true;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean anularFactura(Long idFacturaProveedor)
/*  70:    */     throws AS2Exception
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74:102 */       FacturaProveedor facturaProveedor = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(idFacturaProveedor.intValue()));
/*  75:103 */       this.servicioFacturaProveedor.anular(facturaProveedor);
/*  76:    */     }
/*  77:    */     catch (ExcepcionAS2 e)
/*  78:    */     {
/*  79:106 */       throw new AS2Exception("msg_error_anular", new String[] { "" });
/*  80:    */     }
/*  81:109 */     return true;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean bloquearFactura(int idFacturaProveedor, boolean bloqueo)
/*  85:    */   {
/*  86:121 */     this.servicioFacturaProveedor.bloquearFactura(idFacturaProveedor, bloqueo);
/*  87:    */     
/*  88:123 */     return true;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public FacturaProveedorWSEntity guardarCompraComercializadora(FacturaProveedorWSEntity fpWS)
/*  92:    */     throws AS2Exception
/*  93:    */   {
/*  94:140 */     Map<Integer, DetalleFacturaProveedor> hmDetalle = new HashMap();
/*  95:    */     FacturaProveedor facturaProveedor;
/*  96:141 */     if (fpWS.getIdFacturaProveedor().longValue() != 0L) {
/*  97:    */       try
/*  98:    */       {
/*  99:143 */         FacturaProveedor facturaProveedor = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(fpWS.getIdFacturaProveedor().intValue()));
/* 100:144 */         this.servicioFacturaProveedor.esEditable(facturaProveedor);
/* 101:146 */         for (DetalleFacturaProveedor dfp : facturaProveedor.getListaDetalleFacturaProveedor())
/* 102:    */         {
/* 103:147 */           dfp.setEliminado(true);
/* 104:148 */           hmDetalle.put(Integer.valueOf(dfp.getProducto().getId()), dfp);
/* 105:150 */           for (ImpuestoProductoFacturaProveedor ipfp : dfp.getListaImpuestoProductoFacturaProveedor()) {
/* 106:151 */             ipfp.setEliminado(true);
/* 107:    */           }
/* 108:    */         }
/* 109:    */       }
/* 110:    */       catch (ExcepcionAS2 e)
/* 111:    */       {
/* 112:155 */         e.printStackTrace();
/* 113:156 */         throw new AS2Exception("msg_error_editar", new String[] { "" });
/* 114:    */       }
/* 115:    */     } else {
/* 116:159 */       facturaProveedor = new FacturaProveedor();
/* 117:    */     }
/* 118:163 */     Empresa empresa = this.servicioEmpresa.buscarPorId(Integer.valueOf(fpWS.getIdEmpresa().intValue()));
/* 119:164 */     facturaProveedor.setEmpresa(empresa);
/* 120:    */     
/* 121:166 */     DireccionEmpresa direccionEmpresa = this.servicioEmpresa.buscarDireccionEmpresaPorId(fpWS.getIdDireccionEmpresa().intValue());
/* 122:167 */     facturaProveedor.setDireccionEmpresa(direccionEmpresa);
/* 123:    */     
/* 124:    */ 
/* 125:170 */     Map<String, String> filters = new HashMap();
/* 126:171 */     filters.put("documentoBase", DocumentoBase.FACTURA_PROVEEDOR.toString());
/* 127:172 */     filters.put("indicadorDocumentoTributario", String.valueOf(fpWS.isDocumentoTributario()));
/* 128:173 */     filters.put("indicadorDocumentoExterior", String.valueOf(fpWS.isDocumentoExterior()));
/* 129:174 */     filters.put("idOrganizacion", String.valueOf(fpWS.getIdOrganizacion()));
/* 130:    */     
/* 131:176 */     List<Documento> listaDocumento = this.servicioDocumento.obtenerListaCombo("nombre", true, filters);
/* 132:178 */     if (listaDocumento.isEmpty()) {
/* 133:179 */       throw new AS2Exception("msg_configuracion_documento", new String[] { DocumentoBase.FACTURA_PROVEEDOR.toString() });
/* 134:    */     }
/* 135:181 */     Documento documento = (Documento)listaDocumento.get(0);
/* 136:    */     
/* 137:183 */     facturaProveedor.setDocumento(documento);
/* 138:188 */     if (facturaProveedor.getCondicionPago() == null)
/* 139:    */     {
/* 140:189 */       empresa = this.servicioEmpresa.cargarDetalle(empresa);
/* 141:    */       CondicionPago condicionPago;
/* 142:    */       CondicionPago condicionPago;
/* 143:190 */       if (fpWS.getIdCondicionPago() == null) {
/* 144:191 */         condicionPago = empresa.getProveedor().getCondicionPago();
/* 145:    */       } else {
/* 146:193 */         condicionPago = this.servicioCondicionPago.buscarPorId(Integer.valueOf(fpWS.getIdCondicionPago().intValue()));
/* 147:    */       }
/* 148:195 */       facturaProveedor.setCondicionPago(condicionPago);
/* 149:    */     }
/* 150:197 */     facturaProveedor.setSucursal(new Sucursal(fpWS.getIdSucursal().intValue(), "", ""));
/* 151:198 */     facturaProveedor.setIdOrganizacion(fpWS.getIdOrganizacion().intValue());
/* 152:199 */     facturaProveedor.setIndicadorNoValidarPrecioMayorCero(true);
/* 153:200 */     facturaProveedor.setFecha(fpWS.getFechaRegistro().getTime());
/* 154:201 */     facturaProveedor.setDescuento(BigDecimal.ZERO);
/* 155:202 */     facturaProveedor.setEstado(Estado.APROBADO);
/* 156:203 */     facturaProveedor.setIndicadorSaldoInicial(fpWS.isSaldoInicial());
/* 157:204 */     facturaProveedor.setNumeroCuotas(fpWS.getNumeroCuotas());
/* 158:205 */     facturaProveedor.setDescripcion(fpWS.getDescripcion());
/* 159:206 */     facturaProveedor.setTotal(fpWS.getTotalImporte());
/* 160:207 */     facturaProveedor.setImpuesto(fpWS.getTotalImpuesto());
/* 161:208 */     facturaProveedor.setReferencia1(fpWS.getGuiaMadre() + "/" + fpWS.getGuiaHija());
/* 162:209 */     if (facturaProveedor.getReferencia1().length() > 200) {
/* 163:210 */       facturaProveedor.setReferencia1(facturaProveedor.getReferencia1().substring(0, 200));
/* 164:    */     }
/* 165:212 */     facturaProveedor.setReferencia2(fpWS.getSubcliente() + "/" + fpWS.getInvoice());
/* 166:213 */     if (facturaProveedor.getReferencia2().length() > 200) {
/* 167:214 */       facturaProveedor.setReferencia2(facturaProveedor.getReferencia2().substring(0, 200));
/* 168:    */     }
/* 169:216 */     facturaProveedor.setReferencia3(fpWS.getNumeroFactura());
/* 170:    */     
/* 171:218 */     facturaProveedor.setValorReferencia1(fpWS.getTotalCajas());
/* 172:219 */     facturaProveedor.setValorReferencia2(fpWS.getTotalPiezas());
/* 173:220 */     facturaProveedor.setValorReferencia3(fpWS.getTotalTallos());
/* 174:    */     FacturaProveedorSRI facturaProveedorSRI;
/* 175:221 */     if (fpWS.isDocumentoTributario())
/* 176:    */     {
/* 177:223 */       facturaProveedorSRI = facturaProveedor.getFacturaProveedorSRI();
/* 178:224 */       if (facturaProveedorSRI == null)
/* 179:    */       {
/* 180:225 */         facturaProveedorSRI = new FacturaProveedorSRI();
/* 181:226 */         facturaProveedor.setFacturaProveedorSRI(facturaProveedorSRI);
/* 182:227 */         facturaProveedorSRI.setFacturaProveedor(facturaProveedor);
/* 183:    */       }
/* 184:230 */       facturaProveedorSRI.setIdOrganizacion(facturaProveedor.getIdOrganizacion());
/* 185:231 */       facturaProveedorSRI.setIdSucursal(facturaProveedor.getSucursal().getId());
/* 186:232 */       facturaProveedorSRI.setEstablecimiento(fpWS.getEstablecimiento());
/* 187:233 */       facturaProveedorSRI.setPuntoEmision(fpWS.getPuntoDeVenta());
/* 188:234 */       facturaProveedorSRI.setNumero(fpWS.getNumeroFactura());
/* 189:235 */       facturaProveedorSRI.setFechaRegistro(fpWS.getFechaRegistro().getTime());
/* 190:236 */       facturaProveedorSRI.setFechaEmision(fpWS.getFechaEmision().getTime());
/* 191:237 */       facturaProveedorSRI.setAutorizacion(fpWS.getAutorizacion());
/* 192:    */     }
/* 193:240 */     for (DetalleFacturaProveedorWSEntity detalle : fpWS.getListaDetalleFacturaProveedor())
/* 194:    */     {
/* 195:243 */       Producto producto = null;
/* 196:    */       try
/* 197:    */       {
/* 198:245 */         producto = this.servicioProducto.buscarPorCodigo(detalle.getCodigoProducto(), fpWS.getIdOrganizacion().intValue(), null);
/* 199:    */       }
/* 200:    */       catch (ExcepcionAS2 e1)
/* 201:    */       {
/* 202:247 */         throw new AS2Exception("msg_producto_no_encontrado", new String[] { detalle.getCodigoProducto() });
/* 203:    */       }
/* 204:250 */       DetalleFacturaProveedor dfp = (DetalleFacturaProveedor)hmDetalle.get(Integer.valueOf(producto.getId()));
/* 205:251 */       if (dfp != null)
/* 206:    */       {
/* 207:252 */         dfp.setEliminado(false);
/* 208:    */       }
/* 209:    */       else
/* 210:    */       {
/* 211:254 */         dfp = new DetalleFacturaProveedor();
/* 212:255 */         dfp.setFacturaProveedor(facturaProveedor);
/* 213:256 */         facturaProveedor.getListaDetalleFacturaProveedor().add(dfp);
/* 214:    */       }
/* 215:258 */       dfp.setIdOrganizacion(facturaProveedor.getIdOrganizacion());
/* 216:259 */       dfp.setIdSucursal(facturaProveedor.getSucursal().getId());
/* 217:260 */       dfp.setCantidad(detalle.getCantidad());
/* 218:261 */       dfp.setDescuento(detalle.getDescuentoUnitario());
/* 219:262 */       dfp.setPrecio(detalle.getPrecioUnitario());
/* 220:263 */       dfp.setProducto(producto);
/* 221:    */       
/* 222:265 */       dfp.setUnidadCompra(dfp.getProducto().getUnidadCompra() == null ? dfp.getProducto().getUnidad() : dfp.getProducto().getUnidadCompra());
/* 223:267 */       if (detalle.isIndicadorImpuestos()) {
/* 224:    */         try
/* 225:    */         {
/* 226:269 */           dfp.setIndicadorImpuestos(true);
/* 227:270 */           this.servicioFacturaProveedor.obtenerImpuestosProductos(dfp.getProducto(), dfp);
/* 228:    */         }
/* 229:    */         catch (ExcepcionAS2Inventario e)
/* 230:    */         {
/* 231:273 */           e.printStackTrace();
/* 232:    */         }
/* 233:    */       } else {
/* 234:277 */         dfp.setIndicadorImpuestos(false);
/* 235:    */       }
/* 236:    */     }
/* 237:    */     try
/* 238:    */     {
/* 239:284 */       this.servicioFacturaProveedor.totalizar(facturaProveedor);
/* 240:285 */       this.servicioFacturaProveedorSRI.actualizarFacturaProveedorSRI(facturaProveedor);
/* 241:287 */       if (!facturaProveedor.getTotal().equals(FuncionesUtiles.redondearBigDecimal(BigDecimal.ZERO))) {
/* 242:288 */         this.servicioFacturaProveedor.generarCuentaPorPagar(facturaProveedor);
/* 243:    */       }
/* 244:290 */       this.servicioFacturaProveedor.guardar(facturaProveedor);
/* 245:    */       
/* 246:292 */       fpWS.setIdFacturaProveedor(Long.valueOf(facturaProveedor.getId()));
/* 247:293 */       fpWS.setNumero(facturaProveedor.getNumero());
/* 248:    */     }
/* 249:    */     catch (ExcepcionAS2 e)
/* 250:    */     {
/* 251:297 */       if ("msg_secuencia_no_encontrada".equals(e.getCodigoExcepcion())) {
/* 252:298 */         throw new AS2Exception("msg_secuencia_no_encontrada", new String[] { facturaProveedor.getDocumento().getNombre() });
/* 253:    */       }
/* 254:299 */       if ("msg_periodo_no_encontrado".equals(e.getCodigoExcepcion())) {
/* 255:300 */         throw new AS2Exception("msg_periodo_no_encontrado", new String[0]);
/* 256:    */       }
/* 257:302 */       if ("msgs_mensaje_error_numero_factura_sri_duplicado".equals(e.getCodigoExcepcion())) {
/* 258:304 */         throw new AS2Exception("msg_error_numero_factura_duplicado", new String[] {facturaProveedor.getFacturaProveedorSRI().getNumeroFactura() });
/* 259:    */       }
/* 260:306 */       throw new AS2Exception(e.getCodigoExcepcion());
/* 261:    */     }
/* 262:    */     catch (Exception e)
/* 263:    */     {
/* 264:309 */       e.printStackTrace();
/* 265:310 */       throw new AS2Exception(e.getMessage());
/* 266:    */     }
/* 267:313 */     return fpWS;
/* 268:    */   }
/* 269:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.compras.service.impl.ServicioFacturaProveedorWSImpl
 * JD-Core Version:    0.7.0.1
 */