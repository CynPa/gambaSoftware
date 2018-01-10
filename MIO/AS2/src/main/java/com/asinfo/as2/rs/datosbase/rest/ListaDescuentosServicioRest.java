/*   1:    */ package com.asinfo.as2.rs.datosbase.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*   5:    */ import com.asinfo.as2.entities.DetalleListaDescuentos;
/*   6:    */ import com.asinfo.as2.entities.ListaDescuentos;
/*   7:    */ import com.asinfo.as2.entities.Producto;
/*   8:    */ import com.asinfo.as2.entities.VersionListaDescuentos;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  11:    */ import com.asinfo.as2.rs.datosbase.dto.FiltroRequestDto;
/*  12:    */ import com.asinfo.as2.rs.datosbase.dto.FiltrosRequestDto;
/*  13:    */ import com.asinfo.as2.rs.datosbase.dto.ListaDescuentosOrganizacionRequestDto;
/*  14:    */ import com.asinfo.as2.rs.datosbase.dto.ListaDescuentosRequestDto;
/*  15:    */ import com.asinfo.as2.rs.datosbase.dto.ListaDescuentosResponseDto;
/*  16:    */ import com.asinfo.as2.rs.datosbase.dto.ListaPaginadaResponseDto;
/*  17:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  18:    */ import com.asinfo.as2.rs.datosbase.dto.ValidarDescuentoRequestDto;
/*  19:    */ import java.math.BigDecimal;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Date;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.ws.rs.Consumes;
/*  27:    */ import javax.ws.rs.POST;
/*  28:    */ import javax.ws.rs.Path;
/*  29:    */ import javax.ws.rs.Produces;
/*  30:    */ 
/*  31:    */ @Path("/datosBase")
/*  32:    */ public class ListaDescuentosServicioRest
/*  33:    */ {
/*  34:    */   @EJB
/*  35:    */   private ServicioListaDescuentos servicioListaDescuentos;
/*  36:    */   @EJB
/*  37:    */   private ServicioProducto servicioProducto;
/*  38: 43 */   private LanguageController languageController = new LanguageController();
/*  39:    */   
/*  40:    */   public LanguageController getLanguageController()
/*  41:    */   {
/*  42: 46 */     return this.languageController;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setLanguageController(LanguageController languageController)
/*  46:    */   {
/*  47: 50 */     this.languageController = languageController;
/*  48:    */   }
/*  49:    */   
/*  50:    */   @POST
/*  51:    */   @Path("/consultarListaDescuentos")
/*  52:    */   @Consumes({"application/json"})
/*  53:    */   @Produces({"application/json"})
/*  54:    */   public List<ListaDescuentosResponseDto> consultarListaDescuentos(ListaDescuentosOrganizacionRequestDto request)
/*  55:    */     throws AS2Exception
/*  56:    */   {
/*  57:    */     try
/*  58:    */     {
/*  59: 59 */       Map<String, String> filtros = new HashMap();
/*  60: 60 */       filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/*  61: 61 */       filtros.put("activo", "true");
/*  62:    */       
/*  63: 63 */       List<ListaDescuentos> listaDescuentosOrganizacion = this.servicioListaDescuentos.obtenerListaCombo("predeterminado", false, filtros);
/*  64: 64 */       List<ListaDescuentosResponseDto> response = new ArrayList();
/*  65: 66 */       for (ListaDescuentos descuentos : listaDescuentosOrganizacion)
/*  66:    */       {
/*  67: 67 */         descuentos = this.servicioListaDescuentos.cargarDetalle(descuentos.getId());
/*  68: 69 */         if (descuentos.isIndicadorDescuentoPorProducto())
/*  69:    */         {
/*  70: 70 */           for (VersionListaDescuentos vld : descuentos.getListaVersionesListaDescuentos()) {
/*  71: 71 */             if (vld.isActivo()) {
/*  72: 72 */               for (DetalleListaDescuentos detalleListaDescuentos : vld.getListaDetalleListaDescuentos())
/*  73:    */               {
/*  74: 73 */                 ListaDescuentosResponseDto descuentosResponse = new ListaDescuentosResponseDto();
/*  75: 74 */                 descuentosResponse.setIdListaDescuentos(Integer.valueOf(descuentos.getIdListaDescuentos()));
/*  76: 75 */                 descuentosResponse.setIdOrganizacion(descuentos.getIdListaDescuentos());
/*  77: 76 */                 descuentosResponse.setIdSucursal(descuentos.getIdSucursal());
/*  78: 77 */                 descuentosResponse.setActivo(Boolean.valueOf(descuentos.isActivo()));
/*  79: 78 */                 descuentosResponse.setNombre(descuentos.getNombre());
/*  80: 79 */                 descuentosResponse.setIdDetalleListaDescuentos(Integer.valueOf(detalleListaDescuentos.getIdDetalleListaDescuentos()));
/*  81: 80 */                 descuentosResponse.setIdProducto(Integer.valueOf(detalleListaDescuentos.getProducto().getIdProducto()));
/*  82: 81 */                 descuentosResponse.setPorcentajeDescuentoMaximo(detalleListaDescuentos.getPorcentajeDescuentoMaximo());
/*  83: 82 */                 descuentosResponse.setIndicadorCargaAutomatica(descuentos.isIndicadorCargaAutomatica());
/*  84: 83 */                 descuentosResponse.setIndicadorDescuentoPorProducto(descuentos.isIndicadorDescuentoPorProducto());
/*  85:    */                 
/*  86: 85 */                 boolean encontre = false;
/*  87: 86 */                 for (ListaDescuentosRequestDto descuentoRequest : request.getListaDescuentosRequest()) {
/*  88: 87 */                   if (descuentosResponse.getIdDetalleListaDescuentos().equals(descuentoRequest.getIdDetalleListaDescuentos()))
/*  89:    */                   {
/*  90: 88 */                     encontre = true;
/*  91: 89 */                     descuentoRequest.setRevisado(Boolean.valueOf(true));
/*  92: 90 */                     if (descuentosResponse.getHashCode() == descuentoRequest.getHashCode().intValue()) {
/*  93:    */                       break;
/*  94:    */                     }
/*  95: 91 */                     response.add(descuentosResponse); break;
/*  96:    */                   }
/*  97:    */                 }
/*  98: 96 */                 if (!encontre) {
/*  99: 97 */                   response.add(descuentosResponse);
/* 100:    */                 }
/* 101:    */               }
/* 102:    */             }
/* 103:    */           }
/* 104:    */         }
/* 105:    */         else
/* 106:    */         {
/* 107:103 */           ListaDescuentosResponseDto descuentosResponse = new ListaDescuentosResponseDto();
/* 108:104 */           descuentosResponse.setIdListaDescuentos(Integer.valueOf(descuentos.getIdListaDescuentos()));
/* 109:105 */           descuentosResponse.setIdOrganizacion(descuentos.getIdListaDescuentos());
/* 110:106 */           descuentosResponse.setIdSucursal(descuentos.getIdSucursal());
/* 111:107 */           descuentosResponse.setActivo(Boolean.valueOf(descuentos.isActivo()));
/* 112:108 */           descuentosResponse.setNombre(descuentos.getNombre());
/* 113:109 */           descuentosResponse.setPorcentajeDescuentoMaximo(this.servicioListaDescuentos.getPorcentajeDescuentoMaximoVigente(descuentos, new Date()));
/* 114:110 */           descuentosResponse.setIndicadorCargaAutomatica(descuentos.isIndicadorCargaAutomatica());
/* 115:111 */           descuentosResponse.setIndicadorDescuentoPorProducto(descuentos.isIndicadorDescuentoPorProducto());
/* 116:    */           
/* 117:113 */           boolean encontre = false;
/* 118:114 */           for (ListaDescuentosRequestDto descuentoRequest : request.getListaDescuentosRequest()) {
/* 119:115 */             if (descuentosResponse.getIdListaDescuentos().equals(descuentoRequest.getIdCabeceraListaDescuentos()))
/* 120:    */             {
/* 121:116 */               encontre = true;
/* 122:117 */               descuentoRequest.setRevisado(Boolean.valueOf(true));
/* 123:118 */               if (descuentosResponse.getHashCode() == descuentoRequest.getHashCode().intValue()) {
/* 124:    */                 break;
/* 125:    */               }
/* 126:119 */               response.add(descuentosResponse); break;
/* 127:    */             }
/* 128:    */           }
/* 129:124 */           if (!encontre) {
/* 130:125 */             response.add(descuentosResponse);
/* 131:    */           }
/* 132:    */         }
/* 133:    */       }
/* 134:130 */       for (ListaDescuentosRequestDto descuentosRequest : request.getListaDescuentosRequest()) {
/* 135:131 */         if (!descuentosRequest.getRevisado().booleanValue())
/* 136:    */         {
/* 137:132 */           ListaDescuentosResponseDto descuentoResponse = new ListaDescuentosResponseDto();
/* 138:133 */           descuentoResponse.setIdDetalleListaDescuentos(descuentosRequest.getIdDetalleListaDescuentos());
/* 139:134 */           descuentoResponse.setIdListaDescuentos(descuentosRequest.getIdCabeceraListaDescuentos());
/* 140:135 */           descuentoResponse.setActivo(Boolean.valueOf(false));
/* 141:136 */           response.add(descuentoResponse);
/* 142:    */         }
/* 143:    */       }
/* 144:140 */       return response;
/* 145:    */     }
/* 146:    */     catch (Exception e)
/* 147:    */     {
/* 148:142 */       e.printStackTrace();
/* 149:143 */       throw new AS2Exception(e.getMessage());
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   @POST
/* 154:    */   @Path("/obtenerListaCabeceraListaDescuentoPorPagina")
/* 155:    */   @Consumes({"application/json"})
/* 156:    */   @Produces({"application/json"})
/* 157:    */   public ListaPaginadaResponseDto obtenerListaCabeceraListaDescuentoPorPagina(FiltrosRequestDto request)
/* 158:    */     throws AS2Exception
/* 159:    */   {
/* 160:153 */     String error = null;
/* 161:154 */     this.languageController.setAccesoWeb(false);
/* 162:155 */     this.languageController.setUrlHost(request.getUrlApp());
/* 163:156 */     ListaPaginadaResponseDto response = new ListaPaginadaResponseDto();
/* 164:    */     try
/* 165:    */     {
/* 166:159 */       Integer startIndex = Integer.valueOf(request.getStartIndex() != null ? request.getStartIndex().intValue() : 0);
/* 167:160 */       Integer pageSize = Integer.valueOf(request.getPageSize() != null ? request.getPageSize().intValue() : 100000);
/* 168:161 */       String sortField = (request.getSortField() != null) && (!request.getSortField().trim().isEmpty()) ? request.getSortField() : "nombre";
/* 169:162 */       Boolean sortAsc = Boolean.valueOf(request.getSortAsc() != null ? request.getSortAsc().booleanValue() : true);
/* 170:    */       
/* 171:164 */       Map<String, String> filtros = new HashMap();
/* 172:165 */       if ((request.getIdOrganizacion() != null) && (!request.getIdOrganizacion().equals(Integer.valueOf(0)))) {
/* 173:166 */         filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 174:    */       }
/* 175:168 */       if ((request.getIdSucursal() != null) && (!request.getIdSucursal().equals(Integer.valueOf(0)))) {
/* 176:169 */         filtros.put("idSucursal", request.getIdSucursal() + "");
/* 177:    */       }
/* 178:171 */       for (FiltroRequestDto filtro : request.getListaFiltro()) {
/* 179:172 */         filtros.put(filtro.getCampo(), filtro.getValor());
/* 180:    */       }
/* 181:175 */       Object listaResponse = new ArrayList();
/* 182:176 */       List<ListaDescuentos> listaCabeceraListaDescuento = this.servicioListaDescuentos.obtenerListaPorPagina(startIndex.intValue(), pageSize.intValue(), sortField, sortAsc
/* 183:177 */         .booleanValue(), filtros);
/* 184:178 */       for (ListaDescuentos cabeceraListaDescuento : listaCabeceraListaDescuento)
/* 185:    */       {
/* 186:179 */         ListaDescuentosResponseDto descuentosResponse = new ListaDescuentosResponseDto();
/* 187:180 */         descuentosResponse.setIdListaDescuentos(Integer.valueOf(cabeceraListaDescuento.getIdListaDescuentos()));
/* 188:181 */         descuentosResponse.setIdOrganizacion(cabeceraListaDescuento.getIdListaDescuentos());
/* 189:182 */         descuentosResponse.setIdSucursal(cabeceraListaDescuento.getIdSucursal());
/* 190:183 */         descuentosResponse.setActivo(Boolean.valueOf(cabeceraListaDescuento.isActivo()));
/* 191:184 */         descuentosResponse.setNombre(cabeceraListaDescuento.getNombre());
/* 192:185 */         descuentosResponse.setPorcentajeDescuentoMaximo(this.servicioListaDescuentos.getPorcentajeDescuentoMaximoVigente(cabeceraListaDescuento, new Date()));
/* 193:186 */         descuentosResponse.setIndicadorCargaAutomatica(cabeceraListaDescuento.isIndicadorCargaAutomatica());
/* 194:187 */         descuentosResponse.setIndicadorDescuentoPorProducto(cabeceraListaDescuento.isIndicadorDescuentoPorProducto());
/* 195:    */         
/* 196:189 */         ((List)listaResponse).add(descuentosResponse);
/* 197:    */       }
/* 198:192 */       response.setSuccsess(true);
/* 199:193 */       response.setRowCount(Integer.valueOf(this.servicioListaDescuentos.contarPorCriterio(filtros)));
/* 200:194 */       response.setLista((List)listaResponse);
/* 201:    */       
/* 202:196 */       return response;
/* 203:    */     }
/* 204:    */     catch (Exception e)
/* 205:    */     {
/* 206:198 */       e.printStackTrace();
/* 207:199 */       error = e.getMessage();
/* 208:    */       
/* 209:201 */       response.setSuccsess(false);
/* 210:202 */       response.setError(error);
/* 211:    */     }
/* 212:203 */     return response;
/* 213:    */   }
/* 214:    */   
/* 215:    */   @POST
/* 216:    */   @Path("/validarDescuento")
/* 217:    */   @Consumes({"application/json"})
/* 218:    */   @Produces({"application/json"})
/* 219:    */   public ProcesosResponseDto validarDescuento(ValidarDescuentoRequestDto request)
/* 220:    */   {
/* 221:213 */     this.languageController.setAccesoWeb(false);
/* 222:214 */     this.languageController.setUrlHost(request.getUrlApp());
/* 223:215 */     String error = "";
/* 224:216 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 225:217 */     BigDecimal porcentajeMaximoDescuento = BigDecimal.ZERO;
/* 226:219 */     if (!request.getIdListaDescuentos().equals(Integer.valueOf(0)))
/* 227:    */     {
/* 228:220 */       ListaDescuentos descuentos = this.servicioListaDescuentos.cargarDetalle(request.getIdListaDescuentos().intValue());
/* 229:222 */       if (!descuentos.isIndicadorDescuentoPorProducto())
/* 230:    */       {
/* 231:223 */         porcentajeMaximoDescuento = this.servicioListaDescuentos.getPorcentajeDescuentoMaximoVigente(descuentos, new Date());
/* 232:    */       }
/* 233:    */       else
/* 234:    */       {
/* 235:225 */         Producto producto = new Producto();
/* 236:226 */         producto.setIdProducto(request.getIdProducto().intValue());
/* 237:227 */         DetalleListaDescuentos detalleListaDescuentos = this.servicioListaDescuentos.getDatosListaDescuentosPorProducto(descuentos, producto);
/* 238:228 */         if (detalleListaDescuentos != null) {
/* 239:229 */           porcentajeMaximoDescuento = detalleListaDescuentos.getPorcentajeDescuentoMaximo();
/* 240:    */         } else {
/* 241:231 */           porcentajeMaximoDescuento = BigDecimal.ZERO;
/* 242:    */         }
/* 243:    */       }
/* 244:    */     }
/* 245:236 */     if (request.getPorcentajeDescuentoLinea().compareTo(porcentajeMaximoDescuento) > 0)
/* 246:    */     {
/* 247:237 */       error = getLanguageController().getMensaje("msg_error_porcentaje_descuento_maximo");
/* 248:238 */       response.setSuccsess(false);
/* 249:239 */       response.setError(error);
/* 250:240 */       response.setResponse(porcentajeMaximoDescuento);
/* 251:241 */       return response;
/* 252:    */     }
/* 253:243 */     response.setSuccsess(true);
/* 254:244 */     response.setResponse(request.getPorcentajeDescuentoLinea());
/* 255:245 */     return response;
/* 256:    */   }
/* 257:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.rest.ListaDescuentosServicioRest
 * JD-Core Version:    0.7.0.1
 */