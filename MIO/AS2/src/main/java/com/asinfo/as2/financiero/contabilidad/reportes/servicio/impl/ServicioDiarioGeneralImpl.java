/*  1:   */ package com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AsientoDao;
/*  4:   */ import com.asinfo.as2.entities.Asiento;
/*  5:   */ import com.asinfo.as2.entities.DetalleAsiento;
/*  6:   */ import com.asinfo.as2.entities.TipoAsiento;
/*  7:   */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioDiarioGeneral;
/*  8:   */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  9:   */ import com.asinfo.as2.utils.FuncionesUtiles;
/* 10:   */ import java.util.ArrayList;
/* 11:   */ import java.util.Date;
/* 12:   */ import java.util.List;
/* 13:   */ import java.util.TreeMap;
/* 14:   */ import javax.ejb.EJB;
/* 15:   */ import javax.ejb.Stateless;
/* 16:   */ 
/* 17:   */ @Stateless
/* 18:   */ public class ServicioDiarioGeneralImpl
/* 19:   */   implements ServicioDiarioGeneral
/* 20:   */ {
/* 21:   */   @EJB
/* 22:   */   private AsientoDao asientoDao;
/* 23:   */   
/* 24:   */   public List<Asiento> cargarDatos(Date fechaDesde, Date fechaHasta, int idSucursal, int idTipoAsiento, Asiento asientoFiltro)
/* 25:   */     throws ExcepcionAS2Financiero
/* 26:   */   {
/* 27:54 */     TreeMap<String, Asiento> tmAsiento = new TreeMap();
/* 28:56 */     for (DetalleAsiento detalleAsiento : this.asientoDao.obtenerListaAsiento(fechaDesde, fechaHasta, idSucursal, idTipoAsiento, asientoFiltro))
/* 29:   */     {
/* 30:58 */       int anio = FuncionesUtiles.getAnio(detalleAsiento.getAsiento().getFecha());
/* 31:59 */       int mes = FuncionesUtiles.getMes(detalleAsiento.getAsiento().getFecha());
/* 32:60 */       int dia = FuncionesUtiles.getDiaFecha(detalleAsiento.getAsiento().getFecha());
/* 33:   */       
/* 34:62 */       String clave = anio + "-" + mes + "-" + dia + detalleAsiento.getAsiento().getTipoAsiento().getNombre() + "-" + detalleAsiento.getAsiento().getNumero();
/* 35:   */       
/* 36:64 */       Asiento asiento = (Asiento)tmAsiento.get(clave);
/* 37:66 */       if (asiento == null)
/* 38:   */       {
/* 39:67 */         asiento = new Asiento();
/* 40:68 */         asiento = detalleAsiento.getAsiento();
/* 41:69 */         asiento.setListaDetalleAsiento(new ArrayList());
/* 42:70 */         tmAsiento.put(clave, asiento);
/* 43:   */       }
/* 44:73 */       asiento.getListaDetalleAsiento().add(detalleAsiento);
/* 45:   */     }
/* 46:77 */     Object listaAsiento = new ArrayList(tmAsiento.values());
/* 47:78 */     return listaAsiento;
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl.ServicioDiarioGeneralImpl
 * JD-Core Version:    0.7.0.1
 */