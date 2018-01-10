/*   1:    */ package com.asinfo.as2.rs.datosbase.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Banco;
/*   4:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   5:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   6:    */ import com.asinfo.as2.entities.DenominacionFormaCobro;
/*   7:    */ import com.asinfo.as2.entities.FormaPago;
/*   8:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  12:    */ import com.asinfo.as2.rs.datosbase.dto.ConsultaSincronizacionRequestDto;
/*  13:    */ import com.asinfo.as2.rs.datosbase.dto.CuentaOrganizacionRequestDto;
/*  14:    */ import com.asinfo.as2.rs.datosbase.dto.CuentaOrganizacionResponseDto;
/*  15:    */ import com.asinfo.as2.rs.datosbase.dto.DenominacionFormaCobroResponseDto;
/*  16:    */ import com.asinfo.as2.rs.datosbase.dto.FormaPagoResponseDto;
/*  17:    */ import com.asinfo.as2.rs.datosbase.dto.ListaConsultaPorOrganizacionRequestDto;
/*  18:    */ import com.asinfo.as2.rs.datosbase.dto.ListaCuentaOrganizacionRequestDto;
/*  19:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.Iterator;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.ws.rs.Consumes;
/*  27:    */ import javax.ws.rs.POST;
/*  28:    */ import javax.ws.rs.Path;
/*  29:    */ import javax.ws.rs.Produces;
/*  30:    */ 
/*  31:    */ @Path("/datosBase")
/*  32:    */ public class CuentaOrganizacionServicioRest
/*  33:    */ {
/*  34:    */   @EJB
/*  35:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  36:    */   @EJB
/*  37:    */   private transient ServicioGenerico<DenominacionFormaCobro> servicioDenominacionFormaCobro;
/*  38:    */   
/*  39:    */   @POST
/*  40:    */   @Path("/consultarCuentasPorOrganizacion")
/*  41:    */   @Consumes({"application/json"})
/*  42:    */   @Produces({"application/json"})
/*  43:    */   public List<CuentaOrganizacionResponseDto> consultarCuentasPorOrganizacion(ListaCuentaOrganizacionRequestDto request)
/*  44:    */     throws AS2Exception
/*  45:    */   {
/*  46:    */     try
/*  47:    */     {
/*  48: 43 */       Map<String, String> filtros = new HashMap();
/*  49: 44 */       filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/*  50: 45 */       filtros.put("activo", "true");
/*  51:    */       
/*  52:    */ 
/*  53: 48 */       List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("predeterminado", false, filtros);
/*  54:    */       
/*  55: 50 */       List<CuentaOrganizacionResponseDto> response = new ArrayList();
/*  56: 52 */       for (CuentaBancariaOrganizacion cuenta : listaCuentaBancariaOrganizacion)
/*  57:    */       {
/*  58: 53 */         CuentaOrganizacionResponseDto cuentaResponse = new CuentaOrganizacionResponseDto();
/*  59: 54 */         cuentaResponse.setActivo(cuenta.isActivo());
/*  60: 55 */         cuentaResponse.setIdCuentaOrganizacion(Integer.valueOf(cuenta.getIdCuentaBancariaOrganizacion()));
/*  61: 56 */         cuentaResponse.setIdOrganizacion(Integer.valueOf(cuenta.getIdOrganizacion()));
/*  62: 57 */         cuentaResponse.setNumeroCuenta(cuenta.getNombreCompleto());
/*  63: 58 */         cuentaResponse.setTipoCuenta(Integer.valueOf(cuenta.getTipoCuentaBancariaOrganizacion().ordinal()));
/*  64: 59 */         if (cuenta.getCuentaBancaria() != null)
/*  65:    */         {
/*  66: 60 */           cuentaResponse.setNumeroCuentaSimple(cuenta.getCuentaBancaria().getNumero());
/*  67: 61 */           cuentaResponse.setNombreBanco(cuenta.getCuentaBancaria().getBanco().getNombre());
/*  68:    */         }
/*  69: 64 */         cuenta = this.servicioCuentaBancariaOrganizacion.cargarDetalle(cuenta.getId());
/*  70: 65 */         for (Iterator localIterator2 = cuenta.getListaFormaPago().iterator(); localIterator2.hasNext();)
/*  71:    */         {
/*  72: 65 */           formaPago = (FormaPagoCuentaBancariaOrganizacion)localIterator2.next();
/*  73: 66 */           FormaPagoResponseDto formaPagoResponse = new FormaPagoResponseDto();
/*  74: 67 */           formaPagoResponse.setActivo(formaPago.getFormaPago().getActivo());
/*  75: 68 */           formaPagoResponse.setCodigo(formaPago.getFormaPago().getCodigo());
/*  76: 69 */           formaPagoResponse.setNombre(formaPago.getFormaPago().getNombre());
/*  77: 70 */           formaPagoResponse.setIdFormaPago(Integer.valueOf(formaPago.getFormaPago().getIdFormaPago()));
/*  78: 71 */           formaPagoResponse.setIdOrganizacion(Integer.valueOf(formaPago.getFormaPago().getIdOrganizacion()));
/*  79: 72 */           formaPagoResponse.setIndicadorCheque(formaPago.getFormaPago().isIndicadorCheque());
/*  80: 73 */           formaPagoResponse.setIndicadorDepositoAutomatico(formaPago.getFormaPago().isIndicadorDepositoAutomatico());
/*  81: 74 */           formaPagoResponse.setIndicadorRetencionFuente(formaPago.getFormaPago().isIndicadorRetencionFuente());
/*  82: 75 */           formaPagoResponse.setIndicadorRetencionIVA(formaPago.getFormaPago().isIndicadorRetencionIva());
/*  83: 76 */           formaPagoResponse.setIndicadorTarjetaCredito(formaPago.getFormaPago().isIndicadorTarjetaCredito());
/*  84: 77 */           formaPagoResponse.setPorcentajeRetencion(formaPago.getFormaPago().getPorcentajeRetencion());
/*  85: 78 */           cuentaResponse.getListaFormaPago().add(formaPagoResponse);
/*  86:    */         }
/*  87:    */         FormaPagoCuentaBancariaOrganizacion formaPago;
/*  88: 81 */         boolean encontre = false;
/*  89: 82 */         for (CuentaOrganizacionRequestDto cuentaRequest : request.getListaCuentaOrganizacionRequest()) {
/*  90: 83 */           if (cuentaResponse.getIdCuentaOrganizacion().equals(cuentaRequest.getIdCuentaOrganizacion()))
/*  91:    */           {
/*  92: 84 */             encontre = true;
/*  93: 85 */             cuentaRequest.setRevisado(Boolean.valueOf(true));
/*  94: 86 */             if (cuentaResponse.getHashCode() == cuentaRequest.getHashCode().intValue()) {
/*  95:    */               break;
/*  96:    */             }
/*  97: 87 */             response.add(cuentaResponse); break;
/*  98:    */           }
/*  99:    */         }
/* 100: 92 */         if (!encontre) {
/* 101: 93 */           response.add(cuentaResponse);
/* 102:    */         }
/* 103:    */       }
/* 104: 97 */       for (CuentaOrganizacionRequestDto cuentaRequest : request.getListaCuentaOrganizacionRequest()) {
/* 105: 98 */         if (!cuentaRequest.getRevisado().booleanValue())
/* 106:    */         {
/* 107: 99 */           CuentaOrganizacionResponseDto cuentaResponse = new CuentaOrganizacionResponseDto();
/* 108:100 */           cuentaResponse.setIdCuentaOrganizacion(cuentaRequest.getIdCuentaOrganizacion());
/* 109:101 */           cuentaResponse.setActivo(false);
/* 110:102 */           response.add(cuentaResponse);
/* 111:    */         }
/* 112:    */       }
/* 113:106 */       return response;
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:108 */       e.printStackTrace();
/* 118:109 */       throw new AS2Exception(e.getMessage());
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   @POST
/* 123:    */   @Path("/consultarDenominacionFormaCobroPorOrganizacion")
/* 124:    */   @Consumes({"application/json"})
/* 125:    */   @Produces({"application/json"})
/* 126:    */   public List<DenominacionFormaCobroResponseDto> consultarDenominacionFormaCobroPorOrganizacion(ListaConsultaPorOrganizacionRequestDto request)
/* 127:    */     throws AS2Exception
/* 128:    */   {
/* 129:    */     try
/* 130:    */     {
/* 131:120 */       Map<String, String> filtros = new HashMap();
/* 132:121 */       filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 133:122 */       filtros.put("activo", "true");
/* 134:    */       
/* 135:124 */       List<String> listaCampos = new ArrayList();
/* 136:125 */       listaCampos.add("formaPago");
/* 137:126 */       List<DenominacionFormaCobro> listaDenominacion = this.servicioDenominacionFormaCobro.obtenerListaPorPagina(DenominacionFormaCobro.class, 0, 100000, "idDenominacionFormaCobro", true, filtros, listaCampos);
/* 138:    */       
/* 139:128 */       List<DenominacionFormaCobroResponseDto> response = new ArrayList();
/* 140:130 */       for (DenominacionFormaCobro denominacion : listaDenominacion)
/* 141:    */       {
/* 142:131 */         DenominacionFormaCobroResponseDto denominacionResponse = new DenominacionFormaCobroResponseDto();
/* 143:132 */         denominacionResponse.setActivo(denominacion.isActivo());
/* 144:133 */         denominacionResponse.setCodigo(denominacion.getCodigo());
/* 145:134 */         denominacionResponse.setIdDenominacionFormaCobro(Integer.valueOf(denominacion.getId()));
/* 146:135 */         denominacionResponse.setNombre(denominacion.getNombre());
/* 147:136 */         denominacionResponse.setIdOrganizacion(Integer.valueOf(denominacion.getIdOrganizacion()));
/* 148:137 */         denominacionResponse.setDescripcion(denominacion.getDescripcion());
/* 149:138 */         denominacionResponse.setValor(denominacion.getValor());
/* 150:139 */         denominacionResponse.setIdSucursal(Integer.valueOf(denominacion.getIdSucursal()));
/* 151:140 */         denominacionResponse.setIdFormaPago(Integer.valueOf(denominacion.getFormaPago().getId()));
/* 152:    */         
/* 153:142 */         boolean encontre = false;
/* 154:143 */         for (Iterator localIterator2 = request.getListaConsultaSincronizacionRequest().iterator(); localIterator2.hasNext(); goto 346)
/* 155:    */         {
/* 156:143 */           ConsultaSincronizacionRequestDto denominacionRequest = (ConsultaSincronizacionRequestDto)localIterator2.next();
/* 157:144 */           if ((denominacionRequest.getIdEntidad() != null) && 
/* 158:145 */             (denominacionRequest.getIdEntidad().equals(denominacionResponse.getIdDenominacionFormaCobro())))
/* 159:    */           {
/* 160:146 */             encontre = true;
/* 161:147 */             denominacionRequest.setRevisado(Boolean.valueOf(true));
/* 162:148 */             if (denominacionResponse.getHashCode() == denominacionRequest.getHashCode().intValue()) {
/* 163:    */               break;
/* 164:    */             }
/* 165:149 */             response.add(denominacionResponse);
/* 166:    */           }
/* 167:    */         }
/* 168:154 */         if (!encontre) {
/* 169:155 */           response.add(denominacionResponse);
/* 170:    */         }
/* 171:    */       }
/* 172:159 */       for (ConsultaSincronizacionRequestDto denominacionRequest : request.getListaConsultaSincronizacionRequest()) {
/* 173:160 */         if (!denominacionRequest.getRevisado().booleanValue())
/* 174:    */         {
/* 175:161 */           DenominacionFormaCobroResponseDto denominacionResponse = new DenominacionFormaCobroResponseDto();
/* 176:162 */           denominacionResponse.setIdDenominacionFormaCobro(denominacionRequest.getIdEntidad());
/* 177:163 */           denominacionResponse.setActivo(false);
/* 178:164 */           response.add(denominacionResponse);
/* 179:    */         }
/* 180:    */       }
/* 181:168 */       return response;
/* 182:    */     }
/* 183:    */     catch (Exception e)
/* 184:    */     {
/* 185:170 */       e.printStackTrace();
/* 186:171 */       throw new AS2Exception(e.getMessage());
/* 187:    */     }
/* 188:    */   }
/* 189:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.rest.CuentaOrganizacionServicioRest
 * JD-Core Version:    0.7.0.1
 */