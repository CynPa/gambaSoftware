/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.rest;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  4:   */ import com.asinfo.as2.rs.financiero.cobros.dto.ConsultarSaldosFacturaRequestDto;
/*  5:   */ import com.asinfo.as2.rs.financiero.cobros.dto.SaldosFacturaResponseDto;
/*  6:   */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  7:   */ import java.math.BigDecimal;
/*  8:   */ import java.text.SimpleDateFormat;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ import java.util.Date;
/* 11:   */ import java.util.List;
/* 12:   */ import javax.ejb.EJB;
/* 13:   */ import javax.ws.rs.Consumes;
/* 14:   */ import javax.ws.rs.POST;
/* 15:   */ import javax.ws.rs.Path;
/* 16:   */ import javax.ws.rs.Produces;
/* 17:   */ 
/* 18:   */ @Path("/cobro")
/* 19:   */ public class SaldosFacturaServicioRest
/* 20:   */ {
/* 21:   */   @EJB
/* 22:   */   private transient ServicioReporteVenta servicioReporteVenta;
/* 23:   */   
/* 24:   */   @POST
/* 25:   */   @Path("/consultarSaldosFactura")
/* 26:   */   @Consumes({"application/json"})
/* 27:   */   @Produces({"application/json"})
/* 28:   */   public List<SaldosFacturaResponseDto> consultarSaldosFactura(ConsultarSaldosFacturaRequestDto request)
/* 29:   */     throws AS2Exception
/* 30:   */   {
/* 31:   */     try
/* 32:   */     {
/* 33:35 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 34:   */       
/* 35:37 */       List<SaldosFacturaResponseDto> response = new ArrayList();
/* 36:38 */       List<Object[]> lresulttmp = this.servicioReporteVenta.getSaldosFactura(sdf.parse(request.getFechaHasta()), request.getIdEmpresaFinal().intValue());
/* 37:39 */       for (Object[] obj : lresulttmp)
/* 38:   */       {
/* 39:40 */         SaldosFacturaResponseDto objtmp = new SaldosFacturaResponseDto();
/* 40:41 */         objtmp.setFecha(sdf.format((Date)obj[0]));
/* 41:42 */         objtmp.setNumero((String)obj[1]);
/* 42:43 */         objtmp.setSaldo((BigDecimal)obj[2]);
/* 43:44 */         objtmp.setSaldoBloqueado((BigDecimal)obj[3]);
/* 44:45 */         objtmp.setEmpresa((Integer)obj[4]);
/* 45:46 */         objtmp.setSubempresa((Integer)obj[5]);
/* 46:47 */         objtmp.setIdFactura((Integer)obj[6]);
/* 47:48 */         objtmp.setIndicadorEmitidaRetencion((Boolean)obj[7]);
/* 48:49 */         objtmp.setDiasPlazo((Integer)obj[8]);
/* 49:50 */         objtmp.setMesesPlazo((Integer)obj[9]);
/* 50:51 */         response.add(objtmp);
/* 51:   */       }
/* 52:54 */       return response;
/* 53:   */     }
/* 54:   */     catch (Exception e)
/* 55:   */     {
/* 56:56 */       e.printStackTrace();
/* 57:57 */       throw new AS2Exception("com.asinfo.as2.webservices.MENSAJE_ERROR_203", new String[] { "" });
/* 58:   */     }
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.rest.SaldosFacturaServicioRest
 * JD-Core Version:    0.7.0.1
 */