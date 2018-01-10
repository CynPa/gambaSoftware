/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DetalleVariableDao;
/*   4:    */ import com.asinfo.as2.dao.VariableDao;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetalleVariable;
/*   7:    */ import com.asinfo.as2.entities.Variable;
/*   8:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*   9:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  11:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioVariable;
/*  12:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.ejb.Stateless;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class ServicioVariableImpl
/*  23:    */   implements ServicioVariable
/*  24:    */ {
/*  25:    */   @EJB
/*  26:    */   private VariableDao variableDao;
/*  27:    */   @EJB
/*  28:    */   private DetalleVariableDao detalleVariableDao;
/*  29:    */   @EJB
/*  30:    */   private ServicioCuentaContable servicioCuentaContable;
/*  31:    */   
/*  32:    */   public void guardar(Variable variable)
/*  33:    */     throws ExcepcionAS2Financiero
/*  34:    */   {
/*  35: 60 */     this.variableDao.guardar(variable);
/*  36: 61 */     for (DetalleVariable detalle : variable.getListaDetalleVariable()) {
/*  37: 62 */       this.detalleVariableDao.guardar(detalle);
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void eliminar(Variable variable)
/*  42:    */   {
/*  43: 70 */     for (DetalleVariable detalle : variable.getListaDetalleVariable()) {
/*  44: 71 */       this.detalleVariableDao.eliminar(detalle);
/*  45:    */     }
/*  46: 73 */     this.variableDao.eliminar(variable);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Variable buscarPorId(Integer id)
/*  50:    */   {
/*  51: 79 */     return (Variable)this.variableDao.buscarPorId(id);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<Variable> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  55:    */   {
/*  56: 87 */     return this.variableDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Variable cargarDetalle(int idVariable)
/*  60:    */   {
/*  61: 92 */     return this.variableDao.cargarDetalle(idVariable);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int contarPorCriterio(Map<String, String> filters)
/*  65:    */   {
/*  66: 97 */     return this.variableDao.contarPorCriterio(filters);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public List<Variable> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  70:    */   {
/*  71:104 */     return this.variableDao.obtenerListaCombo(sortField, true, null);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Variable calcularValorVariable(String nombre, Date fechaDesde, Date fechaHasta)
/*  75:    */   {
/*  76:111 */     BigDecimal totalCuentaContable = BigDecimal.ZERO;
/*  77:112 */     Map<String, String> filters = new HashMap();
/*  78:113 */     filters.put("nombre", "= " + nombre);
/*  79:114 */     Variable variable = (Variable)obtenerListaPorPagina(0, 1, "nombre", true, filters).get(0);
/*  80:115 */     variable = this.variableDao.cargarDetalle(variable.getId());
/*  81:116 */     for (DetalleVariable detalleVariable : variable.getListaDetalleVariable()) {
/*  82:117 */       totalCuentaContable = totalCuentaContable.add(this.servicioCuentaContable.obtenerSaldo(fechaDesde, fechaHasta, detalleVariable.getCuentaContable().getId(), ValoresCalculo.DEBE_HABER, TipoCalculo.SALDO_FINAL, false, detalleVariable
/*  83:118 */         .getIdSucursal()));
/*  84:    */     }
/*  85:120 */     variable.setValor(totalCuentaContable);
/*  86:121 */     return variable;
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioVariableImpl
 * JD-Core Version:    0.7.0.1
 */