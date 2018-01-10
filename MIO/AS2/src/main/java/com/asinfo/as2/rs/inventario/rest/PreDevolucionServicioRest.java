/*   1:    */ package com.asinfo.as2.rs.inventario.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.dao.SubempresaDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.DetallePreDevolucionCliente;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  11:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  12:    */ import com.asinfo.as2.entities.PreDevolucionCliente;
/*  13:    */ import com.asinfo.as2.entities.Producto;
/*  14:    */ import com.asinfo.as2.entities.RegistroMovil;
/*  15:    */ import com.asinfo.as2.entities.Subempresa;
/*  16:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  17:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  18:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  19:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  20:    */ import com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente;
/*  21:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*  23:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  24:    */ import com.asinfo.as2.rs.inventario.dto.ConsultarDevolucionRequestDto;
/*  25:    */ import com.asinfo.as2.rs.inventario.dto.CrearDevolucionRequestDto;
/*  26:    */ import com.asinfo.as2.rs.inventario.dto.DetalleDevolucionRequestDto;
/*  27:    */ import com.asinfo.as2.rs.inventario.dto.EstadoDevolucionResponseDto;
/*  28:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  29:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  30:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  31:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  32:    */ import java.math.BigDecimal;
/*  33:    */ import java.math.RoundingMode;
/*  34:    */ import java.text.ParseException;
/*  35:    */ import java.text.SimpleDateFormat;
/*  36:    */ import java.util.Date;
/*  37:    */ import java.util.HashMap;
/*  38:    */ import java.util.List;
/*  39:    */ import java.util.Map;
/*  40:    */ import javax.ejb.EJB;
/*  41:    */ import javax.ws.rs.Consumes;
/*  42:    */ import javax.ws.rs.POST;
/*  43:    */ import javax.ws.rs.Path;
/*  44:    */ import javax.ws.rs.Produces;
/*  45:    */ 
/*  46:    */ @Path("/devolucion")
/*  47:    */ public class PreDevolucionServicioRest
/*  48:    */ {
/*  49:    */   @EJB
/*  50:    */   private transient ServicioProducto servicioProducto;
/*  51:    */   @EJB
/*  52:    */   private transient ServicioUnidad servicioUnidad;
/*  53:    */   @EJB
/*  54:    */   private transient ServicioEmpresa servicioEmpresa;
/*  55:    */   @EJB
/*  56:    */   private transient ServicioSucursal servicioSucursal;
/*  57:    */   @EJB
/*  58:    */   private transient ServicioUsuario servicioUsuario;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioMotivoNotaCreditoCliente servicioMotivoNotaCreditoCliente;
/*  61:    */   @EJB
/*  62:    */   private transient ServicioGenerico<PreDevolucionCliente> servicioPreDevolucionCliente;
/*  63:    */   @EJB
/*  64:    */   private transient ServicioGenerico<DetallePreDevolucionCliente> servicioDetallePreDevolucionCliente;
/*  65:    */   @EJB
/*  66:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  67:    */   @EJB
/*  68:    */   private transient SubempresaDao subempresaDao;
/*  69:    */   @EJB
/*  70:    */   private transient ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  71:    */   @EJB
/*  72:    */   private transient ServicioGenerico<RegistroMovil> servicioRegistroMovil;
/*  73: 79 */   private LanguageController languageController = new LanguageController();
/*  74:    */   
/*  75:    */   public LanguageController getLanguageController()
/*  76:    */   {
/*  77: 82 */     return this.languageController;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setLanguageController(LanguageController languageController)
/*  81:    */   {
/*  82: 86 */     this.languageController = languageController;
/*  83:    */   }
/*  84:    */   
/*  85:    */   @POST
/*  86:    */   @Path("/crearDevolucion")
/*  87:    */   @Consumes({"application/json"})
/*  88:    */   @Produces({"application/json"})
/*  89:    */   public ProcesosResponseDto crearPedido(CrearDevolucionRequestDto request)
/*  90:    */     throws AS2Exception
/*  91:    */   {
/*  92: 95 */     ProcesosResponseDto response = new ProcesosResponseDto();
/*  93: 96 */     PreDevolucionCliente devolucion = new PreDevolucionCliente();
/*  94:    */     
/*  95: 98 */     String error = null;
/*  96: 99 */     this.languageController.setAccesoWeb(false);
/*  97:100 */     this.languageController.setUrlHost(request.getUrlApp());
/*  98:    */     
/*  99:102 */     RegistroMovil registroMovil = new RegistroMovil();
/* 100:103 */     registroMovil.setFecha(new Date());
/* 101:104 */     registroMovil.setCodigoMovil(request.getCodigoMovil());
/* 102:105 */     registroMovil.setDocumentoBase(DocumentoBase.DEVOLUCION_CLIENTE);
/* 103:106 */     registroMovil.setIdOrganizacion(request.getOrganizacion().intValue());
/* 104:107 */     registroMovil.setIdSucursal(request.getSucursal().intValue());
/* 105:    */     
/* 106:109 */     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 107:    */     
/* 108:111 */     EntidadUsuario usuario = this.servicioUsuario.buscarPorId(request.getUsuario());
/* 109:112 */     registroMovil.setUsuario(usuario.getNombreUsuario());
/* 110:    */     try
/* 111:    */     {
/* 112:115 */       Map<String, String> filtros = new HashMap();
/* 113:116 */       filtros.put("idOrganizacion", request.getOrganizacion() + "");
/* 114:117 */       filtros.put("empresa.idEmpresa", request.getCliente() + "");
/* 115:118 */       filtros.put("codigoMovil", request.getCodigoMovil() + "");
/* 116:119 */       List<PreDevolucionCliente> listaPreDevolucionCliente = this.servicioPreDevolucionCliente.obtenerListaCombo(PreDevolucionCliente.class, "fecha", false, filtros);
/* 117:122 */       if (!listaPreDevolucionCliente.isEmpty())
/* 118:    */       {
/* 119:123 */         devolucion = (PreDevolucionCliente)this.servicioPreDevolucionCliente.cargarDetalle(PreDevolucionCliente.class, ((PreDevolucionCliente)listaPreDevolucionCliente.get(0)).getId(), null);
/* 120:124 */         response.setSuccsess(false);
/* 121:125 */         error = "ERROR: Ya existe codigo movil" + devolucion.getCodigoMovil();
/* 122:126 */         registroMovil.setNota(error);
/* 123:127 */         this.servicioRegistroMovil.guardar(registroMovil);
/* 124:    */       }
/* 125:    */       else
/* 126:    */       {
/* 127:130 */         Map<String, String> filtro = new HashMap();
/* 128:131 */         filtro.put("idOrganizacion", request.getOrganizacion() + "");
/* 129:132 */         filtro.put("activo", "true");
/* 130:133 */         MotivoNotaCreditoCliente motivoNotaCredito = null;
/* 131:134 */         List<MotivoNotaCreditoCliente> listaMotivoNotaCredito = this.servicioMotivoNotaCreditoCliente.obtenerListaCombo("predeterminado", false, filtro);
/* 132:136 */         if (listaMotivoNotaCredito.size() > 0)
/* 133:    */         {
/* 134:137 */           motivoNotaCredito = (MotivoNotaCreditoCliente)listaMotivoNotaCredito.get(0);
/* 135:    */         }
/* 136:    */         else
/* 137:    */         {
/* 138:139 */           if (request.getUrlApp() != null)
/* 139:    */           {
/* 140:140 */             response.setSuccsess(false);
/* 141:141 */             error = getLanguageController().getMensaje("msg_error_no_existe_elemento") + " Motivo Nota Credito";
/* 142:142 */             response.setError(error);
/* 143:    */             
/* 144:144 */             registroMovil.setNota(error);
/* 145:145 */             this.servicioRegistroMovil.guardar(registroMovil);
/* 146:    */             
/* 147:147 */             return response;
/* 148:    */           }
/* 149:149 */           registroMovil.setNota("ERROR: No existen Motivos Nota Credito");
/* 150:150 */           this.servicioRegistroMovil.guardar(registroMovil);
/* 151:151 */           throw new AS2Exception("msg_error_no_existe_elemento", new String[] { "" });
/* 152:    */         }
/* 153:155 */         devolucion.setIdOrganizacion(request.getOrganizacion().intValue());
/* 154:156 */         if (request.getReferencia8() != null) {
/* 155:157 */           devolucion.setReferencia8(request.getReferencia8());
/* 156:    */         }
/* 157:159 */         devolucion.setSucursal(this.servicioSucursal.buscarPorId(request.getSucursal()));
/* 158:160 */         devolucion.setFecha(sdf.parse(request.getFecha()));
/* 159:161 */         devolucion.setMotivoNotaCreditoCliente(motivoNotaCredito);
/* 160:162 */         devolucion.setTransportista(this.servicioUsuario.buscarPorId(request.getUsuario()));
/* 161:163 */         devolucion.setNotaTransportista(request.getNota());
/* 162:164 */         devolucion.setEstado(Estado.ELABORADO);
/* 163:165 */         devolucion.setEmpresa(this.servicioEmpresa.buscarPorId(request.getCliente()));
/* 164:166 */         devolucion.setCodigoMovil(request.getCodigoMovil());
/* 165:167 */         devolucion.setIdFacturaClientePadre(request.getFacturaPadre());
/* 166:168 */         devolucion.setTotal(request.getTotal());
/* 167:    */         
/* 168:170 */         MotivoNotaCreditoCliente motivoMovil = this.servicioMotivoNotaCreditoCliente.buscarPorId(request.getMotivoNotaCreditoCliente().intValue());
/* 169:171 */         if (motivoMovil != null) {
/* 170:172 */           devolucion.setMotivoNotaCreditoCliente(motivoMovil);
/* 171:    */         }
/* 172:174 */         devolucion.setUsuarioCreacion(usuario.getNombreUsuario());
/* 173:    */         
/* 174:176 */         Empresa empresaDatos = devolucion.getEmpresa();
/* 175:177 */         if (null != request.getSubcliente())
/* 176:    */         {
/* 177:178 */           Subempresa subempresa = this.subempresaDao.buscarSubEmpresaPorIdEmpresaAndIdSubCliente(request.getCliente().intValue(), request.getSubcliente().intValue());
/* 178:    */           
/* 179:180 */           devolucion.setSubempresa(subempresa);
/* 180:181 */           empresaDatos = devolucion.getSubempresa().getEmpresa();
/* 181:    */         }
/* 182:186 */         for (DetalleDevolucionRequestDto detreq : request.getListaDetalleDevolucion())
/* 183:    */         {
/* 184:188 */           Producto producto = this.servicioProducto.cargaDetalle(detreq.getProducto().intValue());
/* 185:189 */           DetallePreDevolucionCliente detalle = new DetallePreDevolucionCliente();
/* 186:190 */           detalle.setPreDevolucionCliente(devolucion);
/* 187:191 */           detalle.setIdOrganizacion(request.getOrganizacion().intValue());
/* 188:192 */           detalle.setCantidad(detreq.getCantidad());
/* 189:193 */           detalle.setCantidadRecibida(detreq.getCantidad());
/* 190:194 */           detalle.setUnidad(producto.getUnidadVenta());
/* 191:195 */           detalle.setPrecio(detreq.getPrecio());
/* 192:196 */           detalle.setProducto(producto);
/* 193:197 */           Bodega bodegaTrabajo = this.servicioProducto.obtenerBodegaTrabajoProducto(producto, request.getSucursal());
/* 194:198 */           detalle.setBodega(bodegaTrabajo);
/* 195:199 */           detalle.setPrecioLinea(detalle.getCantidad().multiply(detalle.getPrecio()).setScale(2, RoundingMode.HALF_UP));
/* 196:200 */           devolucion.getListaDetallePreDevolucionCliente().add(detalle);
/* 197:    */         }
/* 198:203 */         this.servicioPreDevolucionCliente.guardarValidar(devolucion, devolucion.getListaDetallePreDevolucionCliente());
/* 199:    */         
/* 200:205 */         response.setSuccsess(true);
/* 201:    */         
/* 202:207 */         registroMovil.setNota("REGISTRADO");
/* 203:208 */         this.servicioRegistroMovil.guardar(registroMovil);
/* 204:    */       }
/* 205:210 */       return response;
/* 206:    */     }
/* 207:    */     catch (AS2Exception e)
/* 208:    */     {
/* 209:212 */       e.printStackTrace();
/* 210:213 */       if (request.getUrlApp() != null) {
/* 211:214 */         error = getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage();
/* 212:    */       } else {
/* 213:216 */         error = e.getCodigoExcepcion();
/* 214:    */       }
/* 215:    */     }
/* 216:    */     catch (ParseException e)
/* 217:    */     {
/* 218:219 */       e.printStackTrace();
/* 219:220 */       error = "Mal formado el formato de la fecha";
/* 220:    */     }
/* 221:223 */     registroMovil.setNota(error);
/* 222:224 */     this.servicioRegistroMovil.guardar(registroMovil);
/* 223:227 */     if (request.getUrlApp() != null)
/* 224:    */     {
/* 225:228 */       response.setSuccsess(false);
/* 226:229 */       response.setError(error);
/* 227:230 */       return response;
/* 228:    */     }
/* 229:232 */     throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_203", new String[] { error });
/* 230:    */   }
/* 231:    */   
/* 232:    */   @POST
/* 233:    */   @Path("/consultarDevolucion")
/* 234:    */   @Consumes({"application/json"})
/* 235:    */   @Produces({"application/json"})
/* 236:    */   public EstadoDevolucionResponseDto consultarDevolucion(ConsultarDevolucionRequestDto request)
/* 237:    */     throws AS2Exception
/* 238:    */   {
/* 239:    */     try
/* 240:    */     {
/* 241:242 */       PreDevolucionCliente preDevolucion = null;
/* 242:243 */       FacturaCliente devolucion = null;
/* 243:244 */       EstadoDevolucionResponseDto response = new EstadoDevolucionResponseDto();
/* 244:245 */       response.setEstado("SIN_ENVIAR");
/* 245:246 */       response.setNumero("");
/* 246:247 */       Map<String, String> filters = new HashMap();
/* 247:248 */       filters.put("codigoMovil", request.getCodigoMovil());
/* 248:    */       
/* 249:250 */       List<PreDevolucionCliente> listaPreDevolucion = this.servicioPreDevolucionCliente.obtenerListaCombo(PreDevolucionCliente.class, null, true, filters);
/* 250:252 */       if (listaPreDevolucion.size() > 0)
/* 251:    */       {
/* 252:253 */         preDevolucion = (PreDevolucionCliente)listaPreDevolucion.get(0);
/* 253:254 */         response.setEstado(preDevolucion.getEstado().toString());
/* 254:255 */         response.setNumero("S/N");
/* 255:256 */         response.setIdDevolucion(Integer.valueOf(preDevolucion.getIdPreDevolucionCliente()));
/* 256:    */       }
/* 257:259 */       filters.put("documento.documentoBase", DocumentoBase.DEVOLUCION_CLIENTE.toString());
/* 258:260 */       List<FacturaCliente> listaDevolucion = this.servicioFacturaCliente.obtenerListaCombo(null, true, filters);
/* 259:261 */       if (listaDevolucion.size() > 0)
/* 260:    */       {
/* 261:262 */         devolucion = (FacturaCliente)listaDevolucion.get(0);
/* 262:263 */         response.setEstado(devolucion.getEstado().toString());
/* 263:264 */         response.setNumero(devolucion.getNumero());
/* 264:265 */         response.setIdDevolucion(Integer.valueOf(devolucion.getIdFacturaCliente()));
/* 265:    */       }
/* 266:268 */       return response;
/* 267:    */     }
/* 268:    */     catch (Exception e)
/* 269:    */     {
/* 270:271 */       e.printStackTrace();
/* 271:272 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_203", new String[] { "" });
/* 272:    */     }
/* 273:    */   }
/* 274:    */   
/* 275:    */   @POST
/* 276:    */   @Path("/anularDevolucion")
/* 277:    */   @Consumes({"application/json"})
/* 278:    */   @Produces({"application/json"})
/* 279:    */   public Boolean anularPedido(ConsultarDevolucionRequestDto request)
/* 280:    */     throws AS2Exception
/* 281:    */   {
/* 282:281 */     RegistroMovil registroMovil = new RegistroMovil();
/* 283:282 */     registroMovil.setFecha(new Date());
/* 284:283 */     registroMovil.setCodigoMovil(request.getCodigoMovil());
/* 285:284 */     registroMovil.setDocumentoBase(DocumentoBase.DEVOLUCION_CLIENTE);
/* 286:    */     try
/* 287:    */     {
/* 288:286 */       PreDevolucionCliente preDevolucionAnulado = new PreDevolucionCliente();
/* 289:    */       
/* 290:    */ 
/* 291:289 */       Map<String, String> filters = new HashMap();
/* 292:290 */       filters.put("codigoMovil", request.getCodigoMovil());
/* 293:    */       
/* 294:292 */       List<PreDevolucionCliente> listaPreDevolucion = this.servicioPreDevolucionCliente.obtenerListaCombo(PreDevolucionCliente.class, null, true, filters);
/* 295:294 */       if (listaPreDevolucion.size() > 0)
/* 296:    */       {
/* 297:295 */         preDevolucionAnulado = (PreDevolucionCliente)listaPreDevolucion.get(0);
/* 298:296 */         registroMovil.setIdOrganizacion(preDevolucionAnulado.getIdOrganizacion());
/* 299:297 */         registroMovil.setUsuario(preDevolucionAnulado.getUsuarioCreacion());
/* 300:298 */         if (!preDevolucionAnulado.getEstado().equals(Estado.ELABORADO))
/* 301:    */         {
/* 302:299 */           registroMovil.setNota("ANULAR: Accion no permitida " + preDevolucionAnulado.getEstado());
/* 303:300 */           this.servicioRegistroMovil.guardar(registroMovil);
/* 304:301 */           throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_203", new String[] { preDevolucionAnulado.getEstado().toString() });
/* 305:    */         }
/* 306:303 */         preDevolucionAnulado.setEstado(Estado.ANULADO);
/* 307:304 */         this.servicioPreDevolucionCliente.guardar(preDevolucionAnulado);
/* 308:    */         
/* 309:306 */         registroMovil.setNota(Estado.ANULADO.toString());
/* 310:307 */         this.servicioRegistroMovil.guardar(registroMovil);
/* 311:308 */         return Boolean.valueOf(true);
/* 312:    */       }
/* 313:310 */       registroMovil.setNota("ANULAR: No existe elemento");
/* 314:311 */       this.servicioRegistroMovil.guardar(registroMovil);
/* 315:312 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { "" });
/* 316:    */     }
/* 317:    */     catch (Exception e)
/* 318:    */     {
/* 319:315 */       e.printStackTrace();
/* 320:316 */       registroMovil.setNota("ANULAR: ERROR:" + e.getMessage());
/* 321:317 */       this.servicioRegistroMovil.guardar(registroMovil);
/* 322:318 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_203", new String[] { e.getMessage() });
/* 323:    */     }
/* 324:    */   }
/* 325:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.rest.PreDevolucionServicioRest
 * JD-Core Version:    0.7.0.1
 */