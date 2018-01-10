/*  1:   */ package com.asinfo.as2.rs.datosbase.rest;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.GenericoDao;
/*  4:   */ import com.asinfo.as2.entities.Banco;
/*  5:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  6:   */ import com.asinfo.as2.rs.datosbase.dto.BancoRequestDto;
/*  7:   */ import com.asinfo.as2.rs.datosbase.dto.BancoResponseDto;
/*  8:   */ import com.asinfo.as2.rs.datosbase.dto.ListaBancoRequestDto;
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
/* 19:   */ @Path("/datosBase")
/* 20:   */ public class BancoServicioRest
/* 21:   */ {
/* 22:   */   @EJB
/* 23:   */   private GenericoDao<Banco> bancoDao;
/* 24:   */   
/* 25:   */   @POST
/* 26:   */   @Path("/consultarBancosPorOrganizacion")
/* 27:   */   @Consumes({"application/json"})
/* 28:   */   @Produces({"application/json"})
/* 29:   */   public List<BancoResponseDto> consultarBancosPorOrganizacion(ListaBancoRequestDto request)
/* 30:   */     throws AS2Exception
/* 31:   */   {
/* 32:   */     try
/* 33:   */     {
/* 34:34 */       Map<String, String> filtros = new HashMap();
/* 35:35 */       filtros.put("idOrganizacion", request.getIdOrganizacion() + "");
/* 36:36 */       filtros.put("activo", "true");
/* 37:   */       
/* 38:38 */       List<Banco> listabanco = this.bancoDao.obtenerListaCombo(Banco.class, "predeterminado", false, filtros);
/* 39:39 */       List<BancoResponseDto> response = new ArrayList();
/* 40:41 */       for (Banco banco : listabanco)
/* 41:   */       {
/* 42:42 */         BancoResponseDto bancoResponse = new BancoResponseDto();
/* 43:43 */         bancoResponse.setActivo(banco.isActivo());
/* 44:44 */         bancoResponse.setCodigo(banco.getCodigo());
/* 45:45 */         bancoResponse.setIdBanco(Integer.valueOf(banco.getId()));
/* 46:46 */         bancoResponse.setNombre(banco.getNombre());
/* 47:47 */         bancoResponse.setIdOrganizacion(Integer.valueOf(banco.getIdOrganizacion()));
/* 48:   */         
/* 49:49 */         boolean encontre = false;
/* 50:50 */         for (BancoRequestDto bancoRequest : request.getListaBancoRequest()) {
/* 51:51 */           if (bancoResponse.getIdBanco().equals(bancoRequest.getIdBanco()))
/* 52:   */           {
/* 53:52 */             encontre = true;
/* 54:53 */             bancoRequest.setRevisado(Boolean.valueOf(true));
/* 55:54 */             if (bancoResponse.getHashCode() == bancoRequest.getHashCode().intValue()) {
/* 56:   */               break;
/* 57:   */             }
/* 58:55 */             response.add(bancoResponse); break;
/* 59:   */           }
/* 60:   */         }
/* 61:60 */         if (!encontre) {
/* 62:61 */           response.add(bancoResponse);
/* 63:   */         }
/* 64:   */       }
/* 65:65 */       for (BancoRequestDto bancoRequest : request.getListaBancoRequest()) {
/* 66:66 */         if (!bancoRequest.getRevisado().booleanValue())
/* 67:   */         {
/* 68:67 */           BancoResponseDto bancoResponse = new BancoResponseDto();
/* 69:68 */           bancoResponse.setIdBanco(bancoRequest.getIdBanco());
/* 70:69 */           bancoResponse.setActivo(false);
/* 71:70 */           response.add(bancoResponse);
/* 72:   */         }
/* 73:   */       }
/* 74:74 */       return response;
/* 75:   */     }
/* 76:   */     catch (Exception e)
/* 77:   */     {
/* 78:76 */       e.printStackTrace();
/* 79:77 */       throw new AS2Exception(e.getMessage());
/* 80:   */     }
/* 81:   */   }
/* 82:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.rest.BancoServicioRest
 * JD-Core Version:    0.7.0.1
 */