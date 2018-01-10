/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.cobros;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  11:    */ import java.math.BigDecimal;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.List;
/*  16:    */ import javax.ejb.Stateless;
/*  17:    */ import javax.persistence.EntityManager;
/*  18:    */ import javax.persistence.Query;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class ReporteNotaCreditoClienteDao
/*  22:    */   extends AbstractDaoAS2<AnticipoProveedor>
/*  23:    */ {
/*  24:    */   public ReporteNotaCreditoClienteDao()
/*  25:    */   {
/*  26: 33 */     super(AnticipoProveedor.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public List<Object[]> getListaNotaCreditoCliente(Date fechaDesde, Date fechaHasta, Empresa empresa, MotivoNotaCreditoCliente motivoNotaCreditoCliente, int idOrganizacion, Sucursal sucursal)
/*  30:    */   {
/*  31: 38 */     List<Object[]> lista = new ArrayList();
/*  32: 39 */     HashMap<String, Object[]> hmNotasCreditoCliente = new HashMap();
/*  33: 40 */     for (Object[] objects : getListaNotaCreditoClienteYDevolucionCliente(fechaDesde, fechaHasta, empresa, motivoNotaCreditoCliente, idOrganizacion, sucursal)) {
/*  34: 43 */       if (hmNotasCreditoCliente.containsKey((String)objects[0] + (String)objects[3] + String.valueOf((DocumentoBase)objects[4]))) {
/*  35: 44 */         ((Object[])hmNotasCreditoCliente.get((String)objects[0] + (String)objects[3] + String.valueOf((DocumentoBase)objects[4])))[5] = ((BigDecimal)((Object[])hmNotasCreditoCliente.get((String)objects[0] + (String)objects[3] + String.valueOf((DocumentoBase)objects[4])))[5]).add((BigDecimal)objects[5]);
/*  36:    */       } else {
/*  37: 46 */         hmNotasCreditoCliente.put((String)objects[0] + (String)objects[3] + String.valueOf((DocumentoBase)objects[4]), objects);
/*  38:    */       }
/*  39:    */     }
/*  40: 50 */     lista.addAll(hmNotasCreditoCliente.values());
/*  41: 51 */     return lista;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List<Object[]> getListaNotaCreditoClienteYDevolucionCliente(Date fechaDesde, Date fechaHasta, Empresa empresa, MotivoNotaCreditoCliente motivoNotaCreditoCliente, int idOrganizacion, Sucursal sucursal)
/*  45:    */   {
/*  46: 57 */     StringBuilder sql = new StringBuilder();
/*  47:    */     
/*  48: 59 */     sql.append(" SELECT e.identificacion,e.nombreComercial,e.nombreFiscal, mnc.nombre ,doc.documentoBase,SUM(f.total-f.descuento+f.impuesto),");
/*  49: 60 */     sql.append(" COALESCE((");
/*  50: 61 */     sql.append(" \tSELECT  SUM(fa.total-fa.descuento+fa.impuesto)");
/*  51: 62 */     sql.append(" \tFROM FacturaCliente fa ");
/*  52: 63 */     sql.append(" \tLEFT JOIN fa.documento doca ");
/*  53: 64 */     sql.append(" \tLEFT JOIN fa.empresa ea ");
/*  54: 65 */     sql.append(" \tWHERE fa.fecha BETWEEN :fechaDesde AND :fechaHasta");
/*  55: 66 */     if (empresa != null) {
/*  56: 67 */       sql.append("\tAND ea=:empresa ");
/*  57:    */     }
/*  58: 69 */     sql.append(" \tAND fa.idOrganizacion=:idOrganizacion");
/*  59: 70 */     sql.append(" \tAND (doca.documentoBase IN (:FACTURA_CLIENTE) )");
/*  60: 71 */     sql.append(" \tAND fa.estado<>:estado");
/*  61: 72 */     sql.append(" \tAND e.idEmpresa=ea.idEmpresa");
/*  62: 73 */     sql.append(" \tGROUP BY ea.idEmpresa");
/*  63: 74 */     sql.append(" ), 0)");
/*  64: 75 */     sql.append(" FROM FacturaCliente f ");
/*  65: 76 */     sql.append(" LEFT JOIN f.documento doc ");
/*  66: 77 */     sql.append(" LEFT JOIN f.facturaClientePadre fp ");
/*  67: 78 */     sql.append(" LEFT JOIN f.motivoNotaCreditoCliente mnc ");
/*  68: 79 */     sql.append(" LEFT JOIN f.empresa e ");
/*  69: 80 */     sql.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta");
/*  70: 81 */     if (empresa != null) {
/*  71: 82 */       sql.append(" AND e=:empresa ");
/*  72:    */     }
/*  73: 84 */     if (motivoNotaCreditoCliente != null) {
/*  74: 85 */       sql.append(" AND mnc=:motivoNotaCreditoCliente");
/*  75:    */     }
/*  76: 87 */     sql.append(" AND f.idOrganizacion=:idOrganizacion");
/*  77: 88 */     sql.append(" AND (doc.documentoBase IN (:NOTA_CREDITO_CLIENTE,:DEVOLUCION_CLIENTE) )");
/*  78: 89 */     sql.append(" AND f.estado<>:estado");
/*  79: 91 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  80: 92 */       sql.append(" AND f.sucursal = :sucursal");
/*  81:    */     }
/*  82: 95 */     sql.append("");
/*  83: 96 */     sql.append(" GROUP BY e.idEmpresa,e.identificacion,e.nombreComercial,e.nombreFiscal,doc.documentoBase,mnc.nombre");
/*  84: 97 */     sql.append(" ORDER BY e.nombreComercial,e.nombreFiscal,doc.documentoBase");
/*  85: 98 */     Query query = this.em.createQuery(sql.toString());
/*  86: 99 */     fechaDesde = fechaDesde == null ? FuncionesUtiles.obtenerFechaInicial() : fechaDesde;
/*  87:100 */     fechaHasta = fechaHasta == null ? FuncionesUtiles.obtenerFechaFinal() : fechaHasta;
/*  88:101 */     query.setParameter("fechaDesde", fechaDesde);
/*  89:102 */     query.setParameter("fechaHasta", fechaHasta);
/*  90:103 */     if (empresa != null) {
/*  91:104 */       query.setParameter("empresa", empresa);
/*  92:    */     }
/*  93:106 */     query.setParameter("estado", Estado.ANULADO);
/*  94:107 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  95:108 */     if (motivoNotaCreditoCliente != null) {
/*  96:109 */       query.setParameter("motivoNotaCreditoCliente", motivoNotaCreditoCliente);
/*  97:    */     }
/*  98:111 */     query.setParameter("NOTA_CREDITO_CLIENTE", DocumentoBase.NOTA_CREDITO_CLIENTE);
/*  99:112 */     query.setParameter("DEVOLUCION_CLIENTE", DocumentoBase.DEVOLUCION_CLIENTE);
/* 100:113 */     query.setParameter("FACTURA_CLIENTE", DocumentoBase.FACTURA_CLIENTE);
/* 101:115 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 102:116 */       query.setParameter("sucursal", sucursal);
/* 103:    */     }
/* 104:119 */     return query.getResultList();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List getReporteNotasCredito(Date fechaDesde, Date fechaHasta, Empresa empresa, MotivoNotaCreditoCliente motivoNotaCreditoCliente, int idOrganizacion, Sucursal sucursal)
/* 108:    */   {
/* 109:126 */     StringBuilder sql = new StringBuilder();
/* 110:    */     
/* 111:128 */     sql.append(" SELECT f.fecha,f.numero, fp.fecha, fp.numero, e.identificacion,e.nombreComercial,e.nombreFiscal, mnc.nombre ,d.nombre, SUM(f.total-f.descuento+f.impuesto + coalesce( fSRI.montoIce,0)), SUM(fp.total-fp.descuento+fp.impuesto+ coalesce(fpSRI.montoIce,0)), ");
/* 112:129 */     sql.append(" d.codigo, f.total, f.impuesto, fp.total, fp.impuesto, fp.referencia2, subemp.nombreFiscal, fpSRI.autorizacion, fSRI.autorizacion, fpSRI.documentoTransporteRefrendo, fp.referencia4 ");
/* 113:130 */     sql.append(" FROM FacturaCliente f ");
/* 114:131 */     sql.append(" LEFT JOIN f.facturaClienteSRI fSRI ");
/* 115:132 */     sql.append(" LEFT JOIN f.documento d ");
/* 116:133 */     sql.append(" LEFT JOIN f.facturaClientePadre fp ");
/* 117:134 */     sql.append(" LEFT JOIN fp.facturaClienteSRI fpSRI ");
/* 118:135 */     sql.append(" LEFT JOIN fp.subempresa sub ");
/* 119:136 */     sql.append(" LEFT JOIN sub.empresa subemp ");
/* 120:137 */     sql.append(" LEFT JOIN f.motivoNotaCreditoCliente mnc ");
/* 121:138 */     sql.append(" LEFT JOIN f.empresa e ");
/* 122:139 */     sql.append(" WHERE f.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 123:140 */     if (empresa != null) {
/* 124:141 */       sql.append(" AND e=:empresa ");
/* 125:    */     }
/* 126:143 */     if (motivoNotaCreditoCliente != null) {
/* 127:144 */       sql.append(" AND mnc=:motivoNotaCreditoCliente ");
/* 128:    */     }
/* 129:146 */     sql.append(" AND (d.documentoBase IN (:NOTA_CREDITO_CLIENTE,:DEVOLUCION_CLIENTE) )");
/* 130:147 */     sql.append(" AND f.idOrganizacion=:idOrganizacion");
/* 131:148 */     sql.append(" AND f.estado<>:estado");
/* 132:150 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 133:151 */       sql.append(" AND f.sucursal = :sucursal");
/* 134:    */     }
/* 135:154 */     sql.append(" GROUP BY f.fecha,f.numero, fp.fecha, fp.numero, e.identificacion,e.nombreComercial,e.nombreFiscal, mnc.nombre ,d.nombre, d.codigo, f.total, f.impuesto, fp.total, fp.impuesto, fp.referencia2, subemp.nombreFiscal, fpSRI.autorizacion, fSRI.autorizacion, fpSRI.documentoTransporteRefrendo, fp.referencia4 ");
/* 136:155 */     sql.append(" ORDER BY mnc.nombre, f.fecha, f.numero ");
/* 137:    */     
/* 138:157 */     Query query = this.em.createQuery(sql.toString());
/* 139:158 */     query.setParameter("fechaDesde", fechaDesde);
/* 140:159 */     query.setParameter("fechaHasta", fechaHasta);
/* 141:160 */     if (empresa != null) {
/* 142:161 */       query.setParameter("empresa", empresa);
/* 143:    */     }
/* 144:163 */     query.setParameter("estado", Estado.ANULADO);
/* 145:164 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 146:165 */     if (motivoNotaCreditoCliente != null) {
/* 147:166 */       query.setParameter("motivoNotaCreditoCliente", motivoNotaCreditoCliente);
/* 148:    */     }
/* 149:168 */     query.setParameter("NOTA_CREDITO_CLIENTE", DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 150:169 */     query.setParameter("DEVOLUCION_CLIENTE", DocumentoBase.DEVOLUCION_CLIENTE);
/* 151:171 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 152:172 */       query.setParameter("sucursal", sucursal);
/* 153:    */     }
/* 154:175 */     return query.getResultList();
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.cobros.ReporteNotaCreditoClienteDao
 * JD-Core Version:    0.7.0.1
 */