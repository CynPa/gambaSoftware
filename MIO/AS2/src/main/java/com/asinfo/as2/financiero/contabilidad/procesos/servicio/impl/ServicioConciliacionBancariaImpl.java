/*   1:    */ package com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.dao.ConciliacionBancariaDao;
/*   5:    */ import com.asinfo.as2.dao.MovimientoBancarioDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.entities.Asiento;
/*   8:    */ import com.asinfo.as2.entities.ConciliacionBancaria;
/*   9:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  16:    */ import com.asinfo.as2.enumeraciones.Parametro;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  20:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioConciliacionBancaria;
/*  21:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  22:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  23:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  24:    */ import com.asinfo.as2.util.AppUtil;
/*  25:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  26:    */ import java.math.BigDecimal;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.ejb.SessionContext;
/*  32:    */ import javax.ejb.Stateless;
/*  33:    */ import javax.ejb.TransactionAttribute;
/*  34:    */ import javax.ejb.TransactionAttributeType;
/*  35:    */ import javax.ejb.TransactionManagement;
/*  36:    */ import javax.ejb.TransactionManagementType;
/*  37:    */ 
/*  38:    */ @Stateless
/*  39:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  40:    */ public class ServicioConciliacionBancariaImpl
/*  41:    */   extends AbstractServicioAS2Financiero
/*  42:    */   implements ServicioConciliacionBancaria
/*  43:    */ {
/*  44:    */   @EJB
/*  45:    */   private ConciliacionBancariaDao conciliacionBancariaDao;
/*  46:    */   @EJB
/*  47:    */   private MovimientoBancarioDao movimientoBancarioDao;
/*  48:    */   @EJB
/*  49:    */   private ServicioPeriodo servicioPeriodo;
/*  50:    */   @EJB
/*  51:    */   private ServicioAsiento servicioAsiento;
/*  52:    */   @EJB
/*  53:    */   private ServicioTipoAsiento servicioTipoAsiento;
/*  54:    */   @EJB
/*  55:    */   private ServicioDocumento servicioDocumento;
/*  56:    */   
/*  57:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  58:    */   public void guardar(ConciliacionBancaria conciliacionBancaria)
/*  59:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  60:    */   {
/*  61:    */     try
/*  62:    */     {
/*  63: 84 */       this.conciliacionBancariaDao.guardar(conciliacionBancaria);
/*  64:    */       
/*  65: 86 */       conciliarDesconciliar(conciliacionBancaria);
/*  66:    */       
/*  67: 88 */       this.conciliacionBancariaDao.cerrarConciliacionesAnteriores(conciliacionBancaria);
/*  68:    */     }
/*  69:    */     catch (Exception e)
/*  70:    */     {
/*  71: 91 */       this.context.setRollbackOnly();
/*  72: 92 */       throw new ExcepcionAS2(e);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void conciliarDesconciliar(ConciliacionBancaria conciliacionBancaria)
/*  77:    */   {
/*  78: 97 */     for (MovimientoBancario movimientoBancario : conciliacionBancaria.getListaMovimientoBancario())
/*  79:    */     {
/*  80: 98 */       if (!movimientoBancario.isConciliado()) {
/*  81: 99 */         movimientoBancario.setConciliacionBancaria(null);
/*  82:    */       } else {
/*  83:101 */         movimientoBancario.setConciliacionBancaria(conciliacionBancaria);
/*  84:    */       }
/*  85:103 */       this.movimientoBancarioDao.guardar(movimientoBancario);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void eliminar(ConciliacionBancaria conciliacionBancaria)
/*  90:    */   {
/*  91:115 */     this.conciliacionBancariaDao.eliminar(conciliacionBancaria);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public ConciliacionBancaria buscarPorId(Integer id)
/*  95:    */   {
/*  96:126 */     return (ConciliacionBancaria)this.conciliacionBancariaDao.buscarPorId(id);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public ConciliacionBancaria cargarDetalle(int idConciliacionBancaria)
/* 100:    */   {
/* 101:136 */     return this.conciliacionBancariaDao.cargarDetalle(idConciliacionBancaria);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public List<MovimientoBancario> cargarDatosConciliar(ConciliacionBancaria conciliacionBancaria)
/* 105:    */   {
/* 106:147 */     return this.conciliacionBancariaDao.cargarDatosConciliar(conciliacionBancaria);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List<ConciliacionBancaria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 110:    */   {
/* 111:154 */     return this.conciliacionBancariaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int contarPorCriterio(Map<String, String> filters)
/* 115:    */   {
/* 116:164 */     return this.conciliacionBancariaDao.contarPorCriterio(filters);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List getReporteConciliacionBancaria(Date fechaConciliacion, int idCuentaBancariaOrganizacion)
/* 120:    */     throws ExcepcionAS2
/* 121:    */   {
/* 122:175 */     return this.conciliacionBancariaDao.getReporteConciliacionBancaria(fechaConciliacion, idCuentaBancariaOrganizacion);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void esEditable(ConciliacionBancaria conciliacionBancaria)
/* 126:    */     throws ExcepcionAS2Financiero
/* 127:    */   {
/* 128:188 */     this.servicioPeriodo.buscarPorFecha(conciliacionBancaria.getFecha(), conciliacionBancaria.getIdOrganizacion(), DocumentoBase.CONCEPTOS_CONTABLES);
/* 129:190 */     if (conciliacionBancaria.getEstado() != Estado.ELABORADO) {
/* 130:192 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/* 131:    */     }
/* 132:194 */     if ((conciliacionBancaria.getAsiento() != null) && (conciliacionBancaria.getAsiento().getEstado() == Estado.REVISADO)) {
/* 133:196 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Date obtenerFechaUltimaConciliacion(Date fechaConciliacion)
/* 138:    */   {
/* 139:209 */     return null;
/* 140:    */   }
/* 141:    */   
/* 142:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 143:    */   public void contabilizarGastosYRetenciones(ConciliacionBancaria conciliacionBancaria, List<DetalleInterfazContable> listaDetalleIC)
/* 144:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 145:    */   {
/* 146:    */     try
/* 147:    */     {
/* 148:225 */       Date fechaContabilizacion = conciliacionBancaria.getFecha();
/* 149:226 */       Documento documentoOrigen = null;
/* 150:    */       Asiento asiento;
/* 151:229 */       if (conciliacionBancaria.getAsiento() != null)
/* 152:    */       {
/* 153:230 */         Asiento asiento = this.servicioAsiento.cargarDetalle(conciliacionBancaria.getAsiento().getId());
/* 154:231 */         documentoOrigen = asiento.getDocumentoOrigen();
/* 155:    */       }
/* 156:    */       else
/* 157:    */       {
/* 158:234 */         asiento = new Asiento();
/* 159:235 */         asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 160:236 */         asiento.setSucursal(AppUtil.getSucursal());
/* 161:237 */         documentoOrigen = (Documento)this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.GASTOS_BANCARIOS).get(0);
/* 162:    */         try
/* 163:    */         {
/* 164:240 */           TipoAsiento tipoAsiento = this.servicioTipoAsiento.buscarPorId(ParametrosSistema.getTipoAsientoConciliacionBancariaAutomatica());
/* 165:241 */           asiento.setTipoAsiento(tipoAsiento);
/* 166:    */         }
/* 167:    */         catch (Exception e)
/* 168:    */         {
/* 169:244 */           throw new ExcepcionAS2Financiero("msg_info_configuracion", " " + Parametro.TIPO_ASIENTO_CONCILIACION_BANCARIA_AUTOMATICA.getNombre());
/* 170:    */         }
/* 171:    */       }
/* 172:249 */       String concepto = "";
/* 173:250 */       concepto = "Conciliacion " + conciliacionBancaria.getCuentaBancariaOrganizacion().getNombreCompleto();
/* 174:    */       
/* 175:252 */       asiento.setConcepto(concepto);
/* 176:253 */       asiento.setFecha(fechaContabilizacion);
/* 177:    */       
/* 178:    */ 
/* 179:256 */       super.generarAsiento(asiento, listaDetalleIC, documentoOrigen);
/* 180:    */       
/* 181:    */ 
/* 182:259 */       this.servicioAsiento.guardar(asiento);
/* 183:261 */       if (conciliacionBancaria.getAsiento() == null)
/* 184:    */       {
/* 185:263 */         conciliacionBancaria.setAsiento(asiento);
/* 186:264 */         this.conciliacionBancariaDao.guardar(conciliacionBancaria);
/* 187:    */       }
/* 188:    */     }
/* 189:    */     catch (ExcepcionAS2Financiero e)
/* 190:    */     {
/* 191:268 */       this.context.setRollbackOnly();
/* 192:269 */       throw e;
/* 193:    */     }
/* 194:    */     catch (ExcepcionAS2 e)
/* 195:    */     {
/* 196:271 */       this.context.setRollbackOnly();
/* 197:272 */       throw e;
/* 198:    */     }
/* 199:    */     catch (Exception e)
/* 200:    */     {
/* 201:274 */       this.context.setRollbackOnly();
/* 202:275 */       throw new ExcepcionAS2(e);
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public BigDecimal totalCreditoConciliado(ConciliacionBancaria conciliacionBancaria)
/* 207:    */   {
/* 208:281 */     return this.conciliacionBancariaDao.totalCreditoConciliado(conciliacionBancaria);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public BigDecimal totalDebitoConciliado(ConciliacionBancaria conciliacionBancaria)
/* 212:    */   {
/* 213:286 */     return this.conciliacionBancariaDao.totalDebitoConciliado(conciliacionBancaria);
/* 214:    */   }
/* 215:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioConciliacionBancariaImpl
 * JD-Core Version:    0.7.0.1
 */