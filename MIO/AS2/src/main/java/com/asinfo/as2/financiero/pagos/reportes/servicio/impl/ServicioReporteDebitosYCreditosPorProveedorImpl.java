/*   1:    */ package com.asinfo.as2.financiero.pagos.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.reportes.financiero.pagos.ReporteDebitosYCreditosPorProveedorDao;
/*   4:    */ import com.asinfo.as2.entities.Empresa;
/*   5:    */ import com.asinfo.as2.entities.Sucursal;
/*   6:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReporteDebitosYCreditosPorProveedor;
/*   9:    */ import java.math.BigDecimal;
/*  10:    */ import java.text.SimpleDateFormat;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Collection;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import java.util.TreeMap;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class ServicioReporteDebitosYCreditosPorProveedorImpl
/*  24:    */   implements ServicioReporteDebitosYCreditosPorProveedor
/*  25:    */ {
/*  26:    */   @EJB
/*  27:    */   private ReporteDebitosYCreditosPorProveedorDao reporteDebitosYCreditosPorProveedorDao;
/*  28:    */   
/*  29:    */   public List<Object[]> getReporteDebitosYCreditosPorProveedor(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, int tipoReporte, boolean saldosDiferenteDeCero)
/*  30:    */   {
/*  31: 46 */     int columnas = 13;
/*  32:    */     
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47: 62 */     Map<String, Object[]> hmReporte = new HashMap();
/*  48:    */     
/*  49: 64 */     List<Object[]> listaCompras = this.reporteDebitosYCreditosPorProveedorDao.getCompras(idOrganizacion, sucursal, empresa, DocumentoBase.FACTURA_PROVEEDOR, fechaDesde, fechaHasta, tipoReporte);
/*  50: 66 */     for (Iterator localIterator = listaCompras.iterator(); localIterator.hasNext();)
/*  51:    */     {
/*  52: 66 */       datos = (Object[])localIterator.next();
/*  53: 67 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[4].toString()).toString());
/*  54: 68 */       Object[] detalleReporte = new Object[columnas];
/*  55: 69 */       detalleReporte[0] = datos[0];
/*  56: 70 */       detalleReporte[1] = datos[1];
/*  57: 71 */       detalleReporte[6] = datos[2];
/*  58: 72 */       detalleReporte[7] = datos[3];
/*  59: 73 */       if (tipoReporte == 2)
/*  60:    */       {
/*  61: 74 */         detalleReporte[2] = datos[5];
/*  62: 75 */         detalleReporte[3] = datos[6];
/*  63: 76 */         detalleReporte[12] = datos[4];
/*  64:    */       }
/*  65: 78 */       hmReporte.put(clave, detalleReporte);
/*  66:    */     }
/*  67: 81 */     Object listaSaldos = this.reporteDebitosYCreditosPorProveedorDao.getSaldos(idOrganizacion, empresa, fechaDesde, tipoReporte);
/*  68: 82 */     for (Object[] datos = ((List)listaSaldos).iterator(); datos.hasNext();)
/*  69:    */     {
/*  70: 82 */       datos = (Object[])datos.next();
/*  71: 83 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[3].toString()).toString());
/*  72: 84 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/*  73: 85 */       if (detalleReporte == null)
/*  74:    */       {
/*  75: 86 */         detalleReporte = new Object[columnas];
/*  76: 87 */         detalleReporte[0] = datos[0];
/*  77: 88 */         detalleReporte[1] = datos[1];
/*  78: 89 */         if (tipoReporte == 2)
/*  79:    */         {
/*  80: 90 */           detalleReporte[2] = datos[3];
/*  81: 91 */           detalleReporte[3] = datos[4];
/*  82: 92 */           detalleReporte[12] = datos[3];
/*  83:    */         }
/*  84: 94 */         hmReporte.put(clave, detalleReporte);
/*  85:    */       }
/*  86: 96 */       detalleReporte[5] = datos[2];
/*  87:    */     }
/*  88: 99 */     List<Object[]> listaND = this.reporteDebitosYCreditosPorProveedorDao.getCompras(idOrganizacion, sucursal, empresa, DocumentoBase.NOTA_DEBITO_PROVEEDOR, fechaDesde, fechaHasta, tipoReporte);
/*  89:101 */     for (Object[] datos = listaND.iterator(); datos.hasNext();)
/*  90:    */     {
/*  91:101 */       datos = (Object[])datos.next();
/*  92:102 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[4].toString()).toString());
/*  93:103 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/*  94:104 */       if (detalleReporte == null)
/*  95:    */       {
/*  96:105 */         detalleReporte = new Object[columnas];
/*  97:106 */         detalleReporte[0] = datos[0];
/*  98:107 */         detalleReporte[1] = datos[1];
/*  99:108 */         if (tipoReporte == 2)
/* 100:    */         {
/* 101:109 */           detalleReporte[2] = datos[5];
/* 102:110 */           detalleReporte[3] = datos[6];
/* 103:111 */           detalleReporte[12] = datos[4];
/* 104:    */         }
/* 105:113 */         hmReporte.put(clave, detalleReporte);
/* 106:    */       }
/* 107:115 */       detalleReporte[8] = ((BigDecimal)datos[2]).add((BigDecimal)datos[3]);
/* 108:    */     }
/* 109:118 */     List<Object[]> listaNC = this.reporteDebitosYCreditosPorProveedorDao.getCompras(idOrganizacion, sucursal, empresa, DocumentoBase.NOTA_CREDITO_PROVEEDOR, fechaDesde, fechaHasta, tipoReporte);
/* 110:120 */     for (Object[] datos = listaNC.iterator(); datos.hasNext();)
/* 111:    */     {
/* 112:120 */       datos = (Object[])datos.next();
/* 113:121 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[4].toString()).toString());
/* 114:122 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/* 115:123 */       if (detalleReporte == null)
/* 116:    */       {
/* 117:124 */         detalleReporte = new Object[columnas];
/* 118:125 */         detalleReporte[0] = datos[0];
/* 119:126 */         detalleReporte[1] = datos[1];
/* 120:127 */         if (tipoReporte == 2)
/* 121:    */         {
/* 122:128 */           detalleReporte[2] = datos[5];
/* 123:129 */           detalleReporte[3] = datos[6];
/* 124:130 */           detalleReporte[12] = datos[4];
/* 125:    */         }
/* 126:132 */         hmReporte.put(clave, detalleReporte);
/* 127:    */       }
/* 128:134 */       detalleReporte[9] = ((BigDecimal)datos[2]).add((BigDecimal)datos[3]);
/* 129:    */     }
/* 130:137 */     List<Object[]> listaLiquidaciones = this.reporteDebitosYCreditosPorProveedorDao.getLiquidaciones(idOrganizacion, sucursal, empresa, fechaDesde, fechaHasta, tipoReporte);
/* 131:139 */     for (Object[] datos = listaLiquidaciones.iterator(); datos.hasNext();)
/* 132:    */     {
/* 133:139 */       datos = (Object[])datos.next();
/* 134:140 */       String clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[3].toString()).toString());
/* 135:141 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/* 136:142 */       if (detalleReporte == null)
/* 137:    */       {
/* 138:143 */         detalleReporte = new Object[columnas];
/* 139:144 */         detalleReporte[0] = datos[0];
/* 140:145 */         detalleReporte[1] = datos[1];
/* 141:146 */         if (tipoReporte == 2)
/* 142:    */         {
/* 143:147 */           detalleReporte[2] = datos[4];
/* 144:148 */           detalleReporte[3] = datos[5];
/* 145:149 */           detalleReporte[12] = datos[3];
/* 146:    */         }
/* 147:151 */         hmReporte.put(clave, detalleReporte);
/* 148:    */       }
/* 149:153 */       detalleReporte[10] = datos[2];
/* 150:    */     }
/* 151:156 */     List<Object[]> listaCobros = this.reporteDebitosYCreditosPorProveedorDao.getPagos(idOrganizacion, sucursal, empresa, fechaDesde, fechaHasta, Estado.CONTABILIZADO, tipoReporte);
/* 152:158 */     for (Object[] datos = listaCobros.iterator(); datos.hasNext();)
/* 153:    */     {
/* 154:158 */       datos = (Object[])datos.next();
/* 155:159 */       clave = datos[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(datos[3].toString()).toString());
/* 156:160 */       Object[] detalleReporte = (Object[])hmReporte.get(clave);
/* 157:161 */       if (detalleReporte == null)
/* 158:    */       {
/* 159:162 */         detalleReporte = new Object[columnas];
/* 160:163 */         detalleReporte[0] = datos[0];
/* 161:164 */         detalleReporte[1] = datos[1];
/* 162:165 */         if (tipoReporte == 2)
/* 163:    */         {
/* 164:166 */           detalleReporte[2] = datos[4];
/* 165:167 */           detalleReporte[3] = datos[5];
/* 166:168 */           detalleReporte[12] = datos[3];
/* 167:    */         }
/* 168:170 */         hmReporte.put(clave, detalleReporte);
/* 169:    */       }
/* 170:172 */       detalleReporte[11] = datos[2];
/* 171:    */     }
/* 172:    */     Object[] datos;
/* 173:    */     String clave;
/* 174:175 */     if (saldosDiferenteDeCero)
/* 175:    */     {
/* 176:176 */       datos = (Object[][])hmReporte.values().toArray(new Object[hmReporte.values().size()][]);datos = datos.length;
/* 177:176 */       for (clave = 0; clave < datos; clave++)
/* 178:    */       {
/* 179:176 */         Object[] fila = datos[clave];
/* 180:    */         
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:182 */         BigDecimal saldo = (fila[5] == null ? BigDecimal.ZERO : (BigDecimal)fila[5]).add(fila[6] == null ? BigDecimal.ZERO : (BigDecimal)fila[6]).add(fila[7] == null ? BigDecimal.ZERO : (BigDecimal)fila[7]).add(fila[8] == null ? BigDecimal.ZERO : (BigDecimal)fila[8]).subtract(fila[10] == null ? BigDecimal.ZERO : (BigDecimal)fila[10]).subtract(fila[11] == null ? BigDecimal.ZERO : (BigDecimal)fila[11]);
/* 186:183 */         if (saldo.compareTo(BigDecimal.ZERO) == 0)
/* 187:    */         {
/* 188:184 */           String clave = fila[0].toString() + (tipoReporte == 1 ? "" : new StringBuilder().append("-").append(fila[12].toString()).toString());
/* 189:185 */           hmReporte.remove(clave);
/* 190:    */         }
/* 191:    */       }
/* 192:    */     }
/* 193:190 */     Map<String, Object[]> tmReporte = new TreeMap();
/* 194:191 */     for (Object[] fila : hmReporte.values())
/* 195:    */     {
/* 196:192 */       String clave = fila[1].toString() + "-" + fila[0].toString();
/* 197:193 */       if (tipoReporte == 2) {
/* 198:194 */         if (fila[3] != null) {
/* 199:195 */           clave = clave + "-" + new SimpleDateFormat("yyyyMMdd").format((Date)fila[3]) + "-" + fila[2].toString();
/* 200:    */         } else {
/* 201:197 */           clave = clave + "-" + fila[2].toString();
/* 202:    */         }
/* 203:    */       }
/* 204:200 */       tmReporte.put(clave, fila);
/* 205:    */     }
/* 206:202 */     List<Object[]> datosReporte = new ArrayList(tmReporte.values());
/* 207:    */     
/* 208:204 */     return datosReporte;
/* 209:    */   }
/* 210:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.reportes.servicio.impl.ServicioReporteDebitosYCreditosPorProveedorImpl
 * JD-Core Version:    0.7.0.1
 */