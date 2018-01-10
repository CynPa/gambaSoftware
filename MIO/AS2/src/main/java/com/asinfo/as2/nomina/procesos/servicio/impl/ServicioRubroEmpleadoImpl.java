/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.PagoRolDao;
/*   4:    */ import com.asinfo.as2.dao.PagoRolEmpleadoDao;
/*   5:    */ import com.asinfo.as2.dao.PagoRolEmpleadoRubroDao;
/*   6:    */ import com.asinfo.as2.dao.RubroDao;
/*   7:    */ import com.asinfo.as2.dao.RubroEmpleadoDao;
/*   8:    */ import com.asinfo.as2.entities.Empleado;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.PagoRol;
/*  11:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  12:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*  13:    */ import com.asinfo.as2.entities.Rubro;
/*  14:    */ import com.asinfo.as2.entities.RubroEmpleado;
/*  15:    */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  18:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado;
/*  19:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  20:    */ import java.math.BigDecimal;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.Iterator;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.ejb.Stateless;
/*  27:    */ 
/*  28:    */ @Stateless
/*  29:    */ public class ServicioRubroEmpleadoImpl
/*  30:    */   implements ServicioRubroEmpleado
/*  31:    */ {
/*  32:    */   @EJB
/*  33:    */   RubroEmpleadoDao rubroEmpleadoDao;
/*  34:    */   @EJB
/*  35:    */   private RubroDao rubroDao;
/*  36:    */   @EJB
/*  37:    */   private ServicioEmpleado servicioEmpleado;
/*  38:    */   @EJB
/*  39:    */   private PagoRolEmpleadoDao pagoRolEmpleadoDao;
/*  40:    */   @EJB
/*  41:    */   private PagoRolDao pagoRolDao;
/*  42:    */   @EJB
/*  43:    */   private PagoRolEmpleadoRubroDao pagoRolEmpleadoRubroDao;
/*  44:    */   
/*  45:    */   public void guardar(RubroEmpleado rubroEmpleado)
/*  46:    */   {
/*  47: 67 */     this.rubroEmpleadoDao.guardar(rubroEmpleado);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void eliminar(RubroEmpleado rubroEmpleado)
/*  51:    */   {
/*  52: 77 */     this.rubroEmpleadoDao.eliminar(rubroEmpleado);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public RubroEmpleado buscarPorId(int idRubroEmpleado)
/*  56:    */   {
/*  57: 88 */     return (RubroEmpleado)this.rubroEmpleadoDao.buscarPorId(Integer.valueOf(idRubroEmpleado));
/*  58:    */   }
/*  59:    */   
/*  60:    */   public List<RubroEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  61:    */   {
/*  62: 98 */     return this.rubroEmpleadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public List<RubroEmpleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  66:    */   {
/*  67:108 */     return this.rubroEmpleadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int contarPorCriterio(Map<String, String> filters)
/*  71:    */   {
/*  72:118 */     return this.rubroEmpleadoDao.contarPorCriterio(filters);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public RubroEmpleado cargarDetalle(int idRubroEmpleado)
/*  76:    */   {
/*  77:128 */     return this.rubroEmpleadoDao.cargarDetalle(idRubroEmpleado);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<RubroEmpleado> obtenerRubrosPorEmpleado(int idEmpleado)
/*  81:    */   {
/*  82:138 */     HashMap<String, String> filters = new HashMap();
/*  83:139 */     filters.put("empleado.idEmpleado", "" + idEmpleado);
/*  84:140 */     return this.rubroEmpleadoDao.obtenerListaCombo(null, true, filters);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List<RubroEmpleado> getListaRubroEmpleado(int idRubro, int idOrganizacion)
/*  88:    */   {
/*  89:150 */     return this.rubroEmpleadoDao.getListaRubroEmpleado(idRubro, idOrganizacion);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public BigDecimal obtenerSueldoPorEmpleado(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  93:    */   {
/*  94:160 */     HashMap<String, String> filters = new HashMap();
/*  95:161 */     filters.put("empleado.idEmpleado", "" + pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getIdEmpleado());
/*  96:162 */     filters.put("rubro.idRubro", "" + ParametrosSistema.getRubroSalarioUnificado(pagoRolEmpleadoRubro.getIdOrganizacion()));
/*  97:163 */     List<RubroEmpleado> lista = this.rubroEmpleadoDao.obtenerListaCombo(null, true, filters);
/*  98:164 */     if (!lista.isEmpty()) {
/*  99:165 */       return ((RubroEmpleado)lista.get(0)).getValor();
/* 100:    */     }
/* 101:167 */     return BigDecimal.ZERO;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Rubro rubroEmpleadoRetencionJudicial(Empleado empleado, TipoRubroEnum tipoRubro)
/* 105:    */   {
/* 106:173 */     return this.rubroEmpleadoDao.rubroEmpleadoRetencionJudicial(empleado, tipoRubro);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List<RubroEmpleado> getListaRubroEmpleadoFiniquito(PagoRolEmpleado pagoRolEmpleado, int anio, int mes)
/* 110:    */   {
/* 111:178 */     return this.rubroEmpleadoDao.getListaRubroEmpleadoFiniquito(pagoRolEmpleado, anio, mes);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void guardarEmpleadoAsignacionRubro(List<Empleado> listaEmpleadosAsignacionRubro, PagoRol pagoRol)
/* 115:    */   {
/* 116:184 */     Rubro rubroPropina = this.rubroDao.obtenerRubroPorTipoRubro(TipoRubroEnum.PAGO_PROPINA, ((Empleado)listaEmpleadosAsignacionRubro.get(0)).getEmpresa()
/* 117:185 */       .getIdOrganizacion());
/* 118:187 */     for (Empleado empleado : listaEmpleadosAsignacionRubro)
/* 119:    */     {
/* 120:188 */       RubroEmpleado rubroEmpleado = new RubroEmpleado();
/* 121:189 */       rubroEmpleado.setEmpleado(empleado);
/* 122:190 */       rubroEmpleado.setIdOrganizacion(empleado.getIdOrganizacion());
/* 123:191 */       rubroEmpleado.setIdSucursal(empleado.getEmpresa().getIdSucursal());
/* 124:192 */       rubroEmpleado.setRubro(rubroPropina);
/* 125:193 */       guardar(rubroEmpleado);
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void guardarListaRubroEmpleadoAsignarPagoRol(List<RubroEmpleado> listaRubroEmpleado, PagoRol pagoRol)
/* 130:    */   {
/* 131:199 */     HashMap<String, PagoRolEmpleado> hmEmpleado = new HashMap();
/* 132:200 */     Map<String, PagoRolEmpleadoRubro> hmRubro = new HashMap();
/* 133:201 */     pagoRol = this.pagoRolDao.cargarDetalle(pagoRol.getIdPagoRol());
/* 134:202 */     for (Iterator localIterator1 = pagoRol.getListaPagoRolEmpleado().iterator(); localIterator1.hasNext();)
/* 135:    */     {
/* 136:202 */       pagoRolEmpleado = (PagoRolEmpleado)localIterator1.next();
/* 137:203 */       hmEmpleado.put(pagoRolEmpleado.getEmpleado().getEmpresa().getIdentificacion(), pagoRolEmpleado);
/* 138:204 */       for (PagoRolEmpleadoRubro prer : pagoRolEmpleado.getListaPagoRolEmpleadoRubro()) {
/* 139:205 */         hmRubro.put(pagoRolEmpleado.getEmpleado().getId() + "~" + prer.getRubro().getId(), prer);
/* 140:    */       }
/* 141:    */     }
/* 142:    */     PagoRolEmpleado pagoRolEmpleado;
/* 143:209 */     for (RubroEmpleado rubroEmpleado : listaRubroEmpleado)
/* 144:    */     {
/* 145:210 */       Object filters = new HashMap();
/* 146:211 */       ((Map)filters).put("empleado.idEmpleado", rubroEmpleado.getEmpleado().getId() + "");
/* 147:212 */       ((Map)filters).put("rubro.idRubro", rubroEmpleado.getRubro().getId() + "");
/* 148:213 */       List<RubroEmpleado> listaRubroEmpleadoTemp = this.rubroEmpleadoDao.obtenerListaCombo(null, false, (Map)filters);
/* 149:214 */       if (listaRubroEmpleadoTemp.isEmpty()) {
/* 150:215 */         guardar(rubroEmpleado);
/* 151:    */       } else {
/* 152:217 */         rubroEmpleado = (RubroEmpleado)listaRubroEmpleadoTemp.get(0);
/* 153:    */       }
/* 154:220 */       PagoRolEmpleado pagoRolEmpleado = (PagoRolEmpleado)hmEmpleado.get(rubroEmpleado.getEmpleado().getEmpresa().getIdentificacion());
/* 155:221 */       PagoRolEmpleadoRubro verificarRubro = (PagoRolEmpleadoRubro)hmRubro.get(rubroEmpleado.getEmpleado().getId() + "~" + rubroEmpleado.getRubro().getId());
/* 156:222 */       if ((pagoRolEmpleado != null) && (verificarRubro == null))
/* 157:    */       {
/* 158:223 */         PagoRolEmpleadoRubro prer = new PagoRolEmpleadoRubro();
/* 159:224 */         prer.setIdOrganizacion(pagoRol.getIdOrganizacion());
/* 160:225 */         prer.setIdSucursal(pagoRol.getIdSucursal());
/* 161:226 */         prer.setPagoRolEmpleado(pagoRolEmpleado);
/* 162:227 */         prer.setIndicadorImpresionSobre(true);
/* 163:228 */         prer.setIndicadorTiempo(true);
/* 164:229 */         prer.setRubro(rubroEmpleado.getRubro());
/* 165:230 */         this.pagoRolEmpleadoRubroDao.guardar(prer);
/* 166:    */       }
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<RubroEmpleado> getGenerarRubroEmpleado(List<Rubro> listaRubro, int idOrganizacion)
/* 171:    */     throws ExcepcionAS2Financiero
/* 172:    */   {
/* 173:237 */     return this.rubroEmpleadoDao.getRubroEmpleado(listaRubro, idOrganizacion);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public List<Empleado> getEmpleadoSinRubro(List<Rubro> listaRubro, int idOrganizacion)
/* 177:    */     throws ExcepcionAS2Financiero
/* 178:    */   {
/* 179:242 */     return this.rubroEmpleadoDao.getEmpleadoSinRubro(listaRubro, idOrganizacion);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public List<RubroEmpleado> obtenerRubroPorFormula(int idOrganizacion, Empleado empleado, String formula)
/* 183:    */   {
/* 184:247 */     return this.rubroEmpleadoDao.obtenerRubroPorFormula(idOrganizacion, empleado, formula);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public BigDecimal obtenerSueldoPorEmpleado(Empleado empleado)
/* 188:    */   {
/* 189:257 */     HashMap<String, String> filters = new HashMap();
/* 190:258 */     filters.put("empleado.idEmpleado", "" + empleado.getId());
/* 191:259 */     filters.put("rubro.idRubro", "" + ParametrosSistema.getRubroSalarioUnificado(empleado.getIdOrganizacion()));
/* 192:260 */     List<RubroEmpleado> lista = this.rubroEmpleadoDao.obtenerListaCombo(null, true, filters);
/* 193:261 */     if (!lista.isEmpty()) {
/* 194:262 */       return ((RubroEmpleado)lista.get(0)).getValor();
/* 195:    */     }
/* 196:264 */     return BigDecimal.ZERO;
/* 197:    */   }
/* 198:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioRubroEmpleadoImpl
 * JD-Core Version:    0.7.0.1
 */