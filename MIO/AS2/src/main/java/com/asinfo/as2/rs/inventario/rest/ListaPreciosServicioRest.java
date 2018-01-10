/*   1:    */ package com.asinfo.as2.rs.inventario.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.dao.CategoriaImpuestoDao;
/*   5:    */ import com.asinfo.as2.dao.ClienteDao;
/*   6:    */ import com.asinfo.as2.dao.ListaPreciosDao;
/*   7:    */ import com.asinfo.as2.dao.ProductoDao;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   9:    */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*  10:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  11:    */ import com.asinfo.as2.entities.Cliente;
/*  12:    */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*  13:    */ import com.asinfo.as2.entities.Impuesto;
/*  14:    */ import com.asinfo.as2.entities.ListaPrecios;
/*  15:    */ import com.asinfo.as2.entities.PresentacionProducto;
/*  16:    */ import com.asinfo.as2.entities.Producto;
/*  17:    */ import com.asinfo.as2.entities.ProductoMaterial;
/*  18:    */ import com.asinfo.as2.entities.RangoImpuesto;
/*  19:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  20:    */ import com.asinfo.as2.entities.Unidad;
/*  21:    */ import com.asinfo.as2.entities.VersionListaPrecios;
/*  22:    */ import com.asinfo.as2.enumeraciones.TipoImpuestoEnum;
/*  23:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  24:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  25:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  26:    */ import com.asinfo.as2.rs.datosbase.dto.FiltroRequestDto;
/*  27:    */ import com.asinfo.as2.rs.datosbase.dto.FiltrosRequestDto;
/*  28:    */ import com.asinfo.as2.rs.datosbase.dto.ListaPaginadaResponseDto;
/*  29:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  30:    */ import com.asinfo.as2.rs.inventario.dto.ConsultarPrecioRequestDto;
/*  31:    */ import com.asinfo.as2.rs.inventario.dto.ImpuestoResponseDto;
/*  32:    */ import com.asinfo.as2.rs.inventario.dto.ListaListaPreciosRequestDto;
/*  33:    */ import com.asinfo.as2.rs.inventario.dto.ListaPrecioResponseDto;
/*  34:    */ import com.asinfo.as2.rs.inventario.dto.ListaPreciosRequestDto;
/*  35:    */ import com.asinfo.as2.rs.inventario.dto.ListaProductoMaterialResponseDto;
/*  36:    */ import com.asinfo.as2.rs.inventario.dto.ProductoResponseDto;
/*  37:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  38:    */ import java.math.BigDecimal;
/*  39:    */ import java.text.ParseException;
/*  40:    */ import java.text.SimpleDateFormat;
/*  41:    */ import java.util.ArrayList;
/*  42:    */ import java.util.Date;
/*  43:    */ import java.util.HashMap;
/*  44:    */ import java.util.Iterator;
/*  45:    */ import java.util.List;
/*  46:    */ import java.util.Map;
/*  47:    */ import javax.ejb.EJB;
/*  48:    */ import javax.ws.rs.Consumes;
/*  49:    */ import javax.ws.rs.POST;
/*  50:    */ import javax.ws.rs.Path;
/*  51:    */ import javax.ws.rs.Produces;
/*  52:    */ 
/*  53:    */ @Path("/inventario")
/*  54:    */ public class ListaPreciosServicioRest
/*  55:    */ {
/*  56:    */   @EJB
/*  57:    */   private ListaPreciosDao listaPreciosDao;
/*  58:    */   @EJB
/*  59:    */   private ClienteDao clienteDao;
/*  60:    */   @EJB
/*  61:    */   private CategoriaImpuestoDao categoriaImpuestoDao;
/*  62:    */   @EJB
/*  63:    */   private ProductoDao productoDao;
/*  64:    */   @EJB
/*  65:    */   private ServicioListaPrecios servicioListaPrecios;
/*  66: 64 */   private LanguageController languageController = new LanguageController();
/*  67:    */   
/*  68:    */   public LanguageController getLanguageController()
/*  69:    */   {
/*  70: 67 */     return this.languageController;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setLanguageController(LanguageController languageController)
/*  74:    */   {
/*  75: 71 */     this.languageController = languageController;
/*  76:    */   }
/*  77:    */   
/*  78:    */   @POST
/*  79:    */   @Path("/consultarListaPrecio")
/*  80:    */   @Consumes({"application/json"})
/*  81:    */   @Produces({"application/json"})
/*  82:    */   public List<ListaPrecioResponseDto> consultarListaPrecio(ListaListaPreciosRequestDto request)
/*  83:    */     throws AS2Exception
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87: 80 */       Date hoy = FuncionesUtiles.setAtributoFecha(new Date());
/*  88: 81 */       List<ListaPrecioResponseDto> response = new ArrayList();
/*  89:    */       
/*  90: 83 */       List<Cliente> listaCliente = this.clienteDao.buscarClientesActivos(request.getIdOrganizacion());
/*  91:    */       
/*  92: 85 */       List<Integer> listaIdListaPrecios = new ArrayList();
/*  93: 86 */       for (Cliente cliente : listaCliente) {
/*  94: 87 */         if (cliente.getListaPrecios() != null)
/*  95:    */         {
/*  96: 88 */           idListaPrecios = Integer.valueOf(cliente.getListaPrecios().getId());
/*  97: 89 */           listaIdListaPrecios.add(idListaPrecios);
/*  98:    */         }
/*  99:    */       }
/* 100: 94 */       Object hashIdListaPrecios = new HashMap();
/* 101: 95 */       List<Integer> auxListaIdListaPrecios = new ArrayList();
/* 102: 96 */       for (Integer idListaPrecios = listaIdListaPrecios.iterator(); idListaPrecios.hasNext();)
/* 103:    */       {
/* 104: 96 */         id = (Integer)idListaPrecios.next();
/* 105: 97 */         ((HashMap)hashIdListaPrecios).put(id, id);
/* 106:    */       }
/* 107:    */       Integer id;
/* 108:100 */       auxListaIdListaPrecios.addAll(((HashMap)hashIdListaPrecios).values());
/* 109:    */       
/* 110:102 */       List<DetalleVersionListaPrecios> listaDetalleVersionListaPrecios = this.listaPreciosDao.obtenerListaDetalleVersionListaPrecios(hoy, auxListaIdListaPrecios);
/* 111:105 */       for (DetalleVersionListaPrecios detalle : listaDetalleVersionListaPrecios)
/* 112:    */       {
/* 113:106 */         ListaPrecioResponseDto listaPreciosResponse = new ListaPrecioResponseDto();
/* 114:107 */         listaPreciosResponse.setIdDetalleListaPrecio(Integer.valueOf(detalle.getIdDetalleVersionListaPrecios()));
/* 115:108 */         listaPreciosResponse.setIdListaPrecioCabecera(Integer.valueOf(detalle.getVersionListaPrecios().getListaPrecios().getIdListaPrecios()));
/* 116:109 */         listaPreciosResponse.setPrecioUnitario(detalle.getPrecioUnitario());
/* 117:110 */         listaPreciosResponse.setIdOrganizacion(Integer.valueOf(detalle.getVersionListaPrecios().getListaPrecios().getIdOrganizacion()));
/* 118:111 */         listaPreciosResponse.setIdSucursal(Integer.valueOf(detalle.getVersionListaPrecios().getListaPrecios().getIdSucursal()));
/* 119:112 */         listaPreciosResponse.setNombre(detalle.getVersionListaPrecios().getListaPrecios().getNombre());
/* 120:113 */         Producto producto = detalle.getProducto();
/* 121:    */         
/* 122:    */ 
/* 123:116 */         ProductoResponseDto productoResponse = new ProductoResponseDto();
/* 124:117 */         productoResponse.setIdProducto(Integer.valueOf(producto.getIdProducto()));
/* 125:118 */         productoResponse.setCodigo(producto.getCodigo());
/* 126:119 */         productoResponse.setCodigoAlterno(producto.getCodigoAlterno());
/* 127:120 */         productoResponse.setCodigoBarras(producto.getCodigoBarras());
/* 128:121 */         productoResponse.setCodigoComercial(producto.getCodigoComercial());
/* 129:122 */         productoResponse.setNombre(producto.getNombre());
/* 130:123 */         if (producto.getSubcategoriaProducto() != null)
/* 131:    */         {
/* 132:124 */           productoResponse.setSubcategoriaProducto(producto.getSubcategoriaProducto().getNombre());
/* 133:125 */           productoResponse.setCategoriaProducto(producto.getSubcategoriaProducto().getCategoriaProducto().getNombre());
/* 134:    */         }
/* 135:127 */         productoResponse.setIdOrganizacion(Integer.valueOf(producto.getIdOrganizacion()));
/* 136:128 */         productoResponse.setIndicadorImpuestos(producto.isIndicadorImpuestos());
/* 137:129 */         productoResponse.setMultiploPedido(producto.getMultiploPedido());
/* 138:130 */         if (producto.getPresentacionProducto() != null)
/* 139:    */         {
/* 140:131 */           productoResponse.setPresentacionProducto(producto.getPresentacionProducto().getNombre());
/* 141:132 */           productoResponse.setCantidadUnidades(producto.getPresentacionProducto().getCantidadUnidades());
/* 142:    */         }
/* 143:134 */         productoResponse.setTipoProducto(producto.getTipoProducto().toString());
/* 144:135 */         if (producto.getUnidad() != null) {
/* 145:136 */           productoResponse.setUnidad(producto.getUnidad().getNombre());
/* 146:    */         }
/* 147:138 */         if (producto.getUnidadVenta() != null) {
/* 148:139 */           productoResponse.setUnidadVenta(producto.getUnidadVenta().getNombre());
/* 149:    */         }
/* 150:142 */         productoResponse.setIndicadorPorcentajeIce(producto.isIndicadorPorcentajeIce());
/* 151:143 */         productoResponse.setCodigoIce(producto.getCodigoIce());
/* 152:144 */         productoResponse.setIce(producto.getIce());
/* 153:    */         
/* 154:    */ 
/* 155:147 */         List<ProductoMaterial> listaProductoMaterial = this.productoDao.getListaProductoMaterial(producto);
/* 156:148 */         for (Iterator localIterator2 = listaProductoMaterial.iterator(); localIterator2.hasNext();)
/* 157:    */         {
/* 158:148 */           productoMaterial = (ProductoMaterial)localIterator2.next();
/* 159:    */           
/* 160:150 */           ListaProductoMaterialResponseDto productoMaterialResponse = new ListaProductoMaterialResponseDto();
/* 161:151 */           productoMaterialResponse.setIdOrganizacion(productoMaterial.getIdOrganizacion());
/* 162:152 */           productoMaterialResponse.setIdSucursal(productoMaterial.getIdSucursal());
/* 163:153 */           productoMaterialResponse.setCantidad(productoMaterial.getCantidad());
/* 164:154 */           productoMaterialResponse.setActivo(Boolean.valueOf(productoMaterial.isActivo()));
/* 165:155 */           productoMaterialResponse.setIndicadorPrincipal(productoMaterial.getIndicadorPrincipal());
/* 166:156 */           productoMaterialResponse.setProporcion(productoMaterial.getProporcion());
/* 167:157 */           productoMaterialResponse.setCantidadSustituto(productoMaterial.getCantidadSustituto());
/* 168:    */           
/* 169:    */ 
/* 170:160 */           productoListaMaterial = productoMaterial.getMaterial();
/* 171:    */           
/* 172:162 */           ProductoResponseDto productoMaterialResponseDto = new ProductoResponseDto();
/* 173:163 */           productoMaterialResponseDto.setCodigo(productoListaMaterial.getCodigo());
/* 174:164 */           productoMaterialResponseDto.setNombre(productoListaMaterial.getNombre());
/* 175:165 */           productoMaterialResponseDto.setUnidad(productoListaMaterial.getUnidad().getNombre());
/* 176:    */           
/* 177:167 */           productoMaterialResponse.setProducto(productoMaterialResponseDto);
/* 178:    */           
/* 179:169 */           Producto productoListaMaterialSustituto = productoMaterial.getSustituto();
/* 180:    */           
/* 181:    */ 
/* 182:172 */           ProductoResponseDto productoSustitutoResponseDto = new ProductoResponseDto();
/* 183:173 */           productoSustitutoResponseDto.setCodigo(productoListaMaterialSustituto.getCodigo());
/* 184:174 */           productoSustitutoResponseDto.setNombre(productoListaMaterialSustituto.getNombre());
/* 185:175 */           productoSustitutoResponseDto.setUnidad(productoListaMaterialSustituto.getUnidad().getNombre());
/* 186:    */           
/* 187:177 */           productoMaterialResponse.setSustituto(productoSustitutoResponseDto);
/* 188:    */           
/* 189:179 */           productoResponse.getListaProductoMaterial().add(productoMaterialResponse);
/* 190:    */         }
/* 191:    */         ProductoMaterial productoMaterial;
/* 192:    */         Producto productoListaMaterial;
/* 193:183 */         if (producto.getCategoriaImpuesto() != null)
/* 194:    */         {
/* 195:184 */           CategoriaImpuesto categoriaImpuesto = this.categoriaImpuestoDao.cargarDetalle(producto.getCategoriaImpuesto().getId());
/* 196:185 */           for (productoMaterial = categoriaImpuesto.getListaImpuesto().iterator(); productoMaterial.hasNext();)
/* 197:    */           {
/* 198:185 */             impuesto = (Impuesto)productoMaterial.next();
/* 199:186 */             for (RangoImpuesto rangoImpuesto : impuesto.getListaRangoImpuesto()) {
/* 200:187 */               if ((!rangoImpuesto.getFechaDesde().after(hoy)) && (
/* 201:188 */                 (rangoImpuesto.getFechaHasta() == null) || (!rangoImpuesto.getFechaHasta().before(hoy))))
/* 202:    */               {
/* 203:189 */                 ImpuestoResponseDto impuestoResponse = new ImpuestoResponseDto();
/* 204:190 */                 impuestoResponse.setIdImpuesto(Integer.valueOf(impuesto.getId()));
/* 205:191 */                 impuestoResponse.setCodigo(impuesto.getCodigo());
/* 206:192 */                 impuestoResponse.setNombre(impuesto.getNombre());
/* 207:193 */                 impuestoResponse.setIdOrganizacion(impuesto.getIdOrganizacion());
/* 208:194 */                 impuestoResponse.setIndicadorAfectaCantidades(impuesto.getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_CANTIDAD_UNIDADES));
/* 209:    */                 
/* 210:196 */                 impuestoResponse.setIndicadorCompra(rangoImpuesto.isIndicadorCompra());
/* 211:197 */                 impuestoResponse.setIndicadorVenta(rangoImpuesto.isIndicadorVenta());
/* 212:198 */                 impuestoResponse.setIndicadorImpuestoIce(false);
/* 213:199 */                 impuestoResponse.setIndicadorImpuestoTributario(impuesto.isIndicadorImpuestoTributario());
/* 214:200 */                 impuestoResponse.setIndicadorNoObjetoIVA(impuesto.isIndicadorNoObjetoIVA());
/* 215:201 */                 impuestoResponse.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/* 216:202 */                 productoResponse.getListaImpuesto().add(impuestoResponse);
/* 217:    */               }
/* 218:    */             }
/* 219:    */           }
/* 220:    */         }
/* 221:    */         Impuesto impuesto;
/* 222:208 */         listaPreciosResponse.setProducto(productoResponse);
/* 223:    */         
/* 224:210 */         boolean encontre = false;
/* 225:211 */         for (ListaPreciosRequestDto listaPreciosRequest : request.getListaListaPrecios()) {
/* 226:212 */           if (listaPreciosResponse.getIdDetalleListaPrecio().equals(listaPreciosRequest.getIdDetalleListaPrecio()))
/* 227:    */           {
/* 228:213 */             encontre = true;
/* 229:214 */             listaPreciosRequest.setRevisado(Boolean.valueOf(true));
/* 230:215 */             if (listaPreciosResponse.getHashCode() == listaPreciosRequest.getHashCode().intValue()) {
/* 231:    */               break;
/* 232:    */             }
/* 233:216 */             response.add(listaPreciosResponse); break;
/* 234:    */           }
/* 235:    */         }
/* 236:221 */         if (!encontre) {
/* 237:222 */           response.add(listaPreciosResponse);
/* 238:    */         }
/* 239:    */       }
/* 240:228 */       for (ListaPreciosRequestDto listaPreciosRequest : request.getListaListaPrecios()) {
/* 241:229 */         if (!listaPreciosRequest.getRevisado().booleanValue())
/* 242:    */         {
/* 243:230 */           ListaPrecioResponseDto listaPreciosResponse = new ListaPrecioResponseDto();
/* 244:231 */           listaPreciosResponse.setIdDetalleListaPrecio(listaPreciosRequest.getIdDetalleListaPrecio());
/* 245:232 */           listaPreciosResponse.setActivo(false);
/* 246:233 */           response.add(listaPreciosResponse);
/* 247:    */         }
/* 248:    */       }
/* 249:237 */       return response;
/* 250:    */     }
/* 251:    */     catch (Exception e)
/* 252:    */     {
/* 253:239 */       e.printStackTrace();
/* 254:240 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { "" });
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   @POST
/* 259:    */   @Path("/obtenerListaCabeceraListaPreciosPorPagina")
/* 260:    */   @Consumes({"application/json"})
/* 261:    */   @Produces({"application/json"})
/* 262:    */   public ListaPaginadaResponseDto obtenerListaCabeceraListaPreciosPorPagina(FiltrosRequestDto request)
/* 263:    */     throws AS2Exception
/* 264:    */   {
/* 265:250 */     String error = null;
/* 266:251 */     this.languageController.setAccesoWeb(false);
/* 267:252 */     this.languageController.setUrlHost(request.getUrlApp());
/* 268:253 */     ListaPaginadaResponseDto response = new ListaPaginadaResponseDto();
/* 269:    */     try
/* 270:    */     {
/* 271:256 */       Integer startIndex = Integer.valueOf(request.getStartIndex() != null ? request.getStartIndex().intValue() : 0);
/* 272:257 */       Integer pageSize = Integer.valueOf(request.getPageSize() != null ? request.getPageSize().intValue() : 100000);
/* 273:258 */       String sortField = (request.getSortField() != null) && (!request.getSortField().trim().isEmpty()) ? request.getSortField() : "nombre";
/* 274:259 */       Boolean sortAsc = Boolean.valueOf(request.getSortAsc() != null ? request.getSortAsc().booleanValue() : true);
/* 275:    */       
/* 276:261 */       Map<String, String> filtros = new HashMap();
/* 277:262 */       if ((request.getIdOrganizacion() != null) && (!request.getIdOrganizacion().equals(Integer.valueOf(0)))) {
/* 278:263 */         filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 279:    */       }
/* 280:265 */       if ((request.getIdSucursal() != null) && (!request.getIdSucursal().equals(Integer.valueOf(0)))) {
/* 281:266 */         filtros.put("idSucursal", request.getIdSucursal() + "");
/* 282:    */       }
/* 283:268 */       for (FiltroRequestDto filtro : request.getListaFiltro()) {
/* 284:269 */         filtros.put(filtro.getCampo(), filtro.getValor());
/* 285:    */       }
/* 286:272 */       Object listaResponse = new ArrayList();
/* 287:273 */       List<ListaPrecios> listaCabeceraListaPrecio = this.listaPreciosDao.obtenerListaPorPagina(startIndex.intValue(), pageSize.intValue(), sortField, sortAsc.booleanValue(), filtros);
/* 288:274 */       for (ListaPrecios cabeceraListaPrecios : listaCabeceraListaPrecio)
/* 289:    */       {
/* 290:275 */         ListaPrecioResponseDto cabeceraListaPrecioResponse = new ListaPrecioResponseDto();
/* 291:276 */         cabeceraListaPrecioResponse.setIdListaPrecioCabecera(Integer.valueOf(cabeceraListaPrecios.getIdListaPrecios()));
/* 292:277 */         cabeceraListaPrecioResponse.setIdOrganizacion(Integer.valueOf(cabeceraListaPrecios.getIdOrganizacion()));
/* 293:278 */         cabeceraListaPrecioResponse.setIdSucursal(Integer.valueOf(cabeceraListaPrecios.getIdSucursal()));
/* 294:279 */         cabeceraListaPrecioResponse.setNombre(cabeceraListaPrecios.getNombre());
/* 295:    */         
/* 296:281 */         ((List)listaResponse).add(cabeceraListaPrecioResponse);
/* 297:    */       }
/* 298:284 */       response.setSuccsess(true);
/* 299:285 */       response.setRowCount(Integer.valueOf(this.listaPreciosDao.contarPorCriterio(filtros)));
/* 300:286 */       response.setLista((List)listaResponse);
/* 301:    */       
/* 302:288 */       return response;
/* 303:    */     }
/* 304:    */     catch (Exception e)
/* 305:    */     {
/* 306:290 */       e.printStackTrace();
/* 307:291 */       error = e.getMessage();
/* 308:    */       
/* 309:293 */       response.setSuccsess(false);
/* 310:294 */       response.setError(error);
/* 311:    */     }
/* 312:295 */     return response;
/* 313:    */   }
/* 314:    */   
/* 315:    */   @POST
/* 316:    */   @Path("/buscarPrecioProducto")
/* 317:    */   @Consumes({"application/json"})
/* 318:    */   @Produces({"application/json"})
/* 319:    */   public ProcesosResponseDto buscarPrecioProducto(ConsultarPrecioRequestDto request)
/* 320:    */   {
/* 321:305 */     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 322:    */     
/* 323:307 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 324:308 */     String error = "";
/* 325:    */     try
/* 326:    */     {
/* 327:310 */       DetalleVersionListaPrecios dvlp = this.servicioListaPrecios.getDatosVersionListaPrecios(request.getIdListaPrecios().intValue(), request.getIdProducto().intValue(), sdf
/* 328:311 */         .parse(request.getFecha()), null, "");
/* 329:312 */       BigDecimal precio = dvlp.getPrecioUnitario();
/* 330:    */       
/* 331:314 */       response.setSuccsess(true);
/* 332:315 */       response.setResponse(precio);
/* 333:316 */       return response;
/* 334:    */     }
/* 335:    */     catch (ExcepcionAS2 e)
/* 336:    */     {
/* 337:318 */       e.printStackTrace();
/* 338:319 */       error = e.getMessage();
/* 339:    */       
/* 340:321 */       response.setSuccsess(false);
/* 341:322 */       response.setError(error);
/* 342:323 */       return response;
/* 343:    */     }
/* 344:    */     catch (ParseException e)
/* 345:    */     {
/* 346:325 */       e.printStackTrace();
/* 347:326 */       error = e.getMessage();
/* 348:    */       
/* 349:328 */       response.setSuccsess(false);
/* 350:329 */       response.setError(error);
/* 351:    */     }
/* 352:330 */     return response;
/* 353:    */   }
/* 354:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.rest.ListaPreciosServicioRest
 * JD-Core Version:    0.7.0.1
 */