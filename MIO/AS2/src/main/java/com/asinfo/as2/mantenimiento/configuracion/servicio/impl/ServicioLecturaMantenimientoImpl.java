/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.mantenimiento.EquipoDao;
/*   4:    */ import com.asinfo.as2.dao.mantenimiento.LecturaMantenimientoDao;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.LecturaMantenimiento;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioLecturaMantenimiento;
/*  12:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  13:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  14:    */ import java.math.BigDecimal;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.ejb.SessionContext;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class ServicioLecturaMantenimientoImpl
/*  25:    */   extends AbstractServicioAS2
/*  26:    */   implements ServicioLecturaMantenimiento
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @EJB
/*  30:    */   private transient LecturaMantenimientoDao lecturaMantenimientoDao;
/*  31:    */   @EJB
/*  32:    */   private ServicioGenerico<ComponenteEquipo> servicioComponenteEquipo;
/*  33:    */   @EJB
/*  34:    */   private transient EquipoDao equipoDao;
/*  35:    */   
/*  36:    */   public void guardar(LecturaMantenimiento lecturaMantenimiento)
/*  37:    */     throws AS2ExceptionMantenimiento
/*  38:    */   {
/*  39:    */     try
/*  40:    */     {
/*  41: 59 */       this.lecturaMantenimientoDao.guardar(lecturaMantenimiento);
/*  42:    */     }
/*  43:    */     catch (Exception e)
/*  44:    */     {
/*  45: 61 */       this.context.setRollbackOnly();
/*  46: 62 */       e.printStackTrace();
/*  47: 63 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void eliminar(LecturaMantenimiento lecturaMantenimiento)
/*  52:    */     throws ExcepcionAS2, AS2ExceptionMantenimiento
/*  53:    */   {
/*  54:    */     try
/*  55:    */     {
/*  56: 70 */       if (lecturaMantenimiento.isIndicadorAutomatico()) {
/*  57: 71 */         throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioLecturaMantenimientoImpl.ERROR_ELIMINAR_LECTURA", new String[] { "LECTURA", "" + lecturaMantenimiento.isIndicadorAutomatico() });
/*  58:    */       }
/*  59: 73 */       this.lecturaMantenimientoDao.eliminar(lecturaMantenimiento);
/*  60:    */     }
/*  61:    */     catch (Exception e)
/*  62:    */     {
/*  63: 76 */       this.context.setRollbackOnly();
/*  64: 77 */       e.printStackTrace();
/*  65: 78 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public LecturaMantenimiento buscarPorId(Integer id)
/*  70:    */   {
/*  71: 86 */     return null;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<LecturaMantenimiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  75:    */   {
/*  76: 92 */     return this.lecturaMantenimientoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<LecturaMantenimiento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/*  80:    */   {
/*  81: 97 */     return this.lecturaMantenimientoDao.obtenerListaCombo(sortField, sortOrder, filtros);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int contarPorCriterio(Map<String, String> filters)
/*  85:    */   {
/*  86:102 */     return this.lecturaMantenimientoDao.contarPorCriterio(filters);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public LecturaMantenimiento cargarDetalle(LecturaMantenimiento lecturaMantenimiento)
/*  90:    */   {
/*  91:108 */     return null;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void guardar(LecturaMantenimiento lecturaMantenimiento, List<LecturaMantenimiento> listaLectura)
/*  95:    */     throws ExcepcionAS2, AS2Exception, AS2ExceptionMantenimiento
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:115 */       validaListaLecturaMantenimiento(listaLectura);
/* 100:116 */       if (!listaLectura.isEmpty())
/* 101:    */       {
/* 102:117 */         boolean min = true;
/* 103:118 */         for (LecturaMantenimiento lM : listaLectura) {
/* 104:119 */           if (lM.getFrecuencia() != null)
/* 105:    */           {
/* 106:120 */             if (lM.getValor().compareTo(BigDecimal.ZERO) > 0)
/* 107:    */             {
/* 108:121 */               this.lecturaMantenimientoDao.guardar(lM);
/* 109:122 */               min = false;
/* 110:    */             }
/* 111:    */           }
/* 112:    */           else {
/* 113:125 */             LOG.error("LECTURA SIN FRECUENCIA-->NO SE GUARDA");
/* 114:    */           }
/* 115:    */         }
/* 116:128 */         if (min) {
/* 117:129 */           throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioLecturaMantenimientoImpl.ERROR_NINGUN_VALOR", new String[] { "VALOR" });
/* 118:    */         }
/* 119:    */       }
/* 120:    */       else
/* 121:    */       {
/* 122:132 */         LOG.error("LECTURA SIN VALOR-->NO SE GUARDA");
/* 123:133 */         throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioLecturaMantenimientoImpl.ERROR_LISTA_COMPONENTES_VACIA", new String[] { "COMPONENTE" });
/* 124:    */       }
/* 125:    */     }
/* 126:    */     catch (Exception e)
/* 127:    */     {
/* 128:136 */       this.context.setRollbackOnly();
/* 129:137 */       e.printStackTrace();
/* 130:138 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void actualizarValoresLecturaMantenimiento(LecturaMantenimiento lecturaMantenimiento, boolean buscarValoresAnteriores)
/* 135:    */   {
/* 136:145 */     if (lecturaMantenimiento.getFechaLectura() != null) {
/* 137:148 */       if (buscarValoresAnteriores)
/* 138:    */       {
/* 139:154 */         LecturaMantenimiento ultimaLectura = this.lecturaMantenimientoDao.obtenerUltimaLectura(lecturaMantenimiento.getEquipo(), lecturaMantenimiento
/* 140:155 */           .getComponenteEquipo(), null, lecturaMantenimiento.getFrecuencia(), true);
/* 141:    */         
/* 142:157 */         Long numeroLecturas = this.lecturaMantenimientoDao.countLecturas(lecturaMantenimiento.getEquipo(), lecturaMantenimiento
/* 143:158 */           .getComponenteEquipo(), null, lecturaMantenimiento.getFrecuencia(), true);
/* 144:    */         
/* 145:160 */         lecturaMantenimiento.setNumeroLecturas(numeroLecturas);
/* 146:161 */         lecturaMantenimiento.setFechaLecturaAnterior(ultimaLectura == null ? null : ultimaLectura.getFechaLectura());
/* 147:162 */         lecturaMantenimiento.setValorAnterior(ultimaLectura == null ? BigDecimal.ZERO : ultimaLectura.getValorAcumulado());
/* 148:    */       }
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<LecturaMantenimiento> obtenerLecturasEquipo(Equipo equipo)
/* 153:    */   {
/* 154:168 */     return this.lecturaMantenimientoDao.obtenerLecturasEquipo(equipo);
/* 155:    */   }
/* 156:    */   
/* 157:    */   private void validaListaLecturaMantenimiento(List<LecturaMantenimiento> listaLectura)
/* 158:    */     throws AS2ExceptionMantenimiento
/* 159:    */   {
/* 160:172 */     for (LecturaMantenimiento lectura : listaLectura)
/* 161:    */     {
/* 162:173 */       LecturaMantenimiento lecturaMantenimiento = this.lecturaMantenimientoDao.obtenerUltimaLectura(lectura.getEquipo(), lectura
/* 163:174 */         .getComponenteEquipo(), null, null, true);
/* 164:175 */       if ((lecturaMantenimiento != null) && (lecturaMantenimiento.getFechaLectura().compareTo(lectura.getFechaLectura()) >= 0)) {
/* 165:176 */         throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioOrdenTrabajoMantenimientoImpl.ERROR_FECHA_CIERRE_MAYOR_FECHA_MAX_MANTENIMIENTO", new String[] { "" });
/* 166:    */       }
/* 167:    */     }
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.servicio.impl.ServicioLecturaMantenimientoImpl
 * JD-Core Version:    0.7.0.1
 */