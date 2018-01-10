/*   1:    */ package com.asinfo.as2.rs.financiero.cobros.rest;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.TarjetaCreditoDao;
/*   4:    */ import com.asinfo.as2.entities.PlanTarjetaCredito;
/*   5:    */ import com.asinfo.as2.entities.TarjetaCredito;
/*   6:    */ import com.asinfo.as2.entities.TipoTarjetaCredito;
/*   7:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   8:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioTarjetaCredito;
/*   9:    */ import com.asinfo.as2.rs.financiero.cobros.dto.ListaTarjetaCreditoRequestDto;
/*  10:    */ import com.asinfo.as2.rs.financiero.cobros.dto.PlanTarjetaCreditoResponseDto;
/*  11:    */ import com.asinfo.as2.rs.financiero.cobros.dto.TarjetaCreditoRequestDto;
/*  12:    */ import com.asinfo.as2.rs.financiero.cobros.dto.TarjetaCreditoResponseDto;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.List;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.ws.rs.Consumes;
/*  19:    */ import javax.ws.rs.POST;
/*  20:    */ import javax.ws.rs.Path;
/*  21:    */ import javax.ws.rs.Produces;
/*  22:    */ 
/*  23:    */ @Path("/tarjetaCredito")
/*  24:    */ public class TarjetaCreditoServicioRest
/*  25:    */ {
/*  26:    */   @EJB
/*  27:    */   protected ServicioTarjetaCredito servicioTarjetaCredito;
/*  28:    */   @EJB
/*  29:    */   protected TarjetaCreditoDao tarjetaCreditoDao;
/*  30:    */   
/*  31:    */   @POST
/*  32:    */   @Path("/consultarTarjetaCredito")
/*  33:    */   @Consumes({"application/json"})
/*  34:    */   @Produces({"application/json"})
/*  35:    */   public List<TarjetaCreditoResponseDto> consultarTarjetaCredito(ListaTarjetaCreditoRequestDto request)
/*  36:    */     throws AS2Exception
/*  37:    */   {
/*  38:    */     try
/*  39:    */     {
/*  40: 39 */       List<TarjetaCreditoResponseDto> response = new ArrayList();
/*  41:    */       
/*  42: 41 */       HashMap<String, String> filters = new HashMap();
/*  43: 42 */       filters.put("activo", "true");
/*  44: 43 */       filters.put("idOrganizacion", "" + request.getIdOrganizacion());
/*  45: 44 */       List<TarjetaCredito> listaTarjetaCredito = this.servicioTarjetaCredito.obtenerListaCombo("idTarjetaCredito", true, filters);
/*  46: 46 */       for (TarjetaCredito detalle : listaTarjetaCredito)
/*  47:    */       {
/*  48: 47 */         TarjetaCreditoResponseDto tarjetaCreditoResponseDto = new TarjetaCreditoResponseDto();
/*  49:    */         
/*  50: 49 */         tarjetaCreditoResponseDto.setIdOrganizacion(Integer.valueOf(detalle.getIdOrganizacion()));
/*  51: 50 */         tarjetaCreditoResponseDto.setIdSucursal(Integer.valueOf(detalle.getIdSucursal()));
/*  52: 51 */         tarjetaCreditoResponseDto.setNombre(detalle.getNombre());
/*  53: 52 */         tarjetaCreditoResponseDto.setActivo(detalle.isActivo());
/*  54: 53 */         tarjetaCreditoResponseDto.setIdTarjetaCredito(Integer.valueOf(detalle.getId()));
/*  55: 54 */         tarjetaCreditoResponseDto.setNombreTipoTarjetaCredito(detalle.getTipoTarjetaCredito().getNombre());
/*  56:    */         
/*  57:    */ 
/*  58: 57 */         List<PlanTarjetaCredito> listaPlanTarjetaCredito = this.tarjetaCreditoDao.getPlanTarjetaCredito(detalle);
/*  59: 58 */         for (Iterator localIterator2 = listaPlanTarjetaCredito.iterator(); localIterator2.hasNext();)
/*  60:    */         {
/*  61: 58 */           planTarjetaCredito = (PlanTarjetaCredito)localIterator2.next();
/*  62: 59 */           PlanTarjetaCreditoResponseDto planTarjetaCreditoResponseDto = new PlanTarjetaCreditoResponseDto();
/*  63:    */           
/*  64: 61 */           planTarjetaCreditoResponseDto.setIdOrganizacion(planTarjetaCredito.getIdOrganizacion());
/*  65: 62 */           planTarjetaCreditoResponseDto.setIdSucursal(planTarjetaCredito.getIdSucursal());
/*  66: 63 */           planTarjetaCreditoResponseDto.setCodigo(planTarjetaCredito.getCodigo());
/*  67: 64 */           planTarjetaCreditoResponseDto.setNombre(planTarjetaCredito.getNombre());
/*  68: 65 */           planTarjetaCreditoResponseDto.setIndicadorConInteres(planTarjetaCredito.isIndicadorConInteres());
/*  69: 66 */           planTarjetaCreditoResponseDto.setIdTarjetaCredito(tarjetaCreditoResponseDto.getIdTarjetaCredito());
/*  70: 67 */           planTarjetaCreditoResponseDto.setIdPlanTarjetaCreditoAS2(Integer.valueOf(planTarjetaCredito.getId()));
/*  71:    */           
/*  72: 69 */           tarjetaCreditoResponseDto.getListaPlanTarjetaCredito().add(planTarjetaCreditoResponseDto);
/*  73:    */         }
/*  74:    */         PlanTarjetaCredito planTarjetaCredito;
/*  75: 72 */         boolean encontre = false;
/*  76: 73 */         for (TarjetaCreditoRequestDto tarjetaCreditoRequest : request.getListaTarjetaCredito()) {
/*  77: 74 */           if (tarjetaCreditoResponseDto.getIdTarjetaCredito().equals(tarjetaCreditoRequest.getIdTarjetaCredito()))
/*  78:    */           {
/*  79: 75 */             encontre = true;
/*  80: 76 */             tarjetaCreditoRequest.setRevisado(Boolean.valueOf(true));
/*  81: 77 */             if (tarjetaCreditoResponseDto.getHashCode() == tarjetaCreditoRequest.getHashCode().intValue()) {
/*  82:    */               break;
/*  83:    */             }
/*  84: 78 */             response.add(tarjetaCreditoResponseDto); break;
/*  85:    */           }
/*  86:    */         }
/*  87: 83 */         if (!encontre) {
/*  88: 84 */           response.add(tarjetaCreditoResponseDto);
/*  89:    */         }
/*  90:    */       }
/*  91: 89 */       for (TarjetaCreditoRequestDto tarjetaCreditoRequest : request.getListaTarjetaCredito()) {
/*  92: 90 */         if (!tarjetaCreditoRequest.getRevisado().booleanValue())
/*  93:    */         {
/*  94: 91 */           TarjetaCreditoResponseDto tarjetaCreditoResponse = new TarjetaCreditoResponseDto();
/*  95: 92 */           tarjetaCreditoResponse.setIdTarjetaCredito(tarjetaCreditoRequest.getIdTarjetaCredito());
/*  96: 93 */           tarjetaCreditoResponse.setActivo(false);
/*  97: 94 */           response.add(tarjetaCreditoResponse);
/*  98:    */         }
/*  99:    */       }
/* 100: 98 */       return response;
/* 101:    */     }
/* 102:    */     catch (Exception e)
/* 103:    */     {
/* 104:100 */       e.printStackTrace();
/* 105:101 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_201", new String[] { "" });
/* 106:    */     }
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.rest.TarjetaCreditoServicioRest
 * JD-Core Version:    0.7.0.1
 */