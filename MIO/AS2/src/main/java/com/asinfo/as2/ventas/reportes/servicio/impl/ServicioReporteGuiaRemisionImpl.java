/*  1:   */ package com.asinfo.as2.ventas.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.ventas.ReporteGuiaRemisionDao;
/*  4:   */ import com.asinfo.as2.entities.GuiaRemision;
/*  5:   */ import com.asinfo.as2.entities.HojaRuta;
/*  6:   */ import com.asinfo.as2.entities.Transportista;
/*  7:   */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  8:   */ import com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision;
/*  9:   */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteGuiaRemision;
/* 10:   */ import java.math.BigDecimal;
/* 11:   */ import java.util.ArrayList;
/* 12:   */ import java.util.List;
/* 13:   */ import java.util.TreeMap;
/* 14:   */ import javax.ejb.EJB;
/* 15:   */ import javax.ejb.Stateless;
/* 16:   */ 
/* 17:   */ @Stateless
/* 18:   */ public class ServicioReporteGuiaRemisionImpl
/* 19:   */   implements ServicioReporteGuiaRemision
/* 20:   */ {
/* 21:   */   @EJB
/* 22:   */   private ReporteGuiaRemisionDao reporteGuiaRemisionDao;
/* 23:   */   @EJB
/* 24:   */   private ServicioGuiaRemision servicioGuiaRemision;
/* 25:   */   
/* 26:   */   public List getReporteGuiaRemision(int idDespachoCliente, int idTransferenciaBodega, int idGuiaRemision, TipoOrganizacion tipoOrganizacion, HojaRuta hojaRutaTransportista)
/* 27:   */   {
/* 28:45 */     GuiaRemision guiaRemision = this.servicioGuiaRemision.cargarDetalle(idGuiaRemision);
/* 29:   */     TreeMap<String, Object[]> tmFactura;
/* 30:47 */     if (tipoOrganizacion.equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA))
/* 31:   */     {
/* 32:48 */       List<Object[]> lista = this.reporteGuiaRemisionDao.getReporteGuiaRemision(idDespachoCliente, idTransferenciaBodega, idGuiaRemision, hojaRutaTransportista);
/* 33:49 */       if (hojaRutaTransportista == null)
/* 34:   */       {
/* 35:50 */         tmFactura = new TreeMap();
/* 36:51 */         for (Object[] object : lista)
/* 37:   */         {
/* 38:52 */           Object[] o = (Object[])tmFactura.get((String)object[15]);
/* 39:53 */           if (o == null)
/* 40:   */           {
/* 41:54 */             tmFactura.put((String)object[15], object);
/* 42:   */           }
/* 43:   */           else
/* 44:   */           {
/* 45:56 */             BigDecimal sumaCantidad = ((BigDecimal)o[20]).add((BigDecimal)object[20]);
/* 46:57 */             o[20] = sumaCantidad;
/* 47:   */           }
/* 48:   */         }
/* 49:60 */         return new ArrayList(tmFactura.values());
/* 50:   */       }
/* 51:63 */       return lista;
/* 52:   */     }
/* 53:66 */     if ((guiaRemision != null) && 
/* 54:67 */       (guiaRemision.getIdHojaRuta() != 0))
/* 55:   */     {
/* 56:68 */       List<Object[]> lista2 = this.reporteGuiaRemisionDao.getReporteGuiaRemision(idDespachoCliente, idTransferenciaBodega, idGuiaRemision, hojaRutaTransportista);
/* 57:69 */       for (Object[] object : lista2)
/* 58:   */       {
/* 59:70 */         if (object[2] == null) {
/* 60:71 */           object[2] = guiaRemision.getTransportista().getIdentificacion();
/* 61:   */         }
/* 62:73 */         object[27] = guiaRemision.getTransportista().getNombre();
/* 63:   */       }
/* 64:76 */       return lista2;
/* 65:   */     }
/* 66:80 */     return this.reporteGuiaRemisionDao.getReporteGuiaRemision(idDespachoCliente, idTransferenciaBodega, idGuiaRemision, hojaRutaTransportista);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.servicio.impl.ServicioReporteGuiaRemisionImpl
 * JD-Core Version:    0.7.0.1
 */