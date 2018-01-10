/*   1:    */ package com.asinfo.as2.rs.financiero.cobros.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.dao.AnticipoClienteDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*   7:    */ import com.asinfo.as2.entities.Cliente;
/*   8:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   9:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoCliente;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.Empresa;
/*  12:    */ import com.asinfo.as2.entities.LiquidacionAnticipoCliente;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioLiquidacionAnticipoCliente;
/*  19:    */ import com.asinfo.as2.rs.datosbase.dto.ProcesosResponseDto;
/*  20:    */ import com.asinfo.as2.rs.financiero.cobros.dto.AnticipoClienteRequestDto;
/*  21:    */ import com.asinfo.as2.rs.financiero.cobros.dto.AnticipoClienteResponseDto;
/*  22:    */ import com.asinfo.as2.rs.financiero.cobros.dto.DetalleLiquidacionAnticipoResponseDto;
/*  23:    */ import com.asinfo.as2.rs.financiero.cobros.dto.LiquidacionAnticipoClienteResponseDto;
/*  24:    */ import com.asinfo.as2.rs.financiero.cobros.dto.ListaAnticipoClienteRequestDto;
/*  25:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  26:    */ import java.io.PrintStream;
/*  27:    */ import java.text.SimpleDateFormat;
/*  28:    */ import java.util.ArrayList;
/*  29:    */ import java.util.HashMap;
/*  30:    */ import java.util.Iterator;
/*  31:    */ import java.util.List;
/*  32:    */ import java.util.Map;
/*  33:    */ import javax.ejb.EJB;
/*  34:    */ import javax.ws.rs.Consumes;
/*  35:    */ import javax.ws.rs.POST;
/*  36:    */ import javax.ws.rs.Path;
/*  37:    */ import javax.ws.rs.Produces;
/*  38:    */ 
/*  39:    */ @Path("/anticipoCliente")
/*  40:    */ public class AnticipoClienteServicioRest
/*  41:    */ {
/*  42:    */   @EJB
/*  43:    */   protected ServicioGenerico<AnticipoCliente> servicioAnticipoCliente;
/*  44:    */   @EJB
/*  45:    */   protected AnticipoClienteDao anticipoClienteDao;
/*  46:    */   @EJB
/*  47:    */   protected ServicioSucursal servicioSucursal;
/*  48:    */   @EJB
/*  49:    */   protected ServicioLiquidacionAnticipoCliente servicioLiquidacionAnticipoCliente;
/*  50:    */   @EJB
/*  51:    */   protected ServicioDocumento servicioDocumento;
/*  52:    */   @EJB
/*  53:    */   protected ServicioGenerico<CuentaPorCobrar> servicioCuentaPorCobrar;
/*  54:    */   
/*  55:    */   @POST
/*  56:    */   @Path("/consultarAnticipoCliente")
/*  57:    */   @Consumes({"application/json"})
/*  58:    */   @Produces({"application/json"})
/*  59:    */   public List<AnticipoClienteResponseDto> consultarAnticipoCliente(ListaAnticipoClienteRequestDto request)
/*  60:    */     throws AS2Exception
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64: 63 */       List<AnticipoClienteResponseDto> response = new ArrayList();
/*  65:    */       
/*  66: 65 */       HashMap<String, String> filters = new HashMap();
/*  67: 66 */       filters.put("idOrganizacion", "" + request.getIdOrganizacion());
/*  68: 67 */       filters.put("estado", "!=" + Estado.ANULADO.toString());
/*  69: 68 */       filters.put("empresa.activo", "true");
/*  70: 69 */       System.out.println("IDSUCURSAL" + request.getIdSucursal());
/*  71: 70 */       if (request.getIdSucursal().intValue() != 0) {
/*  72: 71 */         filters.put("sucursal.idSucursal", "" + request.getIdSucursal());
/*  73:    */       }
/*  74: 73 */       if (request.getIdCliente().intValue() != 0) {
/*  75: 74 */         filters.put("empresa.cliente.idCliente", "" + request.getIdCliente());
/*  76:    */       }
/*  77: 76 */       filters.put("saldo", ">0");
/*  78: 77 */       List<AnticipoCliente> listaAnticipoCliente = this.servicioAnticipoCliente.obtenerListaCombo(AnticipoCliente.class, "idAnticipoCliente", true, filters);
/*  79: 79 */       for (AnticipoCliente detalle : listaAnticipoCliente)
/*  80:    */       {
/*  81: 80 */         List<String> listaCampos = new ArrayList();
/*  82: 81 */         listaCampos.add("sucursal");
/*  83: 82 */         listaCampos.add("empresa.cliente");
/*  84: 83 */         detalle = (AnticipoCliente)this.servicioAnticipoCliente.cargarDetalle(AnticipoCliente.class, detalle.getId(), listaCampos);
/*  85: 84 */         AnticipoClienteResponseDto anticipoClienteResponse = new AnticipoClienteResponseDto();
/*  86:    */         
/*  87: 86 */         anticipoClienteResponse.setIdOrganizacion(Integer.valueOf(detalle.getIdOrganizacion()));
/*  88: 87 */         anticipoClienteResponse.setIdSucursal(Integer.valueOf(detalle.getSucursal().getId()));
/*  89: 88 */         anticipoClienteResponse.setActivo(true);
/*  90: 89 */         anticipoClienteResponse.setIdAnticipoCliente(Integer.valueOf(detalle.getId()));
/*  91: 90 */         anticipoClienteResponse.setFecha(detalle.getFecha());
/*  92: 91 */         anticipoClienteResponse.setDescripcion(detalle.getDescripcion());
/*  93: 92 */         anticipoClienteResponse.setIdCliente(Integer.valueOf(detalle.getEmpresa().getCliente().getId()));
/*  94: 93 */         anticipoClienteResponse.setSaldo(detalle.getSaldo());
/*  95: 94 */         anticipoClienteResponse.setValor(detalle.getValor());
/*  96: 95 */         anticipoClienteResponse.setNumero(detalle.getNumero());
/*  97: 96 */         anticipoClienteResponse.setEstado(detalle.getEstado());
/*  98:    */         
/*  99: 98 */         boolean encontre = false;
/* 100: 99 */         for (AnticipoClienteRequestDto anticipoClienteRequest : request.getListaAnticipoCliente()) {
/* 101:100 */           if (anticipoClienteResponse.getIdAnticipoCliente().equals(anticipoClienteRequest.getIdAnticipoCliente()))
/* 102:    */           {
/* 103:101 */             encontre = true;
/* 104:102 */             anticipoClienteRequest.setRevisado(Boolean.valueOf(true));
/* 105:103 */             if (anticipoClienteResponse.getHashCode() == anticipoClienteRequest.getHashCode().intValue()) {
/* 106:    */               break;
/* 107:    */             }
/* 108:104 */             response.add(anticipoClienteResponse); break;
/* 109:    */           }
/* 110:    */         }
/* 111:109 */         if (!encontre) {
/* 112:110 */           response.add(anticipoClienteResponse);
/* 113:    */         }
/* 114:    */       }
/* 115:115 */       for (AnticipoClienteRequestDto anticipoClienteRequest : request.getListaAnticipoCliente()) {
/* 116:116 */         if (!anticipoClienteRequest.getRevisado().booleanValue())
/* 117:    */         {
/* 118:117 */           AnticipoClienteResponseDto anticipoClienteResponse = new AnticipoClienteResponseDto();
/* 119:118 */           anticipoClienteResponse.setIdAnticipoCliente(anticipoClienteRequest.getIdAnticipoCliente());
/* 120:119 */           anticipoClienteResponse.setActivo(false);
/* 121:120 */           response.add(anticipoClienteResponse);
/* 122:    */         }
/* 123:    */       }
/* 124:124 */       return response;
/* 125:    */     }
/* 126:    */     catch (Exception e)
/* 127:    */     {
/* 128:126 */       e.printStackTrace();
/* 129:127 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { "" });
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   @POST
/* 134:    */   @Path("/crearLiquidacionAnticipoCliente")
/* 135:    */   @Consumes({"application/json"})
/* 136:    */   @Produces({"application/json"})
/* 137:    */   public ProcesosResponseDto crearLiquidacionAnticipoCliente(LiquidacionAnticipoClienteResponseDto liquidacionAnticipoClienteResponse)
/* 138:    */     throws AS2Exception
/* 139:    */   {
/* 140:136 */     ProcesosResponseDto response = new ProcesosResponseDto();
/* 141:137 */     String error = "";
/* 142:138 */     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 143:139 */     Documento documento = null;
/* 144:140 */     LiquidacionAnticipoCliente liquidacionAnticipoCliente = new LiquidacionAnticipoCliente();
/* 145:    */     
/* 146:142 */     List<DetalleLiquidacionAnticipoResponseDto> listaDetalleLiquidacionAnticipoResponse = liquidacionAnticipoClienteResponse.getListaDetalleLiquidacionAnticipoResponseDto();
/* 147:    */     try
/* 148:    */     {
/* 149:146 */       Map<String, String> filtrosLiquidacionAnticipoCliente = new HashMap();
/* 150:147 */       filtrosLiquidacionAnticipoCliente.put("idOrganizacion", liquidacionAnticipoClienteResponse.getIdOrganizacion() + "");
/* 151:148 */       filtrosLiquidacionAnticipoCliente.put("idDispositivoSincronizacion", liquidacionAnticipoClienteResponse.getIdDispositivoSincronizacion() + "");
/* 152:149 */       List<LiquidacionAnticipoCliente> listaLiquidacionAnticipoCliente = this.servicioLiquidacionAnticipoCliente.obtenerListaPorPagina(0, 1, "fecha", false, filtrosLiquidacionAnticipoCliente);
/* 153:    */       List<String> listaCamposAnticipo;
/* 154:150 */       if (!listaLiquidacionAnticipoCliente.isEmpty())
/* 155:    */       {
/* 156:151 */         liquidacionAnticipoCliente = this.servicioLiquidacionAnticipoCliente.cargarDetalle(((LiquidacionAnticipoCliente)listaLiquidacionAnticipoCliente.get(0)).getId());
/* 157:    */       }
/* 158:    */       else
/* 159:    */       {
/* 160:154 */         listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.LIQUIDACION_ANTICIPO_CLIENTE, liquidacionAnticipoClienteResponse
/* 161:155 */           .getIdOrganizacion());
/* 162:156 */         if ((listaDocumento != null) && (!listaDocumento.isEmpty())) {
/* 163:157 */           documento = (Documento)listaDocumento.get(0);
/* 164:    */         } else {
/* 165:159 */           throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { Documento.class.getSimpleName() + ". " + DocumentoBase.LIQUIDACION_ANTICIPO_CLIENTE });
/* 166:    */         }
/* 167:162 */         Sucursal sucursal = this.servicioSucursal.buscarPorId(Integer.valueOf(liquidacionAnticipoClienteResponse.getIdSucursal()));
/* 168:    */         
/* 169:164 */         liquidacionAnticipoCliente.setEstado(Estado.PROCESADO);
/* 170:165 */         liquidacionAnticipoCliente.setDocumento(documento);
/* 171:166 */         liquidacionAnticipoCliente.setDescripcion(liquidacionAnticipoClienteResponse.getDescripcion());
/* 172:167 */         liquidacionAnticipoCliente.setIdSucursal(sucursal.getId());
/* 173:168 */         liquidacionAnticipoCliente.setIdOrganizacion(liquidacionAnticipoClienteResponse.getIdOrganizacion());
/* 174:169 */         liquidacionAnticipoCliente.setIdDispositivoSincronizacion(liquidacionAnticipoClienteResponse.getIdDispositivoSincronizacion());
/* 175:170 */         liquidacionAnticipoCliente.setFecha(sdf.parse(liquidacionAnticipoClienteResponse.getFecha()));
/* 176:171 */         liquidacionAnticipoCliente.setNumero("");
/* 177:    */         
/* 178:173 */         listaCamposAnticipo = new ArrayList();
/* 179:174 */         listaCamposAnticipo.add("documento");
/* 180:175 */         listaCamposAnticipo.add("empresa");
/* 181:176 */         AnticipoCliente anticipoCliente = (AnticipoCliente)this.servicioAnticipoCliente.cargarDetalle(AnticipoCliente.class, liquidacionAnticipoClienteResponse.getIdAnticipoCliente().intValue(), listaCamposAnticipo);
/* 182:177 */         liquidacionAnticipoCliente.setAnticipoCliente(anticipoCliente);
/* 183:179 */         for (DetalleLiquidacionAnticipoResponseDto detalleLiquidacionAnticipoResponse : listaDetalleLiquidacionAnticipoResponse)
/* 184:    */         {
/* 185:180 */           DetalleLiquidacionAnticipoCliente detalleLiquidacionAnticipoCliente = new DetalleLiquidacionAnticipoCliente();
/* 186:181 */           detalleLiquidacionAnticipoCliente.setIdOrganizacion(liquidacionAnticipoClienteResponse.getIdOrganizacion());
/* 187:182 */           detalleLiquidacionAnticipoCliente.setIdSucursal(liquidacionAnticipoClienteResponse.getIdSucursal());
/* 188:183 */           detalleLiquidacionAnticipoCliente.setLiquidacionAnticipoCliente(liquidacionAnticipoCliente);
/* 189:184 */           List<String> listaCampos = new ArrayList();
/* 190:185 */           listaCampos.add("facturaCliente");
/* 191:186 */           detalleLiquidacionAnticipoCliente.setCuentaPorCobrar((CuentaPorCobrar)this.servicioCuentaPorCobrar.cargarDetalle(CuentaPorCobrar.class, detalleLiquidacionAnticipoResponse.getIdCxC().intValue(), listaCampos));
/* 192:187 */           detalleLiquidacionAnticipoCliente.setValor(detalleLiquidacionAnticipoResponse.getValor());
/* 193:188 */           detalleLiquidacionAnticipoCliente.setIdDispositivoSincronizacion(detalleLiquidacionAnticipoResponse.getIdDispositivoSincronizacion());
/* 194:    */           
/* 195:190 */           liquidacionAnticipoCliente.getListaDetalleLiquidacionAnticipoCliente().add(detalleLiquidacionAnticipoCliente);
/* 196:    */         }
/* 197:192 */         this.servicioLiquidacionAnticipoCliente.guardar(liquidacionAnticipoCliente);
/* 198:    */       }
/* 199:194 */       liquidacionAnticipoClienteResponse.setIdLiquidacionAnticipoCliente(Integer.valueOf(liquidacionAnticipoCliente.getId()));
/* 200:195 */       liquidacionAnticipoClienteResponse.setNumeroAS2(liquidacionAnticipoCliente.getNumero());
/* 201:197 */       for (List<Documento> listaDocumento = liquidacionAnticipoCliente.getListaDetalleLiquidacionAnticipoCliente().iterator(); listaDocumento.hasNext();)
/* 202:    */       {
/* 203:197 */         detalle = (DetalleLiquidacionAnticipoCliente)listaDocumento.next();
/* 204:198 */         for (DetalleLiquidacionAnticipoResponseDto detalleLiquidacionAnticipoResponse : listaDetalleLiquidacionAnticipoResponse) {
/* 205:199 */           if (detalle.getIdDispositivoSincronizacion().equals(detalleLiquidacionAnticipoResponse.getIdDispositivoSincronizacion())) {
/* 206:200 */             detalleLiquidacionAnticipoResponse.setIdDetalleAs2(detalle.getIdDetalleLiquidacionAnticipoCliente());
/* 207:    */           }
/* 208:    */         }
/* 209:    */       }
/* 210:    */       DetalleLiquidacionAnticipoCliente detalle;
/* 211:205 */       response.setSuccsess(true);
/* 212:206 */       response.setResponse(liquidacionAnticipoClienteResponse);
/* 213:207 */       return response;
/* 214:    */     }
/* 215:    */     catch (ExcepcionAS2 e)
/* 216:    */     {
/* 217:209 */       e.printStackTrace();
/* 218:210 */       error = e.getCodigoExcepcion() + " | " + e.getMessage();
/* 219:    */     }
/* 220:    */     catch (Exception e)
/* 221:    */     {
/* 222:212 */       e.printStackTrace();
/* 223:213 */       error = e.getMessage();
/* 224:    */     }
/* 225:215 */     response.setError(error);
/* 226:216 */     response.setSuccsess(false);
/* 227:217 */     return response;
/* 228:    */   }
/* 229:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.rest.AnticipoClienteServicioRest
 * JD-Core Version:    0.7.0.1
 */