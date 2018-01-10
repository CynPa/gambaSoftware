/*   1:    */ package com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   5:    */ import com.asinfo.as2.dao.CajaChicaDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   7:    */ import com.asinfo.as2.entities.Asiento;
/*   8:    */ import com.asinfo.as2.entities.CajaChica;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  14:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCajaChica;
/*  17:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  18:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  19:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.ejb.SessionContext;
/*  28:    */ import javax.ejb.Stateless;
/*  29:    */ import javax.ejb.TransactionManagement;
/*  30:    */ import javax.ejb.TransactionManagementType;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ 
/*  33:    */ @Stateless
/*  34:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  35:    */ public class ServicioCajaChicaImpl
/*  36:    */   extends AbstractServicioAS2Financiero
/*  37:    */   implements ServicioCajaChica
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = -3807643347187670334L;
/*  40:    */   @EJB
/*  41:    */   private CajaChicaDao cajaChicaDao;
/*  42:    */   @EJB
/*  43:    */   private ServicioAsiento servicioAsiento;
/*  44:    */   @EJB
/*  45:    */   private ServicioPeriodo servicioPeriodo;
/*  46:    */   @EJB
/*  47:    */   private ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  48:    */   @EJB
/*  49:    */   private ServicioSecuencia servicioSecuencia;
/*  50:    */   
/*  51:    */   public void guardar(CajaChica cajaChica)
/*  52:    */   {
/*  53: 78 */     this.cajaChicaDao.guardar(cajaChica);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void eliminar(CajaChica cajaChica)
/*  57:    */     throws ExcepcionAS2Financiero
/*  58:    */   {
/*  59: 91 */     anular(cajaChica);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public CajaChica buscarPorId(Integer id)
/*  63:    */   {
/*  64:104 */     return (CajaChica)this.cajaChicaDao.buscarPorId(id);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public List<CajaChica> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  68:    */   {
/*  69:117 */     return this.cajaChicaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public List<CajaChica> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  73:    */   {
/*  74:129 */     return this.cajaChicaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public CajaChica cargarDetalle(int idCajaChica)
/*  78:    */   {
/*  79:141 */     return this.cajaChicaDao.cargarDetalle(idCajaChica);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int contarPorCriterio(Map<String, String> filters)
/*  83:    */   {
/*  84:153 */     return this.cajaChicaDao.contarPorCriterio(filters);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void contabilizar(CajaChica cajaChica, Asiento asiento)
/*  88:    */     throws ExcepcionAS2
/*  89:    */   {
/*  90:    */     try
/*  91:    */     {
/*  92:168 */       this.servicioPeriodo.buscarPorFecha(cajaChica.getFechaContabilizacion(), cajaChica.getIdOrganizacion(), cajaChica.getDocumento().getDocumentoBase());
/*  93:    */       
/*  94:170 */       this.servicioAsiento.guardar(asiento);
/*  95:    */       
/*  96:172 */       cajaChica.setEstado(Estado.CONTABILIZADO);
/*  97:    */       
/*  98:174 */       cajaChica.setFechaContabilizacion(cajaChica.getFechaContabilizacion());
/*  99:    */       
/* 100:176 */       cajaChica.setAsiento(asiento);
/* 101:    */       
/* 102:178 */       cajaChica.setValor(this.servicioFacturaProveedorSRI.valorAcumuladoCajaChica(null, cajaChica));
/* 103:    */       
/* 104:180 */       this.cajaChicaDao.guardar(cajaChica);
/* 105:182 */       if ((cajaChica.getSecuencia() != null) && (cajaChica.getEstado().equals(Estado.CONTABILIZADO))) {
/* 106:183 */         this.servicioSecuencia.actualizarSecuencia(cajaChica.getSecuencia(), cajaChica.getDocumentoReferencia());
/* 107:    */       }
/* 108:187 */       this.servicioFacturaProveedorSRI.actualizarFechaDeRegistroPorCajaChica(cajaChica);
/* 109:    */     }
/* 110:    */     catch (ExcepcionAS2Compras e)
/* 111:    */     {
/* 112:190 */       this.context.setRollbackOnly();
/* 113:191 */       throw e;
/* 114:    */     }
/* 115:    */     catch (ExcepcionAS2Financiero e)
/* 116:    */     {
/* 117:193 */       this.context.setRollbackOnly();
/* 118:194 */       throw e;
/* 119:    */     }
/* 120:    */     catch (ExcepcionAS2 e)
/* 121:    */     {
/* 122:196 */       this.context.setRollbackOnly();
/* 123:197 */       throw e;
/* 124:    */     }
/* 125:    */     catch (Exception e)
/* 126:    */     {
/* 127:199 */       this.context.setRollbackOnly();
/* 128:200 */       LOG.error(e);
/* 129:201 */       throw new ExcepcionAS2(e);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Asiento vistaPreviaAsiento(CajaChica cajaChica, int idOrganizacion)
/* 134:    */     throws ExcepcionAS2
/* 135:    */   {
/* 136:    */     try
/* 137:    */     {
/* 138:218 */       this.cajaChicaDao.guardar(cajaChica);
/* 139:219 */       Date fechaContabilizacion = cajaChica.getFechaContabilizacion();
/* 140:220 */       int idCajaChica = cajaChica.getIdCajaChica();
/* 141:    */       
/* 142:222 */       Asiento asiento = new Asiento();
/* 143:223 */       asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 144:224 */       asiento.setSucursal(AppUtil.getSucursal());
/* 145:225 */       TipoAsiento tipoAsiento = cajaChica.getDocumento().getTipoAsiento();
/* 146:226 */       asiento.setTipoAsiento(tipoAsiento);
/* 147:227 */       asiento.setIndicadorAutomatico(true);
/* 148:    */       
/* 149:    */ 
/* 150:230 */       String concepto = "";
/* 151:    */       
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:235 */       asiento.setFecha(fechaContabilizacion);
/* 156:236 */       List<DetalleInterfazContable> listaDA = new ArrayList();
/* 157:237 */       listaDA.addAll(this.cajaChicaDao.getCajaChicaIC(idCajaChica, idOrganizacion));
/* 158:    */       
/* 159:    */ 
/* 160:240 */       super.generarAsiento(asiento, listaDA, cajaChica.getDocumento());
/* 161:    */       
/* 162:    */ 
/* 163:    */ 
/* 164:244 */       concepto = cajaChica.getDocumento().getNombre().trim() + " #" + cajaChica.getCodigo() + " " + cajaChica.getNotaContabilizacion() + " Beneficiario: " + cajaChica.getBeneficiario() + " Doc: " + cajaChica.getDocumentoReferencia() + " Fecha Desde: " + FuncionesUtiles.dateToString(cajaChica.getFechaDesde()) + " Fecha Hasta: " + FuncionesUtiles.dateToString(cajaChica.getFechaHasta());
/* 165:245 */       asiento.setConcepto(concepto);
/* 166:    */       
/* 167:247 */       cajaChica.setCuentaBancariaOrganizacion(null);
/* 168:248 */       cajaChica.setFormaPago(null);
/* 169:249 */       cajaChica.setFechaContabilizacion(null);
/* 170:250 */       cajaChica.setDocumentoReferencia(null);
/* 171:251 */       cajaChica.setBeneficiario(null);
/* 172:    */       
/* 173:253 */       this.cajaChicaDao.guardar(cajaChica);
/* 174:    */       
/* 175:255 */       return asiento;
/* 176:    */     }
/* 177:    */     catch (ExcepcionAS2Financiero e)
/* 178:    */     {
/* 179:258 */       this.context.setRollbackOnly();
/* 180:259 */       throw e;
/* 181:    */     }
/* 182:    */     catch (Exception e)
/* 183:    */     {
/* 184:261 */       this.context.setRollbackOnly();
/* 185:262 */       LOG.error(e);
/* 186:263 */       throw new ExcepcionAS2(e);
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void esEditable(CajaChica cajaChica)
/* 191:    */     throws ExcepcionAS2Financiero
/* 192:    */   {
/* 193:277 */     if (cajaChica.getFechaContabilizacion() != null) {
/* 194:278 */       this.servicioPeriodo.buscarPorFecha(cajaChica.getFechaContabilizacion(), cajaChica.getIdOrganizacion(), cajaChica.getDocumento().getDocumentoBase());
/* 195:    */     }
/* 196:280 */     if (cajaChica.getAsiento() != null) {
/* 197:281 */       throw new ExcepcionAS2Financiero("msg_error_asiento_generado_proceso");
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   private void anular(CajaChica cajaChica)
/* 202:    */     throws ExcepcionAS2Financiero
/* 203:    */   {
/* 204:288 */     CajaChica cc = cargarDetalle(cajaChica.getId());
/* 205:290 */     if (cc.getAsiento() != null)
/* 206:    */     {
/* 207:291 */       if ((cc.getAsiento().getEstado() != Estado.APROBADO) && (cc.getAsiento().getEstado() != Estado.ANULADO))
/* 208:    */       {
/* 209:293 */         this.servicioPeriodo.buscarPorFecha(cajaChica.getFechaContabilizacion(), cajaChica.getIdOrganizacion(), cajaChica.getDocumento().getDocumentoBase());
/* 210:294 */         cc.getAsiento().setIndicadorAutomatico(false);
/* 211:295 */         this.servicioAsiento.anular(cc.getAsiento());
/* 212:296 */         cc.setCuentaBancariaOrganizacion(null);
/* 213:297 */         cc.setFormaPago(null);
/* 214:298 */         cc.setFechaContabilizacion(null);
/* 215:299 */         cc.setDocumentoReferencia(null);
/* 216:300 */         cc.setBeneficiario(null);
/* 217:301 */         cc.setAsiento(null);
/* 218:302 */         cc.setNotaContabilizacion(null);
/* 219:303 */         cc.setEstado(Estado.ELABORADO);
/* 220:304 */         this.cajaChicaDao.guardar(cc);
/* 221:    */       }
/* 222:    */       else
/* 223:    */       {
/* 224:307 */         throw new ExcepcionAS2Financiero("msg_accion_no_permitida");
/* 225:    */       }
/* 226:    */     }
/* 227:    */     else {
/* 228:310 */       throw new ExcepcionAS2Financiero("msg_accion_no_permitida");
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void verificaEmisionRetencion(CajaChica cajaChica)
/* 233:    */     throws ExcepcionAS2
/* 234:    */   {
/* 235:324 */     this.cajaChicaDao.verificaEmisionRetencion(cajaChica);
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioCajaChicaImpl
 * JD-Core Version:    0.7.0.1
 */