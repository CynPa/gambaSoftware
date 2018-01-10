/*  1:   */ package com.asinfo.as2.entities.mantenimiento;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class CalendarioMantenimiento
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 1L;
/*  9:   */   private Equipo equipo;
/* 10:   */   private DetallePlanMantenimiento detallePlanMantenimiento;
/* 11:   */   private CalendarioMantenimientoEntidad[] arregloCalendarioMantenimientoEntidad;
/* 12:   */   private Boolean[] arregloIndicadorMantenimiento;
/* 13:   */   private OrdenTrabajoMantenimiento[] arregloOrdenTrabajo;
/* 14:   */   private Boolean[] arregloIndicadorGenerarOT;
/* 15:   */   
/* 16:   */   public CalendarioMantenimiento(int cantidadDias)
/* 17:   */   {
/* 18:19 */     this.arregloIndicadorMantenimiento = new Boolean[cantidadDias];
/* 19:20 */     this.arregloOrdenTrabajo = new OrdenTrabajoMantenimiento[cantidadDias];
/* 20:21 */     this.arregloIndicadorGenerarOT = new Boolean[cantidadDias];
/* 21:22 */     this.arregloCalendarioMantenimientoEntidad = new CalendarioMantenimientoEntidad[cantidadDias];
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Equipo getEquipo()
/* 25:   */   {
/* 26:26 */     return this.equipo;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setEquipo(Equipo equipo)
/* 30:   */   {
/* 31:30 */     this.equipo = equipo;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public DetallePlanMantenimiento getDetallePlanMantenimiento()
/* 35:   */   {
/* 36:34 */     return this.detallePlanMantenimiento;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setDetallePlanMantenimiento(DetallePlanMantenimiento detallePlanMantenimiento)
/* 40:   */   {
/* 41:38 */     this.detallePlanMantenimiento = detallePlanMantenimiento;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public Boolean[] getArregloIndicadorMantenimiento()
/* 45:   */   {
/* 46:42 */     return this.arregloIndicadorMantenimiento;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void setArregloIndicadorMantenimiento(Boolean[] arregloIndicadorMantenimiento)
/* 50:   */   {
/* 51:46 */     this.arregloIndicadorMantenimiento = arregloIndicadorMantenimiento;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public OrdenTrabajoMantenimiento[] getArregloOrdenTrabajo()
/* 55:   */   {
/* 56:50 */     return this.arregloOrdenTrabajo;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void setArregloOrdenTrabajo(OrdenTrabajoMantenimiento[] arregloOrdenTrabajo)
/* 60:   */   {
/* 61:54 */     this.arregloOrdenTrabajo = arregloOrdenTrabajo;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public Boolean[] getArregloIndicadorGenerarOT()
/* 65:   */   {
/* 66:58 */     return this.arregloIndicadorGenerarOT;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public void setArregloIndicadorGenerarOT(Boolean[] arregloIndicadorGenerarOT)
/* 70:   */   {
/* 71:62 */     this.arregloIndicadorGenerarOT = arregloIndicadorGenerarOT;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public CalendarioMantenimientoEntidad[] getArregloCalendarioMantenimientoEntidad()
/* 75:   */   {
/* 76:66 */     return this.arregloCalendarioMantenimientoEntidad;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public void setArregloCalendarioMantenimientoEntidad(CalendarioMantenimientoEntidad[] arregloCalendarioMantenimientoEntidad)
/* 80:   */   {
/* 81:70 */     this.arregloCalendarioMantenimientoEntidad = arregloCalendarioMantenimientoEntidad;
/* 82:   */   }
/* 83:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.CalendarioMantenimiento
 * JD-Core Version:    0.7.0.1
 */