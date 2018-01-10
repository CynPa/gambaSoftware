/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ImpuestoDao;
/*   4:    */ import com.asinfo.as2.dao.RangoImpuestoDao;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.Impuesto;
/*   7:    */ import com.asinfo.as2.entities.RangoImpuesto;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*  11:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuestoRemoto;
/*  12:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.ejb.Stateless;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class ServicioImpuestoImpl
/*  22:    */   implements ServicioImpuesto, ServicioImpuestoRemoto
/*  23:    */ {
/*  24:    */   @EJB
/*  25:    */   private ImpuestoDao impuestoDao;
/*  26:    */   @EJB
/*  27:    */   private RangoImpuestoDao rangoImpuestoDao;
/*  28:    */   
/*  29:    */   public void guardar(Impuesto impuesto)
/*  30:    */     throws ExcepcionAS2, AS2Exception
/*  31:    */   {
/*  32: 55 */     validarUnicaFechaHasta(impuesto);
/*  33: 56 */     impuesto.setDescripcion(impuesto.getDescripcion() + " ");
/*  34: 57 */     for (RangoImpuesto rangoImpuesto : impuesto.getListaRangoImpuesto()) {
/*  35: 58 */       this.rangoImpuestoDao.guardar(rangoImpuesto);
/*  36:    */     }
/*  37: 60 */     this.impuestoDao.guardar(impuesto);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void validarUnicaFechaHasta(Impuesto impuesto)
/*  41:    */     throws AS2Exception
/*  42:    */   {
/*  43: 64 */     int contador = 0;
/*  44: 65 */     if ((impuesto.getListaRangoImpuesto() != null) && (impuesto.getListaRangoImpuesto().size() > 1)) {
/*  45: 66 */       for (RangoImpuesto ri : impuesto.getListaRangoImpuesto()) {
/*  46: 67 */         if (ri.getFechaHasta() == null) {
/*  47: 68 */           contador++;
/*  48:    */         }
/*  49:    */       }
/*  50:    */     }
/*  51: 73 */     if (contador > 1) {
/*  52: 74 */       throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioImpuestoImpl.SE_ADMITE_UN_SOLO_RANGO_SIN_FECHA_HASTA", new String[] { "" });
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void eliminar(Impuesto impuesto)
/*  57:    */     throws ExcepcionAS2, AS2Exception
/*  58:    */   {
/*  59: 86 */     impuesto = this.impuestoDao.cargarDetalle(impuesto.getId());
/*  60: 88 */     for (RangoImpuesto rangoImpuesto : impuesto.getListaRangoImpuesto()) {
/*  61: 89 */       rangoImpuesto.setEliminado(true);
/*  62:    */     }
/*  63: 92 */     impuesto.setEliminado(true);
/*  64:    */     
/*  65: 94 */     guardar(impuesto);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Impuesto buscarPorId(Integer id)
/*  69:    */     throws ExcepcionAS2
/*  70:    */   {
/*  71:104 */     return (Impuesto)this.impuestoDao.buscarPorId(id);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Impuesto cargarDetalle(int idCategoriaImpuesto)
/*  75:    */     throws ExcepcionAS2Financiero
/*  76:    */   {
/*  77:114 */     return this.impuestoDao.cargarDetalle(idCategoriaImpuesto);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<Impuesto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  81:    */   {
/*  82:125 */     return this.impuestoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<Impuesto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  86:    */   {
/*  87:136 */     return this.impuestoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean getIndicadorTributario()
/*  91:    */   {
/*  92:141 */     return this.impuestoDao.getIndicadorTributario();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Impuesto buscarPorId(int id)
/*  96:    */   {
/*  97:151 */     Impuesto impuesto = (Impuesto)this.impuestoDao.buscarPorId(Integer.valueOf(id));
/*  98:152 */     if (impuesto.getCuentaContableCompra() != null) {
/*  99:153 */       impuesto.getCuentaContableCompra().getId();
/* 100:    */     }
/* 101:156 */     return impuesto;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public RangoImpuesto getRangoRangoImpuestoTributario(Date fecha, int idOrganizacion)
/* 105:    */   {
/* 106:166 */     return this.rangoImpuestoDao.getRangoRangoImpuestoTributario(fecha, idOrganizacion);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int contarPorCriterio(Map<String, String> filters)
/* 110:    */   {
/* 111:176 */     return this.impuestoDao.contarPorCriterio(filters);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Impuesto buscarPorCodigo(String codigo, int idOrganizacion)
/* 115:    */   {
/* 116:186 */     return this.impuestoDao.buscarPorCodigo(codigo, idOrganizacion);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public BigDecimal getPorcentajeIVA(int idOrganizacion, Date fecha)
/* 120:    */   {
/* 121:191 */     return this.rangoImpuestoDao.getPorcentajeIVA(idOrganizacion, fecha);
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioImpuestoImpl
 * JD-Core Version:    0.7.0.1
 */