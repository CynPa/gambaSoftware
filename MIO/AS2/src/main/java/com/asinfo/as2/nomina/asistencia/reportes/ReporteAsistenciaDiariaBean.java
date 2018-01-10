/*  1:   */ package com.asinfo.as2.nomina.asistencia.reportes;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*  4:   */ import com.asinfo.as2.entities.Departamento;
/*  5:   */ import com.asinfo.as2.entities.Organizacion;
/*  6:   */ import com.asinfo.as2.nomina.asistencia.procesos.RegistroAsistenciaBean;
/*  7:   */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  8:   */ import com.asinfo.as2.util.AppUtil;
/*  9:   */ import java.util.HashMap;
/* 10:   */ import java.util.List;
/* 11:   */ import java.util.Map;
/* 12:   */ import javax.annotation.PostConstruct;
/* 13:   */ import javax.ejb.EJB;
/* 14:   */ import javax.faces.bean.ManagedBean;
/* 15:   */ import javax.faces.bean.ViewScoped;
/* 16:   */ 
/* 17:   */ @ManagedBean
/* 18:   */ @ViewScoped
/* 19:   */ public class ReporteAsistenciaDiariaBean
/* 20:   */   extends RegistroAsistenciaBean
/* 21:   */ {
/* 22:   */   private static final long serialVersionUID = 1L;
/* 23:   */   @EJB
/* 24:   */   private ServicioUsuario servicioUsuario;
/* 25:   */   
/* 26:   */   public void fechasIguales()
/* 27:   */   {
/* 28:33 */     setFechaHasta(getFechaDesde());
/* 29:   */   }
/* 30:   */   
/* 31:   */   @PostConstruct
/* 32:   */   public void init()
/* 33:   */   {
/* 34:38 */     super.init();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public List<Departamento> getListaDepartamento()
/* 38:   */   {
/* 39:44 */     Map<String, String> filtros = new HashMap();
/* 40:45 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 41:46 */     return this.servicioDepartamento.obtenerListaCombo("nombre", true, filtros);
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.reportes.ReporteAsistenciaDiariaBean
 * JD-Core Version:    0.7.0.1
 */