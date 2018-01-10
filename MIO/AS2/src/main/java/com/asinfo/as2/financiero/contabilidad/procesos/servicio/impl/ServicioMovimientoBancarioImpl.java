/*   1:    */ package com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   5:    */ import com.asinfo.as2.dao.MovimientoBancarioDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.entities.Asiento;
/*   8:    */ import com.asinfo.as2.entities.ConceptoContable;
/*   9:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*  10:    */ import com.asinfo.as2.entities.CuentaContable;
/*  11:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  14:    */ import com.asinfo.as2.entities.EstadoCheque;
/*  15:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*  16:    */ import com.asinfo.as2.entities.MovimientoBancarioEstadoCheque;
/*  17:    */ import com.asinfo.as2.entities.Organizacion;
/*  18:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  19:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  20:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  21:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  22:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  23:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  24:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  25:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  26:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  27:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  28:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioMovimientoBancario;
/*  29:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  30:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  31:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  32:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  33:    */ import com.asinfo.as2.util.AppUtil;
/*  34:    */ import java.math.BigDecimal;
/*  35:    */ import java.util.ArrayList;
/*  36:    */ import java.util.Date;
/*  37:    */ import java.util.List;
/*  38:    */ import java.util.Map;
/*  39:    */ import javax.ejb.EJB;
/*  40:    */ import javax.ejb.SessionContext;
/*  41:    */ import javax.ejb.Stateless;
/*  42:    */ import javax.ejb.TransactionAttribute;
/*  43:    */ import javax.ejb.TransactionAttributeType;
/*  44:    */ 
/*  45:    */ @Stateless
/*  46:    */ public class ServicioMovimientoBancarioImpl
/*  47:    */   extends AbstractServicioAS2Financiero
/*  48:    */   implements ServicioMovimientoBancario
/*  49:    */ {
/*  50:    */   private static final long serialVersionUID = 8925108117464595714L;
/*  51:    */   @EJB
/*  52:    */   private transient MovimientoBancarioDao movimientoBancarioDao;
/*  53:    */   @EJB
/*  54:    */   private transient ServicioDocumento servicioDocumento;
/*  55:    */   @EJB
/*  56:    */   private transient ServicioPeriodo servicioPeriodo;
/*  57:    */   @EJB
/*  58:    */   private transient ServicioAsiento servicioAsiento;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  61:    */   @EJB
/*  62:    */   private transient ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  63:    */   @EJB
/*  64:    */   private transient ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  65:    */   @EJB
/*  66:    */   private transient ServicioGenerico<MovimientoBancarioEstadoCheque> servicioMBEC;
/*  67:    */   
/*  68:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  69:    */   public void guardar(MovimientoBancario movimientoBancario)
/*  70:    */     throws Exception
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74: 86 */       movimientoBancario.setDocumento(this.servicioDocumento.buscarPorId(Integer.valueOf(movimientoBancario.getDocumento().getId())));
/*  75:    */       
/*  76: 88 */       this.movimientoBancarioDao.guardar(movimientoBancario);
/*  77:    */       
/*  78:    */ 
/*  79: 91 */       contabilizar(movimientoBancario);
/*  80:    */     }
/*  81:    */     catch (ExcepcionAS2Financiero e)
/*  82:    */     {
/*  83: 94 */       this.context.setRollbackOnly();
/*  84: 95 */       throw e;
/*  85:    */     }
/*  86:    */     catch (ExcepcionAS2 e)
/*  87:    */     {
/*  88: 97 */       this.context.setRollbackOnly();
/*  89: 98 */       throw e;
/*  90:    */     }
/*  91:    */     catch (Exception e)
/*  92:    */     {
/*  93:100 */       this.context.setRollbackOnly();
/*  94:101 */       throw e;
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void contabilizar(MovimientoBancario movimientoBancario)
/*  99:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 100:    */   {
/* 101:116 */     Date fechaContabilizacion = movimientoBancario.getFecha();
/* 102:    */     Asiento asiento;
/* 103:    */     Asiento asiento;
/* 104:119 */     if (movimientoBancario.getDetalleAsiento() != null)
/* 105:    */     {
/* 106:120 */       asiento = movimientoBancario.getDetalleAsiento().getAsiento();
/* 107:    */     }
/* 108:    */     else
/* 109:    */     {
/* 110:123 */       asiento = new Asiento();
/* 111:124 */       asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 112:125 */       asiento.setSucursal(AppUtil.getSucursal());
/* 113:    */       
/* 114:127 */       TipoAsiento tipoAsiento = movimientoBancario.getDocumento().getTipoAsiento();
/* 115:    */       
/* 116:129 */       asiento.setTipoAsiento(tipoAsiento);
/* 117:    */     }
/* 118:133 */     String concepto = "";
/* 119:    */     
/* 120:135 */     concepto = movimientoBancario.getConceptoContable().getNombre() + " #" + movimientoBancario.getDocumentoReferencia();
/* 121:136 */     asiento.setConcepto(concepto);
/* 122:137 */     asiento.setFecha(fechaContabilizacion);
/* 123:    */     
/* 124:139 */     List<DetalleInterfazContable> listaDA = new ArrayList();
/* 125:    */     
/* 126:141 */     listaDA.addAll(this.movimientoBancarioDao.getMovimientoBanacarioCBIC(movimientoBancario.getIdMovimientoBancario()));
/* 127:    */     List<DocumentoContabilizacion> listaDocumentoContabilizacion;
/* 128:    */     List<CriterioDistribucion> listaCriterioDistribucion;
/* 129:    */     List<Integer> list;
/* 130:    */     List<DetalleInterfazContableProceso> lista;
/* 131:143 */     if (movimientoBancario.getDocumento().getDocumentoBase() == DocumentoBase.GASTOS_BANCARIOS)
/* 132:    */     {
/* 133:144 */       listaDA.addAll(this.movimientoBancarioDao.getMovimientoBanacarioGBIC(movimientoBancario.getIdMovimientoBancario()));
/* 134:    */       
/* 135:146 */       super.generarAsiento(asiento, listaDA, movimientoBancario.getDocumento(), movimientoBancario.getId());
/* 136:    */     }
/* 137:    */     else
/* 138:    */     {
/* 139:149 */       super.generarAsiento(asiento, listaDA, movimientoBancario.getDocumento(), movimientoBancario.getId());
/* 140:    */       
/* 141:151 */       listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(movimientoBancario
/* 142:152 */         .getIdOrganizacion(), DocumentoBase.CONCEPTOS_CONTABLES);
/* 143:    */       
/* 144:154 */       listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(movimientoBancario
/* 145:155 */         .getIdOrganizacion(), DocumentoBase.CONCEPTOS_CONTABLES);
/* 146:    */       
/* 147:157 */       list = new ArrayList();
/* 148:158 */       list.add(Integer.valueOf(movimientoBancario.getIdMovimientoBancario()));
/* 149:159 */       lista = new ArrayList();
/* 150:160 */       for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/* 151:    */       {
/* 152:161 */         lista.addAll(this.movimientoBancarioDao.getInterfazMovimientoBancarioDimensiones(list));
/* 153:162 */         asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso
/* 154:163 */           .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, movimientoBancario.getValor().compareTo(BigDecimal.ZERO) > 0));
/* 155:    */       }
/* 156:    */     }
/* 157:169 */     this.servicioAsiento.guardar(asiento);
/* 158:    */     
/* 159:    */ 
/* 160:172 */     movimientoBancario.setEstado(Estado.CONTABILIZADO);
/* 161:174 */     for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/* 162:175 */       if (detalleAsiento.getCuentaContable().getTipoCuentaContable() == TipoCuentaContable.BANCO)
/* 163:    */       {
/* 164:178 */         MovimientoBancario mb = buscarPorId(Integer.valueOf(movimientoBancario.getId()));
/* 165:179 */         if (mb == null)
/* 166:    */         {
/* 167:180 */           movimientoBancario.setIdMovimientoBancario(0);
/* 168:181 */           movimientoBancario.setEliminado(false);
/* 169:    */         }
/* 170:183 */         movimientoBancario.setDetalleAsiento(detalleAsiento);
/* 171:184 */         this.movimientoBancarioDao.guardar(movimientoBancario);
/* 172:    */       }
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void actualizar(MovimientoBancario movimientoBancario)
/* 177:    */   {
/* 178:197 */     this.movimientoBancarioDao.actualizar(movimientoBancario);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void eliminar(MovimientoBancario movimientoBancario)
/* 182:    */   {
/* 183:207 */     this.movimientoBancarioDao.eliminar(movimientoBancario);
/* 184:    */   }
/* 185:    */   
/* 186:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 187:    */   public void anular(MovimientoBancario movimientoBancario)
/* 188:    */     throws ExcepcionAS2
/* 189:    */   {
/* 190:    */     try
/* 191:    */     {
/* 192:215 */       validarPeriodo(movimientoBancario);
/* 193:216 */       movimientoBancario.setEstado(Estado.ANULADO);
/* 194:    */       
/* 195:    */ 
/* 196:219 */       this.movimientoBancarioDao.guardar(movimientoBancario);
/* 197:220 */       movimientoBancario.getDetalleAsiento().getAsiento().setIndicadorAutomatico(false);
/* 198:221 */       this.servicioAsiento.anular(movimientoBancario.getDetalleAsiento().getAsiento());
/* 199:    */     }
/* 200:    */     catch (ExcepcionAS2 e)
/* 201:    */     {
/* 202:224 */       this.context.setRollbackOnly();
/* 203:225 */       e.printStackTrace();
/* 204:226 */       throw e;
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public MovimientoBancario buscarPorId(Integer id)
/* 209:    */   {
/* 210:237 */     return (MovimientoBancario)this.movimientoBancarioDao.buscarPorId(id);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public List<MovimientoBancario> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 214:    */   {
/* 215:249 */     return this.movimientoBancarioDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public MovimientoBancario recuperaDetalleBancario(int idDetalleMovimientoBanacario)
/* 219:    */     throws ExcepcionAS2Financiero
/* 220:    */   {
/* 221:255 */     MovimientoBancario movimientoBancario = this.movimientoBancarioDao.recuperaMovimientoBancario(idDetalleMovimientoBanacario);
/* 222:    */     try
/* 223:    */     {
/* 224:259 */       Asiento asiento = this.servicioAsiento.cargarDetalle(movimientoBancario.getDetalleAsiento().getAsiento().getId());
/* 225:260 */       movimientoBancario.getDetalleAsiento().setAsiento(asiento);
/* 226:    */     }
/* 227:    */     catch (ExcepcionAS2Financiero e)
/* 228:    */     {
/* 229:262 */       e.printStackTrace();
/* 230:    */     }
/* 231:264 */     esEditable(movimientoBancario);
/* 232:265 */     return movimientoBancario;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public List<MovimientoBancario> getListaMovimientoBancarioPorAsiento(int idAsiento)
/* 236:    */   {
/* 237:275 */     return this.movimientoBancarioDao.getListaMovimientoBancarioPorAsiento(idAsiento);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public int contarPorCriterio(Map<String, String> filters)
/* 241:    */   {
/* 242:285 */     return this.movimientoBancarioDao.contarPorCriterio(filters);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void esEditable(MovimientoBancario movimientoBancario)
/* 246:    */     throws ExcepcionAS2Financiero
/* 247:    */   {
/* 248:296 */     validarPeriodo(movimientoBancario);
/* 249:298 */     if (movimientoBancario.getEstado() == Estado.ANULADO) {
/* 250:300 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/* 251:    */     }
/* 252:302 */     if (movimientoBancario.getDetalleAsiento().getAsiento().getEstado() == Estado.APROBADO) {
/* 253:304 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   private void validarPeriodo(MovimientoBancario movimientoBancario)
/* 258:    */     throws ExcepcionAS2Financiero
/* 259:    */   {
/* 260:315 */     this.servicioPeriodo.buscarPorFecha(movimientoBancario.getFecha(), movimientoBancario.getIdOrganizacion(), movimientoBancario.getDocumento().getDocumentoBase());
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void cambiarEstado(MovimientoBancario movimientoBancario, EstadoCheque estadoCheque, String observacion)
/* 264:    */     throws AS2Exception
/* 265:    */   {
/* 266:321 */     MovimientoBancarioEstadoCheque mbec = new MovimientoBancarioEstadoCheque();
/* 267:322 */     mbec.setEstadoCheque(estadoCheque);
/* 268:323 */     mbec.setMovimientoBancario(movimientoBancario);
/* 269:324 */     mbec.setObservacion(observacion);
/* 270:325 */     movimientoBancario.setEstadoCheque(estadoCheque);
/* 271:    */     try
/* 272:    */     {
/* 273:327 */       this.movimientoBancarioDao.guardar(movimientoBancario);
/* 274:328 */       this.servicioMBEC.guardar(mbec);
/* 275:    */     }
/* 276:    */     catch (Exception e)
/* 277:    */     {
/* 278:330 */       this.context.setRollbackOnly();
/* 279:331 */       throw new AS2Exception(e.getMessage());
/* 280:    */     }
/* 281:    */   }
/* 282:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioMovimientoBancarioImpl
 * JD-Core Version:    0.7.0.1
 */