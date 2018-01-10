/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DetalleVacacionDao;
/*   4:    */ import com.asinfo.as2.dao.VacacionDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   6:    */ import com.asinfo.as2.entities.DetalleVacacion;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Vacacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  12:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  13:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioDetalleVacacion;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.ejb.Stateless;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class ServicioDetalleVacacionImpl
/*  21:    */   implements ServicioDetalleVacacion
/*  22:    */ {
/*  23:    */   @EJB
/*  24:    */   private DetalleVacacionDao detalleVacacionDao;
/*  25:    */   @EJB
/*  26:    */   private VacacionDao vacacionDao;
/*  27:    */   @EJB
/*  28:    */   private transient ServicioSecuencia servicioSecuencia;
/*  29:    */   
/*  30:    */   public void guardar(DetalleVacacion detalleVacacion)
/*  31:    */     throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception
/*  32:    */   {
/*  33: 57 */     cargarSecuencia(detalleVacacion);
/*  34: 58 */     validar(detalleVacacion);
/*  35: 59 */     actualizarDiasTomados(detalleVacacion);
/*  36: 60 */     this.detalleVacacionDao.guardar(detalleVacacion);
/*  37: 61 */     if (Estado.APROBADO.equals(detalleVacacion.getEstado())) {
/*  38: 62 */       this.vacacionDao.guardar(detalleVacacion.getVacacion());
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void cargarSecuencia(DetalleVacacion detalleVacacion)
/*  43:    */     throws ExcepcionAS2, ExcepcionAS2Inventario
/*  44:    */   {
/*  45: 68 */     if ((detalleVacacion.getNumero() == null) || (detalleVacacion.getNumero().equals("")))
/*  46:    */     {
/*  47: 69 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(detalleVacacion.getDocumento().getIdDocumento(), detalleVacacion
/*  48: 70 */         .getFechaFin());
/*  49: 71 */       detalleVacacion.setNumero(numero);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void eliminar(DetalleVacacion detalleVacacion)
/*  54:    */   {
/*  55: 83 */     this.detalleVacacionDao.eliminar(detalleVacacion);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public DetalleVacacion buscarPorId(int idDetalleVacacion)
/*  59:    */   {
/*  60: 96 */     return (DetalleVacacion)this.detalleVacacionDao.buscarPorId(Integer.valueOf(idDetalleVacacion));
/*  61:    */   }
/*  62:    */   
/*  63:    */   public List<DetalleVacacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  64:    */   {
/*  65:108 */     return this.detalleVacacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<DetalleVacacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  69:    */   {
/*  70:119 */     return this.detalleVacacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int contarPorCriterio(Map<String, String> filters)
/*  74:    */   {
/*  75:130 */     return this.detalleVacacionDao.contarPorCriterio(filters);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public DetalleVacacion cargarDetalle(int idDetalleVacacion)
/*  79:    */   {
/*  80:142 */     return this.detalleVacacionDao.cargarDetalle(idDetalleVacacion);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public List<Vacacion> getListaVacacion(int idEmpleado, int idOrganizacion)
/*  84:    */   {
/*  85:153 */     return getListaVacacion(idEmpleado, idOrganizacion, null);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public List<Vacacion> getListaVacacion(int idEmpleado, int idOrganizacion, DetalleVacacion detalleVacacion)
/*  89:    */   {
/*  90:158 */     return this.vacacionDao.getListaVacacion(idEmpleado, idOrganizacion, detalleVacacion);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int totalDiasSolicitadosPorEmpleadoPorVacacion(int idDetalleVacacion, int idVacacion)
/*  94:    */   {
/*  95:166 */     return this.detalleVacacionDao.totalDiasSolicitadosPorEmpleadoPorVacacion(idDetalleVacacion, idVacacion);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public List getReporteSolicitudVacacion(int idDetalleVacacion, int idOrganizacion)
/*  99:    */   {
/* 100:171 */     return this.detalleVacacionDao.getReporteSolicitudVacacion(idDetalleVacacion, idOrganizacion);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public List<Vacacion> obtenerListaVacacionesPorDetalle(int idDetalleVacacion)
/* 104:    */   {
/* 105:175 */     return this.detalleVacacionDao.obtenerListaVacacionesPorDetalle(idDetalleVacacion);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean verificarDetalleVacacionDuplicado(DetalleVacacion detalleVacacion)
/* 109:    */   {
/* 110:180 */     if (this.detalleVacacionDao.verificarDetalleVacacionDuplicado(detalleVacacion) >= 1L) {
/* 111:181 */       return true;
/* 112:    */     }
/* 113:183 */     return false;
/* 114:    */   }
/* 115:    */   
/* 116:    */   private void actualizarDiasTomados(DetalleVacacion detalleVacacion)
/* 117:    */   {
/* 118:187 */     if (Estado.APROBADO.equals(detalleVacacion.getEstado()))
/* 119:    */     {
/* 120:188 */       int diasTomados = detalleVacacion.getVacacion().getDiasTomados();
/* 121:189 */       diasTomados -= detalleVacacion.getDiasTomadosOld();
/* 122:190 */       detalleVacacion.getVacacion().setDiasTomados(diasTomados + detalleVacacion.getDiasTomados());
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void aprobarVacacion(DetalleVacacion detalleVacacion)
/* 127:    */     throws AS2Exception
/* 128:    */   {
/* 129:196 */     validar(detalleVacacion);
/* 130:197 */     detalleVacacion.getVacacion().setDiasTomados(detalleVacacion.getVacacion().getDiasTomados() + detalleVacacion.getDiasTomados());
/* 131:198 */     detalleVacacion.setEstado(Estado.APROBADO);
/* 132:199 */     this.detalleVacacionDao.guardar(detalleVacacion);
/* 133:200 */     this.vacacionDao.guardar(detalleVacacion.getVacacion());
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getTotalDiasSolicitadosPorPeriodo(DetalleVacacion detalleVacacion)
/* 137:    */   {
/* 138:205 */     int idDetalleVacacion = detalleVacacion.getIdDetalleVacacion();
/* 139:206 */     int idVacacion = detalleVacacion.getVacacion().getIdVacacion();
/* 140:207 */     return totalDiasSolicitadosPorEmpleadoPorVacacion(idDetalleVacacion, idVacacion);
/* 141:    */   }
/* 142:    */   
/* 143:    */   private void validar(DetalleVacacion detalleVacacion)
/* 144:    */     throws AS2Exception
/* 145:    */   {
/* 146:211 */     if (verificarDetalleVacacionDuplicado(detalleVacacion)) {
/* 147:212 */       throw new AS2Exception("msg_error_detalle_vacacion_duplicado", new String[] { "" });
/* 148:    */     }
/* 149:214 */     int diasTomados = detalleVacacion.getVacacion().getDiasTomados();
/* 150:215 */     if (Estado.APROBADO.equals(detalleVacacion.getEstado())) {
/* 151:216 */       diasTomados -= detalleVacacion.getDiasTomadosOld();
/* 152:    */     }
/* 153:218 */     int diasSolicitados = diasTomados + detalleVacacion.getDiasTomados() + getTotalDiasSolicitadosPorPeriodo(detalleVacacion);
/* 154:219 */     int diasDisponibles = detalleVacacion.getVacacion().getDias() + detalleVacacion.getVacacion().getDiasAdicionales();
/* 155:220 */     if (diasDisponibles < diasSolicitados) {
/* 156:221 */       throw new AS2Exception("msg_error_dias_vacacion", new String[] { String.valueOf(diasSolicitados), String.valueOf(diasDisponibles) });
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void cerrarVacacion(DetalleVacacion detalleVacacion)
/* 161:    */     throws AS2Exception
/* 162:    */   {
/* 163:227 */     validar(detalleVacacion);
/* 164:228 */     detalleVacacion.setEstado(Estado.CERRADO);
/* 165:229 */     this.detalleVacacionDao.guardar(detalleVacacion);
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioDetalleVacacionImpl
 * JD-Core Version:    0.7.0.1
 */