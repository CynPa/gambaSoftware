/*   1:    */ package com.asinfo.as2.rs.datosbase.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCaja;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   7:    */ import com.asinfo.as2.controller.LanguageController;
/*   8:    */ import com.asinfo.as2.entities.Bodega;
/*   9:    */ import com.asinfo.as2.entities.Caja;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  15:    */ import com.asinfo.as2.rs.datosbase.dto.BodegaResponseDto;
/*  16:    */ import com.asinfo.as2.rs.datosbase.dto.CajaResponseDto;
/*  17:    */ import com.asinfo.as2.rs.datosbase.dto.ConfiguracionResponseDto;
/*  18:    */ import com.asinfo.as2.rs.datosbase.dto.FiltroRequestDto;
/*  19:    */ import com.asinfo.as2.rs.datosbase.dto.FiltrosRequestDto;
/*  20:    */ import com.asinfo.as2.rs.datosbase.dto.ListaOrganizacionRequestDto;
/*  21:    */ import com.asinfo.as2.rs.datosbase.dto.ListaPaginadaResponseDto;
/*  22:    */ import com.asinfo.as2.rs.datosbase.dto.OrganizacionRequestDto;
/*  23:    */ import com.asinfo.as2.rs.seguridad.dto.OrganizacionResponseDto;
/*  24:    */ import com.asinfo.as2.rs.seguridad.dto.PuntoVentaResponseDto;
/*  25:    */ import com.asinfo.as2.rs.seguridad.dto.SucursalResponseDto;
/*  26:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  27:    */ import java.util.ArrayList;
/*  28:    */ import java.util.HashMap;
/*  29:    */ import java.util.Iterator;
/*  30:    */ import java.util.List;
/*  31:    */ import java.util.Map;
/*  32:    */ import javax.ejb.EJB;
/*  33:    */ import javax.ws.rs.Consumes;
/*  34:    */ import javax.ws.rs.POST;
/*  35:    */ import javax.ws.rs.Path;
/*  36:    */ import javax.ws.rs.Produces;
/*  37:    */ 
/*  38:    */ @Path("/datosBase")
/*  39:    */ public class OrganizacionServicioRest
/*  40:    */ {
/*  41:    */   @EJB
/*  42:    */   private ServicioOrganizacion servicioOrganizacion;
/*  43:    */   @EJB
/*  44:    */   private ServicioSucursal servicioSucursal;
/*  45:    */   @EJB
/*  46:    */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  47:    */   @EJB
/*  48:    */   private ServicioBodega servicioBodega;
/*  49:    */   @EJB
/*  50:    */   private ServicioCaja servicioCaja;
/*  51: 55 */   private LanguageController languageController = new LanguageController();
/*  52:    */   
/*  53:    */   public LanguageController getLanguageController()
/*  54:    */   {
/*  55: 58 */     return this.languageController;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setLanguageController(LanguageController languageController)
/*  59:    */   {
/*  60: 62 */     this.languageController = languageController;
/*  61:    */   }
/*  62:    */   
/*  63:    */   @POST
/*  64:    */   @Path("/consultarOrganizacion")
/*  65:    */   @Consumes({"application/json"})
/*  66:    */   @Produces({"application/json"})
/*  67:    */   public List<OrganizacionResponseDto> consultarOrganizacion(ListaOrganizacionRequestDto request)
/*  68:    */     throws AS2Exception
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72: 71 */       Map<String, String> filtros = new HashMap();
/*  73:    */       
/*  74: 73 */       List<Organizacion> listaOrganizacion = this.servicioOrganizacion.obtenerListaCombo("identificacion", false, filtros);
/*  75:    */       
/*  76: 75 */       List<OrganizacionResponseDto> response = new ArrayList();
/*  77: 77 */       for (Organizacion organizacion : listaOrganizacion)
/*  78:    */       {
/*  79: 78 */         OrganizacionResponseDto organizacionResponse = new OrganizacionResponseDto();
/*  80: 79 */         organizacionResponse.setIdOrganizacion(Integer.valueOf(organizacion.getId()));
/*  81: 80 */         organizacionResponse.setIdentificacion(organizacion.getIdentificacion());
/*  82: 81 */         organizacionResponse.setRazonSocial(organizacion.getRazonSocial());
/*  83: 82 */         organizacionResponse.setRepresentanteLegal(organizacion.getRepresentanteLegal());
/*  84: 83 */         organizacionResponse.setImagen(organizacion.getImagen());
/*  85: 84 */         organizacionResponse.setActivo(Boolean.valueOf(true));
/*  86: 85 */         organizacionResponse.setListaSucursal(new ArrayList());
/*  87:    */         
/*  88: 87 */         Map<String, String> filtrosOrganizacion = new HashMap();
/*  89: 88 */         filtrosOrganizacion.put("idOrganizacion", organizacion.getIdOrganizacion() + "");
/*  90: 89 */         filtrosOrganizacion.put("activo", "true");
/*  91: 91 */         for (Iterator localIterator2 = this.servicioSucursal.obtenerListaCombo("predeterminado", false, filtrosOrganizacion).iterator(); localIterator2.hasNext();)
/*  92:    */         {
/*  93: 91 */           sucursal = (Sucursal)localIterator2.next();
/*  94: 92 */           SucursalResponseDto sucursalResponse = new SucursalResponseDto();
/*  95: 93 */           sucursalResponse.setIdSucursal(Integer.valueOf(sucursal.getId()));
/*  96: 94 */           sucursalResponse.setIdOrganizacion(Integer.valueOf(sucursal.getIdOrganizacion()));
/*  97: 95 */           sucursalResponse.setCodigo(sucursal.getCodigo());
/*  98: 96 */           sucursalResponse.setNombre(sucursal.getNombre());
/*  99: 97 */           sucursalResponse.setActivo(Boolean.valueOf(sucursal.isActivo()));
/* 100: 98 */           sucursalResponse.setPredeterminado(Boolean.valueOf(sucursal.isPredeterminado()));
/* 101: 99 */           sucursalResponse.setListaPuntoVenta(new ArrayList());
/* 102:100 */           sucursalResponse.setListaBodega(new ArrayList());
/* 103:    */           
/* 104:102 */           Map<String, String> filtrosSucursal = new HashMap();
/* 105:103 */           filtrosSucursal.put("idOrganizacion", String.valueOf(sucursal.getIdOrganizacion()));
/* 106:104 */           filtrosSucursal.put("sucursal.idSucursal", String.valueOf(sucursal.getIdSucursal()));
/* 107:105 */           filtrosSucursal.put("activo", "true");
/* 108:107 */           for (PuntoDeVenta puntoVenta : this.servicioPuntoDeVenta.obtenerListaCombo("predeterminado", false, filtrosSucursal))
/* 109:    */           {
/* 110:109 */             puntoVentaResponse = new PuntoVentaResponseDto();
/* 111:110 */             puntoVentaResponse.setIdOrganizacion(Integer.valueOf(puntoVenta.getIdOrganizacion()));
/* 112:111 */             puntoVentaResponse.setIdPuntoVenta(Integer.valueOf(puntoVenta.getId()));
/* 113:112 */             puntoVentaResponse.setCodigo(puntoVenta.getCodigo());
/* 114:113 */             puntoVentaResponse.setNombre(puntoVenta.getNombre());
/* 115:114 */             puntoVentaResponse.setActivo(Boolean.valueOf(puntoVenta.isActivo()));
/* 116:115 */             puntoVentaResponse.setPredeterminado(Boolean.valueOf(puntoVenta.isPredeterminado()));
/* 117:116 */             puntoVentaResponse.setIdSucursal(Integer.valueOf(puntoVenta.getSucursal().getIdSucursal()));
/* 118:117 */             puntoVentaResponse.setListaCaja(new ArrayList());
/* 119:118 */             sucursalResponse.getListaPuntoVenta().add(puntoVentaResponse);
/* 120:    */             
/* 121:120 */             Map<String, String> filtrosCaja = new HashMap();
/* 122:121 */             filtrosCaja.put("idOrganizacion", String.valueOf(puntoVenta.getIdOrganizacion()));
/* 123:122 */             filtrosCaja.put("puntoDeVenta.idPuntoDeVenta", String.valueOf(puntoVenta.getIdPuntoDeVenta()));
/* 124:123 */             filtrosCaja.put("activo", "true");
/* 125:125 */             for (Caja caja : this.servicioCaja.obtenerListaCombo("predeterminado", false, filtrosCaja))
/* 126:    */             {
/* 127:126 */               CajaResponseDto cajaResponse = new CajaResponseDto();
/* 128:127 */               cajaResponse.setIdOrganizacion(Integer.valueOf(caja.getIdOrganizacion()));
/* 129:128 */               cajaResponse.setIdSucursal(Integer.valueOf(caja.getIdSucursal()));
/* 130:129 */               cajaResponse.setIdCaja(Integer.valueOf(caja.getId()));
/* 131:130 */               cajaResponse.setCodigo(caja.getCodigo());
/* 132:131 */               cajaResponse.setNombre(caja.getNombre());
/* 133:132 */               cajaResponse.setActivo(Boolean.valueOf(caja.isActivo()));
/* 134:133 */               cajaResponse.setPredeterminado(Boolean.valueOf(caja.isPredeterminado()));
/* 135:134 */               cajaResponse.setIdPuntoDeVenta(Integer.valueOf(caja.getPuntoDeVenta().getIdPuntoDeVenta()));
/* 136:135 */               puntoVentaResponse.getListaCaja().add(cajaResponse);
/* 137:    */             }
/* 138:    */           }
/* 139:    */           PuntoVentaResponseDto puntoVentaResponse;
/* 140:139 */           for (Bodega bodega : this.servicioBodega.obtenerListaCombo("predeterminado", false, filtrosSucursal))
/* 141:    */           {
/* 142:140 */             BodegaResponseDto bodegaResponse = new BodegaResponseDto();
/* 143:141 */             bodegaResponse.setIdOrganizacion(Integer.valueOf(bodega.getIdOrganizacion()));
/* 144:142 */             bodegaResponse.setIdBodega(Integer.valueOf(bodega.getId()));
/* 145:143 */             bodegaResponse.setCodigo(bodega.getCodigo());
/* 146:144 */             bodegaResponse.setNombre(bodega.getNombre());
/* 147:145 */             bodegaResponse.setActivo(Boolean.valueOf(bodega.isActivo()));
/* 148:146 */             bodegaResponse.setPredeterminado(Boolean.valueOf(bodega.isPredeterminado()));
/* 149:147 */             bodegaResponse.setIdSucursal(Integer.valueOf(bodega.getSucursal().getIdSucursal()));
/* 150:148 */             sucursalResponse.getListaBodega().add(bodegaResponse);
/* 151:    */           }
/* 152:151 */           organizacionResponse.getListaSucursal().add(sucursalResponse);
/* 153:    */         }
/* 154:    */         Sucursal sucursal;
/* 155:154 */         boolean encontre = false;
/* 156:155 */         for (OrganizacionRequestDto organizacionRequest : request.getListaOrganizacionRequest()) {
/* 157:156 */           if (organizacionResponse.getIdOrganizacion().equals(organizacionRequest.getIdOrganizacion()))
/* 158:    */           {
/* 159:157 */             encontre = true;
/* 160:158 */             organizacionRequest.setRevisado(Boolean.valueOf(true));
/* 161:159 */             if (organizacionResponse.getHashCode() == organizacionRequest.getHashCode().intValue()) {
/* 162:    */               break;
/* 163:    */             }
/* 164:160 */             response.add(organizacionResponse); break;
/* 165:    */           }
/* 166:    */         }
/* 167:165 */         if (!encontre) {
/* 168:166 */           response.add(organizacionResponse);
/* 169:    */         }
/* 170:    */       }
/* 171:170 */       for (OrganizacionRequestDto organizacionRequest : request.getListaOrganizacionRequest()) {
/* 172:171 */         if (!organizacionRequest.getRevisado().booleanValue())
/* 173:    */         {
/* 174:172 */           OrganizacionResponseDto organizacionResponse = new OrganizacionResponseDto();
/* 175:173 */           organizacionResponse.setIdOrganizacion(organizacionRequest.getIdOrganizacion());
/* 176:174 */           organizacionResponse.setActivo(Boolean.valueOf(false));
/* 177:175 */           response.add(organizacionResponse);
/* 178:    */         }
/* 179:    */       }
/* 180:179 */       return response;
/* 181:    */     }
/* 182:    */     catch (Exception e)
/* 183:    */     {
/* 184:181 */       e.printStackTrace();
/* 185:182 */       throw new AS2Exception(e.getMessage());
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   @POST
/* 190:    */   @Path("/consultarParametros")
/* 191:    */   @Consumes({"application/json"})
/* 192:    */   @Produces({"application/json"})
/* 193:    */   public ConfiguracionResponseDto consultarParametros(Integer idOrganizacion)
/* 194:    */     throws AS2Exception
/* 195:    */   {
/* 196:    */     try
/* 197:    */     {
/* 198:193 */       ConfiguracionResponseDto response = new ConfiguracionResponseDto();
/* 199:194 */       response.setIdOrganizacion(idOrganizacion);
/* 200:195 */       response.setIdEmpresa(ParametrosSistema.getClienteGenerico(idOrganizacion.intValue()));
/* 201:196 */       response.setNumeroDecimalesPrecioVenta(ParametrosSistema.getNumeroDecimalesPrecioVenta(idOrganizacion.intValue()));
/* 202:197 */       response.setFormatoDinero(ParametrosSistema.getFormatoDinero(idOrganizacion.intValue()));
/* 203:198 */       response.setFormatoFecha(ParametrosSistema.getFormatoFecha(idOrganizacion.intValue()));
/* 204:199 */       response.setCierreCajaPorDenominacionFormaCobro(ParametrosSistema.getCierreCajaPorDenominacionFormaCobro(idOrganizacion.intValue()));
/* 205:    */       
/* 206:201 */       return response;
/* 207:    */     }
/* 208:    */     catch (Exception e)
/* 209:    */     {
/* 210:205 */       e.printStackTrace();
/* 211:206 */       throw new AS2Exception(e.getMessage());
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   @POST
/* 216:    */   @Path("/obtenerListaOrganizacionPorPagina")
/* 217:    */   @Consumes({"application/json"})
/* 218:    */   @Produces({"application/json"})
/* 219:    */   public ListaPaginadaResponseDto obtenerListaTipoContactoPorPagina(FiltrosRequestDto request)
/* 220:    */     throws AS2Exception
/* 221:    */   {
/* 222:215 */     String error = null;
/* 223:216 */     this.languageController.setAccesoWeb(false);
/* 224:217 */     this.languageController.setUrlHost(request.getUrlApp());
/* 225:218 */     ListaPaginadaResponseDto response = new ListaPaginadaResponseDto();
/* 226:    */     try
/* 227:    */     {
/* 228:221 */       Integer startIndex = Integer.valueOf(request.getStartIndex() != null ? request.getStartIndex().intValue() : 0);
/* 229:222 */       Integer pageSize = Integer.valueOf(request.getPageSize() != null ? request.getPageSize().intValue() : 100000);
/* 230:223 */       String sortField = (request.getSortField() != null) && (!request.getSortField().trim().isEmpty()) ? request.getSortField() : "nombre";
/* 231:224 */       Boolean sortAsc = Boolean.valueOf(request.getSortAsc() != null ? request.getSortAsc().booleanValue() : true);
/* 232:    */       
/* 233:226 */       Map<String, String> filtros = new HashMap();
/* 234:227 */       for (FiltroRequestDto filtro : request.getListaFiltro()) {
/* 235:228 */         filtros.put(filtro.getCampo(), filtro.getValor());
/* 236:    */       }
/* 237:231 */       Object listaResponse = new ArrayList();
/* 238:232 */       List<Organizacion> listaOrganizacion = this.servicioOrganizacion.obtenerListaPorPagina(startIndex.intValue(), pageSize.intValue(), sortField, sortAsc.booleanValue(), filtros);
/* 239:233 */       for (Organizacion organizacion : listaOrganizacion)
/* 240:    */       {
/* 241:234 */         OrganizacionResponseDto organizacionResponse = new OrganizacionResponseDto();
/* 242:235 */         organizacionResponse.setIdOrganizacion(Integer.valueOf(organizacion.getId()));
/* 243:236 */         organizacionResponse.setIdentificacion(organizacion.getIdentificacion());
/* 244:237 */         organizacionResponse.setRazonSocial(organizacion.getRazonSocial());
/* 245:238 */         organizacionResponse.setRepresentanteLegal(organizacion.getRepresentanteLegal());
/* 246:239 */         organizacionResponse.setImagen(organizacion.getImagen());
/* 247:240 */         organizacionResponse.setActivo(Boolean.valueOf(true));
/* 248:241 */         organizacionResponse.setListaSucursal(new ArrayList());
/* 249:    */         
/* 250:243 */         ((List)listaResponse).add(organizacionResponse);
/* 251:    */       }
/* 252:246 */       response.setSuccsess(true);
/* 253:247 */       response.setRowCount(Integer.valueOf(this.servicioOrganizacion.contarPorCriterio(filtros)));
/* 254:248 */       response.setLista((List)listaResponse);
/* 255:    */       
/* 256:250 */       return response;
/* 257:    */     }
/* 258:    */     catch (Exception e)
/* 259:    */     {
/* 260:252 */       e.printStackTrace();
/* 261:253 */       error = e.getMessage();
/* 262:    */       
/* 263:255 */       response.setSuccsess(false);
/* 264:256 */       response.setError(error);
/* 265:    */     }
/* 266:257 */     return response;
/* 267:    */   }
/* 268:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.rest.OrganizacionServicioRest
 * JD-Core Version:    0.7.0.1
 */