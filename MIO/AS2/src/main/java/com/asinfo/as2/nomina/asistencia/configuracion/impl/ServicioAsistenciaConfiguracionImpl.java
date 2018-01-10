/*  1:   */ package com.asinfo.as2.nomina.asistencia.configuracion.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.GenericoDao;
/*  4:   */ import com.asinfo.as2.dao.nomina.asistencia.AsistenciaConfiguracionDao;
/*  5:   */ import com.asinfo.as2.dao.nomina.asistencia.HoraExtraDao;
/*  6:   */ import com.asinfo.as2.entities.Rubro;
/*  7:   */ import com.asinfo.as2.entities.nomina.asistencia.DiaFestivo;
/*  8:   */ import com.asinfo.as2.entities.nomina.asistencia.HoraExtra;
/*  9:   */ import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
/* 10:   */ import com.asinfo.as2.enumeraciones.PorCientoHoraExtra;
/* 11:   */ import com.asinfo.as2.nomina.asistencia.configuracion.ServicioAsistenciaConfiguracion;
/* 12:   */ import java.util.Calendar;
/* 13:   */ import java.util.Date;
/* 14:   */ import java.util.List;
/* 15:   */ import java.util.Map;
/* 16:   */ import javax.ejb.EJB;
/* 17:   */ import javax.ejb.Stateless;
/* 18:   */ 
/* 19:   */ @Stateless
/* 20:   */ public class ServicioAsistenciaConfiguracionImpl
/* 21:   */   implements ServicioAsistenciaConfiguracion
/* 22:   */ {
/* 23:   */   @EJB
/* 24:   */   private AsistenciaConfiguracionDao asistenciaConfiguracionDao;
/* 25:   */   @EJB
/* 26:   */   private GenericoDao<DiaFestivo> diaFestivoDao;
/* 27:   */   @EJB
/* 28:   */   private HoraExtraDao horaExtraDao;
/* 29:   */   
/* 30:   */   public void copiarDiasFestivos(Integer annoOrigen, Integer annoDestino)
/* 31:   */   {
/* 32:50 */     List<DiaFestivo> listaDiaFestivo = this.asistenciaConfiguracionDao.obtenerDiasFestivosARepetirPorAnno(annoOrigen.intValue());
/* 33:51 */     for (DiaFestivo diaFestivo : listaDiaFestivo)
/* 34:   */     {
/* 35:52 */       this.diaFestivoDao.detach(diaFestivo);
/* 36:   */       
/* 37:54 */       diaFestivo.setIdDiaFestivo(0);
/* 38:   */       
/* 39:56 */       Date fecha = diaFestivo.getFecha();
/* 40:57 */       Calendar calendar = Calendar.getInstance();
/* 41:58 */       calendar.setTime(fecha);
/* 42:59 */       calendar.set(1, annoDestino.intValue());
/* 43:60 */       diaFestivo.setFecha(calendar.getTime());
/* 44:   */       
/* 45:62 */       this.diaFestivoDao.guardar(diaFestivo);
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public HorarioEmpleado cargarDetalleHorarioEmpleado(HorarioEmpleado horarioEmpleado)
/* 50:   */   {
/* 51:68 */     return this.asistenciaConfiguracionDao.cargarDetalleHorarioEmpleado(horarioEmpleado);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public boolean esDiaFestivo(int idOrganizacion, Date fecha)
/* 55:   */   {
/* 56:73 */     return this.asistenciaConfiguracionDao.esDiaFestivo(idOrganizacion, fecha);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public HoraExtra cargarDetalleHoraExtra(HoraExtra horaExtra)
/* 60:   */   {
/* 61:78 */     return this.horaExtraDao.cargarDetalle(horaExtra);
/* 62:   */   }
/* 63:   */   
/* 64:   */   public List<HoraExtra> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 65:   */   {
/* 66:83 */     return this.horaExtraDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 67:   */   }
/* 68:   */   
/* 69:   */   public Rubro buscarRubroHoraExtra(int idOrganizacion, PorCientoHoraExtra porCiento)
/* 70:   */   {
/* 71:88 */     return this.horaExtraDao.buscarRubroHoraExtra(idOrganizacion, porCiento);
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.configuracion.impl.ServicioAsistenciaConfiguracionImpl
 * JD-Core Version:    0.7.0.1
 */