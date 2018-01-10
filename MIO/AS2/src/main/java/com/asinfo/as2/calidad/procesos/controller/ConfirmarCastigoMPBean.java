/*   1:    */ package com.asinfo.as2.calidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.calidad.procesos.servicio.ServicioControlCalidad;
/*   4:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   5:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   8:    */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   9:    */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*  10:    */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*  11:    */ import com.asinfo.as2.entities.Empresa;
/*  12:    */ import com.asinfo.as2.entities.ImpuestoProductoPedidoProveedor;
/*  13:    */ import com.asinfo.as2.entities.ListaPrecios;
/*  14:    */ import com.asinfo.as2.entities.Organizacion;
/*  15:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*  16:    */ import com.asinfo.as2.entities.Producto;
/*  17:    */ import com.asinfo.as2.entities.Proveedor;
/*  18:    */ import com.asinfo.as2.entities.RangoImpuesto;
/*  19:    */ import com.asinfo.as2.entities.RegistroPeso;
/*  20:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*  21:    */ import com.asinfo.as2.enumeraciones.EstadoRegistroPeso;
/*  22:    */ import com.asinfo.as2.enumeraciones.TipoRegistroPeso;
/*  23:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  24:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  25:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  26:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  27:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*  28:    */ import com.asinfo.as2.util.AppUtil;
/*  29:    */ import com.asinfo.as2.utils.JsfUtil;
/*  30:    */ import java.math.BigDecimal;
/*  31:    */ import java.util.ArrayList;
/*  32:    */ import java.util.List;
/*  33:    */ import java.util.Map;
/*  34:    */ import javax.annotation.PostConstruct;
/*  35:    */ import javax.faces.bean.ManagedBean;
/*  36:    */ import javax.faces.bean.ViewScoped;
/*  37:    */ import javax.faces.component.html.HtmlInputText;
/*  38:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  39:    */ import org.apache.log4j.Logger;
/*  40:    */ import org.primefaces.component.datatable.DataTable;
/*  41:    */ import org.primefaces.model.LazyDataModel;
/*  42:    */ import org.primefaces.model.SortOrder;
/*  43:    */ 
/*  44:    */ @ManagedBean
/*  45:    */ @ViewScoped
/*  46:    */ public class ConfirmarCastigoMPBean
/*  47:    */   extends ControlCalidadMPBean
/*  48:    */ {
/*  49:    */   private static final long serialVersionUID = 1L;
/*  50:    */   private DataTable dtDetallePedidoProveedor;
/*  51:    */   private DetallePedidoProveedor detallePedidoProveedorCastigo;
/*  52:    */   
/*  53:    */   @PostConstruct
/*  54:    */   public void init()
/*  55:    */   {
/*  56: 55 */     this.listaRegistroPeso = new LazyDataModel()
/*  57:    */     {
/*  58:    */       private static final long serialVersionUID = 1L;
/*  59:    */       
/*  60:    */       public List<RegistroPeso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  61:    */       {
/*  62: 61 */         List<RegistroPeso> lista = new ArrayList();
/*  63: 62 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  64:    */         
/*  65: 64 */         ConfirmarCastigoMPBean.this.agregarFiltroOrganizacion(filters);
/*  66: 65 */         filters.put("controlCalidadFinalizado", "false");
/*  67: 66 */         filters.put("tipoRegistroPeso", TipoRegistroPeso.INGRESO_MATERIA_PRIMA.name());
/*  68: 67 */         filters.put("estado", EstadoRegistroPeso.SEGUNDO_PESO.name());
/*  69: 68 */         filters.put("estadoControlCalidad", EstadoControlCalidad.ESPERA_CASTIGO.name());
/*  70: 69 */         filters.put("producto.indicadorControlCalidad", "true");
/*  71:    */         
/*  72: 71 */         lista = ConfirmarCastigoMPBean.this.servicioRegistroPeso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  73: 72 */         ConfirmarCastigoMPBean.this.listaRegistroPeso.setRowCount(ConfirmarCastigoMPBean.this.servicioRegistroPeso.contarPorCriterio(filters));
/*  74:    */         
/*  75: 74 */         return lista;
/*  76:    */       }
/*  77:    */     };
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String editar()
/*  81:    */   {
/*  82: 81 */     if ((this.registroPeso != null) && (this.registroPeso.getId() != 0))
/*  83:    */     {
/*  84: 82 */       this.registroPeso = this.servicioRegistroPeso.cargarDetalle(this.registroPeso.getId());
/*  85: 83 */       if (!this.registroPeso.getEstadoControlCalidad().equals(EstadoControlCalidad.ESPERA_CASTIGO))
/*  86:    */       {
/*  87: 84 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  88: 85 */         return "";
/*  89:    */       }
/*  90: 87 */       prepararCastigo();
/*  91:    */       
/*  92:    */ 
/*  93: 90 */       cargaListaVariableCalidadRegistroPeso();
/*  94:    */       
/*  95: 92 */       actualizaValorNIRAceptable();
/*  96:    */       
/*  97: 94 */       clasificaVariables();
/*  98:    */       
/*  99: 96 */       cargarFechaCaducidad();
/* 100:    */       
/* 101: 98 */       setEditado(true);
/* 102:    */     }
/* 103:    */     else
/* 104:    */     {
/* 105:100 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 106:    */     }
/* 107:102 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   private void dividirDetallePedido()
/* 111:    */   {
/* 112:106 */     if (this.pedidoProveedor != null)
/* 113:    */     {
/* 114:107 */       boolean indicadorNuevo = false;
/* 115:108 */       for (DetallePedidoProveedor detallePedidoProveedor : this.pedidoProveedor.getListaDetallePedidoProveedor()) {
/* 116:109 */         if ((detallePedidoProveedor.getId() == this.registroPeso.getDetallePedidoProveedor().getId()) && 
/* 117:110 */           (detallePedidoProveedor.getCantidadPorRecibir().compareTo(BigDecimal.ZERO) > 0))
/* 118:    */         {
/* 119:111 */           BigDecimal cantidadRecibida = this.servicioRegistroPeso.getCantidadRegistroRecepcionProveedor(this.registroPeso);
/* 120:112 */           if (detallePedidoProveedor.getCantidadPorRecibir().compareTo(cantidadRecibida) > 0)
/* 121:    */           {
/* 122:113 */             this.detallePedidoProveedorCastigo = crearDetalle(detallePedidoProveedor);
/* 123:114 */             this.detallePedidoProveedorCastigo.setCantidad(cantidadRecibida);
/* 124:115 */             this.detallePedidoProveedorCastigo.setCantidadPorRecibir(cantidadRecibida);
/* 125:116 */             indicadorNuevo = true;
/* 126:    */           }
/* 127:    */           else
/* 128:    */           {
/* 129:118 */             this.detallePedidoProveedorCastigo = detallePedidoProveedor;
/* 130:    */           }
/* 131:    */         }
/* 132:    */       }
/* 133:122 */       if ((this.detallePedidoProveedorCastigo != null) && (indicadorNuevo))
/* 134:    */       {
/* 135:123 */         this.pedidoProveedor.getListaDetallePedidoProveedor().add(this.detallePedidoProveedorCastigo);
/* 136:124 */         totalizar();
/* 137:    */       }
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   private DetallePedidoProveedor crearDetalle(DetallePedidoProveedor detalleOriginal)
/* 142:    */   {
/* 143:130 */     DetallePedidoProveedor detalleNuevo = this.servicioPedidoProveedor.cargarDetallePedidoProveedor(detalleOriginal);
/* 144:    */     
/* 145:132 */     detalleNuevo.setIdDetallePedidoProveedor(0);
/* 146:133 */     for (ImpuestoProductoPedidoProveedor impuestoProductoPedidoProveedor : detalleNuevo.getListaImpuestoProductoPedidoProveedor())
/* 147:    */     {
/* 148:134 */       impuestoProductoPedidoProveedor.setIdImpuestoProductoPedidoProveedor(0);
/* 149:135 */       impuestoProductoPedidoProveedor.setDetallePedidoProveedor(detalleNuevo);
/* 150:    */     }
/* 151:137 */     return detalleNuevo;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String guardar()
/* 155:    */   {
/* 156:    */     try
/* 157:    */     {
/* 158:143 */       this.servicioControlCalidad.castigar(this.registroPeso, this.pedidoProveedor, this.detallePedidoProveedorCastigo);
/* 159:144 */       limpiar();
/* 160:145 */       setEditado(false);
/* 161:146 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 162:    */     }
/* 163:    */     catch (AS2Exception e)
/* 164:    */     {
/* 165:148 */       JsfUtil.addErrorMessage(e, "");
/* 166:    */     }
/* 167:    */     catch (ExcepcionAS2 e)
/* 168:    */     {
/* 169:150 */       addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/* 170:    */     }
/* 171:    */     catch (Exception e)
/* 172:    */     {
/* 173:152 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 174:153 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 175:    */     }
/* 176:156 */     return "";
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String eliminar()
/* 180:    */   {
/* 181:161 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 182:162 */     return "";
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String limpiar()
/* 186:    */   {
/* 187:167 */     super.limpiar();
/* 188:168 */     this.detallePedidoProveedorCastigo = null;
/* 189:169 */     return "";
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String crear()
/* 193:    */   {
/* 194:174 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 195:175 */     return "";
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String cargarDatos()
/* 199:    */   {
/* 200:180 */     return "";
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void prepararCastigo()
/* 204:    */   {
/* 205:    */     try
/* 206:    */     {
/* 207:185 */       this.pedidoProveedor = this.servicioPedidoProveedor.cargarDetalle(Integer.valueOf(this.registroPeso.getDetallePedidoProveedor().getPedidoProveedor().getId()));
/* 208:    */       
/* 209:187 */       dividirDetallePedido();
/* 210:    */     }
/* 211:    */     catch (ExcepcionAS2Compras e)
/* 212:    */     {
/* 213:189 */       e.printStackTrace();
/* 214:190 */       addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 219:    */   {
/* 220:195 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 221:196 */     DetallePedidoProveedor dpp = (DetallePedidoProveedor)this.dtDetallePedidoProveedor.getRowData();
/* 222:    */     try
/* 223:    */     {
/* 224:199 */       Producto producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 225:200 */       actualizarProducto(dpp, producto);
/* 226:    */     }
/* 227:    */     catch (ExcepcionAS2 e)
/* 228:    */     {
/* 229:202 */       e.printStackTrace();
/* 230:203 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void actualizarProducto(DetallePedidoProveedor dpp, Producto producto)
/* 235:    */   {
/* 236:208 */     for (ImpuestoProductoPedidoProveedor ippc : dpp.getListaImpuestoProductoPedidoProveedor()) {
/* 237:209 */       ippc.setEliminado(true);
/* 238:    */     }
/* 239:211 */     dpp.setProducto(producto);
/* 240:212 */     dpp.setDescripcion(producto.getNombre());
/* 241:213 */     dpp.setUnidadCompra(producto.getUnidadCompra());
/* 242:214 */     dpp.setIndicadorImpuestos(producto.isIndicadorImpuestos());
/* 243:215 */     if (dpp.isIndicadorImpuestos()) {
/* 244:216 */       obtenerImpuestosProductos(dpp.getProducto(), dpp);
/* 245:    */     }
/* 246:    */     try
/* 247:    */     {
/* 248:219 */       Integer idZona = null;
/* 249:220 */       if (this.pedidoProveedor.getEmpresa().getProveedor().getListaPrecios() != null)
/* 250:    */       {
/* 251:221 */         DetalleVersionListaPrecios dvlp = this.servicioListaPrecios.getDatosVersionListaPrecios(this.pedidoProveedor.getEmpresa().getProveedor()
/* 252:222 */           .getListaPrecios().getIdListaPrecios(), producto.getIdProducto(), this.pedidoProveedor.getFecha(), idZona, "");
/* 253:223 */         dpp.setPrecio(dvlp.getPrecioUnitario());
/* 254:    */       }
/* 255:225 */       else if (dpp.getPrecio().compareTo(BigDecimal.ZERO) == 0)
/* 256:    */       {
/* 257:227 */         dpp.setPrecio(dpp.getProducto().getPrecioUltimaCompra());
/* 258:228 */         dpp.setPrecioLinea(dpp.getPrecio().multiply(dpp.getCantidad()));
/* 259:    */       }
/* 260:    */     }
/* 261:    */     catch (ExcepcionAS2 e)
/* 262:    */     {
/* 263:232 */       e.printStackTrace();
/* 264:233 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 265:234 */       LOG.error("ERROR RECUPERAR PRODUCTO", e);
/* 266:    */     }
/* 267:    */     catch (Exception e)
/* 268:    */     {
/* 269:236 */       e.printStackTrace();
/* 270:237 */       addInfoMessage("msg_error_cargar_datos");
/* 271:238 */       LOG.error("ERROR RECUPERAR PRODUCTO", e);
/* 272:    */     }
/* 273:240 */     totalizar();
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void obtenerImpuestosProductos(Producto producto, DetallePedidoProveedor d)
/* 277:    */   {
/* 278:    */     try
/* 279:    */     {
/* 280:245 */       producto.setCategoriaImpuesto(this.servicioCategoriaImpuesto.cargarDetalle(producto.getCategoriaImpuesto().getId()));
/* 281:246 */       List<RangoImpuesto> listaRangoImpuesto = this.servicioProducto.impuestoProducto(producto, d.getPedidoProveedor().getFecha());
/* 282:247 */       for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/* 283:    */       {
/* 284:248 */         ImpuestoProductoPedidoProveedor impuestoProductoPedidoProveedor = new ImpuestoProductoPedidoProveedor();
/* 285:249 */         impuestoProductoPedidoProveedor.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/* 286:250 */         impuestoProductoPedidoProveedor.setImpuesto(rangoImpuesto.getImpuesto());
/* 287:251 */         impuestoProductoPedidoProveedor.setDetallePedidoProveedor(d);
/* 288:252 */         d.getListaImpuestoProductoPedidoProveedor().add(impuestoProductoPedidoProveedor);
/* 289:    */       }
/* 290:    */     }
/* 291:    */     catch (ExcepcionAS2 ex)
/* 292:    */     {
/* 293:255 */       addErrorMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/* 294:256 */       LOG.info("Error es: " + ex.getErrorMessage(ex));
/* 295:    */     }
/* 296:    */     catch (Exception e)
/* 297:    */     {
/* 298:258 */       LOG.info(e);
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void totalizar()
/* 303:    */   {
/* 304:    */     try
/* 305:    */     {
/* 306:264 */       this.servicioPedidoProveedor.totalizar(this.pedidoProveedor);
/* 307:    */     }
/* 308:    */     catch (ExcepcionAS2Compras e)
/* 309:    */     {
/* 310:266 */       LOG.info(e.getErrorMessage(e));
/* 311:    */     }
/* 312:    */     catch (Exception e)
/* 313:    */     {
/* 314:268 */       LOG.info(e);
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   public DataTable getDtDetallePedidoProveedor()
/* 319:    */   {
/* 320:276 */     return this.dtDetallePedidoProveedor;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setDtDetallePedidoProveedor(DataTable dtDetallePedidoProveedor)
/* 324:    */   {
/* 325:284 */     this.dtDetallePedidoProveedor = dtDetallePedidoProveedor;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public List<DetallePedidoProveedor> getListaDetallePedidoProveedor()
/* 329:    */   {
/* 330:288 */     List<DetallePedidoProveedor> listaDetallePedidoProveedor = new ArrayList();
/* 331:289 */     if (this.detallePedidoProveedorCastigo != null) {
/* 332:290 */       listaDetallePedidoProveedor.add(this.detallePedidoProveedorCastigo);
/* 333:    */     }
/* 334:292 */     return listaDetallePedidoProveedor;
/* 335:    */   }
/* 336:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.calidad.procesos.controller.ConfirmarCastigoMPBean
 * JD-Core Version:    0.7.0.1
 */