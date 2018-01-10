/*  1:   */ package com.asinfo.as2.rs.datosbase.rest;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.HojaRutaDao;
/*  4:   */ import com.asinfo.as2.dao.RutaVendeorDao;
/*  5:   */ import com.asinfo.as2.entities.Cliente;
/*  6:   */ import com.asinfo.as2.enumeraciones.DiaSemanaEnum;
/*  7:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  8:   */ import com.asinfo.as2.rs.datosbase.dto.HojaRutaDiariaRequestDto;
/*  9:   */ import com.asinfo.as2.rs.datosbase.dto.HojaRutaDiariaResponseDto;
/* 10:   */ import java.text.SimpleDateFormat;
/* 11:   */ import java.util.ArrayList;
/* 12:   */ import java.util.Calendar;
/* 13:   */ import java.util.Date;
/* 14:   */ import java.util.HashSet;
/* 15:   */ import java.util.List;
/* 16:   */ import java.util.Set;
/* 17:   */ import javax.ejb.EJB;
/* 18:   */ import javax.ws.rs.Consumes;
/* 19:   */ import javax.ws.rs.POST;
/* 20:   */ import javax.ws.rs.Path;
/* 21:   */ import javax.ws.rs.Produces;
/* 22:   */ 
/* 23:   */ @Path("/datosBase")
/* 24:   */ public class HojaRutaDiariaServicioRest
/* 25:   */ {
/* 26:   */   @EJB
/* 27:   */   private HojaRutaDao hojaRutaDao;
/* 28:   */   @EJB
/* 29:   */   private RutaVendeorDao vendedorSectorDao;
/* 30:   */   
/* 31:   */   @POST
/* 32:   */   @Path("/consultarHojaRutaPorUsuarioYFecha")
/* 33:   */   @Consumes({"application/json"})
/* 34:   */   @Produces({"application/json"})
/* 35:   */   public List<HojaRutaDiariaResponseDto> consultarClientesPorusuario(HojaRutaDiariaRequestDto request)
/* 36:   */     throws AS2Exception
/* 37:   */   {
/* 38:   */     try
/* 39:   */     {
/* 40:39 */       List<HojaRutaDiariaResponseDto> response = new ArrayList();
/* 41:   */       
/* 42:41 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/* 43:   */       
/* 44:43 */       Date fecha = sdf.parse(request.getFecha());
/* 45:44 */       int idUsuario = request.getIdUsuario();
/* 46:45 */       Set<Integer> setIdCliente = new HashSet();
/* 47:   */       
/* 48:47 */       List<Cliente> listaClientesHojaRutaTransportista = this.hojaRutaDao.getDetallesHojaRutaPorFechaYTransportista(fecha, idUsuario);
/* 49:   */       
/* 50:49 */       Calendar calendar = Calendar.getInstance();
/* 51:50 */       calendar.setTime(fecha);
/* 52:51 */       calendar.setFirstDayOfWeek(1);
/* 53:52 */       int diaSem = calendar.get(7);
/* 54:53 */       DiaSemanaEnum diaSemana = DiaSemanaEnum.values()[(diaSem - 1)];
/* 55:   */       
/* 56:55 */       List<Cliente> listaClienteVendedorSector = this.vendedorSectorDao.buscarClientesPorDiaSemanaVendedor(idUsuario, diaSemana);
/* 57:   */       
/* 58:57 */       listaClientesHojaRutaTransportista.addAll(listaClienteVendedorSector);
/* 59:59 */       for (Cliente cliente : listaClientesHojaRutaTransportista) {
/* 60:60 */         if (!setIdCliente.contains(Integer.valueOf(cliente.getId())))
/* 61:   */         {
/* 62:61 */           HojaRutaDiariaResponseDto hojaRutaDiariaResponseDto = new HojaRutaDiariaResponseDto();
/* 63:62 */           hojaRutaDiariaResponseDto.setFecha(sdf.format(fecha));
/* 64:63 */           hojaRutaDiariaResponseDto.setIdClienteFinal(cliente.getId());
/* 65:64 */           response.add(hojaRutaDiariaResponseDto);
/* 66:65 */           setIdCliente.add(Integer.valueOf(cliente.getId()));
/* 67:   */         }
/* 68:   */       }
/* 69:69 */       return response;
/* 70:   */     }
/* 71:   */     catch (Exception e)
/* 72:   */     {
/* 73:71 */       e.printStackTrace();
/* 74:72 */       throw new AS2Exception(e.getMessage());
/* 75:   */     }
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.rest.HojaRutaDiariaServicioRest
 * JD-Core Version:    0.7.0.1
 */