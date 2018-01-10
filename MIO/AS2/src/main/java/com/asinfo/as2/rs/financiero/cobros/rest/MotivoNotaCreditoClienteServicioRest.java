/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.rest;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.GenericoDao;
/*  4:   */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  5:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  6:   */ import com.asinfo.as2.rs.financiero.cobros.dto.ListaMotivoNotaCreditoClienteRequestDto;
/*  7:   */ import com.asinfo.as2.rs.financiero.cobros.dto.MotivoNotaCreditoClienteRequestDto;
/*  8:   */ import com.asinfo.as2.rs.financiero.cobros.dto.MotivoNotaCreditoClienteResponseDto;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ import java.util.HashMap;
/* 11:   */ import java.util.List;
/* 12:   */ import java.util.Map;
/* 13:   */ import javax.ejb.EJB;
/* 14:   */ import javax.ws.rs.Consumes;
/* 15:   */ import javax.ws.rs.POST;
/* 16:   */ import javax.ws.rs.Path;
/* 17:   */ import javax.ws.rs.Produces;
/* 18:   */ 
/* 19:   */ @Path("/cobro")
/* 20:   */ public class MotivoNotaCreditoClienteServicioRest
/* 21:   */ {
/* 22:   */   @EJB
/* 23:   */   private GenericoDao<MotivoNotaCreditoCliente> motivoNotaCreditoClienteDao;
/* 24:   */   
/* 25:   */   @POST
/* 26:   */   @Path("/consultarMotivoNotaCreditoClientePorOrganizacion")
/* 27:   */   @Consumes({"application/json"})
/* 28:   */   @Produces({"application/json"})
/* 29:   */   public List<MotivoNotaCreditoClienteResponseDto> consultarMotivoNotaCreditoClientePorOrganizacion(ListaMotivoNotaCreditoClienteRequestDto request)
/* 30:   */     throws AS2Exception
/* 31:   */   {
/* 32:   */     try
/* 33:   */     {
/* 34:34 */       Map<String, String> filtros = new HashMap();
/* 35:35 */       filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 36:36 */       filtros.put("activo", "true");
/* 37:   */       
/* 38:38 */       List<MotivoNotaCreditoCliente> listaMotivoNotaCreditoCliente = this.motivoNotaCreditoClienteDao.obtenerListaCombo(MotivoNotaCreditoCliente.class, "predeterminado", false, filtros);
/* 39:39 */       List<MotivoNotaCreditoClienteResponseDto> response = new ArrayList();
/* 40:41 */       for (MotivoNotaCreditoCliente motivo : listaMotivoNotaCreditoCliente)
/* 41:   */       {
/* 42:42 */         MotivoNotaCreditoClienteResponseDto motivoResponse = new MotivoNotaCreditoClienteResponseDto();
/* 43:43 */         motivoResponse.setActivo(motivo.isActivo());
/* 44:44 */         motivoResponse.setCodigo(motivo.getCodigo());
/* 45:45 */         motivoResponse.setIdMotivoNotaCreditoCliente(Integer.valueOf(motivo.getId()));
/* 46:46 */         motivoResponse.setNombre(motivo.getNombre());
/* 47:47 */         motivoResponse.setIdOrganizacion(Integer.valueOf(motivo.getIdOrganizacion()));
/* 48:48 */         motivoResponse.setIdSucursal(Integer.valueOf(motivo.getIdSucursal()));
/* 49:49 */         motivoResponse.setIndicadorReversaTransformacion(motivo.isIndicadorReversaTransformacion());
/* 50:50 */         motivoResponse.setDescripcion(motivo.getDescripcion());
/* 51:51 */         motivoResponse.setPredeterminado(motivo.isPredeterminado());
/* 52:   */         
/* 53:53 */         boolean encontre = false;
/* 54:54 */         for (MotivoNotaCreditoClienteRequestDto motivoRequest : request.getListaMotivoNotaCreditoClienteRequest()) {
/* 55:55 */           if (motivoResponse.getIdMotivoNotaCreditoCliente().equals(motivoRequest.getIdMotivoNotaCreditoCliente()))
/* 56:   */           {
/* 57:56 */             encontre = true;
/* 58:57 */             motivoRequest.setRevisado(Boolean.valueOf(true));
/* 59:58 */             if (motivoResponse.getHashCode() == motivoRequest.getHashCode().intValue()) {
/* 60:   */               break;
/* 61:   */             }
/* 62:59 */             response.add(motivoResponse); break;
/* 63:   */           }
/* 64:   */         }
/* 65:64 */         if (!encontre) {
/* 66:65 */           response.add(motivoResponse);
/* 67:   */         }
/* 68:   */       }
/* 69:69 */       for (MotivoNotaCreditoClienteRequestDto motRequest : request.getListaMotivoNotaCreditoClienteRequest()) {
/* 70:70 */         if (!motRequest.getRevisado().booleanValue())
/* 71:   */         {
/* 72:71 */           MotivoNotaCreditoClienteResponseDto motivResponse = new MotivoNotaCreditoClienteResponseDto();
/* 73:72 */           motivResponse.setIdMotivoNotaCreditoCliente(motRequest.getIdMotivoNotaCreditoCliente());
/* 74:73 */           motivResponse.setActivo(false);
/* 75:74 */           response.add(motivResponse);
/* 76:   */         }
/* 77:   */       }
/* 78:78 */       return response;
/* 79:   */     }
/* 80:   */     catch (Exception e)
/* 81:   */     {
/* 82:80 */       e.printStackTrace();
/* 83:81 */       throw new AS2Exception(e.getMessage());
/* 84:   */     }
/* 85:   */   }
/* 86:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.rest.MotivoNotaCreditoClienteServicioRest
 * JD-Core Version:    0.7.0.1
 */