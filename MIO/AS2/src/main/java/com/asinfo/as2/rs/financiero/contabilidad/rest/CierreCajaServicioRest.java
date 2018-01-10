/*   1:    */ package com.asinfo.as2.rs.financiero.contabilidad.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.caja.procesos.servicio.ServicioCierreCaja;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.entities.CierreCaja;
/*   7:    */ import com.asinfo.as2.entities.DenominacionFormaCobro;
/*   8:    */ import com.asinfo.as2.entities.DetalleCierreCaja;
/*   9:    */ import com.asinfo.as2.entities.DetalleDenominacionFormaCobro;
/*  10:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  13:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  14:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  17:    */ import com.asinfo.as2.rs.financiero.contabilidad.dto.CierreCajaRequestDto;
/*  18:    */ import com.asinfo.as2.rs.financiero.contabilidad.dto.ConsultarCierreCajaRequestDto;
/*  19:    */ import com.asinfo.as2.rs.financiero.contabilidad.dto.DetalleCierreCajaRequestDto;
/*  20:    */ import com.asinfo.as2.rs.financiero.contabilidad.dto.DetalleDenominacionFormaCobroRequestDto;
/*  21:    */ import com.asinfo.as2.rs.financiero.contabilidad.dto.EstadoCierreCajaResponseDto;
/*  22:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  23:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  24:    */ import java.text.ParseException;
/*  25:    */ import java.text.SimpleDateFormat;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.HashMap;
/*  28:    */ import java.util.Iterator;
/*  29:    */ import java.util.List;
/*  30:    */ import java.util.Map;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.ws.rs.Consumes;
/*  33:    */ import javax.ws.rs.POST;
/*  34:    */ import javax.ws.rs.Path;
/*  35:    */ import javax.ws.rs.Produces;
/*  36:    */ 
/*  37:    */ @Path("/contabilidad")
/*  38:    */ public class CierreCajaServicioRest
/*  39:    */ {
/*  40:    */   @EJB
/*  41:    */   private transient ServicioSucursal servicioSucursal;
/*  42:    */   @EJB
/*  43:    */   private transient ServicioUsuario servicioUsuario;
/*  44:    */   @EJB
/*  45:    */   private transient ServicioCierreCaja servicioCierreCaja;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioGenerico<DetalleFormaCobro> servicioDetalleFormaCobro;
/*  48:    */   @EJB
/*  49:    */   private transient ServicioGenerico<DenominacionFormaCobro> servicioDenominacionFormaCobro;
/*  50: 53 */   private LanguageController languageController = new LanguageController();
/*  51:    */   
/*  52:    */   public LanguageController getLanguageController()
/*  53:    */   {
/*  54: 56 */     return this.languageController;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setLanguageController(LanguageController languageController)
/*  58:    */   {
/*  59: 60 */     this.languageController = languageController;
/*  60:    */   }
/*  61:    */   
/*  62:    */   @POST
/*  63:    */   @Path("/crearCierreCaja")
/*  64:    */   @Consumes({"application/json"})
/*  65:    */   @Produces({"application/json"})
/*  66:    */   public ProcesosResponseDto crearCierreCaja(CierreCajaRequestDto request)
/*  67:    */     throws AS2Exception
/*  68:    */   {
/*  69: 68 */     ProcesosResponseDto response = new ProcesosResponseDto();
/*  70: 69 */     String error = null;
/*  71: 70 */     this.languageController.setAccesoWeb(false);
/*  72:    */     try
/*  73:    */     {
/*  74: 73 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/*  75:    */       
/*  76: 75 */       int idOrganizacion = request.getIdOrganizacion().intValue();
/*  77: 76 */       Sucursal sucursal = this.servicioSucursal.buscarPorId(request.getIdSucursal());
/*  78: 77 */       EntidadUsuario usuario = this.servicioUsuario.buscarPorId(request.getIdUsuario());
/*  79:    */       
/*  80: 79 */       CierreCaja cierreCaja = new CierreCaja();
/*  81: 80 */       cierreCaja.setUsuarioCreacion(usuario.getNombreUsuario());
/*  82: 81 */       cierreCaja.setUsuario(usuario);
/*  83: 82 */       cierreCaja.setIdOrganizacion(idOrganizacion);
/*  84: 83 */       cierreCaja.setValor(request.getValor());
/*  85: 84 */       cierreCaja.setIdSucursal(sucursal.getId());
/*  86: 85 */       cierreCaja.setFechaHasta(sdf.parse(request.getFechaHasta()));
/*  87: 86 */       cierreCaja.setEstado(Estado.ELABORADO);
/*  88: 87 */       cierreCaja.setIdDispositivoSincronizacion(request.getIdDispositivoSincronizacion());
/*  89: 88 */       cierreCaja.setListaDetalleCierreCaja(new ArrayList());
/*  90: 89 */       if (request.getTotalUsuario() != null) {
/*  91: 90 */         cierreCaja.setTotalUsuario(request.getTotalUsuario());
/*  92:    */       }
/*  93: 93 */       for (DetalleCierreCajaRequestDto detalle : request.getListaDetalleCierreCaja())
/*  94:    */       {
/*  95: 94 */         DetalleCierreCaja detalleCierreCaja = new DetalleCierreCaja();
/*  96: 95 */         detalleCierreCaja.setUsuarioCreacion(usuario.getNombreUsuario());
/*  97: 96 */         detalleCierreCaja.setCierreCaja(cierreCaja);
/*  98: 97 */         detalleCierreCaja.setIdOrganizacion(idOrganizacion);
/*  99: 98 */         detalleCierreCaja.setValor(detalle.getValor());
/* 100: 99 */         detalleCierreCaja.setIdSucursal(sucursal.getId());
/* 101:100 */         detalleCierreCaja.setIdDispositivoSincronizacion(detalle.getIdDispositivoSincronizacion());
/* 102:    */         
/* 103:102 */         DetalleFormaCobro detalleFormaCobro = (DetalleFormaCobro)this.servicioDetalleFormaCobro.cargarDetalle(DetalleFormaCobro.class, detalle
/* 104:103 */           .getIdDetalleFormaCobro(), new ArrayList());
/* 105:104 */         detalleCierreCaja.setDetalleFormaCobro(detalleFormaCobro);
/* 106:    */         
/* 107:106 */         cierreCaja.getListaDetalleCierreCaja().add(detalleCierreCaja);
/* 108:    */       }
/* 109:109 */       for (DetalleDenominacionFormaCobroRequestDto detalle : request.getListaDetalleDenominacionFormaCobro())
/* 110:    */       {
/* 111:110 */         detalleDenominacion = new DetalleDenominacionFormaCobro();
/* 112:111 */         detalleDenominacion.setUsuarioCreacion(usuario.getNombreUsuario());
/* 113:112 */         detalleDenominacion.setCierreCaja(cierreCaja);
/* 114:113 */         detalleDenominacion.setIdOrganizacion(idOrganizacion);
/* 115:114 */         detalleDenominacion.setIdSucursal(sucursal.getId());
/* 116:115 */         detalleDenominacion.setIdDispositivoSincronizacion(detalle.getIdDispositivoSincronizacion());
/* 117:116 */         detalleDenominacion.setCantidad(detalle.getCantidad());
/* 118:117 */         detalleDenominacion.setTotal(detalle.getTotal());
/* 119:    */         
/* 120:119 */         DenominacionFormaCobro denominacionFormaCobro = (DenominacionFormaCobro)this.servicioDenominacionFormaCobro.cargarDetalle(DenominacionFormaCobro.class, detalle
/* 121:120 */           .getIdDenominacionFormaCobro(), new ArrayList());
/* 122:121 */         detalleDenominacion.setDenominacionFormaCobro(denominacionFormaCobro);
/* 123:    */         
/* 124:123 */         cierreCaja.getListDetalleDenominacionFormaCobro().add(detalleDenominacion);
/* 125:    */       }
/* 126:    */       DetalleDenominacionFormaCobro detalleDenominacion;
/* 127:126 */       this.servicioCierreCaja.guardar(cierreCaja);
/* 128:    */       
/* 129:128 */       request.setIdCierreCaja(Integer.valueOf(cierreCaja.getId()));
/* 130:129 */       request.setNumeroCierreCajaAs2(cierreCaja.getNumero());
/* 131:130 */       for (??? = cierreCaja.getListaDetalleCierreCaja().iterator(); ???.hasNext();)
/* 132:    */       {
/* 133:130 */         detalle = (DetalleCierreCaja)???.next();
/* 134:131 */         for (DetalleCierreCajaRequestDto detalleCierreCajaRequestDto : request.getListaDetalleCierreCaja()) {
/* 135:132 */           if (detalle.getIdDispositivoSincronizacion().equals(detalleCierreCajaRequestDto.getIdDispositivoSincronizacion())) {
/* 136:133 */             detalleCierreCajaRequestDto.setIdDetalleCierreCaja(detalle.getId());
/* 137:    */           }
/* 138:    */         }
/* 139:    */       }
/* 140:    */       DetalleCierreCaja detalle;
/* 141:137 */       for (??? = cierreCaja.getListDetalleDenominacionFormaCobro().iterator(); ???.hasNext();)
/* 142:    */       {
/* 143:137 */         detalle = (DetalleDenominacionFormaCobro)???.next();
/* 144:138 */         for (DetalleDenominacionFormaCobroRequestDto detalleDenominacionRequestDto : request.getListaDetalleDenominacionFormaCobro()) {
/* 145:139 */           if (detalle.getIdDispositivoSincronizacion().equals(detalleDenominacionRequestDto.getIdDispositivoSincronizacion())) {
/* 146:140 */             detalleDenominacionRequestDto.setIdDetalleDenominacionFormaCobro(detalle.getId());
/* 147:    */           }
/* 148:    */         }
/* 149:    */       }
/* 150:    */       DetalleDenominacionFormaCobro detalle;
/* 151:145 */       response.setSuccsess(true);
/* 152:146 */       response.setResponse(request);
/* 153:147 */       return response;
/* 154:    */     }
/* 155:    */     catch (ExcepcionAS2 e)
/* 156:    */     {
/* 157:149 */       e.printStackTrace();
/* 158:150 */       error = e.getCodigoExcepcion() + " | " + e.getMessage();
/* 159:    */     }
/* 160:    */     catch (ParseException e)
/* 161:    */     {
/* 162:152 */       e.printStackTrace();
/* 163:153 */       error = "Mal formado el formato de la fecha";
/* 164:    */     }
/* 165:    */     catch (Exception e)
/* 166:    */     {
/* 167:155 */       e.printStackTrace();
/* 168:156 */       error = e.getMessage();
/* 169:    */     }
/* 170:158 */     response.setError(error);
/* 171:159 */     response.setSuccsess(false);
/* 172:160 */     return response;
/* 173:    */   }
/* 174:    */   
/* 175:    */   @POST
/* 176:    */   @Path("/anularCierreCaja")
/* 177:    */   @Consumes({"application/json"})
/* 178:    */   @Produces({"application/json"})
/* 179:    */   public ProcesosResponseDto anularCierreCaja(int idCierreCaja)
/* 180:    */     throws AS2Exception
/* 181:    */   {
/* 182:168 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 183:169 */     String error = null;
/* 184:    */     try
/* 185:    */     {
/* 186:172 */       CierreCaja cierreCaja = this.servicioCierreCaja.cargarDetalle(idCierreCaja);
/* 187:173 */       this.servicioCierreCaja.anular(cierreCaja);
/* 188:    */       
/* 189:175 */       response.setSuccsess(true);
/* 190:    */       
/* 191:177 */       return response;
/* 192:    */     }
/* 193:    */     catch (ExcepcionAS2 e)
/* 194:    */     {
/* 195:180 */       e.printStackTrace();
/* 196:181 */       error = e.getCodigoExcepcion() + " | " + e.getMessage();
/* 197:    */     }
/* 198:    */     catch (Exception e)
/* 199:    */     {
/* 200:183 */       e.printStackTrace();
/* 201:184 */       error = e.getMessage();
/* 202:    */     }
/* 203:186 */     response.setError(error);
/* 204:187 */     response.setSuccsess(false);
/* 205:188 */     return response;
/* 206:    */   }
/* 207:    */   
/* 208:    */   @POST
/* 209:    */   @Path("/consultarCierreCaja")
/* 210:    */   @Consumes({"application/json"})
/* 211:    */   @Produces({"application/json"})
/* 212:    */   public EstadoCierreCajaResponseDto consultarCierreCaja(ConsultarCierreCajaRequestDto request)
/* 213:    */     throws AS2Exception
/* 214:    */   {
/* 215:196 */     EstadoCierreCajaResponseDto estadoCierreCajaResponse = new EstadoCierreCajaResponseDto();
/* 216:    */     
/* 217:198 */     Map<String, String> filtros = new HashMap();
/* 218:199 */     filtros.put("idOrganizacion", "" + request.getIdOrganizacion());
/* 219:200 */     filtros.put("idCierreCaja", "" + request.getIdCierreCaja());
/* 220:    */     
/* 221:    */ 
/* 222:203 */     CierreCaja cierreCaja = this.servicioCierreCaja.cargarDetalle(request.getIdCierreCaja().intValue());
/* 223:205 */     if (cierreCaja != null)
/* 224:    */     {
/* 225:206 */       estadoCierreCajaResponse.setEstado(cierreCaja.getEstado());
/* 226:207 */       estadoCierreCajaResponse.setIdCierreCaja(cierreCaja.getIdCierreCaja());
/* 227:208 */       estadoCierreCajaResponse.setNumeroCierreCajaAs2(cierreCaja.getNumero());
/* 228:    */     }
/* 229:    */     else
/* 230:    */     {
/* 231:210 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201");
/* 232:    */     }
/* 233:212 */     return estadoCierreCajaResponse;
/* 234:    */   }
/* 235:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.contabilidad.rest.CierreCajaServicioRest
 * JD-Core Version:    0.7.0.1
 */