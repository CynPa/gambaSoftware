/*   1:    */ package com.asinfo.as2.rs.inventario.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   7:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.Cliente;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.PresentacionProducto;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.entities.Unidad;
/*  15:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  18:    */ import com.asinfo.as2.rs.datosbase.dto.CargarDetalleRequestDto;
/*  19:    */ import com.asinfo.as2.rs.datosbase.dto.FiltroRequestDto;
/*  20:    */ import com.asinfo.as2.rs.datosbase.dto.FiltrosRequestDto;
/*  21:    */ import com.asinfo.as2.rs.datosbase.dto.ListaPaginadaResponseDto;
/*  22:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  23:    */ import com.asinfo.as2.rs.inventario.dto.ProductoResponseDto;
/*  24:    */ import com.asinfo.as2.rs.ventas.dto.DetalleCalculoImpuestoRequestDto;
/*  25:    */ import com.asinfo.as2.rs.ventas.dto.DetalleCalculoImpuestoResponseDto;
/*  26:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  27:    */ import java.text.SimpleDateFormat;
/*  28:    */ import java.util.ArrayList;
/*  29:    */ import java.util.Date;
/*  30:    */ import java.util.HashMap;
/*  31:    */ import java.util.List;
/*  32:    */ import java.util.Map;
/*  33:    */ import javax.ejb.EJB;
/*  34:    */ import javax.ws.rs.Consumes;
/*  35:    */ import javax.ws.rs.POST;
/*  36:    */ import javax.ws.rs.Path;
/*  37:    */ import javax.ws.rs.Produces;
/*  38:    */ 
/*  39:    */ @Path("/inventario")
/*  40:    */ public class ProductoServicioRest
/*  41:    */ {
/*  42:    */   @EJB
/*  43:    */   private ServicioSucursal servicioSucursal;
/*  44:    */   @EJB
/*  45:    */   private ServicioListaPrecios servicioListaPrecios;
/*  46:    */   @EJB
/*  47:    */   private ServicioProducto servicioProducto;
/*  48:    */   @EJB
/*  49:    */   private ServicioEmpresa servicioEmpresa;
/*  50:    */   @EJB
/*  51:    */   private ServicioPedidoCliente servicioPedidoCliente;
/*  52: 50 */   private LanguageController languageController = new LanguageController();
/*  53:    */   
/*  54:    */   public LanguageController getLanguageController()
/*  55:    */   {
/*  56: 53 */     return this.languageController;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setLanguageController(LanguageController languageController)
/*  60:    */   {
/*  61: 57 */     this.languageController = languageController;
/*  62:    */   }
/*  63:    */   
/*  64:    */   @POST
/*  65:    */   @Path("/obtenerListaProductoPorPagina")
/*  66:    */   @Consumes({"application/json"})
/*  67:    */   @Produces({"application/json"})
/*  68:    */   public ListaPaginadaResponseDto obtenerListaProductoPorPagina(FiltrosRequestDto request)
/*  69:    */     throws AS2Exception
/*  70:    */   {
/*  71: 65 */     String error = null;
/*  72: 66 */     this.languageController.setAccesoWeb(false);
/*  73: 67 */     this.languageController.setUrlHost(request.getUrlApp());
/*  74: 68 */     ListaPaginadaResponseDto response = new ListaPaginadaResponseDto();
/*  75:    */     try
/*  76:    */     {
/*  77: 71 */       Integer startIndex = Integer.valueOf(request.getStartIndex() != null ? request.getStartIndex().intValue() : 0);
/*  78: 72 */       Integer pageSize = Integer.valueOf(request.getPageSize() != null ? request.getPageSize().intValue() : 100000);
/*  79: 73 */       String sortField = (request.getSortField() != null) && (!request.getSortField().trim().isEmpty()) ? request.getSortField() : "nombre";
/*  80: 74 */       Boolean sortAsc = Boolean.valueOf(request.getSortAsc() != null ? request.getSortAsc().booleanValue() : true);
/*  81:    */       
/*  82: 76 */       Map<String, String> filtros = new HashMap();
/*  83: 77 */       if ((request.getIdOrganizacion() != null) && (!request.getIdOrganizacion().equals(Integer.valueOf(0)))) {
/*  84: 78 */         filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/*  85:    */       }
/*  86: 80 */       if ((request.getIdSucursal() != null) && (!request.getIdSucursal().equals(Integer.valueOf(0)))) {
/*  87: 81 */         filtros.put("idSucursal", request.getIdSucursal() + "");
/*  88:    */       }
/*  89: 83 */       for (FiltroRequestDto filtro : request.getListaFiltro()) {
/*  90: 84 */         filtros.put(filtro.getCampo(), filtro.getValor());
/*  91:    */       }
/*  92: 87 */       Object listaResponse = new ArrayList();
/*  93: 88 */       List<Producto> listaProducto = this.servicioProducto.obtenerListaPorPagina(startIndex.intValue(), pageSize.intValue(), sortField, sortAsc.booleanValue(), filtros);
/*  94: 89 */       for (Producto producto : listaProducto)
/*  95:    */       {
/*  96: 90 */         ProductoResponseDto productoResponse = new ProductoResponseDto();
/*  97: 91 */         cargarDatosBasicosProducto(productoResponse, producto);
/*  98: 92 */         ((List)listaResponse).add(productoResponse);
/*  99:    */       }
/* 100: 95 */       response.setSuccsess(true);
/* 101: 96 */       response.setRowCount(Integer.valueOf(this.servicioProducto.contarPorCriterio(filtros)));
/* 102: 97 */       response.setLista((List)listaResponse);
/* 103:    */       
/* 104: 99 */       return response;
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:101 */       e.printStackTrace();
/* 109:102 */       error = e.getMessage();
/* 110:    */       
/* 111:104 */       response.setSuccsess(false);
/* 112:105 */       response.setError(error);
/* 113:    */     }
/* 114:106 */     return response;
/* 115:    */   }
/* 116:    */   
/* 117:    */   @POST
/* 118:    */   @Path("/buscarProductoPorId")
/* 119:    */   @Consumes({"application/json"})
/* 120:    */   @Produces({"application/json"})
/* 121:    */   public ProcesosResponseDto buscarProductoPorId(Integer idProducto)
/* 122:    */     throws AS2Exception
/* 123:    */   {
/* 124:115 */     String error = null;
/* 125:116 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 126:    */     try
/* 127:    */     {
/* 128:119 */       Map<String, String> filtros = new HashMap();
/* 129:120 */       filtros.put("idProducto", "" + idProducto);
/* 130:    */       
/* 131:122 */       List<Producto> listaProducto = this.servicioProducto.obtenerListaPorPagina(0, 1, "idProducto", true, filtros);
/* 132:123 */       ProductoResponseDto productoResponse = null;
/* 133:124 */       if (!listaProducto.isEmpty())
/* 134:    */       {
/* 135:125 */         productoResponse = new ProductoResponseDto();
/* 136:126 */         cargarDatosBasicosProducto(productoResponse, (Producto)listaProducto.get(0));
/* 137:    */       }
/* 138:129 */       response.setSuccsess(true);
/* 139:130 */       response.setResponse(productoResponse);
/* 140:    */       
/* 141:132 */       return response;
/* 142:    */     }
/* 143:    */     catch (Exception e)
/* 144:    */     {
/* 145:134 */       e.printStackTrace();
/* 146:135 */       error = e.getMessage();
/* 147:    */       
/* 148:137 */       response.setSuccsess(false);
/* 149:138 */       response.setError(error);
/* 150:    */     }
/* 151:139 */     return response;
/* 152:    */   }
/* 153:    */   
/* 154:    */   @POST
/* 155:    */   @Path("/cargarDetalleProducto")
/* 156:    */   @Consumes({"application/json"})
/* 157:    */   @Produces({"application/json"})
/* 158:    */   public ProcesosResponseDto cargarDetalleProducto(CargarDetalleRequestDto request)
/* 159:    */     throws AS2Exception
/* 160:    */   {
/* 161:148 */     String error = null;
/* 162:149 */     this.languageController.setAccesoWeb(false);
/* 163:150 */     this.languageController.setUrlHost(request.getUrlApp());
/* 164:151 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 165:    */     try
/* 166:    */     {
/* 167:154 */       Producto producto = this.servicioProducto.cargaDetalle(request.getId().intValue());
/* 168:155 */       ProductoResponseDto productoResponse = new ProductoResponseDto();
/* 169:156 */       cargarDatosBasicosProducto(productoResponse, producto);
/* 170:    */       
/* 171:158 */       response.setSuccsess(true);
/* 172:159 */       response.setResponse(productoResponse);
/* 173:    */       
/* 174:161 */       return response;
/* 175:    */     }
/* 176:    */     catch (Exception e)
/* 177:    */     {
/* 178:163 */       e.printStackTrace();
/* 179:164 */       error = e.getMessage();
/* 180:    */       
/* 181:166 */       response.setSuccsess(false);
/* 182:167 */       response.setError(error);
/* 183:    */     }
/* 184:168 */     return response;
/* 185:    */   }
/* 186:    */   
/* 187:    */   @POST
/* 188:    */   @Path("/calcularImpuestoDetalle")
/* 189:    */   @Consumes({"application/json"})
/* 190:    */   @Produces({"application/json"})
/* 191:    */   public DetalleCalculoImpuestoResponseDto calcularImpuestoDetalle(DetalleCalculoImpuestoRequestDto request)
/* 192:    */     throws AS2Exception
/* 193:    */   {
/* 194:177 */     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 195:178 */     String error = null;
/* 196:179 */     this.languageController.setAccesoWeb(false);
/* 197:180 */     this.languageController.setUrlHost(request.getUrlApp());
/* 198:181 */     DetalleCalculoImpuestoResponseDto response = new DetalleCalculoImpuestoResponseDto();
/* 199:    */     try
/* 200:    */     {
/* 201:184 */       Producto producto = this.servicioProducto.cargaDetalle(request.getIdProducto().intValue(), false);
/* 202:    */       boolean empresaExcentoImpuesto;
/* 203:    */       boolean empresaExcentoImpuesto;
/* 204:186 */       if (request.getEmpresaExcentoImpuesto() == null)
/* 205:    */       {
/* 206:187 */         Empresa empresa = this.servicioEmpresa.buscarPorId(request.getIdEmpresa());
/* 207:188 */         empresaExcentoImpuesto = empresa.getCliente().isExcentoImpuestos();
/* 208:    */       }
/* 209:    */       else
/* 210:    */       {
/* 211:190 */         empresaExcentoImpuesto = request.getEmpresaExcentoImpuesto().booleanValue();
/* 212:    */       }
/* 213:193 */       Sucursal sucursal = this.servicioSucursal.buscarPorId(request.getIdSucursal());
/* 214:194 */       Date fecha = sdf.parse(request.getFecha());
/* 215:    */       
/* 216:196 */       response = this.servicioPedidoCliente.calcularImpuestosDetalleProducto(producto, empresaExcentoImpuesto, fecha, sucursal, request
/* 217:197 */         .getCantidad(), request.getBaseImponibleInicial());
/* 218:198 */       response.setSuccsess(true);
/* 219:    */       
/* 220:200 */       return response;
/* 221:    */     }
/* 222:    */     catch (Exception e)
/* 223:    */     {
/* 224:202 */       e.printStackTrace();
/* 225:203 */       error = e.getMessage();
/* 226:    */       
/* 227:205 */       response.setSuccsess(false);
/* 228:206 */       response.setError(error);
/* 229:    */     }
/* 230:207 */     return response;
/* 231:    */   }
/* 232:    */   
/* 233:    */   private void cargarDatosBasicosProducto(ProductoResponseDto productoResponse, Producto producto)
/* 234:    */   {
/* 235:214 */     productoResponse.setIdProducto(Integer.valueOf(producto.getIdProducto()));
/* 236:215 */     productoResponse.setCodigo(producto.getCodigo());
/* 237:216 */     productoResponse.setCodigoAlterno(producto.getCodigoAlterno());
/* 238:217 */     productoResponse.setCodigoBarras(producto.getCodigoBarras());
/* 239:218 */     productoResponse.setCodigoComercial(producto.getCodigoComercial());
/* 240:219 */     productoResponse.setNombre(producto.getNombre());
/* 241:220 */     if (producto.getSubcategoriaProducto() != null)
/* 242:    */     {
/* 243:221 */       productoResponse.setSubcategoriaProducto(producto.getSubcategoriaProducto().getNombre());
/* 244:222 */       productoResponse.setCategoriaProducto(producto.getSubcategoriaProducto().getCategoriaProducto().getNombre());
/* 245:    */     }
/* 246:224 */     productoResponse.setIdOrganizacion(Integer.valueOf(producto.getIdOrganizacion()));
/* 247:225 */     productoResponse.setIndicadorImpuestos(producto.isIndicadorImpuestos());
/* 248:226 */     productoResponse.setMultiploPedido(producto.getMultiploPedido());
/* 249:227 */     if (producto.getPresentacionProducto() != null)
/* 250:    */     {
/* 251:228 */       productoResponse.setPresentacionProducto(producto.getPresentacionProducto().getNombre());
/* 252:229 */       productoResponse.setCantidadUnidades(producto.getPresentacionProducto().getCantidadUnidades());
/* 253:    */     }
/* 254:231 */     productoResponse.setTipoProducto(producto.getTipoProducto().toString());
/* 255:232 */     if (producto.getUnidad() != null) {
/* 256:233 */       productoResponse.setUnidad(producto.getUnidad().getNombre());
/* 257:    */     }
/* 258:235 */     if (producto.getUnidadVenta() != null) {
/* 259:236 */       productoResponse.setUnidadVenta(producto.getUnidadVenta().getNombre());
/* 260:    */     }
/* 261:239 */     productoResponse.setIndicadorPorcentajeIce(producto.isIndicadorPorcentajeIce());
/* 262:240 */     productoResponse.setCodigoIce(producto.getCodigoIce());
/* 263:241 */     productoResponse.setIce(producto.getIce());
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.rest.ProductoServicioRest
 * JD-Core Version:    0.7.0.1
 */