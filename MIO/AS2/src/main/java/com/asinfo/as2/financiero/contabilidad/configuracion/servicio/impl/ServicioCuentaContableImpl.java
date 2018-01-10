/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CuentaContableDao;
/*   4:    */ import com.asinfo.as2.dao.NivelCuentaDao;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.NivelCuenta;
/*   9:    */ import com.asinfo.as2.enumeraciones.GrupoCuenta;
/*  10:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  12:    */ import com.asinfo.as2.enumeraciones.TipoEstadoFinanciero;
/*  13:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  14:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  15:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.ejb.Stateless;
/*  23:    */ import javax.ejb.TransactionAttribute;
/*  24:    */ import javax.ejb.TransactionAttributeType;
/*  25:    */ 
/*  26:    */ @Stateless
/*  27:    */ public class ServicioCuentaContableImpl
/*  28:    */   implements ServicioCuentaContable
/*  29:    */ {
/*  30:    */   @EJB
/*  31:    */   private CuentaContableDao cuentaContableDao;
/*  32:    */   @EJB
/*  33:    */   private NivelCuentaDao nivelCuentaDao;
/*  34:    */   
/*  35:    */   public void guardar(CuentaContable cuentaContable)
/*  36:    */     throws ExcepcionAS2Financiero
/*  37:    */   {
/*  38: 59 */     if ((this.cuentaContableDao.existeCuentaContable(cuentaContable.getCodigo(), cuentaContable.getIdOrganizacion()) != null) && (cuentaContable.getId() <= 0)) {
/*  39: 60 */       throw new ExcepcionAS2Financiero("msg_error_cuenta_contable_codigo", ": " + cuentaContable.getCodigo());
/*  40:    */     }
/*  41: 63 */     if (cuentaContable.getNivelCuenta().getCodigo() == 1) {
/*  42: 64 */       cuentaContable.setCuentaPadre(null);
/*  43:    */     }
/*  44: 66 */     if ((cuentaContable.getNivelCuenta().getCodigo() != 1) && (cuentaContable.getCuentaPadre() == null)) {
/*  45: 68 */       throw new ExcepcionAS2Financiero("msg_error_cuenta_contable_padre", cuentaContable.getCodigo() + "-" + cuentaContable.getNombre());
/*  46:    */     }
/*  47: 70 */     this.cuentaContableDao.guardar(cuentaContable);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void eliminar(CuentaContable cuentaContable)
/*  51:    */   {
/*  52: 82 */     this.cuentaContableDao.eliminar(cuentaContable);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public CuentaContable buscarPorId(Integer id)
/*  56:    */   {
/*  57: 93 */     return (CuentaContable)this.cuentaContableDao.buscarPorId(id);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public List<CuentaContable> buscarPorTipo(TipoCuentaContable tipoCuentaContable, int idOrganizacion)
/*  61:    */   {
/*  62:105 */     return this.cuentaContableDao.buscarPorTipo(tipoCuentaContable, idOrganizacion);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public CuentaContable verificaMovimiento(Integer id)
/*  66:    */     throws ExcepcionAS2Financiero
/*  67:    */   {
/*  68:115 */     CuentaContable cuentaContable = buscarPorId(id);
/*  69:116 */     if (!cuentaContable.isIndicadorMovimiento())
/*  70:    */     {
/*  71:117 */       cuentaContable = new CuentaContable();
/*  72:118 */       cuentaContable.setIdCuentaContable(-1);
/*  73:119 */       throw new ExcepcionAS2Financiero("msg_info_cuenta_contable_0001");
/*  74:    */     }
/*  75:121 */     return cuentaContable;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public CuentaContable buscarPorCodigo(String codigoCuentaContable, int idOrganizacion)
/*  79:    */     throws ExcepcionAS2Financiero
/*  80:    */   {
/*  81:131 */     return this.cuentaContableDao.buscarPorCodigo(codigoCuentaContable, idOrganizacion);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<CuentaContable> buscarPorGrupoNivel(GrupoCuenta grupoCuenta, int idNivelCuenta, int idOrganizacion)
/*  85:    */   {
/*  86:142 */     NivelCuenta nivelCuenta = (NivelCuenta)this.nivelCuentaDao.buscarPorId(Integer.valueOf(idNivelCuenta));
/*  87:143 */     if (nivelCuenta == null) {
/*  88:144 */       return new ArrayList();
/*  89:    */     }
/*  90:146 */     return this.cuentaContableDao.buscarPorGrupoNivel(grupoCuenta, nivelCuenta.getCodigo() - 1, idOrganizacion);
/*  91:    */   }
/*  92:    */   
/*  93:    */   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
/*  94:    */   public List<Object[]> calcularSaldos(Date fechaDesde, Date fechaHasta, TipoEstadoFinanciero tipoEstadoFinanciero, String dimension, String codigoDimension, boolean indicadorNIIF, int idSucursal)
/*  95:    */     throws ExcepcionAS2Financiero
/*  96:    */   {
/*  97:160 */     List<GrupoCuenta> listaGrupos = new ArrayList();
/*  98:162 */     if (tipoEstadoFinanciero == TipoEstadoFinanciero.BALANCE_GENERAL)
/*  99:    */     {
/* 100:163 */       listaGrupos.add(GrupoCuenta.ACTIVO);
/* 101:164 */       listaGrupos.add(GrupoCuenta.PASIVO);
/* 102:165 */       listaGrupos.add(GrupoCuenta.CAPITAL);
/* 103:    */     }
/* 104:166 */     else if (tipoEstadoFinanciero == TipoEstadoFinanciero.BALANCE_RESULTADOS)
/* 105:    */     {
/* 106:167 */       listaGrupos.add(GrupoCuenta.INGRESOS);
/* 107:168 */       listaGrupos.add(GrupoCuenta.COSTOS);
/* 108:169 */       listaGrupos.add(GrupoCuenta.GASTOS);
/* 109:    */     }
/* 110:    */     else
/* 111:    */     {
/* 112:172 */       listaGrupos.add(GrupoCuenta.ACTIVO);
/* 113:173 */       listaGrupos.add(GrupoCuenta.PASIVO);
/* 114:174 */       listaGrupos.add(GrupoCuenta.CAPITAL);
/* 115:175 */       listaGrupos.add(GrupoCuenta.INGRESOS);
/* 116:176 */       listaGrupos.add(GrupoCuenta.COSTOS);
/* 117:177 */       listaGrupos.add(GrupoCuenta.GASTOS);
/* 118:    */     }
/* 119:179 */     return this.cuentaContableDao.calcularSaldos(fechaDesde, fechaHasta, dimension, codigoDimension, indicadorNIIF, listaGrupos, idSucursal);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public BigDecimal obteneResultadoEjercicio(Date fechaDesde, Date fechaHasta, String dimension, String codigoDimension, boolean indicadorNIIF, int idSucursal, int idOrganizacion)
/* 123:    */   {
/* 124:191 */     return this.cuentaContableDao.obteneResultadoEjercicio(fechaDesde, fechaHasta, dimension, codigoDimension, indicadorNIIF, idSucursal, idOrganizacion);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public BigDecimal obtenerSaldo(Date fechaDesde, Date fechaHasta, int idCuentaContable, ValoresCalculo valoresCalculo, TipoCalculo tipoCalculo, boolean indicadorNIIF, int idSucursal)
/* 128:    */   {
/* 129:204 */     return this.cuentaContableDao.obtenerSaldo(fechaDesde, fechaHasta, idCuentaContable, valoresCalculo, tipoCalculo, indicadorNIIF, idSucursal, null, null);
/* 130:    */   }
/* 131:    */   
/* 132:    */   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
/* 133:    */   public List<CuentaContable> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 134:    */   {
/* 135:217 */     return this.cuentaContableDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public List<CuentaContable> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 139:    */   {
/* 140:228 */     return this.cuentaContableDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public CuentaContable cargarDetalle(int idCuentaContable)
/* 144:    */   {
/* 145:238 */     return this.cuentaContableDao.cargarDetalle(idCuentaContable);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int contarPorCriterio(Map<String, String> filters)
/* 149:    */   {
/* 150:248 */     return this.cuentaContableDao.contarPorCriterio(filters);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public BigDecimal obtenerSaldo(Date fechaDesde, Date fechaHasta, int idCuentaContable, ValoresCalculo valoresCalculo, TipoCalculo tipoCalculo, boolean indicadorNIIF, int idSucursal, String dimension, String valorDimension)
/* 154:    */   {
/* 155:254 */     return this.cuentaContableDao.obtenerSaldo(fechaDesde, fechaHasta, idCuentaContable, valoresCalculo, tipoCalculo, indicadorNIIF, idSucursal, dimension, valorDimension);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public List<DetalleAsiento> obtenerCierreCuentas(int idCuentaContable, List<Integer> listaCuentaContable, Date fechaDesde, Date fechaHasta)
/* 159:    */     throws ExcepcionAS2Financiero
/* 160:    */   {
/* 161:261 */     return this.cuentaContableDao.obtenerCierreCuentas(idCuentaContable, listaCuentaContable, fechaDesde, fechaHasta);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public List<DetalleAsiento> obtenerCierreCuentas(List<Integer> listaCuentaContable, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 165:    */     throws ExcepcionAS2Financiero
/* 166:    */   {
/* 167:268 */     return this.cuentaContableDao.obtenerCierreCuentas(listaCuentaContable, fechaDesde, fechaHasta, idOrganizacion);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<CuentaContable> buscarCuentasMovimientoPorCodigo(String codigoCuentaContable, int idOrganizacion)
/* 171:    */     throws ExcepcionAS2Financiero
/* 172:    */   {
/* 173:273 */     return this.cuentaContableDao.buscarCuentasMovimientoPorCodigo(codigoCuentaContable, idOrganizacion);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public List<Object[]> obtenerValores(Date fechaDesde, Date fechaHasta, int idOrganizacion, List<CuentaContable> listaCuentaContable, ValoresCalculo valoresCalculo, TipoCalculo tipoCalculo, boolean indicadorNIIF, int idSucursal, String dimension, String codigoDimension)
/* 177:    */   {
/* 178:285 */     return this.cuentaContableDao.obtenerValores(fechaDesde, fechaHasta, idOrganizacion, listaCuentaContable, valoresCalculo, tipoCalculo, indicadorNIIF, idSucursal, dimension, codigoDimension);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public BigDecimal obtenerSaldoPorConbinacionDimensiones(Date fechaDesde, Date fechaHasta, CuentaContable cuentaContable, ValoresCalculo valoresCalculo, TipoCalculo tipoCalculo, boolean indicadorNIIF, int idSucursal, DimensionContable dimension1, DimensionContable dimension2, DimensionContable dimension3, DimensionContable dimension4, DimensionContable dimension5)
/* 182:    */   {
/* 183:293 */     return this.cuentaContableDao.obtenerSaldoPorConbinacionDimensiones(fechaDesde, fechaHasta, cuentaContable, valoresCalculo, tipoCalculo, indicadorNIIF, idSucursal, dimension1, dimension2, dimension3, dimension4, dimension5);
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioCuentaContableImpl
 * JD-Core Version:    0.7.0.1
 */